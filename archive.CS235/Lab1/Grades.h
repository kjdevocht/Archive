/*Grade Header file Kevin DeVocht Lab 1*/




#include <ostream>
#include <string>

using std::string;
using std::ostream;

class Grades
{
	public:
		string classID;
		string studentID;
		string grades;
	

		
	Grades(string classID0, string studentID0, string grades0) : classID(classID0), studentID(studentID0), grades(grades0)
	{}
};

