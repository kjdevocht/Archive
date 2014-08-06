/*Student.cpp Kevin DeVocht Lab 1*/

#include <string.h>
#include <sstream>
#include <iomanip>



using namespace std;


void Student::setName(string name)
{
	this->name = name;
}

void Student::setID(string ID)
{
	this->ID = ID;
}


void Student::setGPA()
{
	if (numOfGrades == 0.0)
	{
		this->GPA = 0.0;
	}
	else
	{
		this->GPA = this->GPA/this->numOfGrades;
	}
}

void Student::setGrades(string Grade)
{
	if (strcmp(Grade.c_str(), "A") == 0)
	{
		this->GPA += 4.0;
	}
	else if (strcmp(Grade.c_str(), "A-") == 0)
	{
		this->GPA +=3.7;
	}
	else if (strcmp(Grade.c_str(), "B+") == 0)
	{
		this->GPA +=3.4;
	}
	else if (strcmp(Grade.c_str(), "B") == 0)
	{
		this->GPA +=3.0;
	}
	else if (strcmp(Grade.c_str(), "B-") == 0)
	{
		this->GPA +=2.7;
	}
	else if (strcmp(Grade.c_str(), "C+") == 0)
	{
		this->GPA +=2.4;
	}
	else if (strcmp(Grade.c_str(), "C") == 0)
	{
		this->GPA +=2.0;
	}
	else if (strcmp(Grade.c_str(), "C-") == 0)
	{
		this->GPA +=1.7;
	}
	else if (strcmp(Grade.c_str(), "D+") == 0)
	{
		this->GPA +=1.4;
	}
	else if (strcmp(Grade.c_str(), "D") == 0)
	{
		this->GPA +=1.0;
	}
	else if (strcmp(Grade.c_str(), "D-") == 0)
	{
		this->GPA +=0.7;
	}
	else if (strcmp(Grade.c_str(), "E") == 0)
	{
		this->GPA +=0.0;
	}
	
	this->numOfGrades++;
}


string Student::toString(double Grade) const
{
	string string_grades = "";
	std::stringstream streamin(stringstream::out);
	streamin<<fixed<<setprecision(2) << Grade;
	string_grades = streamin.str();
	return ID + "    " + string_grades + "    " + name;
}
