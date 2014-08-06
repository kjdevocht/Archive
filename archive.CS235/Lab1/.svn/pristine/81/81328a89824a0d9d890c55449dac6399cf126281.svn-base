/*GPA.cpp Kevin DeVocht Lab 1*/



#include <fstream>
#include <vector>
#include <algorithm>
#include <string.h>


using std::endl;
using std::string;
using std::vector;
using std::ifstream;
using std::ofstream;


#include "student.h"
#include "Grades.h"




int main(int argc, char *argv[])
{
	vector<Student> student_dir;
	vector<Grades> grade_dir;
	vector<Student> printout_Student;
	vector<Grades> test;
	
	string gotline;	//The string that the input writes to
	string Name;	//Variable used to store the name for creating a new student
	string ID;		//Variable used to store the ID for creating a new student
	string classID;
	string studentID;
	string letterGrade;
	size_t student_Check = 0;
	size_t grades_Check = 0;
	size_t test_Check = 0;
	size_t query_Check = 0;
	int duplicate_Check = 0;
	ofstream out;
	ifstream in;
	

	
	in.open(argv[1]);	//Open the second item on the command line for streaming
	
	/*There are four lines of information for each student.  The first line is the Student ID which we want
	The second line is the Student name, which we want.  The third and forth lines contain the address and phone number,
	which we don't need.  So store gotline into ID first then get line store gotline in Name the getline 2 more times*/
	while (getline(in, gotline))
	{
      if (student_dir.empty())
		{
			ID = gotline;
			getline(in, gotline);
			Name = gotline;
			Student newStudent = Student(Name, ID, 0.0, 0.0);
			student_dir.push_back(newStudent);
			getline(in, gotline);
			getline(in, gotline);
		}
		else
		{
			while (student_Check < student_dir.size())
			{
				if (strcmp(student_dir.at(student_Check).ID.c_str(), gotline.c_str()) == 0)
				{
					duplicate_Check++;
					student_Check++;
				}
				else
				{
					student_Check++;
				}
			}
			student_Check = 0;
			if (duplicate_Check == 0)
			{
				ID = gotline;
				getline(in, gotline);
				Name = gotline;
				Student newStudent = Student(Name, ID, 0.0, 0.0);
				student_dir.push_back(newStudent);
				getline(in, gotline);
				getline(in, gotline);
			}
			else
			{
				getline(in, gotline);
				getline(in, gotline);
				getline(in, gotline);
				duplicate_Check = 0;
			}
				
		}
	}
	in.close();


	in.open(argv[2]);	//Open the third item on the command line for streaming
	while (getline(in, gotline))
	{
      classID = gotline;
	  getline(in, gotline);
	  studentID = gotline;
	  getline(in, gotline);
	  letterGrade = gotline;
	  Grades newGrade = Grades(classID, studentID, letterGrade);
	  grade_dir.push_back(newGrade);
	}
	
	in.close();	

		
	in.open(argv[3]);	//Open the fourth item on the command line for streaming
	while (getline(in, gotline))
	{

		student_Check = 0;
		duplicate_Check = 0;
		query_Check = 0;
		
		while (query_Check < student_dir.size())
		{
			if (strcmp(student_dir.at(query_Check).ID.c_str(), gotline.c_str()) == 0)
			{
			   	printout_Student.push_back(student_dir.at(query_Check));		
                query_Check++;				
			}
			else
			{
				query_Check++;
			}
		}
	}
	in.close();	
	
	test_Check = 0;
	grades_Check = 0;
	while(test_Check < printout_Student.size())
	{
		while(grades_Check< grade_dir.size())
		{
			if(strcmp(printout_Student.at(test_Check).ID.c_str(), grade_dir.at(grades_Check).studentID.c_str()) == 0)
			{
				printout_Student.at(test_Check).setGrades(grade_dir.at(grades_Check).grades);
				grades_Check++;
			}
			else
			{
				grades_Check++;
			}
		}
		grades_Check = 0;
		test_Check++;
	}

	
	
	test_Check = 0;
	while(test_Check <printout_Student.size())
	{
		printout_Student.at(test_Check).setGPA();
		test_Check++;
	}


	
	
	out.open(argv[4]);
		for(size_t i =0; i<printout_Student.size(); i++)
		out << printout_Student.at(i).toString(printout_Student.at(i).GPA)<<endl;
		
		for(size_t i =0; i<printout_Student.size(); i++)
		printf("%s %f %s\n", printout_Student.at(i).ID.c_str(), printout_Student.at(i).GPA, printout_Student.at(i).name.c_str());
		
	out.close();
}
