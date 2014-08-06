/*Kevin DeVocht Assignment 9 Exercise 6. Use your list of strings data definition and write a 
function called longestColourLength that returns the length of longest color in the the list. 
(max and strlen may be useful.)
*/



#include <stdio.h>
#include <math.h>
#include <string.h>


//Contract:max: int int->int
//Purpose: To see which number is larger
int max (int a, int b) {
if (a>b) {return a;} else {return b;}
}

// a listOfColors is either
//a color
// or a lastColor
class listOfColors {
public:

//Contract: longestColourLength: listOfColors->int
//Purpose: To find the longest color in a list
virtual int longestColourLength ()= 0;
};

//a color is 
// a new color(name, restOfList
//Where
//name is a string
//and restOfList is a listOfColors
class color: public listOfColors{
	public:
			const char* name;
			listOfColors* restOfList;

color(const char* name0, listOfColors* restOfList0) {
	this->name = name0;
	this->restOfList = restOfList0;
}

//Contract: longestColourLength: color->int
//Purpose: To find the length of a color
int longestColourLength () {
//Template: this, this->name, this->restOfList, this->restOfList->longestColourLength()
/*Test 1
	red->longestColourLength()
		name = "red"
		restOfList = "blue"
	
		
*/
return max(strlen(this->name), this->restOfList->longestColourLength());
}

};




//a lastColor is
//a new lastColor()
//where there is nothing inside because it is the end of the list
class lastColor: public listOfColors {
public:

lastColor(){
}

int longestColourLength (){
return 0;
}

};

int main () {


listOfColors* last = new lastColor();
listOfColors* blue = new color("blue", last);
listOfColors* red = new color("red", blue);
listOfColors* yellow = new color("yellow", red);
listOfColors* green = new color("green", yellow);
listOfColors* purple = new color("purple", yellow);
listOfColors* pink = new color("pink", purple);


printf("the answer is %d but should be %d\n", red->longestColourLength(), 4);
}