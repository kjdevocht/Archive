/* CS 236 LAB 4 Database.h Kevin DeVocht*/


#include <fstream>
#include <algorithm>
#include <string>
#include <sstream>
#include <iostream>
#include <iomanip>
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <exception>
#include <set>
#include "Parser.h"


class Schema: public vector<string>
{
	
};

class Tuple: public vector<string>
{
	
};

class Relation
{
	public:
		string name;
		Schema schema;
		set<Tuple> tupleSet;
		Query query;
		
		
	Relation(string name0, Schema schema0) : name(name0), schema(schema0)
	{}
	
	string toString()
	{
		set<Tuple>::iterator it;
		string results = this->query.toString();

		if(this->tupleSet.size() == 0)
		{
			results += " No\n";
		}
		else
		{
			results += " Yes(" + getSize() + ")\n";
			for(it = tupleSet.begin(); it != tupleSet.end(); it++)
			{	
				vector<string> row = (*it);
				if(schema.size() !=0)
				{
					results +=" ";
				}
				
				for(int i = 0; i<row.size(); i++)
				{
					results += schema.at(i) + "=" + row.at(i);
					if(row.size() > 1 && i != row.size()-1)
					{
						results+= ", ";
					}
				}
				if(schema.size() !=0)
				{
					results+="\n";	
				}
			}
			
		}
		
		return results;
	}

	void select(string query, int index, Relation& qryRltn)
	{
		set<Tuple>::iterator it;
		set<Tuple> newTupleSet;
		for(it = tupleSet.begin(); it != tupleSet.end(); it++)
		{
			vector<string> row = (*it);
			Tuple tuple;
		
			if(row.at(index) == query)
			{
				for(int i = 0; i<row.size(); i++)
				{
					tuple.push_back(row.at(i));
				}
				newTupleSet.insert(tuple);
			}
		}
		qryRltn.tupleSet = newTupleSet;
	
	}
	
	void selectMulti(int indexOne, int indexTwo, Relation& qryRltn)
	{
		set<Tuple>::iterator it;
		set<Tuple> newTupleSet;
		for(it = tupleSet.begin(); it != tupleSet.end(); it++)
		{
			vector<string> row = (*it);
			Tuple tuple;
			if(row.at(indexOne) == row.at(indexTwo))
			{
				for(int i = 0; i<row.size(); i++)
				{
					tuple.push_back(row.at(i));
				}
				newTupleSet.insert(tuple);
			}
		}
		qryRltn.tupleSet = newTupleSet;
	}
	
	void project(Relation& qryRltn, vector<int>& projectLst)
	{
		set<Tuple> newTupleSet;
		Schema newSchema;
		
		for(int b = 0; b<projectLst.size(); b++)
		{
			newSchema.push_back(qryRltn.schema.at(projectLst.at(b)));
		}
		
		for(set<Tuple>::iterator it = qryRltn.tupleSet.begin(); 
			it != qryRltn.tupleSet.end(); it++)
		{
			Tuple tuple;
			for(int b = 0; b<projectLst.size(); b++)
			{
				tuple.push_back((*it).at(projectLst.at(b)));	
			}
			newTupleSet.insert(tuple);
		}
		qryRltn.schema = newSchema;
		qryRltn.tupleSet = newTupleSet;
	}
	
	void rename(const Predicate& qry, vector<int>& projectLst)
	{
		Schema newSchema;
		for(int i = 0; i<projectLst.size(); i++)
		{
			newSchema.push_back(qry.paramLst.at(projectLst.at(i)).param);	
		}
		
		this->schema = newSchema;
	}
	
	string getSize()
	{
		string size;
		set<Tuple> ::const_iterator it = this->tupleSet.begin();
		
		stringstream streamin(stringstream::out);
		streamin<<tupleSet.size();
		size = streamin.str();
		return size;
	}
};

class Database
{
	public:
		set<Relation> relations;
		char* input;
		DatalogProgram dlp;
		vector<Relation> tables;
		
	
	Database(char* input0) : input(input0)
		{readInput();}
		
	
		
