/*tree_set.h -lab6 - Kevin DeVocht*/
#pragma once


#include <fstream>
#include <algorithm>
#include <string>
#include <sstream>
#include <iostream>
#include <iomanip>
#include <ctype.h>
#include <stdio.h>
#include <math.h>
#include "linked_array_list.h"

using namespace std;

template <typename ItemType>

class AVLTreeSet 
{


  class Node 
  {
    ItemType item;
    Node* left;
    Node* right;
    int height;
    
   public:
    Node(ItemType item0) : item(item0), left(NULL), right(NULL), height(1)
    {}

    
    Node* get_left() const
    {
		return left;
	}
	
	Node* get_right() const
	{
		return right;
	}
	Node* find_smallest()
	{
		if (this->left ==NULL)
		{
			return this;
		}
		else
		{
			this->left->find_smallest();
		}
	}
	
	int get_height() const
	{
		if( this == NULL)
		{
			return 0;
		}
		else
		{
			return height;
		}
	}
	ItemType get_item() const
	{
		return item;
	}
	
	void set_left(Node* new_left)
	{
		left = new_left;
	}
	
	void set_right(Node* new_right)
	{
			right = new_right;
	}
	
	void set_height(Node* node)
	{
		height = max(node->left->get_height(), node->right->get_height())+1;
	}
	
	void set_item(ItemType new_item)
	{
		item = new_item;
	}
  };


  Node* root;
  int size;
  
public:
AVLTreeSet(Node* root0) : root(root0), size(0)
{}

~AVLTreeSet()
{
	this->clear();
}

int get_size() const
{
	return size;
}

void clear()
{
cout<<"ENTERING CLEAR"<<endl;
	while(root != NULL)
	{
		root = real_remove(root, root->get_item());
	}
}

  Node* real_add(Node* node, const ItemType& item) 
  {
	cout<<"ENTERING REAL ADD WITH "<<item<<endl;
	if (node == NULL)
	{
		cout<<"		real_add: You have found the spot to add "<<item<<endl;
		size++;
		Node* new_node = new Node(item);
		return new_node;
	}
	else if (item < node->get_item())
	{
		cout<<"		real_add: "<<item<<" ,the item you are looking for is less then "<<node->get_item()<<" the current node's item"<<endl;
		node->set_left(real_add(node->get_left(), item));
		node = balance(node);
		node->set_height(node);

		return node;
		
		
		
	}
	else if (item >node->get_item())
	{
		cout<<"		real_add: "<<item<<" ,the item you are looking for is greater then "<<node->get_item()<<" the current node's item"<<endl;
		node->set_right(real_add(node->get_right(), item));
		node = balance(node);
		node->set_height(node);
		return node;
	}
	else
	{
		cout<<"		real_add: "<<item<<" is already in this set"<<endl;
		return node;
	}

  }

void add(const ItemType& item)
{
	root = real_add(root, item);
}
void print(ofstream& out)
{	
	if (root == NULL)
	{
		return;
	}
	real_print(out, root);
	out<<endl;
}	

void real_print(ofstream& out, Node* node)
{
	int level = 0;
	int line_count = 0;
	LinkedArrayList<Node*>* print = new LinkedArrayList<Node*>(4);
	LinkedArrayList<Node*>* print_queue = new LinkedArrayList<Node*>(4);
	print->insert(0, node);

	out<<"level "<<level<<":";

	while(print->get_size() != 0)
	{
		node = print->remove(0);
		if (node->get_left() != NULL)
		{
			print_queue->insert(print_queue->get_size(), node->get_left());
		}
		if (node->get_right() != NULL)
		{
			print_queue->insert(print_queue->get_size(), node->get_right());
		}
		out<<" "<<node->get_item()<<"("<<node->get_height()<<")";
		line_count++;
		if (line_count == 8 && print->get_size() !=0)
		{
			out<<endl<<"level "<<level<<": ";
			line_count =0;
		}

		if (print->get_size() == 0 && print_queue->get_size() != 0)
		{
			delete print;
			print = print_queue;
			print_queue = new LinkedArrayList<Node*>(4);
			level++;
			line_count = 0;
			out<<endl<<"level "<<level<<": ";
		}	
	}
	delete print;
	delete print_queue;
}



//Contract: real_remove: Node* + item ->
Node* real_remove(Node* node, const ItemType& item)
{
	//check to see if you should go left
	if (item < node->get_item())
	{
		if (node->get_left() == NULL)
		{
			return node;
		}
		else
		{
			node->set_left(real_remove(node->get_left(), item));
			node = balance(node);
			node->set_height(node);
			return node;
		}
	}
	//check to see if you should go right
	else if (item > node->get_item())
	{
		if (node->get_right() == NULL)
		{
			return node;
		}
		else
		{
			node->set_right(real_remove(node->get_right(), item));
			node = balance(node);
			node->set_height(node);
			return node;
		}
	}
	//you have found what you are looking for
	else
	{
		//Both children are NULL
		if (node->get_left() == NULL && node->get_right() == NULL)
		{
			delete node;
			size--;
			return NULL;
		}
		//The left child is NULL but there is a right child
		else if( node->get_left() == NULL && node->get_right() != NULL)
		{
			Node* temp;
			temp = node->get_right();
			delete node;
			temp = balance(temp);
			temp->set_height(temp);
			size--;
			return temp;
		}
		//There is a left child but the right child is NULL
		else if(node->get_left() != NULL && node->get_right() == NULL)
		{
			Node* temp;
			temp = node->get_left();
			delete node;
			temp = balance(temp);
			temp->set_height(temp);
			size--;
			return temp;			
		}
		//This node has 2 children
		else
		{
			ItemType temp;
			temp = node->get_right()->find_smallest()->get_item();
			cout<<"		node is currently "<<node->get_item()<<" setting it as "<<temp<<endl;
			node->set_item(temp);
			node->set_right(real_remove(node->get_right(), temp));
			node = balance(node);
			cout<<"		node after balance "<<node->get_item()<<endl;
			node->set_height(node);
			size--;
			return node;
		}
	}
	
}
//Contract: remove: item->nothing
//Purpose: to call real_remove to remove a given item from the set
  void remove(const ItemType& item) 
  {
	  if (root == NULL)
	  {
		  return;
	  }
	  else
	  {
		root = real_remove(root, item);
	}
  }


//Contract: find: item->bool
//Purpose: To see if a given item is in the AVL tree
  bool real_find(Node* node, const ItemType& item) 
  {
	if (item < node->get_item())
    {
		if(node->get_left() == NULL)
		{
			return false;
		}
		else
		{
			real_find(node->get_left(), item);
		}
	}
	else if (item > node->get_item())
	{
		if (node->get_right() ==NULL)
		{
			return false;
		}
		else
		{
			real_find(node->get_right(), item);
		}
	}
	else
	{
		return true;
	}
  }
  
