/*Kevin DeVocht assignment 15 exercise 1. Write a function called 
dyeGeneration that takes a parent, a generation number, and an eye color 
and produces a parent where everyone in the given generation’s eyes are the 
given color.
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
  //dyeGeneration: PersonInfo, string->PersonInfo
  //Purpose: to change the eye color of a given person to a given color
  PersonInfo* dyeGeneration(const char* eyeColor)
  {
  return (new PersonInfo(this->name, eyeColor, this->age, this->livingness));
  }
};



// A ListOfDFTs is either
//  mtLoDFTs
//  oneDFT
class ListOfDFTs {
public:
	virtual int show () = 0;

	//dyeGeneration: ListOfDFTs, int, string, ->ListOfDFTs
	//Purpose: To return a given generation with all of their eye colors changed to a given eye color
	virtual ListOfDFTs* dyeGeneration(int genNum, const char* eyeColor) = 0;
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
	

	//dyeGeneration: Person, int, string->Person
	//Purpose: To return a given generation with all of their eye colors changed to a given eye color
	Person* dyeGeneration (int genNum, const char* eyeColor)
	//Template: this, this->info, this->children, genNum, eyeColor, this->children->dyeGeneration(....., ......)
	{
		if (genNum == 0)
		{
		/*Test 1
			JennyD->dyeGeneration(0, "Red")
				this = JennyI, mt
				this->info = JennyI
				this->children = mt
				this->children->dyeGeneration(0, "Red") = new mtLoDFTs()
				ans = new PersonInfo("Jenny", "Red", 23, false)
			return this->info->dyeGeneration(genNum, eyeColor) 
		*/
		return (new Person(this->info->dyeGeneration(eyeColor), this->children));
		}
		else
		{
		/*Test 2
			KevinD->dyeGeneration(1, "Red")
				this = KevinI, SadeD, MaddalynD, mt
				this->info = KevinI
				this->children = SadeD, MaddalynD, mt
				this->children->dyeGeneration(1, "Red") = SadeD(Red eyes), MaddalynD(Red eyes), new mtLoDFTs()
				ans = SadeD(Red eyes), MaddalynD(Red eyes), new mtLoDFTs()
			return this->children->dyeGeneration(genNum, eyeColor) 
		*/
		
		/*Test 3
			JimD->dyeGeneration(3, "Red")
				this = JimI, JimK
				this->info = JimI
				this->children = JimK
				this->children->dyeGeneration(3, "Red") = KatieD(Red eyes), Annabelle(Red eyes), Natalie(Red eyes), Spencer(Red eyes), Esther(Red eyes), SadeD(Red eyes), MaddalynD(Red eyes), new mtLoDFTs()
				ans = KatieD(Red eyes), Annabelle(Red eyes), Natalie(Red eyes), Spencer(Red eyes), Esther(Red eyes), SadeD(Red eyes), MaddalynD(Red eyes), new mtLoDFTs()
			return this->children->dyeGeneration(genNum, eyeColor) 
		*/
		
		/*Distinguish Tests
			We can see that if genNum is 0 run dyeGeneration just on info otherwise run on children.  
			From test three we can see that we can incorporate truncate from last assignment 
			to dyeGenerationwe.  it should be able to return all the grandchildren 
			not just the grandchildren from one child 
		*/
		return (new Person(this->info, this->children->dyeGeneration(genNum-1, eyeColor)));
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


	//dyeGeneration: mtLoDFTs, int, string, ->ListOfDFTs
	//Purpose: To return a given generation with all of their eye colors changed to a given eye color
	ListOfDFTs* dyeGeneration(int genNum, const char* eyeColor)
	//Template: this
	{
	/*Test 1
		mt->dyeGeneration(genNum, eyeColor)
			this = !
			ans = !
		return (new mtLoDFTs())
	*/
	return (new mtLoDFTs());
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

	//dyeGeneration: ListOfDFTs, int, string, ->ListOfDFTs
	//Purpose: To return a given generation with all of their eye colors changed to a given eye color
	ListOfDFTs* dyeGeneration(int genNum, const char* eyeColor)
	//Template: this, this->first, this->rest, genNum, eyeColor, this->rest->dyeGeneration(.....,....)
	{
	/*Test 1
		KevinK2->dyeGeneration(0, "Red")
			this = MadalynD, mt
			this->first = MadalynD
			this->rest = mt
			this->rest->dyeGeneration(0, "Red") = mt
			ans = MadalynD(with Red eyes), mt
		return (new oneDFT((this->first)->dyeGeneration(genNum, eyeColor), this->rest->dyeGeneration(genNum, eyeColor)))
	*/
	
	/*Test 2
		KevinK->dyeGeneration(0, "Red")
			this = SadeD, KevinK2
			this->first = SadeD
			this->rest = KevinK2
			this->rest->dyeGeneration(0, "Red") = KevinK2(Madalyn has Red Eyes)
			ans = SadeD(with Red eyes), KevinK2(Madalyn has Red Eyes)
		return (new oneDFT((this->first)->dyeGeneration(genNum, eyeColor), this->rest->dyeGeneration(genNum, eyeColor)))
	*/
	/*Generalize tests 1 and 2
		we see that we need to call dyeGeneration on both first and rest and make a new list which returns
		both calling dyeGeneration.
	*/
	return (new oneDFT((this->first)->dyeGeneration(genNum, eyeColor), this->rest->dyeGeneration(genNum, eyeColor)));   
	}
};



// main : -> number
int main () {

  PersonInfo* FrankI = new PersonInfo("Frank", "Green", 100, false);
  PersonInfo* MargretI = new PersonInfo("Margret", "Green", 70, false);
  PersonInfo* JimI = new PersonInfo("Jim", "Blue", 65, false);
  PersonInfo* NancyI = new PersonInfo("Nancy", "Blue", 40, false);
  PersonInfo* NeilI = new PersonInfo("Neil", "Green", 38, true);
  PersonInfo* AmyI = new PersonInfo("Amy", "Blue", 35, false);
  PersonInfo* KatieI = new PersonInfo("Katie", "Brown", 8, false);
  PersonInfo* AnnabelleI = new PersonInfo("Annabelle", "Blue", 4, false);
  PersonInfo* NatalieI = new PersonInfo("Natalie", "Blue", 2, false);
  PersonInfo* EmilyI = new PersonInfo("Emily", "Green", 34, true);
  PersonInfo* BeckyI = new PersonInfo("Becky", "Blue", 32, true);
  PersonInfo* SpencerI = new PersonInfo("Spencer", "Brown", 4, false);
  PersonInfo* EstherI = new PersonInfo("Esther", "Blue", 1, false);
  PersonInfo* KevinI = new PersonInfo("Kevin", "Blue", 27, false);
  PersonInfo* SadeI = new PersonInfo("Sade", "Blue", 4, false);
  PersonInfo* MaddalynI = new PersonInfo("Maddalyn", "Green", 2, false);
  PersonInfo* MeganI = new PersonInfo("Megan", "Blue", 25, false);
  PersonInfo* JennyI = new PersonInfo("Jenny", "Blue", 23, false); 
  

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
  Person* MaddalynD = new Person( MaddalynI, (new mtLoDFTs()));
  
  
  ListOfDFTs* KevinK2 = (new oneDFT( MaddalynD, mt ));
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
	JennyD->dyeGeneration(0, "Red")->show();
	printf("but should be new PersonInfo(\"Jenny\", \"Red\", 23, false)\n");
	
printf("the answer is \n");
	KevinD->dyeGeneration(1, "Red")->show();
	printf("but should be new Person(new PersonInfo(\"Kevin\", \"Blue\", 27, false),new oneDFT(new Person(new PersonInfo(\"Sade\", \"Red\", 4, false),new mtLoDFTs()),new oneDFT(new Person(new PersonInfo(\"Maddalyn\", \"Red\", 2, false),new mtLoDFTs()),new mtLoDFTs()))");

printf("the answer is \n");
	FrankD->dyeGeneration(3, "Red")->show();
	printf("but should be new Person(new PersonInfo(\"Frank\", \"Green\", 100, false),new oneDFT(new Person(new PersonInfo(\"Jim\", \"Blue\", 65, false\n),new oneDFT(new Person(new PersonInfo(\"Nancy\", \"Blue\", 40, false),new mtLoDFTs()),new oneDFT(new Person(new PersonInfo(\"Neil\", \"Green\", 38, true),new mtLoDFTs()),new oneDFT(new Person(new PersonInfo(\"Amy\", \"Blue\", 35, false\n),new oneDFT(new Person(new PersonInfo(\"Katie\", \"Red\", 8, false),new mtLoDFTs()),new oneDFT(new Person(new PersonInfo(\"Annabelle\", \"Red\", 4, false),new mtLoDFTs()),new oneDFT(new Person(new PersonInfo(\"Natalie\", \"Red\", 2, false),new mtLoDFTs()),new mtLoDFTs())))),new oneDFT(new Person(new PersonInfo(\"Emily\", \"Green\", 34, true),new mtLoDFTs()),new oneDFT(new Person(new PersonInfo(\"Becky\", \"Blue\", 32, true\n),new oneDFT(new Person(new PersonInfo(\"Spencer\", \"Red\", 4, false),new mtLoDFTs()),new oneDFT(new Person(new PersonInfo(\"Esther\", \"Red\", 1, false),new mtLoDFTs()),new mtLoDFTs()))),new oneDFT(new Person(new PersonInfo(\"Kevin\", \"Blue\", 27, false\n),new oneDFT(new Person(new PersonInfo(\"Red\", \"Blue\", 4, false),new mtLoDFTs()),new oneDFT(new Person(new PersonInfo(\"Maddalyn\", \"Red\", 2, false),new mtLoDFTs()),new mtLoDFTs()))),new oneDFT(new Person(new PersonInfo(\"Megan\", \"Blue\", 25, false),new mtLoDFTs()),new oneDFT(new Person(new PersonInfo(\"Jenny\", \"Blue\", 23, false),new mtLoDFTs()),new mtLoDFTs()))))))))),new oneDFT(new Person(new PersonInfo(\"Margret\", \"Green\", 70, false),newmtLoDFTs()),new mtLoDFTs())))");

	
printf("the answer is \n");
	FrankK->dyeGeneration(0, "Red")->show();
	printf("but should be new oneDFT(new Person(new PersonInfo(\"Jim\", \"Red\", 65, false),new oneDFT(new Person(new PersonInfo(\"Nancy\", \"Blue\", 40, false),new mtLoDFTs()),new oneDFT(new Person(new PersonInfo(\"Neil\", \"Green\", 38, true),new mtLoDFTs()),new oneDFT(new Person(new PersonInfo(\"Amy\", \"Blue\", 35, false),new oneDFT(new Person(new PersonInfo(\"Katie\", \"Brown\", 8, false),new mtLoDFTs()),new oneDFT(new Person(new PersonInfo(\"Annabelle\", \"Blue\", 4, false),new mtLoDFTs()),new oneDFT(new Person(new PersonInfo(\"Natalie\", \"Blue\", 2, false),new mtLoDFTs()),new mtLoDFTs())))),new oneDFT(new Person(new PersonInfo(\"Emily\", \"Green\", 34, true),new mtLoDFTs()),new oneDFT(new Person(new PersonInfo(\"Becky\", \"Blue\", 32, true),new oneDFT(new Person(new PersonInfo(\"Spencer\", \"Brown\", 4, false),new mtLoDFTs()),new oneDFT(new Person(new PersonInfo(\"Esther\", \"Blue\", 1, false),new mtLoDFTs()),new mtLoDFTs()))),new oneDFT(new Person(new PersonInfo(\"Kevin\", \"Blue\", 27, false),new oneDFT(newPerson(new PersonInfo(\"Sade\", \"Blue\", 4, false),new mtLoDFTs()),new oneDFT(new Person(new PersonInfo(\"Maddalyn\", \"Green\", 2, false),new mtLoDFTs()),new mtLoDFTs()))),new oneDFT(new Person(new PersonInfo(\"Megan\", \"Blue\", 25, false),new mtLoDFTs()),new oneDFT(new Person(new PersonInfo(\"Jenny\", \"Blue\", 23, false),new mtLoDFTs()),new mtLoDFTs()))))))))),new oneDFT(new Person(new PersonInfo(\"Margret\",\"Red\", 70, false),new mtLoDFTs()),new mtLoDFTs()))\n");	

printf("the answer is \n");
	KevinK2->dyeGeneration(0, "Red")->show();
	printf("but should be new PersonInfo(\"Madalyn\", \"Red\", 2, false)\n");
return 0;
/*Substitution
	I RAN OUT OF TIME.  I HOPE THIS DOES NOT COME BACK TO HAUNT ME!
*/
}