/*Kevin DeVocht Assignment 6 Exercise 1. A person consists of a name, age, address and telephone number. 
An address consists of a street, city, state, and zip code. Write the data definitions for person and address. 
Then write the function provoSeniorHuh which returns true if the given person is a senior (65 or older) and 
lives in the zip code 84604.
*/




#include <stdio.h>

// booleanToString : boolean -> string
const char* booleanToString ( bool it ) {
 if ( it ) { return "true"; } 
 else { return "false"; }
}


/*An address is a....
new_address(street, city, state, zip_code)
where
street is a string
city is a string
state is a string
zip is an int
*/
typedef struct address;

typedef struct address{
const char* street;
const char* city;
const char* state;
int zip;
};


address* new_address( const char* street0, const char* city0, const char* state0, int zip0) {
address* a = new address ();
a->street = street0;
a->city = city0;
a->state = state0;
a->zip = zip0;
return a;
}


/*A Person is a...
new_Person(name, age, address, phoneNumber)
where
name is a string
age is an int
address is an address*
phoneNumber is a string
*/
typedef struct Person;

typedef struct Person {

const char* name;
int age;
address* street_address;
const char* phoneNumber;
};


Person* new_Person(const char* name0, int age0, address* street_address0, const char* phoneNumber0) {
Person*pr = new Person ();
pr->name = name0;
pr->age = age0;
pr->street_address = street_address0;
pr->phoneNumber = phoneNumber0;
return pr;
}

//Contract: zipCheck: address -> boolean
//Purpose: To see if a given address is in the 84606 zip code
bool zipCheck (address* a) {
//Template: a a->street a->city a->state a->phoneNumber

/*Test 1  
	street = "24 7 Always online Ave"
	city = "Provo"
	state = "Utah"
	zip = 84604
	ans = True
return = True
*/

/*Test 2
	street = "4345 Saddlehorn Dr"
	city = "Eagle Mountain"
	state = "Utah"
	zip = 84005
	ans = False
return False
*/

/*Generalzie Tests 1 and 2
	This function must compare the given address's zip to the constant zip of 84604 so the return must include a->zip
*/
return a->zip ==84604;
}




//Contract: provoSeniorHuh: Person -> boolean
//Purpose:  To see if a Person is both 65 or older and lives in Provo
bool provoSeniorHuh (Person* pr) {
//Template: pr pr->name pr->age pr->street_address pr->phoneNumber

/*Test 1
	provoSeniorHuh(Jay)
		name = "Jay McCarthy"
		age = 35  just a guess not that you look 35 or anything
		address = 24 7 Always online Ave, Provo, Utah, 84604
		phoneNumber = 801-111-1111
		ans = False and True
	return False
*/

/*Test 2
	provoSeniorHuh(Gramps)
	name = "Gran Pappy"
	age = 98
	address = #4 Privot Dr, Provo, Utah, 84604
	phoneNumber = 801-777-7777
	and = True and True
return True
*/

/*Generalize Tests 1 and 2
	In order to generalize the two tessts you must compare the age of a given person to 65 and see call zipCheck to see if they are in the right zip code
*/
return (pr->age >=65) && zipCheck(pr->street_address);
}

int main () {
Person* Jay = new_Person("Jay McCarthy", 35, new_address("24 7 Always online Ave", "Provo", "Utah", 84604), "801-11-1111");
Person* Kevin = new_Person ("Kevin DeVocht", 27, new_address("4345 Saddlehorn Dr", "Eagle Mountain", "Utah", 84005), "801-234-9292");
Person* Gramps = new_Person ("Gran Pappy", 98, new_address("#4 Privot Dr", "Provo", "Utah", 84604), "801-777-7777");

printf("The answer is %s but it should be %s\n", booleanToString(zipCheck(Jay->street_address)), "true");
printf("The answer is %s but it should be %s\n", booleanToString(zipCheck(Kevin->street_address)), "false");
printf("The answer is %s but it should be %s\n", booleanToString(zipCheck(Gramps->street_address)), "true");
printf("The answer is %s but it should be %s\n", booleanToString(provoSeniorHuh(Jay)), "false");
printf("The answer is %s but it should be %s\n", booleanToString(provoSeniorHuh(Kevin)), "false");
printf("The answer is %s but it should be %s\n", booleanToString(provoSeniorHuh(Gramps)), "true");


/*Substitution
	booleanToString(provoSeniorHuh(Kevin))
	
	booleanToString(provoSeniorHuh(new_Person ("Kevin DeVocht", 27, new_address("4345 Saddlehorn Dr", "Eagle Mountain", "Utah", 84005), "801-234-9292")))
	
	booleanToString((pr->age >=65) && zipCheck(pr->street_address));
	
	booleanToString((new_Person ("Kevin DeVocht", 27, new_address("4345 Saddlehorn Dr", "Eagle Mountain", "Utah", 84005), "801-234-9292"))->age >=65) && zipCheck(new_Person ("Kevin DeVocht", 27, new_address("4345 Saddlehorn Dr", "Eagle Mountain", "Utah", 84005), "801-234-9292"))->street_address);	
	
	booleanToString((27 >=65) && zipCheck(new_address("4345 Saddlehorn Dr", "Eagle Mountain", "Utah", 84005));
	
	booleanToString(false && (a->zip ==84604;));
	
	booleanToString(false && ( new_address("4345 Saddlehorn Dr", "Eagle Mountain", "Utah", 84005)->zip ==84604));
	
	booleanToString(false && (84005 ==84604));
		
	booleanToString(false && false);
	
	booleanToString(false);
	
	return "false"
*/
}