  bool find(const ItemType& item)
  {
	  if(root == NULL)
	  {
		 return false;
	  }
	  return real_find(root, item);
  }

Node* balance(Node* node)
{
cout<<"ENTERING BALANCE WITH NODE "<<node->get_item()<<endl;
	if (node->get_left()->get_height()-node->get_right()->get_height() > 1)
	{
		cout<<"		balance: pushing right "<<node->get_item()<<endl;
		node = push_right(node);
		return node;
	}
	else if (node->get_right()->get_height()-node->get_left()->get_height() > 1)
	{
		cout<<"		balance: pushing left "<<node->get_item()<<endl;
		node = push_left(node);
		return node;
	}
	else
	{
	cout<<"		balance: no need to balance "<<node->get_item()<<endl;
		return node;
	}
}

Node* push_right(Node* node)
{
	cout<<"ENTERING PUSH RIGHT "<<endl;
	//double rotate
	if( node->get_left()->get_right()->get_height() > node->get_left()->get_left()->get_height())
	{
		cout<<"		push_right:  double rotate"<<endl;
		node->set_left(rotate_left(node->get_left()));
		return rotate_right(node);
	}
	//single rotate right
	else 
	{
	cout<<"		push_right:  single rotate"<<endl;
		return rotate_right(node);
	}
}

Node* push_left(Node* node)
{
	cout<<"ENTERING PUSH LEFT "<<endl;
	//double rotate
	if( node->get_right()->get_left()->get_height() > node->get_right()->get_right()->get_height())
	{	
	cout<<"		push_left:  double rotate "<<node->get_item()<<endl;
		node->set_right(rotate_right(node->get_right()));
		return rotate_left(node);
		
	}
	//single rotate left
	else 
	{
	cout<<"		push_left:  single rotate "<<node->get_item()<<endl;
		return rotate_left(node);
	}	
}

//Contract:  rotate_right: Node* -> Node*
//Purpose to conduct a single right rotate
Node* rotate_right (Node* node)
{
cout<<"ENTERING RIGHT ROTATE "<<node->get_item()<<endl;
	Node* temp = NULL;
	
	temp = node->get_left();
	node->set_left(temp->get_right());
	temp->set_right(node);
	temp->get_right()->set_height(temp->get_right());
	temp->set_height(temp);
	cout<<"rotate right: returning "<<temp->get_item()<<endl;
	return temp;	
}
//Contract:  rotate_left: Node* -> Node*
//Purpose to conduct a single left rotate
Node* rotate_left (Node* node)
{
cout<<"ENTERING LEFT ROTATE "<<node->get_item()<<endl;
	Node* temp = NULL;
	
	temp = node->get_right();
	node->set_right(temp->get_left());
	temp->set_left(node);
	temp->get_left()->set_height(temp->get_left());
	temp->set_height(temp);
	cout<<"rotate left: returning "<<temp->get_item()<<endl;
	return temp;	
}



};
