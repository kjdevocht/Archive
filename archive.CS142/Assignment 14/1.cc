/*Kevin DeVocht assignment 14 exercise 1. Add a blood type attribute to 
family trees and write a function countType that consumes a bloodtype and a 
family tree and produces the number of people in the family tree who have the given bloodtype.
*/


//time: 1 hour

#include <stdio.h>
#include <math.h>
#include <string.h>


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
  

  //rightType: PersonInfo string->int
  //Purpose: to see if a given PersonInfo has the right blood type
  int rightType (const char* blood)
  //Template: this, this->name, this->bloodtype, this->eyes, blood
  {
  return strcmp(this->bloodtype, blood);
  }


};

// A FamilyTree is either
//   VisitTheFamilyHistoryCenter
//   Person
class FamilyTree {
public:

  
  //how_many_blood: FamilyTree, string->int
  //Purpose: To find out how many people in the family tree have a given blood type
  virtual int how_many_blood(const char* theType) = 0;
};

// A VisitTheFamilyHistoryCenter is a
//  new VisitTheFamilyHistoryCenter()
// where
class VisitTheFamilyHistoryCenter : public FamilyTree {
public:

  VisitTheFamilyHistoryCenter () {}

  
  //how_many_blood: VisitTheFamilyHistoryCenter, string->int
  //Purpose: To find out how many people in the family tree have a given blood type
  int how_many_blood(const char* theType)
  //Template: this, theType
  {
  /*Test 1
		nomore->how_many_blood(O+)
			this = !
			theType = O+
			ans = 0
		return 0;
  */
  return 0;
  }


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

