/*Kevin DeVocht assignment 19 exercise 3. Develop a data definition 
for Hangman bodies, which are lists of parts. Write a function called 
ripper which returns a body with one less part.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>

//A hangManBody is a
//either a bodyPart
// or an emptyBody
class hangManBody
{
public:
	
	virtual int show () = 0;
	
	//theRipper: hangManBody->hangManBody
	//Purpose: to remove a body part from a body
	virtual hangManBody* theRipper() = 0;
	


};

// An emptyBody is a
// new emtpyBody ()
// where
class emptyBody  : public hangManBody
{
public:
	emptyBody() {}
	
	int show ()
	{
		printf("!\n");
		return 0;
	}
	
	//theRipper: hangManBody->hangManBody
	//Purpose: to remove a body part from a body
	hangManBody* theRipper()
	//Template: this
	{
		return new emptyBody();
	}
};

//a bodyPart is a
// new bodyPart(first, rest)
// where
// first is a string
// and rest is a hangManBody
class bodyPart : public hangManBody
{
public:
	const char* first;
	hangManBody* rest;
	
	bodyPart(const char* first0, hangManBody* rest0)
	{
		this->first = first0;
		this->rest = rest0;
	}
	
	int show ()
	{
	printf("%s, ", this->first);
	this->rest->show();
	return 0;
	}
	
	//theRipper: hangManBody->hangManBody
	//Purpose: to remove a body part from a body
	hangManBody* theRipper()
	//Template: this, this->first, this->rest, this->rest->theRipper()
	{
/*Test 1
	Pre-Condition: forHangman = Left Arm, Right Leg, Left Leg, Torso, Head, !
	forHangman->theRipper()
		this = Left Arm, Right Leg, Left Leg, Torso, Head, !
		this->first = Left Arm
		this->rest = Right Leg, Left Leg, Torso, Head, !
		ans = Right Leg, Left Leg, Torso, Head, !
	return this->rest
	Post-Condition forHangman = Right Leg, Left Leg, Torso, Head, !
*/

/*Test 2
	Pre-Condition: forHangman = Right Leg, Left Leg, Torso, Head, !
	forHangman->theRipper()
		this = Right Leg, Left Leg, Torso, Head, !
		this->first = Right Leg
		this->rest = Left Leg, Torso, Head, !
		ans = Left Leg, Torso, Head, !
	return this->rest
	Post-Condition forHangman = Left Leg, Torso, Head, !
*/

/*Generalize Tests 1 and 2
	this is a really simple.  To cut off one of the body parts
	just return this->rest
*/
		return this->rest;
	}

};
// Starting list of Body Parts
hangManBody* forHangman = new bodyPart("Right Arm", new bodyPart("Left Arm", new bodyPart("Right Leg", new bodyPart("Left Leg", new bodyPart("Torso", new bodyPart("Head", new emptyBody()))))));

//Ripper: nothing->nothing
//Purpose: to remove a bodyPart from forHangman using theRipper
void Ripper ()
//Template:forHangMan
{
/*Test 1
	Pre-Condition: forHangman = Left Arm, Right Leg, Left Leg, Torso, Head, !
	Ripper()
	Post-Condition forHangman = Right Leg, Left Leg, Torso, Head, !
	return ;
*/

/*Test 2
	Pre-Condition: forHangman = Right Leg, Left Leg, Torso, Head, !
	Ripper()
	Post-Condition forHangman = Left Leg, Torso, Head, !
	return ;
*/

/*Generalize Tests 1 and 2
	We know Ripper is a non-function that mutates forHangman and returns
	nothing.  so just make forHangman = forHangman->theRipper()
*/
forHangman = forHangman->theRipper();
return ;
}

// theShow : nothing -> nothing
void theShow () {
forHangman->show();
return ;
}


int main ()
{
hangManBody* mt = new emptyBody();
hangManBody* HangMan = new bodyPart("Head", new bodyPart("Right Leg", new bodyPart("Left Leg", new bodyPart("Right Arm", new bodyPart("Left Arm", new bodyPart("Torso", new emptyBody()))))));

printf("the answer is\n");
HangMan->theRipper()->show();
printf("but should be\nRight Leg, Left Leg, Right Arm, Left Arm, Torso, !\n\n");

printf("the answer is\n");
mt->theRipper()->show();
printf("but should be\n!\n\n");

printf("the answer is\n");
Ripper();
theShow();
printf("but should be\nLeft Arm, Right Leg, Left Leg, Torso, Head, !\n\n");

printf("the answer is\n");
Ripper();
theShow();
printf("but should be\nRight Leg, Left Leg, Torso, Head, !\n\n");

printf("the answer is\n");
Ripper();
theShow();
printf("but should be\nLeft Leg, Torso, Head, !\n\n");

//Substitution:  Ran out of time!
}