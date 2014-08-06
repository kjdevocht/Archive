/*Kevin DeVocht Lab 3*/

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
#include <math.h>
#include "tile.h"


using namespace std;


//Global Variables
int row_length = 0;
set<string> words_found;




//Contract lower_case: string->string
//Purpose: to force a string to lower case for easy comparison
string lower_case(string gotline)
{
	for (size_t str_length = 0; str_length<gotline.length(); str_length++)
	{
		gotline[str_length] = tolower(gotline[str_length]);
	}
return gotline;
}



//Contract dictionary_set: char*->set<string>
//Purpose: to create the dictionary to be used to find words on the board
set<string> dictionary_set(char *theFile)
//Template: theFile, dic, in, gotline, str_length
{
	ifstream in;
	set<string> dic;
	string gotline;
	
in.open(theFile);	
	while (getline(in, gotline))
	{
		gotline = lower_case(gotline);
		dic.insert(gotline);
		gotline = "";
	}
	in.close();
	return dic;
}

//Contract board_set: char*->vector<tile>
//Purpose: to create a vector<tile> which contains all the tiles on the board
vector<tile> board_set(char *theFile)
{
	string gotline;
	vector<tile> game_board;
	vector<string> row_finder;
	string tile_letter = "";
	int row = 1;
	int column = 0;
	int curloc = 0;
	size_t line_length = 0; 
	ifstream in;
	
	
	in.open(theFile);
	while (getline(in, gotline))
	{
		gotline = lower_case(gotline);
		line_length = 0;
		printf("gotline length: %u\n",gotline.length());
		printf("gotline: %s\n",gotline.c_str());
		while(line_length<gotline.length())
		{
			while (isalpha(gotline[line_length]))
			{
				printf("tile_letter: %s\n",tile_letter.c_str());
				tile_letter.append(1, gotline[line_length]);
				printf("tile_letter: %s\n",tile_letter.c_str());
				line_length++;
			}
			line_length++;
			
			row_finder.push_back(tile_letter);
			tile_letter = "";
		}
		gotline = "";
	}
	in.close();
	row_length = sqrt(row_finder.size());
	for(int row = 0; row<row_length; ++row)
	{
		for(int column = 0; column<row_length; ++column)
		{
			tile newTile = tile(row_finder.at(curloc), row+1, column+1);
			printf("string: %s, row: %d, column: %d\n", row_finder.at(curloc).c_str(), row, column);
			game_board.push_back(newTile);
			curloc++;
		}
	}
	return game_board;
}

