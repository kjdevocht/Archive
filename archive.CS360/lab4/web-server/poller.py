import errno
import select
import socket
import sys
import traceback
import time

from log import Log
from wsgiref.handlers import format_date_time
from datetime import datetime
from time import mktime

class Poller:
    """ Polling server """
    def __init__(self,port, debug):
        self.host = ""
        self.port = port
        self.open_socket()
        self.clients = {}
        self.size = 1024
        self.test = 1
        self.log = Log(debug)
        self.hosts = {}
        self.mediaTypes = {}
        self.timeOut = 0
        self.method = ""
        self.url = ""
        self.version = ""
        self.statusCode = ""
        self.phrase = ""
        self.entityBody = ""
        self.headers = ""
        self.cache = {}
        self.lastTouched = {}
        self.fileType = ""


    def loadHostFile(self):
        file = open("web.conf", "r")
        hostFile = file.read()
        params = hostFile.split("\n")
        for param in params:
            if "host" in param:
                info = param.split()
                info.pop(0)
                host = info.pop(0)
                path = info.pop(0)
                self.hosts[host] = path
            elif "media" in param:
                mediaType = param.split()
                mediaType.pop(0)
                extension = mediaType.pop(0)
                contentType = mediaType.pop(0)
                self.mediaTypes[extension] = contentType
            elif "timeout" in param:
                param = param.split()
                self.timeOut = param.pop(2)



    def open_socket(self):
        """ Setup the socket for incoming clients """
        try:
            self.server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self.server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR,1)
            self.server.bind((self.host,self.port))
            self.server.listen(5)
            self.server.setblocking(0)
        except socket.error, (value,message):
            if self.server:
                self.server.close()
            print "Could not open socket: " + message
            sys.exit(1)

    def run(self):
        self.loadHostFile()
        """ Use poll() to handle each incoming client."""
        self.poller = select.epoll()
        self.pollmask = select.EPOLLIN | select.EPOLLHUP | select.EPOLLERR
        self.poller.register(self.server,self.pollmask)
        while True:
            # poll sockets
            try:
                fds = self.poller.poll(timeout=1)
            except:
                return
            for (fd,event) in fds:
                # handle errors
                if event & (select.POLLHUP | select.POLLERR):
                    self.handleError(fd)
                    continue
                # handle the server socket
                if fd == self.server.fileno():
                    self.handleServer()
                    continue
                # handle client socket
                result = self.handleClient(fd)
                self.lastTouched[fd] = time.time()

            deleteClient = {}
            for fd in self.clients:
                timeIdle = time.time()-self.lastTouched[fd]
                if timeIdle > float(self.timeOut):
                    self.poller.unregister(fd)
                    self.clients[fd].close()
                    deleteClient[fd] = self.clients[fd]

            for fd in deleteClient:
                del self.clients[fd]


    def handleError(self,fd):
        self.poller.unregister(fd)
        if fd == self.server.fileno():
            # recreate server socket
            self.server.close()
            self.open_socket()
            self.poller.register(self.server,self.pollmask)
        else:
            # close the socket
            self.clients[fd].close()
            del self.clients[fd]

    def handleServer(self):
        # accept as many clients are possible
        while True:
            try:
                (client,address) = self.server.accept()
            except socket.error, (value,message):
                # if socket blocks because no clients are available,
                # then return
                if value == errno.EAGAIN or errno.EWOULDBLOCK:
                    return
                print traceback.format_exc()
                sys.exit()
            # set client socket to be non blocking
            client.setblocking(0)
            self.clients[client.fileno()] = client
            self.lastTouched[client.fileno()] = time.time()
            self.poller.register(client.fileno(),self.pollmask)

    def handleClient(self,fd):
        try:
            data = self.clients[fd].recv(self.size)
        except socket.error, (value,message):
            # if no data is available, move on to another client
            if value == errno.EAGAIN or errno.EWOULDBLOCK:
                return
            print traceback.format_exc()
            sys.exit()

        if data:
            if fd in self.cache:
               self.cache[fd] = self.cache[fd] + data
            else:
                self.cache[fd] = data
            if self.cache[fd].endswith("\r\n\r\n"):
                response = self.processRequest(self.cache[fd])
                self.clients[fd].send(response)
                self.cache[fd] = ""
        else:
            self.poller.unregister(fd)
            self.clients[fd].close()
            del self.clients[fd]

    def processRequest(self, request):
        peices = request.split("\n")
        requestLine = peices.pop(0)
        self.processRequestLine(requestLine)
        if self.method == "GET":
            for header in peices:
                if "Host:" in header:
                    headerInfo = header.split()
                    hostInfo = headerInfo.pop(1)
                    hostInfo = hostInfo.split(":")
                    self.host = hostInfo.pop(0)
            path = self.hosts[self.host]
            try:
                with open(path+self.url,'r') as file:
                    self.entityBody = file.read()
                    self.log.log("Entity Body: " + self.entityBody)
                    if self.entityBody == "Forbidden\n":
                        raise IOError(13, 'File forbidden')
                file.close()
                self.statusCode = "200"
                self.phrase = "OK"
                time = self.getCurrentTime()
                self.headers = "Date: " + time + "\r\n"
                self.headers += "Server: DungusMcfinnigan/5.4.3.2.1 (ChinaTown)\r\n"
                self.headers += "Content-Type: " + self.mediaTypes[self.fileType] + "\r\n"
                self.headers += "Content-Length: " + str(len(self.entityBody)) + "\r\n"
                #self.log.log(self.headers)
            except IOError as e:
                self.log.log("Error Number: " + str(e.errno))
                if e.errno == 2:
                    self.statusCode = "404"
                    self.phrase = "Not Found"
                    self.entityBody = "<h1>The file you requested could not be found</h1>"
                    time = self.getCurrentTime()
                    self.headers = "Date: " + time + "\r\n"
                    self.headers += "Server: DungusMcfinnigan/5.4.3.2.1 (ChinaTown)\r\n"
                    self.headers += "Content-Type: text/html\r\n"
                    self.headers += "Content-Length: " + str(len(self.entityBody)) + "\r\n"

                if e.errno == 13:
                    self.statusCode = "403"
                    self.phrase = "Forbidden"
                    self.entityBody = "<h1>You do not have permission to access this file</h1>"
                    time = self.getCurrentTime()
                    self.headers = "Date: " + time + "\r\n"
                    self.headers += "Server: DungusMcfinnigan/5.4.3.2.1 (ChinaTown)\r\n"
                    self.headers += "Content-Type: text/html\r\n"
                    self.headers += "Content-Length: " + str(len(self.entityBody)) + "\r\n"


        response = self.version + " " + self.statusCode + " " + self.phrase + "\r\n" + self.headers + "\r\n" + self.entityBody

        return response


    def getCurrentTime(self):
        now = datetime.now()
        stamp = mktime(now.timetuple())
        time = format_date_time(stamp)
        return time

    def processRequestLine(self, requestLine):
        requestLine = requestLine.split()
        self.method = requestLine.pop(0)
        if self.method == 'GET':
            url = requestLine.pop(0)
            #self.log.log("\n***************************\nURL: "+url+"\n***************************\n")
            if url == "/":
                url = "/index.html"
            self.url = url
            fileType = url.split(".")
            fileType = fileType[-1]
            if fileType in self.mediaTypes.keys():
                self.fileType = fileType
            else:
                self.fileType = "txt"
            #self.log.log("File Type: " + self.fileType)
            self.version = requestLine.pop(0)
        else:
            self.statusCode = "501"
            self.phrase = "Not Implemented"
            self.entityBody = "The Request Type has not been implemented on my Server\r\n"
            time = self.getCurrentTime()
            self.headers = "Date: " + time + "\r\n"
            self.headers += "Server: DungusMcfinnigan/5.4.3.2.1 (ChinaTown)\r\n"
            self.headers += "Content-Type: text/html\r\n"
            self.headers += "Content-Length: " + str(len(self.entityBody)) + "\r\n"







