/* CS 236 LAB 1 BL.cpp Kevin DeVocht*/
#include <fstream>
#include <algorithm>
#include <string>
#include <sstream>
#include <iostream>
#include <iomanip>
#include <ctype.h>
#include <stdio.h>
#include <math.h>
#include <vector>
#include "Token.h"


using namespace std;





int lineNum = 1;
bool getNext;
int whatToken;

//Creates all Token types
vector<Token> tokenMaker(vector<Token> results, string type, string value)
{
	Token myToken = Token(type, value, lineNum);
	results.push_back(myToken);
	return results;	
}

//Creates a COLON token or a COLON_DASH token
vector<Token> colon(vector<Token> results, FILE * infile)
{
	whatToken = getc(infile);
 
	if(whatToken== '-')
	{
		results = tokenMaker(results,"COLON_DASH", ":-"); 
	}
	else
	{
		results = tokenMaker(results,"COLON", ":"); 
		getNext = false;
	}
	return results;
}

//Creates an error
vector<Token> error(vector<Token> results)
{
	Token error = Token("Error on Line ", "ERROR", lineNum);
	results.insert(results.begin(), error);
	return results;
}

//Creates an ID token sets getNext to false
vector<Token> id(vector<Token> results, FILE * infile, string id)
{
	while(isalnum(whatToken) !=0)
	{
		whatToken = getc(infile);
		if(isalnum(whatToken) !=0)
		{
			id+= (char) whatToken;
		}
		
		
	}
	
	results = tokenMaker(results, "ID", id);
	getNext = false;
	return results;
}


//Creates SCHEMES, FACTS, RULES or QUERIES tokens or calls id(vector<Token>, FILE *, string)
vector<Token> keyword(vector<Token> results, FILE * infile, string keyword)
{
	string test;
	test = (char) whatToken;
	//whatToken = getc(infile);

	//test += (char) whatToken;

	while(test.compare(0, test.length(), keyword, 0, test.length()) == 0 && isalpha(whatToken = getc(infile)) != 0)
	{
		test += (char) whatToken;
	}
	//This takes care of the SCHEME token
	if (test.compare(keyword) == 0)
	{
		for(size_t i = 0; i<keyword.length(); i++)
		{
			keyword[i]= toupper(keyword[i]);
		}
		results = tokenMaker(results, keyword, test);
		getNext = false;		
	}
	else //These takes care of everything else
	{
		results = id(results, infile, test);		
	}
	return results;	
	
}
//Creates a STRING token or an error
vector<Token> makeString(vector<Token> results, FILE * infile)
{
		string myString;
		whatToken = getc(infile);
		while(whatToken != '\'' && whatToken != -1 && whatToken != '\n')
		{
			myString += whatToken;
			whatToken = getc(infile);
		}
		
		if(whatToken == '\'')
		{
			results = tokenMaker(results, "STRING", myString);
		}
		else
		{
			results = error(results);
		}
		
		return results;
				
			
}
void pound(FILE * infile)
{
	while(whatToken != '\n')
	{
		whatToken = getc(infile);
	}
	lineNum++;
}

void idOrError(vector<Token>& results, FILE * infile, bool& getNext)
{
	if(isalpha(whatToken) != 0)
	{	
		string test;
		test = (char) whatToken;
		results = id(results, infile, test);
	}
	else
	{
		results = error(results);
		getNext = true;
	}
}

void idOrKeyword(vector<Token>& results, FILE * infile, bool& getNext)
{
	switch(whatToken)
		{
			case 'S': //Can be the Schemes token or an ID token or an Error
				results = keyword(results, infile, "Schemes"); break;
			case 'F': //Can be the Facts token or an ID token or an Error
				results = keyword(results, infile, "Facts"); break;
			case 'R': //Can be the Rules token or an ID token or an Error
				results = keyword(results, infile, "Rules"); break;
			case 'Q': //Can be the Queries token or an ID token or an Error
				results = keyword(results, infile, "Queries"); break;
			default:
				idOrError(results, infile, getNext);
		}
}

void spaceOrError(vector<Token>& results, bool& getNext)
{
	if(isspace(whatToken) != 0)
	{
		if(whatToken == '\n')
		{
			lineNum++;
		}
		getNext = true;
	}
	else
	{
		results = error(results);
		getNext = true;
	}
}


void everythingElse(vector<Token>& results, FILE * infile, bool& getNext)
{
	
	switch(whatToken)
		{
			case ',': // Can only be the COMMA token
				results = tokenMaker(results, "COMMA", ","); break;
			case '.': // Can only be the PERIOD token
				results = tokenMaker(results, "PERIOD", "."); break;
			case '?': // Can only be the PERIOD token
				results = tokenMaker(results, "Q_MARK", "?"); break;
			case '(': // Can only be the LEFT_PAREN token
				results = tokenMaker(results, "LEFT_PAREN", "("); break;
			case ')': //Can only be the RIGHT_PAREN token
				results = tokenMaker(results, "RIGHT_PAREN", ")"); break;
			case ':': //Can be the COLON token or the COLON_DASH token
				results = colon(results, infile); break;
			case '\'': //Can be a String token or an Error
				results = makeString(results, infile); break;
			case '#': //Can only be a comment token
				pound(infile); break;
			default: //Can be an ID token or a Whitespace token or an Error
				spaceOrError(results, getNext);
		}
}


/*This function is the base function for testing chars
  When in this token the program is in between creating tokens*/
vector<Token> tokenize(vector<Token> results, FILE * infile)
{
		getNext = true;
		
		
		if(isalpha(whatToken) != 0)
		{
			idOrKeyword(results, infile, getNext);
		
		}
		else
		{
			everythingElse(results, infile, getNext);
		}
		
		
		return results;
}
