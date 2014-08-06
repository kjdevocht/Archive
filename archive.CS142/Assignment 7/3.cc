/*Kevin DeVocht Assignment 6 Exercise 3. Redo day 6, exercise 2 (about feeding cats) with methods
*/


#include <stdio.h>

// A Point is a 
// new Point (x, y)
// where 
// - x is a double
// - y is a double
class Point {

public:
  double x;
  double y;


// Contract: new Point : double double -> Point*
Point(double x0, double y0) {
  this->x = x0;
  this->y = y0;
  }
  };


//A Cat is a...
//new cat(posn, happiness)
//where
//posn is a Point*
// happiness is a double

class Cat {

public:
int happiness;
Point* posn;



Cat(Point* posn0, int happiness0) {
	this->posn = posn0;
	this->happiness = happiness0;
}

//Contract: Feed: Cat* -> Cat*
//Purpose: To make a little kitty happy
Cat* Feed (){
//Template: c c->posn c->happiness

if (this->happiness <=90) {
return new Cat(this->posn, this->happiness + 10);
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
	In Order to generalize these tests we must use "this" to allow for all cats
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
return new Cat(this->posn, 100);
 }
 }
 };
 


int main () {
Cat* Happy = (new Cat(new Point(5, 0), 85));
Cat* Sad = (new Cat(new Point(5, 800), 35));
Cat* Punch_Drunk = (new Cat(new Point(0, 52365), 99));

printf("The answer is %d but it should be %d\n", Happy->Feed()->happiness, 95);
printf("The answer is %d but it should be %d\n", Sad->Feed()->happiness, 45);
printf("The answer is %d but it should be %d\n", Punch_Drunk->Feed()->happiness, 100);

/*Substitution
	Sad->Feed()
	new_Cat(new Point(5, 800), 35)->if (this->happiness <=90) {return new Cat(this->posn, this->happiness + 10); else {return new Cat(this->posn, 100);}}
	new_Cat(new Point(5, 800), 35)->if (this->35 <=90) {return new Cat(this->(5, 800), this->35 + 10); else {return new Cat(this->(5, 800), 100);}}
	new_Cat(new Point(5, 800), 35)->if (true) {return new Cat(this->(5, 800), this->35 + 10); else {return new Cat(this->(5, 800), 100);}}
	new Cat(this->(5, 800), this->35 + 10)
*/
}