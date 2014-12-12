#include <semaphore.h>
#include <stdio.h>
#include <queue>


using namespace std;

class ThreadQueue{
	public:
		ThreadQueue();
		void push(int);
		int pop();

	private:
		queue<int> threadQueue;
		sem_t s;
		sem_t n;
		sem_t e;
		bool debug;

}; 
