/* Kevin DeVocht Assignment 8 Exercise 5. Extend the zoo animal data definition so that animals endorse products, 
which are compound data described by a product name and a product company. Write a function called endorse that
consumes an animal and a product and produces an animal that endorses that product.
*/
#include <stdio.h>


//A product is..
//a new product (name, company)
//where 
//name is a string
//company is a string

class product {
public:
const char* name;
const char* company;

product (const char* name0, const char* company0) {
this->name = name0;
this->company = company0;
}
//Contract: endorse: product->product
//Purpose: To change from one product to another
product* endorse(product* p) {
//Template: this this->name this->company p p->name p->company

/*Test1
	endorse(Mitt)
		this = neutral
		this->name = "None"
		this->company = "None"
		p = Mitt
		p->name = "Mitt Romeny"
		p->company = "GOP"
		ans = new product(this->name = "Mitt Romeny", this->company "GOP")
	return new product(this->name = "Mitt Romeny", this->company "GOP")
*/

/*Test2
	endorse(Ron)
		this = neutral
		this->name = "None"
		this->company = "None"
		p = Ron
		p->name = "Ron Paul"
		p->company = "Who Knows"
		ans = new product(this->name = "Ron Paul", this->company "Who Knows")
	return new product(this->name = "Ron Paul", this->company "Who Knows")
*/
/*Generalize Tests 1 and 2
	In order to generalize for all products you must use generic terms this and p
*/
return new product(this->name = p->name, this->company = p->company);
}

 // Contract: show : product -> int
 //Purpose: to display the product by printing it
 int show ( ) {
 // Template: this, this->name, this->company
 
 /*Test1
	return printf("(%s,%s)", Mitt->name, Mitt->company);
		Mitt->name = "Mitt Romeny"
		Mitt->company = "GOP"
		ans = printf("Mitt Romeny, GOP")
	return printf("Mitt Romeny, GOP")
 */
 
  /*Test2
	return printf("(%s,%s)", Ron->name, Ron->company);
		Ron->name = "Ron Paul"
		Ron->company = "Who Knows"
		ans = printf("Ron Paul, Who Knows")
	return printf("Ron Paul, Who Knows")
 */
 
 /*Generalize Tests 1 and 2
	use a typical printf with two strings and than put generic this names and this companies to allow for all possibilities
 */
    return printf("(%s,%s)", this->name, this->company);
  }
};

// an animal is..
//a spider
//or elephant
//or monkey

class animal {
public:
//Contract: endorse: animal product -> animal
//Purpose: to make an animal endorse a product
virtual animal* endorse (product* p) = 0;


 // Contract: show : animal -> int
 // Purpose: to show the endorsement by printing it
  virtual int show ( ) = 0;

};



// A spider is..
// a new spider (num_legs, space, endorsement)
//where
//num_legs is an int
//space is as double
//endorsement is a product
class spider : public animal{
public:
int num_legs;
double space;
product* endorsement;

spider(int num_legs0, double space0, product* endorsement0) {
	this->num_legs = num_legs0;
	this->space = space0;
	this->endorsement = endorsement0;
}

  // Contract: endorse : spider product -> animal
  // Purpose: make spider endorse a product
  animal* endorse ( product* p ) {
 // Template: this, this->num_legs, this->space, this->endorsement p, p->name, p->company
 /*Test 1
	Charlette->endorse(Mitt)
		this = Charlette
		this->num_legs = 8
		this->space = 5.0
		this->endorsement = neutral
		p = Mitt
		p->name ="Mitt Romeny"
		p->company = "GOP"
		ans = new spider(8, 5.0, netral->endorse(Mitt))
	return new spider(8, 5.0, Mitt)
*/ 

 /*Test 2
	Shelob->endorse(Ron)
		this = Charlette
		this->num_legs = 8
		this->space = 5.0
		this->endorsement = Mr_POTUS
		p = Mitt
		p->name ="Ron Paul"
		p->company = "Who Knows"
		ans = new spider(8, 5.0, Mr_POTUS->endorse(Ron))
	return new spider(8, 5.0, Ron)
*/ 
/*Generalize Tests 1 and 2
	In order to generalize you must call the endorse function and use generic terms like this to account for all possiblilities
*/
return (new spider( this->num_legs, this->space, (this->endorsement)->endorse(p)));
	}
// Contract: show : apider -> int
// Purpose: to show the endorsement by printing it
  int show ( ) {
    // template: this, this->num_legs, this->space, this->endorsement
	
/*	Test 1
	Ron->endorsement->show();
		Ron->name = "Ron Paul"
		Ron->company = "Who Knows"
		ans = printf("Ron Paul, Who Knows")
	return printf("Spider Likes Ron Paul, Who Knows")
 */
 
 /*	Test 2
	Mr_POTUS->endorsement->show();
		Mr_POTUS->name = "Barack Obama"
		Ron->company = "DEM"
		ans = printf("Barack Obama, DEM")
	return printf("Spider Likes "Barack Obama, DEM")
 */
 
 //Generalize- you need to call the show fucntion from the product class
    printf("Spider Likes ");
    this->endorsement->show();
}
};



