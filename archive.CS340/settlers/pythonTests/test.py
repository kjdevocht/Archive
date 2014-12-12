import requests
import json
import subprocess
import os, signal
import time
import socket;
import threading
import server
import unittest
import urllib2

#SETUP 
def loginUser(username, password):
	s = requests.Session()
	url = 'http://localhost:8081/user/login'
	body = {'username':username, 'password':password}
	r = s.post(url, json.dumps(body))
	return {'session':s, 'request':r}

def registerUser(username, password):
	s = requests.Session()
	url = 'http://localhost:8081/user/register'
	body = {'username':username, 'password':password}
	r = s.post(url, json.dumps(body))
	return {'session':s, 'request':r}

def joinGame(gameId, color, s):
	url = 'http://localhost:8081/games/join'
	body = {'id':gameId, 'color':color}
	r = s.post(url, json.dumps(body))
	return {'session':s, 'request':r}

def createGame(randomTiles, randomNumbers, randomPorts, name, s = None):
	if s == None:
		s = requests.Session()

	url = 'http://localhost:8081/games/create'
	body = {
			"randomTiles": randomTiles,
			"randomNumbers": randomNumbers,
			"randomPorts": randomPorts,
			"name": name
			}

	r = s.post(url, json.dumps(body))
	return {'session':s, 'request':r}

def listGames(s = None):
  if s == None:
    s = requests.Session()
  url = 'http://localhost:8081/games/list'
  r = s.get(url)
  return {'session':s, 'request':r}

def gameModel(s = None):
  if s == None:
    s = requests.Session()
  url = 'http://localhost:8081/game/model'
  r = s.get(url)
  return {'session':s, 'request':r}

def moveCommand(jsonData, players):
  url = 'http://localhost:8081/moves/'
  moveType = jsonData['type'];

  s = players[jsonData['playerIndex']]
  r = s.post(url+moveType, json.dumps(jsonData))
  return json.loads(r.text)

def saveGame(gameId, name):
  url = 'http://localhost:8081/games/save'
  
  s = requests.Session()

  body = {
      "id": gameId,
      "name": name
      }
  r = s.post(url, json.dumps(body))

  if r.status_code != 200:
    print "THERE WAS AN ERROR SAVE"

def loadGame(name):
  url = 'http://localhost:8081/games/load'
  s = requests.Session()

  body = {
      "name": name
      }
  r = s.post(url, json.dumps(body))

  if r.status_code != 200:
    print "THERE WAS AN ERROR LOAD"

def getGameIdFromName(name, s=None):
  listData = listGames(s)
  jsonArray = json.loads(listData['request'].text)
  for game in jsonArray:
    if game['title'] == name:
      return game['id']
  return -1;


#TESTS
def registerRepeatUser():
	user1 = registerUser("player1", "player1")

	#repeat check
	if not checkIfRegisterFailedCorrectly(registerUser("player1","player1")):
		print "Failed repeat user"
		return False;

	#username check
	if not checkIfRegisterFailedCorrectly(registerUser("12","player1")):
		print "Failed short username"
		return False;
	if not checkIfReisterSuccessCorrectly(registerUser("123","player1"), "123","player1"):
		print "Failed right size username =3"
		return False;
	if not checkIfReisterSuccessCorrectly(registerUser("1234","player1"), "1234","player1"):
		print "Failed right size username =7"
		return False;
	if not checkIfRegisterFailedCorrectly(registerUser("12345678","player1")):
		print "Failed right to big username"
		return False;
	if not checkIfReisterSuccessCorrectly(registerUser("a---b","player1"), "a---b","player1"):
		print "Failed to allow dash"
		return False;	
	if not checkIfReisterSuccessCorrectly(registerUser("a___b","player1"), "a___b","player1"):
		print "Failed to allow underscore"
		return False;
	if not checkIfRegisterFailedCorrectly(registerUser("a$$$$b","player1")):
		print "Failed allowed $$$$ username" 
		return False;

	#password check
	if not checkIfRegisterFailedCorrectly(registerUser("11111","1234")):
		print "Failed to small password"
		return False;
	if not checkIfReisterSuccessCorrectly(registerUser("11112","12345"), "11112","12345"):
		print "Failed right size username =5"
		return False;
	if not checkIfReisterSuccessCorrectly(registerUser("11113","a---b"), "11113","a---b"):
		print "Failed to allow dash"
		return False;	
	if not checkIfReisterSuccessCorrectly(registerUser("11114","a____b"), "11114","a____b"):
		print "Failed to allow underscore"
		return False;
	if not checkIfRegisterFailedCorrectly(registerUser("11115","a&&&&b")):
		print "Failed allowed &&&&& password" 
		return False;

	return True


def checkIfRegisterFailedCorrectly(user):
	success = True
	if user['request'].status_code != 400:
		print "Failed Status Code"
		success = False
	if not checkHeaders(user['request'].headers, {'content-type':'text/plain'}):
		print "Failed Headers"
		success = False
	if "set-cookie" in user['request'].headers:
		print "should not have cookie set"
		success = False
	if not len(user['request'].text) > 0:
		print "Failed request text"
		success = False
	return success

