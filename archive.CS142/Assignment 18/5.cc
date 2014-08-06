/*Kevin DeVocht assignment 18 exercise 5. Convert letterCount 
to use mutation and a while loop.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>


//letterCount: FILE->int
//Purpose: to find how many chars are in a given file
int letterCount(FILE* theFile)
//Template: theFile, answerSoFar
{
	/*Test 1
		leterCount(Test3.1)
			theFile = 123456789
			answerSoFar = 9
			ans = 9
		return answer soFar
	*/

	/*Test 2
		leterCount(Test3.3)
			theFile = This is a Test!
			answerSoFar = 15
			ans = 15
		return answer soFar
	*/
	
	/*Generalize Tests 1 and 2
		We know we will be using
		a while loop and we know that
		the while loop ends at the end of 
		the file which is -1.  as long as
		getc() is not -1 add one to 
		answerSoFar and check again
	*/
	int answerSoFar = 0;
	while (getc(theFile) != -1) 
	{
		answerSoFar = answerSoFar + 1;
	}
	return answerSoFar;
}



int main()
{
FILE* f = fopen("Test3.1.txt", "r");
FILE* i = fopen("Test3.2.txt", "r");
FILE* l = fopen("Test3.3.txt", "r");
FILE* e = fopen("Test3.4.txt", "r");


printf( "The answer is %d but should be %d\n", letterCount(f), 9);
printf( "The answer is %d but should be %d\n", letterCount(i), 0);
printf( "The answer is %d but should be %d\n", letterCount(l), 15);
printf( "The answer is %d but should be %d\n", letterCount(e), 84);

//Substitution:  Ran out of Time!
}