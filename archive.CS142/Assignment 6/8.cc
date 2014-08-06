/*Kevin DeVocht Assignment 6 Exercise 8. Modify jumpLeft and jumpRight to ensure that when cat’s jump, they can’t land on each other. 
Assume that cats are perfect circles with radius 5. If a jump would have a cat land another, leave them both unmoved.
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




//Contract: point_touching: Point* Point*->bool
//Purpose: To make sure two Point*s are more than 5 units away
bool point_touching (Point* posnL, Point* posnR) {
//Template posnL posnL->x posnL->y posnR ponsR->x posnR->y

	if (posnR->y - posnL->y > 10 || posnR->y - posnL->y < -10 && posnR->x - posnL->x > 10 || posnR->x -posnL->x <-10) {


/*Test 
	point_touching((35.0, 200.0), (4.0, 7.0))
		posnL->x = 35.0
		posnL->y = 200.0
		posnR->x = 4.0
		posnR->y = 7.0
		ans = 7.0 - 200.0 > 10 || 7.0 - 200.0 && 4.0 - 35.0 >10 || 4.0 - 35.0 < -10
	return true
*/
	return true;}
	else {
	/*Test 2
	point_touching((5.0, 15.0), (5.0, 20.0))
		posnL->x = 5.0
		posnL->y = 15.0
		posnR->x = 5.0
		posnR->y = 20.0
		ans = 20.0 - 15.0 > 10 || 20.0 - 15.0 && 5.0 - 20.0 >10 || 5.0 - 20.0 < -10
	return false	
*/

	/*Generalize Tests 1 and 2
	As you can see by two different answers an if statment is needed to determine if two given points are within 10 units of each other.  If they are return false
*/
	return false;} 
}






//Contract: Cat_touching: Cat* Cat* -> bool
//Purpose:  To make sure two cats will not touch each other
bool cat_touching (Cat* catL, Cat* catR) {
//Template: catL catL->posn catL->happiness catR catR->posn catR->happiness


/*Test 1
	cat_touching(new_Cat(new_Point(35.0, 200.0), 15), new_Cat(new_Point(4.0,7.0), 67))
		catL->posn = (35.0, 200.0)
		catL->happiness = 15
		catR->posn = (4.0, 7.0)
		catR->happiness = 67
		ans = point_touching((35.0, 200.0), (4.0, 7.0))
	return true
*/
/*Test 2
	cat_touching(new_Cat(new_Point(5.0, 15.0), 75), new_Cat(new_Point(5.0, 20.0), 50)
		catL->posn = (5.0, 15.0)
		catL->happiness = 75
		catR->posn = (5.0, 20.0)
		catR->happiness = 50
		ans = point_touching((35.0, 200.0), (4.0, 7.0))
	return false
*/



/*Generalize Tests 1 and 2
	In all reallity this function just sends information to another function that can look deeper than it can so just return the results of point_touching
*/
return point_touching(catL->posn, catR->posn);
}


