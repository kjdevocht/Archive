/* Kevin DeVocht Assignment 8 Exercise 1. Develop the function perimeter, which consumes either a circle or a square and computes the perimeter.
*/

#include <stdio.h>
#include <math.h>


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

// A Shape is a...
//  Circle
//  or, Square
class Shape {
public:
  // area : Shape -> double
  // Purpose: return the area of the given shape
  virtual double perimeter ( ) = 0 ;
};

// A Circle is a..
//  new Circle ( center, radius )
// where
//  center is a Point
//  radius is a double
class Circle : public Shape {
public:
  Point* center;
  double radius;

  Circle( Point* center0, double radius0 ) {
    this->center = center0;
    this->radius = radius0;
  }
  
 //Contract: perimeter: Circle->decimal
 //Purpose: To find the perimeter of a circle
 double perimeter () {
  //Template: this this->center this->radius
  
 /*Test 1
	cookie->perimeter()
		center = (1.0,1.0)
		radius = 4.0
		ans = 2*M_PI*4.0
	return 25.13274	
 */
 /*Test 2
	pizza->perimeter()
		center = (5.0,5.0)
		radius = 12.0
		ans = 2*M_PI*12.0
	return 75.39822
 */
 //No need to generalize
  return 2*M_PI*this->radius;
  }
};

// A Square is a...
//  new Square( top_left, side_len )
// where
//  top_left is a Point
//  side_len is a double
class Square : public Shape {
public:
  Point* top_left;
  double side_len;

  Square( Point* top_left0, double side_len0 ) {
    this->top_left = top_left0;
    this->side_len = side_len0;
  }
//Contract: perimeter: Square->decimal
//Purpose: to find the perimeter of a square
double perimeter () {
//Template: this this->top_left this->side_len

/*Test 1
	hip->perimeter()
		top_left = (2.0,2.0)
		side_len = 2.0
		ans = 2.0*4
	return 8.0
*/

/*Test 2
	Jay->perimeter()
		top_left = (0.0,0.0)
		side_len = 5.0
		ans = 5.0*4
	return 10.0		
*/
//No need to generalize
return 4*this->side_len;
}
};


//Contract: perimeter: Shape->Shape
//Purpose: To find the perimeter of any shape
double perimeter (Shape* s) {
//Template: s

 /*Test 1
	cookie->perimeter()
		center = (1.0,1.0)
		radius = 4.0
		ans = 2*M_PI*4.0
	return 25.13274	
 */
 
 /*Test 2
	Jay->perimeter()
		top_left = (0.0,0.0)
		side_len = 5.0
		ans = 5.0*4
	return 20.0	
*/
/*Generalize Tests 1 and 2
	the return must call the perimeter function for any shape
*/
return s->perimeter();
}

int main () {

Shape* cookie = new Circle(new Point(1.0,1.0), 4.0);
Shape* pizza = new Circle(new Point(5.0,5.0), 12.0);

Shape* hip = new Square(new Point(2.0,2.0), 2.0);
Shape* Jay = new Square(new Point(0.0,0.0), 5.0); //Cause it is hip to be square

printf("The answer is %f but should be %f\n", cookie->perimeter(), 25.13274);
printf("The answer is %f but should be %f\n", Jay->perimeter(), 20.0);
printf("The answer is %f but should be %f\n", hip->perimeter(), 8.0);
printf("The answer is %f but should be %f\n", pizza->perimeter(), 75.39822);

/*Substitution for a circle
1. pizza->perimeter()

2. return s->perimeter()

3. return pizza->perimeter()

4. return (new Circle(new Point(5.0,5.0), 12.0))->perimeter()

5. return 2*M_PI*this->radius

6. return 2*M_PI*(new Circle(new Point(5.0,5.0), 12.0))->radius

7. return 2*M_PI*12.0
*/

/*Substitution for a Square
1. Jay->perimeter()

2. return s->perimeter()

3. return Jay->perimeter()

4. return (new Square(new Point(0.0,0.0), 5.0))->perimeter()

5. return 4*this->side_len;

6. return 4*(new Square(new Point(0.0,0.0), 5.0))->side_len;

7. return 4*5.0
*/
}