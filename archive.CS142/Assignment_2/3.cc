/* Kevin DeVocht Assignment 2: Exercise 3 Define the function convert3, which consumes 3 digits, starting with the least significant (meaning right-most when written positionally) digit, and produces the corresponding number. So, convert3(1,2,3) should produces 321 and convert3(3,5,1) should produces 153.
*/



#include <stdio.h>



// Contract: convert3: int int int -> int

/*Purpose: To produce a 3 digit number that is is in reverse order from the way the numbers where entered*/


int convert3 (int num1, int num2, int num3) {

//Template: num1 num2 num3

/*Test 1
	printf("The answer is %f but it should be %f\n", convert3(1,2,3), 321);
	num1 = 1
	num2 = 2
	num3 = 3
	ans = (3*100) + (2*100) + 1
	return 321 
*/



/*Test 2
	printf("The answer is %f but it should be %f\n", convert3(6,4,1), 146);
	num1 = 6
	num2 = 4
	num3 = 1
	ans = (1*100) + (4*10) + 6
	return 146 
*/



/* Generalize Tests 1 and 2
	To generalize Tests 1 and 2 you must return num3 * 100 first then num2 * 10 followed by adding num1
*/

return (num3*100) + (num2*10)+ num1;


}




int main (){


	printf("The answer is %d but it should be %d\n", convert3(1,2,3), 321);
	printf("The answer is %d but it should be %d\n", convert3(6,4,1), 146);
	printf("The answer is %d but it should be %d\n", convert3(9,8,7), 789);
	printf ( "Substitution Test\n" );
/*	convert3(9,8,7)
	= (7*100)+(8*10)+9
	= 700+80+9
	= 789
*/
	printf("The answer is %d but it should be %d\n", 9 + (8 * 10) + (7 * 100), 789);
}
