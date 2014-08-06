/*Kevin DeVocht assignment 19 exercise 1. Create a program called 
fileCount that reads a file and prints how many characters, words, 
and lines it contains. This program may only read the file once. 
Write in accumulator style.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>

//A fileCounter is a
// new fileCounter(theChars, theWords, theLines)
// where
// a theChars is an int
// a theWords is an int
// a theLines is an int
class fileCounter {
public:
	int theChars;
	int theWords;
	int theLines;
	
	
	fileCounter(int theChars0, int theWords0, int theLines0)
	{
		this->theChars = theChars0;
		this->theWords = theWords0;
		this->theLines = theLines0;
	}
	
	int show()
	{
	printf("%d, %d, %d\n", 
							this->theChars,
							this->theWords,
							this->theLines);
	return 0;
	}
};

//fileCount: file ->fileCounter
//Purpose: to count the number of chars, words and lines in a given file
fileCounter* fileCount(FILE* theFile)
//Template: theFile, theChars, theWords, theLines, l, c
{
	int theChars = 0; 
	int theWords = 0;
	int theLines = 1;
	char l = ' ';
	char c = getc(theFile);
	/*Test 1
		fileCountAccum(f, 53, 5, 4, '!', '-1')
			theFile = f
			theChars = 53
			theWords = 5
			theLines = 4
			l = '!'
			c = '-1'
			ans = 53:5:4:!
		return new fileCounter(theChars, theWords, theLines);	
	*/
	
	/*Test 2
		fileCountAccum(f, 0, 0, 1, ' ', 'T')
			theFile = f
			theChars = 0
			theWords = 0
			theLines = 1
			l = ' '
			c = 'T'
			ans = 53:5:4:!
		return new fileCounter(theChars, theWords, theLines);	
	*/
	/*Generalize Tests 1 and 2
		we know this will be a while loop
		so just convert 19.1 into a while
		loop.  Make sure the while arguement
		is while c is not -1 then make sure
		to check for all possibilities.
		Super easy
	*/
	while (c != -1)
	{
		if (c == '\n')
		{
			theChars = theChars + 1;
			theLines = theLines + 1;
			c = getc(theFile);
		}
		else
		{
			if (l == ' ' && c != ' ')
			{
				theChars = theChars + 1;
				theWords = theWords + 1; 
				l = c;
				c = getc(theFile);
			}
			else
			{
				theChars = theChars + 1;
				l = c;
				c = getc(theFile);
			}
		}
	}
	return new fileCounter(theChars, theWords, theLines);
}	





int main()
{
FILE* f = fopen("Test1.1.txt", "r");
FILE* i = fopen("Test1.2.txt", "r");
FILE* l = fopen("Test1.3.txt", "r");
FILE* e = fopen("Test1.4.txt", "r");



printf("\nthe answer is \n");
	fileCount(f)->show();	
	printf("\nbut should be \n %d, %d, %d \n",56, 5, 4);
	
printf("\nthe answer is \n");
	fileCount(i)->show();	
	printf("\nbut should be \n %d, %d, %d \n",0, 0, 1);

printf("\nthe answer is \n");
	fileCount(l)->show();	
	printf("\nbut should be \n %d, %d, %d \n",13, 1, 5);
	
printf("\nthe answer is \n");
	fileCount(e)->show();	
	printf("\nbut should be \n %d, %d, %d \n",34, 5, 5);

//Substitution:  Ran out of time!
}