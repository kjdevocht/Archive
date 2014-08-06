/*Kevin DeVocht Assignment 11 Exercise 3. Write the function nameRobot that takes a list of toy 
descriptions (strings) and returns a more specific list where robot has been replaced with wally.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>

// streq : string string -> boolean
bool streq ( const char* l, const char* r ) {
  return strcmp(l,r) == 0;
}

//A listOfToys is either
//a toy
//or an emptyLst
class listOfToys {
	public:
		// Contract: show : listOfToys-> int
		virtual int show () = 0;
		//Contract: nameRobot: listOfToys->listOfToys
		//Purpose: to change any robot to wally
		virtual listOfToys* nameRobot () =0;
};


//a toy is a
// new toy(name, rest)
//where
// a name is a string
// and rest is a listOfToys
class toy : public listOfToys {
	public:
		const char* name;
		listOfToys* rest;
		
	toy(const char* name0, listOfToys* rest0){
		this->name = name0;
		this->rest = rest0;
	}

	
	// Contract: show : cost -> int
	int show ( ) {
    // Template: this, this->name, this->rest, this->rest->show()
		printf ("new toy ( %s, ", this->name);
		this->rest->show();
		printf (" )");
		return 0;
	}
	
	//Contract: nameRobot: toy->listOfToys
	//Purpose: to change any robot to wally
	listOfToys* nameRobot () {
	//Template: this, this->name, this->rest, this->rest->nameRobot(), this->rest->show()
	if (streq(this->name, "robot")) {return (new toy("wally", this->rest->nameRobot()));}
	/*Test 1
		robot->nameRobot()
			this->name = "robot"
			this->rest = "Silly Putty", "Nintendo Entertainment System", "Polly Pocket","Hot Wheels"
			ans = "wally","Silly Putty", "Nintendo Entertainment System", "Polly Pocket","Hot Wheels"
		return (new toy("wally", silly_putty))
	*/
	else
		/*Test 2
		silly_putty->nameRobot()
			this->name = "Silly Putty"
			this->rest ="Nintendo Entertainment System", "Polly Pocket","Hot Wheels"
			ans = "Silly Putty", "Nintendo Entertainment System", "Polly Pocket","Hot Wheels"
		return (new toy("Silly Putty", nintendo_NES))
	*/
	/*Generalize Tests 1 and 2
		We can see from the two tests that there are at least two different returns needed so we will have to use an if statement
	*/
	{return (new toy(this->name, this->rest->nameRobot()));}
	}

};


//an emptyLst is
// a new emptyLst()
//where there is nothing inside because it is empty
class emptyLst : public listOfToys {
	public:
	emptyLst(){
	}
	
	// show : emptyLst -> int
	int show ( ) {
    // Template: this
		printf ( "new emptyLst ()" );
    return 0;
	}
	
		//Contract: nameRobot: listOfToys->listOfToys
		//Purpose: to change any robot to wally
		listOfToys* nameRobot () {
		//Template: this
		
		/*Test 1
			emptyLst->nameRobot()
			this = !
		return (new emptyList())
		*/
		return (new emptyLst());
		}
};


