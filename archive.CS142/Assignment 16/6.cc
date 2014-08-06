/*Kevin DeVocht assignment 16 exercise 6. Write a function called 
death that takes a parent and a name and returns a family tree where 
the named person is marked as non-living.
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
	
	//death: PersonInfo->bool
	//Purpose: to see if the given person is the new parent
	bool death(const char* MarkedForDeath)
	//Template: this, this->name, this->eyes, this->age, this->livingness, MarkedForDeath
	{ 
	if (strcmp(this->name, MarkedForDeath) == 0)
		{
		/*Test 1
			KevinI->death("Kevin")
				this = KevinI
				this->name = "Kevin"
				MarkedForDeath = "Kevin"
				ans = true
			return true
		*/
		return true;
		}
		else
		{
		/*Test 2
			KevinI->death("Jim")
				this = KevinI
				this->name = "Kevin"
				MarkedForDeath = "Jim"
				ans = false
			return false
		*/

		/*Distinguish Tests 1 and 2	
			we can see that there is only two possible out comes
			either the given persons name matchs MarkedForDeath or it does
			not
		*/
		return false;
		}
	}
};



// A ListOfDFTs is either
//  mtLoDFTs
//  oneDFT
class ListOfDFTs {
public:
	virtual int show () = 0;

	
	//death: ListOfDFTs, string->ListOfDFTs
	//Purpose: To remove somebody from the family tree
	virtual ListOfDFTs* death(const char* MarkedForDeath) = 0;
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
	printf("\nnew Person(");
	this->info->show();
	printf(",");
	this->children->show();
	printf(")");
	return 0;
	}
	
	//death: Person, string, PersonInfo->Person
	//Purpose: To add a new child to a given person
	Person* death (const char* MarkedForDeath)
	//Template: this, this->info, this->children, this->children->death(MarkedForDeath, newBaby) MarkedForDeath,newBaby
	{
		if (this->info->death(MarkedForDeath))
		{
		/*Test 1
			NancyD->death("Nancy", newBabyI)
				this = NancyI, mt
				this->info = NancyI
				this->children = mt
				this->children->death = mt
				ans = NancyI, newBabyD, mt
			return new Person(this->info, this->children->append(newBaby)
		*/
		return new Person( this->children->death(MarkedForDeath), this->children->death(MarkedForDeath));
		}
		else
		{
		/*Test 2
			FrankD->death("Margret", newBabyI)
				this = FrankI, JimD, MargretD, mt
				this->info = FrankI
				this->children = JimD, MargretD, mt
				this->children->death = JimD, MargretD(plus newBaby), mt
				ans = FrankI, JimD, MargretD(plus newBaby), mt
			return new Person(this->info, this->children->death->(MarkedForDeath, newBaby)
		*/
		/*Distinguish Tests 1 and 2
			we see that if info is supposed to have the baby just append the newBaby to
			the end of the list of children.  otherwise keep looking for the new Parent
		*/
		return new Person(this->info, (this->children->death(MarkedForDeath)));
		}
	
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

	
	//death: ListOfDFTs, string, string, string, int, bool->oneDFT
	//Purpose: to add a new child to a given parent
	ListOfDFTs* death(const char* MarkedForDeath)
	//Template: this, this->first, this->rest, this->rest->death(MarkedForDeath, newBaby), MarkedForDeath, newBaby
	{
	/*Test 1
		KevinK->death("Sade", newBabyI)
			this = SadeD, KevinK2
			this->First = SadeD
			this->rest = KevinK2
			this->rest->death("Sade", newBabyI) = KevinK2
			ans = SadeD(plus newBaby), KevinK2
		return new oneDFT(this->first->death(MarkedForDeath, newBaby), this->rest)
	*/
	
	/*Test 2
		JimK->death("Sade", newBabyI)
			this = NancyD, JimK2
			this->First = NancyD
			this->rest = JimK2
			this->rest->death("Sade", newBabyI) = JimK2(plus newBaby)
			ans = NancyD, JimK2(plus newBaby)
		return new oneDFT(this->first, this->rest->death(MarkedForDeath, newBaby))
	*/
	
	/*Generalize Tests 1 and 2
		we see that we need a new oneDFT and that we we need to check both first and rest
		to see who in the list is suppossed to be having the new baby
	*/
	return new oneDFT(this->first->death(MarkedForDeath), this->rest->death(MarkedForDeath));
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
	

	
	//death: ListOfDFTs, string, string, string, int, bool->mtLoDFTs
	//Purpose: to add a new child to a given parent
	ListOfDFTs* death(const char* MarkedForDeath)
	//Template: this, MarkedForDeath, newBaby
	{
	/*Test 1
		mt->death("Kevin", newBabyI)
			this = !
			ans = !
		return new mtLoDFTs()
	*/
	return (new mtLoDFTs());
	
	}
};

// main : -> number
int main () {

	PersonInfo* FrankI = new PersonInfo("Frank", "Green", 100, "Living");
	PersonInfo* MargretI = new PersonInfo("Margret", "Green", 70, "Living");
	PersonInfo* JimI = new PersonInfo("Jim", "Blue", 65, "Living");
	PersonInfo* NancyI = new PersonInfo("Nancy", "Blue", 40, "Living");
	PersonInfo* NeilI = new PersonInfo("Neil", "Green", 38, "Living");
	PersonInfo* AmyI = new PersonInfo("Amy", "Blue", 35, "Living");
	PersonInfo* KatieI = new PersonInfo("Katie", "Brown", 8, "Living");
	PersonInfo* AnnabelleI = new PersonInfo("Annabelle", "Blue", 4, "Living");
	PersonInfo* NatalieI = new PersonInfo("Natalie", "Blue", 2, "Living");
	PersonInfo* EmilyI = new PersonInfo("Emily", "Green", 34, "Living");
	PersonInfo* BeckyI = new PersonInfo("Becky", "Blue", 32, "Living");
	PersonInfo* SpencerI = new PersonInfo("Spencer", "Brown", 4, "Living");
	PersonInfo* EstherI = new PersonInfo("Esther", "Blue", 1, "Living");
	PersonInfo* KevinI = new PersonInfo("Kevin", "Blue", 27, "Living");
	PersonInfo* SadeI = new PersonInfo("Sade", "Blue", 4, "Living");
	PersonInfo* MadalynI = new PersonInfo("Madalyn", "Green", 2, "Living");
	PersonInfo* MeganI = new PersonInfo("Megan", "Blue", 25, "Living");
	PersonInfo* JennyI = new PersonInfo("Jenny", "Blue", 23, "Living");

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
	NancyD->death("Nancy")->show();
	printf("but should be \n Nancy, newBaby, mtLoDFTS \n");
	
printf("\nthe answer is \n");
	KevinD->death("Sade")->show();
	printf("but should be \n Kevin, Sade, newBaby, mt, mt, Madalyn, mt, mt\n");
	
printf("\nthe answer is \n");
	KevinK->death("Sade")->show();	
	printf("but should be \n Sade, newBaby, mt, Madalyn, mt, mt\n");
	
printf("\nthe answer is \n");
	FrankD->death("Katie")->show();	
	printf("but should be \n Frank, Jim, Nancy, mt, Neil, mt, Amy, Katie, newBaby, mt, mt, Annabelle, mt, Natalie, mt,mt, Emily, mt, Becky, Spencer, mt, Esther, mt, mt, Kevin, Sade, mt, Madaylyn, mt, mt, Megan, mt, Jenny, mt, mt, Magret, mt, mt\n");
	
printf("\nthe answer is \n");
	mt->death("Katie")->show();	
	printf("\nbut should be \n new mtLoDFTs() \n");
	
return 0;
/*Substitution
	I RAN OUT OF TIME.  I HOPE THIS DOES NOT COME BACK TO HAUNT ME!
*/
}