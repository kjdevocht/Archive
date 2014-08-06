/*lab4.cpp Kevin DeVocht lab4*/

#include <map>
#include <set>
#include <list>
#include <fstream>
#include <vector>
#include <algorithm>
#include <string>
#include <sstream>
#include<iostream>
#include <iomanip>
#include <ctype.h>
#include <stdio.h>
#include <math.h>
#include "student.h"

using namespace std;

//Contract lower_case: string->string
//Purpose: to force a string to lower case for easy comparison
string lower_case(string gotline)
{
	for (size_t str_length = 0; str_length<gotline.length(); str_length++)
	{
		gotline[str_length] = tolower(gotline[str_length]);
	}
return gotline;
}



//Contract: set_query: char*->vector<student>
//Purpose: To readn in a list of Student ID's to query
vector<student> set_query(char* the_file)
//Template: the_file, in
{
  ifstream in;
  vector <student> query;
  string gotline;
  
  in.open(the_file);
  while (getline(in, gotline))
    {
      student newStudent = student(gotline, "");
      query.push_back(newStudent);
      gotline ="";
    }
  in.close();
  return query;
}


//Contract: set_student_dir_vector: char*->vector<student>
//Purpose: To read in a list of students in a file and put them into a vector
vector<student> set_student_dir_vector(char* the_file)
//Template: the_file, in, student_dir, gotline, student_ID_temp, student_name_temp
{
	ifstream in;
	vector <student> student_dir;
	string gotline;
	string student_ID_temp;
	string student_name_temp;
	
	
	in.open(the_file);	
	while (getline(in, gotline))
	{
		student_ID_temp = gotline;
		getline(in,gotline);
		gotline = lower_case(gotline);
		student_name_temp = gotline;
		student newStudent = student(student_ID_temp, student_name_temp);
		student_dir.push_back(newStudent);
		getline(in,gotline);
		getline(in,gotline);
		gotline = "";
	}
	in.close();
	return student_dir;

}


//Contract: selection_sort: vector<student>->nothing
//Purpose: To sort a vector of students using the selection sort metthod
void selection_sort(vector<student> student_dir)
//Template: student_dir, temp, min
{
  student min("","");

  student::reset();
  for(size_t i =0; i <student_dir.size(); i++)
    {
      min =student_dir.at(i);
      size_t min_loc = 0;
      for(size_t j = i+1; j<(student_dir.size()); j++)
        {
          if(student_dir.at(j) < min)
            {
              min = student_dir.at(j);
              min_loc = j;
            }
        }
      if (i !=student_dir.size()-1)
        {
          swap(student_dir.at(min_loc),(student_dir.at(i)));
        }
    }
}

//Contract: insertion_sort: vector<student> ->nothing
//Purpose: to sort a vector of students using the insertion method
void insertion_sort(vector<student> student_dir)
//Template: student_dir
{
	size_t j = 0;
	student temp("","");
	
	student::reset();
	
	for (size_t i = 1; i < student_dir.size(); i++)
	{
		j = i;
		temp = student_dir.at(j);
		while (j >0 && student_dir.at(j-1) > temp)
		{
			student_dir.at(j) = student_dir.at(j-1);
			j--;
		}
		student_dir.at(j)  = temp;
	}
}

//Contract: merge: vector<student> vector<student> -> vector<student>
//Purpose: to merge a merge_sorted vector
vector<student> merge(vector<student> left, vector<student> right)
{
	vector<student> result;
	while (!left.empty()  && !right.empty() )
	{

			if (left.at(0) < right.at(0))
			{
				result.push_back(left.at(0));
				left.erase(left.begin());
			}
			else
			{
				result.push_back(right.at(0));
				right.erase(right.begin());
			}
    }
    while (!left.empty())
      {
			result.push_back(left.at(0));
			left.erase(left.begin());
		}
      while(!right.empty())
		{
			result.push_back(right.at(0));
			right.erase(right.begin());
		}
	return result;
}


