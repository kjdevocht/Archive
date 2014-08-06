/* CS 236 LAB 4 Parser.h Kevin DeVocht*/
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
#include "Tokenizer.h"




using namespace std;



class TokenHolder
{
	public:
		vector<Token> tokenList;
		int currentToken;
	
	TokenHolder(vector<Token> tokenList0) : tokenList(tokenList0)
	{currentToken = 0;}
	
	Token current()
	{
		
		return tokenList.at(currentToken);
	}
	
	int getlineNum()
	{
		return current().line_Number;
		
	}
	
	string getType()
	{
		return current().token_Type;
	}
	
	string getValue()
	{
		return current().value;
	}
	
	string printLineNum()
	{
		string lineNum;
		
		stringstream streamin(stringstream::out);
		streamin<<current().line_Number;
		lineNum = streamin.str();	
		
		return lineNum;
	}
	
};



//////////////////
//Object Classes//
//////////////////



//Is a set of Strings
class Domain
{
		public:
			set<string> dmnLst;
		
		void insert(string str)
		{
				this->dmnLst.insert(str);
		}
		
	string toString()
	{
		//Function Variables
		string results;
		string size;
		set<string> ::const_iterator it = this->dmnLst.begin();
		
		stringstream streamin(stringstream::out);
		streamin<<dmnLst.size();
		size = streamin.str();
		results = "Domain(" + size + "):\n";
		
		for(it; it != this->dmnLst.end(); it++)
		{
			results += "\t" + *it + "\n";
		}
		
		return results;
	}
		
};


//Is a String or an Identifier	
class Parameter
{
	public:
		string param;
		
	Parameter()
	{}
	
	string toString()
	{
		//Function variables
		string results;
		
		results = this->param;
		return results;
	}
	
};


//Is an Identifier ( <ParameterList> )
class Predicate
{
	public:
		string id;
		string leftParen;
		vector<Parameter> paramLst;
		string rightParen;
		
	Predicate()
	{
		leftParen = "(";
		rightParen = ")";
	
	}
	
	void setId(string id)
	{
		this->id = id;
	}
	
	void setParamList(vector<Parameter> paramLst)
	{
		this->paramLst = paramLst;
	}

	string toString()
	{
		//Function Variables
		string results;
		results = this->id + this->leftParen;
		for(size_t i = 0; i<this->paramLst.size(); i++)
		{
			results += this->paramLst.at(i).toString();
			if(i < this->paramLst.size()-1)
			{
				results += ",";
			}
		}
		results += this->rightParen;
		
		return results;
	}
	
};


//Is a <Predicate> Q_MARK
class Query
{
	
	public:
		Predicate qry;
		
	void setQuery(Predicate qry)
	{
		this->qry = qry;
	}
	
	string toString()
	{
		return this->qry.toString() + "?";
	}
	
};


//Is a <Predicate> COLON_DASH <Predicate List>
class Rule
{
	public:
		vector<Predicate> predLst;
		Predicate rl;
	
	
	void setRule(Predicate rl)
	{
		this->rl = rl;
	}
	
	void setPredicateList(vector<Predicate> predLst)
	{
		this->predLst = predLst;
	}
	
	string toString()
	{
		string results;
		results = this->rl.toString() + " :- ";
		for(size_t i = 0; i<predLst.size(); i++)
		{
			results +=this->predLst.at(i).toString();	
			if(i<predLst.size()-1)
			{
				results += ",";
			}
		}
		return results + ".\n";
	}
	
};


//Is a <Predicate> followed by a PERIOD
class Fact
{
	public:
		Predicate fact;
		
	void setFact(Predicate fact)
	{
		this->fact = fact;
	}
	
	string toString()
	{
		return this->fact.toString() + ".\n";
	}
	
	
};


//Is a <Predicate>
class Scheme
{
	public:
		Predicate scheme;
		
	Scheme()
	{}
	
	void setScheme(Predicate scheme)
	{
		this->scheme = scheme;
	}
	
	
	string toString()
	{
		
		return this->scheme.toString() + "\n";
		
	}
	
};



////////////////
//List Classes//
////////////////


//Is a <Query>  and a <Query List> or a <Query>
class QueryList
{

	public:
		vector<Query> qLst;
		
	void push_back(Query qry)
	{
		this->qLst.push_back(qry);
	}
	
	string toString()
	{
		//Function Variables
		string results;
		string size;
		
		stringstream streamin(stringstream::out);
		streamin<<qLst.size();
		size = streamin.str();
		results = "Queries(" + size + "):\n";
		
		for(size_t i = 0; i<this->qLst.size(); i++)
		{
			results += "\t" + this->qLst.at(i).toString();
		}
		
		return results;
	}
	
};


