/* CS 236 LAB 2 Parser.h Kevin DeVocht*/
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
//#include "BL.h"




using namespace std;



class TokenHolder
{
	public:
		vector<Token*> tokenList;
		int currentToken;
	
	TokenHolder(vector<Token*> tokenList0) : tokenList(tokenList0)
	{currentToken = 0;}
	
	Token current()
	{
		
		return *tokenList.at(currentToken);
	}
	
	int getlineNum()
	{
		return current().getLineNumber();
		
	}
	
	string getType()
	{
		return TokenTypeToString(current().getTokenType());
	}
	
	string getValue()
	{
		return current().getTokensValue();
	}
	
	string printLineNum()
	{
		string lineNum;
		
		stringstream streamin(stringstream::out);
		streamin<<current().getLineNumber();
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
			results += "  " + *it + "\n";
		}
		
		return results;
	}
		
};


//Is a <String> | <Identifier> | <Expression>	
class Parameter
{
	public:
		string param;
		Parameter* left;
		string op;
		Parameter* right;
		
	Parameter()
	{left = NULL; right = NULL;}
	
	string toString()
	{
		//Function variables
		string results;

		if(this->left == NULL)
		{
			results = this->param;
		}
		else
		{
			results = "("+this->left->toString()+this->op+this->right->toString()+")";
		}
		
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
		return this->qry.toString() + "?\n";
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
		string id;
		vector<string> constLst;
		

	
	string toString()
	{
		string results;
		results = this->id + "(";
		for(int i = 0; i<this->constLst.size(); i++)
		{
			results += this->constLst.at(i);
			if(i < this->constLst.size()-1)
			{
				results += ",";
			}
		}
		results.append(").\n");
		return results;
	}
	
	
};


//Is a <Predicate>
class Scheme
{
	public:
		string id;
		vector<string> idLst;
		
	Scheme()
	{}
	
	string toString()
	{
		string results;
		results = this->id + "(";
		for(int i = 0; i<this->idLst.size(); i++)
		{
			results += this->idLst.at(i);
			if(i < this->idLst.size()-1)
			{
				results += ",";
			}
		}
		results.append(")\n");
		return results;
		
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
			results += "  " + this->qLst.at(i).toString();
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
			results += "  " + this->rlLst.at(i).toString();
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
			results += "  " + this->fctLst.at(i).toString();
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
			results += "  " + this->schmLst.at(i).toString();
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
		//stripComments();
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
		if(tokHold.getType() == "ID" ||tokHold.getType() == "COMMENT")
		{
			schemeListParser();
		}
	}
	

