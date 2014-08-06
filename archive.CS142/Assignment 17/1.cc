/*Kevin DeVocht assignment 17 exercise 1. Write the function product, 
which takes a list of numbers and returns their product, in accumulator style.
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
	//product: LON->int
	//Purpose: to return the product of all numbers in a list
	virtual int product() = 0;
	
	//productHelper: LON int->int
	//Purpose: to return the product of the list based on the answer so far
	virtual int productHelper(int runningTotal) = 0;
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
	

	//product: LON->int
	//Purpose: to return the product of all numbers in a list
	int product()
	//Template: this
	{
	/*Test 1
		mt->product()
			this = !
			ans = 0
		return 0
	*/
	return 0;
	}
	
	//productHelper: LON int->int
	//Purpose: to return the product of the list based on the answer so far
	int productHelper(int runningTotal)
	//Template: this, runningTotal
	{
	/*Test 1
		mt->producthelper(500)
		this = !
		ans = 500
	return runningTotal
	*/
	return runningTotal;
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
 
 
 
	//productHelper: LON int ->int
	//Purpose: to return the product of the list based on the answer so far
	int productHelper(int runningTotal)
	//Template: this, this->first, this->rest, this->rest->productHelper(...), runningTotal
	{
	/*Test 1
		four->productHelper(5)
			this = 4, three
			this->first = 4
			this->rest = three(3,2,1,!)
			this->rest->producthelper(20) = 120
			20 = 4*5
			4*5 = this->first*runningTotal
			ans = 120
		return this->rest->productHelper(this->first * runningTotal);
	*/
	
	/*Test 2
		three->productHelper(4)
			this = 3, two
			this->first = 3
			this->rest = two(2,1,!)
			this->rest->producthelper(12) = 24
			12 = 3*4
			3*4 = this->first*runningTotal
			ans = 24
		return this->rest->productHelper(this->first * runningTotal);
	*/
	
	/*Generalize tests 1 and 2
		We see that each return statement is the same so we need to make sure that
		the work is getting done inside the ()'s not out side to make sure it is 
		in Accumulator style
	*/
	return this->rest->productHelper(this->first * runningTotal);
	}
 
	//product: LON->int
	//purpose: to consume a list of numbers and return their product
	int product()
	//Template: this, this->first, this->rest, this->rest->product()
	{
		/* Test 1
			one->product(1)
				this = 1, mt
				this->first = 1
				this->rest = mt
				this->rest->product() = 1
				ans =1
			return this->rest->SAF(this->first)
		*/
		
		/* Test 2
			one->product(1)
				this = 2, one
				this->first = 2
				this->rest = one
				this->rest->product() = 1
				ans =1
			return this->rest->producthelper(this->first)
		*/
		
		/*Generalize Tests 1 and 2
			After looking at test one we see that we needes SAF which turned into
			productHelper.  ProductHelper does the real work.  product is actually just
			a wrapper function
		*/
		return this->rest->productHelper(this->first);
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

printf("The answer is %d but should be %d\n", five->product(), 120);

printf("The answer is %d but should be %d\n", three->product(), 6);

printf("The answer is %d but should be %d\n", four->product(), 24);

printf("The answer is %d but should be %d\n", mt->product(), 0);

/*Substitution
	1. two->product()
	
	2. new oneLON(2, one)->product()
	
	3. new oneLON(2, one)->product()
	
	4. return this->rest->productHelper(this->first);
	
	5. return new oneLON(2, one)->rest->productHelper(new oneLON(2, one)->first);
	
	6. return one->productHelper(2);
	
	7. return new oneLON(1, mt)->productHelper(2);
	
	8. return this->rest->productHelper(this->first * runningTotal);
	
	9. return new oneLON(1, mt)->rest->productHelper(new oneLON(1, mt)->first * 2);
	
	10. return mt->productHelper(1* 2);
	
	11. return mt->productHelper(2);
	
	12. return new emptyLON()->productHelper(2);
	
	13. return runningTotal;
	
	14. return 2;
*/
}