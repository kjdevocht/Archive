/* Kevin DeVocht Assignment 2: Exercise 5 The local supermarket needs a program that can compute the value of a bag of coins. Define the function sum_coins. It consumes four numbers: the number of pennies, nickels, dimes, and quarters in the bag; it produces the amount of money in the bag.
*/




#include <stdio.h>



// Contract: sum_coins: whole number whole number whole number whole number -> decimal number
// Purpose: To find out the monetary value of a bag of coins



double sum_coins (int penny, int nickel, int dime, int quarter) {

//Template: penny nickel dime quater

/* Test 1
	printf("the answer is $%f but is should be $%f\n", sum_coins(100,20,10,4), 4.00)
	penny = 100
	nickel = 20
	dime = 10
	quater = 4
	ans = (100*.01)+(20*.05)+(10*.1)+(4*.25)=4.00
	return 4.00;
*/


/* Test 2
	printf("the answer is $%f but is should be $%f\n", sum_coins(50,10,5,2), 2.00);
	penny = 50
	nickel = 10
	dime = 5
	quater = 2
	ans = (50*0.*0.01)+(10*0.05)+(5*0.1)+(2*0.25)=2.00
	return 2.00
*/



/* Generalize Tests 1 and 2
	In order to generalize tests 1 and two you must multiply "penny" by 0.01, "nickel" by 0.05, 	"dime" by 0.1, and "quarter" by 0.25 and then 	add all of the resulting products togeter
*/


return (penny*0.01)+(nickel*0.05)+(dime*0.1)+(quarter*0.25);


}

int main () {



	printf("the answer is $%f but it should be $%f\n", sum_coins(100,20,10,4), 4.00);
	printf("the answer is $%f but it should be $%f\n", sum_coins(50,10,5,2), 2.00);
	printf("the answer is $%f but it should be $%f\n", sum_coins(1,1,1,1), 0.41);
	printf ( "Substitution Tests\n" );
/*	sum_coins(1,1,1,1)
		= (penny*0.01)+(nickel*0.05)+(dime*0.1)+(quarter*0.25)
		= (1*0.01)+(1*0.05)+(1*0.1)+(1*0.25)
		= (0.01)+(0.05)+(0.1)+(0.25)
		= 0.41 
*/
	printf("the answer is $%f but it should be $%f\n",(1*0.01)+(1*0.05)+(1*0.1)+(1*0.25), 0.41);
	printf("the answer is $%f but it should be $%f\n",(0.01)+(0.05)+(0.1)+(0.25), 0.41);
	
}
