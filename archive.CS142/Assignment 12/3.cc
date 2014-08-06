/*Kevin DeVocht Assignment 12 Exercise 3. Using the notes from class (if you want), 
develop the function recall, which consumes the name of a toy ty and an inventory 
and produces an inventory that contains all items of the input with the exception 
of those labeled ty.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>

// booleanToString : boolean -> string
const char* booleanToString ( bool it ) {
  if ( it ) { return "true"; } else { return "false"; }
}

// streq : string string -> boolean
bool streq ( const char* l, const char* r ) {
  return strcmp(l,r) == 0;
}

// A Toy is a
//  new Toy( name, price )
// where
//  name is a string
//  price is a double
class Toy {
public:
  const char* name;
  double price;
  

  Toy ( const char* name0, double price0 ) {
    this->name = name0;
    this->price = price0;
  }
//Contract: show : Toy -> int
  int show () {
    return printf("new Toy(\"%s\", %f)", this->name, this->price);
  }

  //Contract: rightName: Toy string ->bool
  //Purpose: To see if the given Toy has the same name as the given name
  bool rightName (const char* theName) {

 /*Test 1
	bellaFig->rightName("Bella")
		this->name = "Bella"
		theName = "Bella"
		ans = true
	return true
  */

  /*Test 2	
  bellaFig->rightName("Edward")
		this->name = "Bella"
		theName = "Edward"
		ans = false
	return false
  */
  
  /*Generalize Tests 1 and 2
	We can see that this is a simple comparison of two 
	strings so call streq and return the results
*/
  return (streq(this->name,theName));
  }

};

// An Inventory is
//  EmptyInv
//  OneMoreToy
class Inventory {
public:
  
  virtual int show () = 0; 
  
  //Contract: recall: Inventory string -> Inventory
  //Purpose: To remove toys with a given name from Inventory
  virtual Inventory* recall (const char* theName) = 0;
};

// A OneMoreToy is a
//  new OneMoreToy( first, rest )
// where
//  first is a Toy
//  rest is a Inventory
class OneMoreToy : public Inventory {
public:
  Toy* first;
  Inventory* rest;

  OneMoreToy ( Toy* first0, Inventory* rest0 ) {
    this->first = first0;
    this->rest = rest0;
  }
//Contract: show : OneMoreToy -> int
  int show () {
  // Template: this, this->first, this->rest, this->rest->show()
    printf("new OneMoreToy( ");
    this->first->show();
    printf(", ");
    this->rest->show();
    return printf(" )");
  }
  
  //Contract: recall: Inventory string -> Inventory
  //Purpose: To remove toys with a given name from Inventory
  Inventory* recall (const char* theName) {
if (this->first->rightName(theName)) { 
 /* Test 1
	bellaStore->recall("Bella")
		this->first = bellaFig
		this->rest = eddieStore
		ans = new OneMoreTory( eddieFig, jacobStore )
	return new OneMoreTory( eddieFig, jacobStore )
  */
  return this->rest->recall(theName);
  }
  else {
  /*Test 2
	renesmeStore->recall("Bayonetta")
		this->first = renesmeStore
		this->rest = bayoStore
		ans = (new OneMoreToy ( renesmeFig, mtStore )new EmptyInv())
	return (new OneMoreToy ( renesmeFig, mtStore )new EmptyInv());
  */
  
    /*Test 3
	renesmeStore->recall("Bella")
		this->first = renesmeStore
		this->rest = bayoStore
		ans = (new OneMoreToy ( renesmeFig, bayoStore )new EmptyInv())
	return (new OneMoreToy ( renesmeFig, mtStore ) new OneMoreToy( bayoFig, mtStore )new EmptyInv());
  */
  
 /*Generalize Tests 1,2 and 3
	We see that if the given name is not in the Inventory just return the 
	old Inventory but if it is return a new Inventory without it and keep 
	looking to see if there are more
*/
  return (new OneMoreToy(this->first, this->rest->recall(theName)));
  }
  }

};


// A EmptyInv is a
//  new EmptyInv()
// where
class EmptyInv : public Inventory {
public:
  EmptyInv() { }

//Contract: show : EmptyInv -> int
  int show () {
    return printf("new EmptyInv()");
  }
  
  //Contract: recall: Inventory string -> EmptyInv
  //Purpose: To remove toys with a given name from Inventory
  EmptyInv* recall (const char* theName) {
  //Template: this, theName
  /*Test 1
		this = !
	return EmptyInv ()
  */
  return (new EmptyInv ());
  }
};



