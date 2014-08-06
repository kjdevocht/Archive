/*Kevin DeVocht assignment 16 exercise 5. Modify people so that 
rather than a boolean living attribute there is a status string 
that may be living, dead, or undead. Modify the function living 
to accomodate this new data structure.
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
	//append: ListOfInfo, ListOfInfo-> ListOfInfo
	//Purpose: to return a new ListOfInfo with another ListOfInfo added to the end of it
	virtual ListOfInfo* append (ListOfInfo* atTheEnd) = 0;
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
  	//append: ListOfInfo, ListOfInfo-> ListOfInfo
	//Purpose: to return a new ListOfInfo with another ListOfInfo added to the end of it
	//Template: this, atTheEnd
    ListOfInfo* append (ListOfInfo* atTheEnd) {
    return atTheEnd;
  }
};



// A PersonInfo is a
//  new PersonInfo( name, eyes, age, livingness )
// where
//  name is a string
//  eyes is a string
//  age is a int
//  livingness is a string
class PersonInfo {
public:
  const char* name;
  const char* eyes;
  int age;
  const char* livingness;

  PersonInfo(const char* name0, const char* eyes0, int age0, const char* livingness0){
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
		   this->livingness);
	return 0;
	}
	  
	//living: PersonInfo->bool
	//Purpose: To see if a person is living or not
	//Template: this, this->name, this->eyes, this->age, this->livingness
	bool living()
	{
	//pretty straight forward just need to compare the given livingness to the person's
	//livingness
		if (strcmp(this->livingness, "Living") == 0)
		{
		return true;
		}
		else
		{
		return false;
		}
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
  
 //append: ListOfInfo, ListOfInfo-> ListOfInfo
//Purpose: to return a new ListOfInfo with another ListOfInfo added to the end of it 
 ListOfInfo* append ( ListOfInfo* atTheEnd ) 
 //Template: this, this->first, this->rest, this->rest->append(atTheEnd), atTheEnd
 {
    return new OneLOI( this->first, this->rest->append(atTheEnd));
  }
};




// A ListOfDFTs is either
//  mtLoDFTs
//  oneDFT
class ListOfDFTs {
public:
	virtual int show () = 0;
	
	// living : ListOfDFTs -> ListOfInfo
	//Purpose: To return a list of Info about all living decendants of a given person
	virtual ListOfInfo* living () = 0;
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
	
	//living: Person->ListOfInfo
	//Purpose: to produce a list of info about all living decendants of a giving person
	ListOfInfo* living()
	//Template: this, this->info, this->children, this->children->living()
	{
		if (this->info->living())
		{
		/*Test 1
			MadalynD->living()
				this = MadalynI, mt
				this->info = MadalynI
				this->children = mt
				this->children->living() = EmptyLOI()
				ans = MadalynI
			return (new ListOfInfo(this->info, this->children->living()))	
		*/
		return (new OneLOI(this->info, this->children->living()));
		}
		else
		{
		/*Test 2
			AmyD->living()
				this = AmyI, KatieD, AnnabelleD, NatalieD, mt
				this->info = AmyI
				this->children = KatieD, AnnabelleD, NatalieD, mt
				this->children->living() = KatieD, AnnabelleD, mt
				ans = AmyI, KatieI, AnnabelleI, EmptyLst
			return (new ListOfInfo(this->info, this->children->living()))	
		*/
		
		/*Test 3
			KevinD->living()
				this = KevinI, SadeD, MadalynD, mt
				this->info = KevinI
				this->children = SadeD, MadalynD, mt
				this->children->living() = SadeD, MadalynD, EmptyLst
				ans = SadeI, MadalynI, EmptyLst
			return this->children->living()	
		*/
		
		/*Distinguish between all tests
			we see that we we only want to add info if the person is alive
			otherwise check the children to see if any of them are alive to
			add to the list
		*/
		return this->children->living();
		}	
	}
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
	
  // living : ListOfDFTs -> ListOfInfo
  //Purpose: To return a list of Info about all living decendants of a given person
  ListOfInfo* living () {
  //Template: this
  /*Example:
	this = !
	ans = !
	return (new EmptyLOI())
  */
    return (new EmptyLOI());
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
	
	// living : ListOfDFTs -> ListOfInfo
	//Purpose: To return a list of Info about all living decendants of a given person
	ListOfInfo* living ()
	//Template: this, this->first, this->rest, this->rest->living()
	{
	/*Test 1
		AmyK->living()
			this = KatieD, AmyK2
			this->first = KatieD
			this->rest = AmyK2
			this->rest->living = NatalieI Empty
			ans = KatieI, NatalieI, Empty
		return this->first->living->append(this->rest->living())
	*/
	
	/*Test 2
		AmyK3->living()
			this = NatalieD, mt
			this->first = NatalieD
			this->rest = mt
			this->rest->living = Empty
			ans = NatalieI, Empty
		return this->first->living->append(this->rest->living())
	*/
	
	/*Generalize Tests 1 and 2
		they both test to see if first is living then append the rest after testing the
		rest to see if they are also living
	*/
	return this->first->living()->append(this->rest->living());
	}
};