	Relation findSchemes(string id)
	{
			for(int i = 0; i<tables.size(); i++)
			{
				if(tables.at(i).name == id)
				{
					return tables.at(i);
				}
			}
	}
	
	void checkForDuplicates(const vector<Parameter>& schema, Relation& qryRltn)
	{
		set<Tuple>::iterator it;
		set<Tuple> newTupleSet;
		for(int i = 0; i<schema.size(); i++)
		{
			for(int j = 0; j<schema.size(); j++)
			{
				if(schema.at(i).param == schema.at(j).param && i != j)
				{
					qryRltn.selectMulti(i, j, qryRltn);	
				}
			}
		}
	}
	
	void dupSchema(vector<int> &projectLst, set<string> &dupCol, 
			const string &param, int &j)
	{
		if(dupCol.find(param) == dupCol.end())
		{
			dupCol.insert(param);
			projectLst.push_back(j);
		}	
	}
	void runQueries(const QueryList& qLst, string& results)
	{
		for(int i = 0; i<qLst.qLst.size(); i++)
		{
			Relation qryRltn = findSchemes(qLst.qLst.at(i).qry.id);
			qryRltn.query = qLst.qLst.at(i);
			vector<int> projectLst;
			set<string> dupCol;
			for(int j = 0; j<qryRltn.schema.size(); j++)
			{
				checkForDuplicates(qLst.qLst.at(i).qry.paramLst, 
				qryRltn);
				if(qLst.qLst.at(i).qry.paramLst.at(j).param.at(0) == '\'')
				{
					qryRltn.select(qLst.qLst.at(i).qry.paramLst.at(j).param, j, 
					qryRltn);
				}
				else
				{
					dupSchema(projectLst, dupCol, 
					qLst.qLst.at(i).qry.paramLst.at(j).param, j);
				}
			}
			qryRltn.project(qryRltn, projectLst);
			qryRltn.rename(qLst.qLst.at(i).qry, projectLst);
			results += qryRltn.toString();
		}		
	}
		
	void generateTables(const SchemeList& schmLst, const FactList& fctLst)
	{
		for(int i = 0; i< schmLst.schmLst.size(); i++)
		{
			Schema schma;
			for(int j = 0; j<schmLst.schmLst.at(i).idLst.size();  j++)
			{
				schma.push_back(schmLst.schmLst.at(i).idLst.at(j));
			}
			Relation relation(schmLst.schmLst.at(i).id, schma);
			tables.push_back(relation);
		}

		for(int j = 0; j<fctLst.fctLst.size(); j++)
		{
			vector<Fact> facts = fctLst.fctLst;
			Fact fct = facts.at(j);
			Tuple tuple;
			for(int j = 0; j<fct.constLst.size(); j++)
			{
				tuple.push_back(fct.constLst.at(j));
			}

			getRelation(fct.id).tupleSet.insert(tuple);
		}
	}
	
	Relation& getRelation(string id)
	{
		for(int i = 0; i<tables.size(); i++)
		{
			if(id == tables.at(i).name)
			{
					return tables.at(i);
			}
		}
		Schema schema;
		Relation relation(id, schema);
	}
	void readInput()
	{
		//Function Variables
		string endResults = "";
		
		Lex lex(this->input); //Lab 1
		parse(lex.getTokens()); //Lab 2

		generateTables(this->dlp.schmLst, this->dlp.fctLst);
		runQueries(this->dlp.qLst, endResults);
		print(endResults);
	}
	
	void print(string results)
	{
		cout<<results;
	}
	
	string parse(vector<Token*> results)
	{
		//Function Variables
		TokenHolder tok(results);
		Parser parse(tok);
		string parseResults = "";

		
		try
		{
			this->dlp = parse.dataLogProgramParser();
		}
		catch (int e)
		{
			tok.currentToken = e;
		}
		return parseResults;
	}	
};





