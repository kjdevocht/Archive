/*tile.cpp Kevin DeVocht lab3*/

#include <string.h>
#include <sstream>
#include <iomanip>

#include "tile.h"

using namespace std;


//Set functions
void tile::set_tile_letter(string tile_letter)
{
	this->tile_letter = tile_letter;
}


void tile::set_row(int row)
{
	this->row = row;
}

void tile::set_column(int column)
{
	this->column = column;
}