//Contract: jumpLeft: Cat*->Cat*
//Purpose: To make  the cat on the left jump
zoo* jumpLeft (zoo*z){
//Template: z z->left z->right
if (cat_touching(z->left, z->right) == true) {
	return new_zoo(jump(z->left), z->right);
	}
/*Test 1
	jump(San_Diego)
		San_Diego->left = new_Cat(new_Point(35.0, 200.0), 15)
		San_Diego->right = new_Cat(new_Point(4.0, 7.0), 67)
		ans = San_Diego->left->posn->y + 10.0
	return new_zoo(new_Cat((35.0, 210.0), 15), new_Cat((4.0, 7.0), 67)
*/


else {
/*Test 2
	jumpLeft(Hoogle)
		Hoogle->left = new_Cat(new_Point(10.0, 15.0), 75)
		Hoogle->right = new_Cat(new_Point(5.0,20.0), 50)
		ans = new_zoo(left, right)
	return new_zoo(new_Cat(new_Point(10.0, 15.0), 75), new_Cat(new_Point(5.0,20.0), 50))
*/

/*Generalize Tests 1 and 2
	Because there can be two different outcomes you must use an if statement to qualify a true statement
*/

 return new_zoo(z->left, z->right);
 }
}



 //Contract: jumpRight: zoo*->zoo*
 //Purpose: To make the cat in the right cage jump
 zoo* jumpRight (zoo*z){
 //Template: z z->left z->right
 if (cat_touching(z->left, z->right) == true) {
	return new_zoo(z->left, jump(z->right));
	}
/*Test 1
	jump(San_Diego)
		San_Diego->left = new_Cat(new_Point(35.0, 200.0), 15)
		San_Diego->right = new_Cat(new_Point(4.0, 7.0), 67)
		ans = San_Diego->right->posn->y +10
	return new_zoo(new_Cat((35.0, 200.0), 15), new_Cat((4.0, 17.0), 67)
*/
 
else {
/*Test 2
	jumpRight(Hoogle)
		Hoogle->left = new_Cat(new_Point(10.0, 15.0), 75)
		Hoogle->right = new_Cat(new_Point(5.0,20.0), 50)
		ans = new_zoo(left, right)
	return new_zoo(new_Cat(new_Point(10.0, 15.0), 75), new_Cat(new_Point(5.0,20.0), 50))
*/


/*Generalize Tests 1 and 2
	Because there can be two different outcomes you must use an if statement to qualify a true statement
*/
 return new_zoo(z->left, z->right);
}
}
int main () {
zoo* Hoogle = new_zoo(new_Cat(new_Point(5.0, 15.0), 75), new_Cat(new_Point(5.0, 20.0), 50));
zoo* San_Diego = new_zoo(new_Cat(new_Point(35.0, 200.0), 15), new_Cat(new_Point(4.0,7.0), 67));
zoo* Life_Is = new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77));

zoo*yek = jumpLeft(Hoogle);
zoo*dough = jumpLeft(San_Diego);
zoo*say = jumpLeft(Life_Is);

zoo*chahar = jumpRight(Hoogle);
zoo*panj = jumpRight(San_Diego);
zoo*shaysh = jumpRight(Life_Is);


printf("The answer is (%f, %f) (%f, %f)but\n it should be (%f, %f)(%f, %f)\n\n", yek->left->posn->x, yek->left->posn->y, yek->right->posn->x, yek->right->posn->y, 5.0, 15.0, 5.0, 20.0);
printf("The answer is (%f, %f) (%f, %f)but\n it should be (%f, %f)(%f, %f)\n\n", dough->left->posn->x, dough->left->posn->y, dough->right->posn->x, dough->right->posn->y, 35.0, 210.0, 4.0, 7.0);
printf("The answer is (%f, %f) (%f, %f)but\n it should be (%f, %f)(%f, %f)\n\n", say->left->posn->x, say->left->posn->y, say->right->posn->x, say->right->posn->y, 0.0, 0.0, 7.0, 7.0);

