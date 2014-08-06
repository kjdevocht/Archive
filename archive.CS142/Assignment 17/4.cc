/*Kevin DeVocht assignment 17 exercise 4. Write the function isPrime, 
which takes an integer and returns true if the number is prime. 
Recall that a number is prime if it is not divisible by any number 
except itself and 1. Recall that a % b is the remainder after dividing a by b. 
(Hint: This uses the natural number data definition.)
*/



#include <stdio.h>
#include <math.h>
#include <string.h>

// booleanToString : boolean -> string
// Purpose: convert a boolean into a string for printing
const char* booleanToString ( bool it ) {
  if ( it ) { return "true"; } else { return "false"; }
}


//isPrimeAccum: int, int->bool
//Purpose to see if a given number is prime or not
bool isPrimeAccum(int theNum, int theRest)
//Template: theNum, theRest
{	if (theRest == 1)
	/*Test 1
		isPrimeAccum(11, 1)
			theNum = 11
			theRest = 1
			ans = true
		return true
	*/
	{
	return true;
	}
	else
	{
		if (theNum % theRest == 0)
		/*Test 2
			isPrimeAccum(12, 2)
			theNum = 12
			theRest = 2
			ans = true
		return false (because if it is 0 then it can't be prime)
		*/
		{
		return false;
		}
		else
		{
		/*Test 3
			isPrimeAccum(11, 10)
			theNum = 11
			theRest = 10
			ans = false
		return isPrimeAccum(theNum, theRest-1)
		*/
		
		/*Distinguish Tests 1, 2 and 3
			we see from test one that if we have made it to testing on a one then the number is prime
			from test 2 we see that if the remander is 0 then the number is not prime.  From test 3
			we see that if it made it this far then it is none of the above and we need to decrament theRest
			and test again
		*/
		return isPrimeAccum(theNum, theRest-1);
		}
	}
}
//isPrime: int->bool
//Purpose to see if a given number is prime or not
bool isPrime(int theNum)
//Template: theNum
{
/*Test 1
	isPrime(11)
		theNum = 11
		ans = true
	return SAF(11)
*/

/*Test 2
	isPrime(12)
		theNum = 12
		ans = false
	return isPrimeAccum(12, 11)
*/

/*Generalize Tests 1 and 2
	We can see that this is wrapper function.  We also know that we need to test
	all numbers between 1 and theNum to see if theNum is divisible by any of them
	so we need to pass theNum to the Accum function plus the first number to test
	so just pass it theNum-1
*/
return isPrimeAccum(theNum, theNum-1);
}


int main ()
{
printf("The answer is %s but should be %s\n", booleanToString(isPrime(11)), "true");

printf("The answer is %s but should be %s\n", booleanToString(isPrime(12)), "false");

printf("The answer is %s but should be %s\n", booleanToString(isPrime(997)), "true");

printf("The answer is %s but should be %s\n", booleanToString(isPrime(2)), "true");

printf("The answer is %s but should be %s\n", booleanToString(isPrime(4)), "false");
 
 
 /* Substitution
	1. isPrime(3)
	
	2. return isPrimeAccum(theNum, theNum-1);
	
	3. return isPrimeAccum(3, 3-1);
	
	4. if (theRest == 1){return true;}else{if (theNum % theRest == 0){return false;}else{return isPrimeAccum(theNum, theRest-1);}}
	
	5. if (2 == 1){return true;}else{if (3 % 2 == 0){return false;}else{return isPrimeAccum(3, 2-1);}}
	
	6. if (false){return true;}else{if (3 % 2 == 0){return false;}else{return isPrimeAccum(3, 2-1);}}
	
	7. if (3 % 2 == 0){return false;}else{return isPrimeAccum(3, 2-1);}
	
	8. if (false){return false;}else{return isPrimeAccum(3, 2-1);}
	
	9. return isPrimeAccum(3, 2-1);
	
	10. return isPrimeAccum(3, 1);
	
	11. if (theRest == 1){return true;}else{if (theNum % theRest == 0){return false;}else{return isPrimeAccum(theNum, theRest-1);}}
	
	12. if (1 == 1){return true;}else{if (3 % 1 == 0){return false;}else{return isPrimeAccum(3, 1-1);}}
	
	13. if (true){return true;}else{if (3 % 1 == 0){return false;}else{return isPrimeAccum(3, 1-1);}}
	
	14. return true;
*/
}