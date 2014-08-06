/*Kevin DeVocht Assignment 9 Exercise 3. Use your list of numbers data definition and 
write a function called containsFiveHuh that returns true if the number list contains 5.
*/


//time: 1:45
#include <stdio.h>
#include <math.h>
#include <string.h>

// booleanToString : boolean -> string
const char* booleanToString ( bool it ) {
  if ( it ) { return "true"; } else { return "false"; }
}
//^copied above code from class code^




//A listOfNumbers is either
// a number
//or, a endOfList
class listOfNumbers {
public:
	//Contract: containsFiveHuh: listOfNumbers->boolean
	//Purpose: To see if a given list contains the number 5 or not
	virtual bool containsFiveHuh () = 0;
};





//A number is a
// new number (num, restOfList)
//where
//num is a number
// and restOfList is a listOfNumbers
class number: public listOfNumbers {
public:
	int num;
	listOfNumbers* restOfList;

number (int num0, listOfNumbers* restOfList0) {
	this->num = num0;
	this->restOfList = restOfList0;
}


//Contract: containsFiveHuh: number->boolean
//Purpose: To see if a given number is number 5 or not
bool containsFiveHuh() {
//Template: this, this->num, this->restOfList, this->restOfList->containsFiveHuh()
if (!(this->num == 5)){
/*Test 1
	six->containsFiveHuh()
		this->num = 6
		this->restOfList = 5,4,3,2,1
		ans = true
	return true
*/
return this->restOfList->containsFiveHuh(); }



/*Test 2
	three->containsFiveHuh()
		this->num = 3
		this->restOfList = 2,1
		ans = false
	return false
*/

/*Generalize tests 1 and 2
	As we can see by the two tests there are multiple options so we must so an if else statement
	and we must continue to go down the list until we reach a five or the end of the list
*/
else {
return true;
}
}
};




//An endOfList is a
// new endOfList ()
//where
//there is nothing because it is the end
class endOfList: public listOfNumbers {
public:

endOfList () {
}


//Contract: containsFiveHuh: endOfList->boolean
//Purpose: To see if a endOfList is the number 5 or not
bool containsFiveHuh() {
//Template: this
/*Test 1
	last->containsFiveHuh()
		this = !
		ans = true
	return true
*/

//There is only one possible test for this function so there is no need to generalize
return false;
}

};




int main () {

listOfNumbers* last = new endOfList ();

listOfNumbers* one = new number(1, last);

listOfNumbers* two = new number (2, one);

listOfNumbers* three = new number(3, two);

listOfNumbers* four = new number(4, three);

listOfNumbers* five = new number(5, four);

listOfNumbers* six = new number(6, five);

listOfNumbers* seven = new number(7, six);


printf("The answer is %s but it should be %s\n", booleanToString(three->containsFiveHuh()), "false");
printf("The answer is %s but it should be %s\n", booleanToString(four->containsFiveHuh()), "false");
printf("The answer is %s but it should be %s\n", booleanToString(last->containsFiveHuh()), "false");
printf("The answer is %s but it should be %s\n", booleanToString(seven->containsFiveHuh()), "true");
printf("The answer is %s but it should be %s\n", booleanToString(six->containsFiveHuh()), "true");
printf("The answer is %s but it should be %s\n", booleanToString(five->containsFiveHuh()), "true");

/*Substitution
	1. booleanToString(six->containsFiveHuh())
	
	2. booleanToString(new number(6, five)->containsFiveHuh())
	
	3. booleanToString(if (!(this->num == 5)){return this->restOfList->containsFiveHuh(); }else {return true;})
	
	4. booleanToString(if (!(new number(6, five)->num == 5)){return new number(6, five)->restOfList->containsFiveHuh(); }else {return true;})
	
	5. booleanToString(if (!(6 == 5)){return five->containsFiveHuh(); }else {return true;})
	
	6. booleanToString(if (!(false)){return five->containsFiveHuh(); }else {return true;})
	
	7. booleanToString(if (true){return five->containsFiveHuh(); }else {return true;})
	
	8. booleanToString(return five->containsFiveHuh();)
	
	9. booleanToString(new number(5, four)->containsFiveHuh())
	
	10. booleanToString(if (!(this->num == 5)){return this->restOfList->containsFiveHuh(); }else {return true;})
	
	11. booleanToString(if (!(new number(5, four)->num == 5)){return new number(5, four)->restOfList->containsFiveHuh(); }else {return true;})
	
	12. booleanToString(if (!(5 == 5)){return four->containsFiveHuh(); }else {return true;})
	
	13. booleanToString(if (!(true)){return four->containsFiveHuh(); }else {return true;})
	
	14. booleanToString(if (false){return four->containsFiveHuh(); }else {return true;})
	
	15. booleanToString(true)
	
	16. if ( it ) { return "true"; } else { return "false"; }
	
	17. if true  { return "true"; } else { return "false"; }
	
	18. return "true"
*/	


}