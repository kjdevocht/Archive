/*Kevin DeVocht Assignment 6 Exercise 5. Define a structure called zoo that contains two cats. 
Write feedLeft and feedRight that consume a zoo and return a zoo where the left (right) cat has been fed with the feed function.
*/

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

//Contract: Feed: Cat* -> Cat*
//Purpose: To make a little kitty happy
Cat* Feed (Cat*c){
//Template: c c->posn c->happiness

if (c->happiness <=90) {
return new_Cat(c->posn, c->happiness + 10);
}
/*Test 1
	Feed(Happy)
	posn = 5,0
	happiness = 85
	ans = 85+10
return 95
*/

/*Test 2
	Feed(Sad)
	posn = 5, 800
	happiness = 35
	ans = 35+10
return 45
*/

/*Generalize Tests 1 and 2
	In Order to generalize these tests we must use "c" to allow for all cats
*/
else {
/*Test 3
	Feed(Punch_Drunk)
		posn = 0, 52365
		happiness = 99
		ans = 99+10 >= 100
	return 100
*/
/*Generalize all Tests
	Despite what all those motivational books and seminars say, you can’t have more than 100% happiness so the if statement will make sure that you do not go over
*/
return new_Cat(c->posn, 100);
 }
 }

//Contract: feedLeft: Cat*->Cat*
//Purpose: To feed the cat on the left
zoo* feedLeft (zoo*z){
//Template: z z->left z->right

/*Test 1
	feedLeft(San_Diego)
		San_Diego->left = new_Cat(new_Point(1.0, 2.0), 15)
		San_Diego->right = new_Cat(new_Point(4.0, 7.0), 67)
		ans = San_Diego->left->happiness+10
	return new_zoo(new_Cat((1.0, 2.0), 25), new_Cat((4.0, 7.0), 67)
*/

/*Test 2
	feedLeft(Hoogle)
		Hoogle->left = new_Cat(new_Point(10.0, 15.0), 75)
		Hoogle->right = new_Cat(new_Point(5.0,6.0), 50)
		ans = Hoogle->left->happiness+10
	return new_zoo(new_Cat(new_Point(10.0, 15.0), 85), new_Cat(new_Point(5.0,6.0), 50))
*/

/*Generalize Tests 1 and 2
	You must call function feed on the left and have it look at z->left
*/
 return new_zoo(Feed(z->left), z->right);
 }
 
 //Contract: feedRight: zoo*->zoo*
 //Purpose: To feed the little kitty stuck in the right cage
 zoo* feedRight (zoo*z){
 //Template: z z->left z->right
 
/*Test 1
	feedRight(San_Diego)
		San_Diego->left = new_Cat(new_Point(1.0, 2.0), 15)
		San_Diego->right = new_Cat(new_Point(4.0, 7.0), 67)
		ans = San_Diego->right->happiness+10
	return new_zoo(new_Cat((1.0, 2.0), 15), new_Cat((4.0, 7.0), 77)
*/
 
/*Test 2
	feedRight(Hoogle)
		Hoogle->left = new_Cat(new_Point(10.0, 15.0), 75)
		Hoogle->right = new_Cat(new_Point(5.0,6.0), 50)
		ans = Hoogle->right->happiness+10
	return new_zoo(new_Cat(new_Point(10.0, 15.0), 75), new_Cat(new_Point(5.0,6.0), 60))
*/


/*Generalize Tests 1 and 2
	Yup you guessed it.  It is just like feedLeft.  Except on the right. You must call function feed and have it look at z->right
*/
return new_zoo(z->left, Feed(z->right));
}
int main () {
zoo* Hoogle = new_zoo(new_Cat(new_Point(10.0, 15.0), 75), new_Cat(new_Point(5.0, 6.0), 50));
zoo* San_Diego = new_zoo(new_Cat(new_Point(1.0, 2.0), 15), new_Cat(new_Point(4.0,7.0), 67));
zoo* Life_Is = new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77));

zoo*yek = feedLeft(Hoogle);
zoo*dough = feedLeft(San_Diego);;
zoo*say = feedLeft(Life_Is);

zoo*chahar = feedRight(Hoogle);
zoo*panj = feedRight(San_Diego);
zoo*shaysh = feedRight(Life_Is);


printf("The answer is %d but it should be %d\n", yek->left->happiness, 85);
printf("The answer is %d but it should be %d\n", dough->left->happiness, 25);
printf("The answer is %d but it should be %d\n", say->left->happiness, 57);

printf("The answer is %d but it should be %d\n", chahar->right->happiness, 60);
printf("The answer is %d but it should be %d\n", panj->right->happiness, 77);
printf("The answer is %d but it should be %d\n", shaysh->right->happiness, 87);
/*Substitution for feedLeft
	feedLeft(Hoogle)
	
	feedLeft(new_zoo(new_Cat(new_Point(10.0, 15.0), 75), new_Cat(new_Point(5.0, 6.0), 50)))

	new_zoo(Feed(z->left), z->right)
	
	new_zoo(Feed(new_Cat(new_Point(10.0, 15.0), 75), new_Cat(new_Point(5.0, 6.0), 50)))

	new_zoo(if (c->happiness <=90) {return new_Cat(c->posn, c->happiness + 10); else {return new_Cat(c->posn, 100);}, new_Cat(new_Point(5.0, 6.0), 50))
	
	new_zoo(if (75 <=90) {return new_Cat(new_Point(10.0,15.0), 75 + 10); else {return new_Cat(new_Point(10.0, 15.0), 100);}, new_Cat(new_Point(5.0, 6.0), 50))
	
	new_zoo(if true {return new_Cat(new_Point(10.0,15.0), 85); else {return new_Cat(new_Point(10.0, 15.0), 100);}, new_Cat(new_Point(5.0, 6.0), 50))
	
	new_zoo(new_Cat(new_Point(10.0,15.0), 85), new_Cat(new_Point(5.0, 6.0), 50))
*/	
	
	
/* Substitution for feedRight
		feedRight(Hoogle)
		
		feedLeft(new_zoo(new_Cat(new_Point(10.0, 15.0), 75), new_Cat(new_Point(5.0, 6.0), 50)))
	
		new_zoo(z->left, Feed(z->right))
		
		new_zoo(new_Cat(new_Point(10.0, 15.0), 75), Feed(new_Cat(new_Point(5.0, 6.0), 50)))
		
		new_zoo(new_Cat(new_Point(10.0, 15.0), 75), if (c->happiness <=90) {return new_Cat(c->posn, c->happiness + 10); else {return new_Cat(c->posn, 100);})
		
		new_zoo(new_Cat(new_Point(10.0, 15.0), 75) , if (50 <=90) {return new_Cat(new_Point(5.0,6.0), 50 + 10); else {return new_Cat(new_Point(5.0, 6.0), 100);})
		
		new_zoo(new_Cat(new_Point(10.0, 15.0), 75) , if (true) {return new_Cat(new_Point(5.0,6.0), 60); else {return new_Cat(new_Point(5.0, 6.0), 100);})
		
		new_zoo(new_Cat(new_Point(10.0, 15.0), 75) , new_Cat(new_Point(5.0,6.0), 60))
*/
}