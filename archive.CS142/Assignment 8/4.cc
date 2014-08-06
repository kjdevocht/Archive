/* Kevin DeVocht Assignment 8 Exercise 4. Add rectangles to our area program from class. For our purposes, the description of a rectangle includes its upper-left corner, its width, and its height.
*/


#include <stdio.h>
#include <math.h>
#include <string.h>

// booleanToString : boolean -> string
const char* booleanToString ( bool it ) {
  if ( it ) { return "true"; } else { return "false"; }
}

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

// A Shape is a...
//  Circle
//  or, Square
//or Rectangle
class Shape {
public:
  // area : Shape -> double
  // Purpose: return the area of the given shape
  virtual double area ( ) = 0 ;
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


//Contract: area: Circle->decimal
 //Purpose: To find the area of a circle
  double area ( ) {
    // Template: this, this->center, this->radius
	
/*Test 1
	cookie->area()
		center = (1.0,1.0)
		radius = 4.0
		ans = M_PI*4.0*4.0
	return 50.2654
*/

/*Test2
	pizza->area()
		center = (5.0, 5.0)
		radius = 12.0
		ans = M_PI*12.0*12.0
	return 452.389
*/
//Simple math problem no need to generalize
return M_PI * this->radius * this->radius ;
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


//Contract: area: Square->decimal
//Purpose: to find the area of a square
  double area () {
// Template: this, this->top_left, this->side_len

/*Test1
	hip->area()
		top_left = (2.0, 2.0)
		side_len = 2.0
		ans = 2.0*2.0
	return 4.0
*/
/*Test2
	Jay->area()
		top_left = (0.0, 0.0)
		side_len = 5.0
		ans = 5.0*5.0
	return 25.0		
*/

//Simple math problem no need to generalize
return this->side_len * this->side_len;
}
};










// A Rectangle is a...
// new Rectangle (upper_left, width, height)
//where
//upper_left is a Point*
// width is a double
//height is a double
class Rectangle : public Shape {
public:
	Point* upper_left;
	double width;
	double height;
	
Rectangle (Point* upper_left0, double width0, double height0) {
	this->upper_left = upper_left0;
	this->width = width0;
	this->height = height0;
}

//Contract: area: Rectangle -> double
//Purpose: find the area of a given Rectangle
double area () {
//Template: this this->upper_left this->width this->height

/*Shape*Kevin = new Rectangle(new Point(4.0, 4.0), 3.0, 4.0); //Cause I am not quite a square just yet
Shape* Door = new Recatangle(new Point(6.0, 6.0), 3.0, 7.0);
*/
/*Test1
	Kevin->area()
		upper_left = (4.0, 4.0)
		width = 3.0
		height = 4.0
		ans = 3.0*4.0
	return 12.0
*/
/*Test2
	Door->area()
		upper_left = (6.0, 6.0)
		width = 3.0
		height = 7.0
		ans = 3.0*7.0
	return 21.0
*/

//Simple math problem no need to generalize
return this->width * this->height;
}
};












// area : Shape -> double
// Purpose: to return the area of the given shape
double area ( Shape* s ) {
  // Template: s

  /*Test1
	pizza->area()
		center = (5.0, 5.0)
		radius = 12.0
		ans = M_PI*12.0*12.0
	return 452.389
*/

/*Test2
	hip->area()
		top_left = (2.0, 2.0)
		side_len = 2.0
		ans = 2.0*2.0
	return 4.0
*/

/*Test3
	Kevin->area()
		upper_left = (4.0, 4.0)
		width = 3.0
		height = 4.0
		ans = 3.0*4.0
	return 12.0
*/
/*Generalize Tests 1 2 and 3
	Because this functino is looking at mixed data and needs to look too many layers down you must use specific functions for each kind of shape
*/
  return s->area();
}






int main () {
Shape* cookie = new Circle(new Point(1.0,1.0), 4.0);
Shape* pizza = new Circle(new Point(5.0,5.0), 12.0);

Shape* hip = new Square(new Point(2.0,2.0), 2.0);
Shape* Jay = new Square(new Point(0.0,0.0), 5.0); //Cause it is hip to be square

Shape*Kevin = new Rectangle(new Point(4.0, 4.0), 3.0, 4.0); //Cause I am not quite a square just yet
Shape* Door = new Rectangle(new Point(6.0, 6.0), 3.0, 7.0);

printf("The answer is %f but should be %f\n", cookie->area(), 50.2654);
printf("The answer is %f but should be %f\n", Jay->area(), 25.0	);
printf("The answer is %f but should be %f\n", hip->area(), 4.0);
printf("The answer is %f but should be %f\n", pizza->area(), 452.389);
printf("The answer is %f but should be %f\n", Kevin->area(), 12.0);
printf("The answer is %f but should be %f\n", Door->area(), 21.0);

  return 0;
  /* Substitution for Rec tangle
  1. Door->area()
  
  2.   return s->area();
  
  3. new Rectangle(new Point(6.0, 6.0), 3.0, 7.0)->area()
  
  4. new Rectangle(new Point(6.0, 6.0), 3.0, 7.0)->area()
  
  5. return this->width * this->height;
  
  6. return new Rectangle(new Point(6.0, 6.0), 3.0, 7.0)->width * new Rectangle(new Point(6.0, 6.0), 3.0, 7.0)->height;
  
  7. return 3.0 *7.0
  
  8. return 21.0
  */
  
  /*Substitution for a circle
1. pizza->area()

2. return s->area()

3. return pizza->area()

4. return (new Circle(new Point(5.0,5.0), 12.0))->area()

5. return M_PI * this->radius * this->radius ;

6. return M_PI * (new Circle(new Point(5.0,5.0), 12.0))->radius * (new Circle(new Point(5.0,5.0), 12.0))->radius ;

7. return M_PI *12.0 *12.0;
*/

/*Substitution for a Square
1. Jay->area()

2. return s->area()

3. return Jay->area()

4. return (new Square(new Point(0.0,0.0), 5.0))->area()

5. return this->side_len * this->side_len;

6. return (new Square(new Point(0.0,0.0), 5.0))->side_len * (new Square(new Point(0.0,0.0), 5.0))->side_len;

7. return 5.0*5.0
*/
}

