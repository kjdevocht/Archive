/*Kevin DeVocht Assignment 13 Exercise 4. Write a function, restock, that consumes a CD title, 
a number of new copies, and an inventory and produces an inventory where the named CD’s 
stock has increased. Assume the CD is in the inventory.
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
		
	int show () 
	{
	return printf("new CD(\"%s\", \"%s\", %f, %d, \"%s\")", this->title, this->artist, this->price, this->numInStock, this->category);
	}
	
	//Contract: whichCD: CD, string, int->CD
	//Purpose: To see if a given CD matchs the given CD to be restocked
	CD* whichCD(const char* cdTitle, int restockNum)
	//Template: this, this->title, this->artist, this->price, this->numInStock, this->category
	{
		if(streq(this->title, cdTitle))
		{
		/* Test 1
			Zooropa->whichCD("Zooropa", 300)
				this = new CD("Zooropa", "U2", 10.99, 15, "Pop")
				this->title = "Zooropa"
				cdTitle = "Zooropa"
				this->numInStock = 15
				restockNum = 300
				ans = 315
			return new CD("Zooropa", "U2", 10.99, 315, "Pop")
		*/
		return new CD(this->title, this->artist, this->price, this->numInStock+restockNum, this->category);
		}
		else
		{
		/*Test 2
			Zooropa->whichCD("We Are The Night", 50000)
				this = new CD("Zooropa", "U2", 10.99, 15, "Pop")
				this->title = "Zooropa"
				cdTitle = "We Are The Night"
				this->numInStock = 15
				restockNum = 50000
				ans = 15
			return new CD("Zooropa", "U2", 10.99, 15, "Pop")
		*/
		
		/*Generalize Tests 1 and 2
			We can see that we need to compare strings to see if we are 
			looking at the right CD.  If we are, return a new CD with the 
			new numInStock.  If not return the same CD with no changes.  
			So we will need to call streq and use an if statement
		*/
		return this;
		}
	}
};

// An Inventory is
//  OneMoreCD
//  EmptyInv
class Inventory 
{
	public:
		virtual int show () = 0;
		
		//Contract: restock: string, int, Inventory->Inventory
		//Purpose: to add a given number to the correct numInStock
		virtual Inventory* restock(const char* cdTitle, int restockNum) = 0;
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
	
