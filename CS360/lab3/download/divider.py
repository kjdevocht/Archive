import argparse
import math

parser = argparse.ArgumentParser(prog='Divider', description='A simple test for dividing', add_help=True)
parser.add_argument('threads', type=int)
parser.add_argument('fileSize', type=int)
args = parser.parse_args()

chunk = math.floor(args.fileSize/args.threads)
print str(chunk)
threadLst =[]
for x in range(args.threads):
	print str(x)
	threadLst.append(chunk)

leftOvers = args.fileSize-chunk*args.threads
threadLst[args.threads-1]+=leftOvers

for y in range(len(threadLst)):
	print threadLst[int(y)]