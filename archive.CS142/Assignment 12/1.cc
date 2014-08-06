/*Kevin DeVocht Assignment 12 Exercise 1. Develop data definitions for ListOfCourse and Course 
(number, department, name, etc), write a function upperLevelCourses that consumes a list of 
courses and a department and produces a list of all courses at the 300- or 400-level offered by that department.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>

// booleanToString : boolean -> string
const char* booleanToString ( bool it ) {
  if ( it ) { return "true"; } else { return "false"; }
}
  
// streq : string string -> boolean
bool streq ( const char* l, const char* r ) {
return strcmp(l,r) == 0;
}


// A Course is a
// new course (number, department, name)
// where
// number is a number
// department is a string
// name is a string
class Course {
	public:
		int number;
		const char* department;
		const char* name;

	
	Course(int number0, const char* department0, const char* name0) {
		this->number = number0;
		this->department = department0;
		this->name = name0;
	}
	//Contract: show : Course -> int
	int show () {
		return printf("new Course(%d, \"%s\", \"%s\")", this->number,  this->department, this->name);
	}
	
	//Contract: rightDept: Course string->boolean
	//Purpose: To see if the course is in the given department
	bool rightDept (const char* Dept) {
	//Template: this, this->number, this->department, this->name
		if (streq(this->department,Dept)) 
		{return true;}
		/*Test 1
			COW_450->rightDept("Cows")
			this->department = "Cows"
			ans = true
		return true
		*/
		else
		/*Test 2
			POT_431->rightDept("Cows")
			this->department = "Harry Potter"
			ans = false
		return false
		*/
		
		/*Generalize tests 1 and 2
			We can see that we will need two return statements so 
			we will use an if statement and we will call streq to compare strings
		*/
		
		{return false;}
	}
	
	//Contract: upperLevelHuh: course -> boolean
	//Purpose: To see if a given course is an upper level course or not
	bool upperLevelHuh () {
	//Template: this, this->number, this->department, this->name
		if (this->number >= 300) {
		return true;
		}
		/*Test 1
			POT_431->upperLevelHuh()
				this->number = 431
				ans = true
			return true
		*/
		else {
		/*Test 2
			POT_124->upperLevelHuh()
				this->number = 124
				ans = false
			return false
		*/
	
		/*Generalize Tests 1 and 2
			We need an if statement to be able to return 2 possible anwsers and also compare to 300
		*/
		return false;
		}
	}
};

//A ListOfCourse is
// a OneMoreCourse
//or a EmptyLst
class ListOfCourse {
	public:
		//Contract: show : ListOfCourse -> int
		virtual int show () = 0;
		
		//Contract: upperLevelCourses: ListOfCourse string->ListOfCourse
		//Purpose: To display a 300 and 400 level course in a given department
		virtual ListOfCourse* upperLevelCourses (const char* Dept) = 0;
};


//A OneMoreCource is a
// new OneMoreCourse (first, rest)
// where
// first is a Course
// and rest is a ListOfCourse
class OneMoreCourse : public ListOfCourse {
	public:
		Course* first;
		ListOfCourse* rest;
		
	OneMoreCourse(Course* first0, ListOfCourse* rest0) {
		this->first = first0;
		this->rest = rest0;
	}
	//Contract: show : OneMoreCourse -> int
	int show () {
	// Template: this, this->first, this->rest, this->rest->show()
		printf ("new OneMoreCourse(");
		this->first->show();
		printf(", ");
		this->rest->show();
		return printf(" )");
	}
	
