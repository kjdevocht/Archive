/*Kevin DeVocht Assignment 9 Exercise 1. Write a data definition for a list of numbers. Write product that consumes a list of numbers and produces the product of the numbers.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>

//A listOfNumbers is either
// a number
//or, a endOfList

class listOfNumbers {
public:
	//Contract: product: listOfNumbers->int
	//Purpose: To find the product of a list of numbers
	virtual int product () = 0;
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

//Contract: product: number->int
//Purpose: To find the product of a number
int product () {
//Template: this, this->num, this->restOfList, this->restOfList->product()
/*Test1
	three->product()
		num = 3
		restOfList = 2:1:!
		ans = 3*2*1
	return 6
*/

/*Test 2
	four->product()
		num = 4
		restOfList = 3:2:1:!
		ans = 4*3*2*1
	return 24
*/

/*Generalize Tests 1 and 2
	In order to generalize these two tests you must multiply num by the rest of the list to account for an unknown length of lists
*/
return this->num*this->restOfList->product();
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

//Contract: product: ->int
//Purpose: To find the product of a number
int product () {
//Template: this
/*Test 1
	last->product()
	this = !
return 1

Generalize:  Because there is only one possible option for endOfList there is only one possible test.  
Also since you are finding the product of a list of number, if you returned 0 it would make the whole list 0 so return 1
*/
return 1;
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


printf("The answer is %d but it should be %d\n", three->product(), 6);
printf("The answer is %d but it should be %d\n", four->product(), 24);
printf("The answer is %d but it should be %d\n", last->product(), 1);
printf("The answer is %d but it should be %d\n", seven->product(), 5040);

/*Substitution
	1. four->product()
	
	2. new number(4, three)->product()
	
	3. return this->num*this->restOfList->product();
	
	4. return new number(4, three)->num*new number(4, three)->restOfList->product();
	
	5. return 4*three->product();
	
	6. return 4*new number(3, two)->product();
	
	7. return 4*this->num*this->restOfList->product();
	
	8. return 4*new number(3, two)->num*new number(3, two)->restOfList->product();
	
	9. return 4*3*two->product();
	
	10. return 4*3*new number (2, one)->product();
	
	11. return 4*3*this->num*this->restOfList->product();
	
	12. return 4*3* new number (2, one)->num* new number (2, one)->restOfList->product();
	
	13. return 4*3*2*one->product();
	
	14. return 4*3*2*new number(1, last)->product;
	
	15. return 4*3*2*this->num*this->restOfList->product();
	
	16. return 4*3*2*new number(1, last)->num*new number(1, last)->restOfList->product();
	
	17. return 4*3*2*1*last->product();
	
	18. return 4*3*2*1*new endOfList()->product();
	
	19. return 4*3*2*1*1;
	
	20. return 24;
*/	

}