	int show () 
	{
    printf("new OneMoreCD( ");
    this->first->show();
    printf(", ");
    this->rest->show();
    return printf(" )");
	}
	
	
	//Contract: restock: string, string, OneMoreCD->Inventory
	//Purpose: to add a given number to the correct numInStock
	Inventory* restock(const char* cdTitle, int restockNum)
	//Template: this, this->first, this->rest, this->rest->restock(cdTitle, restockNum), cdTitle, restockNum
	{
	/*Test 1
		three->restock("Don't Tread on Me", 300)
			this = Dont_Tread_On_Me, Furr, We_Are_The_Night, new EmptyInv()
			this->first = Dont_Tread_On_Me
			this->rest = Furr, new EmptyInv()
			ans = Dont_Tread_On_Me, Furr, We_Are_The_Night, new EmptyInv()
		return Dont_Tread_On_Me, Furr, We_Are_The_Night, new EmptyInv()
	*/
	
	/*Test 2
		three->restock("We Are The Night", 50)
			this = Dont_Tread_On_Me, Furr, We_Are_The_Night, new EmptyInv()
			this->first = Dont_Tread_On_Me
			this->rest = Furr, We_Are_The_Night, new EmptyInv()
			ans = Dont_Tread_On_Me, Furr, We_Are_The_Night, new EmptyInv()
		return Dont_Tread_On_Me, Furr, We_Are_The_Night, new EmptyInv()
	*/
	
	/*Generalize Tests 2 and 3
		We can see that we need another function to look deeper.
		this function should consume a CD, string and an int and return a CD
		We do not need an if statement because we are returning an Inventory
	*/
	return (new OneMoreCD(this->first->whichCD(cdTitle, restockNum), this->rest->restock(cdTitle,restockNum)));
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
		
	int show () 
	{
    return printf("new EmptyInv()");
	}
	
	//Contract: restock: string, string, EmptyInv->Inventory
	//Purpose: to add a given number to the correct numInStock
	Inventory* restock(const char* cdTitle, int restockNum)
	//Template: this, cdTitle, restockNum
	{
	/*Test 1
		none->restock("Zooropa", 34)
			this = !
			cdTitle = "Zooropa"
			restockNum = 34
			ans = !
		return EmptyInv();
	*/
	return (new EmptyInv());
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

  printf("The answer is\n  ");
  two->restock("We Are The Night", 500)->show();
  printf("\nbut it should be\n  ");
  (new OneMoreCD ( Furr, new OneMoreCD (new CD("We Are The Night", "Chemical Brothers", 17.99, 506, "Electronic"), none) ) )->show();
  printf("\n");
  
  printf("The answer is\n  ");
  eight->restock("Furr", 1000000)->show();
  printf("\nbut it should be\n  ");
  (new OneMoreCD ( Zooropa, new OneMoreCD (The_Sun_And_The_Moon, new OneMoreCD (Light_Years, new OneMoreCD (Morangos_Com_Acucar, new OneMoreCD (Crazy_Love, new OneMoreCD (Dont_Tread_On_Me, new OneMoreCD (new CD("Furr", "Blitzen Trapper", 7.99, 1000005, "Indie"), new OneMoreCD (We_Are_The_Night, none) ) )))))))->show();
  printf("\n");
  
  printf("The answer is\n  ");
  none->restock("Morangos Com Açucar", 5)->show();
  printf("\nbut it should be\n  ");
  (new EmptyInv())->show();
  printf("\n");  

/*Substitution
	1. one->restock("We Are The Night", 500)
	
	2. new OneMoreCD(We_Are_The_Night, none)->restock("We Are The Night", 500)
	
	3. return (new OneMoreCD(this->first->whichCD(cdTitle, restockNum), this->rest->restock(cdTitle,restockNum)));
	
	4. return (new OneMoreCD(We_Are_The_Night, none)->first->whichCD(cdTitle, restockNum), (We_Are_The_Night, none)->rest->restock(cdTitle,restockNum));
	
	5. return (We_Are_The_Night->whichCD("We Are The Night", 500), none->restock("We Are The Night",500));
	
	6. if(streq(this->title, cdTitle)){return (new CD(this->title, this->artist, this->price, this->numInStock+restockNum, this->category);} else {return this;}, none->restock("We Are The Night",500));
	
	7. if(streq(new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->title, "We Are The Night")){return (new CD(new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->title, new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->artist, new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->price, new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->numInStock+500, new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->category));} else {return (new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic");}, none->restock("We Are The Night",500));

	8. if(streq("We Are The Night", "We Are The Night")){return (new CD("We Are The Night", "Chemical Brothers", 17.99, 6+500,"Electronic"));} else {return (new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic");}, none->restock("We Are The Night",500));
	
	9. if( strcmp(l,r) == 0){return (new CD("We Are The Night", "Chemical Brothers", 17.99, 6+500,"Electronic"));} else {return (new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic");}, none->restock("We Are The Night",500));

	10. if( strcmp("We Are The Night","We Are The Night") == 0){return (new CD("We Are The Night", "Chemical Brothers", 17.99, 6+500,"Electronic"));} else {return (new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic");}, none->restock("We Are The Night",500));

	11. if( 0 == 0){return (new CD("We Are The Night", "Chemical Brothers", 17.99, 6+500,"Electronic"));} else {return (new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic");}, none->restock("We Are The Night",500));
	
	12. if true{return (new CD("We Are The Night", "Chemical Brothers", 17.99, 6+500,"Electronic"));} else {return (new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic");}, none->restock("We Are The Night",500));
	
	13. return (new CD("We Are The Night", "Chemical Brothers", 17.99, 6+500,"Electronic")), none->restock("We Are The Night",500));
	
	14. return (new OneMoreCD(new CD("We Are The Night", "Chemical Brothers", 17.99, 6+500,"Electronic")), new EmptyInv());
*/
}