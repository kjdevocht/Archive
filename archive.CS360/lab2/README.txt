Semaphores:
	Semaphores were used in two files. thread-queue.cc(declared in thread-queue.h) and server.cc(declared in server.h)
	In server.cc I use a ThreadQueue object called threadQueue.  threadQueue is a thread safe object (see thread-queue.cc).
	For my messages I stored them in a map. In order to make them thread safe I wrapped each call to the map with a semaphore
	wait and post pair.
