/*Grade Header file Kevin DeVocht Lab 1*/




#include <iostream>
#include <string>

using std::string;
using std::ostream;

class Grades
{
	public:
		string classID;
		string studentID;
		string grades;
	

		
	Grade(string classID0, string studentID0, string grades0) : classID(classID0), studentID(studentID0), grades(grades0)
	{}
	
	string getClassID() const {return classID;}
	
	string getStudentID() const {return studentID;}
	
	string getGrades() const {return grades;}
	
	
	void setClassID(string classID);
	
	void setStudentID(string studentID);
	
	void setGrades (string Grade);
	
	void setGPA();
	
};

ostream& operator<< (ostream& out, const Grade& item);