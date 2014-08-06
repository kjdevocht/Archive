/* Kevin DeVocht Assingment 20 exercise 7. Write a function called anyTrue 
that takes an array of booleans and returns true if any is true. You must
 only use function calls: no while or for.*/

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>

// booleanToString : boolean -> string
// Purpose: convert a boolean into a string for printing
const char* booleanToString ( bool it ) {
  if ( it ) { return "true"; } else { return "false"; }
}

//anyTrueAccum: array, int, int->bool
//Purpose: to see if any of the values in the array are true
bool anyTrueAccum( bool myArray[], int arrayLength, int whereAmI)
//Template: myArray, arrayLength, whereAmI
{
	if (whereAmI == arrayLength)
	/*Test 1
	anyTrueAccum(five, arrayFive)
		myArray = {false, false, false}
		arrayLength = 3
		whereAmI = 0
		ans = false
	return false
	*/
	{
		return false;
	}
	else
	/*Test 2
	anyTrueAccum(one, arrayOne)
		myArray = {false,false,true,false}
		arrayLength = 4
		whereAmI = 0
		ans = true
	return true
	*/
	/*
	Distinguish Tests 1 and 2
		If we reach the end of the array we know the answer is false
		otherwise if at any point the value of whereAmI is true, we know the answer
		is true.  Keep looking until you find a true or the end of the array.
	*/
	{
		if (myArray[whereAmI] == true)
		{
			return true;
		}

		else
		{
			return anyTrueAccum(myArray, arrayLength, whereAmI+1);
		}
	}
}
//anyTrue: array int int->bool
//Purpose: To act as a wrapper function and return the results of anyTrueAccum
bool anyTrue(bool myArray[], int arrayLength)
//Template: myArray, arrayLength
{
	/*Test 1
	anyTrue(five, arrayFive)
		myArray = {false, false, false}
		arrayLength = 3
		whereAmI = 0
		ans = false
	return anyTrueAccum(myArray, arrayLength, 0);
	*/
	/*Test 2
	anyTrue(one, arrayOne)
		myArray = {false,false,true,false}
		arrayLength = 4
		whereAmI = 0
		ans = true
	return anyTrueAccum(myArray, arrayLength, 0);
	*/
	/*Generalize Tests 1 and 2
		We know this is a wrapper function so it will just pass
		the correct values and return the Accum function.  We want to 
		start at the begining of the array so the last value (whereAmI)
		will always start as 0.	
	*/
	return anyTrueAccum(myArray, arrayLength, 0);
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