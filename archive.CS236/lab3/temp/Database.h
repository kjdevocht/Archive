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
		string results = this->query.toString();
		if(this->tupleSet.size() == 0)
		{
			results += " No\n";
		}
		else
		{
			results += " Yes(" + getSize() + ")\n";
		}
		
		return results;
	}

	set<Tuple> select(string query, int index, set<Tuple> tupleSet)
	{
		set<Tuple>::iterator it;
		set<Tuple> newTupleSet;
		cout<<"SELECT"<<endl;
		for(it = tupleSet.begin(); it != tupleSet.end(); it++)
		{
			vector<string> row = (*it);
			Tuple tuple;
		
			if(row.at(index) == query)
			{
				cout<<"select:Row.at(index): "<<row.at(index)<<endl;
				for(int i = 0; i<row.size(); i++)
				{
					tuple.push_back(row.at(i));
				}
				newTupleSet.insert(tuple);
			}
		}
		return newTupleSet;	
	}
	
	//set<Tuple> s
	
	Relation project(Relation qryRltn, vector<int> projectLst)
	{
		set<Tuple>::iterator it;
		set<Tuple> newTupleSet;
		Tuple tuple;
		int schemaLoop = 0;
		for(it = qryRltn.tupleSet.begin(); it != qryRltn.tupleSet.end(); it++)
		{
			vector<string> row = (*it);
			for(int b = 0; b<projectLst.size(); b++)
			{
					row.erase(row.begin()+projectLst.at(b));
					if(schemaLoop == 0)
					{
						qryRltn.schema.erase(qryRltn.schema.begin()+projectLst.at(b));
					}
					
			}
			schemaLoop = 1;
			for(int i = 0; i<row.size(); i++)
			{
				tuple.push_back(row.at(i));
			}
			newTupleSet.insert(tuple);

		}
		
		return qryRltn;	
	}
	
	Schema rename(Predicate qry, vector<int> projectLst)
	{
		Schema newSchema;
		for(int i = 0; i<projectLst.size(); i++)
		{
			newSchema.push_back(qry.paramLst.at(projectLst.at(i)).param);	
		}
		return newSchema;
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
	
	Relation checkForDuplicates(vector<Parameter> schema, Relation qryRltn)
	{
		set<Tuple>::iterator it;
		for(int i = 0; i<schema.size(); i++)
		{
			for(int j = 0; j<schema.size(); j++)
			{
				if(schema.at(i).param == schema.at(j).param && i != j)
				{
					for(it = qryRltn.tupleSet.begin(); it != qryRltn.tupleSet.end(); it++)
					{
						vector<string> row = (*it);
						Tuple tuple;
						if(row.at(i) == row.at(j))
						{
							qryRltn.tupleSet = qryRltn.select(row.at(i), j, qryRltn.tupleSet);
						}
						
					}
				}
			}
		}
		return qryRltn;
	}
	
	void runQueries(QueryList qLst)
	{
		string results = "";
		for(int i = 0; i<qLst.qLst.size(); i++)
		{
		
			Relation qryRltn = findSchemes(qLst.qLst.at(i).qry.id);
			qryRltn.query = qLst.qLst.at(i);
			vector<int> projectLst;
			
			
			
			for(int j = 0; j<qryRltn.schema.size(); j++)
			{
				string::iterator it;
				qryRltn = checkForDuplicates(qLst.qLst.at(i).qry.paramLst, qryRltn);
				string param = qLst.qLst.at(i).qry.paramLst.at(j).param; 
				if(qLst.qLst.at(i).qry.paramLst.at(j).param.at(0) == '\'')
				{
					cout<<"ARE YOU HERE"<<endl;
					qryRltn.tupleSet = qryRltn.select(param, 
					j, qryRltn.tupleSet);
				}
				else
				{
					projectLst.push_back(j);
				}
				
			}
			qryRltn = qryRltn.project(qryRltn, projectLst); //Project on all Identifiers
			qryRltn.schema = qryRltn.rename(qLst.qLst.at(i).qry, projectLst);
			results += qryRltn.toString();
			
		}
		print(results);
			
	}	
	void generateTables(SchemeList schmLst, FactList fctLst)
	{
		
		
		for(int i = 0; i< schmLst.schmLst.size(); i++)
		{
			vector<Scheme> schemes = schmLst.schmLst;
			Scheme schm = schemes.at(i);
			Schema schma;
			for(int j = 0; j<schm.idLst.size();  j++)
			{
				
				schma.push_back(schm.idLst.at(j));
			}
			Relation relation = Relation(schm.id, schma);
			tables.push_back(relation);
		}
		
		for(int i = 0; i<tables.size(); i++)
		{
			for(int j = 0; j<fctLst.fctLst.size(); j++)
			{
				cout<<"Fact List size: "<<fctLst.fctLst.size()<<endl;  //PRINT OUT
				vector<Fact> facts = fctLst.fctLst;
				Fact fct = facts.at(j);
				Tuple tuple;
				for(int j = 0; j<fct.constLst.size(); j++)
				{
					tuple.push_back(fct.constLst.at(j));
				}
				if(fct.id == tables.at(i).name)
				{
						tables.at(i).tupleSet.insert(tuple);
				}
			}
			cout<<"TupleSet size: "<<tables.at(i).tupleSet.size()<<endl;  //PRINT OUT
		}
		
	
	}
	string readInput()
	{
		//Function Variables
		string endResults = "";
		
		Lex lex(this->input); //Lab 1
		parse(lex.getTokens()); //Lab 2
		
		endResults = "";
		generateTables(this->dlp.schmLst, this->dlp.fctLst);
		cout<<dlp.qLst.qLst.size()<<endl;  //PRINT OUT
		runQueries(this->dlp.qLst);

		return endResults;
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
			parseResults = "Failure!\n\t(" + tok.getType() + "," + "\"";
			parseResults+= tok.getValue() + "\"" + "," + tok.printLineNum() + ")\n";
		}
		return parseResults;	
	}	
};





