#include "server.h"

Server::Server() {
    // setup variables
    buflen_ = 1024;
    buf_ = new char[buflen_+1];
	debug = false;
	allMessages;
}

Server::~Server() {
    delete buf_;
}

void Server::run() {
    // create and run the server
    create();
    serve();
}
void Server::setDebug(bool debug0){
	debug = debug0;
}

void Server::create() {
}

void Server::close_socket() {
}

void Server::serve() {
    // setup client
    int client;
    struct sockaddr_in client_addr;
    socklen_t clientlen = sizeof(client_addr);

      // accept clients
    while ((client = accept(server_,(struct sockaddr *)&client_addr,&clientlen)) > 0) {

        handle(client);
    }
    close_socket();
}

void Server::handle(int client) {
    // loop to handle all requests
    while (1) {
        // get a request
        string request = get_request(client);
 		istringstream iss(request);

        // break if client is done or an error occurred
        if (request.empty())
            break;
		
		string response = "";

        string requestType = "";
        iss >> requestType;
	

        if(requestType == "reset"){
		if(debug){
			cout << "\033[34m" <<"==DEBUG==\n" << "RESET:"<<request << "\033[0m" <<endl;
		}
            allMessages.clear();
            response = "OK\n";
        }
        else if(requestType == "put"){
    	if(debug){
			cout << "\033[34m" <<"==DEBUG==\n" << "PUT: "<<request << "\033[0m" <<endl;
		} 
	       response = handle_put(client, request, iss);
        }
        else if(requestType == "list"){
         	if(debug){
				cout << "\033[34m" <<"==DEBUG==\n" << "LIST: "<<request << "\033[0m" <<endl;
			}
           	response = handle_list(iss);
        }
        else if(requestType == "get"){
			if(debug){
				cout << "\033[34m" <<"==DEBUG==\n" << "GET: "<<request << "\033[0m" <<endl;
			}         
	   	response = handle_get(iss);
        }
        else{
			if(debug){
				cout << "\033[34m" <<"==DEBUG==\n" << "UNKNOWN COMMAND: "<<request << "\033[0m" <<endl;
			}
            response = "error unknown command\n";
        }       
        
		// send response
        bool success = send_response(client,response);
        // break if an error occurred
        if (not success)
            break;
    }
    close(client);
}


string Server::handle_put(int client, string request, istringstream& iss){
    string name = "";
    string subject = "";
    int length = -1;
    string message = "";

    iss >> name;
    if(name =="") return "error no name subject or length provided\n";
    iss >> subject; 
    if(subject == "") return "error no subject or length provided\n";
    iss >> length;
    if(length == -1) return "error no length provided\n";

    int msgStart = request.find("\n")+1;
    message.append(buf_, msgStart,request.length()-msgStart);


    while (message.length()<length) {
        int nread = recv(client,buf_,1024,0);
        if (nread < 0) {
            if (errno == EINTR){
                // the socket call was interrupted -- try again
                continue;
            }
            else{
                // an error occurred, so break out
                return "error an unknown error occuried\n";
            }
        } else if (nread == 0) {
            // the socket is closed
            return "";
        }
        // be sure to use append in case we have binary data
       	message.append(buf_,nread);
    }


    Message temp = Message(name, subject, length, message);
    map<string,vector<Message> >::iterator it = allMessages.find(name);
    vector<Message> tmpVector;
    if(it != allMessages.end()){
        tmpVector = it->second;
        allMessages.erase(it);
    }
    tmpVector.push_back(temp);

    allMessages.insert(make_pair(name,tmpVector));

    string test = message;
    if(length>26){
    	test = test.substr(length-26, length);
	}
    return "OK\n";
}

string Server::handle_list(istringstream& iss){
    string name = "";
    iss>>name;
    if(name == ""){
		return"error no name provided\n";
	}

	string response = "list ";


    map<string,vector<Message> >::iterator it = allMessages.find(name);
    if(it == allMessages.end()){
         response.append("0\n");
    }
    else{
        string msgSize = its(allMessages.at(name).size());
        response.append( msgSize+"\n");

        for(int i = 0; i<allMessages.at(name).size(); i++){
            response.append(its(i+1)+" "+allMessages.at(name).at(i).getSubject()+"\n");
        }
    }
    return response;
}

string Server::handle_get(istringstream& iss){
	string name = "";
	string response = "";
    int index =-1;
    iss>>name;
    if(name == ""){
		return "error no name or index provided\n";
	}
	iss>>index;
    if(index == -1){
		return "error no index provided\n";
	}
	map<string,vector<Message> >::iterator it = allMessages.find(name);
    if(it == allMessages.end()){
    	return "error there are no messages for that name\n";
    }
	if(debug){
		cout << "\033[34m" <<"==DEBUG==\n" << "Index: "<<index << "\033[0m" <<endl;
	}
    if(index > allMessages.at(name).size()|| index<0){
    	if(debug){
			cout << "\033[34m" <<"==DEBUG==\n" << "Inside if statement" << "\033[0m" <<endl;
		}
		return "error index out of range\n";
    }
    response = "message ";
    response.append(allMessages.at(name).at(index-1).getSubject()+" ");
    response.append(its(allMessages.at(name).at(index-1).getLength())+"\n");
    response.append(allMessages.at(name).at(index-1).getMessage());

	return response;
}

string Server::get_request(int client) {
    string request = "";
    // read until we get a newline
    while (request.find("\n") == string::npos) {
        int nread = recv(client,buf_,1024,0);
        if (nread < 0) {
            if (errno == EINTR)
                // the socket call was interrupted -- try again
                continue;
            else
                // an error occurred, so break out
                return "";
        } else if (nread == 0) {
            // the socket is closed
            return "";
        }
        // be sure to use append in case we have binary data
        request.append(buf_,nread);
    }
    // a better server would cut off anything after the newline and
    // save it in a cache
    return request;
}

bool Server::send_response(int client, string response) {
    // prepare to send response
    const char* ptr = response.c_str();
    int nleft = response.length();
    int nwritten;
    // loop to be sure it is all sent
    while (nleft) {
        if ((nwritten = send(client, ptr, nleft, 0)) < 0) {
            if (errno == EINTR) {
                // the socket call was interrupted -- try again
                continue;
            } else {
                // an error occurred, so break out
                perror("write");
                return false;
            }
        } else if (nwritten == 0) {
            // the socket is closed
            return false;
        }
        nleft -= nwritten;
        ptr += nwritten;
    }
    return true;
}

string Server::its(int num){
        stringstream ss;
        ss << num;
        string str = ss.str();

        return str;
}
