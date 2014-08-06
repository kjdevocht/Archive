/*Kevin DeVocht Assignment 9 Exercise 5. Make a data definition of lists of booleans. Write a function called allTrueHuh that is true when all the booleans in a list are true.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>

// booleanToString : boolean -> string
const char* booleanToString ( bool it ) {
  if ( it ) { return "true"; } else { return "false"; }
}
//^copied above code from class code^



// a listOfBooleans is either
// a boolean
//or a endOfList
class listOfBooleans {
	public:
	
	//Contract: allTrueHuh: listOfBooleans->bool
	//Purpose: To see if a list of booleans are all true or not
	virtual bool allTrueHuh () = 0;
};


// a boolean is a
// new boolean (tOrF, restOfList)
//where
//tOrF is a bool
// and restOfList is a listOfBooleans
class boolean: public listOfBooleans {
	public:
		bool tOrF;
		listOfBooleans* restOfList;
		
boolean(bool tOrF0, listOfBooleans* restOfList0) {
	this->tOrF = tOrF0;
	this->restOfList = restOfList0;
}

//Contract: allTrueHuh: boolean->bool
//Purpose: To see if a boolean is true or not
bool allTrueHuh () {
//Template: this, this->tOrF, this->restOflist, this->restOfList->allTrueHuh
if (this->tOrF == true) {
/*Test 1
	areBlackBearsBest->allTrueHuh()
		this->tOrF = true
		this->restOfList = true, true, true
		ans = true
	return true
*/


return this->restOfList->allTrueHuh();
}
else {
/*Test 2
	IAmGoingToPassThisClass->allTrueHuh()
		this-tOrF = false
		this->restOfList = true, true, true, true
		ans = false
	return false
*/

/*Generalize Tests 1 and 2
	In order to check the whole list you must check if it is true or not and keep going if it is.  If any one of the answers are false you can stop and return false
*/
return false;
}
}
};

// a endOfList is 
// a new endOfList
//where
// there is nothing inside because it is the end of the list
class endOfList: public listOfBooleans {
	public:
endOfList() {
}	

//Contract: allTrueHuh: restOfList->bool
//Purpose: To see if a list of booleans are all true or not
bool allTrueHuh () {
//Template: this
/*Test 1
	last->allTrueHuh()
		this = !
		ans = true
*/
//Generalize:  This is the only possible test for this function so there is no generalization
return true;
}
};

int main () {
	listOfBooleans* last = new endOfList ();
	listOfBooleans* isSnowWhite = new boolean(true, last);
	listOfBooleans* areRosesRed = new boolean(true, isSnowWhite);
	listOfBooleans* areBlackBearsBest = new boolean(true, areRosesRed);
	listOfBooleans* isThePopeCatholic = new boolean(true, areBlackBearsBest);
	listOfBooleans* IAmGoingToPassThisClass = new boolean(false,isThePopeCatholic);
	
	printf("The answer is %s but it should be %s\n", booleanToString(areRosesRed->allTrueHuh()), "true");
	printf("The answer is %s but it should be %s\n", booleanToString(isThePopeCatholic->allTrueHuh()), "true");
	printf("The answer is %s but it should be %s\n", booleanToString(IAmGoingToPassThisClass->allTrueHuh()), "false");
	printf("The answer is %s but it should be %s\n", booleanToString(last->allTrueHuh()), "true");
	
	
/* Substitution
	1. booleanToString(areBlackBearsBest->allTrueHuh())
	
	2. booleanToString(if (this->tOrF == true) {return this->restOfList->allTrueHuh();}else {return false;})
	
	3. booleanToString(if (new boolean(true, areRosesRed)->tOrF == true) {return new boolean(true, areRosesRed)->restOfList->allTrueHuh();}else {return false;})
	
	4. booleanToString(if (true == true) {return areRosesRed->allTrueHuh();}else {return false;})
	
	5. booleanToString(if (true) {return areRosesRed->allTrueHuh();}else {return false;})
	
	6. booleanToString(areRosesRed->allTrueHuh();)
	
	7. booleanToString(areRosesRed->allTrueHuh();)
	
	8. booleanToString(new boolean(true, isSnowWhite)->allTrueHuh();)
	
	9. booleanToString(if (new boolean(true, isSnowWhite)->tOrF == true) {return new boolean(true, isSnowWhite)->restOfList->allTrueHuh();}else {return false;})
	
	10. booleanToString(if (true == true) {return isSnowWhite->allTrueHuh();}else {return false;})
	
	11. booleanToString(if (true) {return isSnowWhite->allTrueHuh();}else {return false;})
	
	11. booleanToString(isSnowWhite->allTrueHuh())
	
	12. booleanToString(new boolean(true, last)->allTrueHuh())
	
	13. booleanToString(return true;)
	
	14. if ( it ) { return "true"; } else { return "false"; }
	
	15. if ( true ) { return "true"; } else { return "false"; }
	
	16. return "true";
*/
}