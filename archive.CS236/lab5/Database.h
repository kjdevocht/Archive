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
#include "Graph.h"




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
		rh++;
		if(debug) cout<<spaces()<<"unionDLP"<<endl;
		tupleSet.insert(jnRltn.tupleSet.begin(), jnRltn.tupleSet.end());
		rh--;
	}
	
	void joinNameSchema(Relation& qryRltn)
	{
		rh++;
		if(debug) cout<<spaces()<<"joinNameSchema"<<endl;

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

	rh--;
	}
	
	bool canJoin(Schema& qrySchema, Schema& thisSchema, vector<string>& qryTuple, vector<string>& thisTuple)
	{
		rh++;
		if(debug) cout<<spaces()<<"canJoin"<<endl;
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
	
	Tuple joinTuple(Schema& qrySchema, Schema& thisSchema, vector<string>& qryTuple, vector<string>& thisTuple)
	{
		rh++;
		if(debug) cout<<spaces()<<"joinTuple"<<endl;
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
			
		rh--;
	}
	
	void join(Relation& qryRltn)
	{
		rh++;
		if(debug) cout<<spaces()<<"join"<<endl;
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
		rh--;
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
		rh++;
		if(debug) cout<<spaces()<<"select"<<endl;
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
		rh--;
	}
	
	void selectMulti(int indexOne, int indexTwo)
	{
		rh++;
		if(debug) cout<<spaces()<<"selectMulti"<<endl;
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
		rh--;
	}
	
	void project(vector<int>& projectLst)
	{
		rh++;
		if(debug) cout<<spaces()<<"project"<<endl;
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
		rh--;
	}
	
	void rename(const Predicate& qry, vector<int>& projectLst)
	{
		rh++;
		if(debug) cout<<spaces()<<"rename"<<endl;
		Schema newSchema;
		string newName = "";
		for(int i = 0; i<projectLst.size(); i++)
		{
			newSchema.push_back(qry.paramLst.at(projectLst.at(i)).param);
		}
		
		this->schema = newSchema;
		rh--;
	}
	
	string getSize()
	{
		rh++;
		if(debug) cout<<spaces()<<"getSize"<<endl;
		string size;
		set<Tuple> ::const_iterator it = this->tupleSet.begin();
		
		stringstream streamin(stringstream::out);
		streamin<<tupleSet.size();
		size = streamin.str();
		return size;
		rh--;
	}
};

class Database
{
	public:
		set<Relation> relations;
		char* input;
		DatalogProgram dlp;
		vector<Relation> tables;
		Graph graph;
		
	
	Database(char* input0) : input(input0)
		{readInput();}
		
	
		
	int findSchemes(string id)
	{
		rh++;
		if(debug) cout<<spaces()<<"findSchemes"<<endl;
		for(int i = 0; i<tables.size(); i++)
		{
			if(tables.at(i).name == id)
			{
				rh--;
				return i;
			}
		}
	}
	
	void checkForDuplicates(const vector<Parameter>& schema, Relation& qryRltn)
	{
		rh++;
		if(debug) cout<<spaces()<<"checkForDuplicates"<<endl;
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
		rh--;
	}
	
	void dupSchema(vector<int> &projectLst, set<string> &dupCol, const string &param, int &j)
	{
		rh++;
		if(debug) cout<<spaces()<<"dupSchema"<<endl;
		if(dupCol.find(param) == dupCol.end())
		{
			dupCol.insert(param);
			projectLst.push_back(j);
		}
		rh--;
	}
	
	int getTupleTotal()
	{
		rh++;
		if(debug) cout<<spaces()<<"getTupleTotal"<<endl;
		int total = 0;
		for(int i = 0; i<tables.size(); i++)
		{
			total += tables.at(i).tupleSet.size();
		}
		rh--;
		return total;
	}
	
	Schema ruleSchema(vector<Parameter>& paramLst, int& i)
	{
		rh++;
		if(debug) cout<<spaces()<<"ruleSchema"<<endl;
		Schema schema;
		for(int q = 0; q<paramLst.size(); q++)
		{
			schema.push_back(paramLst.at(q).param);			
		}
		rh--;
		return schema;	
	}
	void runRule(Relation& jnRltn, int j, vector<Predicate>& predLst, string& endResults)
	{
		rh++;
		if(debug) cout<<spaces()<<"runRule"<<endl;
		Relation qryRltn = tables.at(findSchemes(predLst.at(j).id));
		qryRltn.query = predLst.at(j);
		runQuery(qryRltn.query, qryRltn, endResults, true);
		if(jnRltn.schema.size() == 0)
		{
			jnRltn = qryRltn;
		}
		else
		{
			jnRltn.join(qryRltn);
		}
		rh--;		
	}
	
	void runRules(RuleList& rlLst, string& endResults, int& count)
	{
		rh++;
		if(debug) cout<<spaces()<<"runRules"<<endl;

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
					runRule(jnRltn, j, rlLst.rlLst.at(i).predLst, endResults); 
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
		rh--;
	}
	
