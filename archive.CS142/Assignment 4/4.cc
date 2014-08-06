/*Kevin DeVocht Assignment 4: Exercise 4. Encode the following interval as a function: everything outside [1,3]
*/

#include <stdio.h>

// booleanToString : boolean -> string
const char* booleanToString ( bool it ) {
  // Template: it

  // Distinguishes test 1 from test 2
  if ( it ) {
    // Test 1
    // it = true
    return "true";
  } else {
    // Test 2
    // it = false
    return "false";
  }
}

//Contract: everyThingOutside: number -> boolean
//Purpose: To find out if a given number does not fall within the interval [1,3]

bool everyThingOutside ( int number) {

//Template: number

/*Test 1
	everyThingOutside(1)
		number = 1
		ans = false
	return "false"
*/

/*Test 2
		everyThingOutside(4)
		number = 4
		ans = true
	return "true"
*/

/*Generalize Tests 1 and 2
	To generalize the tests you must use a boolean statement to find out if the given number meets all of the qualitys of the interval
*/

return (!((number >= 1) && (number <= 3)));
}
int main () {

	printf("The answer is %s but it should be %s\n", booleanToString(everyThingOutside(1)), "false");
	printf("The answer is %s but it should be %s\n", booleanToString(everyThingOutside(4)), "true");
	printf("The answer is %s but it should be %s\n", booleanToString(everyThingOutside(2)), "false");
	
/*Substitution
	booleanToString(everyThingOutside(2))
	booleanToString(everyThingOutside(number))
	booleanToString(everyThingOutside(!((number >= 1) && (number <= 3))))
	booleanToString(everyThingOutside(!((2 >= 1) && (2 <= 3))))
	booleanToString(everyThingOutside(!((True) && (True))))
	booleanToString(everyThingOutside(!(True)))
	booleanToString(everyThingOutside(False))
	booleanToString(everyThingOutside(if ( it ) {
		return "true";
		} else {
		return "false";))
	booleanToString(everyThingOutside(if ( False) {
		return "false";))
		}
	booleanToString(
		return "false";)
*/
}