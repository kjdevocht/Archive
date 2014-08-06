"""
A TCP echo server that handles multiple clients with polling.  Typing
Control-C will quit the server.
"""

import argparse

from poller import Poller

class Main:
    """ Parse command line options and perform the download. """

    def __init__(self):
        self.parse_arguments()
        self.debug = False

    def parse_arguments(self):
        ''' parse arguments, which include '-p' for port '''
        parser = argparse.ArgumentParser(prog='Echo Server', description='A simple echo server that handles one client at a time', add_help=True)
        parser.add_argument('-p', '--port', type=int, action='store', help='port the server will bind to',default=3000)
        parser.add_argument('-d', '--debug', action='store_true', help='turns on debugging')
        self.args = parser.parse_args()
        self.debug = self.args.debug
        print "Debug: %s" % str(self.debug)

    def run(self):
        p = Poller(self.args.port)
        p.run()

if __name__ == "__main__":
    m = Main()
    m.parse_arguments()
    try:
        m.run()
    except KeyboardInterrupt:
        pass
