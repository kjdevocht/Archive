/*Kevin DeVocht assignment 17 exercise 5. Generalize to10 to toB which takes 
an additional "base" argument that controls the numeric base of the digit sequence.
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
	
	//toB: LON int->int
	//Purpose:  To act as a wrapper for toBAccum and return the final answer
	virtual int toB(int base) = 0;
	
	//toBAccum: LON int int->int
	//Purpose to convert a LON into an int where the MSB is the first number of the int
	virtual int toBAccum(int base, int answerSoFar) = 0;
	
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
	
	//toB: LON int->int
	//Purpose:  To act as a wrapper for toBAccum and return the final answer
	int toB(int base)
	//Template: this, base
	{
	/*Test 1
		mt->toB(7)
			this = !
			base = 0
			ans = 0
		return 0
	*/
	return 0;
	}
	
	//toBAccum: LON int int->int
	//Purpose to convert a LON into an int where the MSB is the first number of the int
	int toBAccum(int base, int answerSoFar)
	//Template: this, answerSoFar, base
	{
	/*Test 1
		mt->toBAccum(5, 586)
			this = !
			answerSoFar = 586
			base = 5
			ans = 586
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
	
	
	//toB: LON int->int
	//Purpose:  To act as a wrapper for toBAccum and return the final answer
	int toB(int base)
	//Template: this, this->first, this->rest, this->rest->toB(...), base
	{
	/*Test 1
		five->toB(10)
			this = 5, four
			this->first = 5
			this->rest = four
			base = 10
			ans = 54321
		return this->toBAccum(10, 0)
	*/
	
	/*Test 2
		four->toB(5)
			this = 4, three
			this->first = 4
			this->rest = three
			base = 10
			ans = 586
		return this->toBAccum(5, 0)
	*/
	
	/*Generalize Tests 1 and 2
		We can see that this needs to be a wrapper function and that we need to pass a value
		to the function that does the real work.  In this case passing it a 0 makes the most sense
		we also need to pass the base as well
	*/
	return this->toBAccum(base, 0);
	}
	
	//toBAccum: LON int int->int
	//Purpose to convert a LON into an int where the MSB is the first number of the int
	int toBAccum(int base, int answerSoFar)
	//Template: this,  this-first, this->rest, this->rest->toBAccum(..), answerSoFar
	{
	/*Test 1
		two->toBAccum(0)
			this = 2, one
			this->first = 2
			this->rest = one
			answerSoFar = 0
			ans = 21
		return this->rest->toBAccum(answerSoFar *10 + this->first)
	*/
	
	/*Test 2
		two->toBAccum(543)
			this = 2, one
			this->first = 2
			this->rest = one
			answerSoFar = 0
			ans = 54321
		return this->rest->toBAccum(answerSoFar *10 + this->first)
	*/
	
	/*Generalize Tests 1 and 2
	We can see that we want the answer based off of the previous answer.  The only
	way to do this is to keep shifting everything to the left by * by 10 and then adding
	this->first
	*/
	return this->rest->toBAccum(base, answerSoFar *base + this->first);
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


printf("The answer is %d but should be %d\n", two->toB(2), 5);

printf("The answer is %d but should be %d\n", three->toB(8), 209);

printf("The answer is %d but should be %d\n", four->toB(5), 586);

printf("The answer is %d but should be %d\n", one->toB(16), 1);

printf("The answer is %d but should be %d\n", five->toB(10), 54321);

printf("The answer is %d but should be %d\n", mt->toB(5), 0);

/*Substitution
	1. two->toB(2)
	
	2. new oneLON(2, one)->toB(2)
	
	3. return this->toBAccum(base, 0);
	
	4. return new oneLON(2, one)->toBAccum(base, 0);
	
	5. return this->rest->toBAccum(base, answerSoFar *base + this->first);
	
	6. return new oneLON(2, one)->rest->toBAccum(base, answerSoFar *base + new oneLON(2, one)->first);
	
	7. return one->toBAccum(2, 0 *2 + 2);
	
	8. return new oneLON(1, mt)->toBAccum(2, 0 *2 + 2);
	
	9. return this->rest->toBAccum(base, answerSoFar *base + this->first);
	
	10. return new oneLON(1, mt)->rest->toBAccum(2, 2 *2 + new oneLON(1, mt)->first);
	
	11. return mt->toBAccum(2, 2 *2 + 1);
	
	12. return new emptyLON()->toBAccum(2, 5);
	
	13. return answerSoFar;
	
	14. return 5;	
*/
}