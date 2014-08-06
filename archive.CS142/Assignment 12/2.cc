/*Kevin DeVocht Assignment 12 Exercise 2. A phone directory combines names with phone numbers. 
Develop a data definition for phone records and directories. Using this data definition develop 
the function, whoseNumber, which returns the name that goes with some given phone number and 
phone directory.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>



// streq : string string -> boolean
bool streq ( const char* l, const char* r ) {
  return strcmp(l,r) == 0;
}



// A PhoneRecord is a
// new PhoneRecord (number, name)
// where 
//a number is a string
// and a name is a string
class PhoneRecord {
	public:
		const char* number;
		const char* name;
	
	PhoneRecord (const char* number0, const char* name0){
		this->number = number0;
		this->name = name0;
	}
	//Contract: show : PhoneRecord -> int
	int show () {
    return printf("new PhoneRecord(\"%s\", \"%s\")", this->number, this->name);
	}
	
	//Contract: rightNum: PhoneRecord string -> s
	//Contract: To find the name that corresponds with a given number
	const char* rightNum (const char* theNum) {
	//Template: this, this->number, this->name, theNum
	if (streq(this->number, theNum)) {
	return this->name;
	}
	/*Test 1
		lil_kevin->rightNum("319-683-2983")
			this->number = "319-683-2983"
			this->name = "lil kevin devocht"
			ans = "lil kevin devocht"
		return this->name
	*/
	else {
	/*Test 2
		Kevin->rightNum("319-683-2983")
			this->number = "801-234-9292"
			this->name = "Kevin DeVocht"
			ans = not the same
		return ""
	*/
	
	/*Generalize Tests 1 and 2
		We see that we will need to compare strings and return different answers if the strings compared are the same or not
	*/
	return "";
	}
	
	}
};

//A PhoneDir is
// either a OneMoreRecord
// or an EmptyLst
class PhoneDir {
	public:
		//Contract: show : PhoneDir -> int
		 virtual int show () = 0; 
		 
		 //Contract: whoseNumber: PhoneDir string -> string
		 //Purpose:  To see whose name belongs with a given number
		 virtual const char* whoseNumber (const char* who) = 0;
};

//A OneMoreRecord is a 
// new OneMoreRecord(first, rest)
// where
// first is a PhoneRecord
// and rest is a PhoneDir
class OneMoreRecord : public PhoneDir {
	public:
		PhoneRecord* first;
		PhoneDir* rest;
		
	OneMoreRecord(PhoneRecord* first0, PhoneDir* rest0) {
		this->first = first0;
		this->rest = rest0;
	}
	
	//Contract: show : OneMoreRecord -> int
	int show () {
	// Template: this, this->first, this->rest, this->rest->show()
		printf ("new OneMoreRecord(");
		this->first->show();
		printf(", ");
		this->rest->show();
		return printf(" )");
	}
	
	//Contract: whoseNumber: OneMoreRecord string-> string
	//Purpose:  To see whose name belongs with a given number
	const char* whoseNumber (const char* who){
	//Template: this, who, this->first, this->rest this->rest->whoseNumber(who)
		if (!(streq(this->first->rightNum(who),""))) {
		/*Test 1
			Eagle_MT4->whoseNumber("319-683-2983")
				this->first = lil_kevin
				who = "319-683-2983"
				ans = "lil kevin devocht"
			return "lil kevin devocht"
		*/
		return this->first->rightNum(who);
		}
	
		else {
		/*Test 2
			Eagle_MT4->whoseNumber("319-867-5309")
				this->first = lil_kevin
				who = "319-867-5309"
				ans = "Neil Diamond"
			return "Neil Diamond"		
		*/
	
		/*Generalize Tests 1 and 2
			We can see that we need to send each PhoneRecord to rightNum so it can look deeper
			and return the name to use.  If the PhoneRecord does not match the given number, keep looking
		*/
		return this->rest->whoseNumber(who);
		}
	}
};

class EmptyLst : public PhoneDir {
	public:
	
	EmptyLst() { 
	}
	
	//Contract: show : EmptyLst -> int
	int show () {
	return printf("new EmptyLst()");
	}
	
	//Contract: whoseNumber: EmptyLst string -> string
	//Purpose:  To see whose name belongs with a given number
	const char* whoseNumber (const char* who){
	//Template: this, who
	
	/*Test 1
		EmptyLst->whoseNumber("800-555-2368")
			this = !
			ans = EmptyLst()
		return "There is no name matching that number"
	*/
	return "There is no name matching that number";
	}
};




