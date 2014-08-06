/*Kevin DeVocht Assignment 13 Exercise 6. Write a function, uniqueCategories, 
that consumes an inventory and produces a list of categories. 
The result must not contain duplicates.
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
		
	//Contract: sameCategoryHuh: CD CD->string
	//Purpose: To see if two given CD's have the same category
	const char* sameCategoryHuh(ListOfCategories* The_List)
	//Template: this, this->title, this->artist, this->price, this->numInStock, this->category, The_List
	{
		if (!(streq(this->category, The_List->checkList(this->category))))
		{
		/*Test 1
			The_Sun_And_The_Moon->sameCategoryHuh(new EmptyLst())
				this->category = "Rock"
				The_List = !
				ans = "Rock"
			return (new OneMoreCategory("Rock", new EmptyLst()))
		*/
		return this->category;
		}
		
		else
		{
		/*Test 2
			Zooropa->sameCategoryHuh(new OneMoreCategory("Rock", new EmptyLst()))
				this->category = "Pop"
				second->category = "Rock"
				ans = "Pop"
			return (new OneMoreCategory("Pop",(new OneMoreCategory("Rock", new EmptyLst()))))
		*/
		
		/*Generalize Tests 1 and 2
			We can see that again we will need to look deeper to compare 
			the category with the list of categories so we will have to 
			add an auxillary function in the ListOfCategories class.  
			This function will consume a string and a ListOfCategories 
			and return a string
		*/
		return "";
		}
		
	}
		
};







//A ListOfCategories is
// OneMoreCategory
// EmptyLst
class ListOfCategories
{
	public:
		virtual int show () = 0;
		
		//Contract: checkList: string ListOfCategories->string
		//Purpose: To see if a given string is in the list
		virtual const char* checkList (const char* categoryCheck) = 0;
};


//A OneMoreCategory is
// a new OneMoreCategory(first, rest)
//where 
//first is a string
//and rest is a ListOfCategories
class OneMoreCategory : public ListOfCategories
{
	public:
		const char* first;
		ListOfCategories* rest;
		
	OneMoreCategory(const char* first0, ListOfCategories* rest0)
	{
		this->first = first0;
		this->rest = rest0;
	}
	

	
	int show () 
	{
    printf("new OneMoreCategory(\"%s", this->first);
    printf(", ");
    this->rest->show();
	printf(")");
    return 0;
	}

	
	
	//Contract: checkList: string OneMoreCategory->string
	//Purpose: To see if a given string is in the list
	const char* checkList (const char* categoryCheck)
	//Template: this->first, this->rest categoryCheck
	{
		if(!(streq(this->first, categoryCheck)))
		{
		return categoryCheck;
		}
		else
		{
		return this->rest->checkList(categoryCheck);
		}
	}
};

//An EmptyLst is
// a new EmptyLst()
//where there is nothing because it is empty
class EmptyLst : public ListOfCategories
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
	
	//Contract: checkList: string EmptyLst->string
	//Purpose: To see if a given string is in the list
	const char* checkList (const char* categoryCheck)
	{
	return "";
	}
};





// An Inventory is
//  OneMoreCD
//  EmptyInv
class Inventory 
{
	public:
		// Contract: uniqueCategories: Inventory->ListOfCategories
		//Purpose: To return all the different categories in an Inventory
		virtual ListOfCategories* uniqueCategories() = 0;

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
	
	// Contract: uniqueCategories: OneMoreCD->ListOfCategories
	//Purpose: To return all the different categories in an Inventory
	ListOfCategories* uniqueCategories()
	//Template: this, this->first, this->rest, this->rest->uniqueCategories()
	{
		if(!(streq(this->first->sameCategoryHuh(this->rest->uniqueCategories()),"")))
		{
		/*Test 1
			one->uniqueCategories()
				this = We_Are_The_Night, none
				this->first = We_Are_The_Night
				this->rest = none
				ans = "Electronic"
			return (new OneMoreCategory("Electronic", new EmptyList()))
		*/
		return (new OneMoreCategory(this->first->sameCategoryHuh(this->rest->uniqueCategories())));
		}
		
		else
		{
		/*Test 2
			six->uniqueCategories()
				this = Light_Years, Morangos_Com_Acucar, Crazy_Love, Dont_Tread_On_Me, Furr, We_Are_The_Night, none
				this->first = Light_Years
				this->rest = Morangos_Com_Acucar, Crazy_Love, Dont_Tread_On_Me, Furr, We_Are_The_Night, none
				ans = "Rock", "World", "Easy Listening", "Indie", "Electronic"
			return (new OneMoreCategory("Rock", new OneMoreCategory("World", new OneMoreCategory("Listening", new OneMoreCategory("Indie", new OneMoreCategory("Electronic", new EmptyList()))))
		*/
		
		/*Generalize Tests 1 and 2
			We can see that we need to use an auxillary function to look deeper.  
			This function should consume two CDs and return a string it should 
			also compare categories of the two CDs and if they are different 
			return this->first's category otherwise return an empty string 
			because at the end it will compare the last item in the list to 
			an EmptyLst which will never be the same and it will return the 
			last item's category.
		*/
		return this->rest->uniqueCategories();
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
		
	// Contract: uniqueCategories: EmptyInv->ListOfCategories
	//Purpose: To return all the different categories in an Inventory
	ListOfCategories* uniqueCategories()
	//Template: this
	{
	/*Test 1
		none->uniqueCategories()
			this = !
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


Inventory* none =  new EmptyInv();
Inventory* one  = new OneMoreCD(We_Are_The_Night, none);
Inventory* two  = new OneMoreCD(Furr, one);
Inventory* three  = new OneMoreCD(Dont_Tread_On_Me, two);
Inventory* four  = new OneMoreCD(Crazy_Love, three);
Inventory* five  = new OneMoreCD(Morangos_Com_Acucar, four);
Inventory* six  = new OneMoreCD(Light_Years, five);
Inventory* seven  = new OneMoreCD(The_Sun_And_The_Moon, six);
Inventory* eight  = new OneMoreCD(Zooropa, seven);

ListOfCategories* noLst = new EmptyLst();


printf("The answer is\n  ");
one->uniqueCategories()->show();
printf("\nbut it should be\n  ");
(new OneMoreCategory("Electronic", new EmptyLst()))->show();
printf("\n");

printf("The answer is\n  ");
eight->uniqueCategories()->show();
printf("\nbut it should be\n  ");
(new OneMoreCategory("Pop", new OneMoreCategory("Rock", new OneMoreCategory("World", new OneMoreCategory("Easy Listening", new OneMoreCategory("Indie", new OneMoreCategory("Electronic", new EmptyLst())))))))->show();
printf("\n");

printf("The answer is\n  ");
none->uniqueCategories()->show();
printf("\nbut it should be\n  ");
(new EmptyLst())->show();
printf("\n");


}