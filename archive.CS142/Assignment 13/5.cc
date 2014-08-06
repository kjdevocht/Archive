/*Kevin DeVocht Assignment 13 Exercise 5. Write a function, titlesBy, 
that consumes an artist and inventory, and returns a list of titles 
by that artist in the inventory.
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
	

	//Contract: rightArtist: CD, string->string
	//Purpose: To see if a given CD is by the same artist as a given artist
	const char* rightArtist(const char* artistName)
	//Template: this, this->title, this->artist, this->price, this->numInStock, this->category, artistName
	{
		if (streq(this->artist, artistName))
		{
		/*Test 1
			Zooropa->rightArtist("U2")
				this = new CD("Zooropa", "U2", 10.99, 15, "Pop")
				this->title = "Zooropa"
				this->artist = "U2"
				this->price = 10.99
				this->numInStock = 15
				this->category = "Pop"
				artistName = "U2"
				ans = "Zooropa"
			return "Zooropa"
		*/
		return this->title;
		}
		else
		{
		/*Test 2
			Zooropa->rightArtist("Chemical Brothers")
				this = new CD("Zooropa", "U2", 10.99, 15, "Pop")
				this->title = "Zooropa"
				this->artist = "U2"
				this->price = 10.99
				this->numInStock = 15
				this->category = "Pop"
				artistName = "Chemical Brothers"
				ans = !
			return ""
		*/
		
		/*Generalize Tests 1 and 2
			We can see that we will need an if statement 
			and that if the artists match, we need to return t
			he title otherwise return ""
		*/
		return "";
		}
	}	
};



//A ListOfTitles is
// OneMoreTitle
// EmptyLst
class ListOfTitles
{
	public:
		virtual int show () = 0;
};


//A OneMoreTitle is
// a new OneMoreTitle(first, rest)
//where 
//first is a string
//and rest is a ListOfTitles
class OneMoreTitle : public ListOfTitles
{
	public:
		const char* first;
		ListOfTitles* rest;
		
	OneMoreTitle(const char* first0, ListOfTitles* rest0)
	{
		this->first = first0;
		this->rest = rest0;
	}
	

	
	int show () 
	{
    printf("new OneMoreTitle(\"%s", this->first);
    printf(", ");
    this->rest->show();
	printf(")");
    return 0;
	}

};

//An EmptyLst is
// a new EmptyLst()
//where there is nothing because it is empty
class EmptyLst : public ListOfTitles
{
	public:
	
	
	EmptyLst()
	{
	}
	

	int show () 
	{
    printf("new EmptyLst()");
    return 0;
	}
};









// An Inventory is
//  OneMoreCD
//  EmptyInv
class Inventory 
{
	public:
	
