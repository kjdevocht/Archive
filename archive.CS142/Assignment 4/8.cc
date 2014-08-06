/*Kevin DeVocht Assignment 4: Exercise 8. Some credit card companies pay back a small portion of the charges a customer makes over a year. One company returns .25% for the first $500 of charges, .50% for the next $1000 
(that is, the portion between $500 and $1500), .75% for the next $1000 (that is, the portion between $1500 and $2500), and 1.0% for everything above $2500.

Thus, a customer who charges $400 a year receives $1.00, which is 0.25 · 1/100 · 400, and one who charges $1,400 a year receives $5.75, which is 1.25 = 0.25 · 1/100 · 500 for the first $500 and 0.50 · 1/100 · 900 = 4.50 
for the next $900.

Determine by hand the pay-backs for a customer who charged $2000 and one who charged $2600.

Define the function payback, which consumes a charge amount and computes the corresponding pay-back amount.
*/

/* if a customer charged $2000 dollars you would first see at which percentage rate they would be recieving paybacks
	2000 > 500
	2000 > 1500
	2000 < 2500
so we can see that this would fall into the 0.75% category so we will just
2000 * (0.75 1/100) = $15

next to see how much a customer who charger $2600 will get you follow the same process
	2600 > 500
	2600 > 1500
	2600 > 2500
this means that this custmer will be paid back at the 1% rate so to find out just
	2600 *(1 * 1/100) = $26
*/


#include <stdio.h>

//Contract: paybackRate: number -> number
//Purpose: To find at what percentage rate the account will be getting a payback

double paybackRate ( double amount) {
//Template amount

if (amount < 500.0) {
	/*Test1
		paybackRate(200.0)
			amount = 200.0
			answer = 200.0 < 500.0
		return 0.0025
	*/
return 0.0025;
}

if (amount < 1500.0) {
	/*Test2
		paybackRate(1499.0)
			amount = 1499.0
			ans = 1499.0 < 1500.0
		return 0.005
	*/
return 0.005;
}

if (amount < 2500.0) {
	/*Generalzie All Tests
		It is very clear that a multiple if statement is needed to account for all of the possible outcomes
	*/
return 0.0075;
}

else {
return 0.01;
}
}

//Contract: payback: mumber -> number
//Purpose: To find out how much will be paid in paybacks

double payback (double amount ) {
//Template: amount

/*Test1
	payback(200.0)
		amount = 200.0
		ans = 200.0.0 *  0.0025
	return 0.5
*/
/*Test2
		payback(1499.0)
			amount =1499.0
			ans = 1499.0 * 0.005
		return 7.5
*/

/*Generalize Tests 1 and 2
	You must use amount and call paybackRate to find answer based off of a given amount of money
*/

return amount * paybackRate(amount);
}


int main () {

	printf("The answer is %f but it should be %f\n", paybackRate(200.0), 0.0025);
	printf("The answer is %f but it should be %f\n", paybackRate(1499), 0.005);
	printf("The answer is %f but it should be %f\n", paybackRate(1800), 0.0075);
	printf("The answer is %f but it should be %f\n", paybackRate(3000), 0.01);
	
	printf("The answer is %f but it should be %f\n", payback(200.0), 0.5);
	printf("The answer is %f but it should be %f\n", payback(1499.0), 7.495);
	printf("The answer is %f but it should be %f\n", payback(1800.0), 13.5);
	printf("The answer is %f but it should be %f\n", payback(3000.0), 30.0);
	
	
	/*Substitution
		payback(3000.0)
		payback(amount)
		3000.0 * paybackRate(amount)
		3000.0 * paybackRate(3000.0)
		3000.0 * if (amount < 500.0) {
			return 0.0025;
			}
			if (amount < 1500.0) {
			return 0.005;
			}
			if (amount < 2500.0) {
			return 0.0075;
			}
			else {
			return 0.01;
			}
		3000.0 * if (3000.0 < 500.0) {
			return 0.0025;
			}
			if (3000.0 < 1500.0) {
			return 0.005;
			}
			if (3000.0 < 2500.0) {
			return 0.0075;
			}
			else {
			return 0.01;
			}
		3000.0 * 0.01
		return 30.0
	*/
		
		
		
}
