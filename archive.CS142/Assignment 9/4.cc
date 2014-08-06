/*Kevin DeVocht Assignment 9 Exercise 4. Use your list of numbers data definition and write a 
function called allEvenHuh that returns true if the number list contains only even numbers.
*/

//time 2:30

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
	//Contract: allEvenHuh: listOfNumbers->boolean
	//Purpose: To see if a given list is all even or not
	virtual bool allEvenHuh () = 0;
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


//Contract: allEvenHuh: number->boolean
//Purpose: To see if a given number is a even number or not
bool allEvenHuh() {
//Template: this, this->num, this->restOfList, this->restOfList->allEvenHuh()
if (this->num % 2 == 0){
/*Test 1
	evenFive->allEvenHuh()
		this->num = 10
		this->restOfList = 8,6,4,2
		ans = true
	return true
*/
return this->restOfList->allEvenHuh(); }



/*Test 2
	six->containsFiveHuh()
		this->num = 6
		this->restOfList = 5,4,3,2,1
		ans = false
	return false
*/

/*Generalize tests 1 and 2
	As we can see by the two tests there are multiple options so we must so an if else statement
	and we must continue to go to end of the list to ensure that all numbers are even
*/
else {
return false;
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


//Contract: allEvenHuh: endOfList->boolean
//Purpose: To see if a endOfList is an even number or not
bool allEvenHuh() {
//Template: this
/*Test 1
	last->allEvenHuh()
		this = !
		ans = true
	return true
*/

//There is only one possible test for this function so there is no need to generalize
return true;
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



listOfNumbers* evenOne = new number(2, last);

listOfNumbers* evenTwo = new number(4, evenOne);

listOfNumbers* evenThree = new number(6, evenTwo);

listOfNumbers* evenFour = new number(8, evenThree);

listOfNumbers* evenFive = new number(10, evenFour);


printf("The answer is %s but it should be %s\n", booleanToString(three->allEvenHuh()), "false");
printf("The answer is %s but it should be %s\n", booleanToString(evenFour->allEvenHuh()), "true");
printf("The answer is %s but it should be %s\n", booleanToString(last->allEvenHuh()), "true");
printf("The answer is %s but it should be %s\n", booleanToString(evenFive->allEvenHuh()), "true");
printf("The answer is %s but it should be %s\n", booleanToString(six->allEvenHuh()), "false");
printf("The answer is %s but it should be %s\n", booleanToString(five->allEvenHuh()), "false");

/* Substitution
	1. booleanToString(evenFive->allEvenHuh())
	
	2. booleanToString(new number(10, evenFour)->allEvenHuh())
	
	3. booleanToString(if (this->num % 2 == 0){return this->restOfList->allEvenHuh(); } else {return false;})
	
	4. booleanToString(if (new number(10, evenFour)->num % 2 == 0){return (new number(10, evenFour)->restOfList->allEvenHuh(); } else {return false;}))
	
	5. booleanToString(if (10 % 2 == 0){return evenFour->allEvenHuh(); } else {return false;})
	
	6. booleanToString(if (0 == 0){return evenFour->allEvenHuh(); } else {return false;})
	
	7. booleanToString(return evenFour->allEvenHuh())
	
	8. booleanToString(new number(8, evenThree)->allEvenHuh())
	
	9. booleanToString(if (this->num % 2 == 0){return this->restOfList->allEvenHuh(); } else {return false;})
	
	10. booleanToString(if (new number(8, evenThree)->num % 2 == 0){return new number(8, evenThree)->restOfList->allEvenHuh(); } else {return false;})
	
	11. booleanToString(if (8 % 2 == 0){return evenThree->allEvenHuh(); } else {return false;})
	
	12. booleanToString(if (0 == 0){return evenThree->allEvenHuh(); } else {return false;})
	
	13. booleanToString(evenThree->allEvenHuh())
	
	14. booleanToString(if (new number(6, evenTwo)->num % 2 == 0){return new number(6, evenTwo)->restOfList->allEvenHuh(); } else {return false;})
	
	15. booleanToString(if (6 % 2 == 0){return evenTwo->allEvenHuh(); } else {return false;})
	
	16. booleanToString(if (0 == 0){return evenTwo->allEvenHuh(); } else {return false;})
	
	17. booleanToString(evenTwo->allEvenHuh())
	
	18. booleanToString(new number(4, evenOne)->allEvenHuh())
	
	19. booleanToString(if (this->num % 2 == 0){return this->restOfList->allEvenHuh(); } else {return false;})
	
	20. booleanToString(if (new number(4, evenOne)->num % 2 == 0){return new number(4, evenOne)->restOfList->allEvenHuh(); } else {return false;})
	
	21. booleanToString(if (4 % 2 == 0){return evenOne->allEvenHuh(); } else {return false;})
	
	22. booleanToString(if (0 == 0){return evenOne->allEvenHuh(); } else {return false;})
	
	23. booleanToString(evenOne->allEvenHuh())
	
	24. booleanToString(new number(2, last)->allEvenHuh())
	
	25. booleanToString(if (this->num % 2 == 0){return this->restOfList->allEvenHuh(); } else {return false;})
	
	26. booleanToString(if (new number(2, last)->num % 2 == 0){return new number(2, last)->restOfList->allEvenHuh(); } else {return false;})
	
	27. booleanToString(if (2 % 2 == 0){return last->allEvenHuh(); } else {return false;})
	
	28. booleanToString(if (0 == 0){return last->allEvenHuh(); } else {return false;})
	
	29. booleanToString(last->allEvenHuh();)
	
	30. booleanToString(new endOfList ()->allEvenHuh();)
	
	31. booleanToString(return true;)
	
	32. if ( it ) { return "true"; } else { return "false"; }
	
	33. if ( true ) { return "true"; } else { return "false"; }
	
	34. return "true";
*/	

}