
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
using namespace std;

template <typename TItem>

class LinkedArrayList 
{


  class Node 
  {
	friend class Iterator;
	friend class LinkedArrayList;
    TItem* items;
    Node* next;
    Node* prev;
    int capacity;
    int size;
    public:
	Node( Node* next0, Node* prev0, int capacity0, int size0) :  next(next0), prev(prev0), capacity(capacity0), size(size0)
	{items = new TItem[capacity];}
	
	~Node()
	{
		delete [] items;
	}
	
	void insert(int index, const TItem& item)
	{
		if (index == size)
		{
			items[index] = item;
			size++;
		}
		else 
		{
			for (int i = size; i>index; i--)
			{
				items[i] = items[i-1];
			}
			items[index] = item;
			size++;
		}

	} 
	
	TItem remove (int index)
	{
		TItem temp = items[index];
		if (index == size-1)
		{
			size--;
		}
		else 
		{
			if (size == 2)
			{
				items[0] = items[1];
				size--;
			}
			else
			{
				for (int i = index; i<size-1; i++)
				{
					items[i] = items[i+1];
				}
				size--;
			}
		}
		return temp;
	}
	Node* get_prev() const
	{
		return prev;
	}
	
	Node* get_next() const
	{
		return next;
	}
	
	int get_capacity() const
	{
		return capacity;
	}
	
	int get_size() const
	{
		return size;
	}
	
	TItem* get_items()
	{
		return items;
	}
	
	void set_size(int new_size)
	{
			size = new_size;
	}
	void set_next(Node* new_next)
	{
		next = new_next;
	}
	
	void set_prev(Node* new_prev)
	{
		prev = new_prev;
	}
	
	bool is_full() const
	{
		return this->size == this->capacity;
	}
  };


  class Iterator 
  {
    Node* node;
    int index;
    public:
	Iterator(Node* node0, int index0) : node(node0), index(index0)
	{}
	
	Node* get_node() const
	{
		return node;
	}
	
	int get_node_size() const
	{
		return node->get_size();
	}
	int get_index() const
	{
		return index;
	}
	
	bool is_full() const
	{
		return node->is_full();
	}
	
	Iterator operator ++()
	 {
		index++;
		if(node->next == NULL)
		{
			return *this;
		}
		
		if (index >= node->size)
		{
			
			node = node->get_next();
			index = 0;
		}
		return *this;
	 }
	 
	 Iterator operator --()
	 {
		 index--;
		 if (index < 0)
		 {
			 node = node->prev;
			 index = node->size-1;
		 }
		 return *this;
	 }
	
  };


  Node* head;
  Node* tail;
  int capacity;
  int size;
  
public:
LinkedArrayList(int capacity0) : capacity(capacity0), size(0), head(NULL), tail(NULL)
{}

~LinkedArrayList()
{
	if (head != NULL)
	{
		Node* current = head;
		
		while(current != NULL)
		{
			Node* temp = current->next;
			delete current;
			current = temp;
		} 
	}
}
int get_capacity() const
{
	return capacity;
}

int get_size() const
{
	return size;
}


void set_size(int new_size)
{
	size = new_size;
}


 void insert(int index, const TItem& item) 
  {
		if(index > size)
		{
			return;
		}
	  if (size == 0)
	  {
		  head = new Node(NULL, NULL, get_capacity(), get_size());
		  tail = head;
		head->insert(0,item);
		size++;
		
	  }
	  //Start at the begining of the program and try and find the index
	  else if (size/2 > index)
	  {	
		  Iterator itr(head, 0);
		  for(int i = 0; i < index; i++)
		  {
			++itr;
		  }
			if (itr.is_full())
			{
				split(itr.get_node());
				insert(index, item);
			}
			else
			{
				itr.get_node()->insert(itr.get_index(), item);
				size++;
			}
		}
		//start at the end of the program and try and find the index
		else
		{
			Iterator itr(tail, tail->get_size()-1);
			if(index == size)
			{
				if (itr.is_full())
				{
					split(itr.get_node());
					insert(index,item);
					return;
				}
				++itr;
				itr.get_node()->insert(itr.get_index(), item);
				size++;
			}
			
			else
			{
				for(int i = size-1; i > index; i--)
				{
					--itr;
					
				}
				if (itr.is_full())
				{
					split(itr.get_node());
					insert(index, item);
				}
				else
				{
					itr.get_node()->insert(itr.get_index(), item);
					size++;
				}

			}
		}
  }
  
  void split (Node* original)
  {
	  Node* new_node = new Node(original->get_next(), original, original->get_capacity(), 0);
	  
	if (original == tail)
	{
		
		new_node->prev->set_next(new_node);
		new_node->set_next(NULL);
		tail = new_node;
		
	}
	 
	else
	{
		new_node->prev->set_next(new_node);
		new_node->next->set_prev(new_node);
	}

	  original->set_size(original->get_size()/2);
	  int new_index = new_node->get_capacity()/2-1;
	  

	  for(int old_index = original->get_capacity()-1; old_index>=original->get_size(); old_index--)
	  {
		  new_node->get_items()[new_index]= original->get_items()[old_index];
		  new_index--;
		  new_node->set_size(new_node->get_size() + 1);
	  }
  }

  TItem remove(int index) 
  {
	TItem temp;
	Node* to_delete;
		
	if (index > size)
	{
		return TItem();
	}

	if (size/2 > index)
	{
		Iterator itr(head, 0);
		  for(int i = 0; i < index; i++)
		  {
			++itr;
		  }
		  temp = itr.get_node()->remove(itr.get_index());
		  to_delete = itr.get_node();
		  size--;
	}
	else
	{
		
		Iterator itr(tail, tail->get_size()-1);
		  for(int i = size-1; i > index; i--)
		  {
			--itr;
		  }
		  temp = itr.get_node()->remove(itr.get_index());

		  to_delete = itr.get_node();
		  size--;
	}
	

	if (to_delete->get_size() == 0)
	{
		if (to_delete == head && to_delete == tail)
		{
			delete to_delete;
			head=NULL;
			tail=NULL;
			return temp;
		}
		if (to_delete == head)
		{
			head = to_delete->next;
			to_delete->next->prev = NULL;
		}
		if (to_delete == tail)
		{
			tail = to_delete->prev;
		}
		
		if(to_delete->get_next() != NULL)
		{
			to_delete->next->prev = to_delete->prev;
		}
		
		if (to_delete->get_prev() != NULL)
		{
				to_delete->prev->next = to_delete->next;
		}
		
		delete to_delete;
		
	}
	return temp;
  }

  int find(const TItem& item) 
  {
	int index = 0;
	Iterator itr(head, 0);
	
	if (size == 0)
	{

		return -1;
	}
		for(int i = 0; i < size; i++)
		{
			if(itr.get_node()->get_items()[itr.get_index()] == item)
			{
				return index;
			}
			else
			{

				++itr;
				index++;
			}


		}

	index = -1;
	return index;
  }

 void print(ofstream& out)
  {
	if (size != 0)
	{

		int node_index = 0;
		Node* node = head;
	
		Iterator itr(node, 0);

		  for(int i = 0; i < size;)
		  {
				out<< "node "<<node_index<<": ";
				for (int j = 0; j < node->get_size(); j++)
				{

					out<<node->get_items()[j]<<" ";
					i++;
				}
				out<<endl;
			node = node->get_next();
			node_index++;
		  }
	}
  }
  
};

