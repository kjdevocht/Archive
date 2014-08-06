/*Kevin DeVocht Assignment 11 Exercise 1. Write a function called convertFCs that takes a list of Fahrenheit 
temperatures and produces a list of Celsius temperatures.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>

//listOfF is either
// a temp
//or an emptyLst
class listOfTemps {
public:
	 //Contract: show : ListOfTemps -> int
	virtual int show () = 0;
	//Contract: convertFCs; listOfTemps->listOfTemps
	//Purpose: To consume a list of Fahrenheit temps and return a list of Celsius temps
	virtual listOfTemps* convertFCs () = 0;
};

// a temp is
// new temp (first, rest)
//where first is a double
//and rest is a listOfTemps
class temp : public listOfTemps {
public:
	double first;
	listOfTemps* rest;
	
	temp (double first0, listOfTemps* rest0) {
		this->first = first0;
		this->rest = rest0;
	}
	
	  // Contract: show : temp -> int
	int show ( ) {
    // Template: this, this->first, this->rest, this->rest->show()
		printf ("new temp ( %f, ", this->first);
		this->rest->show();
		printf (" )");
		return 0;
	}
	
	//Contract: convertFCs; temp->listOfTemps
	//Purpose: To consume a Fahrenheit temp and return a Celsius temp
	listOfTemps* convertFCs () {
	//Template: this, this->first, this->rest
		/*Test 1
			spring->convertFCs()
				this->first = 55
				this->rest = 32
				ans = ((55-32)/1.8, (32-32)/1.8)
			return (12.7777, 0)
		*/
		
		/*Test 2
			summer->convertFCs()
				this->first = 95
				this->rest = 55, 32
				ans = ((95-32)/1.8, (55-32)/1.8, (32-32)/1.8)
			return (35, 12.7777, 0)
		*/
		
		/*Generalize Tests 1 and 2
			The math is really easy just make sure to return rest calling convetFCs
		*/
	return (new temp ((this->first-32)/1.8, this->rest->convertFCs()));
	}
};

//an emptyLst is a
//new emptyLst ()
//where there is nothing because it is empty
class emptyLst : public listOfTemps {
public:
	emptyLst() {
	}
	
	 // show : emptyLst -> int
	int show ( ) {
    // Template: this
		printf ( "new emptyLst ()" );
    return 0;
  }
	
	//Contract: convertFCs; emptyLst->listOfTemps
	//Purpose: To consume a Fahrenheit temp and return a Celsius temp
	listOfTemps* convertFCs () {
	//Template: this
	
	/*Test1
		none->convertFCs()
			this = !
			ans = !
		return (new emptyLst());
	*/
	return (new emptyLst ());
	}
};


int main () {
listOfTemps* none = new emptyLst();
listOfTemps* winterF = new temp(32.0, none);
listOfTemps* springF = new temp(55.0, winterF);
listOfTemps* summerF = new temp(95.0, springF);
listOfTemps* fallF = new temp(65.0, summerF);

listOfTemps* winterC = new temp(0.0, none);
listOfTemps* springC = new temp(12.7777, winterC);
listOfTemps* summerC = new temp(35.0, springC);
listOfTemps* fallC = new temp(18.33333, summerC);

  printf("The answer is \n  ");
  (fallF->convertFCs())->show();
  printf("\n, but should be\n  ");
  fallC->show();
  printf("\n");
  
   printf("The answer is \n  ");
  (summerF->convertFCs())->show();
  printf("\n, but should be\n  ");
  summerC->show();
  printf("\n");
  
   printf("The answer is \n  ");
  (springF->convertFCs())->show();
  printf("\n, but should be\n  ");
  springC->show();
  printf("\n");
  
   printf("The answer is \n  ");
  (none->convertFCs())->show();
  printf("\n, but should be\n  ");
  none->show();
  printf("\n");

  
  /*Substitution
	1. springF->convertFCs()
	
	2. new temp(55.0, winterF)->convertFCs()
	
	3. return (new temp ((this->first-32)/1.8, this->rest->convertFCs()));
	
	4. return (new temp ((new temp(55.0, winterF)->first-32)/1.8, new temp(55.0, winterF)->rest->convertFCs()));
	
	5. return (new temp ((55.0-32)/1.8, winterF->convertFCs()));
	
	6. return (new temp (23/1.8, new temp(32.0, none)->convertFCs()));
	
	7. return (new temp (12.7777777, (new temp ((this->first-32)/1.8, this->rest->convertFCs()))));
	
	8. return (new temp (12.7777777, (new temp ((new temp(32.0, none)->first-32)/1.8, new temp(32.0, none)->rest->convertFCs()))));
	
	9. return (new temp (12.7777777, (new temp ((32.0-32)/1.8, none->convertFCs()))));
	
	10. return (new temp (12.7777777, (new temp ((0.0)/1.8, new emptyLst()))));
	
	11. return (new temp (12.7777777, (new temp (0.0, new emptyLst()))));
  */
}