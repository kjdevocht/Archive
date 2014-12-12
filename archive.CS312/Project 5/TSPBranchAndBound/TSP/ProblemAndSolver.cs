using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.Drawing;

namespace TSP
{

	class ProblemAndSolver
	{

		private class TSPSolution
		{
			/// <summary>
			/// we use the representation [cityB,cityA,cityC] 
			/// to mean that cityB is the first city in the solution, cityA is the second, cityC is the third 
			/// and the edge from cityC to cityB is the final edge in the path.  
			/// You are, of course, free to use a different representation if it would be more convenient or efficient 
			/// for your node data structure and search algorithm. 
			/// </summary>
			public ArrayList Route;

			public TSPSolution(ArrayList iroute)
			{
				Route = new ArrayList(iroute);
			}


			/// <summary>
			/// Compute the cost of the current route.  
			/// Note: This does not check that the route is complete.
			/// It assumes that the route passes from the last city back to the first city. 
			/// </summary>
			/// <returns></returns>
			public double costOfRoute()
			{
				// go through each edge in the route and add up the cost. 
				int x;
				City here;
				double cost = 0D;

				for (x = 0; x < Route.Count - 1; x++)
				{
					here = Route[x] as City;
					cost += here.costToGetTo(Route[x + 1] as City);
				}

				// go from the last city to the first. 
				here = Route[Route.Count - 1] as City;
				cost += here.costToGetTo(Route[0] as City);
				return cost;
			}
		}

		#region Private members 

		/// <summary>
		/// Default number of cities (unused -- to set defaults, change the values in the GUI form)
		/// </summary>
		// (This is no longer used -- to set default values, edit the form directly.  Open Form1.cs,
		// click on the Problem Size text box, go to the Properties window (lower right corner), 
		// and change the "Text" value.)
		private const int DEFAULT_SIZE = 25;

		private const int CITY_ICON_SIZE = 5;

		// For normal and hard modes:
		// hard mode only
		private const double FRACTION_OF_PATHS_TO_REMOVE = 0.20;

		/// <summary>
		/// the cities in the current problem.
		/// </summary>
		private City[] Cities;
		/// <summary>
		/// a route through the current problem, useful as a temporary variable. 
		/// </summary>
		private ArrayList Route;
		/// <summary>
		/// best solution so far. 
		/// </summary>
		private TSPSolution bssf; 



		/// <summary>
		/// how to color various things. 
		/// </summary>
		private Brush cityBrushStartStyle;
		private Brush cityBrushStyle;
		private Pen routePenStyle;


		/// <summary>
		/// keep track of the seed value so that the same sequence of problems can be 
		/// regenerated next time the generator is run. 
		/// </summary>
		private int _seed;
		/// <summary>
		/// number of cities to include in a problem. 
		/// </summary>
		private int _size;

		/// <summary>
		/// Difficulty level
		/// </summary>
		private HardMode.Modes _mode;

		/// <summary>
		/// random number generator. 
		/// </summary>
		private Random rnd;
		#endregion

		#region Public members

		public PriorityQueue queue = new PriorityQueue();
		public PriorityQueue includeQueue = new PriorityQueue();
		int numPruned = 0;
		int numSolutions= 0;
		int maxStored = 0;

		public int Size
		{
			get { return _size; }
		}

		public int Seed
		{
			get { return _seed; }
		}
		#endregion

		#region Constructors
		public ProblemAndSolver()
		{
			this._seed = 1; 
			rnd = new Random(1);
			this._size = DEFAULT_SIZE;

			this.resetData();
		}

		public ProblemAndSolver(int seed)
		{
			this._seed = seed;
			rnd = new Random(seed);
			this._size = DEFAULT_SIZE;

			this.resetData();
		}

		public ProblemAndSolver(int seed, int size)
		{
			this._seed = seed;
			this._size = size;
			rnd = new Random(seed); 
			this.resetData();
		}
		#endregion

		#region Private Methods

