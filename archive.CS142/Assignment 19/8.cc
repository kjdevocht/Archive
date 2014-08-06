/*Kevin DeVocht assignment 18 exercise 8. Create a program called 
lineCount that reads a file and prints out how many lines are in it. 
Write in accumulator style. (Hint: The character for newlines is ’\n’.)
*/

#include <stdio.h>
#include <math.h>
#include <string.h>

//lineCountAccum: file, int, char->int
//Purpose: To return how many lines are in a given file
int lineCountAccum(FILE* theFile, int answerSoFar, char c)
//Template: theFile, answerSoFar, c
{

	if (c == -1)
	/*Test 1
		lineCountAccum(i)
			theFile = i
			answerSoFar = 1
			c = -1
			ans = 1
		return asnwerSoFar
	*/
	{
		return answerSoFar;
	}
	else
	/*Test 2
		lineCountAccum(f)
			theFile = f
			answerSoFar = 1
			c = 1
			ans = 6
		return asnwerSoFar + 5 
	*/
	
	/*Distinguish Tests 1 and 2
		We know that when we get to -1 the file is done and we need
		to return the answerSoFar.  Otherwise we need to test if
		the c = \n if it is increase answerSoFar by 1 and keep checking
		or just keep checking
	*/
	{
		if (c == '\n')
		{
			return lineCountAccum(theFile, answerSoFar + 1, getc(theFile));
		}
		else
		{
			return lineCountAccum(theFile, answerSoFar, getc(theFile));
		}
	}
}


//lineCount: FILE->int
//Purpose: to return how many lines are in a given file based off of lineCountAccum
int lineCount(FILE* theFile)
//Template: theFile
{
/*Test 1
	lineCount(f)
		the file = f
		ans = 6
	return lineCountAccum(theFile, 1, getc(theFile));
*/

/*Test 2
	lineCount(i)
		the file = i
		ans = 1
	return lineCountAccum(theFile, 1, getc(theFile));
*/

/*Generalize Tests 1 and 2
	We know this is a wrapper function and that every file has at least one line
	so we need to pass a one to lineCountAccum
*/
	return lineCountAccum(theFile, 1, getc(theFile));
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