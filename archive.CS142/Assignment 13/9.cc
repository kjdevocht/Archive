/*Kevin DeVocht Assignment 13 Exercise 9. Propose and 
write your own function on inventories. Proposals that 
are too trivial or similar to required exercises will 
not be accepted.
*/


#include <stdio.h>
#include <math.h>
#include <string.h>

// streq : string string -> boolean
// Purpose: compares to strings for equality, use this rather than == to compare strings
bool streq ( const char* l, const char* r ) 
{
  return strcmp(l,r) == 0;
}


// A CD is a
// new CD(title, artist, price, numInStock, category)
//where
// title is a string
// artist is a string
// price is an decimal number
// numInStock is an integer
// and category is a string
class CD 
{
	public:
		const char* title;
		const char* artist;
		double price;
		int numInStock;
		const char* category;
	
	CD (const char* title0, const char* artist0, double price0, int numInStock0, const char* category0) 
		{
		this->title = title0;
		this->artist = artist0;
		this->price = price0;
		this->numInStock = numInStock0;
		this->category = category0;
		}
		
};

// An Inventory is
//  OneMoreCD
//  EmptyInv
class Inventory 
{
	public:

};

//A OneMoreCD is
// a new OneMoreCD(first, rest)
//where
//first is a CD
// and rest is an Inventory
class OneMoreCD : public Inventory 
{
	public:
		CD* first;
		Inventory* rest;
		
	OneMoreCD (CD* first0, Inventory* rest0) 
	{
		this->first = first0;
		this->rest = rest0;
	}
};

//An EmptyInv is a
// new EmptyInv()
// where there is nothing because it is empty
class EmptyInv : public Inventory
{
	public:
		EmptyInv() 
		{
		}
};

int main () 
{

CD* Zooropa = new CD("Zooropa", "U2", 10.99, 15, "Pop");
CD* The_Sun_And_The_Moon = new CD("The Sun and The Moon", "The Bravery", 12.99, 26, "Rock");
CD* Light_Years = new CD("Light Years", "Electric Light Orchestra", 9.99, 3, "Rock");
CD* Morangos_Com_Acucar = new CD("Morangos Com Açucar", "Aramac", 25.99, 1, "World");
CD* Crazy_Love = new CD("Crazy Love", "Michael Buble", 15.99, 12, "Easy Listening");
CD* Dont_Tread_On_Me = new CD("Don't Tread on Me", "311", 13.99, 21, "Rock");
CD* Furr = new CD("Furr", "Blitzen Trapper", 7.99, 5, "Indie");
CD* We_Are_The_Night = new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic");


Inventory* none =  new EmptyInv();
Inventory* one  = new OneMoreCD(We_Are_The_Night, none);
Inventory* two  = new OneMoreCD(Furr, one);
Inventory* three  = new OneMoreCD(Dont_Tread_On_Me, two);
Inventory* four  = new OneMoreCD(Crazy_Love, three);
Inventory* five  = new OneMoreCD(Morangos_Com_Acucar, four);
Inventory* six  = new OneMoreCD(Light_Years, five);
Inventory* seven  = new OneMoreCD(The_Sun_And_The_Moon, six);
Inventory* eight  = new OneMoreCD(Zooropa, seven);

printf("I Really Love Music");


}