def registerValidUser():
	user = registerUser("player1", "player1")
	return checkIfReisterSuccessCorrectly(user, "player1", "player1")

def checkIfReisterSuccessCorrectly(user, username, password):
	success = True
	if user['request'].status_code != 200:
		print "Failed Status Code"
		success = False
	if not checkCookie(user['request'].headers, username, password):
		print "Cookie Check Failed"
		success = False
#	if not len(user['request'].text) == 0:
#		print "Failed request text-  should be empty"
#		success = False
	return success	

def loginValidUser(username, password):
	user = loginUser(username, password)
	success = True
	if user['request'].status_code != 200:
		print "Failed Status Code got " + user['request'].status_code;
		success = False
	if not checkCookie(user['request'].headers, username, password):
		print "Cookie Check Failed"
		success = False
	return success

def loginBadUser(username, password):
	user = loginUser(username, password)
	success = True
	if user['request'].status_code != 400:
		print "Failed Status Code"
		success = False
	if not checkHeaders(user['request'].headers, {'content-type':'text/plain'}):
		print "Failed Headers"
		success = False
	if "set-cookie" in user['request'].headers:
		print "should not have cookie set"
		success = False
	if not len(user['request'].text) > 0:
		print "Failed request text"
		success = False
	return success



def checkHeaders(headers, expected = {}):
	success = True
	for k in expected.keys():
		if not k in headers:
			print "Failed " + k
			success = False
		elif not headers[k] == expected[k]:
			print "Failed " + k + " expected " +expected[k];
			success = False
	return success

def checkCookie(headers, username, password, game = None):
	if not "set-cookie" in headers:
		print "Failed No Cookie"
		return False

	else:
		cookie = headers['set-cookie']
		for cookiePart in cookie.split(';'):
			if len(cookiePart) == 0:
				continue
			splitOnEquals = cookiePart.split('=')
			if len(splitOnEquals) != 2:
				print "Bad Cookie Length "+ cookiePart
				return False
			if splitOnEquals[0] == "Path":
				if splitOnEquals[1] != "/":
					print "Bad path in cookie"
					return False
			elif splitOnEquals[0] == "catan.user":
				if not splitOnEquals[1].startswith('%7B'):
					print "Not encoded"
					return False
				if not checkUserJson(urllib2.unquote(splitOnEquals[1]), username, password):
					print "Bad Json in user data"
					return False
	return True

def checkUserJson(rawJsonString, username, password):
	jsonData = json.loads(rawJsonString);
	if not "name" in jsonData:
		print "name not in json"
		return False
	
	if jsonData['name'] != username:
		print "expected name "+username+ " - " + jsonData['username']
		return False

	if not "playerID" in jsonData:
		print "playerID not in json"
		return False

	return True

def testCreateGame(name):
  p1Session = registerUser("player1", "player1")['session'];
  game = createGame(False, False, False, name, p1Session)
  success = True
  if game['request'].status_code != 200:
    print "Failed Status Code"
    success = False
  if not checkHeaders(game['request'].headers, {'content-type':'application/json'}):
    print "Failed Headers"
    success = False

  if not checkIfValidNewGame(game['request'].text, name) > 0:
    print "Failed request text"
    success = False
  return success

def testCreateAndListMultipleGames(count):
  p1Session = registerUser("player1", "player1")['session'];
  for i in range(count):
    game = createGame(False, False, False, "test_game_"+str(count), p1Session)

  listResult = listGames()

  success = True
  if listResult['request'].status_code != 200:
    print "Failed Status Code"
    success = False
  if not checkHeaders(listResult['request'].headers, {'content-type':'application/json'}):
    print "Failed Headers"
    success = False

  jsonArray = json.loads(listResult['request'].text)
  currentCount = 0
  for game in jsonArray:
    if game['title'].startswith("test_game_"):
      currentCount += 1
      if not checkIfValidNewGameJSON(game, game['title']):
        print "Invalid game produced in list"
        return False
  if currentCount != count:
    print "Not all games were added"
    return False
  return True

# def testJoinGame(username, password, gameId):


#   if not checkIfValidNewGame(listResult['request'].text, name) > 0:
#     print "Failed request text"
#     success = False
#   return success
  
def checkIfValidNewGame(rawJson, title):
  jsonData = json.loads(rawJson);
  return checkIfValidNewGameJSON(jsonData, title)

def checkIfValidNewGameJSON(jsonData, title):  

  if not 'title' in jsonData:
    print "No Title Field"
    return False
  if not 'id' in jsonData:
    print "No id included"
    return False
  if not 'players' in jsonData:
    print "players not included"
    return False

  if title != jsonData['title']:
    print "title values are not equal"
    return False
  if not type(jsonData['id']) is int:
    print "Id was not an integer"
    return False
  if not type(jsonData['players']) is list:
    print "players was not a list"
    return False

  for player in jsonData['players']:
    if not type(player) is dict:
      print "player was not a dictionary"
      return False
    if len(player) != 0:
      print "dictionary was not zero"
      return False

  return True

