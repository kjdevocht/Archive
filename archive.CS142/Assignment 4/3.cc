/* Kevin DeVocht Assignment 4: Exercise 3 Encode the following interval as a function: (3,7]*/


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

//Contract: Interval: number -> boolean
//Purpose: To see if a given number is within the interval (3,7]

bool Interval ( int number) {
//Template theTruch numbers

/*Test 1
	number = 5
	ans = true	
return true;


/*Test 2
	number = 3
	ans = false
return false;
*/

/* Generalize Tests 1 and 2
	Use an "AND" statement to find if "number" fits all criteria of the interval
*/
return ((3 < number) && (number <= 7));
}




int main () {
	
	printf("The answer is %s but it should be %s\n", booleanToString(Interval(5)), "true");
	printf("The answer is %s but it should be %s\n", booleanToString(Interval(3)), "false");
	printf("The answer is %s but it should be %s\n", booleanToString(Interval(10)), "false");
	
/*Substitution
	booleanToString(Interval(10))
	booleanToString(Interval(3 < number && (number < 7))
	booleanToString(Interval( 3 < 10) && ( 10 < 7))
	booleanToString(Interval(True && False))
	booleanToString(Interval(False))
	booleanToString(False)
	booleanToString( if ( it ) {
     return "true";
		} else {
     return "false";
	 booleanToString( if ( false ) {
     return "true";
		} else {
     return "false";
	 booleanToString
      return "false";
*/
}