/*search.cpp -lab5 - Kevin DeVocht*/


#include <fstream>
#include <algorithm>
#include <string>
#include <sstream>
#include <iostream>
#include <iomanip>
#include <ctype.h>
#include <stdio.h>
#include <math.h>
#include "search.h"



using namespace std;





vector<student> process_search (char *student_list)
{
	string s;
	ifstream in;
	string id;
	vector<student>full_list;

	
	in.open(student_list);
	while(getline(in, s))
	{
		id = s;
		getline(in, s);
		getline(in, s);
		getline(in, s);
		student new_student = student();
		new_student.ID = id;
		full_list.push_back(new_student);
		s ="";
	}
	in.close();
	return full_list;
}

void process_query(char *query_file, char *output, vector<student> full_list)
{
	string s;
	ifstream in;
	ofstream out;
	vector<student> query_vector;
	vector<student> query_vector_half;
	vector<student> query_vector_quarter;
	vector<student> quarter_list;
	vector<student>half_list;
	student query_student = student();
	LinkedArrayList<student>* the_list_quarter = NULL;
	LinkedArrayList<student>* the_list_half = NULL;
	LinkedArrayList<student>* the_list = NULL;
	
	
	in.open(query_file);
	while(in >> s)
	{
		query_student.ID = s;
		query_vector.push_back(query_student);
	}
	in.close();
	
	the_list = new LinkedArrayList<student>(4);
	for (size_t i =0; i<full_list.size(); i++)
	{
		the_list->insert(i, full_list.at(i));
	}


	the_list_half = new LinkedArrayList<student>(4);
	for(size_t i = 0; i<full_list.size()/2; i++)
	{
		the_list_half->insert(i, full_list.at(i));
	}
	

	
	the_list_quarter = new LinkedArrayList<student>(4);
	for(size_t i = 0; i<full_list.size()/4; i++)
	{
		the_list_quarter->insert(i, full_list.at(i));
	}



 for (size_t i = 0; i<query_vector.size()/4; i++)
   {
     query_vector_quarter.push_back(query_vector.at(i));
   }

 for (size_t i = 0; i<query_vector.size()/2; i++)
   {
     query_vector_half.push_back(query_vector.at(i));
   }
  

student::reset();
 for (size_t i = 0; i<query_vector_quarter.size(); i++)
   {
     the_list_quarter->find(query_vector_quarter.at(i));
   }
   out.open(output);
   out<<"Linked Array List find"<<endl;
   out<<"size: "<<query_vector_quarter.size()<<"    compares: "<<student::count/query_vector_quarter.size()<<endl;
   

student::reset();
 for (size_t i = 0; i<query_vector_half.size(); i++)
   {
     the_list_half->find(query_vector_half.at(i));
   }
out<<"size: "<<query_vector_half.size()<<"    compares: "<<student::count/query_vector_half.size()<<endl;
	
	
	
student::reset();
for (size_t i = 0; i<query_vector.size(); i++)
   {
     the_list->find(query_vector.at(i));
   }	
	out<<"size: "<<query_vector.size()<<"    compares: "<<student::count/query_vector.size()<<endl;
	out.close();	
	
	delete the_list;
	delete the_list_half;
	delete the_list_quarter;
}
