/*lab7 -hash_set.h - Kevin DeVocht*/

#pragma once
#include "linked_array_list.h"
int hash_code(const std::string& s)
{
	int hash_index = 0;
	
	for (int string_length = 0; string_length<s.length(); string_length++)
	{
		int current = s[string_length];
		hash_index*31+current;
	}
	return hash_index;
}


template <typename ItemType>

class hash_set
{


  LinkedArrayList<ItemType> table;
  int table_size;
  int capacity;
public:
	hash_set() : table_size(0), capacity(0)
{table = new LinkedArrayList<ItemType>*[0];}

int get_size()
{
	return table_size;
}

int get_capacity()
{
	return capacity;
}

void grow()
{
cout<<"inside grow"<<endl;
	cout<<"table size "<<table_size<<endl;
	cout<<"temp size "<<table_size*2+1<<endl;
	LinkedArrayList<ItemType>* temp[table_size*2+1];
	int unsigned hash_index;
	
	for(int i = 0; i <capacity; i++)
	{
		ItemType item;
		item = table[i]->remove(0);
		hash_index = hash_code(item);
	}
}



//ADD
  void add(const ItemType& item) 
  {
	int unsigned hash_index = hash_code(item);
	
	hash_index = hash_index % capacity;
	table[hash_index]->insert(item); 
  }
  
 void clear ()
 {
	for(int i =0; i<table_size; i++)
	{
		while(table[i]->get_size() > 0)
		{
			table[i]->remove(0);
		}
	}
 }
  
  
//REMOVE
  void remove(const ItemType& item)
  {
	int unsigned hash_index;
	hash_index = hash_code(item);
	table[hash_index]->remove(table[hash_index]->find(item));
	table_size--;
  }

  
  //FIND: NOT DONE YET
  bool find(const ItemType& item) 
  {

    return false;

  }


};
