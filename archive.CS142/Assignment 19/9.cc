/*Kevin DeVocht assignment 18 exercise 9. Convert lineCount 
to use mutation and a while loop.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>


//lineCount: FILE->int
//Purpose: to find how many chars are in a given file
int lineCount(FILE* theFile)
//Template: theFile, answerSoFar, c
{

	char c = getc(theFile);
	int answerSoFar = 1;
	
	/*Test 1
		lineCountAccum(i)
			theFile = i
			answerSoFar = 1
			c = -1
			ans = 1
		return asnwerSoFar
	*/
	
	/*Test 2
		lineCountAccum(f)
			theFile = f
			answerSoFar = 1
			c = 1
			ans = 6
		return asnwerSoFar + 5 
	*/
	/*Distinguish Tests 1 and 2
		we know this will be a while loop
		so just convert 18.8 into a whil loop
		super easy
	*/
	while (c != -1)
	{
		if (c == '\n')
		{
		c = getc(theFile);
		answerSoFar = answerSoFar + 1;
		}
		else
		{
			c = getc(theFile);
		}
	}
	return answerSoFar;
}



int main()
{
FILE* f = fopen("Test8.1.txt", "r");
FILE* i = fopen("Test8.2.txt", "r");
FILE* l = fopen("Test8.3.txt", "r");
FILE* e = fopen("Test8.4.txt", "r");


printf( "The answer is %d but should be %d\n", lineCount(f), 6);
printf( "The answer is %d but should be %d\n", lineCount(i), 1);
printf( "The answer is %d but should be %d\n", lineCount(l), 6);
printf( "The answer is %d but should be %d\n", lineCount(e), 4);

//Substitution:  Ran out of Time!
}