/*tile header file for lab 3*/


#include <ostream>
#include <string>
#include <vector>


using namespace std;

class tile
{
	public:
		string tile_letter;
		int row;
		int column;

		


		
	tile(string tile_letter0, int row0, int column0) : tile_letter(tile_letter0), row(row0), column(column0)
	{}
	
	string get_tile_letter() const {return tile_letter;}
	
	int get_row() const {return row;}
	int get_column() const {return column;}

	
	void set_tile_letter(string tile_letter);
	void set_row(int row);
	void set_column(int column);
	

	
};

ostream& operator<< (ostream& out, const tile& item);