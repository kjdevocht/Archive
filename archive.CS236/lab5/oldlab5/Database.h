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
#include <map>

class node
{
	public:
		set<string> dep;
	    bool v;
	    string id;
	    Predicate pred;
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

class Database
{
	public:
		//set<Relation> relations;
		char* input;
		char* output;
		DatalogProgram dlp;
		
	
	Database(char* input0, char* output0) : input(input0), output(output0) 
		{readInput();}
	
	string numString(int n)
	{
		string s;
		stringstream streamin(stringstream::out);
		streamin<<n;
		s = streamin.str();
		return s;	
	}
			
	void getDependecies(vector<Rule>& rlLst, string id, node& theNode)
	{
		for(int k = 0; k<rlLst.size(); k++)
		{
			if(id == rlLst.at(k).rl.id)
			{
				
				theNode.dep.insert("R"+ numString(k+1));
			}
		}
	}
	
	
	/*void mapQueries(map<string, node>& graph, vector<Query>& qryLst,
	 vector<Rule>& rlLst)
	{
		for(int i = 0; i<qryLst.size(); i++)
		{
			node newNode;
			newNode.id = "Q" + numString(i+1);
			newNode.pred = qryLst.at(i).qry;
			graph[newNode.id] = newNode;
			getDependecies(rlLst, qryLst.at(i).qry.id, graph[newNode.id]);
					
		}		
	}*/
	
	void mapRules(map<string, node>& graph, vector<Rule>& rlLst)
	{
		for(int i = 0; i<rlLst.size(); i++)
		{
			node newNode;
			newNode.id = "R"+ numString(i+1);
			for(int j = 0; j<rlLst.at(i).predLst.size(); j++)
			{
				getDependecies(rlLst, rlLst.at(i).predLst.at(j).id, newNode);
			}	
			graph[newNode.id] = newNode;
		}		
	}
	
	void printMap(string& results, map<string, node>& graph)
	{
		for(map<string, node>::iterator mit = graph.begin(); mit != graph.end();
		 mit++)
		 {
			string out = (*mit).second.toString();
			cout<<out;
		 }
		 results += "\n";	
	}
	
	void mapNodes(vector<Rule>& rlLst, vector<Query>& qryLst, string& results)
	{
		map<string, node> graph;
		cout<<"Dependency Graph"<<endl;;
		//mapQueries(graph, qryLst, rlLst);
		mapRules(graph, rlLst);
		printMap(results, graph);
		depthFirst(results, graph, qryLst.size());
	}	


	void depthFirst(string& results, map<string, node>& graph, int size)
	{
		int pon = 0;
		set<int> check;
		for(int i = 0; i<size; i++)
		{
			results += graph["Q"+ numString(i+1)].pred.toString() + "\n\n";
			visitDep(graph["Q"+ numString(i+1)], graph, pon, check);
			ponPrint(results, graph);
			topo(graph, results, check);
			cycleFind(graph, results);
			clearGraph(graph, pon, check);
		}	
	}
	
	
	void visitDep(node& n, map<string, node>& graph, int& pon, set<int>& check)
	{
		n.v = true;	
		for(set<string>::iterator it = n.dep.begin(); it != n.dep.end(); it++)
		{
			if(!graph[(*it)].v)
			{
				visitDep(graph[(*it)], graph, pon, check);
			}
		}
		pon++;
		n.pon = pon;
		if(n.id.at(0) != 'Q')
		{
			check.insert(pon);
		}
	}
	
	
	void ponPrint(string& results, map<string, node>& graph)
	{
		results +="  Postorder Numbers\n";
		for(map<string, node>::iterator mit = graph.begin(); mit != graph.end();
		 mit++)
		{
			if((*mit).second.v)
			{
				results += "    " + (*mit).first + ": " + 
				numString((*mit).second.pon) + "\n";
			}
		}
		results += "\n";		
	}
	
	
	void topo(map<string, node>& graph, string& results, set<int>& check)
	{
		results += "  Rule Evaluation Order\n";	
		for(set<int>::iterator it = check.begin(); it != check.end(); it++)
		{
			for(map<string, node>::iterator mit = graph.begin(); mit != 
			graph.end(); mit++)
			{
				if((*it) == (*mit).second.pon)
				{
					results += "    " + (*mit).first + "\n";
					break;
				}
			}
		}
		results +="\n";	
	}

	void cycleFindNest(map<string, node>& graph,string& results, map<string, node>::iterator& mit)
	{
		bool isFirst = true;
		for(set<string>::iterator it = (*mit).second.dep.begin(); it != 
		(*mit).second.dep.end(); it++)
		{
			if((*mit).second.pon <= graph[(*it)].pon && graph[(*it)].v)
			{
				if(isFirst)
				{
					results += "    " + (*mit).first + ": " + (*it);
					isFirst = false;
				}
				else
				{
					results += " " + (*it);
				}
			}
		}
		if(!isFirst)
		{
			results +="\n";
		}
			
	}
	
	void cycleFind(map<string, node>& graph,string& results)
	{
		results += "  Backward Edges\n";
		for(map<string, node>::iterator mit = graph.begin(); mit != 
		graph.end(); mit++)
		{	
			if((*mit).second.v)
			{
				cycleFindNest(graph, results, mit);
			}
			
		}
		results +="\n";
	}
	

	void clearGraph(map<string, node>& graph, int& pon, set<int>& check)
	{
		for(map<string, node>::iterator mit = graph.begin(); mit != graph.end();
		 mit++)
		{
			(*mit).second.v = false;
			(*mit).second.pon = 0;
			pon = 0;
		}
		check.clear();	
	}
	
	
	void readInput()
	{
		//Function Variables
		vector<Token> results;
		string endResults = "";
		results = makeTokens(this->input);
		
		//File is done reading in
		parse(results);
		mapNodes(dlp.rlLst.rlLst, dlp.qLst.qLst, endResults);
		print(endResults, this->output);	
	}
	
	void print(string results, char* output)
	{
		ofstream out;
		out.open(output);
		out<<results;
		out.close();
	}
	
	string parse(vector<Token>& results)
	{
		//Function Variables
		TokenHolder tok(results);
		Parser parse(tok);
		string parseResults = "";

		try
		{
			this->dlp = parse.dataLogProgramParser();
		}
		catch (int e)
		{
			tok.currentToken = e;
		}
		return parseResults;	
	}	
};





