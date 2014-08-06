/*student.h Kevin DeVocht lab4*/


#include <string>
#include <vector>


using namespace std;

class student
{

	public:
		string ID;
		string name;
		static int count;
		
	student(string ID0, string name0) : ID(ID0), name(name0)
	{}

    bool operator < (const student & s) const ;
    bool operator == (const student & s) const;
    bool operator > (const student & s) const;
    static void reset();
};

ostream& operator <<(ostream&, const student& item);
