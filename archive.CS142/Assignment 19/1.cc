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


	
//fileCountAccum: file, int, int, int, char, char->fileCounter
//Purpose: to count the number of chars, words and lines in a given file
fileCounter* fileCountAccum(FILE* theFile, int theChars, int theWords, int theLines, char l, char c)
//Template: theFile, theChars, theWords, theLines, l, c
{

	if (c == -1)
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
	{
		return new fileCounter(theChars, theWords, theLines);
	}
	else
	{
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
		we can see that when we reach the end of the file return the running total.  Otherwise we
		need to conduct all the tests from previous programs to see if we need to increase Chars
		Words or Lines
	*/
		if (c == '\n')
		{
			return fileCountAccum(theFile, theChars + 1, theWords, theLines + 1,  l, getc(theFile));
		}
		else
		{
			if (l == ' ' && c != ' ')
			{
				return fileCountAccum(theFile, theChars + 1, theWords + 1, theLines, c, getc(theFile));
			}
			else
			{
				return fileCountAccum(theFile, theChars + 1, theWords, theLines, c, getc(theFile));
			}
		}
	}
}

//fileCount: file->int
//Purpose: To return the char count, word count and line count of a given file based off of fileCountAccum
fileCounter* fileCount(FILE* theFile)
//Template: theFile
{
/*Test 1
	fileCount(f)
		theFile = f
		ans = 53:5:4:!
	return fileCountAccum(theFile,0,0,1,' ', getc(theFile));
*/

/*Test 2
	fileCount(i)
		theFile = i
		ans = 0:0:1:!
	return fileCountAccum(theFile,0,0,1,' ', getc(theFile));
*/

/*Generalize Tests 1 and 2
	we know that this is a wrapper function and we know from previous programs
	that we need to pass zeros to theChar and theWord and a 1 to theLine and a ' ' to l
*/
return fileCountAccum(theFile,0,0,1,' ', getc(theFile));
}
	
	

int main()
{
FILE* f = fopen("Test1.1.txt", "r");
FILE* i = fopen("Test1.2.txt", "r");
FILE* l = fopen("Test1.3.txt", "r");
FILE* e = fopen("Test1.4.txt", "r");

fileCounter* mt = new fileCounter(0,0,1);


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