  Person ( PersonInfo* info0, FamilyTree* mother0, FamilyTree* father0 ) {
    this->info = info0;
    this->mother = mother0;
    this->father = father0;
  }


  
  //how_many_blood: Person, string->int
  //Purpose: To find out how many people in the family tree have a given blood type
  int how_many_blood(const char* theType)
  //Template: this, this->info, this->mother, this->father, this->mother->how_many_blood(TheType), this->father->how_many_blood(TheType),theType
	{
		if (this->info->rightType(theType) == 0)
		{
		/*Test 1
			jeremyP->how_many_blood("O")
				this = jeremyI, nomore, nomore
				this->info = jeremyI
				this->mother = new VisitTheFamilyHistoryCenter()
				this->father = new VisitTheFamilyHistoryCenter()
				this->mother->how_many_blood(TheType) = 0
				this->father->how_many_blood(TheType) = 0
				ans = 1
			return 1+this->father->how_many_blood(TheType)+this->mother->how_many_blood(TheType)
		*/
		return 1+this->father->how_many_blood(theType)+this->mother->how_many_blood(theType);
		}
		
		else
		
		{
		/*Test 2
			patriciaP->how_many_blood("O")
				this = patriciaI, dianneP, bruceP
				this->info = patriciaI
				this->mother = dianneP
				this->father = bruceP
				this->mother->how_many_blood(TheType) = 0
				this->father->how_many_blood(TheType) = 1
				ans = 1
			return 0+this->father->how_many_blood(TheType)+this->mother->how_many_blood(TheType)
		*/
		
		/*Generalize Tests 1 and 2
			We can see that we need to look deeper so call rightType which 
			should consume a PersonInfo and a string a return a int.  if it 
			returns a 0 return 1+therest of the family tree else return 
			0+therestofthefamilytree.  We also know that we will need an 
			if statement
		*/
		return this->father->how_many_blood(theType)+this->mother->how_many_blood(theType);
		}
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
  


  printf ( "The answer is %d, but should be %d\n", jeremyP->how_many_blood("O"), 1);

  printf ( "The answer is %d, but should be %d\n", patriciaP->how_many_blood("O"), 1);

  printf ( "The answer is %d, but should be %d\n", nomore->how_many_blood("B"), 0);
  
  printf ( "The answer is %d, but should be %d\n", jbP->how_many_blood("B"), 2);
  return 0;
  
 /*Substitution
	1. patriciaP->how_many_blood("O")
	
	2. new Person( patriciaI, dianneP, bruceP )->how_many_blood("O")
	
	3. 	if (this->info->rightType(theType) == 0){return 1+this->father->how_many_blood(theType)+this->mother->how_many_blood(theType);} else {return this->father->how_many_blood(theType)+this->mother->how_many_blood(theType);}
	
	4. 	if (new Person( patriciaI, dianneP, bruceP )->info->rightType(theType) == 0){return 1+new Person( patriciaI, dianneP, bruceP )->father->how_many_blood(theType)+new Person( patriciaI, dianneP, bruceP )->mother->how_many_blood(theType);} else {return new Person( patriciaI, dianneP, bruceP )->father->how_many_blood(theType)+new Person( patriciaI, dianneP, bruceP )->mother->how_many_blood(theType);}
	
	5. 	if (patriciaI->rightType(theType) == 0){return 1+bruceP->how_many_blood(theType)+dianneP->how_many_blood(theType);} else {return bruceP->how_many_blood(theType)+dianneP->how_many_blood(theType);}
	
	6. 	if (new PersonInfo("Patricia", "B", "Brown")->rightType(theType) == 0){return 1+bruceP->how_many_blood(theType)+dianneP->how_many_blood(theType);} else {return bruceP->how_many_blood(theType)+dianneP->how_many_blood(theType);}	
	
	7. 	if (strcmp(this->bloodtype, blood) == 0){return 1+bruceP->how_many_blood(theType)+dianneP->how_many_blood(theType);} else {return bruceP->how_many_blood(theType)+dianneP->how_many_blood(theType);}
	
	8. 	if (strcmp(new PersonInfo("Patricia", "B", "Brown")->bloodtype, "O") == 0){return 1+bruceP->how_many_blood(theType)+dianneP->how_many_blood(theType);} else {return bruceP->how_many_blood(theType)+dianneP->how_many_blood(theType);}
	
	9. 	if (strcmp("B", "O") == 0){return 1+bruceP->how_many_blood(theType)+dianneP->how_many_blood(theType);} else {return bruceP->how_many_blood(theType)+dianneP->how_many_blood(theType);}
	
	10. if (1 == 0){return 1+bruceP->how_many_blood(theType)+dianneP->how_many_blood(theType);} else {return bruceP->how_many_blood(theType)+dianneP->how_many_blood(theType);}
	
	11. if (false){return 1+bruceP->how_many_blood(theType)+dianneP->how_many_blood(theType);} else {return bruceP->how_many_blood(theType)+dianneP->how_many_blood(theType);}
	
	12. return bruceP->how_many_blood("O")+dianneP->how_many_blood("O");
	
	13. return new Person(bruceI, nomore, nomore)->how_many_blood("O")+dianneP->how_many_blood("O");
	
	14. if (this->info->rightType(theType) == 0){return 1+this->father->how_many_blood(theType)+this->mother->how_many_blood(theType);} else {return this->father->how_many_blood(theType)+this->mother->how_many_blood(theType);}+dianneP->how_many_blood("O");

	15. if (new Person(bruceI, nomore, nomore)->info->rightType(theType) == 0){return 1+new Person(bruceI, nomore, nomore)->father->how_many_blood(theType)+new Person(bruceI, nomore, nomore)->mother->how_many_blood(theType);} else {return new Person(bruceI, nomore, nomore)->father->how_many_blood(theType)+new Person(bruceI, nomore, nomore)->mother->how_many_blood(theType);}+dianneP->how_many_blood("O");
	
	16. if (bruceI->rightType(theType) == 0){return 1+nomore->how_many_blood(theType)+nomorehow_many_blood(theType);} else {return nomore->how_many_blood(theType)+nomore->how_many_blood(theType);}+dianneP->how_many_blood("O");
	
	17. if (new PersonInfo("Bruce", "O", "Hazel")->rightType("O") == 0){return 1+nomore->how_many_blood("O")+nomore->how_many_blood("O");} else {return nomore->how_many_blood("O")+nomore->how_many_blood("O");}+dianneP->how_many_blood("O");
	
	18. if (strcmp(this->bloodtype, blood) == 0){return 1+nomore->how_many_blood("O")+nomore->how_many_blood("O");} else {return nomore->how_many_blood("O")+nomore->how_many_blood("O");}+dianneP->how_many_blood("O");
	
	19. if (strcmp("O", "O") == 0){return 1+nomore->how_many_blood("O")+nomore->how_many_blood("O");} else {return nomore->how_many_blood("O")+nomore->how_many_blood("O");}+dianneP->how_many_blood("O");
	
	20. if (0 == 0){return 1+nomore->how_many_blood("O")+nomore->how_many_blood("O");} else {return nomore->how_many_blood("O")+nomore->how_many_blood("O");}+dianneP->how_many_blood("O");
	
	21. if (true){return 1+nomore->how_many_blood("O")+nomore->how_many_blood("O");} else {return nomore->how_many_blood("O")+nomore->how_many_blood("O");}+dianneP->how_many_blood("O");
	
	22. return 1+nomore->how_many_blood("O")+nomore->how_many_blood("O") + dianneP->how_many_blood("O");
	
	23. return 1+new VisitTheFamilyHistoryCenter()->how_many_blood("O")+new VisitTheFamilyHistoryCenter()->how_many_blood("O") + dianneP->how_many_blood("O");
	
	24. return 1+0+0 + dianneP->how_many_blood("O");
	
	25. return 1+0+0 + new Person(dianneI, nomore, nomore)->how_many_blood("O");
	
	26. return 1+0+0 +if (this->info->rightType(theType) == 0){return 1+this->father->how_many_blood(theType)+this->mother->how_many_blood(theType);} else {return this->father->how_many_blood(theType)+this->mother->how_many_blood(theType);}
	
	27. return 1+0+0 +if (new Person(dianneI, nomore, nomore)->info->rightType(theType) == 0){return 1+new Person(dianneI, nomore, nomore)->father->how_many_blood(theType)+new Person(dianneI, nomore, nomore)->mother->how_many_blood(theType);} else {return new Person(dianneI, nomore, nomore)->father->how_many_blood(theType)+new Person(dianneI, nomore, nomore)->mother->how_many_blood(theType);}
	
	28. return 1+0+0 +if (dianneI->rightType("O") == 0){return 1+nomore->how_many_blood(theType)+nomore->how_many_blood(theType);} else {return nomore->how_many_blood(theType)+nomore->how_many_blood(theType);}
	
	29. return 1+0+0 +if (strcmp(this->bloodtype, blood) == 0){return 1+nomore->how_many_blood(theType)+nomore->how_many_blood(theType);} else {return nomore->how_many_blood(theType)+nomore->how_many_blood(theType);}
	
	30. return 1+0+0 +if (strcmp(new PersonInfo("Dianne", "B", "Brown")->bloodtype, "O") == 0){return 1+nomore->how_many_blood(theType)+nomore->how_many_blood(theType);} else {return nomore->how_many_blood(theType)+nomore->how_many_blood(theType);}
	
	31. return 1+0+0 +if (strcmp("B", "O") == 0){return 1+nomore->how_many_blood(theType)+nomore->how_many_blood(theType);} else {return nomore->how_many_blood(theType)+nomore->how_many_blood(theType);}
	
	32. return 1+0+0 +if (1 == 0){return 1+nomore->how_many_blood(theType)+nomore->how_many_blood(theType);} else {return nomore->how_many_blood(theType)+nomore->how_many_blood(theType);}
	
	33. return 1+0+0 +if (false){return 1+nomore->how_many_blood(theType)+nomore->how_many_blood(theType);} else {return nomore->how_many_blood(theType)+nomore->how_many_blood(theType);}
	
	33. return 1+0+0 +nomore->how_many_blood(theType)+nomore->how_many_blood(theType);
	
	34. return 1+0+0 +new VisitTheFamilyHistoryCenter()->how_many_blood("O")+new VisitTheFamilyHistoryCenter()->how_many_blood("O");
	
	35. return 1+0+0 +0+0;
	
	35. return 1
*/
}