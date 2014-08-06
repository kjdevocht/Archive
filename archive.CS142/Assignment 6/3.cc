/*Kevin DeVocht Assignment 6 Exercise 3. Write a cat-to-cat function called newspaper that decreases a cat’s happiness by 15.
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

 
//Contract: newspaper: Cat*->Cat*
/*Purpose: To beat a cat with a newpaper and reduce it's happiness 
(Don't let PETA know about this exercise or they will sue you for abusing a virtual animal)
*/
Cat* newspaper (Cat*c){
//Template: c c->posn c->happiness
/*Test 1
	newspaper(Happy)
		posn = 5,0
		happiness = 85
		ans = 85-15
	return 70
*/

/*Test 2
	newspaper(Sad)
		posn = 5, 800
		happiness = 35
		ans =35-15
	return 20
*/

/*Generalize Tests 1 and 2
	In order to generalize these two tests you must use c->happiness to account for all cats
*/
return new_Cat(c->posn, c->happiness -15);
}


int main () {
Cat* Happy = new_Cat(new_Point(5, 0), 85);
Cat* Sad = new_Cat(new_Point(5, 800), 35);
Cat* Punch_Drunk = new_Cat(new_Point(0, 52365), 99);

printf("The answer is %d but it should be %d\n", newspaper(Happy)->happiness, 70);
printf("The answer is %d but it should be %d\n", newspaper(Sad)->happiness, 20);
printf("The answer is %d but it should be %d\n", newspaper(Punch_Drunk)->happiness, 84);

/*Substitution
	newspaper(Sad)
	newspaper(new_Cat(new_Point(5, 800), 35))
	new_Cat(c->posn, c->happiness -15))
	new_Cat((5, 800), 35-15))
	new_cat((5,800), 20)
*/
}