	//Contract: upperLevelCourses: OneMoreCourse string->OneMoreCourse
	//Purpose: To display a 300 and 400 level course in a given department
	ListOfCourse* upperLevelCourses (const char* Dept) {
	//Template: this, this->first, this->rest, this->rest->upperLevelCourses(Dept)
		if (this->first->rightDept(Dept) && this->first->upperLevelHuh()) {
	
		/*Test 1
			COW4->upperLevelCourses("Cows")
				this->first = new Course(450, "Cows", "Lecture Series on Cows");
				this->rest = new Course(348, "Cows", "Famous Cows in History"), new Course(234, "Cows", "Cow Colors"), new Course(111, "Cows", "Spelling Cow"), (new EmptyLst())
				ans = new OneMoreCourse(new Course(450, "Cows", "Lecture Series on Cows"), new Course(348, "Cows", "Famous Cows in History"))
			return new OneMoreCourse(new Course(450, "Cows", "Lecture Series on Cows"), new Course(348, "Cows", "Famous Cows in History"))	
		*/
		

		return (new OneMoreCourse(this->first, this->rest->upperLevelCourses(Dept)));
		}
		else {
		/*Test 2
			GUM4->upperLevelCourses("Cows")
				this->first = new Course(424, "Gum Chewing", "Discreet Gum Chewing")
				this->rest = new OneMoreCourse(GUM_355, GUM2), new Course(230, "Gum Chewing", "Advanced Gum Chewing Techniqes"), new Course(101, "Gum Chewing", "Fundamentals of Chewing Gum"),(new EmptyLst())
				ans = new EmptyLst()
			return new EmptyLst()
		*/
		
		/*Generalize Tests 1 and 2
			We can see that we need to call other functions to look deeper and we need to make sure both conditions are true before we add a course to the new list
		*/
		return this->rest->upperLevelCourses(Dept);
		}
	}
};

// An EmptyLst is a 
// new EmptyLst ()
// where there is nothing because it is empty
class EmptyLst : public ListOfCourse {
	public:
		EmptyLst() {
		}
		
	//Contract: show : EmptyLst -> int	
	int show () {
		return printf("new EmptyLst()");
	}
	
	
	//Contract: upperLevelCourses: EmptyLst->ListOfCourse
	//Purpose: To display a 300 and 400 level course in a given department
	ListOfCourse* upperLevelCourses (const char* Dept) {
	//Template: this, Dept
	/*Test 1
		EmptyLst->upperLevelCourses("Gum Chewing")
			this = !
			Dept = "Gum Chewing"
			ans = EmtpyLst()
		return (new EmptyLst())
	*/
	return (new EmptyLst());
	}
};