		/// <summary>
		/// Reset the problem instance.
		/// </summary>
		private void resetData()
		{

			Cities = new City[_size];
			Route = new ArrayList(_size);
			bssf = null;

			if (_mode == HardMode.Modes.Easy)
			{
				for (int i = 0; i < _size; i++)
					Cities[i] = new City(rnd.NextDouble(), rnd.NextDouble());
			}
			else // Medium and hard
			{
				for (int i = 0; i < _size; i++)
					Cities[i] = new City(rnd.NextDouble(), rnd.NextDouble(), rnd.NextDouble() * City.MAX_ELEVATION);
			}

			HardMode mm = new HardMode(this._mode, this.rnd, Cities);
			if (_mode == HardMode.Modes.Hard)
			{
				int edgesToRemove = (int)(_size * FRACTION_OF_PATHS_TO_REMOVE);
				mm.removePaths(edgesToRemove);
			}
			City.setModeManager(mm);

			cityBrushStyle = new SolidBrush(Color.Black);
			cityBrushStartStyle = new SolidBrush(Color.Red);
			routePenStyle = new Pen(Color.Blue,1);
			routePenStyle.DashStyle = System.Drawing.Drawing2D.DashStyle.Solid;
		}

		#endregion

		#region Public Methods

		/// <summary>
		/// make a new problem with the given size.
		/// </summary>
		/// <param name="size">number of cities</param>
		//public void GenerateProblem(int size) // unused
		//{
		//   this.GenerateProblem(size, Modes.Normal);
		//}

		/// <summary>
		/// make a new problem with the given size.
		/// </summary>
		/// <param name="size">number of cities</param>
		public void GenerateProblem(int size, HardMode.Modes mode)
		{
			this._size = size;
			this._mode = mode;
			resetData();
		}

		/// <summary>
		/// return a copy of the cities in this problem. 
		/// </summary>
		/// <returns>array of cities</returns>
		public City[] GetCities()
		{
			City[] retCities = new City[Cities.Length];
			Array.Copy(Cities, retCities, Cities.Length);
			return retCities;
		}

		/// <summary>
		/// draw the cities in the problem.  if the bssf member is defined, then
		/// draw that too. 
		/// </summary>
		/// <param name="g">where to draw the stuff</param>
		public void Draw(Graphics g)
		{
			float width  = g.VisibleClipBounds.Width-45F;
			float height = g.VisibleClipBounds.Height-45F;
			Font labelFont = new Font("Arial", 10);

			// Draw lines
			if (bssf != null)
			{
				// make a list of points. 
				Point[] ps = new Point[bssf.Route.Count];
				int index = 0;
				foreach (City c in bssf.Route)
				{
					if (index < bssf.Route.Count -1)
						g.DrawString(" " + index +"("+c.costToGetTo(bssf.Route[index+1]as City)+")", labelFont, cityBrushStartStyle, new PointF((float)c.X * width + 3F, (float)c.Y * height));
					else 
						g.DrawString(" " + index +"("+c.costToGetTo(bssf.Route[0]as City)+")", labelFont, cityBrushStartStyle, new PointF((float)c.X * width + 3F, (float)c.Y * height));
					ps[index++] = new Point((int)(c.X * width) + CITY_ICON_SIZE / 2, (int)(c.Y * height) + CITY_ICON_SIZE / 2);
				}

				if (ps.Length > 0)
				{
					g.DrawLines(routePenStyle, ps);
					g.FillEllipse(cityBrushStartStyle, (float)Cities[0].X * width - 1, (float)Cities[0].Y * height - 1, CITY_ICON_SIZE + 2, CITY_ICON_SIZE + 2);
				}

				// draw the last line. 
				g.DrawLine(routePenStyle, ps[0], ps[ps.Length - 1]);
			}

			// Draw city dots
			foreach (City c in Cities)
			{
				g.FillEllipse(cityBrushStyle, (float)c.X * width, (float)c.Y * height, CITY_ICON_SIZE, CITY_ICON_SIZE);
			}

		}

		/// <summary>
		///  return the cost of the best solution so far. 
		/// </summary>
		/// <returns></returns>
		public double costOfBssf ()
		{
			if (bssf != null)
				return (bssf.costOfRoute());
			else
				return -1D; 
		}

		/// <summary>
		///  solve the problem.  This is the entry point for the solver when the run button is clicked
		/// right now it just picks a simple solution. 
		/// </summary>
		public void solveProblem()
		{
			Route = new ArrayList();
			State baseState = new State(Cities);
			baseState.reduceCosts();
			queue.Push(baseState, baseState.lowerbound);
			DateTime startTime = DateTime.UtcNow;
			DateTime endTime = DateTime.UtcNow.AddSeconds(30);
			while(!queue.isEmpty()){
				if (endTime > DateTime.UtcNow)
				{
					if (bssf == null && !includeQueue.isEmpty())
					{
						findBestZero(includeQueue.Pop());
					}
					else
					{
						if (queue.Count > maxStored)
						{
							maxStored = queue.Count;
						}
						findBestZero(queue.Pop());
					}
					
				}
				else
				{
					Program.MainForm.tbElapsedTime.Text = " 30";
					Program.MainForm.Invalidate();
					break;
				}
			}
			System.Console.WriteLine("Static Count: " + State.numCreated);
			System.Console.WriteLine("Number Pruned: " + numPruned);
			System.Console.WriteLine("Number of Solutions: " + numSolutions);
			System.Console.WriteLine("Maxed Stored in Queue: " + maxStored);
			State.numCreated = 0;
			Program.MainForm.tbElapsedTime.Text = " "+ (DateTime.UtcNow - startTime);
			Program.MainForm.tspSolution.Text = " " + numSolutions;
			Program.MainForm.Invalidate();


		}



