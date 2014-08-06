/*Kevin DeVocht assignment 14 exercise 2. Write the function properBlueEyedAncestor 
that consumes a family tree and returns true only if an actual ancestor of the person 
given has blue eyes. (For example, I have blue eyes, but none of my ancestors do, so 
this function would return false on my family tree.)
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
  
	//areMyAncestorsEyesBlueHuh: PersonInfo->bool
	//Purpose: To see if any of your ancestors have blue eyes
	bool areMyAncestorsEyesBlueHuh()
	//Template: this, this->name, this->bloodtype, this->eyes
	{
	
		if(strcmp(this->eyes, "Blue") == 0)
		/*Test 1
			jeremyI->areMyAncestorsEyesBlueHuh
				this = new PersonInfo("Jeremy Bieber", "O", "Blue")
				this->eyes = "Blue"
				ans = true
			return true
		*/
		{
		return true;
		}
		else
		/*Test 2 
			patriciaI->areMyAncestorsEyesBlueHuh
				this = new PersonInfo("Patricia", "B", "Brown")
				this->eyes = "Brown"
				ans = false
			return false
		*/
		/*Generalize Tests 1 and 2
			we need to compare this->eyes to "Blue" if they are the same return true otherwise return false
		*/
		{
		return false;
		}
	}
};

// A FamilyTree is either
//   VisitTheFamilyHistoryCenter
//   Person
class FamilyTree {
public:
	
	//properBlueEyedAncestor: FamilyTree->bool
	//Purpose: To see if any of your ancestors have blue eyes
	virtual bool properBlueEyedAncestor() = 0;
	
	//checkTheLine: FamilyTree->bool
	//Purpose: to check your ancestors eye color while skipping you
	virtual bool checkTheLine() = 0;

  

};

// A VisitTheFamilyHistoryCenter is a
//  new VisitTheFamilyHistoryCenter()
// where
class VisitTheFamilyHistoryCenter : public FamilyTree {
public:

  VisitTheFamilyHistoryCenter () {}
  
  
  	//properBlueEyedAncestor: VisitTheFamilyHistoryCenter->bool
	//Purpose: To see if any of your ancestors have blue eyes
	bool properBlueEyedAncestor()
	//Template: this
	{
	/*Test 1
		nomore->properBlueEyedAncestor()
			this =!
			ans = false
		return false
	*/
	return false;
	}
	
