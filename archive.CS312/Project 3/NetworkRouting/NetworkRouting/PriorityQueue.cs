using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;

namespace NetworkRouting
{
	class PriorityQueue
	{
		public int[] pointers;
		public Node[] heap;
		public int counter;
		public int size;
		public List<Node> nodes;

		public PriorityQueue(int size)
		{
			this.size = size;
			initQueue();
		}

		public PriorityQueue(List<Node> nodes)
		{
			this.nodes = nodes;
			this.size = nodes.Count;
			initQueue();
		}

		public void initQueue()
		{
			this.pointers = new int[size + 1];
			this.heap = new Node[size + 1];
			counter = 0;
			for (int i = 0; i < size + 1; i++)
			{
				pointers[i] = -1;
				heap[i] = null;
			}
		}



		public void create(List<PointF> points)
		{
			foreach (PointF point in points)
			{
				counter++;
				Node n = new Node(point, counter);
				heap[counter] = n;
				pointers[counter] = counter;
				
			}
		}

		public Boolean isEmpty()
		{
			return (counter == 0);
		}

		public void insert(int index, Node node, double distance, Node previous)
		{
			index = index + 1;
			counter++;
			pointers[index] = counter;
			node.distance = distance;
			node.previous = previous;
			heap[counter] = node;
			bubbleUp(counter);
		}

		public Node delete()
		{
			Node minNode = heap[1];
			pointers[minNode.pointerIndex] = -1;
			if (counter != 1)
			{
				heap[1] = heap[counter];
				pointers[heap[1].pointerIndex] = 1;
			}
			heap[counter] = null;
			counter--;
			bubbleDown();
			return minNode;
		}

		private void bubbleDown()
		{
			int index = 1;
			while (hasLeftChild(index))
			{
				int smaller = index * 2;
				if (hasRightChild(index))
				{
					if (heap[index * 2].distance > heap[(index * 2) + 1].distance)
					{
						smaller = (index * 2) + 1;
					}
				}
				if (heap[index].distance > heap[smaller].distance)
				{
					exchange(smaller, index);
				}
				else
				{
					break;
				}
				index = smaller;
			}
		}

		private bool hasLeftChild(int index)
		{
			if (index * 2 > size)
			{
				return false;
			}
			return heap[index * 2] != null;
		}

		private bool hasRightChild(int index)
		{
			if (index * 2 > size)
			{
				return false;
			}
			return heap[(index * 2) + 1] != null;
		}

		public void decreaseKey(int index, double distance)
		{
			index = index + 1;
			int heapIndex = pointers[index];
			if (distance < heap[heapIndex].distance || heap[heapIndex].distance == Double.PositiveInfinity)
			{
				heap[heapIndex].distance = distance;
			}
			bubbleUp(index);
		}

		private void bubbleUp(int index)
		{
			if (index == 1)
			{
				return;
			}
			bool bubbling = true;
			while (bubbling)
			{
				bubbling = false;
				double heapIndex = pointers[index];
				if (heapIndex <= 1)
				{
					break;
				}
				double parent = Math.Floor(heapIndex / 2);
				if (heap[(int)heapIndex].distance < heap[(int)parent].distance)
				{
					exchange((int)heapIndex, (int)parent);
					bubbling = true;
				}
			}
		}

		private void exchange(int heapIndex, int parent)
		{
			Node tempNode = heap[heapIndex];
			heap[heapIndex] = heap[parent];
			heap[parent] = tempNode;
			pointers[heap[heapIndex].pointerIndex] = heapIndex;
			pointers[heap[parent].pointerIndex] = parent;
		}
	}

	public class Node
	{
		public PointF point;
		public int pointerIndex;
		public Double distance;
		public Node previous;
		public Boolean visited;

		public Node(PointF point, int index)
		{
			this.point = point;
			this.pointerIndex = index;
			this.previous = null;
			this.distance = Double.PositiveInfinity;
			this.visited = false;
		}

	}

}
