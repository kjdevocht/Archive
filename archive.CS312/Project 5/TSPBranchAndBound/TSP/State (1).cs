using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TSP
{
	class State
	{

		public double[,] costMatrix;
		public double lowerbound = 0;
		public double Infinity = Double.PositiveInfinity;
		public bool isSolution;
		public static int numCreated = 0;

		//the key is the city that you are leaving and the value is the city that you arrive at
		public Dictionary<int, int> routeMap = new Dictionary<int,int>();
		public Dictionary<int, int> cycleKeyMap = new Dictionary<int, int>();
		public Dictionary<int, int> cycleValueMap = new Dictionary<int, int>();


		public State(City[] cities)
		{
			numCreated++;
			int size = (int)cities.Count();
			costMatrix = new double[size, size];
			for (int row = 0; row < cities.Count(); row++)
			{
				for (int col = 0; col < cities.Count(); col++)
				{
					if (row == col)
					{
						costMatrix[row, col] = Double.PositiveInfinity;
					}
					else
					{
						costMatrix[row, col] = cities[row].costToGetTo(cities[col]);
					}

				}
			}
		}

		public State(State other)
		{
			numCreated++;
			costMatrix = (double[,])other.costMatrix.Clone();
			lowerbound = other.lowerbound;
			isSolution = other.isSolution;
			routeMap = new Dictionary<int, int>(other.routeMap);
			cycleKeyMap = new Dictionary<int, int>(other.cycleKeyMap);
			cycleValueMap = new Dictionary<int, int>(other.cycleValueMap);
		}

		public void reduceCosts()
		{
			//Check all rows
			int lowestX = 0;
			int lowestY = 0;
			double lowestValue = 0;
			for (int row = 0; row < costMatrix.GetLength(0); row++)
			{
				//This is here because if this row is in the solution then skip it
				if (!routeMap.ContainsKey(row))
				{
					lowestValue = costMatrix[row, 0];
					lowestX = row;
					lowestY = 0;
					for (int col = 0; col < costMatrix.GetLength(1); col++)
					{
						//This is here because if this column is in the solution then skip it
						if (!routeMap.ContainsValue(col))
						{
							if (costMatrix[row, col] < lowestValue)
							{
								lowestValue = costMatrix[row, col];
								lowestY = col;
							}
						}
					}
					//TODO:  This portion of the code might still need some help when things start to have lots of Infinity
					if (costMatrix[lowestX, lowestY] != Infinity)
					{
						lowerbound += costMatrix[lowestX, lowestY];
					}
					for (int col = 0; col < costMatrix.GetLength(1); col++)
					{
						if (costMatrix[lowestX, lowestY] != Infinity)
						{
							costMatrix[row, col] = costMatrix[row, col] - lowestValue;
						}
					}
				}
			}

			//Check all columns
			for (int col = 0; col < costMatrix.GetLength(1); col++)
			{
				//This is here because if this column is in the solution then skip it
				if (!routeMap.ContainsValue(col))
				{
					lowestValue = costMatrix[0, col];
					lowestX = 0;
					lowestY = col;
					for (int row = 0; row < costMatrix.GetLength(0); row++)
					{
						//This is here because if this row is in the solution then skip it
						if (!routeMap.ContainsKey(row))
						{
							if (costMatrix[row, col] < lowestValue)
							{
								lowestValue = costMatrix[row, col];
								lowestX = row;
							}
						}
					}
					if (costMatrix[lowestX, lowestY] != Infinity)
					{
						lowerbound += costMatrix[lowestX, lowestY];
					}
					for (int row = 0; row < costMatrix.GetLength(1); row++)
					{
						if (costMatrix[lowestX, lowestY] != Infinity)
						{
							costMatrix[row, col] = costMatrix[row, col] - lowestValue;
						}

					}
				}
			}
		}

		public void printOutCostMatrix()
		{
			for (int row = 0; row < costMatrix.GetLength(0); row++)
			{
				for (int col = 0; col < costMatrix.GetLength(1); col++)
				{
					if (costMatrix[row, col] == Double.PositiveInfinity)
					{
						System.Console.Write("INF " + " ");
					}
					else
					{
						if (costMatrix[row, col] < 10)
						{
							System.Console.Write(costMatrix[row, col] + "    ");
						}
						else if (costMatrix[row, col] < 100)
						{
							System.Console.Write(costMatrix[row, col] + "   ");
						}
						else if (costMatrix[row, col] < 1000)
						{
							System.Console.Write(costMatrix[row, col] + "  ");
						}
						else
						{
							System.Console.Write(costMatrix[row, col] + " ");
						}
					}
				}
				System.Console.Write("\n");
			}   
		}

		public void checkForCycles(int rowID, int ColID)
		{
			routeMap.Add(rowID, ColID);
			//cycleMap.Add(rowID, ColID);//Probably move this so that we don't have to just remove it again if there is a merge

			costMatrix[ColID, rowID] = Double.PositiveInfinity;
			//crosscheck
			bool merging = true;
			int cycleKey = rowID;
			int cycleValue = ColID;
			
			while (merging)
			{
				if (cycleKeyMap.ContainsValue(cycleKey))
				{
					//set the new cycleKey using the old one
					cycleKey = cycleValueMap[cycleKey];
					//Remove the old entry from the cycleValueMap
					cycleValueMap.Remove(cycleKeyMap[cycleKey]);
					//Remove the old entry from the cycleKeyMap
					cycleKeyMap.Remove(cycleKey);

					//At this point we have merged to nodes together along a common path.
					//And we have a new node to check and see if we can merge even further
				}
				else if (cycleKeyMap.ContainsKey(cycleValue))
				{
					cycleValueMap.Remove(cycleKeyMap[cycleValue]);
					int keyKey = cycleValue;
					cycleValue = cycleKeyMap[cycleValue];
					cycleKeyMap.Remove(keyKey);
					
				}
				else
				{
					merging = false;
				}
			}
			//Now add in the newly merged key value pair, and it's inverse into the cycleKeyMap and cycleValueMap respectivly
			//You also need invalidate the inverse in the cost matrix
			cycleKeyMap.Add(cycleKey, cycleValue);
			cycleValueMap.Add(cycleValue, cycleKey);
			costMatrix[cycleValue, cycleKey] = Infinity;
			if(routeMap.Count == costMatrix.GetLength(0)-1)
			{
				isSolution = true;
			}
		}
	}
}
