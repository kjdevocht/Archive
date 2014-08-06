/*Kevin DeVocht Assignment 11 Exercise 4. Generalize substitute to substitute that takes 
a list of toy descriptions, a target, and new. It replaces all occurrences of the target with the new.
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
		//Contract: substitute: listOfToys string string->listOfToys
		//Purpose: to change any toy to any name
		virtual listOfToys* substitute (const char* target, const char* New) =0;
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

	
	// Contract: show : toy -> int
	int show ( ) {
    // Template: this, this->name, this->rest, this->rest->show()
		printf ("new toy ( %s, ", this->name);
		this->rest->show();
		printf (" )");
		return 0;
	}
	
	//Contract: substitute: toy string string->listOfToys
	//Purpose: to change any toy to any name
	listOfToys* substitute (const char* target, const char* New) {
	//Template: this, this->name, this->rest, this->rest->substitute(), this->rest->show(), target, New
	if (streq(this->name, target)) {return (new toy(New, this->rest->substitute(target, New)));}
	/*Test 1
		lego_castle->substitute("Nintendo Entertainment System", "NES")
			this->name = "Lego Castle"
			this->rest = "robot", "Silly Putty", "Nintendo Entertainment System", "Polly Pocket","Hot Wheels"
			ans = "Lego Castle", "robot","Silly Putty", "NES", "Polly Pocket","Hot Wheels"
		return (new toy(new toy("Lego Castle", new toy("robot",new toy("Silly Putty", new toy("NES", new toy("Polly Pocket",new toy("Hot Wheels"(new emptyLst())))))))))
	*/
	else
		/*Test 2
		silly_putty->substitute("Lego Castle", "King Leo's Castle")
			this->name = "Silly Putty"
			this->rest ="Nintendo Entertainment System", "Polly Pocket","Hot Wheels"
			ans = "Silly Putty", "Nintendo Entertainment System", "Polly Pocket","Hot Wheels"
		return (new toy("Silly Putty", nintendo_NES))
	*/
	/*Generalize Tests 1 and 2
		We can see from the two tests that there are at least two different returns needed so we will have to use an if statement
	*/
	{return (new toy(this->name, this->rest->substitute(target, New)));}
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
	
		//Contract: substitute: emptyLst string string ->listOfToys
		//Purpose: to change any toy to any name
		listOfToys* substitute (const char* target, const char* New){
		//Template: this, target, New
		
		/*Test 1
			emptyLst->substitute()
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


listOfToys* hot_wheels3 = new toy("Hot Wheels", none);
listOfToys* polly_pocket3 = new toy("Polly Pocket", hot_wheels3);
listOfToys* nintendo_NES3 = new toy("NES", polly_pocket3);
listOfToys* silly_putty3 = new toy("Silly Putty", nintendo_NES3);
listOfToys* robot3 = new toy("robot", silly_putty3);
listOfToys* lego_castle3 = new toy("Lego Castle", robot3);


  printf("The answer is \n  ");
  (lego_castle->substitute("Nintendo Entertainment System", "NES"))->show();
  printf("\n, but should be\n  ");
  lego_castle3->show();
  printf("\n");
  
    printf("The answer is \n  ");
  (robot05->substitute("robot", "wally"))->show();
  printf("\n, but should be\n  ");
  wally05->show();
  printf("\n");
  
  
    printf("The answer is \n  ");
  (silly_putty->substitute("Lego Castle", "King Leo's Castle"))->show();
  printf("\n, but should be\n  ");
  silly_putty->show();
  printf("\n");
  
    printf("The answer is \n  ");
  (none->substitute("Lego Castle", "King Leo's Castle"))->show();
  printf("\n, but should be\n  ");
  none->show();
  printf("\n");
  
  /*Substitution
	1. nintendo_NES->substitute("Nintendo Entertainment System", "NES")
	
	2. new toy("Nintendo Entertainment System", polly_pocket)->substitute("Nintendo Entertainment System", "NES")
	
	3. if (streq(this->name, target)) {return (new toy(New, this->rest->substitute(target, New)));} else {return (new toy(this->name, this->rest->substitute(target, New)));}
	
	4. if (streq(new toy("Nintendo Entertainment System", polly_pocket)->name, "Nintendo Entertainment System")) {return (new toy("NES", new toy("Nintendo Entertainment System", polly_pocket)->rest->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy(new toy("Nintendo Entertainment System", polly_pocket)->name, new toy("Nintendo Entertainment System", polly_pocket2)->rest->substitute("Nintendo Entertainment System", "NES")));}
	
	5. if (streq("Nintendo Entertainment System", "Nintendo Entertainment System")) {return (new toy("NES", polly_pocket->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy("Nintendo Entertainment System", polly_pocket)->substitute("Nintendo Entertainment System", "NES"));}
	
	6. if (strcmp(l,r) == 0;) {return (new toy("NES", polly_pocket->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy("Nintendo Entertainment System", polly_pocket)->substitute("Nintendo Entertainment System", "NES"));}
	
	7. if (strcmp("Nintendo Entertainment System","Nintendo Entertainment System") == 0;) {return (new toy("NES", polly_pocket->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy("Nintendo Entertainment System", polly_pocket)->substitute("Nintendo Entertainment System", "NES"));}
	
	8. if (strcmp (0) == 0;) {return (new toy("NES", polly_pocket->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy("Nintendo Entertainment System", polly_pocket)->substitute("Nintendo Entertainment System", "NES"));}
	
	9. if (true) {return (new toy("NES", polly_pocket->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy("Nintendo Entertainment System", polly_pocket)->substitute("Nintendo Entertainment System", "NES"));}
	
	10. return (new toy("NES", polly_pocket->substitute("Nintendo Entertainment System", "NES")));
	
	11. return (new toy("NES", new toy("Polly Pocket", hot_wheels)->substitute("Nintendo Entertainment System", "NES")));
	
	12. return (new toy("NES", if (streq( new toy("Polly Pocket", hot_wheels)->name, "Nintendo Entertainment System")) {return (new toy("NES",  new toy("Polly Pocket", hot_wheels)->rest->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy( new toy("Polly Pocket", hot_wheels)->name,  new toy("Polly Pocket", hot_wheels)->rest->substitute("Nintendo Entertainment System", "NES")))));}
	
	13. return (new toy("NES", if (streq("Polly Pocket", "Nintendo Entertainment System")) {return (new toy("NES", hot_wheels->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy("Polly Pocket", hot_wheels->substitute("Nintendo Entertainment System", "NES")))));}
	
	14. return (new toy("NES", if (strcmp(l,r) == 0;) {return (new toy("NES", hot_wheels->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy("Polly Pocket", hot_wheels->substitute("Nintendo Entertainment System", "NES")))));}
	
	15. return (new toy("NES", if (strcmp("Polly Pocket","Nintendo Entertainment System") == 0;) {return (new toy("NES", hot_wheels->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy("Polly Pocket", hot_wheels->substitute("Nintendo Entertainment System", "NES")))));}
	
	16. return (new toy("NES", if (strcmp(1) == 0;) {return (new toy("NES", hot_wheels->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy("Polly Pocket", hot_wheels->substitute("Nintendo Entertainment System", "NES")))));}
	
	17. return (new toy("NES", if (false) {return (new toy("NES", hot_wheels->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy("Polly Pocket", hot_wheels->substitute("Nintendo Entertainment System", "NES")))));}
	
	18. return (new toy("NES", (new toy("Polly Pocket", hot_wheels->substitute("Nintendo Entertainment System", "NES")))))
	
	19. return (new toy("NES", (new toy("Polly Pocket", new toy("Hot Wheels", none)->substitute("Nintendo Entertainment System", "NES")))))
	
	20. return (new toy("NES", (new toy("Polly Pocket", if (streq(this->name, target)) {return (new toy(New, this->rest->substitute(target, New)));} else {return (new toy(this->name, this->rest->substitute(target, New)))))));}
	
	21. return (new toy("NES", (new toy("Polly Pocket", if (streq(new toy("Hot Wheels", none)->name, "Nintendo Entertainment System")) {return (new toy("NES", new toy("Hot Wheels", none->rest->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy(new toy("Hot Wheels", none)->name, new toy("Hot Wheels", none)->rest->substitute("Nintendo Entertainment System", "NES"))))))));}
	
	22. return (new toy("NES", (new toy("Polly Pocket", if (streq("Hot Wheels", "Nintendo Entertainment System")) {return (new toy("NES", none->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy("Hot Wheels", none->substitute("Nintendo Entertainment System", "NES")))))));}
	
	23. return (new toy("NES", (new toy("Polly Pocket", if (strcmp(l,r) == 0) {return (new toy("NES", none)->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy("Hot Wheels", none->substitute("Nintendo Entertainment System", "NES"))))));}
	
	24. return (new toy("NES", (new toy("Polly Pocket", if (strcmp("Hot Wheels","Nintendo Entertainment System") == 0) {return (new toy("NES", none)->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy("Hot Wheels", none->substitute("Nintendo Entertainment System", "NES"))))));}
	
	25. return (new toy("NES", (new toy("Polly Pocket", if (strcmp(1) == 0) {return (new toy("NES", none->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy("Hot Wheels", none->substitute("Nintendo Entertainment System", "NES")))))));}
	
	26. return (new toy("NES", (new toy("Polly Pocket", if (false) {return (new toy("NES", none->substitute("Nintendo Entertainment System", "NES")));} else {return (new toy("Hot Wheels", none)->substitute("Nintendo Entertainment System", "NES"))))));}
	
	27. return (new toy("NES", (new toy("Polly Pocket", (new toy("Hot Wheels", none->substitute("Nintendo Entertainment System", "NES")))))));}
	
	28. return (new toy("NES", (new toy("Polly Pocket", (new toy("Hot Wheels", new emptyLst()->substitute("Nintendo Entertainment System", "NES")))))));}
	
	29. return (new toy("NES", (new toy("Polly Pocket", (new toy("Hot Wheels", (new emptyLst())))))));}
*/
  }