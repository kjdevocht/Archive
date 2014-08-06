/*Kevin DeVocht Assignment 10 Exercise 4. Develop a function longestString that returns the longest string in a list of strings.
*/



#include <stdio.h>
#include <math.h>
#include <string.h>


//Contract:max: string string->string
//Purpose: To see which strin is larger
const char* max (const char* a, const char* b) {
if (strlen(a)>strlen(b)) {return a;} else {return b;}
}

// a listOfStrings is either
//a string
// or an emptyString
class listOfStrings {
public:

//Contract: longestString: listOfStrings->int
//Purpose: To find the longest string in a list
virtual const char* longestString ()= 0;
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

//Contract: longestString: string->int
//Purpose: To find the longtest string in a list
const char* longestString () {
//Template: this, this->stuff, this->restOfList, this->restOfList->longestString()
/*Test 1
	lots->longestString()
		stuff = "lots of stuff"
		restOfList = "even more stuff"
		ans = 
	return 	"even more stuff"
*/

/*Test 2
	the_most->longestString()
		stuff = "the most stuff you will ever see"
		restOfList = "even more stuff", "lots of stuff"
		ans =  "the most stuff you will ever see"
	return 	 "the most stuff you will ever see"
*/


/*Generalize Tests 1 and 2
	Call the max function which has been modified to return strings rather than ints to return the longest string in any list
*/
return max(this->stuff, this->restOfList->longestString());
}

};




//an emptyString is
//a new emptyString()
//where there is nothing inside because it is the end of the list
class emptyString: public listOfStrings {
public:

emptyString(){
}

//Contract: longestString: ->int
//Purpose: To the longtest string in a list
const char* longestString (){
//Template: this
/*Test 1
	last->longestString()
		this= !
		ans = ""
*/
return "";
}

};

int main () {


listOfStrings* last = new emptyString();
listOfStrings* lots = new string("lots of stuff", last);
listOfStrings* even_more = new string("even more stuff", lots);
listOfStrings* the_most = new string("the most stuff you will ever see", even_more);
listOfStrings* too_much = new string("way too much stuff", the_most);



printf("the answer is %s but should be %s\n", even_more->longestString(), "even more stuff");
printf("the answer is %s but should be %s\n", the_most->longestString(), "the most stuff you will ever see");
printf("the answer is %s but should be %s\n", last->longestString(), "");

/*Substitiution

	1. even_more->longestString()
	
	2. new string("even more stuff", lots)->longestString()
	
	3. max(this->stuff, this->restOfList->longestString())
	
	4. max("even more stuff", lots->longestString())
	
	5. max("even more stuff", new string("lots of stuff", last))
	
	6. max("even more stuff",max(this->stuff, this->restOfList->longestString());
	
	7. max("even more stuff",max("lots of stuff", last->longestString());
	
	8. max("even more stuff",max("lots of stuff", new emptyString()->longestString());
	
	9. max("even more stuff",max("lots of stuff", "");
	
	10. max("even more stuff", if (strlen(a)>strlen(b)) {return a;} else {return b;}
	
	11. max("even more stuff", if (strlen("lots of stuff")>strlen("")) {return a;} else {return b;}
	
	12. max("even more stuff", if (14>0) {return a;} else {return b;}
	
	13. max("even more stuff", "lots of stuff")
	
	14. if (strlen(a)>strlen(b)) {return a;} else {return b;}
	
	15. if (strlen("even more stuff")>strlen("lots of stuff")) {return a;} else {return b;}
	
	16. if (16>14) {return a;} else {return b;}
	
	16. return a;
	
	16. return "even more stuff";
*/
	
}