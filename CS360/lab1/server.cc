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

void Server::setDebug(bool debug0)
{
    this->debug = debug0;
}

void
Server::run() {
    // create and run the server
    create();
    serve();
}

void
Server::create() {
}

void
Server::close_socket() {
}

void
Server::serve() {
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

void
Server::handle(int client) {
    // loop to handle all requests
    while (1) {
        // get a request
        string response = get_request(client);
        // break if client is done or an error occurred
        if (response.empty())
            break;

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

    iss >> name;  cout<<"Name: "<<name<<endl;
    if(name =="") return "error no name subject or length provided\n";
    iss >> subject; cout<<"Subject: "<<subject<<endl;
    if(subject == "") return "error no subject or length provided\n";
    iss >> length; cout<<"Length: "<<length<<endl;
    if(length == -1) return "error no length provided\n";

    int msgStart = request.find("\n")+1;
    message.append(buf_, msgStart,request.length()-msgStart);


    while (message.length()<length) {
        if(debug) cout<<"DEBUG: server.cc-get_request--length: "<<length<<endl;
        if(debug) cout<<"DEBUG: server.cc-get_request--message.length(): "<<message.length()<<endl;
        int nread = recv(client,buf_,1024,0);
        if (nread < 0) {
        if(debug) cout<<"DEBUG: server.cc-get_request: nread < 0"<<endl;
            if (errno == EINTR){
                // the socket call was interrupted -- try again
                continue;
            }
            else{
                // an error occurred, so break out
                if(debug) cout<<"DEBUG: server.cc-get_request: ERROR"<<endl;
                return "error an unknown error occuried\n";
            }
        } else if (nread == 0) {
            // the socket is closed
            if(debug) cout<<"DEBUG: server.cc-get_request: Socket Closed"<<endl;
            return "";
        }
        // be sure to use append in case we have binary data
        message.append(buf_,nread);
        /*if(message.length()>length){
           message =  message.substr (0,length);
        }*/
        if(debug) cout<<"DEBUG: server.cc-get_request--message.length(): "<<message.length()<<endl;
        //if(debug) cout<<"DEBUG: server.cc-get_request--message: "<<message<<endl;
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
    if(debug) cout<<"DEBUG: server.cc-get_request--message end : "<<test<<endl;
    if(debug) cout<<"DEBUG: server.cc-get_request: Message done reading in"<<endl;}
    return "OK\n";
}
string Server::handle_list(string name){
    string response = "list ";


    map<string,vector<Message> >::iterator it = allMessages.find(name);
    if(it == allMessages.end()){
    if(debug) cout<<"DEBUG: server.cc-get_request:  No messages for user found "<<endl;
         response.append("0\n");
    }
    else{
        string msgSize = its(allMessages.at(name).size());
        response.append( msgSize+"\n");

        for(int i = 0; i<allMessages.at(name).size(); i++){
            response.append(its(i+1)+" "+allMessages.at(name).at(i).getSubject()+"\n");
        }
    }
    if(debug) cout<<"DEBUG: server.cc-handle_list--response: "<<response<<endl;
    return response;
}

string
Server::get_request(int client) {
    string request = "";
    string response = "";
    int nread = 0;
    // read until we get a newline
    while (request.find("\n") == string::npos) {
        nread = recv(client,buf_,64,0);
        if (nread < 0) {
            if (errno == EINTR)
                // the socket call was interrupted -- try again
                continue;
            else
                // an error occurred, so break out
                return "error  an unknown error has occured\n";
        } else if (nread == 0) {
            // the socket is closed
            return "";
        }
        // be sure to use append in case we have binary data
        request.append(buf_,nread);
    }
        istringstream iss(request);
        string requestType = "";
        iss >> requestType;
        if(debug) cout<<"DEBUG: server.cc-get_request--requestType: "<<requestType<<endl;
        if(requestType == "reset"){

            allMessages.clear();
            response = "OK\n";
        }
        else if(requestType == "put"){
            response = handle_put(client, request, iss);
        }
        else if(requestType == "list"){
            string name = "";
            iss>>name;
            if(name == "") return "error no name provided\n";
            response = handle_list(name);
        }
        else if(requestType == "get"){
            string name = "";
            int index =-1;
            iss>>name;
            cout<<"name: "<<name<<endl;
            if(name == "") return "error no name or index provided\n";
            iss>>index;
            if(index == -1) return "error no index provided\n";
            if(debug) cout<<"DEBUG: server.cc-get_request--index "<<index<<endl;
            map<string,vector<Message> >::iterator it = allMessages.find(name);
            if(it == allMessages.end()){
                return "error there are no messages for that name\n";
            }

            if(index > allMessages.at(name).size()+1|| index<0){
                return "error index out of range";
            }
            response = "message ";
            response.append(allMessages.at(name).at(index-1).getSubject()+" ");
            response.append(its(allMessages.at(name).at(index-1).getLength())+"\n");
            response.append(allMessages.at(name).at(index-1).getMessage());

        }
        else{
            if(debug) cout<<"DEBUG: server.cc-get_request--requestType unknown: "<<requestType<<endl;
            response = "error unknown command\n";
        }
    //cout<<"Response: "<<response<<endl;
    return response;
}

bool
Server::send_response(int client, string response) {
    // prepare to send response
    //if(debug)  cout<<"DEBUG: server.cc-send_response--response: "<<response<<endl;
    const char* ptr = response.c_str();
    int nleft = response.length();

    int nwritten;
    // loop to be sure it is all sent
    while (nleft) {
       // if(debug) cout<<"DEBUG: server.cc-send_response--nleft: "<<nleft<<endl;
        //if(debug) cout<<"DEBUG: server.cc-send_response--ptr: "<<ptr<<endl;
        if ((nwritten = send(client, ptr, nleft, 0)) < 0) {
            if (errno == EINTR) {
                // the socket call was interrupted -- try again
                continue;
            } else {
                // an error occurred, so break out
                if(debug) cout<<"DEBUG: server.cc-send_response: error occurried"<<endl;
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
