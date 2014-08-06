/*Kevin DeVocht Assignment 6 Exercise 4. Redo day 6, exercise 5 (about feeding cats in zoos) with methods
*/

#include <stdio.h>

// A Point is a 
// new Point (x, y)
// where 
// - x is a double
// - y is a double
class Point {

public:
  double x;
  double y;



Point(double x0, double y0) {
  this->x = x0;
  this->y = y0;
  }
};


//A Cat is a...
//new cat(posn, happiness)
//where
//posn is a Point*
// happiness is a double

class Cat {

public:
int happiness;
Point* posn;



Cat(Point* posn0, int happiness0) {
	this->posn = posn0;
	this->happiness = happiness0;
}

//Contract: Feed: Cat* -> Cat*
//Purpose: To make a little kitty happy
Cat* Feed (){
//Template: this this->posn this->happiness

if (this->happiness <=90) {
return new Cat(this->posn, this->happiness + 10);
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
	In Order to generalize these tests we must use "this" to allow for all cats
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
return new Cat(this->posn, 100);
 }
 }
};
//A zoo is a....
//new zoo(left, right)
//where
// left is a Cat*
//right is a Cat*

class zoo{

public:
Cat* left;
Cat* right;



zoo(Cat* left0, Cat* right0) {

	this->left = left0;
	this->right = right0;
}



//Contract: feedLeft: zoo*->zoo*
//Purpose: To feed the cat on the left
zoo* feedLeft (){
//Template: this this->left this->right

/*Test 1
	San_Diego->feedLeft()
		San_Diego->left = new Cat(new Point(1.0, 2.0), 15)
		San_Diego->right = new Cat(new Point(4.0, 7.0), 67)
		ans = San_Diego->left->happiness+10
	return new zoo(new Cat((1.0, 2.0), 25), new Cat((4.0, 7.0), 67)
*/

/*Test 2
	Hoogle->feedLeft()
		Hoogle->left = new Cat(new Point(10.0, 15.0), 75)
		Hoogle->right = new Cat(new Point(5.0,6.0), 50)
		ans = Hoogle->left->happiness+10
	return new zoo(new Cat(new Point(10.0, 15.0), 85), new Cat(new Point(5.0,6.0), 50))
*/

/*Generalize Tests 1 and 2
	You must call function feed on the left and have it look at this->left
*/
 return (new zoo(this->left->Feed(), this->right));
 }
 
