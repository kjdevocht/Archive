/* CS 236 LAB 4 Tokenizer.cpp Kevin DeVocht*/
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
bool getNext = true;
int whatToken;

//Creates all Token types
void tokenMaker(vector<Token>& results, string type, string value)
{
	Token myToken = Token(type, value, lineNum);
	results.push_back(myToken);
}

//Creates a COLON token or a COLON_DASH token
void colon(vector<Token>& results, FILE * infile)
{
	whatToken = getc(infile);
 
	if(whatToken== '-')
	{
		tokenMaker(results,"COLON_DASH", ":-"); 
	}
	else
	{
		tokenMaker(results,"COLON", ":"); 
		getNext = false;
	}
}

//Creates an error
void error(vector<Token>& results)
{
	Token error = Token("Error on Line ", "ERROR", lineNum);
	results.insert(results.begin(), error);
}

//Creates an ID token sets getNext to false
void id(vector<Token>& results, FILE * infile, string id)
{
	while(isalnum(whatToken) !=0)
	{
		whatToken = getc(infile);
		if(isalnum(whatToken) !=0)
		{
			id+= (char) whatToken;
		}
		
		
	}
	
	tokenMaker(results, "ID", id);
	getNext = false;
}


//Creates SCHEMES, FACTS, RULES or QUERIES tokens or calls id(vector<Token>, FILE *, string)
void keyword(vector<Token>& results, FILE * infile, string keyword)
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
		tokenMaker(results, keyword, test);
		getNext = false;		
	}
	else //These takes care of everything else
	{
		id(results, infile, test);		
	}
}
//Creates a STRING token or an error
void makeString(vector<Token>& results, FILE * infile)
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
		tokenMaker(results, "STRING", myString);
	}
	else
	{
		error(results);
	}
}
void pound(FILE * infile)
{
	while(whatToken != '\n')
	{
		whatToken = getc(infile);
	}
	lineNum++;
}

/*This function is the base function for testing chars
  When in this token the program is in between creating tokens*/
void tokenize(vector<Token>& results, FILE * infile)
{
		getNext = true;
		switch(whatToken)
		{
			case ',': // Can only be the COMMA token
				tokenMaker(results, "COMMA", ",");
				break;
			case '.': // Can only be the PERIOD token
				tokenMaker(results, "PERIOD", ".");
				break;
			case '?': // Can only be the PERIOD token
				tokenMaker(results, "Q_MARK", "?");
				break;
			case '(': // Can only be the LEFT_PAREN token
				tokenMaker(results, "LEFT_PAREN", "(");
				break;
			case ')': //Can only be the RIGHT_PAREN token
				tokenMaker(results, "RIGHT_PAREN", ")");
				break;
			case ':': //Can be the COLON token or the COLON_DASH token
				colon(results, infile);
				break;
			case 'S': //Can be the Schemes token or an ID token or an Error
				keyword(results, infile, "Schemes");
				break;
			case 'F': //Can be the Facts token or an ID token or an Error
				keyword(results, infile, "Facts");
				break;
			case 'R': //Can be the Rules token or an ID token or an Error
				keyword(results, infile, "Rules");
				break;
			case 'Q': //Can be the Queries token or an ID token or an Error
				keyword(results, infile, "Queries");
				break;
			case '\'': //Can be a String token or an Error
				makeString(results, infile);
				break;
			case '#': //Can only be a comment token
					pound(infile);
				break;
			default: //Can be an ID token or a Whitespace token or an Error
				if(isspace(whatToken) != 0)
				{
					if(whatToken == '\n')
					{
						lineNum++;
					}
					getNext = true;
					break;
				}
				if(isalpha(whatToken) != 0)
				{	
					string test;
					test = (char) whatToken;
					id(results, infile, test);
					break;
				}
				else
				{
					error(results);
					getNext = true;
					break;
				}
			
		}
}

string throwError(vector<Token> results)
{
	//Function Variables
	string linNum;
	string errorResults;
	
	stringstream streamin(stringstream::out);
	streamin<<results.at(0).line_Number;
	linNum = streamin.str();
	errorResults = results.at(0).token_Type + linNum;
	return errorResults;	
}

vector<Token> makeTokens(char* input)
{
	//Function Variables
	extern bool getNext;
	extern int whatToken;
	vector<Token> results;
	FILE * inFile = fopen(input, "r"); 
	
	//Intial set of Variables
	whatToken = getc(inFile);

	//Tokenize Section
	while(whatToken != -1)
	{
		tokenize(results, inFile);
		if( results.size() !=0)
		{
			if(results.at(0).value == "ERROR")
			{
					break;
			}
		}
		if(getNext)
		{
			whatToken= getc(inFile);
		}
	}
	fclose(inFile);	
	return results;
}
