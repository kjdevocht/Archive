/* Kevin DeVocht Assignment 2: Exercise 6. An old-style movie theater has a simple profit function. Each customer pays $5 per ticket. Every performance costs the theater $20, plus $.50 per attendee. Develop the function total_profit. It consumes the number of attendees (of a show) and produces how much income the attendees produce.
*/



#include <stdio.h>



// Contract: total_profit: whole number -> decimal number

// Purpose: To find out how much profit the attendees produce for the movie theater



double total_profit (int attendees) {

// Template attendees

/* Test 1
	printf("the answer is %f but it should be %f\n", total_profit(0),-20.0);
		attendees = 0
		ans = (0*5.0)- (0*0.5)-20.0=20.0
		return -20.0
*/

	


/* Test 2
	printf("the answer is %f but it should be %f\n", total_profit(5),-2.50);
		attendees = 5
		ans (5*5.0)- (5*0.5)-20.0=2.50
		return 2.50
*/




/* Generalize Tests 1 and 2
	In order to generalize the two tests you must replace the indiviudal value of the number of attendess with "attendees"
*/

return (attendees*5) - (attendees * 0.5) - 20;



}


int main () {

	printf("the answer is %f but it should be %f\n", total_profit(0),-20.0);
	printf("the answer is %f but it should be %f\n", total_profit(5),2.50);
	printf("the answer is %f but it should be %f\n", total_profit(40),160.0);
	printf ( "Substitution Test\n" );

/*	total_profit(40)
		= (attendees*5) - (attendees * 0.5) - 20
		= (40*5) - (40 * 0.5) - 20
		= 200 - 20 -20
		= 160
*/
	printf("the answer is %f but it should be %f\n", (40*5) - (40 * 0.5) - 20,160.0);
	printf("the answer is %f but it should be %f\n", 200.0-20.0-20.0, 160.0);




}
