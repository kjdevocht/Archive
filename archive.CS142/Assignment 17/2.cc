/*Kevin DeVocht assignment 17 exercise 2. Write the function palindrome, 
which takes a list of numbers and returns a palindrome of that list. 
For example, on the list 1:2:3:! it should return 1:2:3:2:1:!.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>


//LON i either
// emptyLON
// oneLON
class LON
{
public:
	virtual int show () = 0;
	
	//isItEmpty: LON->bool
	//Purpose: To see if the rest of the list is empty
	virtual bool isItEmpty() = 0;
	
	//palindromeAccum: LON LON->LON
	//Purpose: to produce a palindrome using a given list of numbers
	virtual LON* palindromeAccum(LON* theAnswer) = 0;
	
	//palindrome: LON->LON
	//Purpose: is to act as a wrapper and return the final palindrome
	virtual LON* palindrome() = 0;
};

//An emptyLON is a
//  new emptyLON()
// where
class emptyLON : public LON
{
public:
	emptyLON ()
	{
	}

	int show () {
	printf("!");
	return 0;
	}
	
	//isItEmpty: LON->bool
	//Purpose: To see if the rest of the list is empty
	bool isItEmpty()
	//Template: this
	{
	/*Test 1
		mt->isItEmpty()
		this = !
		ans = true
	return true
	*/
	return true;
	}

	//palindromeAccum: LON LON->LON
	//Purpose: to produce a palindrome using a given list of numbers
	LON* palindromeAccum(LON* theAnswer)
	//Template: this, theAnswer
	{
	/*Test 1
		mt->palindromeAccum(120)
		this = !
		theAnswer = 120
		ans = 120
	return theAnswer
	*/
	return theAnswer;
	}
	
	//palindrome: LON->LON
	//Purpose: is to act as a wrapper and return the final palindrome
	LON* palindrome()
	//Template: this
	{
	/*Test 1
		mt->palindrome()
		this = !
		ans = !
	return new emptyLON()
	*/
	return new emptyLON();
	}
};


// A oneLON is a
//  new oneLON (first, rest)
// where
//  first is a int
//  rest is a LON
class oneLON : public LON 
{
public:
	int first;
	LON* rest;

	oneLON( int first0, LON* rest0 ) {
	this->first = first0;
	this->rest = rest0;
	}

	int show () {
	printf("%d:", this->first);
	this->rest->show();
	return 0;
	}


	//isItEmpty: LON->bool
	//Purpose: To see if the rest of the list is empty
	bool isItEmpty()
	//Template: this, this->first, this->rest. this->rest->isItEmpty
	{
	/*Test 1
		three->isItEmpty()
			this = 3, two
			this->first = 3
			this->rest = two
			ans = false
		return false
	*/
	
	/*Test 2
		two->isItEmpty()
			this = 2, one
			this->first = 2
			this->rest = one
			ans = false
		return false
	*/
	
	/*Generalize Tests 1 and 2
		we can see that the answer will always be false if the funciton isItEmpty is
		called on a string that's rest is not empty.  there is no need to test for
		anything.  By it's very nature the only way to get a true is if the rest is
		empty and at that point it would go to the emptyLON class
	*/
	return false;
	}

	//palindromeAccum: LON LON->LON
	//Purpose: to produce a palindrome using a given list of numbers
	LON* palindromeAccum(LON* theAnswer)
	//Template: this, this->first, this->rest, this->rest->palindromeAccum(...), theAnswer
	{
	if (this->rest->isItEmpty())
	/*Test 1
		two->palindromeAccum(2:!)
			this = 2, one
			this->first = 2
			this->rest = one(mt)
			theAnswer = 2:!
			this->rest->palindromeAccum(2:!) = 1:2:!
			ans = 2:!:2:!
		return new oneLON(this->first, this->rest->palindromeAccum(theAnswer))
	*/
	{
	return new oneLON(this->first, this->rest->palindromeAccum(theAnswer));
	}
	else
	{
	/*Test 2
		two->palindrome()
			this = 2, one
			this->first = 2
			this->rest = one
			this->rest->palindromeAccum() = 1:2:!
			ans = 2:1:2:!
		return new oneLON(this->first, this->rest->palindromeAccum(new oneLON(this->first, theAnswer)))
	*/
	
	/*Distinguish Tests 1 and 2
		It is kind of diffucult for me to show the proper answers in these to tests because the answer is 
		based off of the previous answer.  But we can see that we have to check and see if we have reach the
		end of the list.  if we have return this->first, theAnswer.  Other wise just keep moving donw the list
		and adding the number to the left of the list
	*/
	return new oneLON(this->first, this->rest->palindromeAccum(new oneLON(this->first, theAnswer)));
	}

	}

