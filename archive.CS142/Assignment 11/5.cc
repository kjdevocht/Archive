/*Kevin DeVocht Assignment 11 Exercise 5. Write a function called insert that takes a number and a sorted list of numbers and 
returns a new sort list of number where the new number is added in the correct spot. [Sorted meaning the numbers go from smallest to largest.]
*/

#include <stdio.h>
#include <math.h>
#include <string.h>


#include <stdio.h>
#include <math.h>
#include <string.h>

//A listOfNumbers is either
// a number
//or, a emptyLst

class listOfNumbers {
public:
	// Contract: show : listOfNumbers-> int
	virtual int show () = 0;
	//Contract: insert:listOfNumbers int ->listOfNumbers
	//Purpose: to insert a number into a sorted listed in the right location
	virtual listOfNumbers* insert (int unsorted) = 0;
};



//A number is a
// new number (num, rest)
//where
//num is a number
// and rest is a listOfNumbers
class number: public listOfNumbers {
public:
	int num;
	listOfNumbers* rest;

number (int num0, listOfNumbers* rest0) {
	this->num = num0;
	this->rest = rest0;
}


	// Contract: show : number -> int
	int show () {
    // Template: this, this->num, this->rest, this->rest->show()
		printf ("new number ( %d, ", this->num);
		this->rest->show();
		printf (" )");
		return 0;
	}
//Contract: insert:listOfNumbers int ->listOfNumbers
//Purpose: to insert a number into a sorted listed in the right location
listOfNumbers* insert (int unsorted){
	if (this->num > unsorted) {
return (new number(this->num, (new number(unsorted,this->rest->insert(unsorted)))));}
//Template: this, this->num, this->rest, this->rest->insert(), unsorted
/*Test 1
	four->insert(5)
		this->num = 4
		this->rest = 2,1
		unsorted = 5
		ans = 5,4,2,1
	return (new number(5(new number(4(new number(2(new number(1(new emptyLst())))))))));
*/
return (new number(unsorted,(new number(this->num,this->rest))));
}

};

//An emptyLst is a
// new emptyLst ()
//where
//there is nothing because it is the end
class emptyLst: public listOfNumbers {
public:

emptyLst () {
}

	// show : emptyLst -> int
	int show () {
    // Template: this
		printf ( "new emptyLst ()" );
    return 0;
	}

//Contract: insert:emptyLst int ->listOfNumbers
//Purpose: to insert a number into a sorted listed in the right location
listOfNumbers* insert (int unsorted) {
//Template: this, unsorted
/*Test 1
	last->insert()
		this = !
		ans = 0
	return 0
*/
return (new emptyLst());

}
};


int main () {

listOfNumbers* last = new emptyLst ();

listOfNumbers* one = new number(1, last);

listOfNumbers* two = new number (2, one);

listOfNumbers* four = new number(4, two);


listOfNumbers* one2 = new number(1, last);

listOfNumbers* two2 = new number (2, one2);

listOfNumbers* three2= new number(3, two2);

listOfNumbers* four2 = new number(7, three2);



listOfNumbers* one3 = new number(1, last);

listOfNumbers* two3 = new number (2, one3);

listOfNumbers* four3 = new number(4, two3);

listOfNumbers* five3 = new number(5, four3);


  printf("The answer is \n  ");
  (four->insert(5))->show();
  printf("\n, but should be\n  ");
  five3->show();
  printf("\n");


}