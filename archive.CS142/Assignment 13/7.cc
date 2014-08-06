/*Kevin DeVocht Assignment 13 Exercise 7. Write a function, 
categoryStock, that consumes an inventory and a category 
and produces a list of all CDs in the named category that 
have more than 0 copies in stock.
*/


#include <stdio.h>
#include <math.h>
#include <string.h>


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
	
	//SAF: CD string->bool
	//Purpose: to see if a given CD is in the correct category and has more than 0 CD's in stock
	bool SAF (const char* theCat)
	//Template: this, this->title, this->artist, this->price, this->numInStock, this->category, theCat
	{
		if (strcmp(this->category, theCat) == 0 && this->numInStock > 0)
		{
		/*Test 1
			Zooropa->SAF("Pop")
				this = new CD("Zooropa", "U2", 10.99, 15, "Pop")
				this->title = "Zooropa"
				this->artist = "U2"
				this->price  = 10.99
				this->numInStock = 15
				this->category = "Pop"
				theCat = "Pop"
				ans = true
			return true
		*/
		return true;
		}
		else
		{
		/*Test 2
			Zooropa->SAF("Rock")
				this = new CD("Zooropa", "U2", 10.99, 15, "Pop")
				this->title = "Zooropa"
				this->artist = "U2"
				this->price  = 10.99
				this->numInStock = 15
				this->category = "Pop"
				theCat = "Pop"
				ans = false
			return false
		*/
		
		/*Generalize Tests 1 and 2
			We can see that we need to compare this->category 
			to theCat and this->numInStock to zero.  We will also need an if statement if both items do not match
		*/
		return false;
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
		
		//categoryStock: Inventory string->Invnetory
		//Purpose: To see if a title in a given category is in of stock
		virtual Inventory* categoryStock(const char* theCat) = 0;

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
	
	
	
	//categoryStock: OneMoreCD string->Invnetory
	//Purpose: To see if a title in a given category is in of stock
	Inventory* categoryStock(const char* theCat)
	//Template: this, this->first, this-rest, this->rest->categoryStock(theCat), theCat
	{
		if (this->first->SAF(theCat))
		{
		/*Test 1
			one->categoryStock("Electronic")
				this = We_Are_The_Night, none
				this->first = We_Are_The_Night
				this->rest = none
				theCat = "Electronic"
				ans = new OneMoreCD(We_Are_The_Night, none)
			return (new OneMoreCD(this->first->SAF("Electronic"), new EmptyInv()))
		*/
		return (new OneMoreCD(this->first, this->rest->categoryStock(theCat)));
		}
		else
		{
		/*Test 2
			two->categoryStock("Indie")
				this = Furr, We_Are_The_Night, none
				this->first = Furr
				this->rest = We_Are_The_Night, none
				theCat = "Indie"
				ans = new OneMoreCD(Furr, none)
			return (new OneMoreCD(this->first->SAF("Indie"), new EmptyInv()))
		*/
		
		/*Test 3
			three->categoryStock("Indie")
				this = Dont_Tread_On_Me, Furr, We_Are_The_Night, none
				this->first = Dont_Tread_On_Me
				this->rest = Furr, We_Are_The_Night, none
				theCat = "Indie"
				ans = new OneMoreCD(Furr, none)
			return (new OneMoreCD(this->first->SAF("Indie"), this->rest->categoryStock(theCat)))
		*/
		
		/*Generalize Tests 1 2 and 3
			Well we know that we need SAF that can look deeper.  
			It should consume a CD and a string and return a bool.  
			It needs to make sure the given CD is in the right category 
			and has more than 0 copies in stock.  Then categoryStock can 
			add it to the list if both are true and keep checking the rest 
			of the list.  Otherwise keep checking the rest of the list
		*/
		return this->rest->categoryStock(theCat);
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
		
	int show () 
	{
    return printf("new EmptyInv()");
	}
		
	//categoryStock: EmptyInv string->Invnetory
	//Purpose: To see if a title in a given category is in of stock
	Inventory* categoryStock(const char* theCat)
	//Template: this, theCat
	{
	/*Test 1
		none->categoryStock("Rock")
			this = !
			theCat = "Rock"
			ans =!
		return (new EmptyInv())
	*/
	return (new EmptyInv());
	}		
	
};

int main () 
{

CD* We_Are_The_Night = new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic");
CD* Furr = new CD("Furr", "Blitzen Trapper", 7.99, 5, "Indie");
CD* Dont_Tread_On_Me = new CD("Don't Tread on Me", "311", 13.99, 21, "Rock");
CD* Crazy_Love = new CD("Crazy Love", "Michael Buble", 15.99, 12, "Easy Listening");
CD* Morangos_Com_Acucar = new CD("Morangos Com Açucar", "Aramac", 25.99, 1, "World");
CD* Light_Years = new CD("Light Years", "Electric Light Orchestra", 9.99, 3, "Rock");
CD* The_Sun_And_The_Moon = new CD("The Sun and The Moon", "The Bravery", 12.99, 26, "Rock");
CD* Zooropa = new CD("Zooropa", "U2", 10.99, 15, "Pop");


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
  three->categoryStock("Rock")->show();
  printf("\nbut it should be\n  ");
  (new OneMoreCD ( Dont_Tread_On_Me, none))->show();
  printf("\n");
  
  printf("The answer is\n  ");
  eight->categoryStock("Rock")->show();
  printf("\nbut it should be\n  ");
  (new OneMoreCD ( The_Sun_And_The_Moon, (new OneMoreCD ( Light_Years, (new OneMoreCD ( Dont_Tread_On_Me, none))))))->show();
  printf("\n");
  
  printf("The answer is\n  ");
  none->categoryStock("Rock")->show();
  printf("\nbut it should be\n  ");
  (new EmptyInv())->show();
  printf("\n");

return 0;

/*Substitution
	1.one->categoryStock("Electronic")
	
	2.new OneMoreCD(We_Are_The_Night, none)->categoryStock("Electronic")
	
	3.if (this->first->SAF(theCat)){return (new OneMoreCD(this->first, this->rest->categoryStock(theCat)));} else {return this->rest->categoryStock(theCat);}
	
	4.if (new OneMoreCD(We_Are_The_Night, none)->first->SAF(theCat)){return (new OneMoreCD(new OneMoreCD(We_Are_The_Night, none)->first, new OneMoreCD(We_Are_The_Night, none)->rest->categoryStock(theCat)));} else {return new OneMoreCD(We_Are_The_Night, none)->rest->categoryStock(theCat);}
	
	5.if (We_Are_The_Night->SAF(theCat)){return (new OneMoreCD(We_Are_The_Night, none->categoryStock(theCat)));} else {return none->categoryStock(theCat);}
	
	6.if (if (strcmp(this->category, theCat) == 0 && this->numInStock > 0){return true;} else {return false;}){return (new OneMoreCD(We_Are_The_Night, none->categoryStock(theCat)));} else {return none->categoryStock(theCat);}
	
	7.if (if (strcmp(new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->category, "Electronic") == 0 && new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->numInStock > 0){return true;} else {return false;}){return (new OneMoreCD(We_Are_The_Night, none->categoryStock(theCat)));} else {return none->categoryStock(theCat);}

	8.if (if (strcmp("Electronic", "Electronic") == 0 && 6 > 0){return true;} else {return false;}){return (new OneMoreCD(We_Are_The_Night, none->categoryStock(theCat)));} else {return none->categoryStock(theCat);}
	
	9.if (if (0 == 0 && true){return true;} else {return false;}){return (new OneMoreCD(We_Are_The_Night, none->categoryStock(theCat)));} else {return none->categoryStock(theCat);}
	
	10.if (if (true && true){return true;} else {return false;}){return (new OneMoreCD(We_Are_The_Night, none->categoryStock(theCat)));} else {return none->categoryStock(theCat);}
	
	11.if (if true{return true;} else {return false;}){return (new OneMoreCD(We_Are_The_Night, none->categoryStock(theCat)));} else {return none->categoryStock(theCat);}
	
	12.if (true){return (new OneMoreCD(We_Are_The_Night, none->categoryStock(theCat)));} else {return none->categoryStock(theCat);}
	
	13.return (new OneMoreCD(We_Are_The_Night, none->categoryStock(theCat)));} 
	
	14.return (new OneMoreCD(We_Are_The_Night, new EmptyInv()));} 
*/
}