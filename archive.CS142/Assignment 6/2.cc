/*Kevin DeVocht Assignment 6 Exercise 2. Define a structure that keeps track of the cat’s (x,y) coordinate 
and its happiness (expressed as a percentage number between 0 to 100.) Write a function called feed that consumes 
a cat and produces a cat with an increased happiness by 10%.
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

//Contract: Feed: Cat* -> Cat*
//Purpose: To make a little kitty happy
Cat* Feed (Cat*c){
//Template: c c->posn c->happiness

if (c->happiness <=90) {
return new_Cat(c->posn, c->happiness + 10);
}
/*Test 1
	Feed(Happy)
	posn = 5,0
	happiness = 85
	ans = 85+10
return 95
*/

/*Test 2
	Feed(Sad)
	posn = 5, 800
	happiness = 35
	ans = 35+10
return 45
*/

/*Generalize Tests 1 and 2
	In Order to generalize these tests we must use "c" to allow for all cats
*/
else {
/*Test 3
	Feed(Punch_Drunk)
		posn = 0, 52365
		happiness = 99
		ans = 99+10 >= 100
	return 100
*/
/*Generalize all Tests
	Despite what all those motivational books and seminars say, you can’t have more than 100% happiness so the if statement will make sure that you do not go over
*/
return new_Cat(c->posn, 100);
 }
 }
 
 


int main () {
Cat* Happy = new_Cat(new_Point(5, 0), 85);
Cat* Sad = new_Cat(new_Point(5, 800), 35);
Cat* Punch_Drunk = new_Cat(new_Point(0, 52365), 99);

printf("The answer is %d but it should be %d\n", Feed(Happy)->happiness, 95);
printf("The answer is %d but it should be %d\n", Feed(Sad)->happiness, 45);
printf("The answer is %d but it should be %d\n", Feed(Punch_Drunk)->happiness, 100);

/*Substitution
	Feed(Sad)
	Feed(new_Cat(new_Point(5, 800), 35))
	
	if (c->happiness <=90) {
		return new_Cat(c->posn, c->happiness + 10);}
	else{
		return new_Cat(c->posn, 100);}

	if (35 <=90) {
		return new_Cat((5,800), 35 + 10);}
		
		return new_Cat((5,800), 100);
		
	return new_Cat((5,800), 35 + 10);
		
	return new_Cat((5,800), 45);
*/
}