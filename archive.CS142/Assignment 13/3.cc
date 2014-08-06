/*Kevin DeVocht Assignment 13 Exercise 3. Write a function, copiesInStock, that 
consumes a CD title, artist, and inventory and produces the number of copies of 
the item in stock—return 0 if it isn’t in the inventory (because that means it isn’t in stock.)
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

	
	//Contract: whichCD: CD, string, string-> int
	//Purpose: To find the right CD and return how many are in stock
	int whichCD(const char* cdTitle, const char* cdArtist)
	//Template: this, this->title, this->artist, this->price, this->numInStock, this->category, cdTitle, cdArtist
	{				
		if (streq(this->title, cdTitle) && streq(this->artist, cdArtist))
		{
		/*Test 1
			Zooropa->whichCD("Zooropa", "U2")
				this = new CD("Zooropa", "U2", 10.99, 15, "Pop")
				this->title = "Zooropa"
				this->artist = "U2"
				this->price = 10.99
				this->numInStock = 15
				this->category "Pop"
				cdTitle = "Zooropa"
				cdArtist = "U2"
				ans = 15
			return 15
		*/
		return this->numInStock;
		}
		else
		{
		/*Test 2
			Zooropa->whichCD("Crazy Love", "Michael Buble")
				this = new CD("Zooropa", "U2", 10.99, 15, "Pop")
				this->title = "Zooropa"
				this->artist = "U2"
				this->price = 10.99
				this->numInStock = 15
				this->category "Pop"
				cdTitle = "Crazy Love"
				cdArtist = "Michael Buble"
				ans = 0
			return 0		
		*/

	/*Generalize Tests 1 and 2
		We can see that we need to compare both cdTitle and cdArtist to 
		the title and artist of the cd.  if they both are a match then 
		return the numInStock, otherwise return a 0 and copiesInStock will sort it out
	*/



			return 0;
		}
	}	
};

