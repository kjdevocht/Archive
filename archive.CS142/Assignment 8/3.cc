/* Kevin DeVocht Assignment 8 Exercise 3. Develop the function fitsHuh. The function consumes a zoo animal and the volume of a cage. It determines whether the cage is large enough for the animal.
*/

#include <stdio.h>


// booleanToString : boolean -> string
const char* booleanToString ( bool it ) {
  if ( it ) { 
  return "true"; } 
  else { 
  return "false"; }
}

// an animal is..
//a spider
//or elephant
//or monkey

class animal {
public:
//Contract: fitsHuh: animal number->boolean
//Purpose: To see if a given animal will fit inside of a cage
virtual bool fitsHuh (double cage) = 0;
};



// A spider is..
// a new spider (num_legs, space)
//where
//num_legs is an int
//space is as double
class spider : public animal{
public:
int num_legs;
double space;

spider(int num_legs0, double space0) {
	this->num_legs = num_legs0;
	this->space = space0;
}

//Contract: fitsHuh: spider double->boolean
//Purpose: To see if a cage is big enough for a given spider
bool fitsHuh (double cage) {
//Template this this->num_legs this->space cage
if (this->space < cage){
/*Test 1
	Charlette->fitsHuh(lil_cage)
		num_legs = 8
		space = 5.0
		cage = 10.0
		ans = 5.0 < 10.0
	return true
		
*/
return true;
}
else {
/*Test 2
	Charlette->fitsHuh(extra_small_cage)
		num_legs = 8
		space = 8.0
		cage = 0.5
		ans = 8.0 > 0.5
	return false
*/
/*Generalzie Tests 1 and 2
	Because there can be more than one possible outcome you must us an if statement the rest is simple math
*/
return false;

}
}
};



//An elephant is..
// a new elephant(space)
//where
//space is a double
class elephant : public animal {
public:
double space;

elephant (double space0) {
	this->space = space0;
}

//Contract: fitsHuh: elephant double->boolean
//Purpose: To see if a cage is big enough for a given elephant
bool fitsHuh (double cage) {
//Template this this->space cage
if (this->space < cage){
/*Test 1
	Babar->fitsHuh(corral)
		space = 600.0
		cage = 5000.0
		ans = 600.0 <5000.0
	return true
*/
return true;
}
else 
/*Test 2
	Babar->fitsHuh(cell)
		space = 600.0
		cage = 50.0
		ans = 600.0 > 50.0
	return false
*/
/*Generalzie Tests 1 and 2
	Because there can be more than one possible outcome you must us an if statement the rest is simple math
*/
return false;
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

//Contract: fitsHuh: monkey double->boolean
//Purpose: To see if a cage is big enough for a given monkey
bool fitsHuh (double cage) {
//Template this this->intelligence this->space cage
if (this->space < cage){
/*Test 1
	George->fitsHuh(cell)
		intelligence = 1
		space = 25.0
		cage = 50.0
		ans = 25.0 < 50.0
	return true
*/
return true;
}
else {

/*Test 2
	George->fitsHuh(lil_cage)
		intelligence = 1
		space = 25.0
		cage = 10.0
		ans = 25.0 > 10.0
	return false
*/
/*Generalzie Tests 1 and 2
	Because there can be more than one possible outcome you must us an if statement the rest is simple math
*/


return false;
}
}

};


//Contract: fitsHuh: animal double->boolean
//Purpose: To see if a cage is big enough for a given animal
bool fitsHuh (animal* a, double cage) {
//Template a cage

/*Test 1
	Babar->fitsHuh(cell)
		space = 600.0
		cage = 50.0
		ans = 600.0 > 50.0
	return false
*/

/*Test 2
	George->fitsHuh(cell)
		intelligence = 1
		space = 25.0
		cage = 50.0
		ans = 25.0 < 50.0
	return true
*/

/*Test 3
	Babar->fitsHuh(cell)
		space = 600.0
		cage = 50.0
		ans = 600.0 > 50.0
	return false
*/
/*Generalize Tests 1 2 and 3
	Because we are dealing with mixed data fitsHuh must just consume an animal and then let the specific animal class do all the heavy lifting
*/
return fitsHuh(a, cage);
}

int main () {
animal* Babar = new elephant(600.0);
animal* Charlette = new spider(8, 5.0);
animal* George = new monkey(1, 25.0);

int extra_small_cage = 0.5;
int lil_cage = 10.0;
int cell = 50.0;
int corral = 5000.0;

printf("The answer is %s but should be %s\n", booleanToString(George->fitsHuh(cell)), "true");
printf("The answer is %s but should be %s\n", booleanToString(George->fitsHuh(lil_cage)), "false");
printf("The answer is %s but should be %s\n", booleanToString(Babar->fitsHuh(corral)), "true");
printf("The answer is %s but should be %s\n", booleanToString(Babar->fitsHuh(cell)), "false");
printf("The answer is %s but should be %s\n", booleanToString(Charlette->fitsHuh(lil_cage)), "true");
printf("The answer is %s but should be %s\n", booleanToString(Charlette->fitsHuh(extra_small_cage)), "false");

/*Substitution
1.booleanToString(Babar->fitsHuh(cell))

2.booleanToString(return fitsHuh(a, cage))

3.booleanToString(return fitsHuh(Babar, 50.0))

4.booleanToString(return fitsHuh(new elephant(600.0), 50.0))

5. booleanToString(if (this->space < cage){return true;}else {return false;})

6. booleanToString(if ((new elephant(600.0))->space < 50.0){return true;}else {return false;})

7. booleanToString(if (600.0 < 50.0){return true;}else {return false;})

8. booleanToString(if false {return true;}else {return false;})

9. booleanToString(false)

10. if ( it ) { return "true"; } else { return "false";}

11. if false  { return "true"; } else { return "false";}

12. return "false"
*/
}