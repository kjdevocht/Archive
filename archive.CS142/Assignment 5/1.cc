/*Kevin DeVocht Assignment 5 Exercise 1 Write a data definition for a baseball player. Your definition should record the player’s name, 
batting average, number of home runs, number of runs batted in, and the player’s position in the field. Give two examples of baseball players. 
Finally, write a function worthAMillion that returns true if a given player has more than 20 home runs, more than 100 RBI’s, and an average above .280.
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
	new_Player (Name, bat_avg, home_run, runs_batted_in, Position)
	Name is a string
	bat_avg is a decimal
	home_runs is a whole number
	runs_batted_in is a whole number
	Position is a string
*/
	
typedef struct Player;

typedef struct Player {
	const char* Name;
	double bat_avg;
	int home_runs;
	int runs_batted_in;
	const char* Position;
};

//Contract: new_Player: string decimal number number string -> Player*
//Purpose: To create a new Player
Player* new_Player ( const char* Name0, double bat_avg0, int home_runs0, int runs_batted_in0, const char* Position0) {
//Template Name0 bat_avg0 home_runs0 runs_batted_in0 Postion0
Player* p = new Player ();
	p->Name = Name0;
	p->bat_avg = bat_avg0;
	p->home_runs = home_runs0;
	p->runs_batted_in = runs_batted_in0;
	p->Position = Position0;
/*Example
	new_Player("Doc Holiday", 0.999, 300, 500, "Catcher")
		p->Name = "Doc Holiday"
		p->bat_avg = 0.999
		p->home_runs = 300
		p->runs_batted-in = 500
		p->Position = "Catcher"
		p =("Doc Holiday", 0.999, 300, 500, "Catcher")
*/
return p;
}

//Contract: worthAMillion : string decimal number number string -> boolean
//Purpose: To see if a given player's stats meet a given standard
bool worthAMillion (Player* p) {
//Template: p, p->bat_avg, p->home_runs, p->runs_batted_in

if ((p->home_runs > 20) && (p->runs_batted_in >100) && (p->bat_avg > 0.280)) { //Obvious formula no need to generalize
/*Test 1
	worthAMillion(Butch)
		p->home_runs = 78
		p->runs_batted_in = 120
		p->bat_avg = 0.456
		ans = true
*/
return true;
}

else {
/*Test 2
	worthAMillion(Sundance)
		p->home_runs = 20
		p->runs_batted_in = 101
		p->bat_avg = 0.199
		ans = false  
		Cause Butch was the better man
*/
return false;
}

}

int main () {
Player* Butch = new_Player("Butch Cassidy", 0.456, 78, 120, "Pitcher");
Player* Sundance = new_Player("Sundance Kid", 0.199, 20, 101, "Third Base");
Player* Doc_Holiday = new_Player("Doc Holiday", 0.999, 300, 500, "Catcher");

printf("The answer is %s, %f, %d, %d, %s\nbut should be %s, %f, %d, %d, %s\n",
	Butch-> Name, Butch-> bat_avg, 
	Butch-> home_runs, Butch-> runs_batted_in,
	Butch-> Position, "Butch Cassidy", 0.456, 78, 120, "Pitcher");
	
printf("The answer is %s, %f, %d, %d, %s\nbut should be %s, %f, %d, %d, %s\n",
	Sundance-> Name, Sundance-> bat_avg, 
	Sundance-> home_runs, Sundance-> runs_batted_in,
	Sundance-> Position, "Sundance Kid", 0.199, 20, 101, "Third Base");

	
printf("The answer is %s, but it should be %s\n", booleanToString(worthAMillion(Butch)), "true");
printf("The answer is %s, but it should be %s\n", booleanToString(worthAMillion(Sundance)), "false");
printf("The answer is %s, but it should be %s\n", booleanToString(worthAMillion(Doc_Holiday)), "true");

/*Substitution
	booleanToString(worthAMillion(Sundance))
	booleanToString(worthAMillion(Sundance(new_Player("Sundance Kid", 0.199, 20, 101, "Third Base"))))
	booleanToString(worthAMillion(new_Player(Name0 bat_avg0 home_runs0 runs_batted_in0 Postion0)))
												Player* p = new Player
												p->Name = Name0;
												p->bat_avg = bat_avg0;
												p->home_runs = home_runs0;
												p->runs_batted_in = runs_batted_in0;
												p->Position = Position0;
	booleanToString(worthAMillion(new_Player(Name0 bat_avg0 home_runs0 runs_batted_in0 Postion0)))
												p->Name = "Sundance Kid";
												p->bat_avg = 0.199;
												p->home_runs = 20;
												p->runs_batted_in = 101;
												p->Position = "Third Base";
	booleanToString(worthAMillion)	
								if ((p->home_runs > 20) && (p->runs_batted_in >100) && (p->bat_avg > 0.280)) {
								return true;}
								else {
								return false;}	
	booleanToString(worthAMillion)	
								if ((20 > 20) && (101 >100) && (0.199 > 0.280)) {
								return true;}
								else {
								return false;}								
	booleanToString(worthAMillion)	
								if ((False) && (True) && (False)) {
								return true;}
								else {
								return false;}	
	booleanToString(worthAMillion)	
								if (False) {
								return true;}
								else {
								return false;}
	booleanToString(worthAMillion)	
								return false;}
			 
	booleanToString(false)		 
					if ( it ) {
					return "true";
					} else {
					return "false";	
	booleanToString(false)		 
					if ( false ) {
					return "true";
					} else {
					return "false";	
	booleanToString(false)		 
					return "false";						
*/	
}