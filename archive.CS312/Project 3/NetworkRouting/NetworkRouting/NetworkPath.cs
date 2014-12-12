using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Diagnostics;

namespace NetworkRouting
{
	class NetworkPath
	{

		public int start = -1;
		public int stop = -1;
		public List<PointF> listPoints;
		public Graphics drawing;
		public List<HashSet<int>> adjacencyList;
		public double lengthofPath;
		public Pen pen;

		public NetworkPath(int start, int stop, List<PointF> listPoints, Graphics drawing, List<HashSet<int>> adjacencyList)
		{
			this.pen = new Pen(Color.Black, 4);
			this.start = start;
			this.stop = stop;
			this.listPoints = listPoints;
			this.drawing = drawing;
			this.adjacencyList = adjacencyList;
		}

		public TimeSpan solveAllPaths()
		{
			Stopwatch timer = new Stopwatch();
			timer.Start();
			checkMinNodesAll();
			timer.Stop();
			return timer.Elapsed;
		}

		public void checkMinNodesAll()
		{
			PriorityQueue queue = new PriorityQueue(listPoints.Count);
			queue.create(listPoints);
			queue.decreaseKey(start, 0);
			Node minNode = null;
			List<Node> nodes = new List<Node>();
			while (!queue.isEmpty())
			{
				minNode = queue.delete();
				HashSet<int> adjacencies = adjacencyList[minNode.pointerIndex - 1];
				foreach (int edge in adjacencies)
				{
					if (queue.pointers[edge + 1] != -1)
					{
						Node endpoint = queue.heap[queue.pointers[edge + 1]];
						if (endpoint.distance > (minNode.distance + distance(minNode.point, endpoint.point)))
						{
							endpoint.distance = (minNode.distance + distance(minNode.point, endpoint.point));
							endpoint.previous = minNode;
							queue.decreaseKey(edge, endpoint.distance);
						}
					}
				}
				nodes.Add(minNode);
			}
			Node endNode = findEndNode(nodes);
			drawPath(endNode);
			lengthofPath = endNode.distance;
		}

		public Node findEndNode(List<Node> nodes)
		{
			Node endNode = null;
			foreach (Node n in nodes)
			{
				if (n.pointerIndex - 1 == this.stop)
				{
					endNode = n;
				}
			}

			return endNode;
		}

	   
		public TimeSpan solvePath()
		{
			Stopwatch timer = new Stopwatch();
			timer.Start();
			checkMinNodes();
			timer.Stop();
			return timer.Elapsed;
		}

		public List<Node> generateNodes()
		{
			List<Node> nodes = new List<Node>();
			int counter = 1;
			foreach (PointF point in listPoints)
			{
				Node node = new Node(point, counter);
				counter++;
				nodes.Add(node);
			}
			return nodes;
		}

		public void checkMinNodes()
		{
			List<Node> nodes = generateNodes();
			Node destination = nodes[stop];
			PriorityQueue queue = new PriorityQueue(nodes);
			queue.insert(start, queue.nodes[start], 0, null);

			bool complete = false;

			Node minNode = null;

			while (!queue.isEmpty())
			{
				minNode = queue.delete();
				minNode.visited = true;
				if (minNode == destination)
				{
					complete = true;
					break;
				}
				HashSet<int> adjacencies = adjacencyList[minNode.pointerIndex - 1];
				foreach (int edge in adjacencies)
				{
					Node nextNode = queue.nodes[edge];
					if (!nextNode.visited)
					{
						if (nextNode.distance == Double.PositiveInfinity)
						{
							queue.insert(edge, nextNode, (minNode.distance + distance(minNode.point, nextNode.point)), minNode);
						}
						else
						{

							if (nextNode.distance > minNode.distance + distance(minNode.point, nextNode.point))
							{
								nextNode.distance = minNode.distance + distance(minNode.point, nextNode.point);
								nextNode.previous = minNode;
								queue.decreaseKey(edge, nextNode.distance);
							}
						}
					}
				}
			}

			setLengthofPath(complete, minNode);
		}

		public void setLengthofPath(bool complete, Node minNode)
		{
			if (complete)
			{
				pen.Color = Color.Red;
				pen.Width = 2;
				drawPath(minNode);
				lengthofPath = minNode.distance;
			}
			else
			{
				lengthofPath = Double.PositiveInfinity;
			}
		}

		private void drawPath(Node node)
		{
			while (node.previous != null)
			{
				drawing.DrawLine(pen, node.point, node.previous.point);
				node = node.previous;
			}
			
		}


		public double distance(PointF start, PointF end)
		{
			double a = Math.Pow((start.X - end.X), 2);
			double b = Math.Pow((start.Y - end.Y), 2);
			return Math.Sqrt(a + b);
		}
	}
}
