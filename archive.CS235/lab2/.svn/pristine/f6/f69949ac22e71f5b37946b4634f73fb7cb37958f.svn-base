/*Kevin DeVocht Lab 2*/

#include <map>
#include <set>
#include <fstream>
#include <vector>
#include <algorithm>
#include <string>
#include <sstream>
#include <iomanip>
#include <ctype.h>
#include <stdio.h>

using namespace std;

ostream& operator<< (ostream& out, const vector<int>& item);


int main(int argc, char *argv[])
{
	ifstream in;
	ofstream out;
	string gotline;	//The string that the input writes to
	set<string> dic;
	map<string, vector<int> >::iterator miss_spelled_it;
	string dic_string = "";
	vector<int> lines;
	map<string, vector<int> > miss_spelled;
	int line_number = 0;

	
	//This is phase one of the program.  It reads in the dicitonary
	//and automaticly sets everything to lower case
	in.open(argv[1]);
	
	while (getline(in, gotline))
	{
		//Read each line and force it to lowercase
		for (size_t str_length = 0; str_length<gotline.length(); str_length++)
		{
			gotline[str_length] = tolower(gotline[str_length]);
		}
	
		size_t word_length = 0;
		//take the forced lowercase word and put it back into a string
		while(word_length<gotline.length())
		{
			dic_string.append(1, gotline[word_length]);
			word_length++;
		}
		dic.insert(dic_string);	
		gotline = "";
		dic_string = "";
		
	}
	in.close();
	
	//This is phase two of the program.  It reads the txt file
	// and parses the lines into individual words
	in.open(argv[2]);
	
	while (getline(in, gotline))
	{
		line_number++;
		string parsed_word = "";
		//force each line from the text to lower case
		for (size_t str_length = 0; str_length<gotline.length(); str_length++)
		{
			gotline[str_length] = tolower(gotline[str_length]);
		}
		
		size_t line_length = 0;


		while(line_length<gotline.length())
		{
			while (isalpha(gotline[line_length]))
			{
				parsed_word.append(1, gotline[line_length]);
				line_length++;
			}


			if (dic.find(parsed_word) == dic.end()&& parsed_word != "")
			{
				miss_spelled[parsed_word].push_back(line_number);
				parsed_word = "";
			}
			else
			{
				parsed_word = "";	
			}
			line_length++;
		}
		gotline = "";
	}
	in.close();
	
	out.open(argv[3]);
		for(miss_spelled_it = miss_spelled.begin(); miss_spelled_it != miss_spelled.end(); ++miss_spelled_it)
		{
                    out << (*miss_spelled_it).first<<":";
			for(size_t i =0; i<(*miss_spelled_it).second.size(); i++)
			{
				out << " " << (*miss_spelled_it).second.at(i);
			}
			out  << endl;
		}
		
		
	out.close();
}
