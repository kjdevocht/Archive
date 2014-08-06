/*Kevin DeVocht assignment 17 exercise 6. Write the function howMany, 
which takes a list of strings and returns how many letters are in all the 
strings combined, in accumulator style
*/


#include <stdio.h>
#include <math.h>
#include <string.h>


//LOS is either
//mtLOS
//oneLOS
class LOS
{
public:

	//howMany: LOS->int
	//Purpose: to find out how many letters are in a list of strings
	virtual int howMany() = 0;
	
	//howManyAccum: LOS int->int
	//Purpose: to do the real work for howMany
	virtual int howManyAccum(int answerSoFar) = 0;
};

//A mtLOS is a
//new mtLOS()
// where
class mtLOS : public LOS
{
public:
	mtLOS ()
	{
	}
	
	//howMany: LOS->int
	//Purpose: to find out how many letters are in a list of strings
	int howMany() 
	//Template: this
	{
	/*Test 1
		mt->howMany()
			this = !
			ans = 0
		return 0
	*/
	return 0;
	}
	
	//howManyAccum: LOS int->int
	//Purpose: to do the real work for howMany
	int howManyAccum(int answerSoFar)
	//Template: this, answerSoFar
	{
	/*Test 1
		mt->howManyAccum(12)
			this = !
			answerSoFar = 12
			ans = 12
		return answerSoFar
	*/
	return answerSoFar;
	}
};


//A oneLOS is a
// new oneLOS(first, rest)
//where
// first is a string
// rest is a LOS
class oneLOS : public LOS
{
public:
	const char* first;
	LOS* rest;
	
	oneLOS(const char* first0, LOS* rest0)
	{
    this->first = first0;
    this->rest = rest0;
	}
	
	//howMany: LOS->int
	//Purpose: to find out how many letters are in a list of strings
	int howMany()
	//Template: this, this->first, this->rest, this->rest->howMany()
	{
	/*Test 1
		string->howmany()
			this = "string", mt
			this->first = "string"
			this->rest = mt
			this->rest->howMany = 0
			ans = 6
		return this->howManyAccum(0)
	*/
	
	/*Test 2
		cheese->howmany()
			this = "cheese", string
			this->first = "cheese"
			this->rest = string
			this->rest->howMany = 6
			ans = 12
		return this->howManyAccum(0)
	*/
	
	/*Generalize Tests 1 and 2
		We know that this is a wrapper function and the value to pass that
		makes the most sense is 0
	*/
	
	return this->howManyAccum(0);
	}
	
	//howManyAccum: LOS int->int
	//Purpose: to do the real work for howMany
	int howManyAccum(int answerSoFar)
	//Template: this, this->first, this->rest, this->rest->howManyAccum(...), answerSoFar
	{
	/*Test 1
		string->howManyAccum(0)
			this = "string", mt
			this->first = "string"
			this->rest = mt
			this->rest->howManyAccum = 0
			answerSoFar = 0
			ans = 6
		return this->rest->howManyAccum(answerSoFar + strlen(this->first))
	*/
	
	/*Test 2
		cheese->howManyAccum(6)
			this = "cheese", string
			this->first = "cheese"
			this->rest = string
			this->rest->howManyAccum(6) = 12
			ans = 12
		return this->rest->howManyAccum(answerSoFar + strlen(this->first))
	*/
	
	/*Generalize Tests 1 and 2
		We know that the work needs to be based off of the answer so far
		so we need to pass the answer so far plus this->first
	*/
	return this->rest->howManyAccum(answerSoFar + strlen(this->first));
	}
};

int main ()
{
LOS* mt = new mtLOS();
LOS* string = new oneLOS("string", mt);
LOS* cheese = new oneLOS("cheese", string);
LOS* reminds = new oneLOS("reminds", cheese);
LOS* me = new oneLOS("me", reminds);
LOS* of = new oneLOS("of", me);
LOS* when = new oneLOS("when", of);
LOS* I = new oneLOS("I", when);
LOS* sneeze = new oneLOS("sneeze", I);


printf("The answer is %d but should be %d\n", mt->howMany(), 0);

printf("The answer is %d but should be %d\n", string->howMany(), 6);

printf("The answer is %d but should be %d\n", cheese->howMany(), 12);

printf("The answer is %d but should be %d\n", sneeze->howMany(), 34);

/* Substitution
	1. string->howMany()
	
	2. new oneLOS("string", mt)->howMany()
	
	3. return this->howManyAccum(0);
	
	4. return new oneLOS("string", mt)->howManyAccum(0);
	
	5. return this->rest->howManyAccum(answerSoFar + strlen(this->first));
	
	6. return new oneLOS("string", mt)->rest->howManyAccum(answerSoFar + strlen(new oneLOS("string", mt)->first));
	
	7. return mt->howManyAccum(0 + strlen("string");
	
	8. return new mtLOS()->howManyAccum(0 + 6);
	
	9. return new mtLOS()->howManyAccum(0 + 6);
	
	10. return answerSoFar;
	
	10. return 6;
*/	
}