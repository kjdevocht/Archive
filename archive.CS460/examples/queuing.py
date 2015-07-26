import sys
sys.path.append('..')
import matplotlib
import pylab

from src.sim import Sim
from src import node
from src import link
from src import packet

from networks.network import Network

import random

class Generator(object):
    def __init__(self,node,destination,load,duration):
        self.node = node
        self.load = load
        self.duration = duration
        self.start = 0
        self.ident = 1
        self.destination = destination

    def handle(self,event):
        # quit if done
        now = Sim.scheduler.current_time()
        if (now - self.start) > self.duration:
            return

        # generate a packet
        self.ident += 1
        p = packet.Packet(destination_address=destination,ident=self.ident,protocol='delay',length=1000)
        Sim.scheduler.add(delay=0, event=p, handler=self.node.send_packet)
        # schedule the next time we should generate a packet
        Sim.scheduler.add(delay=random.expovariate(self.load), event='generate', handler=self.handle)

class DelayHandler(object):
    def receive_packet(self,packet):
        global total_delay
        global num_packets
        total_delay += packet.queueing_delay
        num_packets += 1
        #print Sim.scheduler.current_time(),packet.ident,packet.created,Sim.scheduler.current_time() - packet.created,packet.transmission_delay,packet.propagation_delay,packet.queueing_delay

if __name__ == '__main__':
    # parameters
    Sim.scheduler.reset()

    # setup network
    net = Network('../networks/one-hop.txt')

    # setup routes
    n1 = net.get_node('n1')
    n2 = net.get_node('n2')
    n1.add_forwarding_entry(address=n2.get_address('n1'),link=n1.links[0])
    n2.add_forwarding_entry(address=n1.get_address('n2'),link=n2.links[0])

    # setup app
    d = DelayHandler()
    net.nodes['n2'].add_protocol(protocol="delay",handler=d)
    #meu = max_rate
    rho = 0

    # setup packet generator
    destination = n2.get_address('n1')
    meu = 1000000/(1000*8)
    actual_points = []
    theoretical_points = []
    utilizations = [0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.8,0.95,0.98]
    for util in utilizations:
        Sim.scheduler.reset()
        #global total_delay
        #global num_packets
        total_delay = 0
        num_packets = 0
        lam = util *meu
        g = Generator(node=n1,destination=destination,load=lam,duration=10)
        Sim.scheduler.add(delay=0, event='generate', handler=g.handle)
        Sim.scheduler.run()
        actual_delay = total_delay/num_packets
        actual_points.append(actual_delay)
        rho = lam/meu

        theoretical_delay = (1.0/(2.0*meu)) * (rho/(1.0-rho))
        theoretical_points.append(theoretical_delay)

    #print "Utilization count: " + utilizations.count()
    #print "Actual Delay count: " + actual_points.count()
    pylab.clf()
    pylab.plot(utilizations,actual_points, color = 'b', label = 'Actual Delay')
    pylab.plot(utilizations, theoretical_points, color = 'g', label = 'Theoretical Delay')
    pylab.xlabel('Utilization')
    pylab.ylabel('Queuing Delay')
    pylab.legend(loc='upper left')
    pylab.savefig("test3.png")



