/*Kevin DeVocht Assignment 5 Exercise 2. Redo day 5, exercise 3 (about jet fighters) with methods
*/
#include <stdio.h>


/* A fighter_jet is a
	new fighter_jet(designation, acceleration, top_speed, range
	designation is a string
	acceleration is a decimal
	top_speed is a decimal
	range is a deciaml
*/

class fighter_jet {

public:
	const char* designation;
	double acceleration;
	double top_speed;
	double range;


//Contract: new fighter_jet: string decimal decimal decimal ->fighter_jet*
fighter_jet (const char* designation0, double acceleration0, double top_speed0, double range0) {
	this->designation = designation0;
	this->acceleration = acceleration0;
	this->top_speed = top_speed0;
	this->range = range0;
}

//Contract: reduceRange: fighter_jet -> fighter_jet
//Purpose: To reduce the range of a fighter jet to 80%
fighter_jet* reduceRange (){
//Template this this->designation this->accelration this->top_speed this->range

/*Test 1
	reduceRange(MIG22)
		this->range = 1500.0
		ans = 1500.0 *0.8
	return 1200.0
*/

/*Test 2
	reduceRange(F22)
		this->range = 3000.0
		ans = 3000.0 *0.8
	return 2400.0
*/

return new fighter_jet(this->designation, this->acceleration, this->top_speed, this->range * 0.8);  //Math so easy a caveman could do it.  No need to generalize
}
};
int main (){

fighter_jet* MIG22 = (new fighter_jet("MIG22", 5000.0, 800.0, 1500.0));
fighter_jet* F22 = (new fighter_jet("F22", 10000.0, 1600.0, 3000.0));
fighter_jet* Tornado = (new fighter_jet("Tornado", 2000.0, 3200.0, 6000.0));

/*
printf("The answer is %s, %f, %f,%f\nbut should be %s, %f, %f,%f\n", MIG22->designation, MIG22->acceleration, MIG22->top_speed, MIG22->range, "MIG22", 5000.0, 800.0, 1500.0);
printf("The answer is %s, %f, %f,%f\nbut should be %s, %f, %f,%f\n", F22->designation, F22->acceleration, F22->top_speed, F22->range, "F22", 10000.0, 1600.0, 3000.0);
*/

printf("The answer is %f, but it should be %f\n", MIG22->reduceRange()->range , 1200.0);
printf("The answer is %f, but it should be %f\n", F22->reduceRange()->range , 2400.0);
printf("The answer is %f, but it should be %f\n", Tornado->reduceRange()->range ,4800.0 );

/*Substitution
	Tornado->reduceRange()
	new fighter_jet->("Tornado", 2000.0, 3200.0, 6000.0)->reduceRange()
	new fighter_jet(this->designation, this->acceleration, this->top_speed, this->range * 0.8);
	new fighter_jet(this->"Tornado, this->2000.0, this->3200.0, this->6000.0 * 0.8);
	new fighter_jet(this->"Tornado, this->2000.0, this->3200.0, this->4800.0);
	new fighter_jet(this->"Tornado, this->2000.0, this->3200.0, this->4800.0);
	
*/

}
