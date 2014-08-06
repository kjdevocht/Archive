/* Kevin DeVocht Assignment 3 Exercise 3 Develop the program height, which computes the height that a rocket reaches in a given amount of time. If the rocket accelerates at a constant rate g (= 2m/s^2), it reaches a speed of g * t in t time units and a height of 1/2 * v * t where v is the speed at t. You should write an auxiliary function.
*/


#include <stdio.h>
#include <math.h>


//Contract: velocity: decimal number decimal number -> decimal number
//Purpose: To find the velocity at a given moment in time

double velocity (double time) {

//Template time

/*Test 1
	velocity(2.0)
	time = 2.0
	ans = 2m/s^2 *2.0
return 4.0;
*/

/*Test 2
	velocity(6.0)
	time = 6.0
	ans =2m/s^2*6.0
return 12.0;
*/

/*Generalize Tests 1 and 2
	The return must include the value "time" in order to solve for all values
*/

return 2*time;
}



//Contract: height: decimal number decimal number decimal number -> decimal number
//Purpose: To find the height of a rocket after a given amount of time

double height (double time) {

//Template time

/* Test 1
	height(2.0)
	time = 2.0
	ans =1.0/2.0 * velocity(2.0) * 2.0
return 4.0;
*/

/*Test 2
	height(6.0)
	time = 6.0
	ans = 1.0/2.0 * velocity(6.0) * 6.0
return 36.0;
*/

/*Generalize Tests 1 and 2
	Use the value "time" that height consumes to find the value for any number
*/

return 1.0/2.0 * velocity(time) * time;

}

int main () {

	printf ("this is the answer %f but it should be %f\n", velocity(2.0),4.0);
	printf ("this is the answer %f but it should be %f\n", velocity(6.0),12.0);
	printf ("this is the answer %f but it should be %f\n", velocity(4.0),8.0);
	printf ("this is the answer %f but it should be %f\n", height(2.0),4.0);
	printf ("this is the answer %f but it should be %f\n", height(6.0),36.0);
	printf ("this is the answer %f but it should be %f\n", height(4.0),16.0);

/*Substitution for velocity
	velocity(4.0)
	= velocity(time)
	= velocity(4.0)
	= 2.0*4.0
	= 8.0
*/


/*Substitution for height
	height(4.0)
	=height(time)
	=height(4.0)
	=1.0/2.0 * velocity(time) * time
	=1.0/2.0 * velocity(4.0) * 4.0
	=1.0/2.0 * (2.0*4.0) * 4.0
	=1.0/2.0 * 8.0 * 4.0
	=1.0/2.0 * 32.0
	=16.0
*/
}