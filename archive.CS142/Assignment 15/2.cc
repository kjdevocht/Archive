/*Kevin DeVocht assignment 15 exercise 2. Develop the 
function countProperDescendents which consumes a parent 
and produces the number of descendents, not including 
the parent.
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


  PersonInfo(const char* name0, const char* eyes0)
  {
    this->name = name0;
    this->eyes = eyes0;

  }
  
};



// A ListOfDFTs is either
//  mtLoDFTs
//  oneDFT
class ListOfDFTs {
public:

	//countProperDescendents: ListOfDFTs->int
  //purpose: to see how man decendents a given person has
  virtual int countProperDescendents() = 0;
  
    //countDescendents: ListOfDFTs->int
  //purpose: to return all of the decendents in a tree
  virtual int countDescendents() = 0;
};





// A mtLoDFTs is a
//  new mtLoDFTs ()
// where
class mtLoDFTs : public ListOfDFTs {
public:

  mtLoDFTs () {}
  
  //countProperDescendents: mtLoDFTs->int
  //purpose: to see how man decendents a given person has
  int countProperDescendents()
  {
  //Template: this
  /*Test 1
		mt->countProperDescendents
			this  = !
			ans = 0
		return 0
  */
  return 0;
	}
	
	
	//countDescendents: mtLoDFTs->int
  //purpose: to return all of the decendents in a tree
  int countDescendents()
  {
  //Template: this
  /*Test 1
		mt->countDescendents
			this  = !
			ans = 0
		return 0
  */
  return 0;
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
  
  //countProperDescendents: Person->int
  //purpose: to see how man decendents a given person has
  int countProperDescendents()
  //Template: this, this->info, this->children, this->children->countProperDescendents(), this->info->countProperDescendents()
  {
	/*Test 1
		AragornD->countProperDescendents()
			this = AragornI, EldarionD, new mtLoDFTs()
			this->info = AragornI
			this->children = EldarionD, new mtLoDFTs()
			this->info->countProperDescendents() = 0
			this->children->countProperDescendents() = 0
			ans = 1
		return this->children->countProperDescendents()
	*/
	
	/*Test 2
		FirielD->countProperDescendents()
			this = FirielI, AragornD, EldarionD, new mtLoDFTs()
			this->info = FirielI
			this->children = AragornD, EldarionD, new mtLoDFTs()
			this->info->countProperDescendents() = 0
			this->children->countProperDescendents() = 1
			ans = 2
		return this->children->countProperDescendents()
	*/
	
	/*Test 3
		OndoherD->countProperDescendents()
			this = OndoherI, ArtamirD, FaramirD, FirielD, AragornD, EldarionD, new mtLoDFTs()
			this->info = OndoherI
			this->children = ArtamirD, FaramirD, FirielD, AragornD, EldarionD, new mtLoDFTs()
			this->info->countProperDescendents() = 0
			this->children->countProperDescendents() = 4
			ans = 5
		return this->children->countProperDescendents()
	*/
	

	
	/*Test 4
		ArtamirD->countProperDescendents()
			this = ArtamirD, new mtLoDFTs()
			this->info = ArtamirI
			this->children = new mtLoDFTs()
			this->info->countProperDescendents() = 0
			this->children->countProperDescendents() = 0
			ans = 0
		return this->children->countProperDescendents()
	*/
	
	/*Generalize Tests
		We can see that all three have the same solution so just return
		this->children->countProperDescendents() because the parent will never
		count
	*/
	return this->children->countDescendents();
  }
  
      //countDescendents: Person->int
  //purpose: to return all of the decendents in a tree
  int countDescendents()
  {
  	/*Test 1
		AragornD->countDescendents()
			this = AragornI, EldarionD, new mtLoDFTs()
			this->info = AragornI
			this->children = EldarionD, new mtLoDFTs()
			this->info->countDescendents() = 0
			this->children->countDescendents() = 2
			ans = 3
		return 1 + this->children->countDescendents()
	*/
	
	/*Test 2
		OndoherD->countDescendents()
			this = OndoherI, ArtamirD, FaramirD, FirielD, AragornD, EldarionD, new mtLoDFTs()
			this->info = OndoherI
			this->children = ArtamirD, FaramirD, FirielD, AragornD, EldarionD, new mtLoDFTs()
			this->info->countDescendents() = 0
			this->children->countDescendents() = 5
			ans = 6
		return 1 + this->children->countDescendents()
	*/
	
	return 1 + this->children->countDescendents();
  
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
  
  //countDescendents: oneDFT->int
  //purpose: to return all of the decendents in a tree
  int countDescendents()
  //Template: this, this->first, this->rest, this->first->countDescendents(), this->rest->countDescendents()
	{  
	/*Test 1
		OndoherK3->countDescendents()
			this = FaramirD, mt
			this->first = FaramirD
			this->rest = mt
			this->first->countDescendents() = 1
			this->rest->countDescendents() = 0
			ans =1
		return this->first->countDescendents()
	*/
	
	/*Test 2
		OndoherK->countDescendents()
			this = FirielD, OndoherK2
			this->first = FirielD
			this->rest = OndoherK2
			this->first->countDescendents() = 3
			this->rest->countDescendents() = 2
			ans =5
		return this->first->countDescendents()+ this->rest->countDescendents()
	*/
	
	  return this->first->countDescendents()+ this->rest->countDescendents();
	  

	 } 
  //countProperDescendents: oneDFT->int
  //purpose: to see how man decendents a given person has
  int countProperDescendents()
  //Template: this, this->first, this->rest, this->first->countProperDescendents(), this->rest->countProperDescendents(), this->first->countDescendents(), this->rest->countDescendents()
  {
  
	/*Test 1
		OndoherK->countProperDescendents()
			this = FirielD, OndoherK2
			this->first = FirielD
			this->rest = new OndoherK2
			this->first->countProperDescendents() = 2
			this->rest->countProperDescendents() = 0
			this->first->countDescendents() = 3 
			this->rest->countDescendents() = 2
			ans = 2
		return this->first->countProperDescendents() + this->rest->countProperDescendents();
	*/
	
	/*Test 2
		OndoherK2->countProperDescendents()
			this = ArtamirD, OndoherK3
			this->first = ArtamirD
			this->rest = OndoherK3
			this->first->countProperDescendents() = 0
			this->rest->countProperDescendents() = 0
			ans = 3
		return this->first->countProperDescendents()+ this->rest->countProperDescendents();
	*/
	
	/*Generalize Tests 1 and 2
		We can see that when we get to this stage in the program the count should
		at least be one so we should just add one to the equation.  Next we add the first
		which makes sure to add all the decendants in the same generation and we know 
		then add the rest of list
	*/
	
	
  
  return this->first->countProperDescendents() + this->rest->countProperDescendents();
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
  ListOfDFTs* FirielK = (new oneDFT( AragornD, mt ));

  Person* OndoherD = new Person( OndoherI, OndoherK );

  
    printf ( "The answer is %d, but should be %d\n",
           AragornD->countProperDescendents(),
           1 ) ;
		   
	printf ( "The answer is %d, but should be %d\n",
           OndoherD->countProperDescendents(),
           5 ) ;
	printf ( "The answer is %d, but should be %d\n",
           FirielD->countProperDescendents(),
           2 ) ;
		   
	printf ( "The answer is %d, but should be %d\n",
           OndoherK->countProperDescendents(),
           2 ) ;

	printf ( "The answer is %d, but should be %d\n",
           OndoherK2->countProperDescendents(),
           0 ) ;

	printf ( "The answer is %d, but should be %d\n",
           OndoherK3->countProperDescendents(),
           0 ) ;		   

  return 0;
  
	
}