/* Kevin DeVocht Assingment 20 exercise 9. Write the function distances that 
takes an array of numbers and modifies the array such that each number is 
replaced with the difference between it and the previous number. For the 
first number, assume the previous number was 42.*/

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>

// showArray : Array(number) number -> nothing
// Purpose: to print the array
void showArray ( int someArray[], int length ) 
//Template: someArray, length, whereAmI
{
  int whereAmI = 0;

  while ( whereAmI < length ) {
    printf("%d,", someArray[whereAmI]);
    whereAmI = whereAmI + 1;
  }
  return ;
}

//distances: array, int->array
//Purpose: to modify a given area so that each number is the difference between it and the previous number
void distance(int myArray[], int length)
//Template: myArray, length, whereAmI, previousOne, previousTwo
{
	int whereAmI = 0;
	int previousOne = 42;
	int previousTwo = 0;

	/*Test 1
		distance(one, arrayOne)
			myArray = {41, 39, 36, 32}
			length = 4
			whereAmI = 0
			previousOne = 42
			previousTwo = 0
			ans = {1,2,3,4}
		return;
	*/
	/*Test 2
		distance(two, arrayTwo)
			myArray = {123, 234, 345}
			length = 3
			whereAmI = 0
			previousOne = 42
			previousTwo = 0
			ans = {-81,-111,-111
		return;
	*/
	/*Generalize Tests 1 and 2
		we know that this function returns nothing so it must use mutation.  
		We need to store the value of the current location and subtract it 
		from the value of the previous location.  Then we need to change the
		previous location and increment whereAmI
	*/
	while (whereAmI != length)
	{
		previousTwo = myArray[whereAmI];
		myArray[whereAmI] = previousOne - previousTwo;
		previousOne = previousTwo;
		whereAmI++;
	}
	showArray(myArray, length);
}

int main ()
{
int one[] = {41, 39, 36, 32};
int arrayOne = 4;

int two[] = {123, 234, 345};
int arrayTwo = 3;

int three[] = {};
int arrayThree = 0;

printf("The Answer is \n ");
distance(one, arrayOne);
printf("\nbut should be \n 1,2,3,4\n");

printf("The Answer is \n ");
distance(two, arrayTwo);
printf("\nbut should be \n -81,-111,-111\n");

printf("The Answer is \n ");
distance(three, arrayThree);
printf("\nbut should be \n");
}