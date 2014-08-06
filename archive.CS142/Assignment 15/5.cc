/*Kevin DeVocht assignment 15 exercise 5. Write a function 
countGenerations which counts the number of generations in a 
descendant family tree.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>


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
};



// A ListOfDFTs is either
//  mtLoDFTs
//  oneDFT
class ListOfDFTs {
public:

  //countGenerations: ListOfDFTs->int
  //Purpose: to see how many generations are in a tree
  virtual int countGenerations() = 0;
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

   //countGenerations: Person->int
  //Purpose: to see how many generations are in a tree
  int countGenerations()
  //Template: this, this->info, this->children, this->children->countGenerations()
  {
	/*Test 1
		FirielD->countGenerations()
		this = FirielI, AragornD, mtLoDFTs()
		this->info = FirielI
		this->children = AragornD, mtLoDFTs()
		this->children->countGenerations() = 2
		ans =3
	return 1 + this->children->countGenerations()
	*/
	
	/*Test 2
		FirielD->countGenerations()
		this = FirielI, AragornD, mtLoDFTs()
		this->info = FirielI
		this->children = AragornD, mtLoDFTs()
		this->children->countGenerations() = 2
		ans =3
	return 1 + this->children->countGenerations()
	*/
	
	/*Generalize Tests 1 and 2
		we see that they have the same return so keep up the good work
	*/
	
  return 1 + this->children->countGenerations();
   }
};








// A mtLoDFTs is a
//  new mtLoDFTs ()
// where
class mtLoDFTs : public ListOfDFTs {
public:

  mtLoDFTs () {}
  
  //countGenerations: mtLoDFTs->int
 //Purpose: to see how many generations are in a tree
 //Template: this
  int countGenerations()
  {
  /*Test 1
	mt->countGenerations()
		this = !
		ans = 0
	return 0
  */
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
  
  //countGenerations: oneDFT->int
 //Purpose: to see how many generations are in a tree
 //Template: this, this->first, this->rest, this->first->countGenerations(), this->rest->countGenerations()
  int countGenerations()
  {
  if (this->first->countGenerations() > this->rest->countGenerations())
  {
    /*Test 1
	OndoherK3->countGenerations()
		this = FaramirD, mt
		this->first = FaramirD
		this->rest = mt
		this->first->countGenerations() = 1
		this->rest->countGenerations() = 0
		ans = 1
	return this->first->countGenerations() + this->rest->countGenerations()
  */
  return this->first->countGenerations();
  }

  else
  {
   /*Test 2
	OndoherK2->countGenerations()
		this = ArtamirD, FaramirD, mt
		this->first = ArtamirD
		this->rest = FaramirD, mt
		this->first->countGenerations() = 1
		this->rest->countGenerations() = 1
		ans = 1
	return this->first->countGenerations()
  */
  
  /*Distinguish Tests 1 and 2
	we want to return the longest branch of the tree because that will have the most gens
	so we need to compare first to rest and return the bigger one
  */
	return this->rest->countGenerations();
	}
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
  ListOfDFTs* OndoherK2 = (new oneDFT( FirielD, OndoherK3));
  ListOfDFTs* OndoherK = (new oneDFT( ArtamirD, OndoherK2 ));
  Person* OndoherD = new Person( OndoherI, OndoherK );

  printf ( "The answer is %d, but should be %d\n",
           OndoherD->countGenerations(),
           4 ) ;
		   
  printf ( "The answer is %d, but should be %d\n",
           AragornD->countGenerations(),
           2 ) ;
		   
  printf ( "The answer is %d, but should be %d\n",
           ArtamirD->countGenerations(),
           1 ) ;
  printf ( "The answer is %d, but should be %d\n",
           FirielD->countGenerations(),
           3 ) ;
  printf ( "The answer is %d, but should be %d\n",
           OndoherK->countGenerations(),
           3 ) ;
		   
  printf ( "The answer is %d, but should be %d\n",
           OndoherK2->countGenerations(),
           3 ) ;
  printf ( "The answer is %d, but should be %d\n",
           OndoherK3->countGenerations(),
           1 ) ;
  return 0;
  
 /*Substitution
	1. AragornD->countGenerations()
	
	2. new Person( AragornI, (new oneDFT( EldarionD, (new mtLoDFTs()))))->countGenerations()
	
	3.  return 1 + this->children->countGenerations();
	
	4.  return 1 + new Person( AragornI, (new oneDFT( EldarionD, (new mtLoDFTs()))))->children->countGenerations();
	
	5.  return 1 + (EldarionD, (new mtLoDFTs()))->countGenerations();
	
	6.  return 1 + if (this->first->countGenerations() > this->rest->countGenerations()){return this->first->countGenerations();}else{return this->rest->countGenerations();};
	
	7.  return 1 + if ( (EldarionD, (new mtLoDFTs()))->first->countGenerations() >  (EldarionD, (new mtLoDFTs()))->rest->countGenerations()){return  (EldarionD, (new mtLoDFTs()))->first->countGenerations();}else{return  (EldarionD, (new mtLoDFTs()))->rest->countGenerations();};
	  
	8.  return 1 + if ( (EldarionD->countGenerations() > (new mtLoDFTs())->countGenerations()){return  (EldarionD->countGenerations();}else{return  (new mtLoDFTs())->countGenerations();};  
	  
	9.  return 1 + if ( (new Person( EldarionI, (new mtLoDFTs()))->countGenerations() > 0){return  (EldarionD->countGenerations();}else{return  0;};  
	  
	10.  return 1 + if ( 1 + this->children->countGenerations() > 0){return  (EldarionD->countGenerations();}else{return  0;};  
	  
	11.  return 1 + if ( 1 + (new Person( EldarionI, (new mtLoDFTs()))->children->countGenerations() > 0){return  (EldarionD->countGenerations();}else{return  0;};  
	  
	12.  return 1 + if ( 1 + (new mtLoDFTs())->countGenerations() > 0){return  (EldarionD->countGenerations();}else{return  0;};  
	  
	13.  return 1 + if ( 1 + 0 > 0){return  (EldarionD->countGenerations();}else{return  0;};  
	  
	14.  return 1 + EldarionD->countGenerations(); 
	 
	15.  return 1 + 1 + this->children->countGenerations(); 
	
	16.  return 1 + 1 + new Person( EldarionI, (new mtLoDFTs()))->children->countGenerations(); 
	
	17.  return 1 + 1 + (new mtLoDFTs())->countGenerations(); 
	
	18.  return 1 + 1 + 0; 
	
	19.  return 2; 
	*/
}