	//Parses the Facts portion of the program
	void factListParser()
	{
		//Function Variables
		Fact fct;
		

		if(tokHold.getType() == "ID" ||tokHold.getType() == "COMMENT")
		{

			fct = factParser();
			this->fctLst.push_back(fct);
			if(tokHold.currentToken< tokHold.tokenList.size() && tokHold.getType() == "ID")
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
		
		if(tokHold.getType() == "ID"||tokHold.getType() == "COMMENT")
		{
			rl = ruleParser();
			
			this->rlLst.push_back(rl);
			if(tokHold.currentToken< tokHold.tokenList.size() && tokHold.getType() == "ID")
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
		if(tokHold.currentToken< tokHold.tokenList.size() && tokHold.getType() == "ID")
		{
			queryListParser();
		}
	}
	
	
	//A Predicate list can either be a predicate and a predicate list deliminated by a comma 
	//or just a predicate 
	vector<Predicate> predicateListParser(vector<Predicate> predList0)
	{
		//Function Variables
		Predicate pred;
		vector<Predicate> predList = predList0;

		
		pred = predicateParser();
		predList.push_back(pred);
		if(tokHold.getType() == "COMMA")
		{
			match("COMMA");
			predList = predicateListParser(predList);
		}
		
		return predList;
	}
	
	
	//A Parameter List can be either a parameter and a parameter list deliminated by a comma
	//or just a parameter
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
	
	vector<string> idLstParser(vector<string> idLst0)
	{
		vector<string> idLst = idLst0;
		
		idLst.push_back(tokHold.getValue());
		match("ID");
		if(tokHold.getType() == "COMMA")
		{
			match("COMMA");
			idLst = idLstParser(idLst);
		}
		else
		{
			match("RIGHT_PAREN");
		}
		
		return idLst;	
	}
	
	vector<string> constLstParser(vector<string> constLst0)
	{
		vector<string> constLst = constLst0;
		
		constLst.push_back(tokHold.getValue());
		this->dmn.insert(tokHold.getValue());
		match("STRING");
		if(tokHold.getType() == "COMMA")
		{
			match("COMMA");
			constLst = constLstParser(constLst);
		}
		else
		{
			match("RIGHT_PAREN");
		}
		
		return constLst;
	}
	
	//////////////////
	//Object Parsers//
	/////////////////
	
	
	//Returns a Scheme 
	Scheme schemeParser()
	{
		//Function Variables
		Scheme schm;
		vector<string> idLst;
		
		schm.id = tokHold.getValue();
		match("ID");
		match("LEFT_PAREN");
		schm.idLst = idLstParser(idLst);
		return schm;
	}
	
	
	//Returns a Fact
	Fact factParser()
	{
		//Function Variables
		Fact fct;
		vector<string> constLst;
		
		fct.id = tokHold.getValue();
		match("ID");
		match("LEFT_PAREN");
		fct.constLst = constLstParser(constLst);
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
	
	//A predicate must have an ID followed by a left paren, parameter list and finally a right paren
	Predicate predicateParser()
	{
		//Function Variables
		vector<Parameter> paramList;
		Predicate pred;
		
		pred.setId(tokHold.getValue());
		match("ID");
		match("LEFT_PAREN");
		paramList = parameterListParser(paramList);
		pred.setParamList(paramList);
		match("RIGHT_PAREN");
		
		return pred;
	}
	
	//Returns a Parameter which is either a String or an ID or an Expression
	Parameter parameterParser()
	{
		//Function Variables
		Parameter param;
		
		//Because Parameter is a special case where it can be either a String or an ID or 
		//Expresion.
		//I want to check to see which on I am going to match it with
		if(tokHold.getType() == "STRING")
		{
			param.param = tokHold.getValue();
			this->dmn.insert(tokHold.getValue());
			match("STRING");
		}
		else if(tokHold.getType() == "ID")
		{	
			param.param = tokHold.getValue();
			match("ID");
		}
		else if(tokHold.getType() == "LEFT_PAREN")
		{
			match("LEFT_PAREN");
			Parameter* left = new Parameter;
			*left =  parameterParser();
			param.left = left;
			param.op = operatorParser();
			Parameter* right = new Parameter;
			*right =  parameterParser();
			param.right = right;
			match("RIGHT_PAREN");
		}
		else
		{
			throw tokHold.currentToken;//It was not a string or an ID so I am throwing an exception
		}
		return param;
	}
	
	string operatorParser()
	{
		string op;
		if(tokHold.getType() == "MULTIPLY")
		{
			op = tokHold.getValue();
			match("MULTIPLY");
		}
		else if(tokHold.getType() == "ADD")
		{
			op = tokHold.getValue();
			match("ADD");
		}
		else
		{
			throw tokHold.currentToken;
		}
		
		return op;	
	}
	

	/////////
	//MATCH//
	/////////
	
	//Is the current Token what it should be?
	void match(string shouldBe)
	{
		string current = tokHold.getType();
		string curVal = tokHold.getValue();
		/*if(tokHold.getType() == "COMMENT")
		{
			tokHold.currentToken++;
			match(shouldBe);
		}*/
		//string current2 = tokHold.getType();
		//string curVal2 = tokHold.getValue();
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
