/*Kevin DeVocht assignment 15 exercise3. Develop the function generationDescendents 
which consumes a parent and a generation number and produces a list of all information 
about descendents in that generation.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>

// A ListofInfo is either
//  EmptyLOI
//  OneLOI
class ListOfInfo {
public:
  virtual int show () = 0;

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
};

// A PersonInfo is a
//  new PersonInfo( name, eyes)
// where
//  name is a string
//  eyes is a string
class PersonInfo {
public:
  const char* name;
  const char* eyes;

  PersonInfo(const char* name0, const char* eyes0){
    this->name = name0;
    this->eyes = eyes0;
  }
  
  int show () {
    printf("new PersonInfo(\"%s\", \"%s\")",
           this->name,
           this->eyes);
    return 0;
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
};


// A ListOfDFTs is either
//  mtLoDFTs
//  oneDFT
class ListOfDFTs {
public:

  //howFarRemoved: ListOfDFTs->int
  //Purpose: to see how many generations it takes to get to a blue eyed person
  virtual double howFarRemoved() = 0;

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

  generationDescendents
   
  //howFarRemoved: Person->int
  //Purpose:to see how many generations it takes to get to a blue eyed person
  double howFarRemoved ()
  //Template: this, this->info, this->children, this->children->howFarRemoved(), this->info->howFarRemoved()
  {
	if (this->info->generationDescendents(0) == 0)
	{
	  /*Test 1
		OndoherD->howFarRemoved()
			this = OndoherI, FirielD, ArtamirD, FaramirD, mt
			this->info = OndoherI
			this->children = FirielD, ArtamirD, FaramirD, mt
			this->children->howFarRemoved() = 3
			this->info->howFarRemoved() = 0
			ans = 0
		return this->info->howFarRemoved();
	  */
	 return 0;
	}
	 else 
	 {
	 	/*Test 2
			AragornD->howFarRemoved()
				this = AragornD, EldarionD, mtLoDFTs()
				this->info = AragornD
				this->children = EldarionD, mtLoDFTs()
				this->children->howFarRemoved() = 0
				this->info->howFarRemoved() = 1
				ans = 1
			return this->children->howFarRemoved();
		*/
		
		
		/*Test 3
			FirielD->howFarRemoved()
				this = FirielI, AragornD, EldarionD, mtLoDFTs()
				this->info = FirielI
				this->children = AragornD, EldarionD, mtLoDFTs()
				this->info->howFarRemoved() = 1
				this->children->howFarRemoved() = 1
				ans = 2
			return this->info->howFarRemoved() + this->children->howFarRemoved();
		*/
		
		/*Generalize Tests 1 - 3
			We can tell from the answers that we we need to check the first person
			if they are not blue eyed, then add 1 and check the next generation.
			we do this by checking this->info's eye color and stopping if they are blue
			or continuing on if they are not.
		*/
		
	  return this->info->howFarRemoved() + this->children->howFarRemoved();
	  }  
  }
};








// A mtLoDFTs is a
//  new mtLoDFTs ()
// where
class mtLoDFTs : public ListOfDFTs {
public:

  mtLoDFTs () {}
  


  //howFarRemoved: mtLoDFTs->double
  //Purpose: to see how many generations it takes to get to a blue eyed person
  //Template: this
  double howFarRemoved()
  {
	return INFINITY;
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
  


   //howFarRemoved: oneDFT->int
  //Purpose: to see how many generations it takes to get to a blue eyed person
  //Template: this, this->first, this->rest, this->rest->howFarRemoved(), this->first->howFarRemoved()
  double howFarRemoved()
  {
	

	/*Test 1
		EldarionD->howFarRemoved()
			this = EldarionI, mtLoDFTs()
			this->first = EldarionI
			this->rest = mtLoDFTs()
			this->rest->howFarRemoved() = INFINITY
			this->first->howFarRemoved() = 0
			ans = 0
		return this->first->howFarRemoved()
	*/


	/*Test 2
		AragornD->howFarRemoved()
			this = AragornI, EldarionD, mtLoDFTs()
			this->first = AragornI
			this->rest = EldarionD, mtLoDFTs()
			this->rest->howFarRemoved() = 0
			this->first->howFarRemoved() = 1
			ans = 1
		return this->first->howFarRemoved()
	*/
	
	/*Test 3
		ArtamirD->howFarRemoved()
			this = ArtamirI, mtLoDFTs()
			this->first = ArtamirI
			this->rest = mtLoDFTs()
			this->rest->howFarRemoved() = INFINITY
			this->first->howFarRemoved() = 1
			ans = INFINITY
		return this->first->howFarRemoved() + INFINITY
	*/
	
	/*Test 4
		FirielD->howFarRemoved()
			this = FirielI, AragornD, EldarionD, mtLoDFTs()
			this->first = FirielI
			this->rest = AragornD, EldarionD, mtLoDFTs()
			this->rest->howFarRemoved() = 1
			this->first->howFarRemoved() = 1
			ans = 2
		return this->first->howFarRemoved() + 1
	*/
	
	
	
	/*Generalize Tests 1 and 2
		We can see that in all for tests that we must return this->first->howFarRemoved()
		We also see in 3 and four that there are some other things that in the final answer
		need to be added to the total.  But we also know that in another function it will
		call this function on each decendant of each person so we really only need to pass the
		value of this->rest->howFarRemoved() and later on the other values will be added
		this way we avoid going all the way through the tree and adding INFINITY to everything.
	*/
return this->first->howFarRemoved();
  }
};







// main : -> number
int main () {
 

  PersonInfo* OndoherI = new PersonInfo("Ondoher", "Blue");
  PersonInfo* FirielI = new PersonInfo("Firiel", "Black");
  PersonInfo* ArtamirI = new PersonInfo("Artamir", "Yellow");
  PersonInfo* FaramirI = new PersonInfo("Faramir", "Green");
  PersonInfo* AragornI = new PersonInfo("Aragorn", "Green");
  PersonInfo* EldarionI = new PersonInfo("Eldarion", "Blue");

  Person* EldarionD = new Person( EldarionI, (new mtLoDFTs()));
  Person* ArtamirD = new Person( ArtamirI, (new mtLoDFTs()));
  Person* FaramirD = new Person( FaramirI, (new mtLoDFTs()));

  Person* AragornD = new Person( AragornI, (new oneDFT( EldarionD, (new mtLoDFTs()))));

  Person* FirielD = new Person( FirielI, (new oneDFT( AragornD, (new mtLoDFTs()))));

  ListOfDFTs* mt = (new mtLoDFTs());
  ListOfDFTs* OndoherK3 = (new oneDFT( FaramirD, mt));
  ListOfDFTs* OndoherK2 = (new oneDFT( ArtamirD, OndoherK3));
  ListOfDFTs* OndoherK = (new oneDFT( FirielD, OndoherK2 ));
  Person* OndoherD = new Person( OndoherI, OndoherK );



 
  printf ( "The answer is %f, but should be %f\n",
           OndoherD->howFarRemoved(),
           0.0 ) ;
		   
  printf ( "The answer is %f, but should be %f\n",
           AragornD->howFarRemoved(),
           1.0 ) ;
		   
  printf ( "The answer is %f, but should be %f\n",
           ArtamirD->howFarRemoved(),
           INFINITY ) ;
  printf ( "The answer is %f, but should be %f\n",
           FirielD->howFarRemoved(),
           2.0 ) ;

  return 0;
}