//Contract word_finder: vector<tile> set<string> set<int> string int ->int
//Purpose: to use recursion to find all possible words and put them in a set
int word_finder(int curloc, vector<tile> game_board, set<string> dic, set<int> been_there, string possible_word )
{
	std::set<string>::iterator dic_it;

	//Check to make sure the current location is even on the board
	if (game_board.empty())
	{
		return 0;
	}
	if (curloc < 0 || curloc > (game_board.size()-1)) 
	{
		return 0;
	}
	else if(game_board.at(curloc).row < 1|| game_board.at(curloc).column < 1 || game_board.at(curloc).column > row_length  || game_board.at(curloc).row > row_length )
	{
		return 0;
	}
	else 
	{
		//Add the string at the current location to the possible word
		possible_word.append(game_board.at(curloc).tile_letter);

		//Check to make sure you have not used this square before
		if (been_there.find(curloc) != been_there.end())
		{
			return 0;
		}

		else 
		{
			//Check to see if the possible word can possible even be in the dictionary
			if (dic.lower_bound(possible_word) == dic.end())
			{
				return 0;
			}
			else
			{
				dic_it = dic.lower_bound(possible_word);
			}
			//Refine the search to see if the possible word might be in the dictionary
			if (dic_it->compare(0, possible_word.length(), possible_word) !=0)
			{
				return 0;
			}

			else
			{
				been_there.insert(curloc);
				//Check to see if your possible word is in the dictionary
				if (dic.find(possible_word) != dic.end()&& possible_word.length() > 3)
				{
					words_found.insert(possible_word);
				}

				
				//Top left corner
				if (game_board.at(curloc).row == 1 && game_board.at(curloc).column == 1)
				{
					word_finder(curloc + 1, game_board, dic, been_there, possible_word);
					word_finder(curloc +(row_length), game_board, dic, been_there, possible_word);
					word_finder(curloc +(row_length + 1), game_board, dic, been_there, possible_word);
				}
				//Bottom right corner
				else if (game_board.at(curloc).row == row_length && game_board.at(curloc).column == row_length)
				{
					word_finder(curloc - 1, game_board, dic, been_there, possible_word);
					word_finder(curloc -(row_length + 1), game_board, dic, been_there, possible_word);
					word_finder(curloc -(row_length), game_board, dic, been_there, possible_word);
				}
				//Bottom left corner
				else if (game_board.at(curloc).row == row_length && game_board.at(curloc).column == 1)
				{
					word_finder(curloc -(row_length), game_board, dic, been_there, possible_word);
					word_finder(curloc + 1, game_board, dic, been_there, possible_word);
					word_finder(curloc -(row_length - 1), game_board, dic, been_there, possible_word);
				}
				//Top right corner
				else if (game_board.at(curloc).row == 1 && game_board.at(curloc).column == row_length)
				{
					word_finder(curloc +(row_length), game_board, dic, been_there, possible_word);
					word_finder(curloc - 1, game_board, dic, been_there, possible_word);
					word_finder(curloc +(row_length - 1), game_board, dic, been_there, possible_word);				
				}
				//Far left column
				else if (game_board.at(curloc).column == 1)
				{
					word_finder(curloc -(row_length), game_board, dic, been_there, possible_word);
					word_finder(curloc -(row_length - 1), game_board, dic, been_there, possible_word);
					word_finder(curloc + 1, game_board, dic, been_there, possible_word);
					word_finder(curloc +(row_length + 1), game_board, dic, been_there, possible_word);
					word_finder(curloc +(row_length), game_board, dic, been_there, possible_word);
				}
				//Bottom row
				else if (game_board.at(curloc).row == row_length)
				{
					word_finder(curloc + 1, game_board, dic, been_there, possible_word);
					word_finder(curloc - 1, game_board, dic, been_there, possible_word);
					word_finder(curloc -(row_length + 1), game_board, dic, been_there, possible_word);
					word_finder(curloc -(row_length), game_board, dic, been_there, possible_word);
					word_finder(curloc -(row_length -1), game_board, dic, been_there, possible_word);
				}
				//Far right column
				else if (game_board.at(curloc).column == row_length)
				{
					word_finder(curloc -(row_length), game_board, dic, been_there, possible_word);
					word_finder(curloc +(row_length - 1), game_board, dic, been_there, possible_word);
					word_finder(curloc - 1, game_board, dic, been_there, possible_word);
					word_finder(curloc -(row_length + 1), game_board, dic, been_there, possible_word);
					word_finder(curloc +(row_length), game_board, dic, been_there, possible_word);				
				}
				//Top row
				else if (game_board.at(curloc).row == 1)
				{
					word_finder(curloc + 1, game_board, dic, been_there, possible_word);
					word_finder(curloc - 1, game_board, dic, been_there, possible_word);
					word_finder(curloc +(row_length + 1), game_board, dic, been_there, possible_word);
					word_finder(curloc +(row_length), game_board, dic, been_there, possible_word);
					word_finder(curloc +(row_length -1), game_board, dic, been_there, possible_word);				
				}
				else
				{
					word_finder(curloc + 1, game_board, dic, been_there, possible_word);
					word_finder(curloc +(row_length - 1), game_board, dic, been_there, possible_word);
					word_finder(curloc +(row_length), game_board, dic, been_there, possible_word);
					word_finder(curloc +(row_length + 1), game_board, dic, been_there, possible_word);
					word_finder(curloc -(row_length + 1), game_board, dic, been_there, possible_word);
					word_finder(curloc -(row_length), game_board, dic, been_there, possible_word);
					word_finder(curloc -(row_length -1), game_board, dic, been_there, possible_word);
					word_finder(curloc - 1, game_board, dic, been_there, possible_word);
				}
			}
		}
	}
	return 0;
}

int main(int argc, char *argv[])
{
	ofstream out;
	set<string> dic;
	vector<tile> game_board;
	set<int> been_there;



	
	//This is phase one of the program.  It reads in the dicitonary
	// and puts the words into a set

		dic = dictionary_set(argv[1]);

	//This is phase two of the program.  It reads the boggle file
	// and parses the lines into individual tiles

		game_board = board_set(argv[2]);
		row_length = sqrt(game_board.size());




		for ( size_t j= 0; j <= game_board.size(); j++)
		{
			word_finder(j, game_board, dic, been_there, "");
		}

	

	//This is phase three of the program.  It outputs the words
	// that were found to a file.
	out.open(argv[3]);
		for(std::set<string>::iterator it = words_found.begin(); it != words_found.end(); ++it)
		{
			out << *it <<endl;
	
		}
	out.close();
}