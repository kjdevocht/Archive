/*Kevin DeVocht Assignment 9 Exercise 2. Use the data definition for lists of 
strings from class and create a list of colors that contains at least 5 colors.
*/
// time: 25 min

#include <stdio.h>
#include <math.h>
#include <string.h>

// a listOfColors is either
//a color
// or a lastColor
class listOfColors {
public:
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
	this->restOfList = restOfList;
}
};

//a lastColor is
//a new lastColor()
//where there is nothing inside because it is the end of the list
class lastColor: public listOfColors {
public:

lastColor(){
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


printf("Taste the Rainbow");
}