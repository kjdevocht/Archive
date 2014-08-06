
/*Kevin DeVocht assignment 19 exercise 4. Develop a data definition for 
Hangman words, which are lists of characters. Make a list of Hangman words 
and a function called randomWord which takes a list and returns a random 
word in it. Hint: To get random numbers in C, do this...

#include <stdio.h>

#include <stdlib.h>

#include <math.h>

#include <time.h>

 
int main () {

  // Put in the main() function

  srand ( time(NULL) );

  // If you don't do it, then rand() will always return

  // the same sequence of numbers


  // rand() returns a random number

  // rand() % N chops it to the range [0,N)

  printf("The answer is %d, but should be %d\n",

         rand() % 50,

         24 );
}
*/


#include <stdio.h>

#include <stdlib.h>

#include <math.h>

#include <time.h>




//A HangManWord
// is either
// a LOC
//or an emptyWord
class HangManWord
{
public:
	virtual int show() = 0;
};

//An emptyWord is
// a new emptyWord()
//where
class emptyWord : public HangManWord
{
	public:
	
	emptyWord() 
	{}
	
	int show()
	{
		printf("!");
		return 0;
	}
};


//A LOC is a
// new LOC(first, rest)
// where
// first is a char
// and rest is a HangManWord
class LOC : public HangManWord
{
	public:
		char first;
		HangManWord* rest;
		
		
	LOC(char first0, HangManWord* rest0)
	{
		this->first = first0;
		this->rest = rest0;
	}
	
	int show()
	{
	printf("%c:", this->first);
	this->rest->show();
	return 0;
	}
 };
 
 
//A LOW is either
// a oneWord
// or an emptyList
 class LOW
 {
	public:
	
	virtual int show () = 0;
	
	//isEmpty: LOW->bool
	//Purpose: to see if the rest of the list is empty
	virtual bool isEmpty() = 0;
	
	//howmany: LOW->int
	//Purpose: to see how many words are in a list
	virtual int howMany(int n) = 0;
	
	//randomWord: LOW-> Word
	//Purpose: to chose a random word from a list of words
	virtual HangManWord* randomWord() = 0;
	
	virtual LOW* SAF() = 0;

 };
 
 //An emptyList is
 // a new emptyList()
 //where
 class emptyList : public LOW
 {
	public:
		emptyList()
		{}
	
	int show()
	{
		printf("list!");
		return 0;
	}
	
	//isEmpty: LOW->bool
	//Purpose: to see if the rest of the list is empty
	bool isEmpty()
	{
		return false;
	}

	//howmany: LOW->int
	//Purpose: to see how many words are in a list
	int howMany(int n)
	{
		return 0;
	}
	
	//randomWord: LOW-> Word
	//Purpose: to chose a random word from a list of words
	HangManWord* randomWord()
	{
		return new emptyWord();
	}
	
	LOW* SAF()
	{
		return new emptyList();
	}

 };
 
 
 //A oneWord is a
 // new oneWord(first, rest)
 //where
 //first is a HangManWord
 // and rest is a LOW
 class oneWord : public LOW
 {
	public:
	HangManWord* first;
	LOW* rest;
	
	oneWord(HangManWord* first0, LOW* rest0)
	{
		this->first = first0;
		this->rest = rest0;
	}
	
	int show ()
	{
	printf("");
	return 0;
	}

	//isEmpty: LOW->bool
	//Purpose: to see if the rest of the list is empty
	bool isEmpty()
	{
		return true;
	}
	
	//howmany: LOW int ->int
	//Purpose: to see how many words are in a list
	int howMany(int n)
	{
		
		if (this->rest->isEmpty())
		{
		return n;
		}
		else
		{
		return this->rest->howMany(n + 1);
		}
	
	}
	
	
	LOW* SAF()
	{
		return this->rest;
	}
	
	//randomWord: LOW-> Word
	//Purpose: to chose a random word from a list of words
	HangManWord* randomWord()
	{
		int n = this->howMany(0);
		int wordNum = rand() % n;
		int theWord = 0;
		
		if (wordNum != theWord)
		{
			theWord = theWord+1;
			this->SAF();
		}
		else
		{
		return this->first;
		}
	}
 };
 

 


int main () 
{
HangManWord* COW = new LOC('C', new LOC('O', new LOC('W', new emptyWord())));
HangManWord* BOW = new LOC('B', new LOC('O', new LOC('W', new emptyWord())));
HangManWord* BOX = new LOC('B', new LOC('O', new LOC('X', new emptyWord())));

LOW* HangMan = new oneWord(BOX, new oneWord(BOW, new oneWord(COW, new emptyList())));

  srand ( time(NULL) );
  
  HangMan->randomWord()->show();

}