int main () {
listOfToys* none = new emptyLst();
listOfToys* hot_wheels = new toy("Hot Wheels", none);
listOfToys* polly_pocket = new toy("Polly Pocket", hot_wheels);
listOfToys* nintendo_NES = new toy("Nintendo Entertainment System", polly_pocket);
listOfToys* silly_putty = new toy("Silly Putty", nintendo_NES);
listOfToys* robot = new toy("robot", silly_putty);
listOfToys* lego_castle = new toy("Lego Castle", robot);

listOfToys* hot_wheels2 = new toy("Hot Wheels", none);
listOfToys* polly_pocket2 = new toy("Polly Pocket", hot_wheels2);
listOfToys* nintendo_NES2 = new toy("Nintendo Entertainment System", polly_pocket2);
listOfToys* silly_putty2 = new toy("Silly Putty", nintendo_NES2);
listOfToys* robot2 = new toy("wally", silly_putty2);
listOfToys* lego_castle2 = new toy("Lego Castle", robot2);

listOfToys* robot01 = new toy("robot", none);
listOfToys* robot02 = new toy("robot", robot01);
listOfToys* robot03 = new toy("robot", robot02);
listOfToys* robot04 = new toy("robot", robot03);
listOfToys* robot05 = new toy("robot", robot04);

listOfToys* wally01 = new toy("wally", none);
listOfToys* wally02 = new toy("wally", wally01);
listOfToys* wally03 = new toy("wally", wally02);
listOfToys* wally04 = new toy("wally", wally03);
listOfToys* wally05 = new toy("wally", wally04);





  printf("The answer is \n  ");
  (lego_castle->nameRobot())->show();
  printf("\n, but should be\n  ");
  lego_castle2->show();
  printf("\n");
  
   printf("The answer is \n  ");
  (robot05->nameRobot())->show();
  printf("\n, but should be\n  ");
  wally05->show();
  printf("\n");
  
  
   printf("The answer is \n  ");
  (silly_putty->nameRobot())->show();
  printf("\n, but should be\n  ");
  silly_putty2->show();
  printf("\n");
  
  printf("The answer is \n  ");
  (none->nameRobot())->show();
  printf("\n, but should be\n  ");
  none->show();
  printf("\n");
  
  /*Substitution
	1. robot02->nameRobot()
	
	2. new toy("robot", robot01)->nameRobot()
	
	3. if (streq(this->name, "robot")) {return (new toy("wally", this->rest->nameRobot()));} else {return (new toy(this->name, this->rest->nameRobot()));}
	
	4. if (streq(new toy("robot", robot01)->name, "robot")) {return (new toy("wally", new toy("robot", robot01)->rest->nameRobot()));} else {return (new toy(new toy("robot", robot01)->name, new toy("robot", robot01)->rest->nameRobot()));}
	
	5. if (streq("robot", "robot")) {return (new toy("wally", robot01->nameRobot()));} else {return (new toy("robot", robot01)->nameRobot());}
	
	6. if (strcmp(l,r) == 0) {return (new toy("wally", robot01->nameRobot()));} else {return (new toy("robot", robot01)->nameRobot());}
	
	7. if (strcmp("robot","robot") == 0) {return (new toy("wally", robot01->nameRobot()));} else {return (new toy("robot", robot01)->nameRobot());}
	
	8. if (strcmp(0) == 0) {return (new toy("wally", robot01->nameRobot()));} else {return (new toy("robot", robot01)->nameRobot());}
	
	9. if (true) {return (new toy("wally", robot01->nameRobot()));} else {return (new toy("robot", robot01)->nameRobot());}
	
	10. return (new toy("wally", robot01->nameRobot()));
	
	11. return (new toy("wally", new toy("robot", none)->nameRobot()));
	
	12. return (new toy("wally", if (streq(this->name, "robot")) {return (new toy("wally", this->rest->nameRobot()));} else {return (new toy(this->name, this->rest->nameRobot()))));}
	
	13. return (new toy("wally", if (streq( new toy("robot", none)->name, "robot")) {return (new toy("wally",  new toy("robot", none)->rest->nameRobot()));} else {return (new toy( new toy("robot", none)->name,  new toy("robot", none)->rest->nameRobot()))));}
	
	14. return (new toy("wally", if (streq("robot", "robot")) {return (new toy("wally", none->nameRobot()));} else {return (new toy("robot", none->rest->nameRobot()))));}
	
	15. return (new toy("wally", if (strcmp(l,r) == 0) {return (new toy("wally", none->nameRobot()));} else {return (new toy("robot", none->rest->nameRobot()))));}
	
	16. return (new toy("wally", if (strcmp("robot","robot") == 0) {return (new toy("wally", none->nameRobot()));} else {return (new toy("robot", none->rest->nameRobot()))));}
	
	17. return (new toy("wally", if (strcmp(0) == 0) {return (new toy("wally", none->nameRobot()));} else {return (new toy("robot", none->rest->nameRobot()))));}
	
	18. return (new toy("wally", if (true) {return (new toy("wally", none->nameRobot()));} else {return (new toy("robot", none->rest->nameRobot()))));}
	
	19. return (new toy("wally", (new toy("wally", none->nameRobot()))));
	
	20. return (new toy("wally", (new toy("wally", (new emptyLst())))));
*/
  }