 //Contract: feedRight: zoo*->zoo*
 //Purpose: To feed the little kitty stuck in the right cage
 zoo* feedRight (){
 //Template: this this->left this->right
 
/*Test 1
	San_Diego->feedRight()
		San_Diego->left = new Cat(new Point(1.0, 2.0), 15)
		San_Diego->right = new Cat(new Point(4.0, 7.0), 67)
		ans = San_Diego->right->happiness+10
	return new zoo(new Cat((1.0, 2.0), 15), new Cat((4.0, 7.0), 77)
*/
 
/*Test 2
	Hoogle->feedRight()
		Hoogle->left = new Cat(new Point(10.0, 15.0), 75)
		Hoogle->right = new Cat(new Point(5.0,6.0), 50)
		ans = Hoogle->right->happiness+10
	return new zoo(new Cat(new Point(10.0, 15.0), 75), new Cat(new Point(5.0,6.0), 60))
*/


/*Generalize Tests 1 and 2
	Yup you guessed it.  It is just like feedLeft.  Except on the right. You must call function feed and have it look at this->right
*/
return (new zoo(this->left, this->right->Feed()));
}
};
int main () {
zoo* Hoogle = new zoo(new Cat(new Point(10.0, 15.0), 75), new Cat(new Point(5.0, 6.0), 50));
zoo* San_Diego = new zoo(new Cat(new Point(1.0, 2.0), 15), new Cat(new Point(4.0,7.0), 67));
zoo* Life_Is = new zoo(new Cat(new Point(0.0, 0.0), 47), new Cat(new Point(7.0, 7.0), 77));

zoo*yek = Hoogle->feedLeft();
zoo*dough = San_Diego->feedLeft();;
zoo*say = Life_Is->feedLeft();

zoo*chahar = Hoogle->feedRight();
zoo*panj = San_Diego->feedRight();
zoo*shaysh = Life_Is->feedRight();


printf("The answer is %d but it should be %d\n", yek->left->happiness, 85);
printf("The answer is %d but it should be %d\n", dough->left->happiness, 25);
printf("The answer is %d but it should be %d\n", say->left->happiness, 57);

printf("The answer is %d but it should be %d\n", chahar->right->happiness, 60);
printf("The answer is %d but it should be %d\n", panj->right->happiness, 77);
printf("The answer is %d but it should be %d\n", shaysh->right->happiness, 87);
/*Substitution for feedLeft
zoo*yek = Hoogle->feedLeft();

Hoogle = new zoo(new Cat(new Point(10.0, 15.0), 75), new Cat(new Point(5.0, 6.0), 50))->return (new zoo(this->left->Feed(), this->right));

new zoo(this->new Cat(new Point(10.0, 15.0), 75->Feed(), this->new Cat(new Point(5.0, 6.0), 50)));

new zoo(this->new Cat(new Point(10.0, 15.0), 75->if (this->happiness <=90) {return new Cat(this->posn, this->happiness + 10);}else {return new Cat(this->posn, 100), this->new Cat(new Point(5.0, 6.0), 50)));}

new zoo(this->new Cat(new Point(10.0, 15.0), 75->if (this->75 <=90) {return new Cat(this->(10.0, 15.0), this->75 + 10);}else {return new Cat(this->(10.0, 15.0), 100), this->new Cat(new Point(5.0, 6.0), 50)));}

new zoo(this->new Cat(new Point(10.0, 15.0), 75->if (true) {return new Cat(this->(10.0, 15.0), this->75 + 10);}else {return new Cat(this->(10.0, 15.0), 100), this->new Cat(new Point(5.0, 6.0), 50)));}

new zoo(this->new Cat(new Point(10.0, 15.0), 75->if (true) return new Cat(this->(10.0, 15.0), 85), this->new Cat(new Point(5.0, 6.0), 50)));

new zoo(this->new Cat(this->(10.0, 15.0), 85)(), this->new Cat(new Point(5.0, 6.0), 50));

*/	
	
	
/* Substitution for feedRight
zoo*chahar = Hoogle->feedRight();

Hoogle = new zoo(new Cat(new Point(10.0, 15.0), 75), new Cat(new Point(5.0, 6.0), 50))->feedRight();	

new zoo(new Cat(new Point(10.0, 15.0), 75), new Cat(new Point(5.0, 6.0), 50))->return (new zoo(this->left, this->right->Feed()));	

new zoo(new Cat(new Point(10.0, 15.0), 75), new Cat(new Point(5.0, 6.0), 50))->return (new zoo(this->(new Cat(new Point(10.0, 15.0), 75), this->new Cat(new Point(5.0, 6.0), 50)->Feed()));	

new zoo(this->(new Cat(new Point(10.0, 15.0), 75), this->new Cat(new Point(5.0, 6.0), 50)->if (this->happiness <=90) {return new Cat(this->posn, this->happiness + 10);}else {return new Cat(this->posn, 100)));

new zoo(this->(new Cat(new Point(10.0, 15.0), 75), this->new Cat(new Point(5.0, 6.0), 50)->if (this->50 <=90) {return new Cat(this->(5.0, 6.0), this->50 + 10);}else {return new Cat(this->(5.0, 6.0), 100)));

new zoo(this->(new Cat(new Point(10.0, 15.0), 75), this->new Cat(new Point(5.0, 6.0), 50)->if (true) {return new Cat(this->(5.0, 6.0), this->50 + 10);}else {return new Cat(this->(5.0, 6.0), 100)));	

new zoo(this->(new Cat(new Point(10.0, 15.0), 75), this->new Cat(new Point(5.0, 6.0), 50)->if (true) return new Cat(this->(5.0, 6.0), 60)));	

new zoo(this->(new Cat(new Point(10.0, 15.0), 75), this->new Cat(this->(5.0, 6.0), 60)));			
*/
}