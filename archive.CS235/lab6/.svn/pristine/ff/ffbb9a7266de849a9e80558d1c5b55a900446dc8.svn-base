/*command.cpp -lab6 - Kevin DeVocht*/
#include <fstream>
#include <algorithm>
#include <string>
#include <sstream>
#include <iostream>
#include <iomanip>
#include <ctype.h>
#include <stdio.h>
#include <math.h>
#include "tree_set.h"
#include "search.h"



//Contract: process_command: char *->nothing
//Purpose: to process input files as commands
void process_command(char *input, char *output)
//Template: theFile
{
	string s;
	ifstream in;
	ofstream out;
	in.open(input);
	out.open(output);
	AVLTreeSet<string>* the_set = NULL;


	while(in >> s)
	{
	
		if(s == "clear")
		{
			if (the_set != NULL)
			{
				the_set->clear();
			}
			out<<"clear"<<endl;

		}
		else if (s == "add")
		{
			in>>s;
			if(the_set == NULL)
			{
				the_set = new AVLTreeSet<string>(NULL);
			}
			the_set->add(s);
			out<<"add "<<s<<endl;

		}
		else if (s == "remove")
		{
			in>>s;
			if (the_set != NULL)
			{
				the_set->remove(s);
			}

			out<<"remove "<<s<<endl;
	
		}
		else if (s == "find")
		{
			in>>s;
			if(the_set !=NULL)
			{
				if(the_set->find(s))
				{
					out<<"find "<<s<<" "<<"true"<<endl;
				}
				else
				{
					out<<"find "<<s<<" "<<"false"<<endl;
				}
			}
			else
			{
				out<<"find "<<s<<" "<<"false"<<endl;
			}
		}
		else if (s =="print")
		{
			out<<"print"<<endl;
			if(the_set !=NULL)
			{
				the_set->print(out);
			}
		}	
	}
	if (the_set != NULL)
	{
		delete the_set;
	}
	in.close();
	out.close();
}
