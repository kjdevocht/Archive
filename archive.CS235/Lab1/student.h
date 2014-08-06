/*Student Header file Kevin DeVocht Lab 1*/



#include <ostream>
#include <string>


using std::string;
using std::ostream;

class Student
{
	public:
		string name;
		string ID;
		double GPA;
		double numOfGrades;

		
	Student(string name0, string ID0, double GPA0, double numOfGrades0) : name(name0), ID(ID0), GPA(GPA0), numOfGrades(numOfGrades0)
	{}
	
	string getName() const {return name;}
	
	string getID () const { return ID;}
	
	double getNumOfGrades () const { return numOfGrades;}
	
	double getGPA () const { return GPA;}
	
	void setName(string name);
	
	void setID(string ID);
	
	void setGrades(string Grade);
	
	void setGPA();
	
	string toString(double Grade) const;
};

ostream& operator<< (ostream& out, const Student& item);