//Is a <Rule> and a <RuleList> or empty
class RuleList
{
	
	public:
		vector<Rule> rlLst;
		
	void push_back(Rule rl)
	{
		this->rlLst.push_back(rl);
	}
	
	string toString()
	{
		//Function Variables
		string results;
		string size;
		
		stringstream streamin(stringstream::out);
		streamin<<rlLst.size();
		size = streamin.str();
		results = "Rules(" + size + "):\n";
		
		for(size_t i = 0; i<this->rlLst.size(); i++)
		{
			results += "\t" + this->rlLst.at(i).toString();
		}
		
		return results;
	}
	
};


//Is a <Fact> and a <FactList> or empty
class FactList
{
	public:
		vector<Fact> fctLst;
		
	
		
		
	void push_back(Fact fct)
	{
		this->fctLst.push_back(fct);
	}
	
	string toString()
	{
		//Function Variables
		string results;
		string size;
		
		stringstream streamin(stringstream::out);
		streamin<<fctLst.size();
		size = streamin.str();
		results = "Facts(" + size + "):\n";
		
		for(size_t i = 0; i<this->fctLst.size(); i++)
		{
			results += "\t" + this->fctLst.at(i).toString();
		}
		
		return results;
	}
	
};


//Is a <Scheme> and a <SchemeList> or just a <Scheme>
class SchemeList
{
	public:
		vector<Scheme> schmLst;
		
		
	void push_back(Scheme schm)
	{
		this->schmLst.push_back(schm);
	}
	
	
	string toString()
	{
		//Function Variables
		string results;
		string size;
		
		stringstream streamin(stringstream::out);
		streamin<<schmLst.size();
		size = streamin.str();
		results = "Schemes(" + size + "):\n";
		
		for(size_t i = 0; i<this->schmLst.size(); i++)
		{
			results += "\t" + this->schmLst.at(i).toString();
		}
		
		return results;
	}
};

/////////////////
//Datalog Class//
/////////////////

//Contains Schemes, Facts, Rules, Quereies and Domains
class DatalogProgram
{
	public:
		SchemeList schmLst;
		FactList fctLst;
		RuleList rlLst;
		QueryList qLst;
		Domain dmn;
	
	DatalogProgram()
	{}
	
	string toString()
	{
		string results = "Success!\n";
		results += this->schmLst.toString();
		results += this->fctLst.toString();
		results += this->rlLst.toString();
		results += this->qLst.toString();
		results += this->dmn.toString();
		return results;
	}			
};

/////////////////////////////////
//Parser Section of the Program//
/////////////////////////////////


//This is the meat and potatos of the program and does all the heavy lefting
class Parser
{
	
	public:
			TokenHolder tokHold;
			DatalogProgram dlp;
			SchemeList schmLst;
			FactList fctLst;
			RuleList rlLst;
			QueryList qLst;
			Domain dmn;
	
		Parser(TokenHolder tokHold0) : tokHold(tokHold0)
		{}
		
		
	//Take results of all Parses and set them to dlp	
	void setDLP()
	{
		dlp.schmLst = this->schmLst;
		dlp.fctLst = this->fctLst;
		dlp.rlLst = this->rlLst;
		dlp.qLst = this->qLst;
		dlp.dmn = this->dmn;
	}
	
	//Parses all parts of the Program
	DatalogProgram dataLogProgramParser()
	{
		ssp();
		fsp();
		rsp();
		qsp();
		match("EOF");
		setDLP();
		return dlp;
	}
	
	
	///////////////////
	//Portion Parsers//
	///////////////////
	
	//Parses the Schemes portion of the Program
	void ssp()
	{
		match("SCHEMES");//The first Token must be a Schemes token
		match("COLON");//The second Token must be a COLON token
		schemeListParser();	
	}
	
	//Parses the Facts portion of the Program
	void fsp()
	{
		match("FACTS");//There must be a Facts token next
		match("COLON");//There must be a COLON token again
		factListParser();	
	}
	
	//Parses the Rules portion of the Program
	void rsp()
	{
		match("RULES");
		match("COLON");
		ruleListParser();	
	}
	
	//Parses the Queries portion of the Program
	void qsp()
	{
		match("QUERIES");
		match("COLON");
		queryListParser();	
	}
	
	
	
	////////////////
	//List Parsers//
	////////////////
	
	
	//Parsers the Schemes portion of the program
	void schemeListParser()
	{
		//Function Variables
		Scheme schm;
		

		
		schm = schemeParser();
		this->schmLst.push_back(schm);
		if(tokHold.getType() == "ID")
		{
			schemeListParser();
		}
	}
	

