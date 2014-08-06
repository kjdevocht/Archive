/*Kevin DeVocht Assignment 5 Exercise 3. Develop the function reduceRange. The function consumes a jet fighter and 
produces a fighter in which the range field is reduced to 80% of the original value.
*/
#include <stdio.h>


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

//Contract: reduceRange: fighter_jet -> fighter_jet
//Purpose: To reduce the range of a fighter jet to 80%

fighter_jet* reduceRange ( fighter_jet* f){
//Template f f->range

/*Test 1
	reduceRange(MIG22)
		f->range = 1500.0
		ans = 1500.0 *0.8
	return 1200.0
*/

/*Test 2
	reduceRange(F22)
		f->range = 3000.0
		ans = 3000.0 *0.8
	return 2400.0
*/

return new_fighter_jet(f->designation, f->acceleration, f->top_speed, f->range * 0.8);  //Math so easy a caveman could do it.  No need to generalize
}

int main (){

fighter_jet* MIG22 = new_fighter_jet("MIG22", 5000.0, 800.0, 1500.0);
fighter_jet* F22 = new_fighter_jet("F22", 10000.0, 1600.0, 3000.0);
fighter_jet* Tornado = new_fighter_jet("Tornado", 2000.0, 3200.0, 6000.0);

printf("The answer is %s, %f, %f,%f\nbut should be %s, %f, %f,%f\n", MIG22->designation, MIG22->acceleration, MIG22->top_speed, MIG22->range, "MIG22", 5000.0, 800.0, 1500.0);
printf("The answer is %s, %f, %f,%f\nbut should be %s, %f, %f,%f\n", F22->designation, F22->acceleration, F22->top_speed, F22->range, "F22", 10000.0, 1600.0, 3000.0);
printf("The answer is %f, but it should be %f\n", reduceRange(MIG22)->range, 1200.0);
printf("The answer is %f, but it should be %f\n", reduceRange(F22)->range, 2400.0);
printf("The answer is %f, but it should be %f\n", reduceRange(Tornado)->range,4800.0 );

/*Substitution
	reduceRange(Tornado)->range
	reduceRange(new_fighter_jet("Tornado", 2000.0, 3200.0, 6000.0))->range
	reduceRange(new_fighter_jet(designation0, acceleration0, top_speed0, range0))->range
	reduceRange(new_fighter_jet(designation0, acceleration0, top_speed0, range0))->range
								fighter_jet* f = new fighter_jet ();
									f->designation = designation0;
									f->acceleration = acceleration0;
									f->top_speed = top_speed0;
									f->range = range0;
								return f;
	reduceRange(new_fighter_jet(designation0, acceleration0, top_speed0, range0))->range
								fighter_jet* f = new fighter_jet ();
									f->designation = "Tornado;
									f->acceleration = 2000.0;
									f->top_speed = 3200.0;
									f->range = 6000.0;
								return f;							
	reduceRange(new_fighter_jet("Tornado", 2000.0, 3200.0, 6000.0 * 0.8))->range
	reduceRange(new_fighter_jet("Tornado", 2000.0, 3200.0, 4800.0))->range
	reduceRange(new_fighter_jet)->range =4800.0
	retun 4800.0
*/

}