printf("The answer is (%f, %f) (%f, %f)but\n it should be (%f, %f)(%f, %f)\n\n", chahar->left->posn->x, chahar->left->posn->y, chahar->right->posn->x, chahar->right->posn->y,5.0, 15.0, 5.0, 20.0);
printf("The answer is (%f, %f) (%f, %f)but\n it should be (%f, %f)(%f, %f)\n\n", panj->left->posn->x, panj->left->posn->y, panj->right->posn->x, panj->right->posn->y, 35.0, 200.0, 4.0, 17.0);
printf("The answer is (%f, %f) (%f, %f)but\n it should be (%f, %f)(%f, %f)\n\n", shaysh->left->posn->x, shaysh->left->posn->y, shaysh->right->posn->x, shaysh->right->posn->y,0.0, 0.0 , 7.0, 7.0);
/*Substitution for jumpLeft
	jumpLeft(Life_Is)
	
	jumpLeft(new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77)))
	
	if (cat_touching(z->left, z->right) == true) {return new_zoo(jump(z->left), z->right);else {return new_zoo(z->left, z->right)}
	
	if (cat_touching(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77)) == true) {return new_zoo(jump(new_Cat(new_Point(0.0, 0.0), 47)), new_Cat(new_Point(7.0, 7.0), 77));else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}
	
	if (point_touching(catL->posn, catR->posn) == true) {return new_zoo(new_Cat(move(c->posn), c->happiness));else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}	
	
	if (point_touching((0.0, 0.0), (7.0, 7.0) == true) {return new_zoo(new_Cat(move(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77));else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}	
	
	if (if (posnR->y - posnL->y > 10 || posnR->y - posnL->y < -10 && posnR->x - posnL->x > 10 || posnR->x -posnL->x <-10) {return true;} else {return false;}  == true) {return new_zoo(new_Cat(new_Point(p->x , p->y + 10.0));}, 47), new_Cat(new_Point(7.0, 7.0), 77));else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}

	if (if (7.0 - 0.0 > 10 || 7.0 - 0.0 < -10 && 7.0 - 0.0 > 10 || 7.0 - 0.0 <-10) {return true;} else {return false;}  == true) {return new_zoo(new_Cat(new_Point(0.0 , 0.0 + 10.0));}, 47), new_Cat(new_Point(7.0, 7.0), 77));else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}	
	
	if (if (false || false && false || false) {return true;} else {return false;}  == true) {return new_zoo(new_Cat(new_Point(0.0, 10.0), 47), new_Cat(new_Point(7.0, 7.0), 77));else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}
	
	if (if (false) {return true;} else {return false;}  == true) {return new_zoo(new_Cat(new_Point(0.0, 10.0), 47), new_Cat(new_Point(7.0, 7.0), 77));else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}
	
	if (false == true) {return new_zoo(new_Cat(new_Point(0.0, 10.0), 47), new_Cat(new_Point(7.0, 7.0), 77));else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}
	
	if (false) {return new_zoo(new_Cat(new_Point(0.0, 10.0), 47), new_Cat(new_Point(7.0, 7.0), 77));else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}
	
	return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))
	

/* Substitution for newsPaperRight

	jumpRight(Life_Is)
	
	jumpRight(new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77)))
	
	if (cat_touching(z->left, z->right) == true) {return new_zoo(z->left, jump(z->right));}else {return new_zoo(z->left, z->right)
	
	if (cat_touching(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77)) == true) {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), jump(new_Cat(new_Point(7.0, 7.0), 77)));}else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}
	
	if (point_touching(catL->posn, catR->posn)== true) {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(move(c->posn), c->happiness));}else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}	
	
	if (point_touching((0.0, 0.0), (7.0, 7.0)== true) {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(move(7.0, 7.0 ), 47));}else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}		
	
	if (if (posnR->y - posnL->y > 10 || posnR->y - posnL->y < -10 && posnR->x - posnL->x > 10 || posnR->x -posnL->x <-10) {return true;} else {return false;}== true) {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(p->x , p->y + 10.0));}else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}	
	
	if (if (7.0 - 0.0 > 10 || 7.0 - 0.0 < -10 && 7.0 - 0.0 > 10 || 7.0 - 0.0 <-10) {return true;} else {return false;}== true) {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0 + 10.0), 47);}else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}	
	
	if (if (7.0 > 10 || 7.0 < -10 && 7.0 > 10 || 7.0 <-10) {return true;} else {return false;}== true) {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0,17.0), 47);}else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}	
	
	if (if (false || false && false || false) {return true;} else {return false;}== true) {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0,17.0), 47);}else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}
	
	if (if (false) {return true;} else {return false;}== true) {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0,17.0), 47);}else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}
	
	if (false == true) {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0,17.0), 47);}else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}
	
	if (false) {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0,17.0), 47);}else {return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))}
	
	return new_zoo(new_Cat(new_Point(0.0, 0.0), 47), new_Cat(new_Point(7.0, 7.0), 77))
*/	
}