// main : -> number
int main () {

	PersonInfo* FrankI = new PersonInfo("Frank", "Green", 100, "Dead");
	PersonInfo* MargretI = new PersonInfo("Margret", "Green", 70, "Undead");
	PersonInfo* JimI = new PersonInfo("Jim", "Blue", 65, "Living");
	PersonInfo* NancyI = new PersonInfo("Nancy", "Blue", 40, "Living");
	PersonInfo* NeilI = new PersonInfo("Neil", "Green", 38, "Dead");
	PersonInfo* AmyI = new PersonInfo("Amy", "Blue", 35, "Undead");
	PersonInfo* KatieI = new PersonInfo("Katie", "Brown", 8, "Living");
	PersonInfo* AnnabelleI = new PersonInfo("Annabelle", "Blue", 4, "Undead");
	PersonInfo* NatalieI = new PersonInfo("Natalie", "Blue", 2, "Living");
	PersonInfo* EmilyI = new PersonInfo("Emily", "Green", 34, "Dead");
	PersonInfo* BeckyI = new PersonInfo("Becky", "Blue", 32, "Undead");
	PersonInfo* SpencerI = new PersonInfo("Spencer", "Brown", 4, "Living");
	PersonInfo* EstherI = new PersonInfo("Esther", "Blue", 1, "Undead");
	PersonInfo* KevinI = new PersonInfo("Kevin", "Blue", 27, "Dead");
	PersonInfo* SadeI = new PersonInfo("Sade", "Blue", 4, "Living");
	PersonInfo* MadalynI = new PersonInfo("Madalyn", "Green", 2, "Undead");
	PersonInfo* MeganI = new PersonInfo("Megan", "Blue", 25, "Dead");
	PersonInfo* JennyI = new PersonInfo("Jenny", "Blue", 23, "Undead"); 

	ListOfInfo* Empty = (new EmptyLOI());
	ListOfDFTs* mt = (new mtLoDFTs());

	Person* MargretD = new Person( MargretI, (new mtLoDFTs()));
	Person* NancyD = new Person( NancyI, (new mtLoDFTs()));
	Person* NeilD = new Person( NeilI, (new mtLoDFTs()));
	Person* EmilyD = new Person( EmilyI, (new mtLoDFTs()));
	Person* MeganD = new Person( MeganI, (new mtLoDFTs()));
	Person* JennyD = new Person( JennyI, (new mtLoDFTs()));
	Person* KatieD = new Person( KatieI, (new mtLoDFTs()));
	Person* AnnabelleD = new Person( AnnabelleI, (new mtLoDFTs()));
	Person* NatalieD = new Person( NatalieI, (new mtLoDFTs()));
	Person* SpencerD = new Person( SpencerI, (new mtLoDFTs()));
	Person* EstherD = new Person( EstherI, (new mtLoDFTs()));
	Person* SadeD = new Person( SadeI, (new mtLoDFTs()));
	Person* MadalynD = new Person( MadalynI, (new mtLoDFTs()));


	ListOfDFTs* KevinK2 = (new oneDFT( MadalynD, mt ));
	ListOfDFTs* KevinK = (new oneDFT( SadeD, KevinK2 ));
	Person* KevinD = new Person( KevinI, KevinK); 


	ListOfDFTs* BeckyK2 = (new oneDFT( EstherD, mt ));
	ListOfDFTs* BeckyK = (new oneDFT( SpencerD, BeckyK2 ));
	Person* BeckyD = new Person( BeckyI, BeckyK);

	ListOfDFTs* AmyK3 = (new oneDFT( NatalieD, mt ));
	ListOfDFTs* AmyK2 = (new oneDFT( AnnabelleD, AmyK3 ));
	ListOfDFTs* AmyK = (new oneDFT( KatieD, AmyK2 ));
	Person* AmyD = new Person( AmyI, AmyK);

	ListOfDFTs* JimK8 = (new oneDFT( JennyD, mt ));
	ListOfDFTs* JimK7 = (new oneDFT( MeganD, JimK8 ));
	ListOfDFTs* JimK6 = (new oneDFT( KevinD, JimK7 ));
	ListOfDFTs* JimK5 = (new oneDFT( BeckyD, JimK6 ));
	ListOfDFTs* JimK4 = (new oneDFT( EmilyD, JimK5 ));
	ListOfDFTs* JimK3 = (new oneDFT( AmyD, JimK4 ));
	ListOfDFTs* JimK2 = (new oneDFT( NeilD, JimK3 ));
	ListOfDFTs* JimK = (new oneDFT( NancyD, JimK2 ));
	Person* JimD = new Person( JimI, JimK);


	ListOfDFTs* FrankK2 = (new oneDFT( MargretD, mt));
	ListOfDFTs* FrankK = (new oneDFT( JimD, FrankK2 ));
	Person* FrankD = new Person( FrankI, FrankK );
	
	
printf("the answer is \n");
	AnnabelleD->living()->show();
	printf("but should be new EmptyLOI\()\n");
	
printf("the answer is \n");
	NatalieD->living()->show();
	printf("\nbut should be\n new OneLOI(new PersonInfo(\"Natalie\", \"Blue\", 2, true),new EmptyLOI())\n");
	
printf("the answer is \n");
	KevinD->living()->show();
	printf("but should be SadeI, EmptyLOI\n");
	
printf("the answer is \n");
	AmyK->living()->show();
	printf("but should be KatieI, NatalieI, EmptyLOI\n");
	
printf("the answer is \n");
	FrankK->living()->show();
	printf("\nbut should be JimI, NancyI, KatieI, NatalieI, SpencerI, SadeI, EmptyLOI\n");	
return 0;
/*Substitution
	1. AmyK3->living()
	
	2. (new oneDFT( NatalieD, mt ))->living()
	
	3. return this->first->living()->append(this->rest->living());
	
	4. return (new oneDFT( NatalieD, mt  ))->first->living()->append((new oneDFT( NatalieD, mt  ))->rest->living());
	
	5. return (NatalieD->living()->append(mt->living());
	
	6. return new Person( NatalieI, (new mtLoDFTs()))->living()->append(mt->living());
	
	7. return if (this->info->living()){return (new OneLOI(this->info, this->children->living()));}else{return this->children->living();}	->append(mt->living());
	
	8. return if (new Person( NatalieI, (new mtLoDFTs()))->info->living()){return (new OneLOI(new Person( NatalieI, (new mtLoDFTs()))->info, new Person( NatalieI, (new mtLoDFTs()))->children->living()));}else{return new Person( NatalieI, (new mtLoDFTs()))->children->living();}	->append(mt->living());
	
	9. return if (NatalieI->living()){return (new OneLOI(new Person( NatalieI, new mtLoDFTs()))->children->living());}else{return new mtLoDFTs()->living();}->append(mt->living());

	10. return if (new PersonInfo("Natalie", "Blue", 2, "Living")->living()){return (new OneLOI(new Person( NatalieI, new mtLoDFTs()))->children->living());}else{return new mtLoDFTs()->living();}->append(mt->living());
	
	11. return if (if (strcmp(this->livingness, "Living") == 0){return true;}else{return false;}){return (new OneLOI(new Person( NatalieI, new mtLoDFTs()))->children->living());}else{return new mtLoDFTs()->living();}->append(mt->living());
	
	12. return if (if (strcmp(new PersonInfo("Natalie", "Blue", 2, "Living")->livingness, "Living") == 0){return true;}else{return false;}){return (new OneLOI(new Person( NatalieI, new mtLoDFTs()))->children->living());}else{return new mtLoDFTs()->living();}->append(mt->living());
	
	13. return if (if (strcmp("Living", "Living") == 0){return true;}else{return false;}){return (new OneLOI(new Person( NatalieI, new mtLoDFTs()))->children->living());}else{return new mtLoDFTs()->living();}->append(mt->living());

	14. return if (if (true){return true;}else{return false;}){return (new OneLOI(new Person( NatalieI, new mtLoDFTs()))->children->living());}else{return new mtLoDFTs()->living();}->append(mt->living());

	15. return if (true){return (new OneLOI(NatalieI, new mtLoDFTs()))->children->living());}else{return new mtLoDFTs()->living();}->append(mt->living());

	16. return (new OneLOI(NatalieI, new mtLoDFTs())->living())->append(mt->living());

	17. return (new OneLOI(NatalieI, (new EmptyLOI()))->append(mt->living());

	18. return (new OneLOI(NatalieI, (new EmptyLOI()))->append(mt->living());

	19. return new OneLOI( this->first, this->rest->append(atTheEnd))

	20. return new OneLOI( NatalieI, new EmptyLOI()->append(atTheEnd))

	21.	return OneLOI( NatalieI, new EmptyLOI());
*/
}