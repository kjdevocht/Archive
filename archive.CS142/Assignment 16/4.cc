/*Kevin DeVocht assignment 15 exercise 4. Write a function called 
line that takes a list of information about people and returns a family 
tree where each person in the list is the ancestor of everyone to their right. 
(For example, the list The Boss:Big Boss:Solidus:Raiden would construct a 
portion of the Metal Gear trained-by family tree.)
*/



#include <stdio.h>
#include <math.h>
#include <string.h>

#include <stdio.h>
#include <math.h>
#include <string.h>

// booleanToString : boolean -> string
// Purpose: convert a boolean into a string for printing
const char* booleanToString ( bool it ) {
  if ( it ) { return "true"; } else { return "false"; }
}



// A PersonInfo is a
//  new PersonInfo( name, eyes, age, livingness )
// where
//  name is a string
//  eyes is a string
//  age is a int
//  livingness is a bool
class PersonInfo {
public:
  const char* name;
  const char* eyes;
  int age;
  bool livingness;

  PersonInfo(const char* name0, const char* eyes0, int age0, bool livingness0){
    this->name = name0;
    this->eyes = eyes0;
    this->age = age0;
    this->livingness = livingness0;
  }
  
	int show () {
	printf("new PersonInfo(\"%s\", \"%s\", %d, %s)",
		   this->name,
		   this->eyes,
		   this->age,
		   booleanToString(this->livingness));
	return 0;
	}
};





// A ListOfDFTs is either
//  mtLoDFTs
//  oneDFT
class ListOfDFTs {
public:
	virtual int show () = 0;
};

// A mtLoDFTs is a
//  new mtLoDFTs ()
// where
class mtLoDFTs : public ListOfDFTs {
public:
	mtLoDFTs () {}

	int show () {
	printf ("new mtLoDFTs()");
	}
};



// A DescendantFamilyTree (DFT) is
//  new Person( info, children )
// where
//  info is a PersonInfo
//  children is a ListOfDFTs
class Person {
public:
	PersonInfo* info;
	ListOfDFTs* children;

	Person ( PersonInfo* info0 ,
		   ListOfDFTs* children0 ) {
	this->info = info0;
	this->children = children0;
	}

	int show () {
	printf("new Person(");
	this->info->show();
	printf(",");
	this->children->show();
	printf(")");
	return 0;
	}
};



// A oneDFT is a
//  new oneDFT( first, rest )
// where
//  first is a DFT
//  rest is a ListOfDFTs
class oneDFT : public ListOfDFTs {
public:
	Person* first;
	ListOfDFTs* rest;

	oneDFT( Person* first0, ListOfDFTs* rest0 ) {
	this->first = first0;
	this->rest = rest0;
	}

	int show () {
	printf("new oneDFT(");
	this->first->show();
	printf(",");
	this->rest->show();
	printf(")");
	}
};

// A ListofInfo is either
//  EmptyLOI
//  OneLOI
class ListOfInfo {
public:
	virtual int show () = 0;
	
	//line: ListOfInfo->ListOfDFTs
	//Purpose: to make a family tree out of a list of info
	virtual ListOfDFTs* line() = 0;
};

// An EmptyLOI is a
//  new EmptyLOI()
// where
class EmptyLOI : public ListOfInfo {
public:

  EmptyLOI () {}

  int show () {
    printf("new EmptyLOI()");
    return 0;
  }
  
  	//line: ListOfInfo->mtLoDFTs
	//Purpose: to make a family tree out of a list of info
	ListOfDFTs* line()
	//Template: this
	{
	/*Test 1
		mt->line()
		this = !
		ans = !
	return (new mtLoDFTs());
	*/
	return (new mtLoDFTs());
	}
};


// A OneLOI is a
//  new OneLOI (first, rest)
// where
//  first is a PersonInfo
//  rest is a ListOfInfo
class OneLOI : public ListOfInfo {
public:
  PersonInfo* first;
  ListOfInfo* rest;

  OneLOI( PersonInfo* first0, ListOfInfo* rest0 ) {
    this->first = first0;
    this->rest = rest0;
  }

  int show () {
    printf("new OneLOI(");
    this->first->show();
    printf(",");
    this->rest->show();
    printf(")");
    return 0;
  }
  
    //line: ListOfInfo->ListOfDFTs
	//Purpose: to make a family tree out of a list of info
	ListOfDFTs* line()
	//Template: this, this->first, this->rest, this->rest->line()
	{
	/*Test 1
		Jenny->line()
			this = JennyI, Empty
			this->fist = JennyI
			this->rest = Empty
			this->rest->line() = mt
			ans = JennyI, mt
		return new oneDFT(this->first, this->rest->line())
	*/
	
	/*Test 2
		Megan->line()
			this = MeganI, Jenny
			this->fist = MeganI
			this->rest = Jenny
			this->rest->line() = JennyD(a person because she now has an mt), mt
			ans = MeganI, JennyD, mt
		return new oneDFT(new Person(this->first, new mtLoDFTs()), this->rest->line())
	*/
	
	/*Generalize Tests 1 and 2
		we see that we want to add a new listofDFT here and that we add the other people
		in the listOfInfo to this new tree
	*/
	return (new oneDFT(new Person(this->first, this->rest->line()), new mtLoDFTs()));
	}
	
	
};


// main : -> number
int main () {


	PersonInfo* SadeI = new PersonInfo("Sade", "Blue", 4, true);
	PersonInfo* MadalynI = new PersonInfo("Madalyn", "Green", 2, false);
	PersonInfo* MeganI = new PersonInfo("Megan", "Blue", 25, true);
	PersonInfo* JennyI = new PersonInfo("Jenny", "Blue", 23, true); 

	ListOfInfo* Empty = new EmptyLOI();
	ListOfInfo* Jenny = new OneLOI(JennyI, Empty);
	ListOfInfo* Megan = new OneLOI(MeganI, Jenny);
	ListOfInfo* Madalyn = new OneLOI(MadalynI, Megan);

	
	printf("\nthe answer is \n");
	Madalyn->line()->show();	
	printf("\nbut should be \n Madalyn, Megan, Jenny, mt() \n");
	
	printf("\nthe answer is \n");
	Empty->line()->show();	
	printf("\nbut should be \n mt() \n");
	
	printf("\nthe answer is \n");
	Jenny->line()->show();	
	printf("\nbut should be \n Jenny, mt() \n");
return 0;

/*Substitution
	I RAN OUT OF TIME.  I HOPE THIS DOES NOT COME BACK TO HAUNT ME!
*/
}