	vector<int> createProjectList(Schema schema, Relation& jnRltn)
	{
		rh++;
		if(debug) cout<<spaces()<<"createProjectList"<<endl;
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
		rh--;
		return projectLst;		
	}
	

	
	void generateRuleList(vector<Rule>& optimizedRuleList)
	{
		rh++;
		if(debug) cout<<spaces()<<"generateRuleList"<<endl;
		for(int i = 0; i<this->graph.ruleOrder.size(); i++)
		{
			int pos = this->graph.ruleOrder.at(i);
			string val;
			itoa(val, pos+1);
			optimizedRuleList.push_back(this->dlp.rlLst.rlLst.at(pos));
		}
		
		rh--;
	}
	
	void optimize(Relation& qryRltn, string& endResults, int& count)
	{
		rh++;
		if(debug) cout<<spaces()<<"optimize"<<endl;
		int pon = 0;
		pon = this->graph.depthFirst(qryRltn.query.id);

		if(pon != 0)
		{
			vector<Rule> optimizedRuleList;
			this->graph.topo();
			generateRuleList(optimizedRuleList);
			RuleList rlLst;
			rlLst.rlLst = optimizedRuleList;
			this->graph.cycleFind();
			
			if(this->graph.cycles.size() == 0)
			{

				
				for(int i = 0; i<rlLst.rlLst.size(); i++)
				{
					Relation jnRltn;
					for(int j = 0; j<rlLst.rlLst.at(i).predLst.size(); j++)
					{
						runRule(jnRltn, j, rlLst.rlLst.at(i).predLst, endResults); 
					}
					vector<int> projectLst = createProjectList(ruleSchema
					(rlLst.rlLst.at(i).rl.paramLst, i), jnRltn);
					jnRltn.project(projectLst);
					tables.at(findSchemes(rlLst.rlLst.at(i).rl.id)).
					unionDLP(jnRltn);	
				}
			}
			else
			{
				runRules(rlLst, endResults, count); 
			}
		}
		this->graph.clearGraph();
		rh--;
	}
	
	void runQueries(QueryList& qLst, string& endResults, int& count)
	{
		rh++;
		if(debug) cout<<spaces()<<"runQueries"<<endl;
		for(int i = 0; i<qLst.qLst.size(); i++)
		{
			Relation qryRltn = tables.at(findSchemes(qLst.qLst.at(i).qry.id));
			qryRltn.query = qLst.qLst.at(i).qry;
			optimize(qryRltn, endResults, count);
			qryRltn = tables.at(findSchemes(qLst.qLst.at(i).qry.id));
			qryRltn.query = qLst.qLst.at(i).qry;
			runQuery(qryRltn.query, qryRltn, endResults, false);
		}
		rh--;
	}
	
	void runQuery(Predicate& query, Relation& qryRltn, string& endResults, bool isRule)
	{

		rh++;
		if(debug) cout<<spaces()<<"runQuery"<<endl;
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
			endResults += qryRltn.toString();
		}
		rh--;
	}
		
	void generateTables(const SchemeList& schmLst, const FactList& fctLst)
	{
		rh++;
		if(debug) cout<<spaces()<<"generateTables"<<endl;
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
		rh--;
	}
	
	Relation& getRelation(string id)
	{
		rh++;
		if(debug) cout<<spaces()<<"getRelation"<<endl;
		for(int i = 0; i<tables.size(); i++)
		{
			if(id == tables.at(i).name)
			{
					rh--;
					return tables.at(i);
			}
		}
		Schema schema;
		Relation relation(id, schema);
		rh--;
	}
	void readInput()
	{
		rh++;
		if(debug) cout<<spaces()<<"readInput"<<endl;
		//Function Variables
		string endResults = "";
		int count = 0;
		map<string,node> depend;
		
		Lex lex(this->input); //Lab 1
		parse(lex.getTokens()); //Lab 2

		generateTables(this->dlp.schmLst, this->dlp.fctLst);
		this->graph.mapNodes(this->dlp.rlLst.rlLst, this->dlp.qLst.qLst);
		runQueries(this->dlp.qLst, endResults, count);
		print(endResults, count);
		rh--;
	}
	
	void print(string results, int& count)
	{
		rh++;
		if(debug) cout<<spaces()<<"print"<<endl;
		cout<<results;
		rh--;
	}
	
	void stripComments(TokenHolder& tokHold)
	{
		rh++;
		if(debug) cout<<spaces()<<"stripComments"<<endl;
		for (std::vector<Token*>::iterator it = tokHold.tokenList.begin() ; it != tokHold.tokenList.end(); ++it)
		{
			Token temp = **it;
			if(TokenTypeToString(temp.getTokenType()) == "COMMENT")
			{
				tokHold.tokenList.erase(it);
				--it;
			}
		}
		rh--;
	}
	
	string parse(vector<Token*> results)
	{
		rh++;
		if(debug) cout<<spaces()<<"parse"<<endl;
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
		rh--;
		return parseResults;
	}	
};




