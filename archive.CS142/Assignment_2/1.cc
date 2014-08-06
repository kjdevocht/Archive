/* Kevin DeVocht Assignment 2: Exercise 1 Define a function called tip that will add a 15% tip to a given number. Define a function called shareWithTip that takes a pizza price and the number of slices consumed computes the share of the cost of a pizza, assuming it has 8 slices, including the cost of the tip (refer to your answer to yesterday.s sample exam question), using tip.
*/





#include <stdio.h>






// Contract: tip: decimal number decimal number -> decimal number

// Purpose: To add 15% to given number

double tip (double cost) {

// Template: cost


/* Test 1
	printf("The total cost plus tip is $%f but it should be $%f\n", tip(Test1), 13.8);
	cost=Test1=12.0
	ans=12.0*1.15
	return 12.0*1.15;
*/


/*Test 2
	printf("The total cost plus tip is $%f but it should be $%f\n", tip(Test2), 11.5);
	cost=Test2=10.0
	ans=10.0*1.15
	return 10.0*1.15; 
*/




/* Generalize Tests 1 and 2
	In order to gerneralize all  tests "cost" must be incorperated into function tip's return
 */


return 1.15 * cost;

}




// Contract: shareWithTip: decimal number decimal number -> decimal number

// Purpose:  Find the share of the cost of a pizza plus tip




double shareWithTip (double sliceseaten, double cost) {

//Template: sliceseaten cost

/* Test 1
	printf("The answer is %f but it should be %f\n", shareWithTip(Test1, Slice1), 5.175);printf("Your share with tip is $%f but it should be $%f\n", 		shareWithTip(Slice1, tip(Test1)), 5.18);
		cost=Test1=12.0
		sliceseaten=3
		ans=(3/8) * tip(12)
		return (3/8) * tip(12)
*/


/* Test 2
	printf("Your share with tip is $%f but it should be $%f\n", shareWithTip(Slice2, tip(Test2)), 11.5);
		cost=Test2=10.0
		sliceseaten=8.0
		ans=(8/8) * tip(10.0)
		return (8/8) * tip(10.0)
*/



/*Generalize Tests 1 and 2
	In order to gerneralize all tests "sliceseaten" must be divided by 8 and then mulytiplyed by "tipcost" to find out what your share of the 			pizza and the tip is
*/

return (sliceseaten/8.0) * tip(cost);

}




int main () {
double Test1 = 12.0;
double Test2 = 10.0;
double Test3 = 15.0;
double Slice1=3.0;
double Slice2=8.0;
double Slice3=1.0;

	printf("The total cost plus tip is $%f but it should be $%f\n", tip(Test1), 13.8);
	printf("The total cost plus tip is $%f but it should be $%f\n", tip(Test2), 11.5);
	printf("The total cost plus tip is $%f but it should be $%f\n", tip(Test3), 17.25);
 	printf("Your share with tip is $%f but it should be $%f\n", shareWithTip(Slice1, Test1), 5.18);
	printf("Your share with tip is $%f but it should be $%f\n", shareWithTip(Slice2, Test2), 11.5);
	printf("Your share with tip is $%f but it should be $%f\n", shareWithTip(Slice3, Test3), 2.16);
	printf ( "Substitution Tests\n" );
/*	
	tip(Test3)
		= tip(15)
		= 1.15*15
		= 17.25
	shareWithTip(Slice3, Test3)
		= sharewithTip(1.0, 15.0)
		= (1.0/8.0)*tip(15.0)
		= (1.0/8/0)*(1.15*15.0)
		= 17.25
*/
	printf("The total cost plus tip is $%f but it should be $%f\n", tip(15.0), 17.25);
	printf("The total cost plus tip is $%f but it should be $%f\n", 1.15* 15.0, 17.25);
	printf("Your share with tip is $%f but it should be $%f\n", shareWithTip(1.0,Test3), 2.16);
	printf("Your share with tip is $%f but it should be $%f\n", shareWithTip(1.0, 15.0), 2.16);
	printf("Your share with tip is $%f but it should be $%f\n", (1.0/8.0) * tip(15.0), 2.16);
}