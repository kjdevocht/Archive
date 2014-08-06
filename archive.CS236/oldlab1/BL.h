/* CS 236 LAB 1 BL.h Kevin DeVocht*/

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

void comma(int lineNum);

vector<Token> period(vector<Token> results);

vector<Token> q_Mark(vector<Token> results);

vector<Token> left_Paren(vector<Token> results);

vector<Token> right_Paren(vector<Token> results);

vector<Token> colon(vector<Token> results, FILE * infile);

vector<Token> tokenize(vector<Token> results, FILE * inFile);
