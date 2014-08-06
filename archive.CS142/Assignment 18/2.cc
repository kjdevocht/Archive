/*Kevin DeVocht assignment 18 exercise 2. Write isPrime, 
from last exercise, with a while loop.
*/



#include <stdio.h>
#include <math.h>
#include <string.h>

// booleanToString : boolean -> string
// Purpose: convert a boolean into a string for printing
const char* booleanToString ( bool it ) {
  if ( it ) { return "true"; } else { return "false"; }
}


//isPrime: int->bool
//Purpose to see if a given number is prime or not
bool isPrime(int theNum)
//Template: theNum, theRest
{
	int theRest = theNum-1;
	/*Test 1
		isPrime(2)
			theNum = 2
			theRest = 1
			ans = true
		return true;
	*/
	/*Test 2
		isPrime(4)
			theNum = 4
			theRest = 3,2,1
			ans = false
		return false;
	*/
	/*Distinguish Tests 1 and 2
		well we know that we need to use a while loop
		and just like last time if it makes it to one
		then it is prime other wise check to see if
		there is a remainder and decrement theRest if there is
		not
	*/
	while(! (theRest == 1))
	{
		if (theNum % theRest == 0)

		{
			return false;
		}
		else
		{
			theRest = theRest-1;
		}
	}
	return true;
}


int main ()
{
printf("The answer is %s but should be %s\n", booleanToString(isPrime(11)), "true");

printf("The answer is %s but should be %s\n", booleanToString(isPrime(12)), "false");

printf("The answer is %s but should be %s\n", booleanToString(isPrime(997)), "true");

printf("The answer is %s but should be %s\n", booleanToString(isPrime(2)), "true");

printf("The answer is %s but should be %s\n", booleanToString(isPrime(4)), "false");
 
 
//Substitution: Ran out of Time!

}