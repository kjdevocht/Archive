/* Kevin DeVocht Assignment 2: Exercise 2 Define the function fahrenheitToCelsius, which consumes a Fahrenheit temperature and produces the Celsius equivalent. Use Google or Wikipedia to find the conversion formula.
*/



#include <stdio.h>



// Contract: fahrenheitToCelsius: decimal number -> decimal number

// Purpose: Convert a Fahrenheit temperature into a Celsius temperature


//C = (F - 32) * 5/9



double fahrenheitToCelsius (double fahrenheit) {

//Template: fahrenheit


/* Test 1
	printf("The answer is %f but it should be %f\n", fahrenheitToCelsius(Test1), 0);
	fahrenheit = Test1=32
	ans=(32.0-32.0) * (5.0/9.0)
	return (32.0-32.0) * (5.0/9.0) 
*/




/* Test 2
	printf("The answer is %f but it should be %f\n", fahrenheitToCelsius(Test2), 57.23);
	fahrenheit = Test2 = 135
	ans = (32.0-32.0) * (5.0/9.0)



/* Generalize Test 1 and Test 2
	To generalize Tests 1 and 2 you must use "fahrenheit" in place of F in the formula C = (F-	32) * (5/9)
*/

return (fahrenheit-32.0) * (5.0/9.0);


}

int main () {

double Test1 = 32.0;
double Test2 = 135.0;
double Test3 = 65.0;

	printf("The answer is %f but it should be %f\n", fahrenheitToCelsius(Test1), 0.0);
	printf("The answer is %f but it should be %f\n", fahrenheitToCelsius(Test2), 57.23);
	printf("The answer is %f but it should be %f\n", fahrenheitToCelsius(Test3), 18.34);
	printf ( "Substitution Tests\n" );

/*	fahrenheitToCelsius(Test3)
		= fahrenheitToCelsius(65.0)
		= (65.0-32.0) * (5.0/9.0)
		= 33.0 * 5.0/9.0
		= 18.34
*/
	printf("The answer is %f but it should be %f\n", fahrenheitToCelsius(65.0), 18.34);
	printf("The answer is %f but it should be %f\n", (65.0-32.0) * (5.0/9.0), 18.34);
}
