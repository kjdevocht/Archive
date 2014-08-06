/*Kevin DeVocht assignment 18 exercise 4. Create a program called 
wordCount that reads a file and prints out how many words are in it. 
Write in accumulator style. Remember, words are separated by one or 
more spaces. You must correctly handle words separated by many spaces. 
(Hint: The character for a space is ’ ’.)
*/

#include <stdio.h>
#include <math.h>
#include <string.h>





//wordCountAccum: file, char, int, char->char
//Purpose: to count the number of words in a given file
int wordCountAccum(FILE* theFile, char l, int answerSoFar, char c)
//Template: theFile, l, answerSoFar, c
{
	if (c == -1)
	/*Test 1
		wordCountAccum(Test4.3, ' ', 0, -1)
			theFile =
			l = ' '
			answerSoFar = 0
			c = -1
			ans = 0
		return answerSoFar;
	*/
	{
		return answerSoFar;
	}
	else
	/*Test 2
		wordCountAccum(Test4.1, ' ', 0, t)
			theFile = two words
			l = ' '
			answerSoFar = 0
			c = t
			ans = 2
		return wordCountAccum(theFile, c, answerSoFar, getc(theFile));
	*/
	
	/*Distinguish Tests 1 and 2
		we know that if c = -1 we are at the end of the file
		and we need to return the answerSofar.  otherwise
		we need to see if we are at the begining of a word
		we know we are if l = ' ' and c does not = ' '.
	*/
	{
		if (l == ' ' && c != ' ')
		{
			return wordCountAccum(theFile, c, answerSoFar + 1, getc(theFile));
		}
		else
		{
			return wordCountAccum(theFile, c, answerSoFar, getc(theFile));
		}
	}
}

//wordCount: file->int
//Purpose: To return the word count of a given file based off of wordCountAccum
int wordCount(FILE* theFile)
//Template: theFile
{
/*Test 1
	wordCount(Test4.3, ' ', 0, -1)
		theFile =
		l = ' '
		answerSoFar = 0
		c = -1
		ans = 0
	return wordCountAccum(theFile,' ', 0, getc(theFile));
*/

/*Test 2
	wordCount(Test4.1, ' ', 0, t)
		theFile = two words
		l = ' '
		answerSoFar = 0
		c = t
		ans = 2
	return wordCountAccum(theFile,' ', 0, getc(theFile));
*/

/*Generalize Tests 1 and 2
	We know this is a wrapper function and we need to pass
	the right values to the Accum function.  We know we need
	to pass the theFile, l needs to be set at ' ' , answerSoFar
	needs to be 0 and c is the first char of the file so might 
	as well pass it the first char by calling getc on the file
*/
return wordCountAccum(theFile,' ', 0, getc(theFile));
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

//Substitution:  Ran out of time!
}