/*Kevin DeVocht Assignment 5 Exercise 2. Write a data definition that represents an airforce’s jet fighters. 
Assume that a fighter has four essential properties: designation ("f22", "tornado", or "mig22"), acceleration, 
top speed, and range. Then develop the function withinRange. The function consumes a fighter and the distance 
of a target from the (fighter’s) base. It determines whether the fighter can reach the intended target.*/


#include <stdio.h>


// booleanToString : boolean -> string
const char* booleanToString ( bool it ) {
  // Template: it

  // Distinguishes test 1 from test 2
  if ( it ) {
    // Test 1
    // it = true
    return "Within Range";
  } else {
    // Test 2
    // it = false
    return "Out of Range";
  }
}


/* A fighter_jet is a
	new_fighter_jet(designation, acceleration, top_speed, range
	designation is a string
	acceleration is a decimal
	top_speed is a decimal
	range is a deciaml
*/

typedef struct fighter_jet;

typedef struct fighter_jet {
	const char* designation;
	double acceleration;
	double top_speed;
	double range;
};

//Contract: new_fighter_jet: string decimal decimal decimal ->fighter_jet*
//Purpose: To create a new fighter_jet

fighter_jet* new_fighter_jet (const char* designation0, double acceleration0, double top_speed0, double range0) {
//Template designation0 acceleration0 top_speed0 range0
fighter_jet* f = new fighter_jet ();
	f->designation = designation0;
	f->acceleration = acceleration0;
	f->top_speed = top_speed0;
	f->range = range0;
/*Example
	new_fighter_jet("Tornado", 2000.0, 3200.0, 6000.0)
		f->designation = "Tornado"
		f->acceleration = 2000.0
		f->top_speed = 3200.0
		f->range = 6000.0
		f=("Tornado", 2000.0, 3200.0, 6000.0)
*/
return f;
}


//Contract: withinRange: fighter_jet distance -> boolean
//Purpose: To see if given fighter can reach a given target or not

bool withinRange (fighter_jet* f, double distance) {
//Template f, f->range, distance

if (distance <= f->range) { //Super easy math formula here.  No need to generalize
/*Test 1
	withinRange(MIG22, 523)
		distance = 523
		f->range = 1500
		ans = true
*/

return true;
}
else {
/*Test2
	withinRange(F22, 3000.1)
		distance = 3000.1
		f->range = 3000.0
		ans = false
*/
return false;
}

}

int main () {

fighter_jet* MIG22 = new_fighter_jet("MIG22", 5000.0, 800.0, 1500.0);
fighter_jet* F22 = new_fighter_jet("F22", 10000.0, 1600.0, 3000.0);
fighter_jet* Tornado = new_fighter_jet("Tornado", 2000.0, 3200.0, 6000.0);

printf("The answer is %s, %f, %f,%f\nbut should be %s, %f, %f,%f\n", MIG22->designation, MIG22->acceleration, MIG22->top_speed, MIG22->range, "MIG22", 5000.0, 800.0, 1500.0);
printf("The answer is %s, but it should be %s\n", booleanToString(withinRange(MIG22, 523.0)), "Within Range");
printf("The answer is %s, but it should be %s\n", booleanToString(withinRange(F22, 3000.1)), "Out of Range");
printf("The answer is %s, but it should be %s\n", booleanToString(withinRange(Tornado, 3000.1)), "Within Range");


/*Substitution
	booleanToString(withinRange(Tornado, 3000.1)
	booleanToString(withinRange(new_fighter_jet("Tornado", 2000.0, 3200.0, 6000.0), 3000.1)
	booleanToString(withinRange(new_fighter_jet(designation0, acceleration0, top_speed0, range0), 3000.1)											
												fighter_jet* f = new fighter_jet
												f->designation = "F22"
												f->acceleration = 2000.0
												f->top_speed = 3200.0
												f->range = 6000.0
	booleanToString(withinRange(f->range, 3000.1)
	booleanToString(withinRange(6000.0, 3000.1)	
								if (distance <= f->range) {
									return true;
								}
								else {
									return false;
								}
	booleanToString(withinRange(6000.0, 3000.1)	
								if (3000.1 <= 6000.0) {
									return true;
								}
								else {
									return false;
								}	
	booleanToString(withinRange(6000.0, 3000.1)	
								if (true) {
									return true;
								}
								else {
									return false;
								}	
	booleanToString(withinRange(6000.0, 3000.1)	
									return true;	
	booleanToString(True)
					if ( it ) {
					return "true";
					} else {
					return "false";	
	booleanToString(True)
					if ( True) {
					return "true";
					} else {
					return "false";	
	booleanToString(True)
					return "true";	
*/
}