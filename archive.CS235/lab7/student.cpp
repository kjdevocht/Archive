/*student.cpp Kevin DeVocht lab4*/
/*
#include <map>
#include <set>
#include <fstream>
#include <vector>
#include <algorithm>
#include <string>
#include <sstream>
#include <iomanip>
#include <ctype.h>
#include <stdio.h>
#include <math.h>
#include "student.h"

using namespace std;

int student::count;

void student::reset()
{
  count = 0;
}

bool student::operator < (const student & s) const
{
  count++;
  return ID<s.ID;
}

bool student::operator == (const student & s) const
{
  count++;
  return ID == s.ID;
}


bool student::operator > (const student & s) const
{
  count++;
  return ID>s.ID;
}


bool student::operator != (const student & s) const
{
	count++;
  return ID != s.ID;
}

ostream &operator<<(ostream &out, student s)
{
	out<<s.ID;
	return out;
}
*/