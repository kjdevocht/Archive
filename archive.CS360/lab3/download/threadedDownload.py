import argparse
import os
import sys
import requests
import threading
import math
import time


''' Downloader for a set of files '''
class Downloader:
    def __init__(self):
        ''' initialize the file where the list of URLs is listed, and the
        directory where the downloads will be stored'''
        self.args = None
        self.url = None
        self.size = 1
        self.threads = 1
        self.byteIndex = 1
        self.filename = None
        self.parse_arguments()

    def parse_arguments(self):
        ''' parse arguments, which include '-i' for input file and
        '-d' for download directory'''
        parser = argparse.ArgumentParser(prog='Mass downloader', description='A simple script that downloads multiple files from a list of URLs specified in a file', add_help=True)
        parser.add_argument('-n', '--threads', type=int, action='store', help='Specify the number of threads to use',default=1)
        parser.add_argument('url')
        args = parser.parse_args()
        self.url = args.url
        if self.url[-1] == "/":
            self.filename = "index.html"
        else:
            self.filename = [self.url.split('/')[-1].strip()]
        self.threads = args.threads

    def download(self):
        try:
            r = requests.head(self.url, stream=True)
            if r.headers['content-length'] > 0:
                self.size = int(r.headers['content-length'])
                chunk = math.floor(self.size/self.threads)
                chunk = int(chunk)
                threadSizes = []
                for x in range(self.threads):
                    threadSizes.append(chunk)
                leftOvers = self.size-chunk*self.threads
                threadSizes[-1]+=leftOvers-1
                threads = []
                t0 = time.clock()
                for size in range(len(threadSizes)):
                    d = DownThread(self.url,threadSizes[size], self.byteIndex)
                    self.byteIndex += threadSizes[size]
                    threads.append(d)
                for t in threads:
                    t.start()
                for t in threads:
                    t.join()
                for i in range(len(threads)):
                    file= open(self.filename[-1], 'a')
                    file.write(threads[i].r.content)
                    file.close()
                    t1 = time.clock()-t0
                print("%s %s %s %s" %(self.url, self.threads, self.size, t1))
        except TypeError, ex:
            print "TypeError:", ex


''' Use a thread to download one file given by url and stored in filename'''
class DownThread(threading.Thread):
    def __init__(self,url, size, byteIndex):
        self.url = url
        self.r = None
        self.size = size
        self.byteIndex = byteIndex
        threading.Thread.__init__(self)
        self._content_consumed = False

    def run(self):
        #print 'Downloading %s' % self.url
        s = requests.Session()
        range = 'bytes=%s-%s' % (self.byteIndex, self.byteIndex+self.size)
        headers = {'range': range}
        self.r = requests.get(self.url, headers=headers, stream=True)
 
if __name__ == '__main__':
    d = Downloader()
    d.download()
