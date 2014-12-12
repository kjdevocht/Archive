#include <stdlib.h>
#include <unistd.h>

#include <iostream>

#include "inet-client.h"

using namespace std;

int
main(int argc, char **argv)
{
    int option;

    // setup default arguments
    int port = 5000;
    string host = "localhost";
	bool debug = false;


    // process command line options using getopt()
    // see "man 3 getopt"
    while ((option = getopt(argc,argv,"dh:p:")) != -1) {
        switch (option) {
			case 'd':
				debug = true;
				break;
            case 'p':
                port = atoi(optarg);
                break;
            case 'h':
                host = optarg;
                break;
            default:
                cout << "client [-h host] [-p port]" << endl;
                exit(EXIT_FAILURE);
        }
    }

    InetClient client = InetClient(host, port);
	client.setDebug(debug);
    client.run();
}
