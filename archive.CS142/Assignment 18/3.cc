/*Kevin DeVocht assignment 18 exercise 3. Create a program called 
letterCount that reads a file and prints out how many characters 
are in it. Write in accumulator style. (Remember the example we did in class!)
*/

#include <stdio.h>
#include <math.h>
#include <string.h>




//letterCountAccum: file int->int
//Purpose: to see how many char's are in a given file
int letterCountAccum(FILE* theFile, int theNum)
//Template: theFile, theNum
{
	/*Test 1
		letterCountAccum(Test3.1, 0)
			theFile = 123456789
			theNum = 0
			ans = 9
		return letterCountAccum(theFile, theNum + 1);
	*/
	if (getc(theFile) == -1)
	{
		return theNum;
	}
	else
	/*Test 2
		letterCountAccum(Test3.3, 0)
			theFile = This is a Test!
			theNum = 0
			ans = 15
		return letterCountAccum(theFile, theNum + 1);
	*/
	
	/*Generalize Tests 1 and 2
		We need to check to see if we are at the end of the
		file.  If not check the next char, and increment 
		theNum by 1
	*/
	{
		return letterCountAccum(theFile, theNum + 1);
	}
}

//letterCount: file->int
//Purpose: to see how many char's are in a given file
int letterCount(FILE* theFile)
//Template: theFile
{
/*Test 1
	letterCount(Test3.1)
		theFile = 123456789
		ans = 9
	return letterCountAccum(theFile,0);
*/

/*Test 2
	letterCount(Test3.3)
		theFile = This is a Test!
		ans = 15
	return letterCountAccum(theFile,0);
*/

/*Generalize Tests 1 and 2
	We know this is just a wrapper
	function and that we need to 
	pass the correct arguments to
	the Accum file.  theFile and 0
	is what we need to pass
*/

return letterCountAccum(theFile,0);
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

//Substitution: Ran out of Time!
}