	//Parses the Facts portion of the program
	void factListParser()
	{
		//Function Variables
		Fact fct;
		

		if(tokHold.getType() == "ID")
		{

			fct = factParser();
			this->fctLst.push_back(fct);
			if(tokHold.currentToken< tokHold.tokenList.size() && 
				tokHold.getType() == "ID")
			{
				factListParser();
			}
		}	
	}
	
	
	//Parses the Rules portion of the Program
	void ruleListParser()
	{
		//Function Variables
		Rule rl;
		
		if(tokHold.getType() == "ID")
		{
			rl = ruleParser();
			
			this->rlLst.push_back(rl);
			if(tokHold.currentToken< tokHold.tokenList.size() && 
				tokHold.getType() == "ID")
			{
				ruleListParser();
			}
			
		}
	}
	
	
	//Parsers the Query portion of the program
	void queryListParser()
	{
		//Function Variables
		Query qry;
		
		qry = queryParser();
		this->qLst.push_back(qry);
		if(tokHold.currentToken< tokHold.tokenList.size() && 
			tokHold.getType() == "ID")
		{
			queryListParser();
		}
	}
	
	
	//A Predicate list can either be a predicate and a predicate list 
	//deliminated by a comma or just a predicate 
	vector<Predicate> predicateListParser(vector<Predicate> predLst0)
	{
		//Function Variables
		Predicate pred;
		vector<Predicate> predLst = predLst0;

		
		pred = predicateParser();
		predLst.push_back(pred);
		if(tokHold.getType() == "COMMA")
		{
			match("COMMA");
			predLst = predicateListParser(predLst);
		}
		
		return predLst;
	}
	
	
	//A Parameter List can be either a parameter and a parameter list 
	//deliminated by a comma or just a parameter
	vector<Parameter> parameterListParser(vector<Parameter> paramLst0)
	{
		//Function Variables
		Parameter param;
		vector<Parameter> paramLst = paramLst0;
		param = parameterParser();
		paramLst.push_back(param);
		if(tokHold.getType() == "COMMA")
		{
			match("COMMA");
			paramLst = parameterListParser(paramLst);
		}
		return paramLst;
		
	}
	

	//////////////////
	//Object Parsers//
	/////////////////
	
	
	//Returns a Scheme 
	Scheme schemeParser()
	{
		//Function Variables
		Scheme schm;
		
		schm.setScheme(predicateParser());
		
		return schm;
	}
	
	
	//Returns a Fact
	Fact factParser()
	{
		//Function Variables
		Fact fct;
		fct.setFact(predicateParser());
		match("PERIOD");
		
		return fct;
	}
	
	//Returns a Rule
	Rule ruleParser()
	{
		//Function Variables
		Rule rl;
		vector<Predicate> predLst;
		
		rl.setRule(predicateParser());
		match("COLON_DASH");
		predLst = predicateListParser(predLst);
		rl.setPredicateList(predLst);
		match("PERIOD");
		return rl;
	}
	
	
	//Returns a Query
	Query queryParser()
	{
		//Function Variables
		Query qry;
		
		qry.setQuery(predicateParser());
		match("Q_MARK");
		
		return qry;
	}
	
	//A predicate must have an ID followed by a left paren, parameter list 
	//and finally a right paren
	Predicate predicateParser()
	{
		//Function Variables
		vector<Parameter> paramList;
		Predicate pred;
		vector<Parameter> paramLst;
		
		pred.setId(tokHold.getValue());
		match("ID");
		match("LEFT_PAREN");
		paramList = parameterListParser(paramLst);
		pred.setParamList(paramList);
		match("RIGHT_PAREN");
		
		return pred;
	}
	
	//Returns a Parameter which is either a String or an ID
	Parameter parameterParser()
	{
		//Function Variables
		string parameter;
		Parameter param;
		
		parameter= tokHold.getValue();
		//Because Parameter is a special case where it can be either a 
		//String or an ID.  I want to check to see which on I am going 
		//to match it with
		if(tokHold.getType() == "STRING")
		{
			parameter = "'" + parameter +"'";
			param.param = parameter;
			this->dmn.insert(parameter);
			match("STRING");
		}
		else if(tokHold.getType() == "ID")
		{	
			param.param = parameter;
			match("ID");
		}
		else
		{
			//It was not a string or an ID so I am throwing an exception
			throw tokHold.currentToken;
		}
		return param;
	}
	
	

	/////////
	//MATCH//
	/////////
	
	//Is the current Token what it should be?
	void match(string shouldBe)
	{

		if(tokHold.getType().compare(shouldBe) == 0)
		{
			tokHold.currentToken++;
		}
		else
		{
			throw tokHold.currentToken;
		}
	}
	
	
};
