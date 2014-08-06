/* Kevin DeVocht Assignment 3 Exercise 2 Develop the function areaOfTube. It computes the surface area of a tube, which is an open cylinder. The program consumes three values: the tube’s inner radius, its length, and the thickness of its wall. [Use Google if you don’t know the equation.] You should write at least two auxiliary functions.
*/



#include <stdio.h>
#include <math.h>

/*Information: inner radius, length, thickness of its wall, goal: surface area of the tube


S=2?(R²-r²)+2?h(R+r)

2?h(R+r) = Surface area of a curved surface

2?(R²-r²) = Surface area of the top ring  and bottom ring



Data Relationship

area of curved surface areas of rings -> Surface area of tube

inner radius length thickness->area of curved surface area
inner radius thickness->areas of rings

inner radius is a double
length is a double
thickness is a radius
area of curved surface area is a double
area of rings is a double
area of tube is a double
*/

//Contract: areaOfRing: decimal number deciaml number -> decimal number
//Purpose : To find the area of the top and bottom of the tube using the formula sA = 2PI(R^2-r^2)

double areaOfRing ( double radius, double thickness){

//Template: radius thickness

/*Test 1
	areaOfRing(2.0, 1.0)
	radius = 2.0
	thickness = 1.0
	ans = 2 * M_PI * ((2.0+1.0)*(2.0*1.0)-2.0*2.0)
return 2 * M_PI * 5
*/
	

/*Test 2
	areaOfRing(3.0, 2.0)
	radius = 3.0
	thickness = 2.0
	ans = 2 * M_PI * ((3.0+2.0)*(3.0*2.0)-3.0*3.0)
return 2 * M_PI * 16;
*/


/*Generalize Tests 1 and 2
	In order to Generalize the tests you must substitute the actual numbers with the values 	radius and thickness that areaOfRing consumes in main
*/


return 2*M_PI*((thickness+radius)*(thickness+radius)-radius*radius);
}

//Contract: areaOfCurvedSurface: decimal number decimal number decimal number
//Purpose: To find the surface area of the curved surfaces of the tube

double areaOfCurvedSurface (double length, double radius, double thickness) {

//Template: length radius thickness

/*Test 1
	areaOfCurvedSurface(3.0,2.0,1.0)
	length = 3.0
	radius = 2.0
	thickness = 1.0
	ans = 2*M_PI*3.0*((2.0+1.0)+2.0)
return 2*M_PI*15
*/

/*Test 2
	areaOfCurvedSurface(5.0,6.0,7.0)
	length = 5.0
	radius = 6.0
	thickness = 7.0
	ans = 2*M_PI*5.0*((6.0+7.0)+6.0)
return 2*M_PI*95
*/


/*Generalize Tests 1 and 2
	You must use the values of length radius and thickness to allow for any possiblity
*/


return 2*M_PI*length*((radius+thickness)+radius);

}


//Contract: areaOfTube: decimal number decimal number -> decimal number
//Purpose: To find the total surface area of a tube using areaOfCurvedSurface and areaOfRing

double areaOfTube ( double length, double radius, double thickness) {

//Template length radius thickness

/*Test 1
	areaOfTube(3.0,2.0,1.0)
	length = 3.0
	radius = 2.0
	thickness = 1.0
	ans = areaOfCurvedSurface(3.0, 2.0, 1.0)+areaOfRing(2.0, 1.0)
return areaOfCurvedSurface(3.0, 2.0, 1.0)+areaOfRing(2.0, 1.0);
*/


/*Test 2
	areaOfTube(5.0,6.0,7.0)
	length = 5.0
	radius = 6.0
	thickness = 7.0
	ans =areaOfCurvedSurface(5.0, 6.0, 7.0)+areaOfRing(6.0, 7.0)
return areaOfCurvedSurface(5.0, 6.0, 7.0)+areaOfRing(6.0, 7.0);
*/


/*Generalize Tests 1 and 2
	Use the values length radius and thickness that areaOfTube captures to call 	areaOfCurvedSurface and areaOfRing tofind the answer
*/

return areaOfCurvedSurface(length, radius, thickness)+areaOfRing(radius, thickness);

}

	
int main () {

	printf("The answer is %f but it should be %f\n", areaOfRing(2.0, 1.0), 31.41592);
	printf("The answer is %f but it should be %f\n", areaOfRing(3.0, 2.0), 100.53096);
	printf("The answer is %f but it should be %f\n", areaOfRing(4.0, 5.0), 408.40705);
	printf("The answer is %f but it should be %f\n",areaOfCurvedSurface(3.0,2.0,1.0),94.24778);
	printf("The answer is %f but it should be %f\n",areaOfCurvedSurface(5.0,6.0,7.0),596.9026);
	printf("The answer is %f but it should be %f\n",areaOfCurvedSurface(2.0,1.0,2.0),50.26548);
	printf("The answer is %f but it should be %f\n",areaOfTube(3.0,2.0,1.0),125.6637);
	printf("The answer is %f but it should be %f\n",areaOfTube(5.0,6.0,7.0),1432.566246);
	printf("The answer is %f but it should be %f\n",areaOfTube(2.0,1.0,2.0),100.53096);

/*Substitution for areaOfRing
	areaOfRing(4.0, 5.0)
	= 2*M_PI*((thickness+radius)*(thickness+radius)-radius*radius)
	= 2*M_PI*((5.0+4.0)*(5.0+4.0)-4.0*4.0)
	= 2*M_PI*(9.0*9.0-4.0*4.0)
	= 2*M_PI*(81.0-16.0)
	= 2*M_PI*(65)
	= M_PI*130
*/

/*Substitution for areaOfCurvedSurface
	areaOfCurvedSurface(2.0,1.0,2.0)
	=2*M_PI*length*(thickness+radius)+radius)
	=2*M_PI*2.0*((1.0+2.0)+1.0)
	=2*M_PI*2.0*(3.0+1.0)
	=2*M_PI*2.0*(4.0)
	=2*M_PI*8.0
	=M_PI*16.0
*/

/*Substitution for areaOfTube
	areaOfTube(2.0,1.0,2.0)
	=areaOfCurvedSurface(length, radius, thickness) + areaOrRing(radius, thickness)
	=areaOfCurvedSurface(2.0, 1.0, 2.0) + areaOrRing(1.0, 2.0)
	=(2*M_PI*length*(thickness+radius)+radius)) + (2*M_PI*((thickness+radius)*(thickness	+radius)-radius*radius))
	=(2*M_PI*2.0*(2.0+1.0)+1.0)) + (2*M_PI*((2.0+1.0)*(2.0+1.0)-1.0*1.0))
	=(2*M_PI*2.0*(3.0+1.0)) + (2*M_PI*(3.0*3.0-1.0*1.0)
	=(2*M_PI*2.0*4.0) + (2*M_PI*(9.0-1.0)
	=(2*M_PI*8.0) + (2*M_PI*8.0)
	=(M_PI*16.0) + (M_PI*16.0)
*/
}