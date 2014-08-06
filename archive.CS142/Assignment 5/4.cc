/*Kevin DeVocht Assignment 5 Exercise 4. Write a data definition for three digit numbers, where there are three integers. Write a function called reveal that takes three arguments: a ’chosen’ three digit number,
 a ’current’ three digit number, and ’guess’ integer. The function produces a new ’current’ number where the digits are the same as before, unless the ’guess’ is the same as one of the digits, in which case it is 
 "revealed". For example,

reveal( new_TDN(1, 2, 3), new_TDN(0, 0, 0), 1) = new_TDN( 1, 0, 0)

reveal( new_TDN(1, 2, 3), new_TDN(1, 0, 0), 3) = new_TDN( 1, 0, 3)

reveal( new_TDN(1, 2, 3), new_TDN(1, 0, 3), 6) = new_TDN( 1, 0, 3)

reveal( new_TDN(1, 2, 3), new_TDN(1, 0, 3), 2) = new_TDN( 1, 2, 3)

reveal( new_TDN(1, 2, 2), new_TDN(1, 0, 3), 2) = new_TDN( 1, 2, 2)

This is basically Hangman!
*/

#include <stdio.h>
#include <math.h>


/* A TDN is a
	new TDN(one, two, three)
	one is an int
	two is an int
	three is an int
*/

typedef struct TDN;

typedef struct TDN {
	int one;
	int two;
	int three;
};

//Contract: new_TDN: number number number -> TDN
//Purpose: to create a new TDN
TDN* new_TDN (int one0, int two0, int three0){
//Template: one0 two0 three0
TDN* t = new TDN ();
	t->one = one0;
	t->two = two0;
	t->three = three0;
/*Example for new_TDN
	new_TDN(1, 2, 3)
	t->one = 1
	t->two = 2
	t->three = 3
	t = (1,2,3)
*/	
return t;
}


//Contract: reveal: TDN TDN number -> TDN
//Purpose: To see if the guess number is one of the chosen numbers and if it is then reveal it
TDN* reveal (TDN* t, TDN* current, int guess){
//Template t t->one t->two t->three guess

if (guess == t->one ){
/*Test!
	reveal((1,2,3),(0,0,0), 1)
		t = (1,2,3)
		current = (0,0,0)
		guess = 1
		ans = 1 == 1
	return new_TDN(1,0,0)
*/	
return new_TDN(t->one, current->two, current->three);
}
if (guess == t->two) {
/*Test 2
	reveal((1,2,3),(1,0,3), 2)
		t = (1,2,3)
		current = (1,0,3)
		guess = 2
		ans = 2 == 2
	return new_TDN(1,2,3)
*/
/*Generalize Tests 1 and 2
	In order to generalize these two tests you must return the t value of the 
	number you are looking at if it is the same and the current value of 
	everything else
*/
return new_TDN(current->one, t->two, current->three);
}
if (guess == t->three) {
/*Test 3
	reveal((1,2,3),(1,0,0), 3)
		t = (1,2,3)
		current = (1,0,0)
		guess = 3
		ans = 3 == 3
	return new_TDN(1,0,3)
*/
return new_TDN(t->one, current->two, t->three);
}
else {
/*Test4
	reveal((1,2,3),(1,0,0), 4)
		t = (1,2,3)
		current = (1,0,0)
		guess = 4
		ans = 4 does not == one, two or three
	return (1,0,0)
*/

/*Generalize all tests with Test 4
	If none of the values are the same as the ones they are compared to you must return current values for all three parts of new_TDN
*/
return new_TDN(current->one, current->two, current->three);
}
}

int main (){


printf( "the answer is %d, %d, %d but it should be %d, %d, %d\n", reveal(new_TDN(1,2,3), new_TDN(0,0,0), 1)->one, reveal(new_TDN(1,2,3), new_TDN(0,0,0), 1)->two, reveal(new_TDN(1,2,3), new_TDN(0,0,0), 1)->three,
new_TDN(1,0,0)->one, new_TDN(1,0,0)->two, new_TDN(1,0,0)->three);
printf( "the answer is %d, %d, %d but it should be %d, %d, %d\n", reveal(new_TDN(1,2,3), new_TDN(1,0,0), 4)->one, reveal(new_TDN(1,2,3), new_TDN(1,0,0), 4)->two, reveal(new_TDN(1,2,3), new_TDN(1,0,0), 4)->three,
new_TDN(1,0,0)->one, new_TDN(1,0,0)->two, new_TDN(1,0,0)->three);
printf( "the answer is %d, %d, %d but it should be %d, %d, %d\n", reveal(new_TDN(1,2,3), new_TDN(1,0,0), 3)->one, reveal(new_TDN(1,2,3), new_TDN(1,0,0), 3)->two, reveal(new_TDN(1,2,3), new_TDN(1,0,0), 3)->three,
new_TDN(1,0,3)->one, new_TDN(1,0,3)->two, new_TDN(1,0,3)->three);
printf( "the answer is %d, %d, %d but it should be %d, %d, %d\n", reveal(new_TDN(1,2,3), new_TDN(1,0,3), 2)->one, reveal(new_TDN(1,2,3), new_TDN(1,0,3), 2)->two, reveal(new_TDN(1,2,3), new_TDN(1,0,3), 2)->three,
new_TDN(1,2,3)->one, new_TDN(1,2,3)->two, new_TDN(1,2,3)->three);

/*Substitution
	reveal(new_TDN(1,2,3), new_TDN(1,0,3), 2)
	reveal(t, current, guess)
	reveal((new_TDN(one0, two0, three0), new_TDN(one0, two0, three0), 2)
	reveal((new_TDN(one0, two0, three0), new_TDN(one0, two0, three0), 2)
			TDN* t = new TDN ();				TDN* t = new TDN ();
				t->one = one0;						t->one = one0;	
				t->two = two0;						t->two = two0;	
				t->three = three0;					t->three = three0;				
	reveal((new_TDN(one0, two0, three0), new_TDN(one0, two0, three0), 2)
			TDN* t = new TDN ();				TDN* t = new TDN ();
				t->one = 1;							t->one = 1;	
				t->two = 2;							t->two = 0;	
				t->three = 3;						t->three = 3;								
			return t							return t
	reveal((1,2.3), (1,0,3), 2)		
		if (guess == t->one ){
			return new_TDN(t->one, current->two, current->three);
		if (guess == t->two) {
			return new_TDN(current->one, t->two, current->three);}
		if (guess == t->three) {
			return new_TDN(t->one, current->two, current->three);}
		else {
			return new_TDN(current->one, current->two, current->three);}}
	reveal((1,2.3), (1,0,3), 2)		
		if (2 == 1 ){
			return new_TDN(t->one, current->two, current->three);
		if (2 == 2) {
			return new_TDN(current->one, t->two, current->three);}
		if (2 == 3) {
			return new_TDN(t->one, current->two, current->three);}
		else {
			return new_TDN(current->one, current->two, current->three);}}
	reveal((1,2.3), (1,0,3), 2)		
		if (2 == 2) {
			return new_TDN(current->one, t->two, current->three);}
	reveal((1,2.3), (1,0,3), 2)		
		if (2 == 2) {
			return new_TDN(1, 2, 3);}
*/
}
