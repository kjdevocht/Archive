/*Kevin DeVocht assignment 14 exercise 5. Develop the function, mothers, 
that takes a family tree and produces a list of the names of all the mothers in the tree.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>

// booleanToString : boolean -> string
// Purpose: convert a boolean into a string for printing
const char* booleanToString ( bool it ) {
  if ( it ) { return "true"; } else { return "false"; }
}


 

// A PersonInfo is a
//  new PersonInfo( name, bloodtype, eyes)
// where
//  name is a string
//  bloodtype is a string
//  eyes is a string
class PersonInfo {
public:
  const char* name;
  const char* bloodtype;
  const char* eyes;

  PersonInfo(const char* name0, const char* bloodtype0, const char* eyes0){
    this->name = name0;
    this->bloodtype = bloodtype0;
    this->eyes = eyes0;
  }
  
	

};

// A FamilyTree is either
//   VisitTheFamilyHistoryCenter
//   Person
class FamilyTree {
public:
	


  

};

// A VisitTheFamilyHistoryCenter is a
//  new VisitTheFamilyHistoryCenter()
// where
class VisitTheFamilyHistoryCenter : public FamilyTree {
public:

  VisitTheFamilyHistoryCenter () {}
  
  

};

// A Person is a
//  new Person( info, mother, father )
// where
//  info is a PersonInfo
//  mother is a FamilyTree
//  father is a FamilyTree
class Person : public FamilyTree {
public:
  PersonInfo* info;
  FamilyTree* mother;
  FamilyTree* father;

  Person ( PersonInfo* info0, FamilyTree* mother0, FamilyTree* father0 ) 
  {
    this->info = info0;
    this->mother = mother0;
    this->father = father0;
  }

};

// main : -> number
int main () {


  FamilyTree* nomore = new VisitTheFamilyHistoryCenter();

  PersonInfo* jeremyI = new PersonInfo("Jeremy Bieber", "O", "Blue");
  FamilyTree* jeremyP = new Person(jeremyI, nomore, nomore);

  PersonInfo* dianneI = new PersonInfo("Dianne", "B", "Brown");
  FamilyTree* dianneP = new Person(dianneI, nomore, nomore);

  PersonInfo* bruceI = new PersonInfo("Bruce", "O", "Hazel");
  FamilyTree* bruceP = new Person(bruceI, nomore, nomore);

  PersonInfo* patriciaI = new PersonInfo("Patricia", "B", "Brown");
  FamilyTree* patriciaP = new Person( patriciaI, dianneP, bruceP );

  PersonInfo* jbI = new PersonInfo("Justin", "A", "Dreamy");
  FamilyTree* jbP = new Person( jbI, patriciaP, jeremyP);
  


  printf ( "The answer is %s, but should be %s\n", booleanToString(jeremyP->properBlueEyedAncestor()), "false");
  
  printf ( "The answer is %s, but should be %s\n", booleanToString(jbP->properBlueEyedAncestor()), "true");
  
  printf ( "The answer is %s, but should be %s\n", booleanToString(patriciaP->properBlueEyedAncestor()), "false");
  return 0;
  
}