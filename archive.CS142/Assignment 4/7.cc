/*Kevin DeVocht Assignment 4: Exercise 7. Develop the function interest. Like interestRate, it consumes a deposit amount. 
Instead of the rate, it produces the actual amount of interest that the money earns in a year. The bank pays a 
flat 6% for deposits of up to $1,600, a flat 7.5% per year for deposits of up to $9,000, and a flat 8% for deposits of more than $9,000.*/

#include <stdio.h>


//Contract: interestRate: decimal number -> decimal number
//Purpose:  Find at what interest rate a given sum of money will earn during a year

double interestRate (double yoMoney) {

//Template: yoMoney

if (yoMoney <= 1600) {
	/*Test1
		interestRate(1500)
			yoMoney =1500
			ans = 1500 <= 1600
		return 0.06
	*/
return 0.06;
	}
if (yoMoney <=9000) {
	/*Test2
		interstRate(3000)
			yoMoney = 3000
			ans = 3000 <=9000
		return 0.075
	*/
return 0.075;
	}
else {
	/*Test3
		interestRate(9001)
			yoMoney = 9001
			ans = 9001 >= 1600 and >= 9000
		return 0.08;
	*/
	
	/*Generalize All Tests
		It is clear that a multiple if statment will be need to cover all the possible outputs
	*/
return 0.08;
	}

}




//Contract: interest: decimal number -> decimal number
//Purpose:  Find the actual amount of interest that a given amount of money earns in a year

double interest ( double yoMoney) {

//Template: yoMoney
/*Test 1
	interest(1500)
		yoMoney = 1500
		ans = 1500 * 0.65
	return 90
*/

/*Test2
	interest(30000
		yoMoney = 3000
		ans = 3000 * 0.075
	return 210
*/

/*Generalize Tests 1 snd 2
	To generalize the two test you must call funciton interestRate and then times yoMoney by the interest rate that interestRate returns
*/

return yoMoney * interestRate(yoMoney);

}

int main () {

	printf("The answer is %f but it should be %f\n", interestRate(1500), 0.06);
	printf("The answer is %f but it should be %f\n", interestRate(3000), 0.075);
	printf("The answer is %f but it should be %f\n", interestRate(9001), 0.08);
	
	
	printf("The answer is %f but it should be %f\n", interest(1500), 90.0);
	printf("The answer is %f but it should be %f\n", interest(3000), 210.0);
	printf("The answer is %f but it should be %f\n", interest(9001), 720.08);
	
	/*Substitution
		interest(9001)
		yoMoney * interestRate(yoMoney))
		9001 * interestRate(9001))
		9001 * if (yoMoney <= 1600) {
			return 0.06;
			}
			if (yoMoney <=9000) {
			return 0.075;
			}
			else {
			return 0.08))
		9001 * if (9001 <= 1600) {
			return 0.06;
			}
			if (9001 <=9000) {
			return 0.075;
			}
			else {
			return 0.08
		9001 * else { return 0.08}
		9001 * 0.08
		720.08
	*/
}