/*Kevin DeVocht assignment 18 exercise 6. Convert wordCount to 
use mutation and a while loop.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>





//wordCount: FILE->int
//Purpose: to count how many words are in a given file
int wordCount(FILE* theFile)
//Template: theFile, answerSoFar
{
int answerSoFar = 0;
char c = getc(theFile);
char l = ' ';
	/*Test 1
		wordCount(Test4.1)
			theFile = "two words"
			answerSoFar = 2
			ans = 2
		return answerSoFar
	*/
	
	/*Test 2
		wordCount(Test4.2)
			theFile = "Now three        words"
			answerSoFar = 3
			ans = 3
		return answerSoFar
	*/
	
	/*Distinguish Tests 1 and 2
		we know we need a while loop.  Also
		in order to check to see if we are at the
		begining of a word by check to see if
		last is a ' ' and c is not a ' '
		then when you get to the end of the file
		return the answerSoFar
	*/
	while (c != -1)
	{
		if (l == ' ' && c != ' ')
		{
			l=c;
			c = getc(theFile);
			answerSoFar = answerSoFar + 1;
		}
		else
		{
			l=c;
			c = getc(theFile);
		}
	}
	return answerSoFar;
}





int main()
{


FILE* f = fopen("Test4.1.txt", "r");
FILE* i = fopen("Test4.2.txt", "r");
FILE* l = fopen("Test4.3.txt", "r");
FILE* e = fopen("Test4.4.txt", "r");

printf( "The answer is %d but should be %d\n", wordCount(f), 2);
printf( "The answer is %d but should be %d\n", wordCount(i), 3);
printf( "The answer is %d but should be %d\n", wordCount(l), 0);
printf( "The answer is %d but should be %d\n", wordCount(e), 30);

//Substitution:  Ran out of Time!
}