// main : -> number
int main () {


  // Nerd dot com action figure store
  Toy* bayoFig = new Toy( "Bayonetta", 1000.0 );
  Toy* renesmeFig = new Toy( "Renesme", 700.0 );
  Toy* jacobFig = new Toy( "Jacob", 7000.0 );
  Toy* eddieFig = new Toy( "Edward", 10.0 );
  Toy* bellaFig = new Toy( "Bella", 10000.0 );
  
  Inventory* mtStore = new EmptyInv ();
  Inventory* bayoStore = new OneMoreToy ( bayoFig, mtStore );
  Inventory* renesmeStore = new OneMoreToy ( renesmeFig, bayoStore );
  Inventory* jacobStore = new OneMoreToy ( jacobFig, renesmeStore );
  Inventory* eddieStore = new OneMoreToy ( eddieFig, jacobStore );
  Inventory* bellaStore = new OneMoreToy ( bellaFig, eddieStore );
  
  
  
  Inventory* eddieStore1 = new OneMoreToy ( eddieFig, renesmeStore );
  Inventory* bellaStore1 = new OneMoreToy ( bellaFig, eddieStore1 );
  
  
  printf("The answer is \n  ");
  (bellaStore->recall("Bella"))->show();
  printf("\n but should be\n  ");
  eddieStore->show();
  printf("\n");


  printf("The answer is \n  ");
  (bellaStore->recall("Jacob"))->show();
  printf("\n but should be\n  ");
  bellaStore1->show();
  printf("\n");  
  
  printf("The answer is \n  ");
  (mtStore->recall("Jacob"))->show();
  printf("\n but should be\n  ");
  mtStore->show();
  printf("\n");
  
  
 /* Substitution
	1. renesmeStore->recall("Renesme")
	
	2. new OneMoreToy ( renesmeFig, bayoStore )->recall("Renesme")
	
	3. if (this->first->rightName(theName)) {return this->rest->recall(theName);} else {return (new OneMoreToy(this->first, this->rest->recall(theName)));}
	
	4. if (this->new OneMoreToy ( renesmeFig, bayoStore )->rightName(theName)) {return new OneMoreToy ( renesmeFig, bayoStore )->rest->recall(theName);} else {return (new OneMoreToy( renesmeFig, bayoStore )->first, new OneMoreToy ( renesmeFig, bayoStore )->rest->recall(theName));}
	
	5. if (renesmeFig->rightName(theName)) {return bayoStore->recall(theName);} else {return (new OneMoreToy(renesmeFig, bayoStore->recall(theName)));}

	6. if (new Toy( "Renesme", 700.0 )->rightName(theName)) {return (new OneMoreToy( "Bayonetta", 1000.0 ), mtStore )->recall(theName);} else {return (new OneMoreToy(new OneMoreToy( "Renesme", 700.0 ), new OneMoreToy ( bayoFig, mtStore )->recall(theName)));}
	
	7. if (return (streq(this->name,theName))) {return (new OneMoreToy( "Bayonetta", 1000.0 ), mtStore )->recall(theName);} else {return (new OneMoreToy(new OneMoreToy( "Renesme", 700.0 ), new OneMoreToy ( bayoFig, mtStore )->recall(theName)));}
	
	8. if (return (streq(this->"Renesme","Renesme"))) {return (new OneMoreToy( "Bayonetta", 1000.0 ), mtStore )->recall(theName);} else {return (new OneMoreToy(new OneMoreToy( "Renesme", 700.0 ), new OneMoreToy ( bayoFig, mtStore )->recall(theName)));}
	
	9. if (return (return strcmp(l,r) == 0)) {return (new OneMoreToy( "Bayonetta", 1000.0 ), mtStore )->recall(theName);} else {return (new OneMoreToy(new OneMoreToy( "Renesme", 700.0 ), new OneMoreToy ( bayoFig, mtStore )->recall(theName)));}
	
	10. if (return (return strcmp("Renesme","Renesme") == 0)) {return (new OneMoreToy( "Bayonetta", 1000.0 ), mtStore )->recall(theName);} else {return (new OneMoreToy(new OneMoreToy( "Renesme", 700.0 ), new OneMoreToy ( bayoFig, mtStore )->recall(theName)));}

	11. if (return (return strcmp(0 == 0))) {return (new OneMoreToy( "Bayonetta", 1000.0 ), mtStore )->recall(theName);} else {return (new OneMoreToy(new OneMoreToy( "Renesme", 700.0 ), new OneMoreToy ( bayoFig, mtStore )->recall(theName)));}
	
	12. if (return (return true)) {return (new OneMoreToy( "Bayonetta", 1000.0 ), mtStore )->recall(theName);} else {return (new OneMoreToy(new OneMoreToy( "Renesme", 700.0 ), new OneMoreToy ( bayoFig, mtStore )->recall(theName)));}
	
	13. if (true) {return (new OneMoreToy( "Bayonetta", 1000.0 ), mtStore )->recall(theName);} else {return (new OneMoreToy(new OneMoreToy( "Renesme", 700.0 ), new OneMoreToy ( bayoFig, mtStore )->recall(theName)));}
	
	14. return (new OneMoreToy( "Bayonetta", 1000.0 ), mtStore )->recall(theName);	
	
	15. return (new OneMoreToy( "Bayonetta", 1000.0 ), new EmptyInv () )->recall("Renesme");	
	
	16. return (new OneMoreToy( "Bayonetta", 1000.0 ), return (new EmptyInv ()));	
	
	17. return (new OneMoreToy( "Bayonetta", 1000.0 ), new EmptyInv ());	
*/
	
  }