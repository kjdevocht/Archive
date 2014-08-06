/*Kevin DeVocht assignment 15 exercise 6. Generalize 
howFarRemoved so it takes an eye color argument and 
does not always check for blue-eye descendents.
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
  
  //howFarRemoved: PersonInfo->double
  //Purpose: to see if a person has blue eyes
  double howFarRemoved(const char* color)
  {
  // pretty obvious answers here
	if (strcmp(this->eyes, color) == 0)
	{
	return 0;
	}
	else
	{
	return 1;
	}
  }
};



// A ListOfDFTs is either
//  mtLoDFTs
//  oneDFT
class ListOfDFTs {
public:

  //howFarRemoved: ListOfDFTs->int
  //Purpose: to see how many generations it takes to get to a blue eyed person
  virtual double howFarRemoved(const char* color) = 0;
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

  
   
  //howFarRemoved: Person->int
  //Purpose:to see how many generations it takes to get to a blue eyed person
  double howFarRemoved (const char* color)
  //Template: this, this->info, this->children, this->children->howFarRemoved(), this->info->howFarRemoved()
  {
	if (this->info->howFarRemoved(color) == 0)
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
		
	return this->info->howFarRemoved(color) + this->children->howFarRemoved(color);
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
  double howFarRemoved(const char* color)
  {
  /*Test 1
	mt->howFarRemoved(color)
		this = !
		ans = !
	return INFINITY
  */
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
  double howFarRemoved(const char* color)
  {
	
	if(this->first->howFarRemoved(color) <this->rest->howFarRemoved(color))
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
	return this->first->howFarRemoved(color);
	}
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
	else
	{
	
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
	return this->rest->howFarRemoved(color);
	}
  }
};







