#include "thread-queue.h"
#include <iostream>

ThreadQueue::ThreadQueue(){
	sem_init(&s, 0, 1);
	sem_init(&n, 0, 0);
	sem_init(&e, 0, 10);
	debug = false;
}

void ThreadQueue::push(int socket){
	if(debug){
		cout << "\033[34m" <<"==DEBUG==\n" << "Sem_Wait E" << "\033[0m" <<endl;
	}
	sem_wait(&e);
	if(debug){
		cout << "\033[34m" <<"==DEBUG==\n" << "Sem_Wait S Thread" << "\033[0m" <<endl;
	}
	sem_wait(&s);
	threadQueue.push(socket);
	if(debug){
		cout << "\033[34m" <<"==DEBUG==\n" << "Sem_Post S Thread" << "\033[0m" <<endl;
	}
	sem_post(&s);
	if(debug){
		cout << "\033[34m" <<"==DEBUG==\n" << "Sem_Post N" << "\033[0m" <<endl;
	}
	sem_post(&n);	
}

int ThreadQueue::pop(){
	if(debug){
		cout << "\033[34m" <<"==DEBUG==\n" << "Sem_Wait N" << "\033[0m" <<endl;
	}
	sem_wait(&n);
	if(debug){
		cout << "\033[34m" <<"==DEBUG==\n" << "Sem_Wait S Thread" << "\033[0m" <<endl;
	}
	sem_wait(&s);
	int socket = threadQueue.front();
	threadQueue.pop();
	if(debug){
		cout << "\033[34m" <<"==DEBUG==\n" << "Sem_Post S Thread" << "\033[0m" <<endl;
	}
	sem_post(&s);
	if(debug){
		cout << "\033[34m" <<"==DEBUG==\n" << "Sem_Post E" << "\033[0m" <<endl;
	}
	sem_post(&e);
	if(debug){
		cout << "\033[34m" <<"==DEBUG==\n" << "Socket: "<<socket << "\033[0m" <<endl;
	}
	return socket;
}
