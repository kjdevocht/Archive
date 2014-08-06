/*Kevin DeVocht Assignment 4: Exercise 6 Write a function that identifies a solution to this equation: 4n + 2 = 62 */

#include <stdio.h>
#include <math.h>

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

//Contract: ForN: number -> number
//Purpose: Find a solution to the equation 4n + 2 = 62

bool ForN (int N) {
//Template: N

/*Test 1
	ForN(14)
		N = 14
		ans = False
	return "false";
*/

/*Test 2
	ForN(15)
		N = 15
		ans = True
	return "true";
*/

/*Generalize Tests 1 and 2
	us "N" to solve the equation 4n + 2 = 62
*/
return ((4*N) +2 == 62);

}

int main () {


	printf("The answer is %s but it should be %s\n", booleanToString(ForN (14)), "false");
	printf("The answer is %s but it should be %s\n", booleanToString(ForN (15)), "true");
	printf("The answer is %s but it should be %s\n", booleanToString(ForN (27)), "false");
	
	/*Substitution
		booleanToString(ForN (27))
		booleanToString(ForN (N))
		booleanToString(ForN ((4*N) +2 == 62))
		booleanToString(ForN (True))booleanToString(ForN(if ( it ) {
		return "true";
		} else {
		return "false";))
	booleanToString(ForN(if ( False) {
		return "false";))
		}
	booleanToString(
		return "false";)
	*/


}