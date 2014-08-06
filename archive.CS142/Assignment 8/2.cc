/* Kevin DeVocht Assignment 8 Exercise 2. Develop structure and data definitions for a collection of zoo animals. The collection includes

    spiders, whose relevant attributes are the number of remaining legs (we assume that spiders can lose legs in accidents) and the space they need in case of transport;

    elephants, whose only attributes are the space they need in case of transport;

    monkeys, whose attributes are intelligence and space needed for transportation.
*/

#include <stdio.h>


// an animal is..
//a spider
//or elephant
//or monkey

class animal {
public:
};



// A spider is..
// a new spider (num_legs, space)
//where
//num_legs is an int
//space is as double
class spider : public animal {
public:
int num_legs;
double space;

spider(int num_legs0, double space0) {
	this->num_legs = num_legs0;
	this->space = space0;
}
};



//An elephant is..
// a new elephant(space)
//where
//space is a double
class elephant : public animal{
public:
double space;

elephant (double space0) {
	this->space = space0;
	}
	};
	
	
	
//A monkey is...
// a new monkey(int intelligence, double space)
//where
// intelligence is an int
//space is a double

class monkey : public animal{
public:
int intelligence;
double space;

monkey (int intelligence0, double space0) {
	this->intelligence = intelligence0;
	this->space = space0;
}
};

int main () {

printf("I love to go to the zoo");
//Because there are no functions there is nothing to substitute
}