def testValidJoinGame(username, gameId, color, s):
  joinData = joinGame(gameId, color, s)
  success = True
  if joinData['request'].status_code != 200:
    print "Failed Status Code"
    success = False

  listResult = listGames()['request'].text;
  parseJson = json.loads(listResult)
  if areRepeatsInGames(parseJson):
    print "are repeats in game list"
    success = False

  if not checkIfUserIsGame(username, gameId, color, parseJson):
    print "user not in game after joining"
    success = False

  return success

def testInValidJoinGame(gameId, color, s):
  joinData = joinGame(gameId, color, s)
  if joinData['request'].status_code == 200:
    print "Failed Status Code"
    return False
  return True

def checkIfUserIsGame(username, gameId, color, parseJson):
  for game in parseJson:
    if gameId == game['id']:
      for player in game['players']:
        if len(player) == 0:
          continue
        if username == player['name'] and color.lower() == player['color'].lower():
          return True
  return False

def areRepeatsInGames(parseJson):
  for game in parseJson:
    playerNames = {}
    playerIds = {}
    playerColors = {}
    for player in game['players']:
      if len(player) == 0:
          continue
      if player['name'] in playerNames:
        print "repeat name in games " + player['name']
        return True
      if player['color'] in playerColors:
        print "repeat colors in game"
        return True
      if player['id'] in playerIds:
        print "repeat ids in game"
        return True
      playerNames[player['name']] = True
      playerIds[player['id']] = True
      playerColors[player['color']] = True

  return False

import unittest

class RegisterTest(unittest.TestCase):

    def test(self):
        server.start()
        self.assertTrue(registerValidUser())
        # self.assertTrue(registerRepeatUser())
        
    def tearDown(self):
    	server.kill()

class LoginTest(unittest.TestCase):

    def test(self):
        server.start()
        username = 'player1'
        password = 'player1'
        registerUser(username, password)
        self.assertTrue(loginValidUser(username, password))
        
    def tearDown(self):
    	server.kill()

class CreateGame(unittest.TestCase):

    def test(self):
        server.start()

        self.assertTrue(testCreateGame("Game Name1"));
        
    def tearDown(self):
    	server.kill()

class ListGame(unittest.TestCase):

    def test(self):
        server.start()
        self.assertTrue(testCreateAndListMultipleGames(3));
        
    def tearDown(self):
      server.kill()

class JoinGame(unittest.TestCase):

    def test(self):
        server.start()
        p1Session = registerUser("player1", "player1")['session'];
        p2Session = registerUser("player2", "player2")['session'];
        p3Session = registerUser("player3", "player3")['session'];
        p4Session = registerUser("player4", "player4")['session'];
        p5Session = registerUser("player5", "player5")['session'];

        for val in range(2):
          gameName = "test_game_"+str(val);
          gameData = createGame(False, False, False, gameName)

          colorList = ['red','green','blue','yellow','puce','brown','white','purple','orange'];
          for color in colorList:
            self.assertTrue(testValidJoinGame("player1", getGameIdFromName(gameName), color, p1Session));

          self.assertTrue(testValidJoinGame("player1", getGameIdFromName(gameName), 'red', p1Session));

          self.assertTrue(testInValidJoinGame(getGameIdFromName(gameName), 'red', p2Session));
          self.assertTrue(testValidJoinGame("player2", getGameIdFromName(gameName), 'blue', p2Session));
          
          self.assertTrue(testInValidJoinGame(getGameIdFromName(gameName), 'blue', p3Session));
          self.assertTrue(testValidJoinGame("player3", getGameIdFromName(gameName), 'white', p3Session));

          self.assertTrue(testInValidJoinGame(getGameIdFromName(gameName), 'white', p4Session));
          self.assertTrue(testValidJoinGame("player4", getGameIdFromName(gameName), 'puce', p4Session));

          self.assertTrue(testInValidJoinGame(getGameIdFromName(gameName), 'puce', p5Session));
          self.assertTrue(testInValidJoinGame(getGameIdFromName(gameName), 'orange', p5Session));

          self.assertTrue(testValidJoinGame("player1", getGameIdFromName(gameName), 'green', p1Session));

          self.assertTrue(testInValidJoinGame(-1, 'puce', p1Session));
          self.assertTrue(testInValidJoinGame(1000, 'puce', p1Session));

        badSession = registerUser("bad$", "bad$")['session'];
        gameData = createGame(False, False, False, "badGame")
        self.assertTrue(testInValidJoinGame(getGameIdFromName("badGame"), 'puce', badSession));


        
    def tearDown(self):
      server.kill()

if __name__ == '__main__':
    unittest.main()



'''todo
create game and check all of the randoms 
list games with people in it

'''
