/*Kevin DeVocht Assignment 10 Exercise 2. Write a function called convertList that consumes a list of digits and returns the corresponding number. 
The first digit is the least significant digit. (Refer to convert3 if you are unsure what this means.)
*/

// time 1 hour

#include <stdio.h>
#include <math.h>
#include <string.h>

//A listOfNumbers is either
// a number
//or, a endOfList

class listOfNumbers {
public:
	//Contract: convertlist: listOfNumbers->int
	//Purpose: To return a number that corresponds with their place in the list
	virtual int convertList () = 0;
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

//Contract: convertlist: number->int
//Purpose: To return a number that corresponds with their place in the list
int convertList (){
//Template: this, this->num, this->restOfList, restOfList->convertList()
/*Test 1
	three->convertList
		this-> = 3
		this->restOfList = 2, 1
		ans = 1*100 + 2*10 +3*1
	return 123
*/

/*Test 2
	five->convertList
		this-> = 5
		this->restOfList = 4, 3, 2, 1
		ans = 1*10000 + 2*1000 +3*100 + 4*10 + 5*1
	return 12345
*/
/*Generalize Tests 1 and 2
	We can see that for each number in the list it must shift one space to the left so we must multiply the restOfList by ten to account for any length of lists
*/
return (this->num) + (this->restOfList->convertList()*10);
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

//Contract: convertlist: ->int
//Purpose: To return a number that corresponds with their place in the list
int convertList () {
//Template: this
/*Test 1
	last->convertList()
		this = !
		ans = 0
	return 0
*/
return 0;
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

listOfNumbers* ten = new number(10, seven);


printf("The answer is %d but it should be %d\n", three->convertList(), 123);
printf("The answer is %d but it should be %d\n", four->convertList(), 1234);
printf("The answer is %d but it should be %d\n", last->convertList(), 0);
printf("The answer is %d but it should be %d\n", seven->convertList(), 1234567);
printf("The answer is %d but it should be %d\n", ten->convertList(), 12345680);


/*Substitution

	1.three->convertList()
	
	2.new number(3, two)->convertList()
	
	3.return (this->num) + (this->restOfList->convertList()*10);
	
	4.return (3) + (two->convertList()*10);
	
	5.return (3) + (new number (2, one)->convertList()*10);
	
	6.return (3) + ((this->num*10) + (this->restOfList->convertList()*10));
	
	7.return (3) + ((2*10) + (one->convertList()*10*10));
	
	8.return (3) + (20) + (new number(1, last)->convertList()*10*10));
	
	9.return (3) + (20) + ((this->num) + (this->restOfList->convertList()*10)*10*10));
	
	10.return (3) + (20) + ((1*10*10) + (last->convertList()*10)*10*10));
	
	11.return (3) + (20) + (100) + (0*10)*10*10));
	
	12.return (3) + (20) + (100) + (0));
	
	12.return 123;
*/
}