/*Kevin DeVocht Assignment 6 Exercise 4. Write a cat-to-cat function called jump that increases a cat’s y position by 10.
*/

#include <stdio.h>

// A Point is a 
// new_Point (x, y)
// where 
// - x is a double
// - y is a double
typedef struct Point;

typedef struct Point {
  double x;
  double y;
};

// Contract: new_Point : double double -> Point*
Point* new_Point(double x0, double y0) {
  Point* p = new Point();
  p->x = x0;
  p->y = y0;
  return p;
  }
  //I copied this code from class code


//A Cat is a...
//new cat(posn, happiness)
//where
//posn is a Point*
// happiness is a double

typedef struct Cat;

typedef struct Cat {
int happiness;
Point* posn;
};


Cat* new_Cat(Point* posn0, int happiness0) {
Cat* c =new Cat ();
	c->posn = posn0;
	c->happiness = happiness0;
return c;
}
// Contract: move : Point* -> Point*
// Purpose: move the y coordinate by 10.0
Point* move(Point*p) {
  // Template: p p->x p->y 

/*Test 1
	move(5.0,0.0)
		p->x =5.0
		p->y =0.0
		ans =0.0+10.0
	return 10.0
*/
	
/*Test 2
	move(5.0, 800.0)
		p->x =5.0
		p->y = 800.0
		ans = 800.0 + 10.0
	return 810.0
*/
/*Generalize 1 & 2
	in order to generalize you must use p-> + 10 to account for all possible "y"'s
*/
  return new_Point(p->x , p->y + 10.0);
}	

//Contract: jump: Cat*->Cat*
//Purpose: To make a cat jump by 10 units
Cat* jump(Cat*c){
//Template: c c->posn c->happiness

/*Test 1
	jump(Happy)
		posn =5.0, 0.0
		happiness = 85
		ans = new_Cat(5.0, 0.0 + 10.0), 85)
	return new_Cat((5.0,10.0), 85)
*/

/*Test 2
	jump(Sad)
		posn = 5.0, 800.0
		happiness = 85
		ans = new_Cat((5.0, 800.0 + 10), 35)
	return new_Cat((5.0, 810.0), 35)
*/

/*Generalize Tests 1 and 2
	Since function "jump" needs to change a value of posn which is more than layer deep function "jump" 
	must call funciton "move" and feed "move" then generic term c->posn to cover all possible outcomes
*/
return new_Cat(move(c->posn), c->happiness);
}


int main () {
Cat* Happy = new_Cat(new_Point(5.0, 0.0), 85);
Cat* Sad = new_Cat(new_Point(5.0, 800.0), 35);
Cat* Punch_Drunk = new_Cat(new_Point(0.0, 52365.0), 99);

Cat*one = jump(Happy);
Cat*two = jump(Sad);
Cat*three = jump(Punch_Drunk);


printf("The answer is %f but it should be %f\n", one->posn->y, 10.0);
printf("The answer is %f but it should be %f\n", two->posn->y, 810.0);
printf("The answer is %f but it should be %f\n", three->posn->y, 52375.0);

/*Substitution
	jump(Happy)
	
	jump(new_Cat(new_Point(5.0, 0.0), 85))
	
	new_Cat(move(c->posn), c->happiness)
	
	new_Cat(move(5.0, 0.0), 85)
	
	new_Cat(new_Point(p->x , p->y + 10.0), 85)
	
	new_Cat(new_Point(5.0 , 0.0 + 10.0), 85)
	
	new_Cat(new_Point(5.0 ,10.0), 85)
	
	new_Cat((5.0 ,10.0), 85)
*/
}