// main : -> number
int main () {
 

  PersonInfo* OndoherI = new PersonInfo("Ondoher", "Green");
  PersonInfo* FirielI = new PersonInfo("Firiel", "Black");
  PersonInfo* ArtamirI = new PersonInfo("Artamir", "Yellow");
  PersonInfo* FaramirI = new PersonInfo("Faramir", "Green");
  PersonInfo* AragornI = new PersonInfo("Aragorn", "Green");
  PersonInfo* EldarionI = new PersonInfo("Eldarion", "Blue");
  PersonInfo* BobI = new PersonInfo("Bob", "Blue");

  Person* EldarionD = new Person( EldarionI, (new mtLoDFTs()));
  Person* BobD = new Person( BobI, (new mtLoDFTs()));
  Person* FaramirD = new Person( FaramirI, (new mtLoDFTs()));
  Person* ArtamirD = new Person( ArtamirI, (new oneDFT( BobD, (new mtLoDFTs()))));

  Person* AragornD = new Person( AragornI, (new oneDFT( EldarionD, (new mtLoDFTs()))));

  Person* FirielD = new Person( FirielI, (new oneDFT( AragornD, (new mtLoDFTs()))));

  ListOfDFTs* mt = (new mtLoDFTs());
  ListOfDFTs* OndoherK3 = (new oneDFT( FaramirD, mt));
  ListOfDFTs* OndoherK2 = (new oneDFT( ArtamirD, OndoherK3));
  ListOfDFTs* OndoherK = (new oneDFT( FirielD, OndoherK2 ));
  Person* OndoherD = new Person( OndoherI, OndoherK );



 
  printf ( "The answer is %f, but should be %f\n",
           OndoherD->howFarRemoved("Black"),
           1.0 ) ;
		   
  printf ( "The answer is %f, but should be %f\n",
           AragornD->howFarRemoved("Red"),
           INFINITY ) ;
		   
  printf ( "The answer is %f, but should be %f\n",
           ArtamirD->howFarRemoved("Blue"),
           1.0 ) ;
  printf ( "The answer is %f, but should be %f\n",
           FirielD->howFarRemoved("Blue"),
           2.0 ) ;

  printf ( "The answer is %f, but should be %f\n",
           OndoherD->howFarRemoved("Blue"),
           2.0 ) ;
		   
	   
		   
  return 0;
  
/* Substitution
	1. AragornD->howFarRemoved("Red")
	
	2. new Person( AragornI, (new oneDFT( EldarionD, (new mtLoDFTs()))))->howFarRemoved("Red")
	
	3. if (this->info->howFarRemoved(color) == 0){return 0;}else {return this->info->howFarRemoved(color) + this->children->howFarRemoved(color);}  

	4. if (new Person( AragornI, (new oneDFT( EldarionD, (new mtLoDFTs()))))->info->howFarRemoved(color) == 0){return 0;}else {return new Person( AragornI, (new oneDFT( EldarionD, (new mtLoDFTs()))))->info->howFarRemoved(color) + new Person( AragornI, (new oneDFT( EldarionD, (new mtLoDFTs()))))->children->howFarRemoved(color);}  
	
	5. if (AragornI->howFarRemoved("Red") == 0){return 0;}else {return AragornI->howFarRemoved("Red") + ( EldarionD, (new mtLoDFTs()))->howFarRemoved("Red");}  
	
	6. if (if (strcmp(this->eyes, color) == 0){return 0;}else{return 1;} == 0){return 0;}else {return AragornI->howFarRemoved("Red") + ( EldarionD, (new mtLoDFTs()))->howFarRemoved("Red");}  
	
	7. if (if (strcmp(new PersonInfo("Aragorn", "Green")->eyes, color) == 0){return 0;}else{return 1;} == 0){return 0;}else {return AragornI->howFarRemoved("Red") + ( EldarionD, (new mtLoDFTs()))->howFarRemoved("Red");}  
	
	8. if (if (strcmp("Green"), "Red") == 0){return 0;}else{return 1;} == 0){return 0;}else {return AragornI->howFarRemoved("Red") + ( EldarionD, (new mtLoDFTs()))->howFarRemoved("Red");}  
	
	9. if (if (1 == 0){return 0;}else{return 1;} == 0){return 0;}else {return AragornI->howFarRemoved("Red") + ( EldarionD, (new mtLoDFTs()))->howFarRemoved("Red");}  
	
	10 if (if (false){return 0;}else{return 1;} == 0){return 0;}else {return AragornI->howFarRemoved("Red") + ( EldarionD, (new mtLoDFTs()))->howFarRemoved("Red");}  
	
	11 if (if  1 == 0){return 0;}else {return AragornI->howFarRemoved("Red") + ( EldarionD, (new mtLoDFTs()))->howFarRemoved("Red");}  
	
	12 if (false){return 0;}else {return AragornI->howFarRemoved("Red") + ( EldarionD, (new mtLoDFTs()))->howFarRemoved("Red");}  

	13 return AragornI->howFarRemoved("Red") + ( EldarionD, (new mtLoDFTs()))->howFarRemoved("Red");  

	14 return if (strcmp(this->eyes, color) == 0){return 0;}else{return 1;} + ( EldarionD, (new mtLoDFTs()))->howFarRemoved("Red");  

	15 return if (strcmp(new PersonInfo("Aragorn", "Green")->eyes, color) == 0){return 0;}else{return 1;} + ( EldarionD, (new mtLoDFTs()))->howFarRemoved("Red");  

	16 return if (strcmp("Green", "Red") == 0){return 0;}else{return 1;} + ( EldarionD, (new mtLoDFTs()))->howFarRemoved("Red");  

	17 return if (1 == 0){return 0;}else{return 1;} + ( EldarionD, (new mtLoDFTs()))->howFarRemoved("Red");  

	18 return if (false){return 0;}else{return 1;} + ( EldarionD, (new mtLoDFTs()))->howFarRemoved("Red");  

	19 return 1 + ( EldarionD, (new mtLoDFTs()))->howFarRemoved("Red");  

	20 return 1 + if(this->first->howFarRemoved(color) <this->rest->howFarRemoved(color)){return this->first->howFarRemoved(color);}else{return this->rest->howFarRemoved(color);};  

	21 return 1 + if(( EldarionD, (new mtLoDFTs()))->first->howFarRemoved(color) <( EldarionD, (new mtLoDFTs()))->rest->howFarRemoved(color)){return ( EldarionD, (new mtLoDFTs()))->first->howFarRemoved(color);}else{return ( EldarionD, (new mtLoDFTs()))->rest->howFarRemoved(color);};  

	22 return 1 + if(EldarionD->howFarRemoved(color) <(new mtLoDFTs())->howFarRemoved(color)){return EldarionD->howFarRemoved(color);}else{return (new mtLoDFTs())->howFarRemoved(color);};  

	23 return 1 + if(1 <INFINITY{return EldarionD->howFarRemoved(color);}else{return (new mtLoDFTs())->howFarRemoved(color);};  

	24 return 1 + if(true{return EldarionD->howFarRemoved(color);}else{return (new mtLoDFTs())->howFarRemoved(color);};  

	25 return 1 + EldarionD->howFarRemoved(color);  

	26 return 1 + new Person( EldarionI, (new mtLoDFTs()))->howFarRemoved(color);  

	27 return 1 + 1 + (new mtLoDFTs())->howFarRemoved(color);  

	28 return 1 + 1 + (new mtLoDFTs())->howFarRemoved("Red"); 

	29 return 1 + 1 + INFINITY;  
*/
}