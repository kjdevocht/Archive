/*lab6.cpp -lab6 - Kevin DeVocht*/
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
#include "command.h"


using namespace std;




int main (int argc, char *argv[])
{
	vector<student> student_list;
	if (argc < 4)
	{
		process_command(argv[1], argv[2]);
	}
	else
	{
		student_list = process_search(argv[1]);
		process_query(argv[2], argv[3], student_list);
	}
}
