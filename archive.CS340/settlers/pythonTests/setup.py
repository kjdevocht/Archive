import test
import json

def setupFourPlayers(count):
  player1 = test.registerUser("player1", "player1")
  player2 = test.registerUser("player2", "player2")
  player3 = test.registerUser("player3", "player3")
  player4 = test.registerUser("player4", "player4")

  if(player1['request'].status_code != 200):
    player1 = test.loginUser("player1", "player1")
  if(player2['request'].status_code != 200):
    player2 = test.loginUser("player2", "player2")
  if(player3['request'].status_code != 200):
    player3 = test.loginUser("player3", "player3")
  if(player4['request'].status_code != 200):
    player4 = test.loginUser("player4", "player4")

  player1 = player1['session']
  player2 = player2['session']
  player3 = player3['session']
  player4 = player4['session']

  game = test.createGame(False, False, False, "test_game_"+str(count), player1);
  gameId = test.getGameIdFromName("test_game_"+str(count), player1);

  test.joinGame(gameId, "red", player1);
  test.joinGame(gameId, "blue", player2);
  test.joinGame(gameId, "green", player3);
  test.joinGame(gameId, "puce", player4);

  # print test.gameModel(player1)['request'].text

  return (player1, player2, player3, player4)

def setupFirstRounds():
  players = setupFourPlayers(1);
  gameId = test.getGameIdFromName("test_game_"+str(1));
  # filename = "testManyResources.json"
  #filename = "buyASoldier.json"
  # filename = "buyAMonoploy.json"
  # filename = "buyAMonument.json"
  # filename = "buyARoadBuild.json"
  filename = "firstRounds.json"

  with open(filename) as json_file:
    json_data = json.load(json_file)

  for jsonObject in json_data:
    if jsonObject['type'] == 'buyDevCard':
      makeDevCardRequest(jsonObject, gameId, jsonObject['playerIndex'], players)
    else:
      test.moveCommand(jsonObject, players)

def makeDevCardRequest(jsonObject, gameId, playerIndex, players):
  resourceType = jsonObject['card']
  del jsonObject['card']
  name = "temp_save_"+str(gameId);
  test.saveGame(gameId, name)
  gameModel = getGameModel(players[0]);
  orgOldDevCards = gameModel['players'][playerIndex]["oldDevCards"]
  orgNewDevCards = gameModel['players'][playerIndex]["newDevCards"]
  nextGameModel = None
  isTrying = True
  while isTrying:
    nextModel = test.moveCommand(jsonObject, players)
    oldDevCards = nextModel['players'][playerIndex]["oldDevCards"]
    newDevCards = nextModel['players'][playerIndex]["newDevCards"]

    if resourceType == "monument":
      if orgOldDevCards['monument'] < oldDevCards['monument']:
        break
    else:
      if orgNewDevCards[resourceType] < newDevCards[resourceType]:
        break

    test.loadGame(name)


def getGameModel(s):
  return json.loads(test.gameModel(s)['request'].text)
