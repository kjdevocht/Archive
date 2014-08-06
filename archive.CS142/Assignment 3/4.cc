/* Kevin DeVocht Assignment 3: Exercise 4 Recall fahrenheitToCelsius, write celsiusToFahrenheit.

Now consider the function:

I : double -> double

to convert a Fahrenheit temperature to Celsius and back

double I ( double f ) {

       return celsiusToFahrenheit( fahrenheitToCelsius( f ) );

}

Evaluate I( 32.0 ) by hand. What does this suggest about the composition of the two functions?
*/




#include <stdio.h>




//Contract: celsiusToFahrenheit: decimal number -> decimal number
//Purpose: To covert a temprature in celsius to fahrenheit
//F = C * 9.0/5.0 +32

double celsiusToFahrenheit (double celsius) {

//Template celcius

/*Test 1
	celsiusToFahrenheit(0.0)
	celsius = 0.0
	ans = 0.0 * (9.0/5.0) + 32.0
return 32.0;
*/


/*Test 2
	celsiusToFahrenheit(45.0)
	celsius = 45.0
	ans = 45.0 * (9.0/5.0) + 32.0
return 113.0;
*/

/*Generalize Tests 1 and 2
	celsiusToFahrenheit must use the value of celsius to solve for all values
*/

return celsius * (9.0/5.0) + 32.0;

}


int main () {
	printf("The answer is %f but it should be %f\n", celsiusToFahrenheit(0.0), 32.0);
	printf("The answer is %f but it should be %f\n", celsiusToFahrenheit(45.0), 113.0);
	printf("The answer is %f but it should be %f\n", celsiusToFahrenheit(65.0), 149.0);

/*Substitution for celsiusToFahrenheit	
	celsiusToFahrenheit(65.0)
		= celsiusToFahrenheit(65.0)
		= 65.0 * (9.0/5.0) + 32
		= 65.0 * 1.8 + 32
		= 117 + 32
		=149
*/


/*Solve for I( 32.0 ) by hand
 	=celsiusToFahrenheit( fahrenheitToCelsius( f ))
	=celsiusToFahrenheit( fahrenheitToCelsius( 32.0 ))
	=celsiusToFahrenheit((f-32.0) * (5.0/9.0))
	=celsiusToFahrenheit((32.0-32.0) * (5.0/9.0))
	=celsiusToFahrenheit(0 * (5.0/9.0))
	=celsiusToFahrenheit(0.0)
	=celsius * (9.0/5.0) + 32.0
	=0.0 * (9.0/5.0) + 32.0
	=0.0 + 32.0
	=32.0

fahrenheitToCelsius and celsiusToFahrenheit are inverse functions.  You can get the original number back by inputting the out come of the first function into the other function
*/

}