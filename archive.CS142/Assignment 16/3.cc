/*Kevin DeVocht assignment 15 exercise 3. Write a function called 
birth that takes a parent, a name, and information about a new person. 
The function should return an updated family tree where the named 
descendant (assume there is only one) has a new descendant (with no children) 
specified by the given information.
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
	
	//birth: PersonInfo->bool
	//Purpose: to see if the given person is the new parent
	bool birth(const char* newParent)
	//Template: this, this->name, this->eyes, this->age, this->livingness, newParent
	{ 
	if (strcmp(this->name, newParent) == 0)
		{
		/*Test 1
			KevinI->birth("Kevin")
				this = KevinI
				this->name = "Kevin"
				newParent = "Kevin"
				ans = true
			return true
		*/
		return true;
		}
		else
		{
		/*Test 2
			KevinI->birth("Jim")
				this = KevinI
				this->name = "Kevin"
				newParent = "Jim"
				ans = false
			return false
		*/

		/*Distinguish Tests 1 and 2	
			we can see that there is only two possible out comes
			either the given persons name matchs newParent or it does
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
	
	//append: ListOfDFTs, PersonInfo->ListOfDFTs
	//Purpose: To add a new person to a family tree
	virtual ListOfDFTs* append (PersonInfo* newBaby) = 0;
	
	//birth: ListOfDFTs, string, PersonInfo->ListOfDFTs
	//Purpose: to add a new child to a given parent
	virtual ListOfDFTs* birth(const char* newParent, PersonInfo* newBaby) = 0;
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
	
	//birth: Person, string, PersonInfo->Person
	//Purpose: To add a new child to a given person
	Person* birth (const char* newParent, PersonInfo* newBaby)
	//Template: this, this->info, this->children, this->children->birth(newParent, newBaby) newParent,newBaby
	{
		if (this->info->birth(newParent))
		{
		/*Test 1
			NancyD->birth("Nancy", newBabyI)
				this = NancyI, mt
				this->info = NancyI
				this->children = mt
				this->children->birth = mt
				ans = NancyI, newBabyD, mt
			return new Person(this->info, this->children->append(newBaby)
		*/
		return (new Person(this->info, this->children->append(newBaby)));
		}
		else
		{
		/*Test 2
			FrankD->birth("Margret", newBabyI)
				this = FrankI, JimD, MargretD, mt
				this->info = FrankI
				this->children = JimD, MargretD, mt
				this->children->birth = JimD, MargretD(plus newBaby), mt
				ans = FrankI, JimD, MargretD(plus newBaby), mt
			return new Person(this->info, this->children->birth->(newParent, newBaby)
		*/
		/*Distinguish Tests 1 and 2
			we see that if info is supposed to have the baby just append the newBaby to
			the end of the list of children.  otherwise keep looking for the new Parent
		*/
		return (new Person(this->info, this->children->birth(newParent, newBaby)));
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
	
	//append: ListOfDFTs, PersonInfo->ListOfDFTs
	//Purpose: To add a new person to a family tree
	ListOfDFTs* append ( PersonInfo* newBaby ) 
	//Template: this, this->first, this->rest, this->rest->append(newBaby), newBaby
	{
    return new oneDFT( this->first, this->rest->append(newBaby));
	}
	
	//birth: ListOfDFTs, string, string, string, int, bool->oneDFT
	//Purpose: to add a new child to a given parent
	ListOfDFTs* birth(const char* newParent, PersonInfo* newBaby)
	//Template: this, this->first, this->rest, this->rest->birth(newParent, newBaby), newParent, newBaby
	{
	/*Test 1
		KevinK->birth("Sade", newBabyI)
			this = SadeD, KevinK2
			this->First = SadeD
			this->rest = KevinK2
			this->rest->birth("Sade", newBabyI) = KevinK2
			ans = SadeD(plus newBaby), KevinK2
		return new oneDFT(this->first->birth(newParent, newBaby), this->rest)
	*/
	
	/*Test 2
		JimK->birth("Sade", newBabyI)
			this = NancyD, JimK2
			this->First = NancyD
			this->rest = JimK2
			this->rest->birth("Sade", newBabyI) = JimK2(plus newBaby)
			ans = NancyD, JimK2(plus newBaby)
		return new oneDFT(this->first, this->rest->birth(newParent, newBaby))
	*/
	
	/*Generalize Tests 1 and 2
		we see that we need a new oneDFT and that we we need to check both first and rest
		to see who in the list is suppossed to be having the new baby
	*/
	return new oneDFT(this->first->birth(newParent, newBaby), this->rest->birth(newParent, newBaby));
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
	
	//append: ListOfDFTs, PersonInfo->ListOfDFTs
	//Purpose: To add a new person to a family tree
	ListOfDFTs* append (PersonInfo* newBaby) 
	//Template: this, newBaby
	{
	/*Test 1
		this = !
		ans = newBaby, mt
	return(new oneDFT(new Person(newBaby, new mtLoDFTs()), (new mtLoDFTs())))
	
	we know that the only time append on an emptylist is called is when the parent has
	been found and we are trying to add the baby at the end of their kids
	*/
    return (new oneDFT(new Person(newBaby, new mtLoDFTs()), (new mtLoDFTs())));
	}
	
	
	//birth: ListOfDFTs, string, string, string, int, bool->mtLoDFTs
	//Purpose: to add a new child to a given parent
	ListOfDFTs* birth(const char* newParent, PersonInfo* newBaby)
	//Template: this, newParent, newBaby
	{
	/*Test 1
		mt->birth("Kevin", newBabyI)
			this = !
			ans = !
		return new mtLoDFTs()
	*/
	return (new mtLoDFTs());
	
	}
};

// main : -> number
int main () {

	PersonInfo* FrankI = new PersonInfo("Frank", "Green", 100, false);
	PersonInfo* MargretI = new PersonInfo("Margret", "Green", 70, false);
	PersonInfo* JimI = new PersonInfo("Jim", "Blue", 65, true);
	PersonInfo* NancyI = new PersonInfo("Nancy", "Blue", 40, true);
	PersonInfo* NeilI = new PersonInfo("Neil", "Green", 38, true);
	PersonInfo* AmyI = new PersonInfo("Amy", "Blue", 35, true);
	PersonInfo* KatieI = new PersonInfo("Katie", "Brown", 8, true);
	PersonInfo* AnnabelleI = new PersonInfo("Annabelle", "Blue", 4, false);
	PersonInfo* NatalieI = new PersonInfo("Natalie", "Blue", 2, true);
	PersonInfo* EmilyI = new PersonInfo("Emily", "Green", 34, true);
	PersonInfo* BeckyI = new PersonInfo("Becky", "Blue", 32, true);
	PersonInfo* SpencerI = new PersonInfo("Spencer", "Brown", 4, true);
	PersonInfo* EstherI = new PersonInfo("Esther", "Blue", 1, true);
	PersonInfo* KevinI = new PersonInfo("Kevin", "Blue", 27, false);
	PersonInfo* SadeI = new PersonInfo("Sade", "Blue", 4, true);
	PersonInfo* MadalynI = new PersonInfo("Madalyn", "Green", 2, false);
	PersonInfo* MeganI = new PersonInfo("Megan", "Blue", 25, true);
	PersonInfo* JennyI = new PersonInfo("Jenny", "Blue", 23, true); 
	PersonInfo* newBabyI = new PersonInfo("New Baby", "Blue", 0, true);

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
	Person* newBabyD = new Person( newBabyI, (new mtLoDFTs()));

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
	NancyD->birth("Nancy", newBabyI)->show();
	printf("but should be \n Nancy, newBaby, mtLoDFTS \n");
	
printf("\nthe answer is \n");
	KevinD->birth("Sade", newBabyI)->show();
	printf("but should be \n Kevin, Sade, newBaby, mt, mt, Madalyn, mt, mt\n");
	
printf("\nthe answer is \n");
	KevinK->birth("Sade", newBabyI)->show();	
	printf("but should be \n Sade, newBaby, mt, Madalyn, mt, mt\n");
	
printf("\nthe answer is \n");
	FrankD->birth("Katie", newBabyI)->show();	
	printf("but should be \n Frank, Jim, Nancy, mt, Neil, mt, Amy, Katie, newBaby, mt, mt, Annabelle, mt, Natalie, mt,mt, Emily, mt, Becky, Spencer, mt, Esther, mt, mt, Kevin, Sade, mt, Madaylyn, mt, mt, Megan, mt, Jenny, mt, mt, Magret, mt, mt\n");
	
printf("\nthe answer is \n");
	mt->birth("Katie", newBabyI)->show();	
	printf("\nbut should be \n new mtLoDFTs() \n");
	
return 0;
/*Substitution
	I RAN OUT OF TIME.  I HOPE THIS DOES NOT COME BACK TO HAUNT ME!
*/
}