//Contract: merge_sort: vector<student> -> nothing
//Purpose: to sort a vector of students using the merge sort method
vector<student> merge_sort(vector<student> current_list)
{
	vector<student> right;
	vector<student> left;
	if(current_list.size() <= 1)
	{
		return current_list;
	}
	else
      {
	size_t  middle = current_list.size()/2;
	for(size_t i = 0; i < middle; i++)
	{
		left.push_back(current_list.at(i));
	}

	for(size_t i = middle; i < current_list.size(); i++)
	{
		right.push_back(current_list.at(i));
	}

	left = merge_sort(left);
	right = merge_sort(right);



	current_list = merge(left,right);

	return current_list;
      }
}


vector<student> concat(vector<student> small, vector<student> equal, vector<student> large,vector<student> & student_dir)
    {
     
 
      for(size_t i = 0;  i<small.size(); i++)
        {
          student_dir.push_back(small.at(i));
         }

      for(size_t i = 0;  i<equal.size(); i++)
        {
        student_dir.push_back(equal.at(i));
        }

      for(size_t i = 0;  i<large.size(); i++)
        {
        student_dir.push_back(large.at(i));
        }
      return student_dir;
    }

//Contract: quick_sort: vector<student>+vector<student>+vector<student> ->nothing
//Purpose: To sort a list of students using the quick sort method
vector<student> quick_sort (vector <student> student_dir)
//Template: student_dir
{
 
  vector<student> small;
  vector<student> equal;
  vector<student> large;

  if (student_dir.size() >1)
    {
        size_t pivot = student_dir.size()/2;

    for(size_t i = 0; i<student_dir.size(); i++)
      {

        if(student_dir.at(i) < student_dir.at(pivot))
          {
 
            small.push_back(student_dir.at(i));
          }
            else if(student_dir.at(i) == student_dir.at(pivot))
              {

                equal.push_back(student_dir.at(i));
              }
            else
              {
 
                large.push_back(student_dir.at(i));
              }
      }
   

  quick_sort(small);
  quick_sort(large);

return concat(small, equal, large, student_dir);
    }
  else
    {
      return student_dir;
    }
}




//Contract: linear_search:vector<string> vector<student> ->nothing
//Purpose: to search for a given student ID number
void linear_search (vector<student> query, vector<student> student_dir)
//Template: query, student_dir
{

  student::reset();
  
 

  for(size_t i = 0; i<query.size(); i++)
    {
      for(size_t j = 0; j<student_dir.size(); j++)
        {
          if(query.at(i) == student_dir.at(j))
            {
              break;
             }
        }
    }
}

//Contract: binary_search: student vector<student> -> nothing
//Purpose: To search for a given student ID using binary search
void binary_search(vector<student> query, size_t org_low, size_t org_high, vector<student> student_dir)
{

  student::reset();
  for( size_t i = 0; i<query.size(); i++)
   {
  int mid = 0;
  int low = org_low;
  int high = org_high;

  if(!student_dir.empty() )
    {
 
  while (low<=high)
    {
 
      mid = (low+high)/2;

      if (query.at(i) < student_dir.at(mid))
        {
 
          high = mid-1;
        }
      else if(query.at(i) > student_dir.at(mid))
        {
 
          low = mid+1;
 
        }
        else
          {
            break;
          }

    
    }
    }
   }
}