//An elephant is..
// a new elephant(space, endorsement)
//where
//space is a double
//endorsement is a product
class elephant : public animal {
public:
double space;
product* endorsement;

elephant (double space0, product* endorsement0) {
	this->space = space0;
	this->endorsement = endorsement0;
}


 // Contract: endorse : elephant product -> animal
  // Purpose: make elephant endorse a product
  animal* endorse ( product* p ) {
// Template: this, this->space, this->endorsement p, p->name, p->company
 /*Test 1
	Babar->endorse(Mitt)
		this = Babar
		this->space = 600.0
		this->endorsement = neutral
		p = Mitt
		p->name ="Mitt Romeny"
		p->company = "GOP"
		ans = new elephant (600.0, netral->endorse(Mitt))
	return new elephant(600.0, Mitt)
*/ 

 /*Test 2
	Dumbo->endorse(Mitt)
		this = Dumbo
		this->space = 300.0
		this->endorsement = Ron
		p = Mitt
		p->name ="Mitt Romeny"
		p->company = "GOP"
		ans = new spider(300.0, Ron->endorse(Mitt))
	return new spider(300.0, Mitt)
*/ 
/*Generalize Tests 1 and 2
	In order to generalize you must call the endorse function and use generic terms like this to account for all possiblilities
*/
    return (new elephant(this->space, (this->endorsement)->endorse(p)));
	}
	
// Contract: show : elephant -> int
// Purpose: to show the endorsement by printing it
  int show ( ) {
    // template: this, this->space, this->endorsement
	
	/*	Test 1
	Ron->endorsement->show();
		Ron->name = "Ron Paul"
		Ron->company = "Who Knows"
		ans = printf("Ron Paul, Who Knows")
	return printf("Elephant Likes Ron Paul, Who Knows")
 */
 
 /*	Test 2
	Mr_POTUS->endorsement->show();
		Mr_POTUS->name = "Barack Obama"
		Ron->company = "DEM"
		ans = printf("Barack Obama, DEM")
	return printf("Elephant Likes "Barack Obama, DEM")
 */
 
 //Generalize- you need to call the show fucntion from the product class
    printf("Elephant Likes ");
    this->endorsement->show();
}
};
	
	
	
//A monkey is...
// a new monkey(intelligence, space, )
//where
// intelligence is an int
//space is a double
//endorsement is a product*

class monkey : public animal{
public:
int intelligence;
double space;
product* endorsement;
monkey (int intelligence0, double space0, product* endorsement0) {
	this->intelligence = intelligence0;
	this->space = space0;
	this->endorsement = endorsement0;
}

  // Contract: endorse : monkey product -> animal
  // Purpose: make monkey endorse a product
  animal* endorse ( product* p ) {
  // Template: this, this->intelligence, this->space, this->endorsement p, p->name, p->company
  
 /*Test 1
	George->endorse(Ron)
		this = George
		this->intelligence = 1
		this->space = 25.0
		this->endorsement = neutral
		p = Ron
		p->name ="Ron Paul"
		p->company = "Who Knows"
		ans = new spider(1, 25.0, netral->endorse(Ron))
	return new spider(1, 25.0, Ron)
*/ 

 /*Test 2
	Abu->endorse(Mr_POTUS)
		this = Abu
		this->intelligence = 35
		this->space = 15.0
		this->endorsement = Mitt
		p = Mr_POTUS
		p->name ="Barack Obama"
		p->company = "DEM"
		ans = new monkey(35, 15.0, Mitt->endorse(Mr_POTUS))
	return new spider(35, 15.0, Mr_POTUS)
*/ 
/*Generalize Tests 1 and 2
	In order to generalize you must call the endorse function and use generic terms like this to account for all possiblilities
*/  

    return (new monkey( this->intelligence, this->space, (this->endorsement)->endorse(p)));
	}
	
	// Contract: show : monkey -> int
// Purpose: to show the endorsement by printing it
  int show ( ) {
    // template: this, this->intelligence, this->space, this->endorsement
		/*	Test 1
	Ron->endorsement->show();
		Ron->name = "Ron Paul"
		Ron->company = "Who Knows"
		ans = printf("Ron Paul, Who Knows")
	return printf("Monkey Likes Ron Paul, Who Knows")
 */
 
 /*	Test 2
	Mr_POTUS->endorsement->show();
		Mr_POTUS->name = "Barack Obama"
		Ron->company = "DEM"
		ans = printf("Barack Obama, DEM")
	return printf("Monkey Likes "Barack Obama, DEM")
 */
 
 //Generalize- you need to call the show fucntion from the product class
	
    printf("Monkey Likes ");
    this->endorsement->show();
}

};


