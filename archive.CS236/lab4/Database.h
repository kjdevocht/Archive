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
		Predicate query;
		
	Relation()
	{}
		
	Relation(string name0, Schema schema0) : name(name0), schema(schema0)
	{}
	
	void setRelation(Relation& qryRltn)
	{
		this->name = qryRltn.name;
		this->schema = qryRltn.schema;
		this->tupleSet = qryRltn.tupleSet;
		this->query = qryRltn.query;
	}
	
	void unionDLP(Relation& jnRltn)
	{
		tupleSet.insert(jnRltn.tupleSet.begin(), jnRltn.tupleSet.end());
	}
	
	void joinNameSchema(Relation& qryRltn)
	{

		set<string> checkDup;
		
		for(int i = 0; i<schema.size(); i++)
		{
			checkDup.insert(schema.at(i));
		}
		
		
		for(int i = 0; i<qryRltn.schema.size(); i++)
		{
			if(checkDup.count(qryRltn.schema.at(i)) == 0)
			{
				schema.push_back(qryRltn.schema.at(i));
			}
		}	

	
	}
	
	bool canJoin(Schema& qrySchema, Schema& thisSchema, 
	vector<string>& qryTuple, vector<string>& thisTuple)
	{
			for(int i = 0; i<thisSchema.size(); i++)
			{
				for(int j = 0; j<qrySchema.size(); j++)
				{
					if(thisSchema.at(i) == qrySchema.at(j) && 
					thisTuple.at(i) != qryTuple.at(j))
					{
						return false;
					}
				}
			}
			
			return true;
	}
	
	Tuple joinTuple(Schema& qrySchema, Schema& thisSchema, 
	vector<string>& qryTuple, vector<string>& thisTuple)
	{
			Tuple newTuple;
			set<string> checkDup;
			for(int i = 0; i<thisTuple.size(); i++)
			{
				newTuple.push_back(thisTuple.at(i));
				checkDup.insert(thisSchema.at(i));
			}
			
			for(int i = 0; i<qryTuple.size(); i++)
			{
				
				if(checkDup.count(qrySchema.at(i)) == 0)
				{
					newTuple.push_back(qryTuple.at(i));
				}
			}
			
			return newTuple;
	}
	
	void join(Relation& qryRltn)
	{
		set<Tuple>::iterator it;
		set<Tuple>::iterator qryIt;
		set<Tuple> newTupleSet;

		for(it = tupleSet.begin(); it != tupleSet.end(); it++)
		{
			for(qryIt = qryRltn.tupleSet.begin(); qryIt != qryRltn.tupleSet.end(); qryIt++)
			{
				vector<string> qry = (*qryIt);
				vector<string> ths = (*it);
				if(canJoin(qryRltn.schema, this->schema, qry, ths))
				{
					Tuple tuple = joinTuple(qryRltn.schema, this->schema, qry, ths);
					newTupleSet.insert(tuple);
				}
			}
		}
		tupleSet = newTupleSet;
		
		joinNameSchema(qryRltn);
	}
	
	
	string toString()
	{
		set<Tuple>::iterator it;
		string results = this->query.toString() + "?";

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
					results +="  ";
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

	void select(string query, int index)
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
		tupleSet = newTupleSet;
	
	}
	
	void selectMulti(int indexOne, int indexTwo)
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
		tupleSet = newTupleSet;
	}
	
	void project(vector<int>& projectLst)
	{
		set<Tuple> newTupleSet;
		Schema newSchema;
		
		for(int b = 0; b<projectLst.size(); b++)
		{
			newSchema.push_back(schema.at(projectLst.at(b)));
		}
		
		for(set<Tuple>::iterator it = tupleSet.begin(); it != tupleSet.end(); it++)
		{
			Tuple tuple;
			for(int b = 0; b<projectLst.size(); b++)
			{
				tuple.push_back((*it).at(projectLst.at(b)));	
			}
			newTupleSet.insert(tuple);
		}
		schema = newSchema;
		tupleSet = newTupleSet;
	}
	
	void rename(const Predicate& qry, vector<int>& projectLst)
	{
		Schema newSchema;
		string newName = "";
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
		
	
		
	int findSchemes(string id)
	{
			for(int i = 0; i<tables.size(); i++)
			{
				if(tables.at(i).name == id)
				{
					return i;
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
					qryRltn.selectMulti(i, j);	
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
	
	int getTupleTotal()
	{
		int total = 0;
		for(int i = 0; i<tables.size(); i++)
		{
			total += tables.at(i).tupleSet.size();
		}
		return total;
	}
	
	Schema ruleSchema(vector<Parameter>& paramLst, int& i)
	{
		Schema schema;
		for(int q = 0; q<paramLst.size(); q++)
		{
			schema.push_back(paramLst.at(q).param);			
		}
		return schema;	
	}
	void runRule(Relation& jnRltn, int j, vector<Predicate>& predLst, 
	string& results)
	{
		Relation qryRltn = tables.at(findSchemes(predLst.at(j).id));
		qryRltn.query = predLst.at(j);
		runQuery(qryRltn.query, qryRltn, results, true);
		if(jnRltn.schema.size() == 0)
		{
			jnRltn = qryRltn;
		}
		else
		{
			jnRltn.join(qryRltn);
		}		
	}
	
	void runRules(RuleList& rlLst, string& results, int& count)
	{
		int before = 0;
		int after = 1; 
		while(before != after)
		{
			before = getTupleTotal();
			for(int i = 0; i<rlLst.rlLst.size(); i++)
			{
				Relation jnRltn;
				for(int j = 0; j<rlLst.rlLst.at(i).predLst.size(); j++)
				{
					runRule(jnRltn, j, rlLst.rlLst.at(i).predLst, results); 
				}
				vector<int> projectLst = createProjectList(ruleSchema
				(rlLst.rlLst.at(i).rl.paramLst, i), jnRltn);
				jnRltn.project(projectLst);
				tables.at(findSchemes(rlLst.rlLst.at(i).rl.id)).
				unionDLP(jnRltn);	
			}
			count++;
			after = getTupleTotal();
		}
	}
	
	vector<int> createProjectList(Schema schema, Relation& jnRltn)
	{
		vector<int> projectLst;
		for(int j = 0; j<schema.size(); j++)
		{
			for(int k = 0; k<jnRltn.schema.size(); k++)
			{
				if(schema.at(j) == jnRltn.schema.at(k))
				{
						projectLst.push_back(k);
				}
			}
		}
		return projectLst;		
	}
	
	void runQuery(Predicate& query, Relation& qryRltn, string& results, 
		bool isRule)
	{
		vector<int> projectLst;
		set<string> dupCol;
		for(int j = 0; j<qryRltn.schema.size(); j++)
		{
			checkForDuplicates(query.paramLst, 
			qryRltn);
			
			if(query.paramLst.at(j).param.at(0) == '\'')
			{
				qryRltn.select(query.paramLst.at(j).param, j);
			}
			else
			{
				dupSchema(projectLst, dupCol, 
				query.paramLst.at(j).param, j);
			}
		}
		qryRltn.project(projectLst);
		qryRltn.rename(query, projectLst);
		
		if(!isRule)
		{
			results += qryRltn.toString();
		}
	}
	
	void runQueries(QueryList& qLst, string& results)
	{
		for(int i = 0; i<qLst.qLst.size(); i++)
		{
			Relation qryRltn = tables.at(findSchemes(qLst.qLst.at(i).qry.id));
			qryRltn.query = qLst.qLst.at(i).qry;
			runQuery(qryRltn.query, qryRltn, results, false);
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
		int count = 0;
		
		Lex lex(this->input); //Lab 1
		parse(lex.getTokens()); //Lab 2

		generateTables(this->dlp.schmLst, this->dlp.fctLst);
		runRules(this->dlp.rlLst, endResults, count);
		runQueries(this->dlp.qLst, endResults);
		print(endResults, count);
	}
	
	void print(string results, int& count)
	{
		string total;
		itoa(total, count);
		results.insert(0, "Schemes populated after " + total + 
		" passes through the Rules.\n");
		results+="Done!";
		cout<<results;
	}
	
	void stripComments(TokenHolder& tokHold)
	{
		for (std::vector<Token*>::iterator it = tokHold.tokenList.begin() ; it != tokHold.tokenList.end(); ++it)
		{
			Token temp = **it;
			if(TokenTypeToString(temp.getTokenType()) == "COMMENT")
			{
				tokHold.tokenList.erase(it);
				--it;
			}
		}
	}
	
	string parse(vector<Token*> results)
	{
		//Function Variables
		TokenHolder tok(results);
		stripComments(tok);
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




