/* CS 236 LAB 1 Token.h Kevin DeVocht*/

#include <fstream>
#include <algorithm>
#include <string>
#include <sstream>
#include <iostream>
#include <iomanip>
#include <ctype.h>
#include <stdio.h>
#include <math.h>


using namespace std;

/*The Token class is a generic class that will create any type of token
  It contains three values.  The token_Type, which determines what kind
  of token it is.  The value, which is a string containing the actual content
  of the token and then the line_number which stores which line that token is found on*/
class Token
{
	public:
		string token_Type;
		string value;
		int line_Number;
		
	Token(string token_Type0, string value0, int line_Number0) : token_Type(token_Type0), value(value0), line_Number(line_Number0)
	{}
	
	
	string toString()
	{
		string print = "";
		std::stringstream streamin(stringstream::out);
		streamin<< "(" <<token_Type<<",\""<<value << "\"," << line_Number << ")"<<endl;
		print = streamin.str();
		return print;
	}		
		
};