// An Inventory is
//  OneMoreCD
//  EmptyInv
class Inventory 
{
	public:
		//Contract: copiesInStock: Inventory, string, string -> int
		//Purpose: To find how many copies of a given cd are in stock
		virtual int copiesInStock (const char* cdTitle, const char* cdArtist) = 0;
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
	

	
	//Contract: copiesInStock: OneMoreCD, string, string -> int
	//Purpose: To find how many copies of a given cd are in stock
	int copiesInStock (const char* cdTitle, const char* cdArtist)
	//Template: this, this->first, this->rest, this->rest->copiesInStock(), cdTitle, cdArtist
	{
		if(!(this->first->whichCD(cdTitle, cdArtist) == 0))
		{
			/*Test 1
				two->copiesInStock("Furr", "Blitzen Trapper")
					this = Furr, We_Are_The_Night, new EmptyInv()
					this->first = Furr
					this->rest = We_Are_The_Night, new EmptyInv()
					cdTitle = "Furr"
					cdArtist = "Blitzen Trapper"
					ans = 5
				return 5
			*/
		return this->first->whichCD(cdTitle, cdArtist);
		}
		
		else
		{
			/*Test 2
				two->copiesInStock("We Are The Night", "Chemical Brothers")
					this = Furr, We_Are_The_Night, new EmptyInv()
					this = Furr, We_Are_The_Night, new EmptyInv()
					this->first = Furr
					this->rest = We_Are_The_Night, new EmptyInv()
					cdTitle = "We Are The Night"
					cdArtist = "Chemical Brothers"
					ans = 6
				return 6
			*/
			
			/*Generalize Tests 1 and 2
				From these two tests we can see that we need another function that can look 
				deeper, at the numInStock part of the given artist and title.  
				This function needs to consume a CD, 2 strings and return an int
				Plus we will need an if statement to allow for all possible anwsers
			*/
		return this->rest->copiesInStock(cdTitle, cdArtist);
		}
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
	
	//Contract: copiesInStock: EmptyInv, string, string -> int
	//Purpose: To find how many copies of a given cd are in stock
	int copiesInStock (const char* cdTitle, const char* cdArtist)
	//Template: this, cdTitle, cdArtist
	{
	/*Test 1
		none->copiesInStock("Zooropa", "U2")
			this = new EmptyInv()
			cdTitle = "Zooropa"
			cdArtist = "U2"
			ans = 0
		return 0
	*/
	return 0;
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

printf("The answer is %d, but should be %d\n", two->copiesInStock("Furr", "Blitzen Trapper"), 5);
printf("The answer is %d, but should be %d\n", two->copiesInStock("We Are The Night", "Chemical Brothers"), 6);
printf("The answer is %d, but should be %d\n", none->copiesInStock("We Are The Night", "Chemical Brothers"), 0);



/* Substitution
	1. one->copiesInStock("We Are The Night", "Chemical Brothers")
	
	2. new OneMoreCD(We_Are_The_Night, none)->copiesInStock("We Are The Night", "Chemical Brothers")
	
	3. if(!(this->first->whichCD(cdTitle, cdArtist) == 0)) {return this->first->whichCD(cdTitle, cdArtist);} else {return this->rest->copiesInStock(cdTitle, cdArtist);}

	4. if(!(new OneMoreCD(We_Are_The_Night, none)->first->whichCD("We Are The Night", "Chemical Brothers") == 0)) {return new OneMoreCD(We_Are_The_Night, none)->first->whichCD("We Are The Night", "Chemical Brothers");} else {return new OneMoreCD(We_Are_The_Night, none)->rest->copiesInStock("We Are The Night", "Chemical Brothers");}
	
	5. if(!(We_Are_The_Night->whichCD("We Are The Night", "Chemical Brothers") == 0)) {return We_Are_The_Night->whichCD("We Are The Night", "Chemical Brothers");} else {return none->copiesInStock("We Are The Night", "Chemical Brothers");}
	
	6. if(!(if (streq(this->title, cdTitle) && streq(this->artist, cdArtist)){return this->numInStock;} else {return 0;} == 0)) {return We_Are_The_Night->whichCD("We Are The Night", "Chemical Brothers");} else {return none->copiesInStock("We Are The Night", "Chemical Brothers");}
	
	7. if(!(if (streq(new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->title, "We Are The Night") && streq(new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->artist, "Chemical Brothers")){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->numInStock;} else {return 0;} == 0)) {return We_Are_The_Night->whichCD("We Are The Night", "Chemical Brothers");} else {return none->copiesInStock("We Are The Night", "Chemical Brothers");}
	
	8. if(!(if (streq("We Are The Night", "We Are The Night") && streq("Chemical Brothers", "Chemical Brothers")){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->numInStock;} else {return 0;} == 0)) {return We_Are_The_Night->whichCD("We Are The Night", "Chemical Brothers");} else {return none->copiesInStock("We Are The Night", "Chemical Brothers");}
	
	9. if(!(if (return strcmp(l,r) == 0; && return strcmp(l,r) == 0;){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->numInStock;} else {return 0;} == 0)) {return We_Are_The_Night->whichCD("We Are The Night", "Chemical Brothers");} else {return none->copiesInStock("We Are The Night", "Chemical Brothers");}
	
	10. if(!(if (return strcmp("We Are The Night","We Are The Night") == 0; && return strcmp("Chemical Brothers","Chemical Brothers") == 0;){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->numInStock;} else {return 0;} == 0)) {return We_Are_The_Night->whichCD("We Are The Night", "Chemical Brothers");} else {return none->copiesInStock("We Are The Night", "Chemical Brothers");}
		
	11. if(!(if (0 == 0; && 0 == 0;){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->numInStock;} else {return 0;} == 0)) {return We_Are_The_Night->whichCD("We Are The Night", "Chemical Brothers");} else {return none->copiesInStock("We Are The Night", "Chemical Brothers");}
		
	12. if(!(if true{return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->numInStock;} else {return 0;} == 0)) {return We_Are_The_Night->whichCD("We Are The Night", "Chemical Brothers");} else {return none->copiesInStock("We Are The Night", "Chemical Brothers");}

	14. if(!(false)) {return We_Are_The_Night->whichCD("We Are The Night", "Chemical Brothers");} else {return none->copiesInStock("We Are The Night", "Chemical Brothers");}

	15. if(true) {return We_Are_The_Night->whichCD("We Are The Night", "Chemical Brothers");} else {return none->copiesInStock("We Are The Night", "Chemical Brothers");}

	16. return We_Are_The_Night->whichCD("We Are The Night", "Chemical Brothers");
	
	17. return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->whichCD("We Are The Night", "Chemical Brothers");

	18. if (streq(this->title, cdTitle) && streq(this->artist, cdArtist)){return this->numInStock;} else {return 0;}
	
	19. if (streq(new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->title, cdTitle) && streq(new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->artist, cdArtist)){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->numInStock;} else {return 0;}

	20. if (streq("We Are The Night", "We Are The Night") && streq("Chemical Brothers", "Chemical Brothers")){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->numInStock;} else {return 0;}
	
	21. if (streq("We Are The Night", "We Are The Night") && streq("Chemical Brothers", "Chemical Brothers")){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->numInStock;} else {return 0;}
	
	22. if (return strcmp(l,r) == 0; && return strcmp(l,r) == 0;){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->numInStock;} else {return 0;}
	
	23. if (return strcmp("We Are The Night","We Are The Night") == 0; && return strcmp("We Are The Night","We Are The Night") == 0;){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->numInStock;} else {return 0;}
	
	24. if (0 == 0 && 0 == 0){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->numInStock;} else {return 0;}
	
	24. if (true && true){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->numInStock;} else {return 0;}
	
	25. if (true){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->numInStock;} else {return 0;}
	
	26. return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->numInStock;

	27. return 6;
*/
}