//Kevin DeVocht Assignment 6 Exercise 6. Write newspaperLeft and newspaperRight


#include <stdio.h>

// A Point is a 
// new_Point (x, y)
// where 
// - x is a double
// - y is a double
typedef struct Point;

typedef struct Point {
  double x;
  double y;
};

// Contract: new_Point : double double -> Point*
Point* new_Point(double x0, double y0) {
  Point* p = new Point();
  p->x = x0;
  p->y = y0;
  return p;
  }
  //I copied this code from class code


//A Cat is a...
//new cat(posn, happiness)
//where
//posn is a Point*
// happiness is a double

typedef struct Cat;

typedef struct Cat {
int happiness;
Point* posn;
};


Cat* new_Cat(Point* posn0, int happiness0) {
Cat* c =new Cat ();
	c->posn = posn0;
	c->happiness = happiness0;
return c;
}
//A zoo is a....
//new_zoo(left, right)
//where
// left is a Cat*
//right is a Cat*

typedef struct zoo;

typedef struct zoo {
Cat* left;
Cat* right;
};


zoo* new_zoo(Cat* left0, Cat* right0) {
zoo* z = new zoo ();
	z->left = left0;
	z->right = right0;
return z;
}


//Contract: newspaper: Cat*->Cat*
/*Purpose: To beat a cat with a newpaper and reduce it's happiness 
(Don't let PETA know about this exercise or they will sue you for abusing a virtual animal)
*/
Cat* newspaper (Cat*c){
//Template: c c->posn c->happiness
/*Test 1
	newspaper(Happy)
		posn = 5,0
		happiness = 85
		ans = 85-15
	return 70
*/

/*Test 2
	newspaper(Sad)
		posn = 5, 800
		happiness = 35
		ans =35-15
	return 20
*/

/*Generalize Tests 1 and 2
	In order to generalize these two tests you must use c->happiness to account for all cats
*/
return new_Cat(c->posn, c->happiness -15);
}

//Contract: newspaperLeft: Cat*->Cat*
//Purpose: To abuse the cat on the left
zoo* newspaperLeft (zoo*z){
//Template: z z->left z->right

/*Test 1
	newspaperLeft(San_Diego)
		San_Diego->left = new_Cat(new_Point(1.0, 2.0), 15)
		San_Diego->right = new_Cat(new_Point(4.0, 7.0), 67)
		ans = San_Diego->left->happiness+10
	return new_zoo(new_Cat((1.0, 2.0), 0), new_Cat((4.0, 7.0), 67)
*/

/*Test 2
	newspaperLeft(Hoogle)
		Hoogle->left = new_Cat(new_Point(10.0, 15.0), 75)
		Hoogle->right = new_Cat(new_Point(5.0,6.0), 50)
		ans = Hoogle->left->happiness - 15
	return new_zoo(new_Cat(new_Point(10.0, 15.0), 60), new_Cat(new_Point(5.0,6.0), 50))
*/

/*Generalize Tests 1 and 2
	You must call function newspaper on the left and have it look at z->left
*/
 return new_zoo(newspaper(z->left), z->right);
}



 //Contract: newspaperRight: zoo*->zoo*
 //Purpose: To beat the little kitty stuck in the right cage  You really better not let PETA see this.  not only is the cat stuck in a cage but you are beating it
 zoo* newspaperRight (zoo*z){
 //Template: z z->left z->right
 
/*Test 1
	newspaperRight(San_Diego)
		San_Diego->left = new_Cat(new_Point(1.0, 2.0), 15)
		San_Diego->right = new_Cat(new_Point(4.0, 7.0), 67)
		ans = San_Diego->right->happiness-15
	return new_zoo(new_Cat((1.0, 2.0), 15), new_Cat((4.0, 7.0), 52)
*/
 
/*Test 2
	newspaperRight(Hoogle)
		Hoogle->left = new_Cat(new_Point(10.0, 15.0), 75)
		Hoogle->right = new_Cat(new_Point(5.0,6.0), 50)
		ans = Hoogle->right->happiness-15
	return new_zoo(new_Cat(new_Point(10.0, 15.0), 75), new_Cat(new_Point(5.0,6.0), 35))
*/


/*Generalize Tests 1 and 2
	You must call function newspaper and have it look at z->right
*/
return new_zoo(z->left, newspaper(z->right));
}

int main () {
zoo* Hoogle = new_zoo(new_Cat(new_Point(10.0, 15.0), 75), new_Cat(new_Point(5.0, 6.0), 50));
zoo* San_Diego = new_zoo(new_Cat(new_Point(1.0, 2.0), 15), new_Cat(new_Point(4.0,7.0), 67));
zoo* Life_Is = new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77));

zoo*yek = newspaperLeft(Hoogle);
zoo*dough = newspaperLeft(San_Diego);;
zoo*say = newspaperLeft(Life_Is);

zoo*chahar = newspaperRight(Hoogle);
zoo*panj = newspaperRight(San_Diego);
zoo*shaysh = newspaperRight(Life_Is);


printf("The answer is %d but it should be %d\n", yek->left->happiness, 60);
printf("The answer is %d but it should be %d\n", dough->left->happiness, 0);
printf("The answer is %d but it should be %d\n", say->left->happiness, 32);

printf("The answer is %d but it should be %d\n", chahar->right->happiness, 35);
printf("The answer is %d but it should be %d\n", panj->right->happiness, 52);
printf("The answer is %d but it should be %d\n", shaysh->right->happiness, 62);
/*Substitution for newsPaperLeft
	newspaperLeft(Life_Is)
	
	newspaperLeft(new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77)))
	
	new_zoo(newspaper(z->left), z->right)
	
	new_zoo(newspaper(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77)))
	
	new_zoo(new_Cat(c->posn, c->happiness -15), new_Cat(new_Point(7.0, 7.0), 77)))
	
	new_zoo(new_Cat(new_Point(0.0, 0.0), 47 -15), new_Cat(new_Point(7.0, 7.0), 77))
	
	new_zoo(new_Cat(new_Point(0.0, 0.0), 47 -15), new_Cat(new_Point(7.0, 7.0), 77))
	
	new_zoo(new_Cat(new_Point(0.0, 0.0), 32), new_Cat(new_Point(7.0, 7.0), 77))
*/	
	
	
/* Substitution for newsPaperRight
	newspaperRight(Life_Is)
	
	newspaperRight(new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77)))

	new_zoo(z->left, newspaper(z->right))
	
	new_zoo(new_Cat(new_Point(0.0, 0.0), 47), newspaper(new_Cat(new_Point(7.0, 7.0), 77)))
	
	new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(c->posn, c->happiness -15))
	
	new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat((7.0, 7.0), 77 -15))
	
	new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat((7.0, 7.0), 62))
*/	
}