/*Kevin DeVocht assignment 17 exercise 3. Write the function to10, 
which takes a list of digits and returns the corresponding number, 
where the first number is the most significant digit. Thus, 1:2:3:! 
should return 123. (This is unlike the convert functions we’ve seen before.)
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
	
	//to10: LON->int
	//Purpose:  To act as a wrapper for to10Accum and return the final answer
	virtual int to10() = 0;
	
	//to10Accum: LON int >int
	//Purpose to convert a LON into an int where the MSB is the first number of the int
	virtual int to10Accum(int answerSoFar) = 0;
	
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
	
	//to10: LON->int
	//Purpose:  To act as a wrapper for to10Accum and return the final answer
	int to10()
	//Template: this
	{
	/*Test 1
		mt->to10
			this = !
			ans = 0
		return 0
	*/
	return 0;
	}
	
	//to10Accum: LON int >int
	//Purpose to convert a LON into an int where the MSB is the first number of the int
	int to10Accum(int answerSoFar)
	//Template: this, answerSoFar
	{
	/*Test 1
		mt->to10Accum(321)
			this = !
			answerSoFar = 321
			ans = 321
		return answerSoFar
	*/
	return answerSoFar;
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
	
	
	//to10: LON->int
	//Purpose:  To act as a wrapper for to10Accum and return the final answer
	int to10()
	//Template: this, this->first, this->rest, this->rest->to10
	{
	/*Test 1
		five->to10()
			this = 5, four
			this->first = 5
			this->rest = four
			ans = 54321
		return this->to10Accum(0)
	*/
	
	/*Test 2
		four->to10()
			this = 4, three
			this->first = 4
			this->rest = three
			ans = 4321
		return this->to10Accum(0)
	*/
	
	/*Generalize Tests 1 and 2
		We can see that this needs to be a wrapper function and that we need to pass a value
		to the function that does the real work.  In this case passing it a 0 makes the most sense
	*/
	return this->to10Accum(0);
	}
	
	//to10Accum: LON int >int
	//Purpose to convert a LON into an int where the MSB is the first number of the int
	int to10Accum(int answerSoFar)
	//Template: this,  this-first, this->rest, this->rest->to10Accum(..), answerSoFar
	{
	/*Test 1
		two->to10Accum(0)
			this = 2, one
			this->first = 2
			this->rest = one
			answerSoFar = 0
			ans = 21
		return this->rest->to10Accum(answerSoFar *10 + this->first)
	*/
	
	/*Test 2
		two->to10Accum(543)
			this = 2, one
			this->first = 2
			this->rest = one
			answerSoFar = 0
			ans = 54321
		return this->rest->to10Accum(answerSoFar *10 + this->first)
	*/
	
	/*Generalize Tests 1 and 2
	We can see that we want the answer based off of the previous answer.  The only
	way to do this is to keep shifting everything to the left by * by 10 and then adding
	this->first
	*/
	return this->rest->to10Accum(answerSoFar *10 + this->first);
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


printf("The answer is %d but should be %d\n", two->to10(), 21);

printf("The answer is %d but should be %d\n", three->to10(), 321);

printf("The answer is %d but should be %d\n", five->to10(), 54321);

printf("The answer is %d but should be %d\n", mt->to10(), 0);

/*Substitution
	1. two->to10()
	
	2. new oneLON(2, one)->to10()
	
	3. return this->to10Accum(0);
	
	4. return new oneLON(2, one)->to10Accum(0);
	
	5. return this->rest->to10Accum(answerSoFar *10 + this->first);
	
	6. return new oneLON(2, one)->rest->to10Accum(0 *10 + new oneLON(2, one)->first);
	
	7. return one->to10Accum(0 *10 + 2);
	
	8. return new oneLON(1, mt)->to10Accum(2);
	
	9. return this->rest->to10Accum(answerSoFar *10 + this->first);
	
	10. return new oneLON(1, mt)->rest->to10Accum(2 *10 + new oneLON(1, mt)->first);
	
	11. return mt->to10Accum(2 *10 + 1);
	
	12. return mt->to10Accum(21);
	
	13. return answerSoFar;
	
	14. return 21;
*/
}