int main () {
Course* GUM_101 = new Course(101, "Gum Chewing", "Fundamentals of Chewing Gum");
Course* GUM_230 = new Course(230, "Gum Chewing", "Advanced Gum Chewing Techniqes");
Course* GUM_355 = new Course(355, "Gum Chewing", "Chewing and Walking");
Course* GUM_424 = new Course(424, "Gum Chewing", "Discreet Gum Chewing");

Course* POT_124 = new Course(124, "Harry Potter", "Intro to the World of Harry Potter");
Course* POT_256 = new Course(256, "Harry Potter", "The Physics of Quidditch");
Course* POT_312 = new Course(312, "Harry Potter", "Hogwarts a History");
Course* POT_431 = new Course(431, "Harry Potter", "An abstract Study of Divination");

Course* COW_111 = new Course(111, "Cows", "Spelling Cow");
Course* COW_234 = new Course(234, "Cows", "Cow Colors");
Course* COW_348 = new Course(348, "Cows", "Famous Cows in History");
Course* COW_450 = new Course(450, "Cows", "Lecture Series on Cows");

ListOfCourse* none = new EmptyLst();

ListOfCourse* GUM1 = new OneMoreCourse(GUM_101, none);
ListOfCourse* GUM2 = new OneMoreCourse(GUM_230, GUM1);
ListOfCourse* GUM3 = new OneMoreCourse(GUM_355, GUM2);
ListOfCourse* GUM4 = new OneMoreCourse(GUM_424, GUM3);

ListOfCourse* GUMU1 = new OneMoreCourse(GUM_355, none);
ListOfCourse* GUMU2 = new OneMoreCourse(GUM_424, GUMU1);

ListOfCourse* POT1 = new OneMoreCourse(POT_124, none);
ListOfCourse* POT2 = new OneMoreCourse(POT_256, POT1);
ListOfCourse* POT3 = new OneMoreCourse(POT_312, POT2);
ListOfCourse* POT4 = new OneMoreCourse(POT_431, POT3);

ListOfCourse* POTU1 = new OneMoreCourse(POT_312, none);
ListOfCourse* POTU2 = new OneMoreCourse(POT_431, POTU1);

ListOfCourse* COW1 = new OneMoreCourse(COW_111, none);
ListOfCourse* COW2 = new OneMoreCourse(COW_234, COW1);
ListOfCourse* COW3 = new OneMoreCourse(COW_348, COW2);
ListOfCourse* COW4 = new OneMoreCourse(COW_450, COW3);

ListOfCourse* COWU1 = new OneMoreCourse(COW_348, none);
ListOfCourse* COWU2 = new OneMoreCourse(COW_450, COWU1);

ListOfCourse* All1 = new OneMoreCourse(GUM_101, none);
ListOfCourse* All2 = new OneMoreCourse(GUM_230, All1);
ListOfCourse* All3 = new OneMoreCourse(GUM_355, All2);
ListOfCourse* All4 = new OneMoreCourse(GUM_424, All3);
ListOfCourse* All5 = new OneMoreCourse(POT_124, All4);
ListOfCourse* All6 = new OneMoreCourse(POT_256, All5);
ListOfCourse* All7 = new OneMoreCourse(POT_312, All6);
ListOfCourse* All8 = new OneMoreCourse(POT_431, All7);
ListOfCourse* All9 = new OneMoreCourse(COW_111, All8);
ListOfCourse* All10 = new OneMoreCourse(COW_234, All9);
ListOfCourse* All11 = new OneMoreCourse(COW_348, All10);
ListOfCourse* All12 = new OneMoreCourse(COW_450, All11);


  printf("%s, %s\n", booleanToString(COW_450->rightDept("Cows")), booleanToString(COW_450->upperLevelHuh()));
  printf("%s, %s\n", booleanToString(GUM_424->rightDept("Cows")), booleanToString(GUM_424->upperLevelHuh()));
  printf("%s, %s\n", booleanToString(GUM_101->rightDept("Cows")), booleanToString(GUM_101->upperLevelHuh()));
  printf("%s, %s\n", booleanToString(COW_111->rightDept("Cows")), booleanToString(COW_111->upperLevelHuh()));
 printf("The answer is \n  ");
  (All12->upperLevelCourses("Gum Chewing"))->show();
  printf("\n, but should be\n  ");
  GUMU2->show();
  printf("\n");
  
  printf("The answer is \n  ");
  (COW4->upperLevelCourses("Cows"))->show();
  printf("\n, but should be\n  ");
  COWU2->show();
  printf("\n");
  
  printf("The answer is \n  ");
  (POT4->upperLevelCourses("Harry Potter"))->show();
  printf("\n, but should be\n  ");
  POTU2->show();
  printf("\n");
  
  printf("The answer is \n  ");
  (POT4->upperLevelCourses("Cows"))->show();
  printf("\n, but should be\n  ");
  none->show();
  printf("\n");
  
  /* Substitution
	1. COW3->upperLevelCourses("Cows")
	
	2. new OneMoreCourse(COW_348, COW2)->upperLevelCourses("Cows")
	
	3. if (this->first->rightDept(Dept) && this->first->upperLevelHuh()) {return (new OneMoreCourse(this->first, this->rest->upperLevelCourses(Dept)));}else {return this->rest->upperLevelCourses(Dept);}

	4. if (COW_348->rightDept("Cows") && COW_348->upperLevelHuh()) {return (new OneMoreCourse(COW_348, COW2->upperLevelCourses("Cows")));}else {return COW2->upperLevelCourses("Cows");}
	
	5. if (new Course(348, "Cows", "Famous Cows in History")->rightDept("Cows") && new Course(348, "Cows", "Famous Cows in History")->upperLevelHuh()) {return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), new OneMoreCourse(COW_234, COW1)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_234, COW1)->upperLevelCourses("Cows");}
	
	6. if (if (streq(this->department,Dept)) {return true;} else {return false;} && if (this->number >= 300) {return true;} else {return false;}) {return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), new OneMoreCourse(COW_234, COW1)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_234, COW1)->upperLevelCourses("Cows");}

	7. if (if (streq("Cows","Cows")) {return true;} else {return false;} && if (348 >= 300) {return true;} else {return false;}) {return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), new OneMoreCourse(COW_234, COW1)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_234, COW1)->upperLevelCourses("Cows");}
			
	8. if (if (return strcmp(l,r) == 0) {return true;} else {return false;} && if (true) {return true;} else {return false;}) {return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), new OneMoreCourse(COW_234, COW1)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_234, COW1)->upperLevelCourses("Cows");}
	
	9. if (if (return strcmp("Cows","Cows") == 0) {return true;} else {return false;} && true ) {return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), new OneMoreCourse(COW_234, COW1)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_234, COW1)->upperLevelCourses("Cows");}

	10. if (if (return 0 == 0) {return true;} else {return false;} && true ) {return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), new OneMoreCourse(COW_234, COW1)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_234, COW1)->upperLevelCourses("Cows");}

	11. if (true {return true;} else {return false;} && true ) {return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), new OneMoreCourse(COW_234, COW1)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_234, COW1)->upperLevelCourses("Cows");}

	12. return true && true  {return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), new OneMoreCourse(COW_234, COW1)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_234, COW1)->upperLevelCourses("Cows");}

	13. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), new OneMoreCourse(COW_234, COW1)->upperLevelCourses("Cows")));
	
	14. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), new OneMoreCourse(new Course(234, "Cows", "Cow Colors"), new OneMoreCourse(COW_111, none))->upperLevelCourses("Cows")));
	
	15. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (this->first->rightDept(Dept) && this->first->upperLevelHuh()) {return (new OneMoreCourse(this->first, this->rest->upperLevelCourses(Dept)));}else {return this->rest->upperLevelCourses(Dept)));}

	16. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (new Course(234, "Cows", "Cow Colors")->rightDept("Cows") && new Course(234, "Cows", "Cow Colors")->upperLevelHuh()) {return (new OneMoreCourse(new Course(234, "Cows", "Cow Colors"), new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}

	17. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (if (streq(this->department,Dept)) {return true;} else {return false;} && if (this->number >= 300) {return true;} else {return false;}) {return (new OneMoreCourse(new Course(234, "Cows", "Cow Colors"), new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}
	
	18. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (if (streq(new Course(234, "Cows", "Cow Colors")->department,"Cows")) {return true;} else {return false;} && if (new Course(234, "Cows", "Cow Colors")->number >= 300) {return true;} else {return false;}) {return (new OneMoreCourse(new Course(234, "Cows", "Cow Colors"), new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}

	19. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (if (streq("Cows","Cows")) {return true;} else {return false;} && if (234 >= 300) {return true;} else {return false;}) {return (new OneMoreCourse(new Course(234, "Cows", "Cow Colors"), new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}

	20. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (if (return strcmp(l,r) == 0) {return true;} else {return false;} && if (false) {return true;} else {return false;}) {return (new OneMoreCourse(new Course(234, "Cows", "Cow Colors"), new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}
	
	21. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (if (return strcmp("Cows","Cows") == 0) {return true;} else {return false;} && false) {return (new OneMoreCourse(new Course(234, "Cows", "Cow Colors"), new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}
	
	22. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (if (return strcmp(0 == 0) {return true;} else {return false;} && false) {return (new OneMoreCourse(new Course(234, "Cows", "Cow Colors"), new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}

	23. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (if (true) {return true;} else {return false;} && false) {return (new OneMoreCourse(new Course(234, "Cows", "Cow Colors"), new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}

	24. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (true && false) {return (new OneMoreCourse(new Course(234, "Cows", "Cow Colors"), new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}
	
	25. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (false) {return (new OneMoreCourse(new Course(234, "Cows", "Cow Colors"), new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}else {return new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}

	26. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (false) {return new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")));}

	27. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")))

	28. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	new OneMoreCourse(COW_111, none)->upperLevelCourses("Cows")))
	
	29. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	new OneMoreCourse(new Course(111, "Cows", "Spelling Cow"), none)->upperLevelCourses("Cows")))

	30. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (this->first->rightDept(Dept) && this->first->upperLevelHuh()) {return (new OneMoreCourse(this->first, this->rest->upperLevelCourses(Dept)));}else {return this->rest->upperLevelCourses(Dept)));}

	31. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (COW_111->rightDept("Cows") && COW_111->upperLevelHuh()) {return (new OneMoreCourse(COW_111, none->upperLevelCourses("Cows")));}else {return none->upperLevelCourses("Cows")));}

	32. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (new Course(111, "Cows", "Spelling Cow")->rightDept("Cows") && new Course(111, "Cows", "Spelling Cow")->upperLevelHuh()) {return (new OneMoreCourse(new Course(111, "Cows", "Spelling Cow"), new EmptyLst()->upperLevelCourses("Cows")));}else {return new EmptyLst()->upperLevelCourses("Cows")));}

	33. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (if (streq(this->department,Dept)) {return true;} else {return false;} && if (this->number >= 300) {return true;} else {return false;}) {return (new OneMoreCourse(new Course(111, "Cows", "Spelling Cow"), new EmptyLst()->upperLevelCourses("Cows")));}else {return new EmptyLst()->upperLevelCourses("Cows")));}

	34. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (if (streq("Cows","Cows")) {return true;} else {return false;} && if (111 >= 300) {return true;} else {return false;}) {return (new OneMoreCourse(new Course(111, "Cows", "Spelling Cow"), new EmptyLst()->upperLevelCourses("Cows")));}else {return new EmptyLst()->upperLevelCourses("Cows")));}
	
	35. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (if (return strcmp(l,r) == 0) {return true;} else {return false;} && if (false) {return true;} else {return false;}) {return (new OneMoreCourse(new Course(111, "Cows", "Spelling Cow"), new EmptyLst()->upperLevelCourses("Cows")));}else {return new EmptyLst()->upperLevelCourses("Cows")));}

	36. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (if (return strcmp("Cows","Cows") == 0) {return true;} else {return false;} && false) {return (new OneMoreCourse(new Course(111, "Cows", "Spelling Cow"), new EmptyLst()->upperLevelCourses("Cows")));}else {return new EmptyLst()->upperLevelCourses("Cows")));}

	37. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (if (return strcmp 0 == 0) {return true;} else {return false;} && false) {return (new OneMoreCourse(new Course(111, "Cows", "Spelling Cow"), new EmptyLst()->upperLevelCourses("Cows")));}else {return new EmptyLst()->upperLevelCourses("Cows")));}

	38. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (if (true) {return true;} else {return false;} && false) {return (new OneMoreCourse(new Course(111, "Cows", "Spelling Cow"), new EmptyLst()->upperLevelCourses("Cows")));}else {return new EmptyLst()->upperLevelCourses("Cows")));}

	39. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (true && false) {return (new OneMoreCourse(new Course(111, "Cows", "Spelling Cow"), new EmptyLst()->upperLevelCourses("Cows")));}else {return new EmptyLst()->upperLevelCourses("Cows")));}

	40. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	if (false) {return (new OneMoreCourse(new Course(111, "Cows", "Spelling Cow"), new EmptyLst()->upperLevelCourses("Cows")));}else {return new EmptyLst()->upperLevelCourses("Cows")));}

	41. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	new EmptyLst()->upperLevelCourses("Cows")))
	
	42. return (new OneMoreCourse(new Course(348, "Cows", "Famous Cows in History"), 	new EmptyLst()))
*/
	
}