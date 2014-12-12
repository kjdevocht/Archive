

__author__ = 'kdevocht'
import requests
import time
import numpy
import matplotlib
matplotlib.use('Agg')
from pylab import *
import os
from matplotlib.pyplot import clf, boxplot

lightArray = []
myArray = []
num = 100
for x in range(0, num):
    timeBefore = time.time()
    r= requests.get("http://127.0.0.1:3000/file999.txt")
    timeAfter = time.time()
    elapsed = timeAfter-timeBefore
    lightArray.append(elapsed)

avg = numpy.mean(lightArray)
lightMeu = 1/avg
print lightMeu

for x in range(0, num):
    timeBefore = time.time()
    r= requests.get("http://localhost:8080/file999.txt")
    timeAfter = time.time()
    elapsed = timeAfter-timeBefore
    myArray.append(elapsed)

avg = numpy.mean(myArray)
myMeu = 1/avg
print myMeu

graphAid = 100

lightMeuDif = lightMeu/graphAid
myMeuDif = myMeu/graphAid
lightX = []
lightY= []
myX = []
myY = []

clf()
for i in range(0, graphAid):
    lam = i*lightMeuDif
    x = lam/lightMeu
    y = 1/(lightMeu-lam)
    lightX.append(x)
    lightY.append(y)
plot(lightX,lightY)
title("Theoretical-lighttpd Server")
xlabel("rho")
ylabel("1/(mu-lamda)")
savefig('theoretical-lighttpd.png')

clf()
for i in range(0, graphAid):
    lam = i*myMeuDif
    x = lam/myMeu
    y = 1/(myMeu-lam)
    myX.append(x)
    myY.append(y)
plot(myX,myY)
title("Theoretical-My Server")
xlabel("rho")
ylabel("1/(mu-lamda)")
savefig('theoretical-myServer.png')

light_workloads = []
server_workloads = []
light_load_data = []
server_load_data = []
duration = 10
lightPort = 3000
myPort = 8080
interval = lightMeu/10
for i in range(0, 10):
    workload = (i*interval)+1
    light_workloads.append(workload)
    outputFile = "light-server-%s.txt" % int(workload)
    light_load_data.append(outputFile)
    os.system("python /home/kjdevocht/testing/tests/generator.py --port %s -l %s -d %s >> %s" % (lightPort, int(workload), duration, outputFile))

print "Done with Light Server"

interval = myMeu/10
for i in range(0, 10):
    workload = (i*interval)+1
    server_workloads.append(workload)
    outputFile = "web-server-%s.txt" % int(workload)
    server_load_data.append(outputFile)
    os.system("python /home/kjdevocht/testing/tests/generator.py --port %s -l %s -d %s >> %s" % (myPort, int(workload), duration, outputFile))

print "Done Testing Load"

light_box_x = []
light_box_y = []
server_box_x = []
server_box_y = []

def plotEvaluationGraph(x_values, y_values, serverName):
    clf()
    if serverName is "myServer":
        plot(myX, myY)
    else:
        plot(lightX, lightY)

    for i in range(0,len(x_values)):
        xPoints = x_values[i]
        yPoints = y_values[i]
        boxplot(yPoints, widths=.05, positions=xPoints, whis=2)
        xlim([0,1])
        ylim([0,1])
        xlabel('rho')
        ylabel("1/(mu-lamda)")
        savefig(serverName + 'Boxplot.png')

for dataFile in server_load_data:
    xWrapper = []
    yWrapper = []

    try:
        f = open(dataFile, "r")
        split_name = dataFile.split("-")
        file_workload = split_name[2]
        file_workload = file_workload.split(".")
        lam = float(file_workload[0])
        x = lam /myMeu
        xWrapper.append(x)
        server_box_x.append(xWrapper)

        for line in f.readlines():
            line_split = line.split()
            if line_split[2] == '200':
                y = float(line_split[5])
                yWrapper.append(y)
        server_box_y.append(yWrapper)
    except IOError as e:
        print e.message
    plotEvaluationGraph(server_box_x, server_box_y, "myServer")

print "Done Plotting My Server box plots"

for dataFile in light_load_data:
    xWrapper = []
    yWrapper = []

    try:
        f = open(dataFile, "r")
        split_name = dataFile.split("-")
        file_workload = split_name[2]
        file_workload = file_workload.split(".")
        lam = float(file_workload[0])
        x = lam /lightMeu
        xWrapper.append(x)
        light_box_x.append(xWrapper)

        for line in f.readlines():
            line_split = line.split()
            if line_split[2] == '200':
                y = float(line_split[5])
                yWrapper.append(y)
        light_box_y.append(yWrapper)
    except IOError as e:
        print e.message
    plotEvaluationGraph(light_box_x, light_box_y, "lighttpd")

print "Done Plotting Box Plots"




