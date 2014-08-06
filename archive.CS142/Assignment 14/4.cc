/*Kevin DeVocht assignment 14 exercise 4. Develop the function, inorder, that 
takes a binary search tree and produces a list of strings in the key order. 
You must make use of the BST property.
*/

#include <stdio.h>
#include <math.h>
#include <string.h>

/*
// A ListOfStrings is
// a OnemoreString
// an EmptyLst
class ListOfStrings 
{
	public:
		
};


//A OneMoreString is
// a new OneMoreString(first, rest)
// where
// first is a string
// rest is a ListOfStrings
class OneMoreString : public ListOfStrings
{
	public:
		const char* first;
		ListOfStrings* rest;
	
	OneMoreString(const char* first0, ListOfStrings* rest0)
	{
		this->first = first0;
		this->rest = rest0;
	}
	
	int show () 
	{
    printf("new OneMoreString(\"%s\",", this->first);
    this->rest->show();
    printf(")");
    return 0;
	}
	
	 ListOfStrings* append ( ListOfStrings* that ) 
	 {
    return new OneMoreString(this->first, this->rest->append(that));
	}	

};

//A EmptyLst is
// a new EmptyLst()
// where there is nothing because it is empty
class EmptyLst : public ListOfStrings 
{
	public:

  EmptyLst () {  }
  int show () { 
    printf("new EmptyLst()"); 
    return 0;
  }
  ListOfStrings* append ( ListOfStrings* that ) {
    return that;
  }
};


//A BST is either
// an empty
// or a node
class BST 
{
	public:
	
	//inorder: BST-> ListOfStrings
	//Purpose: To return a list of strings in key order
	virtual ListOfStrings* inorder() = 0;
	


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
		 
	//inorder: BST-> ListOfStrings
	//Purpose: To return a list of strings in key order
	ListOfStrings* inorder()
	//Template: this
	{
	return new EmptyLst();
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
	
	//inorder: BST-> ListOfStrings
	//Purpose: To return a list of strings in key order
	ListOfStrings* inorder()
	//Template: this, this->key, this->value, this->left, this->right, this->left->inorder(), this->right->inorder()
	{
		/*Test 1
			Mary->inorder()
				this = 3 "Mary", nomore, nomore
				this->key = 3
				this->left = nomore
				this->right = nomore
				ans = new OneMoreString("Mary", rest)
			return new OneMoreString("Mary", rest)
		*/
		
		/*Test 2
			Jim->inorder()
				this = 6, "Jim", Frank, Mary
				this->key = 6
				this->left = Frank
				this->right = Mary
			ans ="Mary", "Jim","Frank"
		return new OneMoreString("Mary", new OneMoreString("Jim", new OneMoreString("Frank", new EmptyLst())))
		*/
		
		/* Generalize Tests 1 and 2
			So we know that we want SAF that returns a list of the 
			fathers in order and another SAF that returns a list of 
			all the mothers in order and then we need to append the fathers 
			list to the mothers list
		*/
		
/*	return new OneMoreString(this->valeuleft->listLeft()->append(this->right->listRight()) // where SAFF is a function on the left that creates a list in order and SAMF is a list that returns a list of the right in order
	
	}
	
	//listLeft: BST->ListOfStrings
	//Purpose: to make a list of all the left brances of the tree in order
	ListOfStrings* theList()
	//Template: this, this->key, this->value, this->left, this->right, this->left->listLeft(), this->right->listRight()
	{
	/* Test 1
			Frank->theList()
				this = 7, "Frank", nomore, nomore
				this->value = "Frank"
				this->left = nomore
				this->right = nomore
				ans = "Frank", "new EmptyLst()"
			return new OneMoreString(this->value, this->left->listLeft());
	*/
	
	/* Test 2
			Jim->theList()
				this = 6, "Jim", Frank, Mary
				this->value = "Jim"
				this->left = Frank
				this->right = Mary
				ans = "Jim", "Frank", "new EmptyLst()"
			return new OneMoreString(this->value, this->left->listLeft());
	*/
/*	returns new OneMoreString(this->value, this->left->theList()
	
	}
	
	//listRight: BST->ListOfStrings
	//Purpose: to make a list of all the right brances of the tree in order
	ListOfStrings* listRight()
	//Template: this, this->key, this->value, this->left, this->right, this->left->listLeft(), this->right->listRight()

};
*/
int main ()
{
/*
BST* nomore = new empty();
BST* Martha = new node(2, "Martha", nomore, nomore);
BST* Bill = new node(8, "Bill", nomore, nomore);
BST* Mary = new node(3, "Mary", nomore, nomore);
BST* Frank = new node(7, "Frank", nomore, nomore);
BST* Patrica = new node(4, "Patrica", Bill, Martha);
BST* Jim = new node(6, "Jim", Frank, Mary);
BST* Kevin = new node(5, "Kevin", Jim, Patrica);
*/
printf("Arrgh I cant figure this one out");

}