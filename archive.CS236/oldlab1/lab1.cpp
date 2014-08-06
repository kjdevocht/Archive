/* CS 236 LAB 1 lab1 Kevin DeVocht*/
#include <fstream>
#include <algorithm>
#include <string>
#include <sstream>
#include <iostream>
#include <iomanip>
#include <ctype.h>
#include <stdio.h>
#include <math.h>
#include "BL.h"


using namespace std;


void print(vector<Token> results, char* output)
{
	ofstream out;
	out.open(output);
	
	if(results.size() !=0)
	{
		if(results.at(0).value == "ERROR")
		{
			out<<results.at(0).token_Type<<results.at(0).line_Number<<endl;
		}
		else
		{
		for(size_t i = 0; i<results.size(); i++)
			{
				out<<results.at(i).toString();
				
			}
			out<<"Total Tokens = "<<results.size()<<endl;
		}
		
	}
	else
	{
		out<<"Total Tokens = "<<results.size()<<endl;
	}
	out.close();
}

int main(int argc, char *argv[])
{
	vector<Token> results;
	extern bool getNext;
	extern int whatToken;

	FILE * inFile = fopen(argv[1], "r"); 
	whatToken = getc(inFile);

	getNext = true;
	
	while(whatToken != -1)
	{
	
		results = tokenize(results, inFile);
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
	print(results, argv[2]);
}
