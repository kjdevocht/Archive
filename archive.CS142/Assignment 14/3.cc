/*Kevin DeVocht assignment 14 exercise 3. Develop a data definition for a binary search tree. 
A binary search tree is either empty or a node that is a number (the "key"), a string (the "value"), 
and two binary search trees—"left" and "right". In the left tree, every node’s key is less than its parent. 
In the right tree, every node’s key is greater than its parent. This means the same key cannot appear in 
the tree multiple times. You do not need to enforce this property, but you must make use of it.
Develop a function, searchBst, that takes a binary search tree, a key, and a missing string. 
It should produce the value associated with the key if the key is in the tree or the missing string.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>

//A BST is either
// an empty
// or a node
class BST 
{
	public:
	
	//searchBst: BST, int, string-> string
	//Purpose: To so see if a given number is in the tree and return the string value associated with it.
	virtual const char* searchBst(int theKey, const char* missingString) = 0;

};


//An empty is a 
// new empty
//where there is nothing because it is empty
class empty : public BST
{
	public:
		 empty () 
		 {
		 }
		 
	//searchBst: empty, int, string-> string
	//Purpose: To so see if a given number is in the tree and return the string value associated with it.
	const char* searchBst(int theKey, const char* missingString)
	//Template: this, theKey, missingString
	{
	/*Test 1
		nomore->searchBst(3, "Frank")
			this = !
			theKey = 3
			missingString = "Frank"
			ans = !
		return missingString
	*/
	return missingString;
	
	}
	
};

//A node is a
// new node(key, value,left, right)
//where
//key is an int
//value is a string
//left is a BST
//right is a BST
class node : public BST
{
	public:
		int key;
		const char* value;
		BST* left;
		BST* right;
		
	node(int key0, const char* value0, BST* left0, BST* right0)
	{
		this->key = key0;
		this->value = value0;
		this->left = left0;
		this->right = right0;
	}
	
	
	//searchBst: node, int, string-> string
	//Purpose: To so see if a given number is in the tree and return the string value associated with it.
	const char* searchBst(int theKey, const char* missingString)
	//Template: this, this->key, this->value, this->left, this->right, theKey, missingString
	
	{
		if (this->key == theKey)
		{
		/*Test 1 
			Mary->searchBst(3, "Frank")
				this = Mary, nomore, nomore
				this->key = 3
				this->value = "Mary"
				theKey = 3
				missingString = "Frank"
				ans = "Mary"
			return this->value
		*/
		return this->value;
		}
		
		else
		{
			if (this->key < theKey)
			{
			return this->left->searchBst(theKey, missingString);
			}
		
		/*Test 2
			Jim->searchBst(3, "Frank")
				this = Jim, Frank, Mary
				this->key = 6
				this->value = "Jim"
				theKey = 3
				missingString = "Frank"
				ans = "Mary"
			return this->right->searchBst(3, "Frank")
		*/
		
		/*Test 3
			Frank->searchBst(3, "Jim")
				this = Frank, nomore, nomore
				this->key = 7
				this->value = "Frank"
				theKey = 3
				missingString = "Jim"
				ans = "Jim"
			return this->left->searchBst(3, "Jim")
		*/
		
		/*Generalize Tests 1,2 and 3
			We don't need to look any deeper so we just need to compare 
			this->key to theKey if they don't match return this->SAF(theKey, missingString), which compares this->left and if it does not match than.  So we know that we need an if statement.   
		*/
			else
			{
			return this->right->searchBst(theKey, missingString);
			}
		}
	}
};

int main ()
{
BST* nomore = new empty();



BST* Martha = new node(2, "Martha", nomore, nomore);
BST* Bill = new node(8, "Bill", nomore, nomore);
BST* Mary = new node(3, "Mary", nomore, nomore);
BST* Frank = new node(7, "Frank", nomore, nomore);
BST* Patrica = new node(4, "Patrica", Bill, Martha);
BST* Jim = new node(6, "Jim", Frank, Mary);
BST* Kevin = new node(5, "Kevin", Jim, Patrica);

printf ( "The answer is %s, but should be %s\n", Mary->searchBst(3, "Frank"), "Mary");

printf ( "The answer is %s, but should be %s\n", Jim->searchBst(3, "Frank"), "Mary");

printf ( "The answer is %s, but should be %s\n", Frank->searchBst(3, "Jim"), "Jim");



/* Substitution
	1. Frank->searchBst(3, "Jim")
	
	2. new node(7, "Frank", nomore, nomore)->searchBst(3, "Jim")

	3. if (this->key == theKey){return this->value;} else {if (this->key < theKey){return this->left->searchBst(theKey, missingString);} else {return this->right->searchBst(theKey, missingString);}}

	4. if (new node(7, "Frank", nomore, nomore)->key == 3){return new node(7, "Frank", nomore, nomore)->value;} else {if (new node(7, "Frank", nomore, nomore)->key < 3){return new node(7, "Frank", nomore, nomore)->left->searchBst(3, "Jim");} else {return new node(7, "Frank", nomore, nomore)->right->searchBst(3, "Jim");}}

	5. if (7 == 3){return "Frank";} else {if (7 < 3){return nomore->searchBst(3, "Jim");} else {return nomore->searchBst(3, "Jim");}}

	6. if false){return "Frank";} else {if (7 < 3){return nomore->searchBst(3, "Jim");} else {return nomore->searchBst(3, "Jim");}}

	7. if (7 < 3){return nomore->searchBst(3, "Jim");} else {return nomore->searchBst(3, "Jim");}
	
	8. if (false){return nomore->searchBst(3, "Jim");} else {return nomore->searchBst(3, "Jim");}
	
	9.return nomore->searchBst(3, "Jim");
	
	10.return missingString;
	
	11.return "Jim";
*/
};