int main (int argc, char *argv[])
{

vector<student> student_dir;
 vector<student> half_student_dir;
 vector<student> quarter_student_dir;
 vector<student> query;
 vector<student> quarter_query;
 vector<student> half_query;
 vector<student> quarter_merge_sorted;
 vector<student> half_merge_sorted;
 vector<student> merge_sorted;
 list<student> quarter_student_list;
  list<student> half_student_list;
   list<student> student_list;
   set<student> quarter_student_set;
   set<student> half_student_set;
   set<student> student_set;
   ofstream out;
out.open(argv[3]);
student_dir = set_student_dir_vector(argv[1]);
 query = set_query(argv[2]);


 for (size_t i = 0; i<student_dir.size()/4; i++)
   {
     quarter_student_dir.push_back(student_dir.at(i));
   }

 for (size_t i = 0; i<student_dir.size()/2; i++)
   {
     half_student_dir.push_back(student_dir.at(i));
   }

 for (size_t i = 0; i<query.size()/4; i++)
   {
     quarter_query.push_back(query.at(i));
   }

 for (size_t i = 0; i<query.size()/2; i++)
   {
     half_query.push_back(query.at(i));
   }

 vector<student> quarter_select = quarter_student_dir;
 vector<student> half_select = half_student_dir;
 vector<student> full_select = student_dir;


 out<< "Selection Sort" <<endl;
selection_sort(quarter_select);
 out<<"size: "<< quarter_select.size() <<"   compares: "<< student::count<<endl;
selection_sort(half_select);
 out<<"size: "<< half_select.size() <<"   compares: "<< student::count<<endl;
selection_sort(full_select);
 out<<"size: "<< full_select.size() <<"   compares: "<< student::count<<endl;

 vector<student> quarter_insert = quarter_student_dir;
 vector<student> half_insert = half_student_dir;
 vector<student> full_insert = student_dir;



 out<<"Insertion Sort"<<endl;
insertion_sort(quarter_insert);
out<<"size: "<< quarter_insert.size() <<"   compares: "<< student::count<<endl;
insertion_sort(half_insert);
out<<"size: "<< half_insert.size() <<"   compares: "<< student::count<<endl;
insertion_sort(full_insert);
out<<"size: "<< full_insert.size() <<"   compares: "<< student::count<<endl;



 vector<student> quarter_merge = quarter_student_dir;
 vector<student> half_merge = half_student_dir;
 vector<student> full_merge = student_dir;


 out<<"Merge Sort"<<endl;
 student::reset();
quarter_merge_sorted = merge_sort(quarter_merge);
 out<<"size: "<<quarter_merge.size()<<"   compares: "<< student::count<<endl;
 student::reset();
half_merge_sorted = merge_sort(half_merge);
 out<<"size: "<<half_merge.size()<<"   compares: "<< student::count<<endl;
 student::reset();
merge_sorted = merge_sort(full_merge);
 out<<"size: "<<merge_sorted.size()<<"   compares: "<< student::count<<endl;

 
 vector<student> quarter_quick = quarter_student_dir;
 vector<student> half_quick = half_student_dir;
 vector<student> full_quick = student_dir;



 out<<"Quick Sort"<<endl;
 student::reset();
 quick_sort(quarter_quick);
 out<<"size: "<<quarter_quick.size()<<"   compares: "<< student::count<< endl;
 student::reset();
 quick_sort(half_quick);
out<<"size: "<<half_quick.size()<<"   compares: "<< student::count<< endl;
 student::reset();
 quick_sort(full_quick);
out<<"size: "<<full_quick.size()<<"   compares: "<< student::count<< endl;

 vector<student> quarter_sort = quarter_student_dir;
 vector<student> half_sort = half_student_dir;
 vector<student> full_sort = student_dir;




 out<<"std::sort"<<endl;
student::reset();
sort(quarter_sort.begin(), quarter_sort.end());
 out<<"size: "<<quarter_sort.size()<<"   compares: "<< student::count<<endl;
student::reset();
sort(half_sort.begin(), half_sort.end());
 out<<"size: "<<half_sort.size()<<"   compares: "<< student::count<<endl;
student::reset();
sort(full_sort.begin(), full_sort.end());
 out<<"size: "<<full_sort.size()<<"   compares: "<< student::count<<endl;
 


 




 for(size_t i = 0; i<quarter_student_dir.size(); i++)
 {
	quarter_student_list.push_back(quarter_student_dir.at(i));
 }
  for(size_t i = 0; i<half_student_dir.size(); i++)
 {
	half_student_list.push_back(half_student_dir.at(i));
 }
  for(size_t i = 0; i<student_dir.size(); i++)
 {
	student_list.push_back(student_dir.at(i));
 }
  out<<"std::list.sort"<<endl;
 student::reset();
 quarter_student_list.sort();
 out<<"size: "<< quarter_student_list.size()<<"   compares: "<< student::count<<endl;
 student::reset();
 half_student_list.sort();
 out<<"size: "<< half_student_list.size()<<"   compares: "<< student::count<<endl;
 student::reset();
 student_list.sort();
 out<<"size: "<< student_list.size()<<"   compares: "<< student::count<<endl;






 out<<"Linear Search"<<endl;
linear_search(quarter_query, quarter_student_dir);
 out<<"size: "<< quarter_student_dir.size()<<"   compares: "<< student::count/quarter_query.size()<<endl;
linear_search(half_query, half_student_dir);
out<<"size: "<< half_student_dir.size()<<"   compares: "<< student::count/half_query.size()<<endl;
linear_search(query, student_dir);
out<<"size: "<< student_dir.size()<<"   compares: "<< student::count/query.size()<<endl;





 out<<"Binary Search"<<endl;
binary_search(quarter_query, 0, quarter_student_dir.size()-1, quarter_merge_sorted);
 out<<"size: "<< quarter_student_dir.size()<<"   compares: "<< student::count/quarter_query.size()<<endl;
binary_search(half_query, 0, half_student_dir.size()-1,half_merge_sorted);
out<<"size: "<< half_student_dir.size()<<"   compares: "<< student::count/half_query.size()<<endl;
binary_search(query, 0, student_dir.size()-1, merge_sorted);
out<<"size: "<< student_dir.size()<<"   compares: "<< student::count/query.size()<<endl;











int average = student::count/ query.size();







out<<"std::find"<<endl;
   student::reset();
   for(size_t i =0; i<quarter_query.size(); i++)
   {
     find(quarter_student_dir.begin(), quarter_student_dir.end(), quarter_query.at(i));
   }
average =student::count/ quarter_query.size();
out<<"size: "<< quarter_student_dir.size()<<"   compares: "<< average<<endl;

   student::reset();
   for(size_t i =0; i<half_query.size(); i++)
   {
     find(half_student_dir.begin(), half_student_dir.end(), half_query.at(i));
   }
average =student::count/ half_query.size();
out<<"size: "<< half_student_dir.size()<<"   compares: "<< average<<endl;

   student::reset();
   for(size_t i =0; i<query.size(); i++)
   {
     find(student_dir.begin(), student_dir.end(), query.at(i));
   }
average =student::count/ query.size();
out<<"size: "<< student_dir.size()<<"   compares: "<< average<<endl;







for(size_t i =0; i <quarter_student_dir.size(); i++)
{
	quarter_student_set.insert(student_dir.at(i));
}

for(size_t i =0; i <half_student_dir.size(); i++)
{
	half_student_set.insert(student_dir.at(i));
}

for(size_t i =0; i <student_dir.size(); i++)
{
	student_set.insert(student_dir.at(i));
}




out<<"std::set.find"<<endl;
 student::reset();
 for(size_t i = 0; i<quarter_query.size(); i++)
   {
     quarter_student_set.find(quarter_query.at(i));
   }
 average =student::count/ quarter_query.size();
out<<"size: "<< quarter_student_set.size()<<"   compares: "<< average<<endl;
student::reset();
 for(size_t i = 0; i<half_query.size(); i++)
   {
     half_student_set.find(half_query.at(i));
   }
 average =student::count/ half_query.size();
out<<"size: "<< half_student_set.size()<<"   compares: "<< average<<endl;
student::reset();
 for(size_t i = 0; i<query.size(); i++)
   {
     student_set.find(query.at(i));
   }
average =student::count/ query.size();
out<<"size: "<< student_set.size()<<"   compares: "<< average<<endl;


 


 out.close();

}
