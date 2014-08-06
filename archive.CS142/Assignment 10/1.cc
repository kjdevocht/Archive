/*Kevin DeVocht Assignment 10 Exercise 1. Write a function called containsHuh that 
determines if a string given as an input is in a list of strings.
*/

//time: 30 min



#include <stdio.h>
#include <math.h>
#include <string.h>


// booleanToString : boolean -> string
const char* booleanToString ( bool it ) {
  if ( it ) { return "true"; } else { return "false"; }
}

// a listOfStrings is either
//a string
// or an emptyString
class listOfStrings {
public:

//Contract: containsHuh: listOfStrings string->bool
//Purpose: To find if a list contains a certain string
virtual bool containsHuh (const char* isIt)= 0;
};

//a string is 
// a new string(stuff, restOfList
//Where
//stuff is a string
//and restOfList is a listOfStrings
class string: public listOfStrings{
	public:
		const char* stuff;
		listOfStrings* restOfList;

string(const char* stuff0, listOfStrings* restOfList0) {
	this->stuff = stuff0;
	this->restOfList = restOfList0;
}

//Contract: containsHuh: string string->bool
//Purpose:  To find if a list contains a certain string
bool containsHuh (const char* isIt) {
//Template: this, this->stuff, this->restOfList, this->restOfList->containsHuh()
if (strcmp(this->stuff, isIt) == 0) {
/*
Test 1
	StringyHair->containsHuh("bean")
		this->stuff = 'hair"
		this->restOfList = "cheese" and "bean"
		ans = ans = strcmp(this, isIt) = 1
	return false
*/
return true;}
else 
/*
Test 2
		StringBean->containsHuh("bean")
		this->stuff = "bean"
		this->restOfList = ""
		ans = ans = strcmp(this, isIt) = 
	return true
*/
/*Generalize Tests 1 and 2
	We know that StringyHair also contains StringBean so it should not return false.  You must check the rest of the list to make sure it is not anywhere in the list
*/
{ return this->restOfList->containsHuh(isIt);}
}

};




//an emptyString is
//a new emptyString()
//where there is nothing inside because it is the end of the list
class emptyString: public listOfStrings {
public:

emptyString(){
}
//Contract: containsHuh: string->bool
//Purpose:  To find if a list contains a certain string
bool containsHuh (const char* isIt){
//Template: this, isIt

/*Test1
	emptyString->containsHuh(bean)
		this = ""
		isIt = "bean"
		ans = strcmp(this, isIt) = 1
	return false
*/
/*This is the only possible outcome so there is no need to generalize
*/
return false;
}

};

int main () {


listOfStrings* last = new emptyString();
listOfStrings* StringBean = new string("bean", last);
listOfStrings* StringCheese = new string("cheese", StringBean);
listOfStrings* StringyHair = new string("hair", StringCheese);



printf("the answer is %s but should be %s\n", booleanToString(StringyHair->containsHuh("bean")), "true");
printf("the answer is %s but should be %s\n", booleanToString(last->containsHuh("bean")), "false");
printf("the answer is %s but should be %s\n", booleanToString(StringBean->containsHuh("bean")), "true");

/*Substitution
	1. booleanToString(StringCheese->containsHuh("bean"))
	
	2. booleanToString(new string("cheese", StringBean)->containsHuh("bean"))
	
	3. booleanToString(if (strcmp(this->stuff, isIt) == 0) {return true;}else {return this->restOfList->containsHuh(isIt);})
	
	4. booleanToString(if (strcmp("cheese", "bean") == 0) {return true;}else {return StringBean->containsHuh("bean");})
	
	5. booleanToString(if (false) {return true;}else {return StringBean->containsHuh("bean");})
	
	6. booleanToString(StringBean->containsHuh("bean"))
	
	6. booleanToString(new string("bean", last)->containsHuh("bean"))
	
	7. booleanToString(if (strcmp(this->stuff, isIt) == 0) {return true;}else {return this->restOfList->containsHuh(isIt);})
	
	8. booleanToString(if (strcmp("bean", "bean") == 0) {return true;}else {return last->containsHuh(isIt);})
	
	9. booleanToString(if (true) {return true;}else {return last->containsHuh(isIt);})
	
	10. booleanToString(true)
	
	11. if ( it ) { return "true"; } else { return "false"; }
	
	12. if ( true ) { return "true"; } else { return "false"; }
	
	12. return "true"
*/
}