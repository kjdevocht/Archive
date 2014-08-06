/* Kevin DeVocht Assingment 20 exercise 8. Write anyTrue using while or for*/

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>

// booleanToString : boolean -> string
// Purpose: convert a boolean into a string for printing
const char* booleanToString ( bool it ) {
  if ( it ) { return "true"; } else { return "false"; }
}


//anyTrue: array, int->bool
//Purpose: To act as a wrapper function and return the results of anyTrueAccum
bool anyTrue(bool myArray[], int arrayLength)
//Template: myArray, arrayLength, whereAmI
{
	int whereAmI = 0;
	/*Test 1
	anyTrue(one, arrayOne)
		myArray = {false,false,true,false}
		arrayLength = 4
		whereAmI = 0
		ans = true
	return true
	*/
	
	/*Test 2
	anyTrue(five, arrayFive)
		myArray = {false, false, false}
		arrayLength = 3
		whereAmI = 0
		ans = false
	return false
	*/
	/*Distinguish Tests 1 and 2
		We know we will be using a while loop so if at any point in the while
		loop the value at whereAmI is true we can just return true.  Otherwise
		keep looking.  If you reach the end of the array then you know the answer
		is false so outside of the while loop return false.
	*/
	while(arrayLength != whereAmI)
	{
		if (myArray[whereAmI] == true)
		{
			return true;
		}
		else
		{
			whereAmI++;
		}
	}
	return false;
}


int main ()
{
bool one[] = {false,false,true,false};
int arrayOne = 4;

bool two[] = {false, false,true};
int arrayTwo = 3;

bool three[] = {true, false, false};
int arrayThree = 3;

bool four[] = {};
int arrayFour = 0;

bool five[] = {false, false, false};
int arrayFive = 3;


printf("the answer is %s, but should be %s\n", booleanToString(anyTrue(one, arrayOne)), "true");
printf("the answer is %s, but should be %s\n", booleanToString(anyTrue(two, arrayTwo)), "true");
printf("the answer is %s, but should be %s\n", booleanToString(anyTrue(three, arrayThree)), "true");
printf("the answer is %s, but should be %s\n", booleanToString(anyTrue(four, arrayFour)), "false");
printf("the answer is %s, but should be %s\n", booleanToString(anyTrue(five, arrayFive)), "false");
}