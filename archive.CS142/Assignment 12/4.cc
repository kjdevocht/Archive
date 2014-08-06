/*Kevin DeVocht Assignment 12 Exercise 4. Write a function, append, which takes two 
lists of numbers and returns a list of numbers where every element of the first list 
is before every element of the second list. For example,

append(
 new OneMoreNumber(1, new OneMoreNumber(2,
      new EmptyListOfNumbers ())),
 new OneMoreNumber(3, new OneMoreNumber(4,
      new EmptyListOfNumbers ()))
) =
 new OneMoreNumber(1, new OneMoreNumber(2,
      new OneMoreNumber(3, new OneMoreNumber(4,
           new EmptyListOfNumbers ()))))
*/


#include <stdio.h>
#include <math.h>
#include <string.h>

//A listOfNumbers is either
// a OneMoreNumber
//or, a EmptyLst

class listOfNumbers {
	public:
		virtual int show () = 0; 
		
		// Contract: append: listOfNumbers listOfNumbers-> listOfNumbers
		//Purpose: to add a listOfNumbers to the end of another list of numbers
		virtual listOfNumbers* append (listOfNumbers* otherList) = 0;
};



//A OneMoreNumber is a
// new OneMoreNumber (first, rest)
//where
//first is a int
// and rest is a listOfNumbers
class OneMoreNumber: public listOfNumbers {
public:
	int first;
	listOfNumbers* rest;

OneMoreNumber (int first0, listOfNumbers* rest0) {
	this->first = first0;
	this->rest = rest0;
}


  //Contract: show : OneMoreNumber -> int
  int show ( ) {
    // Template: this, this->first, this->rest, this->rest->show()
    printf ("new OneMoreNumber ( %d, ", this->first);
    this->rest->show();
    printf (" )");
    return 0;
	}

	
	// Contract: append: OneMoreNumber OneMoreNumber-> listOfNumbers
	//Purpose: to add a listOfNumbers to the end of another list of numbers
	listOfNumbers* append (listOfNumbers* otherList){
	//Template: this, this->first, this->rest, this->rest->append(otherList), otherList->first, otherList->rest, otherList->rest->append(otherList)
	/*Test 1
		four->append(one)
			this->first = new OneMoreNumber(4, none)
			otherList = new OneMoreNumber(1, none)
			ans = new OneMoreNumber(4, none)
	*/
	}
};

//An EmptyLst is a
// new EmptyLst ()
//where
//there is nothing because it is the end
class EmptyLst: public listOfNumbers {
public:

EmptyLst () {
}


  int show () {
    return printf("new EmptyInv()");
  }
  
  	// Contract: append: EmptyLst listOfNumbers-> listOfNumbers
	//Purpose: to add a listOfNumbers to the end of another list of numbers
	listOfNumbers* append (listOfNumbers* otherList){
	//Template: this, otherList
	/*Test 1
		none->append(four)
		ans = new ListofNumbers(new OneMoreNumber(4(new EmptyLst())))
	return (new ListofNumbers(new OneMoreNumber(4(new EmptyLst(0)))))
	*/
	
	/*Test 2
		none->append(none)
		ans = new ListofNumbers(new EmptyLst(0(new EmptyLst(0))))
	return (new ListofNumbers(new EmptyLst(0(new EmptyLst(0)))))
	*/
	}
};


int main (){

listOfNumbers* none = new EmptyLst();
listOfNumbers* one = new OneMoreNumber(1, none);
listOfNumbers* two = new OneMoreNumber(2, one);
listOfNumbers* three = new OneMoreNumber(3, two);

listOfNumbers*four = new OneMoreNumber(4, none);
listOfNumbers* five = new OneMoreNumber(5, four);
listOfNumbers* six = new OneMoreNumber(6, five);
listOfNumbers* seven = new OneMoreNumber(7, six);


listOfNumbers*append1_1 = new OneMoreNumber(1, none);
listOfNumbers* append1_2 = new OneMoreNumber(2, append1_1);
listOfNumbers* append1_3 = new OneMoreNumber(3, append1_2);
listOfNumbers* append1_4 = new OneMoreNumber(4, append1_3);


listOfNumbers*append2_1 = new OneMoreNumber(1, none);
listOfNumbers* append2_2 = new OneMoreNumber(4, append2_1);
listOfNumbers* append2_3 = new OneMoreNumber(5, append2_2);
listOfNumbers* append2_4 = new OneMoreNumber(6, append2_3);

  /*printf("The answer is \n  ");
  (one->append(four))->show();
  printf("\n, but should be\n  ");
  none->show();
  printf("\n");
*/





}