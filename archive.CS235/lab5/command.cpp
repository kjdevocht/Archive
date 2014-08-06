/*command.cpp -lab5 - Kevin DeVocht*/


#include <fstream>
#include <algorithm>
#include <string>
#include <sstream>
#include <iostream>
#include <iomanip>
#include <ctype.h>
#include <stdio.h>
#include <math.h>
#include "linked_array_list.h"
#include "command.h"

using namespace std;



//Contract: process_command: char *->nothing
//Purpose: to process input files as commands
void process_command(char *theFile, char *output)
//Template: theFile
{
	string s;
	ifstream in;
	ofstream out;
	in.open(theFile);
	out.open(output);
	LinkedArrayList<string>* master_list = NULL;
	int remove_index = 0;

	while(in >> s)
	{
	
		if(s == "list")
		{
			in>>s;
			int llsize = atoi(s.c_str());
			
			if (llsize % 2 == 0)
			{
				delete master_list;
				master_list = new LinkedArrayList<string>(llsize);
				out<<"list "<<llsize<<endl;
			}
			else
			out<<"list "<< s <<endl;

		}
		else if (s == "insert")
		{
			
			out<<"insert ";
			in>>s;
			out<<s;
			int index = atoi(s.c_str());
			in>>s;
			out<<" "<<s<<endl;
			if (master_list != NULL)
			{
				master_list->insert(index, s);
			}
			else
			{
				out<<endl;
			}

		}
		else if (s == "remove")
		{
			remove_index++;
			out<<"remove ";
			in>>s;
			int index = atoi(s.c_str());
			out<<index;
			if (master_list != NULL)
			{
				out<<" ";
				out<<master_list->remove(index);
			}
			out<<endl;
	
		}
		else if (s == "print")
		{
		
			out<<"print "<<endl;
			if (master_list != NULL)
			{
				master_list->print(out);
			}
		}
		else if (s =="find")
		{
			in>>s;
			out<<"find ";
			if (master_list != NULL)
			{
				out<<s<< " ";
				out<<master_list->find(s)<<endl;
			}
			else
			{
				out<<endl;
			}
		}
	}
	delete master_list;
	in.close();
	out.close();
}
