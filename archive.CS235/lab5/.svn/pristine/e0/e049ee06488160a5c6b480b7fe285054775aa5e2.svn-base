/*student.h Kevin DeVocht lab4*/


#include <string>
#include <vector>


using namespace std;

class student
{

	public:
		string ID;

		static int count;
		
	student()
	{}

    bool operator < (const student & s) const ;
    bool operator == (const student & s) const;
    bool operator > (const student & s) const;
    bool operator != (const student & s) const;
    static void reset();
};

ostream &operator<<(ostream &out, student s);