		//Contract: titlesBy: Inventory, string->ListOfTitles
		//Purpose: To make a list of all titles by a given artist
		virtual ListOfTitles* titlesBy(const char* artistName) = 0;

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
	
	
	//Contract: titlesBy: OneMoreCD, string->ListOfTitles
	//Purpose: To make a list of all titles by a given artist
	ListOfTitles* titlesBy(const char* artistName)
	//Template: this, this->first, this->rest, this->rest->titlesBy(artistName), artistName
	{
		if (!(streq(this->first->rightArtist(artistName), "")))
		{
		/*Test 1
			one->titlesBy("Chemical Brothers")
				this = We_Are_The_Night, none
				this->first = We_Are_The_Night
				this->rest = none
				artistName = "Chemical Brothers"
				ans = We_Are_The_Night, !
			return (new ListOfTitles("We are the Night", new EmptyLst()))
		*/
		return (new OneMoreTitle(this->first->rightArtist(artistName), this->rest->titlesBy(artistName)));
		}
		
		else
		{
		/*Test 2
			ten->titlesBy("U2")
				this = Zooropa, The_Sun_And_The_Moon, Light_Years, The_Joshua_Tree, Morangos_Com_Acucar, Crazy_Love, Dont_Tread_On_Me, Achtung_Baby, Furr, We_Are_The_Night, none
				this->first = Zooropa
				this->rest = The_Sun_And_The_Moon, Light_Years, The_Joshua_Tree, Morangos_Com_Acucar, Crazy_Love, Dont_Tread_On_Me, Achtung_Baby, Furr, We_Are_The_Night, none
				artistName = "U2"
				ans = Zooropa, The_Joshua_Tree, Achtung_Baby, !
			return (new ListOfTitles("Zooropa", new OneMoreTitle("The Joshua Tree", new OneMoreTitle("Achtung Baby", new EmptyLst()))))
		*/
		
		/*Generalize Tests 1 and 2
			We can clearly see that we will need an auxillary function to look 
			deeper at the indivdual titles of each CD.  This function should take 
			in a CD and a string and return a string.  We will also need a  
			ListOfTitles so that we can return a list of the Titles.  
			The return statement will need an if statement to know if the CD's 
			title should be added to the new list of Titles or if we should just 
			move on to the next CD.
		*/
		return this->rest->titlesBy(artistName);
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
		
	

	
	//Contract: titlesBy: EmptyInv, string->ListOfTitles
	//Purpose: To make a list of all titles by a given artist
	ListOfTitles* titlesBy(const char* artistName)
	//Template: this, artistName
	{
	/*Test 1
		none->titlesBy("U2")
			this = !
			artistName = "U2"
			ans = !
		return (new EmptyLst())
	*/
	
	return (new EmptyLst());
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
CD* The_Joshua_Tree = new CD("The Joshua Tree", "U2", 13.59, 78, "Pop");
CD* Achtung_Baby = new CD("Achtung Baby", "U2", 15.84, 54, "Pop");

Inventory* none =  new EmptyInv();
Inventory* one  = new OneMoreCD(We_Are_The_Night, none);
Inventory* two  = new OneMoreCD(Furr, one);
Inventory* three = new OneMoreCD(Achtung_Baby, two);
Inventory* four  = new OneMoreCD(Dont_Tread_On_Me, three);
Inventory* five  = new OneMoreCD(Crazy_Love, four);
Inventory* six  = new OneMoreCD(Morangos_Com_Acucar, five);
Inventory* seven = new OneMoreCD(The_Joshua_Tree, six);
Inventory* eight  = new OneMoreCD(Light_Years, seven);
Inventory* nine  = new OneMoreCD(The_Sun_And_The_Moon, eight);
Inventory* ten  = new OneMoreCD(Zooropa, nine);

ListOfTitles* noLst = new EmptyLst();


printf("The answer is\n  ");
one->titlesBy("Chemical Brothers")->show();
printf("\nbut it should be\n  ");
(new OneMoreTitle("We are The Night", new EmptyLst()))->show();
printf("\n");

printf("The answer is\n  ");
ten->titlesBy("U2")->show();
printf("\nbut it should be\n  ");
(new OneMoreTitle("Zooropa", new OneMoreTitle("The Joshua Tree", new OneMoreTitle("Achtung Baby", new EmptyLst()))))->show();
printf("\n");

printf("The answer is\n  ");
none->titlesBy("Chemical Brothers")->show();
printf("\nbut it should be\n  ");
(new EmptyLst())->show();
printf("\n");

/*Substitution
	1. one->titlesBy("Chemical Brothers")
	
	2. new OneMoreCD(We_Are_The_Night, none)->titlesBy("Chemical Brothers")
	
	3. if (!(streq(this->first->rightArtist(artistName), ""))){return (new OneMoreTitle(this->first->rightArtist(artistName), this->rest->titlesBy(artistName)));} else {return this->rest->titlesBy(artistName);}
	
	4. if (!(streq(new OneMoreCD(We_Are_The_Night, none)->first->rightArtist("Chemical Brothers"), ""))){return (new OneMoreTitle(new OneMoreCD(We_Are_The_Night, none)->first->rightArtist("Chemical Brothers"), new OneMoreCD(We_Are_The_Night, none)->rest->titlesBy("Chemical Brothers")));} else {return new OneMoreCD(We_Are_The_Night, none)->rest->titlesBy("Chemical Brothers");}
	
	5. if (!(streq(We_Are_The_Night->rightArtist("Chemical Brothers"), ""))){return (new OneMoreTitle(We_Are_The_Night->rightArtist("Chemical Brothers"), new OneMoreCD(none->titlesBy("Chemical Brothers"))));} else {return none->titlesBy("Chemical Brothers");}

	6. if (!(streq(if (streq(this->artist, artistName)){return this->title;} else {return "";}, ""))){return (new OneMoreTitle(We_Are_The_Night->rightArtist("Chemical Brothers"), new OneMoreCD(none->titlesBy("Chemical Brothers"))));} else {return none->titlesBy("Chemical Brothers");}
	
	7. if (!(streq(if (streq(new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->artist, artistName)){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->title;} else {return "";}, ""))){return (new OneMoreTitle(We_Are_The_Night->rightArtist("Chemical Brothers"), new OneMoreCD(none->titlesBy("Chemical Brothers"))));} else {return none->titlesBy("Chemical Brothers");}
	
	8. if (!(streq(if (streq("Chemical Brothers", "Chemical Brothers")){return "Chemical Brothers";} else {return "";}, ""))){return (new OneMoreTitle(We_Are_The_Night->rightArtist("Chemical Brothers"), new OneMoreCD(none->titlesBy("Chemical Brothers"))));} else {return none->titlesBy("Chemical Brothers");}
	
	9. if (!(streq(if (strcmp(l,r) == 0){return "Chemical Brothers";} else {return "";}, ""))){return (new OneMoreTitle(We_Are_The_Night->rightArtist("Chemical Brothers"), new OneMoreCD(none->titlesBy("Chemical Brothers"))));} else {return none->titlesBy("Chemical Brothers");}
	
	10. if (!(streq(if (strcmp("Chemical Brothers","Chemical Brothers") == 0){return "Chemical Brothers";} else {return "";}, ""))){return (new OneMoreTitle(We_Are_The_Night->rightArtist("Chemical Brothers"), new OneMoreCD(none->titlesBy("Chemical Brothers"))));} else {return none->titlesBy("Chemical Brothers");}

	11. if (!(streq(if (0 == 0){return "Chemical Brothers";} else {return "";}, ""))){return (new OneMoreTitle(We_Are_The_Night->rightArtist("Chemical Brothers"), new OneMoreCD(none->titlesBy("Chemical Brothers"))));} else {return none->titlesBy("Chemical Brothers");}
	
	12. if (!(streq(if true{return "Chemical Brothers";} else {return "";}, ""))){return (new OneMoreTitle(We_Are_The_Night->rightArtist("Chemical Brothers"), new OneMoreCD(none->titlesBy("Chemical Brothers"))));} else {return none->titlesBy("Chemical Brothers");}
	
	13. if (!(streq("Chemical Brothers", ""))){return (new OneMoreTitle(We_Are_The_Night->rightArtist("Chemical Brothers"), new OneMoreCD(none->titlesBy("Chemical Brothers"))));} else {return none->titlesBy("Chemical Brothers");}
	
	14. if (!(strcmp(l,r) == 0)){return (new OneMoreTitle(We_Are_The_Night->rightArtist("Chemical Brothers"), new OneMoreCD(none->titlesBy("Chemical Brothers"))));} else {return none->titlesBy("Chemical Brothers");}
	
	15. if (!(strcmp("Chemical Brothers", "") == 0)){return (new OneMoreTitle(We_Are_The_Night->rightArtist("Chemical Brothers"), new OneMoreCD(none->titlesBy("Chemical Brothers"))));} else {return none->titlesBy("Chemical Brothers");}
	
	16. if (!(1 == 0)){return (new OneMoreTitle(We_Are_The_Night->rightArtist("Chemical Brothers"), new OneMoreCD(none->titlesBy("Chemical Brothers"))));} else {return none->titlesBy("Chemical Brothers");}
	
	17. if (!(false)){return (new OneMoreTitle(We_Are_The_Night->rightArtist("Chemical Brothers"), new OneMoreCD(none->titlesBy("Chemical Brothers"))));} else {return none->titlesBy("Chemical Brothers");}
	
	18. if (true){return (new OneMoreTitle(We_Are_The_Night->rightArtist("Chemical Brothers"), new OneMoreCD(none->titlesBy("Chemical Brothers"))));} else {return none->titlesBy("Chemical Brothers");}
	
	19. return (new OneMoreTitle(We_Are_The_Night->rightArtist("Chemical Brothers"), none->titlesBy("Chemical Brothers"))); 
	
	20. return (new OneMoreTitle(if (streq(this->artist, artistName)){return this->title;} else {return "";}, none->titlesBy("Chemical Brothers"))); 
	
	21. return (new OneMoreTitle(if (streq(new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->artist, "Chemical Brothers")){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->title;} else {return "";}, none->titlesBy("Chemical Brothers"))); 
	
	22. return (new OneMoreTitle(if (streq("Chemical Brothers", "Chemical Brothers")){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->title;} else {return "";}, none->titlesBy("Chemical Brothers"))); 
	
	23. return (new OneMoreTitle(if (strcmp(l,r) == 0){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->title;} else {return "";}, new OneMoreCD(none->titlesBy("Chemical Brothers")))); 
	
	24. return (new OneMoreTitle(if (strcmp("Chemical Brothers", "Chemical Brothers") == 0){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->title;} else {return "";}, none->titlesBy("Chemical Brothers"))); 
	
	25. return (new OneMoreTitle(if (0 == 0){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->title;} else {return "";}, none->titlesBy("Chemical Brothers"))); 
	
	26. return (new OneMoreTitle(if (true){return new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->title;} else {return "";}, none->titlesBy("Chemical Brothers"))); 
	
	27. return (new OneMoreTitle(new CD("We Are The Night", "Chemical Brothers", 17.99, 6, "Electronic")->title, none->titlesBy("Chemical Brothers"))); 
	
	28. return (new OneMoreTitle(new CD("We Are The Night", none->titlesBy("Chemical Brothers")))); 
	
	29. return (new OneMoreTitle(new CD("We Are The Night", new EmptyInv()->titlesBy("Chemical Brothers")))); 

	30. return (new OneMoreTitle(new CD("We Are The Night", new EmptyLst()))); 
*/
}