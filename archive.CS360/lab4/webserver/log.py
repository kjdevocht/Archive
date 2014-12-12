__author__ = 'kjdevocht'

class Log:
    def __init__(self, debug):
        self.file = 0
        self.debug = debug



    def log(self, message):
        if self.debug:
            print "Debug:\n " + message