	//palindrome: LON->LON
	//Purpose: is to act as a wrapper and return the final palindrome
	LON* palindrome()
	//Template: this, this->first, this->rest, this->rest->palindrome()
	{
	/*Test 1
		four->palindrome()
			this = 4, three
			this->first = 4
			this->rest = three
			this->rest->palindrome = 3:2:1:2:3:!
			ans = 4:3:2:1:2:3:4:!
		return this->palindromeAccum(new emptyLON())
	*/
	
	
	/*Test 2
		three->palindrome()
			this = 3, two
			this->first = 3
			this->rest = two
			this->rest->palindrome = 2:1:2:!
			ans = 3:2:1:2:3:!
		return this->palindromeAccum(new emptyLON())
	*/
	
	/*Generalize Tests 1 and 2
		We see that we need to call palindromeAccum on the whole list at once
		and not try and peice things together at the end.  We can also see that
		this is a wrapper function and since the final answer is a list we need
		to send a list to palindromeAccum to get things started.  It makes the most
		sense to send it an emptyLON
	*/
	return this->palindromeAccum(new emptyLON());
	}
};





int main ()
{
LON* mt = new emptyLON();
LON* one = new oneLON(1, mt);
LON* two = new oneLON(2, one);
LON* three = new oneLON(3, two);
LON* four = new oneLON(4, three);
LON* five = new oneLON(5, four);
 
 
  printf("The answer is\n  ");
  five->palindrome()->show();
  printf("\nbut should be\n  ");
  printf("5:4:3:2:1:2:3:4:5:!");
  printf("\n");
  
  printf("The answer is\n  ");
  four->palindrome()->show();
  printf("\nbut should be\n  ");
  printf("4:3:2:1:2:3:4:!");
  printf("\n");
  
  
  printf("The answer is\n  ");
  mt->palindrome()->show();
  printf("\nbut should be\n  ");
  printf("!");
  printf("\n");
  
  
  printf("The answer is\n  ");
  two->palindrome()->show();
  printf("\nbut should be\n  ");
  printf("2:1:2:!");
  printf("\n");
  
 /* Substitution
	1. two->palindrome()
	
	2. new oneLON(2, one)->palindrome()
	
	3. return this->palindromeAccum(new emptyLON());
	
	4. return new oneLON(2, one)->palindromeAccum(new emptyLON());
	
	5. if (this->rest->isItEmpty()){return new oneLON(this->first, this->rest->palindromeAccum(theAnswer));}else{return new oneLON(this->first, this->rest->palindromeAccum(new oneLON(this->first, theAnswer)));}
	
	6. if (new oneLON(2, one)->rest->isItEmpty()){return new oneLON(new oneLON(2, one)->first, new oneLON(2, one)->rest->palindromeAccum(theAnswer));}else{return new oneLON(new oneLON(2, one)->first, new oneLON(2, one)->rest->palindromeAccum(new oneLON(new oneLON(2, one)->first, theAnswer)));}
	
	7. if (one->isItEmpty()){return new oneLON(2, one->palindromeAccum(new emptyLON()));}else{return new oneLON(2, one->palindromeAccum(new oneLON(2, new emptyLON())));}
	
	8. if (new oneLON(1, mt)->isItEmpty()){return new oneLON(2, new oneLON(1, mt)->palindromeAccum(new emptyLON()));}else{return new oneLON(2, new oneLON(1, mt)->palindromeAccum(new oneLON(2, new emptyLON())));}
	
	9. if (false){return new oneLON(2, new oneLON(1, mt)->palindromeAccum(new emptyLON()));}else{return new oneLON(2, new oneLON(1, mt)->palindromeAccum(new oneLON(2, new emptyLON())));}
	
	10. return new oneLON(2, new oneLON(1, mt)->palindromeAccum(new oneLON(2, new emptyLON())));
	
	11. return new oneLON(2, if (this->rest->isItEmpty()){return new oneLON(this->first, this->rest->palindromeAccum(theAnswer));}else{return new oneLON(this->first, this->rest->palindromeAccum(new oneLON(this->first, theAnswer)));});
	
	12. return new oneLON(2, if (new oneLON(1, mt)->rest->isItEmpty()){return new oneLON(new oneLON(1, mt)->first, new oneLON(1, mt)->rest->palindromeAccum(theAnswer));}else{return new oneLON(new oneLON(1, mt)->first, new oneLON(1, mt)->rest->palindromeAccum(new oneLON(new oneLON(1, mt)->first, 2,!)));});
	
	13. return new oneLON(2, if (mt->isItEmpty()){return new oneLON(1, mt->palindromeAccum(2,!));}else{return new oneLON(1, mt->palindromeAccum(new oneLON(1, 2,!)));});

	14. return new oneLON(2, if (true){return new oneLON(1, mt->palindromeAccum(2,!));}else{return new oneLON(1, mt->palindromeAccum(new oneLON(1, 2,!)));});

	15. return new oneLON(2,1, mt->palindromeAccum(2,!));
	
	16. return new oneLON(2,1, theAnswer);

	16. return new oneLON(2,1, 2,!);
*/
}