//Contract: endorse: animal product ->animal
//Purpose: To make an animal endorse a product
animal* endorse (animal* a, product* p){
//Template: a p p->name p->company

 /*Test 1
	Abu->endorse(Mr_POTUS)
		a = Abu
		p = Mr_POTUS
		p->name ="Barack Obama"
		p->company = "DEM"
		ans = new monkey(35, 15.0, Mitt->endorse(Mr_POTUS))
	return new spider(35, 15.0, Mr_POTUS)
*/

 /*Test 2
	Charlette->endorse(Mitt)
		a = Charlette
		p = Mitt
		p->name ="Mitt Romeny"
		p->company = "GOP"
		ans = new spider(8, 5.0, netral->endorse(Mitt))
	return new spider(8, 5.0, Mitt)
*/ 

//Generalize - Because you must look deeper you must call auxillary functions depending on what kind of animal to complete the work
return a->endorse(p);
}


int main () {

product* Mitt = new product("Mitt Romeny", "GOP");
product* Mr_POTUS = new product("Barack Obama", "DEM");
product* Ron = new product("Ron Paul", "Who knows");
product* neutral = new product ("None", "None");

animal* Babar = new elephant(600.0, neutral);
animal* Dumbo = new elephant(300.0, Ron);
animal* Charlette = new spider(8, 5.0, neutral);
animal* Shelob = new spider(16, 75.0, Mr_POTUS);
animal* George = new monkey(1, 25.0, neutral);
animal* Abu = new monkey(35, 15.0, Mitt);


printf("The answer is \n");
 Charlette->endorse(Mitt)->show();
printf("\n, but it shoud be, %s, %s\n", "Mitt Romeny", "GOP");

printf("The answer is \n");
 Shelob->endorse(Ron)->show();
printf("\n, but it shoud be, %s, %s\n", "Ron Paul", "Who knows");

printf("The answer is \n");
 Babar->endorse(Mitt)->show();
printf("\n, but it shoud be, %s, %s\n", "Mitt Romeny", "GOP");

printf("The answer is \n");
 Dumbo->endorse(Mitt)->show();
printf("\n, but it shoud be, %s, %s\n", "Mitt Romeny", "GOP");

printf("The answer is \n");
 George->endorse(Ron)->show();
printf("\n, but it shoud be, %s, %s\n", "Ron Paul", "Who knows");

printf("The answer is \n");
 Abu->endorse(Mr_POTUS)->show();
printf("\n, but it shoud be, %s, %s\n", "Barack Obama", "DEM");
  

/*Substitution for spider

1. Charlette->endorse(Mitt)

2. return a->endorse(p)

3. return new spider(8, 5.0, neutral)->endorse(new product("Mitt Romeny", "GOP");)

4. return (new spider( this->num_legs, this->space, (this->endorsement)->endorse(p)));

5. return (new spider( 8, 5.0, (neutral)->endorse(new product("None", "GOP")));

6. return (new spider( 8, 5.0, (new product ("None", "None");)->endorse(new product("Mitt Romeny", "GOP")));

7. return (new spider( 8, 5.0, new product(this->name = p->name, this->company = p->company);

8.return (new spider( 8, 5.0,  new product(neutral->name = Mitt->name, neutral->company = Mitt->company);

9. return (new spider( 8, 5.0, new product("None" = "Mitt Romeny", "None" = "GOP");

10. return (new spider( 8, 5.0, new product("Mitt Romeny", "GOP");
*/

/*Substitution for elephant

1. Babar->endorse(Mitt)

2. return a->endorse(p)

3. return new elephant(600.0, neutral)->endorse(new product("Mitt Romeny", "GOP");)

4. return new elephant(this->space, (this->endorsement)->endorse(p)));

5. return new elephant(600.0 ,(neutral)->endorse(new product("None", "GOP")));

6. return new elephant(600.0 ,(new product ("None", "None");)->endorse(new product("Mitt Romeny", "GOP")));

7. return new elephant(600.0 ,new product(this->name = p->name, this->company = p->company);

8.return new elephant(600.0 , new product(neutral->name = Mitt->name, neutral->company = Mitt->company);

9. return new elephant(600.0 ,new product("None" = "Mitt Romeny", "None" = "GOP");

10. return new elephant(600.0 , new product("Mitt Romeny", "GOP");
*/


/*Substitution for monkey

1. Abu->endorse(Mitt)

2. return a->endorse(p)

3. return new monkey(35, 15.0, neutral)->endorse(new product("Mitt Romeny", "GOP");)

4. return new monkey(this->space, (this->endorsement)->endorse(p)));

5. return new monkey(35, 15.0,,(neutral)->endorse(new product("None", "GOP")));

6. return new monkey(35, 15.0,(new product ("None", "None");)->endorse(new product("Mitt Romeny", "GOP")));

7. return new monkey(35, 15.0, new product(this->name = p->name, this->company = p->company);

8.return new monkey(35, 15.0, new product(neutral->name = Mitt->name, neutral->company = Mitt->company);

9. return new monkey(35, 15.0, new product("None" = "Mitt Romeny", "None" = "GOP");

10. return new monkey(35, 15.0, new product("Mitt Romeny", "GOP");
*/


}