		public void findBestZero(State current)
		{
			//this should be the pruning portion and it should only ever prune if there is already a solution
			if(bssf != null && current.lowerbound > costOfBssf())
			{
				numPruned++;
				return;
			}

			double bestDifference = Double.NegativeInfinity;
			State bestInclude = null;
			State bestExclude = null;
			int bestRow = -1;
			int bestCol = -1;
			for (int row = 0; row < current.costMatrix.GetLength(0); row++)
			{
				for (int col = 0; col < current.costMatrix.GetLength(1); col++)
				{
					if (current.costMatrix[row, col] == 0)
					{
						State include = new State(current);
						State exclude = new State(current);
						include = createIncludeState(include, row, col);
						exclude = createExcludeState(exclude, row, col);
						if(include.isSolution)
						{
							createSolution(include);
							return;
						}
						if (exclude.isSolution)
						{
							createSolution(exclude);
							return;
						}
						double diff;
						if(exclude.lowerbound != Double.PositiveInfinity && include.lowerbound != Double.PositiveInfinity)
						{
							diff = exclude.lowerbound - include.lowerbound;
						}
						else
						{
							diff = Double.PositiveInfinity;
						}
						
						if (diff > bestDifference)
						{
							bestDifference = diff;
							bestInclude = include;
							bestExclude = exclude;
							bestRow = row;
							bestCol = col;
						}
					}
				}
			}
			if(bssf == null)
			{
				includeQueue.Push(bestInclude, bestInclude.lowerbound);
			}
			if (bssf != null && bestInclude.lowerbound > costOfBssf())
			{
				numPruned++;
			}
			else
			{
				queue.Push(bestInclude, bestInclude.lowerbound);
			}

			if (bssf != null && bestExclude.lowerbound > costOfBssf())
			{
				numPruned++;
			}
			else
			{
				queue.Push(bestExclude, bestExclude.lowerbound);
			}
				
				

			
		}

		public State createIncludeState(State include, int rowID, int ColID)
		{
			//Set Column to Infinity
			for (int col = 0; col < include.costMatrix.GetLength(1); col++)
			{
				include.costMatrix[rowID, col] = Double.PositiveInfinity;
			}
			//Set Row to Infinity
			for (int row = 0; row < include.costMatrix.GetLength(1); row++)
			{
				include.costMatrix[row, ColID] = Double.PositiveInfinity;
			}
			include.checkForCycles(rowID, ColID);
			include.reduceCosts();
			
			
			return include;
		}


		public State createExcludeState(State exclude, int rowID, int ColID)
		{
			exclude.costMatrix[rowID, ColID] = Double.PositiveInfinity;
			exclude.reduceCosts();
			return exclude;
		}

		public void createSolution(State solutionState)
		{
			if (bssf == null || solutionState.lowerbound < costOfBssf())
			{
				if(bssf != null)
				{
					numSolutions++;
				}
				List<int> keys = new List<int>();
				Route.Clear();
				foreach (int key in solutionState.cycleKeyMap.Keys)
				{
					keys.Add(key);

				}
				if (keys.Count > 1)
				{
					throw new Exception("There are more than keys in the cycleKeyMap");
				}
				int startCity = keys[0];
				Route.Add(Cities[startCity]);
				int nextCity = startCity;
				for (int i = 0; i < solutionState.routeMap.Count; i++)
				{
					nextCity = solutionState.routeMap[nextCity];
					Route.Add(Cities[nextCity]);
				}

				// call this the best solution so far.  bssf is the route that will be drawn by the Draw method. 
				bssf = new TSPSolution(Route);
				// update the cost of the tour. 
				Program.MainForm.tbCostOfTour.Text = " " + bssf.costOfRoute();
				// do a refresh. 
				Program.MainForm.Invalidate();
			}
		}

		#endregion
	}

}