int main () {


PhoneRecord* Kevin = new PhoneRecord("801-234-9292", "Kevin DeVocht");
PhoneRecord* Bob = new PhoneRecord("555-123-4567", "Bob Smith");
PhoneRecord* Neil = new PhoneRecord("319-867-5309", "Neil Diamond");
PhoneRecord* lil_kevin = new PhoneRecord("319-683-2983", "lil kevin devocht");
PhoneRecord* Ghost_Buster = new PhoneRecord("800-555-2368", "The Ghost Busters");

PhoneDir* none = new EmptyLst();
PhoneDir* Eagle_MT1 = new OneMoreRecord(Neil, none);
PhoneDir* Eagle_MT2 = new OneMoreRecord(Ghost_Buster, Eagle_MT1);
PhoneDir* Eagle_MT3 = new OneMoreRecord(Kevin, Eagle_MT2);
PhoneDir* Eagle_MT4 = new OneMoreRecord(lil_kevin, Eagle_MT3);





printf("The answer is \n %s\n but should be \n %s\n", Eagle_MT4->whoseNumber("800-555-2368"), "The Ghost Busters");

printf("The answer is \n %s\n but should be \n %s\n", Eagle_MT3->whoseNumber("319-683-2983"), "There is no name matching that number");

printf("The answer is \n %s\n but should be \n %s\n", Eagle_MT4->whoseNumber("319-867-5309"), "Neil Diamond");


/* Substitution
	1. Eagle_MT1->whoseNumber("800-555-2368")
	
	2. new OneMoreRecord(Neil, none)->whoseNumber("800-555-2368")
	
	3. if (!(streq(this->first->rightNum(who),""))) {return this->first->rightNum(who);} else {return this->rest->whoseNumber(who);}

	4. if (!(streq(new OneMoreRecord(Neil, none)->first->rightNum("800-555-2368"),""))) {return new OneMoreRecord(Neil, none)->first->rightNum("800-555-2368");} else {return new OneMoreRecord(Neil, none)->rest->whoseNumber("800-555-2368");}

	5. if (!(streq(Neil->rightNum("800-555-2368"),""))) {return Neil->rightNum("800-555-2368");} else {return none->whoseNumber("800-555-2368");}

	6. if (!(streq(new PhoneRecord("319-867-5309", "Neil Diamond")->rightNum("800-555-2368"),""))) {return new PhoneRecord("319-867-5309", "Neil Diamond")->rightNum("800-555-2368");} else {return new EmptyLst()->whoseNumber("800-555-2368");}
	
	7. if (!(streq(if (streq(this->number, theNum)) {return this->name;} else {return "";}	,""))) {return new PhoneRecord("319-867-5309", "Neil Diamond")->rightNum("800-555-2368");} else {return new EmptyLst()->whoseNumber("800-555-2368");}

	8. if (!(streq(if (streq(new PhoneRecord("319-867-5309", "Neil Diamond")->number, theNum)) {return new PhoneRecord("319-867-5309", "Neil Diamond")->name;} else {return "";}	,""))) {return new PhoneRecord("319-867-5309", "Neil Diamond")->rightNum("800-555-2368");} else {return new EmptyLst()->whoseNumber("800-555-2368");}

	9. if (!(streq(if (streq("319-867-5309", theNum)) {return "Neil Diamond";} else {return "";}	,""))) {return new PhoneRecord("319-867-5309", "Neil Diamond")->rightNum("800-555-2368");} else {return new EmptyLst()->whoseNumber("800-555-2368");}
	
	10. if (!(streq(if (streq("319-867-5309", "800-555-2368")) {return "Neil Diamond";} else {return "";}	,""))) {return new PhoneRecord("319-867-5309", "Neil Diamond")->rightNum("800-555-2368");} else {return new EmptyLst()->whoseNumber("800-555-2368");}

	11. if (!(streq(return strcmp(l,r) == 0)) {return "Neil Diamond";} else {return "";}	,"") {return this->first->rightNum(who);} else {return new EmptyLst()->whoseNumber("800-555-2368");}
	
	12. if (!(streq(return strcmp("319-867-5309","800-555-2368") == 0)) {return "Neil Diamond";} else {return "";}	,"") {return this->first->rightNum(who);} else {return new EmptyLst()->whoseNumber("800-555-2368");}
	
	13. if (!(streq(return strcmp(1) == 0)) {return "Neil Diamond";} else {return "";}	,"") {return this->first->rightNum(who);} else {return new EmptyLst()->whoseNumber("800-555-2368");}
	
	14. if (!(streq(return false)) {return "Neil Diamond";} else {return "";}	,"") {return this->first->rightNum(who);} else {return new EmptyLst()->whoseNumber("800-555-2368");}
	
	15. if (!(streq(return "")	,"")) {return this->first->rightNum(who);} else {return new EmptyLst()->whoseNumber("800-555-2368");}

	16. if (!(streq( ""	,""))) {return this->first->rightNum(who);} else {return new EmptyLst()->whoseNumber("800-555-2368");}

	17. if (!(  return strcmp(l,r) == 0;)) {return this->first->rightNum(who);} else {return new EmptyLst()->whoseNumber("800-555-2368");}
	
	18. if (!(  return strcmp("","") == 0;)) {return this->first->rightNum(who);} else {return new EmptyLst()->whoseNumber("800-555-2368");}

	19. if (!(  return strcmp(0 == 0;))) {return this->first->rightNum(who);} else {return new EmptyLst()->whoseNumber("800-555-2368");}
	
	20. if (!(  true)) {return this->first->rightNum(who);} else {return new EmptyLst()->whoseNumber("800-555-2368");}
	
	21. if (false) {return this->first->rightNum(who);} else {return new EmptyLst()->whoseNumber("800-555-2368");}
	
	22. return new EmptyLst()->whoseNumber("800-555-2368")
	
	23. return "There is no name matching that number";
*/

}