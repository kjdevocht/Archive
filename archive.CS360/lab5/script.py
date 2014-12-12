__author__ = 'kdevocht'
from math import pow
from math import factorial
from math import fabs

lam = float(1)/20
meu = float(1)/18
roe = lam/meu

queueLength = (roe*roe)/(1-roe)
print "Question 1:"

print "\tThe average length of the queue is " + str(queueLength) + " people."

queueTime = roe/(meu-lam)

print "\tThe average wait time in the queue is " +str(queueTime) + " seconds."

print "Question 2:"
lam = float(2)/3
meu = float(1)/3
roe = lam/meu
N = 3
sumN = 0
for i in range(0, N):
    sumN += pow(roe, i)/factorial(i)


sumN += pow(roe, N)/(factorial(N)*(1-roe/N))

pNot = 1/sumN
temp = (1-roe)/N
qNot = (pNot*(pow(roe,N+1))/(factorial(N)*N))*fabs(1/(pow(temp,2)))
print "\tThe average length of the queue is " + str(qNot) + " people."

wNot = ((roe+qNot)/lam)-(1/meu)
print "\tThe average wait time in the queue is " + str(wNot) + " seconds."
probFive = (pow(roe,5)*pNot)/(pow(N,5-N)*factorial(N))
print "\tThe probability of having 5 users in the system is " + str(probFive)

print "Question 3:"
lam = float(9)/60
meu = float(1)/6
roe = lam/meu
qNot = pow(roe,2)/(2*(1-roe))
print "\tThe average length of the queue is " + str(qNot) + " people."
wNot = (1/(2*meu))*(roe/(1-roe))
print "\tThe average wait time in the queue is " + str(wNot) + " seconds."
tNot = (1/(2*meu))*((2-roe)/(1-roe))
print "\tThe average wait time in the queue is " + str(tNot) + " seconds."