/* CS 236 LAB 4 Database.h Kevin DeVocht*/


#include <fstream>
#include <algorithm>
#include <string>
#include <sstream>
#include <iostream>
#include <iomanip>
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <exception>
#include <set>
#include "Parser.h"
#include "spaces.h"
#include <map>




class node
{
	public:
		set<string> dep;
	    bool v;
	    string id;
	    Predicate pred;
		int ruleNumber;
	    int pon;
		
	    
	node()
	{v = false; pon = 0;}
	
	
	string toString()
	{
		string results;
		results = "  " + id + ": ";
		for(set<string>::iterator it = dep.begin(); it != dep.end(); it++)
		{
			results += (*it) + " ";
		}
		results += "\n";
		
		return results;
		
	}
	

};

class Graph
{
	public:
	map<string, node> graph;
	set<int> check;
	map<string, string> cycles;
	vector<int> ruleOrder;
	
			
	void getDependecies(vector<Rule>& rlLst, string id, node& theNode)
	{
		rh++;
		if(debug) cout<<spaces()<<"getDependecies"<<endl;
		for(int k = 0; k<rlLst.size(); k++)
		{
			if(id == rlLst.at(k).rl.id)
			{
				string answer;
				theNode.dep.insert("R"+ itoa(answer, k+1));
			}
		}
		rh--;
	}
	
	
	void mapRules(map<string, node>& graph, vector<Rule>& rlLst)
	{
		rh++;
		if(debug) cout<<spaces()<<"mapRules"<<endl;
		for(int i = 0; i<rlLst.size(); i++)
		{
			node newNode;
			string answer;
			newNode.id = "R"+ itoa(answer, i+1);
			newNode.pred = rlLst.at(i).rl;
			newNode.ruleNumber = i;
			for(int j = 0; j<rlLst.at(i).predLst.size(); j++)
			{
				getDependecies(rlLst, rlLst.at(i).predLst.at(j).id, newNode);
			}	
			graph[newNode.id] = newNode;
		}
		rh--;
	}
	
	void mapNodes(vector<Rule>& rlLst, vector<Query>& qryLst)
	{
		rh++;
		if(debug) cout<<spaces()<<"mapNodes"<<endl;
		map<string, node> graph;
		mapRules(graph, rlLst);
		this->graph = graph;
		rh--;
	}	


	int depthFirst(string id)
	{
		rh++;
		if(debug) cout<<spaces()<<"depthFirst"<<endl;
		int pon = 0;
		string answer;
		for(map<string, node>::iterator mit = graph.begin(); mit != graph.end(); mit++)
		{
			if(id == (*mit).second.pred.id)
			{
				visitDepth(this->graph[(*mit).first], pon, check);
				//break;
			}
		}
		rh--;
		return pon;	
	}
	
	
	void visitDepth(node& n, int& pon, set<int>& check)
	{
		rh++;
		if(debug) cout<<spaces()<<"visitDepth"<<endl;
		n.v = true;	
		for(set<string>::iterator it = n.dep.begin(); it != n.dep.end(); it++)
		{
			if(!this->graph[(*it)].v)
			{
				visitDepth(this->graph[(*it)], pon, check);
			}
		}
		pon++;
		n.pon = pon;
		string print;
		itoa(print, pon);
		check.insert(pon);
		rh--;

	}
	
	
	
	void topo()
	{
		rh++;
		if(debug) cout<<spaces()<<"topo"<<endl;
		for(set<int>::iterator it = this->check.begin(); it != this->check.end(); it++)
		{
			for(map<string, node>::iterator mit = this->graph.begin(); mit != this->graph.end(); mit++)
			{
				if((*it) == (*mit).second.pon)
				{
					this->ruleOrder.push_back((*mit).second.ruleNumber);
					break;
				}
			}
		}	
		rh--;
	}

	void cycleFindNest(map<string, node>::iterator& mit)
	{
		rh++;
		if(debug) cout<<spaces()<<"cycleFindNest"<<endl;
		for(set<string>::iterator it = (*mit).second.dep.begin(); it != (*mit).second.dep.end(); it++)
		{
			if((*mit).second.pon <= this->graph[(*it)].pon && this->graph[(*it)].v)
			{
					this->cycles[(*mit).first] = (*it);
			}
		}
		rh--;
	}
	
	void cycleFind()
	{
		rh++;
		if(debug) cout<<spaces()<<"cycleFind"<<endl;
		for(map<string, node>::iterator mit = this->graph.begin(); mit != this->graph.end(); mit++)
		{	
			if((*mit).second.v)
			{
				cycleFindNest(mit);
			}
			
		}
		rh--;
	}
	

	void clearGraph()
	{
		rh++;
		if(debug) cout<<spaces()<<"clearGraph"<<endl;
		for(map<string, node>::iterator mit = this->graph.begin(); mit != this->graph.end();
		 mit++)
		{
			(*mit).second.v = false;
			(*mit).second.pon = 0;
		}
		this->check.clear();
		this->cycles.clear();
		this->ruleOrder.clear();
		rh--;
	}	
};





