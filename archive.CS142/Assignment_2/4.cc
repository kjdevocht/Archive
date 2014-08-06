/* Kevin DeVocht Assignment 2: Exercise 4 Formulate n^2 + 10 as a function. Ensure your function's correctness on 2 and 9
*/



#include <stdio.h>


// Contract: ForN: decimal number -> decimal number

// Purpose: To find n^2 + 10



double ForN ( double N){
//Template N


/* Test 1
	printf("the answer is %f but it should be %f\n", ForN(2), 14);
	N=2
	ans=2*2+10=14
	return 14 
*/




/* Test 2
	printf("the answer is %f but it should be %f\n", ForN(9), 91);
	N=9
	ans=9*9+10=91
	return 91 
*/




/* Generalize Tests 1 and 2
	To Generalize Tests 1 and 2 you must use "N" in place of the first two numbers in the 	equation.  
	example 
		return N*N+10
*/


return N*N+10.0;

}



int main () {
	printf("the answer is %f but it should be %f\n", ForN(2.0), 14.0);
	printf("the answer is %f but it should be %f\n", ForN(9.0), 91.0);
	printf("the answer is %f but it should be %f\n", ForN(10.0), 110.0);
	printf ( "Substitution Test\n" );
/*	ForN(10.0)
		=  N*N+10
		= 10*10+10
*/
	printf("the answer is %f but it should be %f\n", 10.0*10.0+10, 110.0);
	
}
