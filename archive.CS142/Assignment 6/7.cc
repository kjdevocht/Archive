//Kevin DeVocht Assignment 6 Exercise 7. Write jumpLeft and jumpRight


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

// Contract: move : Point* -> Point*
// Purpose: move the y coordinate by 10.0
Point* move(Point*p) {
  // Template: p p->x p->y 

/*Test 1
	move(5.0,0.0)
		p->x =5.0
		p->y =0.0
		ans =0.0+10.0
	return 10.0
*/
	
/*Test 2
	move(5.0, 800.0)
		p->x =5.0
		p->y = 800.0
		ans = 800.0 + 10.0
	return 810.0
*/
/*Generalize 1 & 2
	in order to generalize you must use p-> + 10 to account for all possible "y"'s
*/
  return new_Point(p->x , p->y + 10.0);
}	

//Contract: jump: Cat*->Cat*
//Purpose: To make a cat jump by 10 units
Cat* jump(Cat*c){
//Template: c c->posn c->happiness

/*Test 1
	jump(Happy)
		posn =5.0, 0.0
		happiness = 85
		ans = new_Cat(5.0, 0.0 + 10.0), 85)
	return new_Cat((5.0,10.0), 85)
*/

/*Test 2
	jump(Sad)
		posn = 5.0, 800.0
		happiness = 85
		ans = new_Cat((5.0, 800.0 + 10), 35)
	return new_Cat((5.0, 810.0), 35)
*/

/*Generalize Tests 1 and 2
	Since function "jump" needs to change a value of posn which is more than layer deep function "jump" 
	must call funciton "move" and feed "move" then generic term c->posn to cover all possible outcomes
*/
return new_Cat(move(c->posn), c->happiness);
}

//Contract: jumpLeft: Cat*->Cat*
//Purpose: To make  the cat on the left jump
zoo* jumpLeft (zoo*z){
//Template: z z->left z->right

/*Test 1
	jump(San_Diego)
		San_Diego->left = new_Cat(new_Point(1.0, 2.0), 15)
		San_Diego->right = new_Cat(new_Point(4.0, 7.0), 67)
		ans = San_Diego->left->posn->y + 10.0
	return new_zoo(new_Cat((1.0, 12.0), 15), new_Cat((4.0, 7.0), 67)
*/

/*Test 2
	jumpLeft(Hoogle)
		Hoogle->left = new_Cat(new_Point(10.0, 15.0), 75)
		Hoogle->right = new_Cat(new_Point(5.0,6.0), 50)
		ans = Hoogle->left->posn->y + 10
	return new_zoo(new_Cat(new_Point(10.0, 25.0), 75), new_Cat(new_Point(5.0,6.0), 50))
*/

/*Generalize Tests 1 and 2
	You must call function jump, which will calll function move, on the left Cat so that each function is not looking at too much
*/
 return new_zoo(jump(z->left), z->right);
}



 //Contract: jumpRight: zoo*->zoo*
 //Purpose: To make the cat in the right cage jump
 zoo* jumpRight (zoo*z){
 //Template: z z->left z->right
 
/*Test 1
	jump(San_Diego)
		San_Diego->left = new_Cat(new_Point(1.0, 2.0), 15)
		San_Diego->right = new_Cat(new_Point(4.0, 7.0), 67)
		ans = San_Diego->right->posn->y +10
	return new_zoo(new_Cat((1.0, 2.0), 15), new_Cat((4.0, 17.0), 67)
*/
 
/*Test 2
	jumpRight(Hoogle)
		Hoogle->left = new_Cat(new_Point(10.0, 15.0), 75)
		Hoogle->right = new_Cat(new_Point(5.0,6.0), 50)
		ans = Hoogle->right->posn->y +10
	return new_zoo(new_Cat(new_Point(10.0, 15.0), 75), new_Cat(new_Point(5.0,16.0), 50))
*/


/*Generalize Tests 1 and 2
	You must call function jump, which will calll function move, on the right Cat so that each function is not looking at too much
*/
return new_zoo(z->left, jump(z->right));
}

int main () {
zoo* Hoogle = new_zoo(new_Cat(new_Point(10.0, 15.0), 75), new_Cat(new_Point(5.0, 6.0), 50));
zoo* San_Diego = new_zoo(new_Cat(new_Point(1.0, 2.0), 15), new_Cat(new_Point(4.0,7.0), 67));
zoo* Life_Is = new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77));

zoo*yek = jumpLeft(Hoogle);
zoo*dough = jumpLeft(San_Diego);
zoo*say = jumpLeft(Life_Is);

zoo*chahar = jumpRight(Hoogle);
zoo*panj = jumpRight(San_Diego);
zoo*shaysh = jumpRight(Life_Is);


printf("The answer is %f but it should be %f\n", yek->left->posn->y, 25.0);
printf("The answer is %f but it should be %f\n", dough->left->posn->y, 12.0);
printf("The answer is %f but it should be %f\n", say->left->posn->y, 10.0);

printf("The answer is %f but it should be %f\n", chahar->right->posn->y, 16.0);
printf("The answer is %f but it should be %f\n", panj->right->posn->y, 17.0);
printf("The answer is %f but it should be %f\n", shaysh->right->posn->y, 17.0);
/*Substitution for jumpLeft
	jumpLeft(Life_Is)
	
	jumpLeft(new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77)))
	
	new_zoo(jump(z->left), z->right)
	
	new_zoo(jump(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77)))
	
	new_zoo(new_Cat(move(c->posn), c->happiness), new_Cat(new_Point(7.0, 7.0), 77))
	
	new_zoo(new_Cat(move(new_Point(0.0, 0.0)), 47), new_Cat(new_Point(7.0, 7.0), 77))
	
	new_zoo(new_Cat(new_Point(p->x , p->y + 10.0),47), new_Cat(new_Point(7.0, 7.0), 77))
	
	new_zoo(new_Cat(new_Point(0.0 , 0.0 + 10.0),47), new_Cat(new_Point(7.0, 7.0), 77))
	
	new_zoo(new_Cat(new_Point(0.0 ,10.0),47), new_Cat(new_Point(7.0, 7.0), 77))

	
	
/* Substitution for newsPaperRight
	jumpRight(Life_Is)
	
	jumpRight(new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77)))

	new_zoo(z->left, jump(z->right))
	
	new_zoo(new_Cat(new_Point(0.0, 0.0), 47), jump(new_Cat(new_Point(7.0, 7.0), 77)))
	
	new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(move(c->posn), c->happiness))
	
	new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(move(new_Point(7.0, 7.0)), 77))
	
	new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(p->x , p->y + 10.0), 77
	
	new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0 , 7.0 + 10.0), 77))
	
	new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0 , 17.0), 77))
*/	
}