	//checkTheLine: VisitTheFamilyHistoryCenter->bool
	//Purpose: to check your ancestors eye color while skipping you
	bool checkTheLine()
	//Template: this
	{
		/*Test 1
		nomore->checkTheLine()
			this =!
			ans = false
		return false
	*/
	return false;
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

  Person ( PersonInfo* info0, FamilyTree* mother0, FamilyTree* father0 ) 
  {
    this->info = info0;
    this->mother = mother0;
    this->father = father0;
  }


    //properBlueEyedAncestor: Person->bool
	//Purpose: To see if any of your ancestors have blue eyes
	bool properBlueEyedAncestor()
	//Template: this, this->info, this->mother, this->father, this->mother->properBlueEyedAncestor(), this->father->properBlueEyedAncestor(), this->mother->checkTheLine(), this->father->checkTheLine()
	{
		/*Test 1
			jbP->properBlueEyedAncestor()
				this = jbI, patriciaP, jeremyP
				this->info = jbI
				this->mother = patriciaP
				this->father = jeremyP
				ans = true
			retrun true
		*/
		
		/*Test 2
			patriciaP->properBlueEyedAncestor()
				this = patriciaI, dianneP, bruceP 
				this->info = jbI
				this->mother = dianneP
				this->father = bruceP
				ans = false
			return false
		*/
		
		/*Generalize Tests 1 and 2
			We can see that we need another function to look deeper.  
			This function should consume a PersonInfo and return an bool.  
			The return from areMyAncestorsEyesBlueHuh is the same return for properBlueEyedAncestor
		*/
		return this->mother->checkTheLine()|| this->father->checkTheLine();
	}
	
	//checkTheLine: FamilyTree->bool
	//Purpose: to check your ancestors eye color while skipping you
	bool checkTheLine()
	//Template: this, this->info, this->mother, this->father, this->mother->properBlueEyedAncestor(), this->father->properBlueEyedAncestor(), this->mother->checkTheLine(), this->father->checkTheLine()
	{
		if (this->info->areMyAncestorsEyesBlueHuh() )
		/*Test 1
			jeremyP->checkTheLine()
				this = jeremyI, nomore, nomore
				this->info = jeremyI
				this->mother = nomore
				this->father = nomore
				ans = true
			return true
		*/
		{
		return true;
		}
		
		else
		{
		/*Test 2
			patriciaP->checkTheLine()
				this = patriciaI, dianneP, bruceP
				this->info = patriciaI
				this->mother = dianneP
				this->father = bruceP
				ans = false
			return false
		*/
		/* Generalize Tests 1 and 2
			We can see that we need another function that can look deep enough to compare eye colors.  
			if it is not true check the rest of the tree.  thus we know we need an if statemenet
		*/
		return this->mother->checkTheLine() || this->father->checkTheLine();
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
  


  printf ( "The answer is %s, but should be %s\n", booleanToString(jeremyP->properBlueEyedAncestor()), "false");
  
  printf ( "The answer is %s, but should be %s\n", booleanToString(jbP->properBlueEyedAncestor()), "true");
  
  printf ( "The answer is %s, but should be %s\n", booleanToString(patriciaP->properBlueEyedAncestor()), "false");
  return 0;
  
/* Substitution
	1. jeremyP->properBlueEyedAncestor()
	
	2. new Person(patriciaP, dianneP, bruceP)->properBlueEyedAncestor()
	
	3. return this->mother->checkTheLine()|| this->father->checkTheLine();
	
	4. return this->mother->checkTheLine()|| this->father->checkTheLine();
 
	5. return new Person(patriciaP, dianneP, bruceP)->mother->checkTheLine()|| new Person(patriciaP, dianneP, bruceP)->father->checkTheLine();
	
	6. return dianneP->checkTheLine()|| bruceP->checkTheLine();
 
	7. if (this->info->areMyAncestorsEyesBlueHuh() ){return true;} else {return this->mother->checkTheLine() || this->father->checkTheLine();}|| if (this->info->areMyAncestorsEyesBlueHuh() ){return true;} else {return this->mother->checkTheLine() || this->father->checkTheLine();}
 
	8. if (new Person(dianneI, nomore, nomore)->info->areMyAncestorsEyesBlueHuh() ){return true;} else {return new Person(dianneI, nomore, nomore)->mother->checkTheLine() || new Person(dianneI, nomore, nomore)->father->checkTheLine();}|| if (new Person(bruceI, nomore, nomore)->info->areMyAncestorsEyesBlueHuh() ){return true;} else {return new Person(bruceI, nomore, nomore)->mother->checkTheLine() || new Person(bruceI, nomore, nomore)->father->checkTheLine();}
 
	9. if (dianneI->areMyAncestorsEyesBlueHuh() ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}|| if (bruceI->areMyAncestorsEyesBlueHuh() ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}
 
 	10. if (new PersonInfo("Dianne", "B", "Brown")->areMyAncestorsEyesBlueHuh() ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}|| if (bruceI->areMyAncestorsEyesBlueHuh() ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}

  	11. if (if(strcmp(this->eyes, "Blue") == 0){return true;} else {return false;}){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}|| if (bruceI->areMyAncestorsEyesBlueHuh() ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}

   	12. if (if(strcmp(new PersonInfo("Dianne", "B", "Brown")->eyes, "Blue") == 0){return true;} else {return false;}){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}|| if (bruceI->areMyAncestorsEyesBlueHuh() ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}

    13. if (if(strcmp("Brown", "Blue") == 0){return true;} else {return false;}){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}|| if (bruceI->areMyAncestorsEyesBlueHuh() ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}

    14. if (if(1 == 0){return true;} else {return false;}){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}|| if (bruceI->areMyAncestorsEyesBlueHuh() ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}

    15. if (if(false){return true;} else {return false;}){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}|| if (bruceI->areMyAncestorsEyesBlueHuh() ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}
	
    16. if (false){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}|| if (bruceI->areMyAncestorsEyesBlueHuh() ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}
	
    17. return nomore->checkTheLine() || nomore->checkTheLine()|| if (bruceI->areMyAncestorsEyesBlueHuh() ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}
	
    18. return new VisitTheFamilyHistoryCenter()->checkTheLine() || new VisitTheFamilyHistoryCenter()->checkTheLine()|| if (bruceI->areMyAncestorsEyesBlueHuh() ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}
	
    19. return false || false|| if (bruceI->areMyAncestorsEyesBlueHuh() ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}
	
    20. return false || if (new PersonInfo("Bruce", "O", "Hazel")->areMyAncestorsEyesBlueHuh() ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}
	
    21. return false || if (if(strcmp(this->eyes, "Blue") == 0){return true;} else {return false;} ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}
	
    22. return false || if (if(strcmp(new PersonInfo("Bruce", "O", "Hazel")->eyes, "Blue") == 0){return true;} else {return false;} ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}
	
    23. return false || if (if(strcmp("Hazel", "Blue") == 0){return true;} else {return false;} ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}
	
    24. return false || if (if(strcmp(1 == 0)){return true;} else {return false;} ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}
	
    25. return false || if (if(false){return true;} else {return false;} ){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}
	
    26. return false || if (false){return true;} else {return nomore->checkTheLine() || nomore->checkTheLine();}
	
    27. return false || nomore->checkTheLine() || nomore->checkTheLine();
	
    28. return false || new VisitTheFamilyHistoryCenter()->checkTheLine() || new VisitTheFamilyHistoryCenter()->checkTheLine();
	
    29. return false || false || false;
	
    30. return false;
*/
}