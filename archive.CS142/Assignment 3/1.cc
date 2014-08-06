/* Kevin DeVocht Assignment 3 Exercise 1 Write a function called sign that consumes a number, and returns one of the words positive, negative, or zero depending on the sign of the number. For example, sign(-2) would return negative.
*/

#include <stdio.h>

//Contract: sign: number-> word
//Purpose: To find out if a number is positive or negative

const char* sign (int number) {

//Template: number


if (number <  0) {


	/* Test 1
		sign(-3)
		number = -3
		ans = -3 is < 0
		return "negative"
	*/


	/* Test 2
		sign(-7)
		number = -7
		ans = -7 is < 0
		return "negative"
	*/



	/* Generalize Tests 1 and 2
		In order to generalize all tests, you must use a conditional statement to find if 		the number is less then 0 or not
	*/
	

return "negative";
} 

else {

	/*Test 3
		sign(0)
		number = 0
		ans = 0 is not < 0
	return "positve"
	*/

	/*Test 4
		sign(4)
		number = 4
		ans = 4 is not < 0
	return "positve"
	*/

	/*Generalize Tests 3 and 4
		If the number is not less then 0 it must be positve so return "positive"
	*/


return "positive";

} // Closes the else statement


} //Closes function "sign"


int main () {


	printf("The answer is %s but should be %s\n", sign(-3), "negative");
	printf("The answer is %s but should be %s\n", sign(-7), "negative");
	printf("The answer is %s but should be %s\n", sign(0), "positive");
	printf("The answer is %s but should be %s\n", sign(4), "positive");

/* Substitution for function sign
	sign(-3)
		= if ( number < 0) {
			return "negative";
			}
		 else {
			return "positive";
			}
		 [Where number = 0]
		= if ( 0 < 0) {
			return "negative";
			}
		 else {
			return "positive";
			}
		= if (true) {
			return "negative";
			}
		= return "negative";
*/
			
		
}
