/*Kevin DeVocht Assignment 10 Exercise 3. Develop the function delta, 
which consumes two price lists, that is, lists of numbers. 
The first represents the inventory at the beginning of a time period, 
the second one the inventory at the end. The function outputs the difference in value. 
If the value of the inventory has increased, the result is positive; if the value has decreased, it is negative.
*/

//time:2.5 hours because I was trying to make it harder than it needed to be

#include <stdio.h>
#include <math.h>
#include <string.h>

//A listOfNumbers is either
// a number
//or, a endOfList

class listOfNumbers {
public:
	//Contract: sum:vlistOfNumbers->int
	//Purpose: To find the total value of a listOfNumbers
	virtual int sum () = 0;
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

//Contract: sum: number->int
//Purpose: To find the total value of a number
int sum (){
//Template: this, this->num, this->restOfList, this->restOfList->sum()
/*Test 1
	doll1->sum()
		this->num = 4
		this->restOfList = 6,5,3
		ans = 4+6+5+3
	return 18
*/

/*Test 2
	 boot2->sum()
		endOfList->num = 12
		endOfList->restOfList = 2,1
		ans = 12+2+1
	return 14

*/
/*Generalize Tests 1 and 2
	just add each number in the list to the rest of the list by just recalling the sum function on the rest of the list
*/
return this->num + this->restOfList->sum();
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

//Contract: sum: ->int
//Purpose: To find the total value of an empty list
int sum () {
//Template: this
/*Test 1
	last->sum()
		this = !
		ans = 0
	return 0
*/
return 0;

}
};

//Contract: delta: listOfNumbers listOfNumbers->int
//Purpose: To find the difference between the sum of two lists
int delta (listOfNumbers* one, listOfNumbers* two) {
//Template: one, one->sum(), two, two->sum
/*Test 1
	doll1->delta(doll2)
		one = 4,6,5,3
		two = 7,12,2,1
		ans = (4+6+5+3)-(7+12+2+1)
	return 4
		
*/

/*Test 2
	boot1->delta(boot2)
		one = 6,5,3
		two = 12,2,1
		ans = (6+5+3)-(12+2+1)
	return 4
		
*/
/*Generalize Tests 1 and 2
	Each list must call the sum function and then just subtract two from one to get the difference
*/
return two->sum() - one->sum();
}


int main () {

listOfNumbers* last = new endOfList ();

listOfNumbers* pickle1 = new number(3, last);

listOfNumbers* baby_wipes1 = new number (5, pickle1);

listOfNumbers* boot1= new number(6, baby_wipes1);

listOfNumbers* doll1 = new number(4, boot1);


listOfNumbers* pickle2 = new number(1, last);

listOfNumbers* baby_wipes2 = new number (2, pickle2);

listOfNumbers* boot2= new number(12, baby_wipes2);

listOfNumbers* doll2 = new number(7, boot2);




printf("The answer is %d but it should be %d\n", delta(doll1, doll2), 4);
printf("The answer is %d but it should be %d\n", delta(boot1, boot2), 1);
printf("The answer is %d but it should be %d\n", delta(baby_wipes1, baby_wipes2), -5);
printf("The answer is %d but it should be %d\n", delta(last, last), 0);


/*Substitution
	1. delta(boot1, boot2)
	
	2. delta(new number(6, baby_wipes1), new number(12, baby_wipes2))
	
	3. return two->sum() - one->sum();
	
	4. return new number(12, baby_wipes2)->sum() -new number(6, baby_wipes1)->sum();
	
	5. return this->num + this->restOfList->sum() -this->num + this->restOfList->sum();
	
	6. return 12 + baby_wipes2->sum() -6 + baby_wipes2->sum();
	
	7. return 12 + new number (2, pickle2)->sum() -6 +new number (5, pickle1)->sum();
	
	8. return 12 + this->num + this->restOfList->sum() -6 + this->num + this->restOfList->sum();
	
	9. return 12 + 2 + pickle2->sum() -6 + 5 + pickle1->sum();
	
	10. return 12 + 2 + new number(1, last)->sum() -6 + 5 + new number(3, last)->sum();
	
	11. return 12 + 2 + this->num + this->restOfList->sum() -6 + 5 + this->num + this->restOfList->sum();
	
	12. return 12 + 2 + 1 + last->sum() -6 + 5 + 3 + last->sum();
	
	13. return 12 + 2 + 1 + new endOfList ()->sum() -6 + 5 + 3 + new endOfList ()->sum();
	
	14. return 12 + 2 + 1 +0 -6 + 5 + 3 + 0;
	
	15. return 15 -14;
	
	16. return 1;
*/
}