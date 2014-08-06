/*Kevin DeVocht Assignment 11 Exercise 2. Write a function eliminateExpensive that takes a list of 
toy prices and returns a list with all the elements of the given list, except those that cost more than $10.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>


//A listOfCost is either
//a cost
//or an emptyLst
class listOfCost {
	public:
		// Contract: show : ListOfCost-> int
		virtual int show () = 0;
		//Contract: eliminateExpensive: listOfCost->listOfCost
		//Purpose: to remove any toy price more than $10
		virtual listOfCost* eliminateExpensive () =0;
};




//a cost is a
// new cost(price, rest)
//where
// a price is a double
// and rest is a listOfCost
class cost : public listOfCost {
	public:
		double price;
		listOfCost* rest;
		
	cost(double price0, listOfCost* rest0){
		this->price = price0;
		this->rest = rest0;
	}
	
	
	  // Contract: show : cost -> int
	int show ( ) {
    // Template: this, this->first, this->rest, this->rest->show()
		printf ("new cost ( %f, ", this->price);
		this->rest->show();
		printf (" )");
		return 0;
	}
	
		//Contract: eliminateExpensive: cost->listOfCost
		//Purpose: to remove any toy price more than $10
		listOfCost* eliminateExpensive () {
		//Template: this, this->price, this->rest, this->rest->show(), this->rest->eliminateExpensive()
		if (this->price > 10) 

		/*Test 1
			silly_putty->eliminateExpensive()
				this->price = 0.5
				this->rest = 150.0, 6.75, 1.50
				ans = 0.5 < 100, 150.0 >100, 6.75 < 100, 1.50 <100
			return (0.5, 6.75, 1.50)
		*/
		{ return this->rest->eliminateExpensive();}
		
		else
		
		/*Test 2
			nintendo_NES->eliminateExpensive()
				this->price = 150.0
				this->rest = 6.75, 1.50
				ans = 150.0 >100, 6.75 < 100, 1.50 < 100
			return (6.75, 1.50)
		*/
		
		/*Generalize Tests 1 and 2
			As you can see this will require an if statement to see if a number is greater than 100 if it is don't put it in the new list
		*/
		{return new cost(this->price, this->rest->eliminateExpensive());}
		}
};

//an emptyLst is
// a new emptyLst()
//where there is nothing inside because it is empty
class emptyLst : public listOfCost {
	public:
	emptyLst(){
	}
	
	// show : emptyLst -> int
	int show ( ) {
    // Template: this
		printf ( "new emptyLst ()" );
    return 0;
	}
	
		//Contract: eliminateExpensive: emptyList->listOfCost
		//Purpose: to remove any toy price more than $10
		listOfCost* eliminateExpensive () {
		//Template: this
		
		/*Test 1
			emptyLst->eliminateExpensive()
			this = !
		return (new emptyList())
		*/
		return (new emptyLst());
		}
};
	
	

int main () {

listOfCost* none = new emptyLst();
listOfCost* hot_wheels = new cost(1.50, none);
listOfCost* polly_pocket = new cost(6.75, hot_wheels);
listOfCost* nintendo_NES = new cost(150.0, polly_pocket);
listOfCost* silly_putty = new cost(0.5, nintendo_NES);
listOfCost* lego_castle = new cost(100.0, silly_putty);

listOfCost* hot_wheels2 = new cost(1.50, none);
listOfCost* polly_pocket2 = new cost(6.75, hot_wheels);
listOfCost* silly_putty2 = new cost(0.5, polly_pocket);

  printf("The answer is \n  ");
  (lego_castle->eliminateExpensive())->show();
  printf("\n, but should be\n  ");
  silly_putty2->show();
  printf("\n");

  
  printf("The answer is \n  ");
  (polly_pocket->eliminateExpensive())->show();
  printf("\n, but should be\n  ");
  polly_pocket2->show();
  printf("\n");
  
  printf("The answer is \n  ");
  (none->eliminateExpensive())->show();
  printf("\n, but should be\n  ");
  none->show();
  printf("\n");  
  
  /*Substitution
	1. polly_pocket->eliminateExpensive()
	
	2. new cost(6.75, hot_wheels)->eliminateExpensive()
	
	3. if (this->price > 10) { return this->rest->eliminateExpensive();} else {return new cost(this->price, this->rest->eliminateExpensive());}
	
	4. if (new cost(6.75, hot_wheels)->price > 10) { return new cost(6.75, hot_wheels)->rest->eliminateExpensive();} else {return new cost(new cost(6.75, hot_wheels)->price, new cost(6.75, hot_wheels)->rest->eliminateExpensive());}
	
	5. if (6.75 > 10) { return hot_wheels->eliminateExpensive();} else {return new cost(6.75, hot_wheels->eliminateExpensive());}
	
	6. if (false) { return hot_wheels->eliminateExpensive();} else {return new cost(6.75, hot_wheels->eliminateExpensive());}
	
	7. return new cost(6.75, hot_wheels->eliminateExpensive());
	
	8. return new cost(6.75, new cost(1.50, none)->eliminateExpensive());
	
	9. return new cost(6.75, if (this->price > 10) { return this->rest->eliminateExpensive();} else {return new cost(this->price, this->rest->eliminateExpensive());}
	
	10. return new cost(6.75, if (new cost(1.50, none)->price > 10) { return new cost(1.50, none)->rest->eliminateExpensive();} else {return new cost(new cost(1.50, none)->price, new cost(1.50, none)->rest->eliminateExpensive());}
	
	11. return new cost(6.75, if (1.50 > 10) { return none->eliminateExpensive();} else {return new cost(1.50, none->eliminateExpensive());}
	
	12. return new cost(6.75, if (false) { return none->eliminateExpensive();} else {return new cost(1.50, none->eliminateExpensive());}
	
	13. return new cost(6.75, 1.50, none->eliminateExpensive());
	
	14. return new cost(6.75, 1.50, new emptyLst()->eliminateExpensive());
	
	15. return new cost(6.75, 1.50, new emptyLst());
*/
}