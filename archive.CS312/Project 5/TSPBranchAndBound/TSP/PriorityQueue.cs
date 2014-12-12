using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TSP
{
    class PriorityQueue
    {
        private int count;
        SortedDictionary<double, Queue> queue;

        public PriorityQueue()
        {
            this.count = 0;
            this.queue = new SortedDictionary<double, Queue>();
        }

        public int Count
        {
            get { return count; }
        }

        public bool isEmpty()
        {
            return count == 0;
        }

        public State Pop()
        {
            if (isEmpty())
            {
                throw new InvalidOperationException("Queue Is Empty");
            }
            var key = queue.First();
            Queue q = key.Value;
            State state = (State)q.Dequeue();
            if (q.Count == 0)
            {
                queue.Remove(key.Key);
            }
            count--;
            return state;
        }

        public void Push(State state, double priority)
        {
            if (!queue.ContainsKey(priority))
            {
                queue.Add(priority, new Queue());
            }
            queue[priority].Enqueue(state);
            count++;
        }

        public bool Has(State state)
        {
            double priority = state.lowerbound;
            if (!queue.ContainsKey(priority))
            {
                return false;
            }
            return queue[priority].Contains(state);
        }



    }
}
