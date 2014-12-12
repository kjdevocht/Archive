import urllib2;
import urllib;
import httplib
import json
# def makeGame():


def makePostRequest(url, body, headers):

	url = "http://localhost:8081"+url
	print url
	req = urllib2.Request(url)
	for key, value in headers.iteritems():
		req.add_header(key, value)

	r = urllib2.urlopen(req, json.dumps(body))
	body = r.read()
	r.close()
	return {'r':r, 'b':body, 'h':r.info().dict}

def registerPlayer(username, password):
	r = makePostRequest("/user/register", {'username': username,'password': password}, {"Content-type": "application/json"})
	cookie = r['h']['set-cookie']
	return  cookie

def loginPlayer(username, password):
	r = makePostRequest("/user/login", {'username': username,'password': password}, {"Content-type": "application/json"})
	cookie = r['h']['set-cookie']
	return cookie

def createGame(name):
	r = makePostRequest("/games/create", {'randomTiles': False,'randomNumbers': False, 'randomPorts': False, 'name':name}, {"Content-type": "application/json"})
	r['b'] = json.loads(r['b'])
	return r['b']['id']

def joinGame(gameId, cookie, color):
	url = "/games/join"
	body = {'id':gameId, 'color':color}
	headers = {"Content-Type": "application/json", "cookie":cookie.replace("Path=/;","")}
	r = makePostRequest(url, body, headers)
	print r['b']
	print r['r'].status

gameId = createGame("testgame1")
player1 = registerPlayer("p1", "p1")
# player1 = loginPlayer("p1","p1")
print gameId
print player1
joinGame(gameId, player1, "blue")
