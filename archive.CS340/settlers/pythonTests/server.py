import requests
import json
import subprocess
import os, signal
import time
import socket;
import threading
import server

def run_command(cmd):
	print cmd
	os.system(cmd)

def start():
	
	if isPortOpen():
		kill()

	threading.Thread(target=run_command, args=("(cd .. ; ant our-server) >> log.txt", )).start()
	
	while not isPortOpen():		
		time.sleep(1.0)


def isPortOpen():
	sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	result = sock.connect_ex(('127.0.0.1',8081))
	return result == 0

def kill():
	os.system("fuser -k 8081/tcp")
