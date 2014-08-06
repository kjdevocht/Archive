/*Kevin DeVocht assignment 18 exercise 1. Write the function theRemoveEntry, 
which takes a name and modifies the address book by removing the associated 
entries from the book. (Hint: Write without mutation first!)
*/

#include <stdio.h>
#include <math.h>
#include <string.h>



// An Entry is a
//  new Entry(name, number)
// where
//  name is a string
//  number is a string
class Entry {
	public:
		const char* name;
		const char* number;

		Entry (const char* name0, const char* number0) 
		{
			this->name = name0;
			this->number = number0;
		}

		int show () 
		{
			printf("%s:", 
				   this->name);
			return 0;
		}
		
		//isThisYourNameHuh: Entry string->bool
		//Purpose: to see if the given entry is the right person
		bool isThisYourNameHuh(const char* target) 
		//Template: name, number, target
		{
		//Pretty straight forward no need for a test
			return strcmp(this->name, target) == 0;
		}
};



// A ListofEntry is either
//  EmptyLOE
//  OneLOE
class ListOfEntry {
public:
	virtual int show () = 0;
  
	//RemoveEntry: string ListOfEntry->ListOfEntry
	//Purpose: To remove an entry from a list of entries
	virtual ListOfEntry* RemoveEntry(const char* name) = 0;
};

// An EmptyLOE is a
//  new EmptyLOE()
// where
class EmptyLOE : public ListOfEntry {
public:

	EmptyLOE () {}

	int show () 
	{
	printf("!");
	return 0;
	}

	//RemoveEntry: string ListOfEntry->ListOfEntry
	//Purpose: To remove an entry from a list of entries
	ListOfEntry* RemoveEntry(const char* name)
	{
		return new EmptyLOE();
	}
};

// A OneLOE is a
//  new OneLOE (first, rest)
// where
//  first is a int
//  rest is a ListOfEntry
class OneLOE : public ListOfEntry {
public:
  Entry* first;
  ListOfEntry* rest;

  OneLOE( Entry* first0, ListOfEntry* rest0 ) 
  {
    this->first = first0;
    this->rest = rest0;
  }

  int show () 
  {
    this->first->show();
    this->rest->show();
    return 0;
  }

	//RemoveEntry: ListOfEntry string->ListOfEntry
	//Purpose: To remove an entry from a list of entries
	ListOfEntry* RemoveEntry(const char* theX)
	//Template: theX
	{

		if (this->first->isThisYourNameHuh(theX))
		/*Test 1
			LBB->RemoveEntry("Kevin")
				theX = "Kevin"
				LBB = Kevin:!
				ans = !
			return this->rest->RemoveEntry(theX);
		*/
		{
			return this->rest->RemoveEntry(theX);
		}
		else
		/*Test 2
			LBB->RemoveEntry("Kevin")
				theX = "Kevin"
				LBB = snow:Kevin:!
				ans = snow:!
			return new OneLOE(this->first, this->rest->RemoveEntry(theX));
		*/
		/*Distinguish Tests 1 and 2
			if first is the name we want to return
			don't include it in the List of entries
			otherwise and first to the list and keep
			checking
		
		*/
		{
			return new OneLOE(this->first, this->rest->RemoveEntry(theX));
		}
	}
};

//Possible Entries
Entry* lightning = new Entry("Lightning", "777-123-1452");
Entry* snow = new Entry("Snow", "777-542-1452");
Entry* King_Truffle = new Entry("Truffle", "132-587-8958");
Entry* Kevin = new Entry("Kevin", "848-584-8597");
 
// The starting LBB
ListOfEntry* LBB = new OneLOE(Kevin, new OneLOE(King_Truffle, new OneLOE(snow, new OneLOE(lightning, new EmptyLOE()))));

//theRemoveEntry: name->nothing
//Purpose: To remove a name from my LBB
void theRemoveEntry(const char* name)
//Template: name, LBB
{
/*Test 1
	theRemoveEntry("Kevin")
		name = "Kevin"
		LBB = Kevin:!
		ans = !
	return;
*/

/*Test 2
	theRemoveEntry("Kevin")
		name = "Kevin"
		LBB = snow:Kevin:!
		ans = snow:!
	return;
*/

/*Generalize Tests 1 and 2
	we know that the return is nothing because this is
	a void non-function.  But we also know that we want
	LBB to be changed so we know that inside this
	non-function we want to mutate LBB
*/
	LBB = LBB->RemoveEntry(name);
	return;
}



// theShow : nothing -> nothing
void theShow () {
  LBB->show();
  return ;
}

// main : -> number
int main () {
	theRemoveEntry("Snow");
	printf("The answer is "); 
	theShow();
	printf(", but should be %s\n",
		 "Kevin:Truffle:Lightning:!");
		 
		 
	theRemoveEntry("Lightning");
	printf("The answer is "); 
	theShow();
	printf(", but should be %s\n",
		 "Kevin:Truffle:!");
		 
	theRemoveEntry("Jay");
	printf("The answer is "); 
	theShow();
	printf(", but should be %s\n",
		 "Kevin:Truffle:!");

  return 0;
  
  //Substitution:  Ran out of Time!
}