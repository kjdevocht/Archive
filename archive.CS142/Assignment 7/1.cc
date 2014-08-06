/*Kevin DeVocht Assignment 5 Exercise 1. Redo day 5, exercise 1 (about baseball players) with methods
*/

#include <stdio.h>
#include <string.h>


// booleanToString : boolean -> string
const char* booleanToString ( bool it ) {
  // Template: it

  // Distinguishes test 1 from test 2
  if ( it ) {
    // Test 1
    // it = true
    return "true";
  } else {
    // Test 2
    // it = false
    return "false";
  }
}


/*A Player is a
	new Player (Name, bat_avg, home_run, runs_batted_in, Position)
	Name is a string
	bat_avg is a decimal
	home_runs is a whole number
	runs_batted_in is a whole number
	Position is a string
*/
class Player{

public: 
	const char* Name;
	double bat_avg;
	int home_runs;
	int runs_batted_in;
	const char* Position;

//Contract: new Player; String decimal number number string ->Player*
Player ( const char* Name0, double bat_avg0, int home_runs0, int runs_batted_in0, const char* Position0) {
	this->Name = Name0;
	this->bat_avg = bat_avg0;
	this->home_runs = home_runs0;
	this->runs_batted_in = runs_batted_in0;
	this->Position = Position0;
}

//Contract: worthAMillion : string decimal number number string -> boolean
//Purpose: To see if a given player's stats meet a given standard
bool worthAMillion () {
//Template: this, this->bat_avg, this->home_runs, this->runs_batted_in

if ((this->home_runs > 20) && (this->runs_batted_in >100) && (this->bat_avg > 0.280)) { //Obvious formula no need to generalize
/*Test 1
	worthAMillion(Butch)
		this->home_runs = 78
		this->runs_batted_in = 120
		this->bat_avg = 0.456
		ans = true
*/
return true;
}

else {
/*Test 2
	worthAMillion(Sundance)
		this->home_runs = 20
		this->runs_batted_in = 101
		this->bat_avg = 0.199
		ans = false  
		Cause Butch was the better man
*/
return false;
}

}

};

int main () {
Player* Butch = (new Player("Butch Cassidy", 0.456, 78, 120, "Pitcher"));
Player* Sundance = (new Player("Sundance Kid", 0.199, 20, 101, "Third Base"));
Player* Doc_Holiday = (new Player("Doc Holiday", 0.999, 300, 500, "Catcher"));

printf("The answer is %s, %f, %d, %d, %s\nbut should be %s, %f, %d, %d, %s\n",
	Butch-> Name, Butch-> bat_avg, 
	Butch-> home_runs, Butch-> runs_batted_in,
	Butch-> Position, "Butch Cassidy", 0.456, 78, 120, "Pitcher");
	
printf("The answer is %s, %f, %d, %d, %s\nbut should be %s, %f, %d, %d, %s\n",
	Sundance-> Name, Sundance-> bat_avg, 
	Sundance-> home_runs, Sundance-> runs_batted_in,
	Sundance-> Position, "Sundance Kid", 0.199, 20, 101, "Third Base");

	
printf("The answer is %s, but it should be %s\n", booleanToString(Butch->worthAMillion()), "true");
printf("The answer is %s, but it should be %s\n", booleanToString(Sundance->worthAMillion()), "false");
printf("The answer is %s, but it should be %s\n", booleanToString(Doc_Holiday->worthAMillion()), "true");

/*Substitution
	booleanToString(Doc_Holiday->worthAMillion()
	booleanToString((new Player("Doc Holiday", 0.999, 300, 500, "Catcher"))->worthAMillion()
	booleanToString((new Player("Doc Holiday", 0.999, 300, 500, "Catcher"))->if ((this->home_runs > 20) && (this->runs_batted_in >100) && (this->bat_avg > 0.280)) {return true;} else {return false;}
	booleanToString((new Player("Doc Holiday", 0.999, 300, 500, "Catcher"))->if ((this->300 > 20) && (this->500 >100) && (this->0.999 > 0.280)) {return true;} else {return false;}
	booleanToString((new Player("Doc Holiday", 0.999, 300, 500, "Catcher"))->if ((true) && (true) && (true)) {return true;} else {return false;}
	booleanToString((new Player("Doc Holiday", 0.999, 300, 500, "Catcher"))->if (true) {return true;} else {return false;}
	if ( it ) {return "true";} else {return "false";}->true
	if ( true ) {return "true";} else {return "false";}
	return "true";
*/	
}