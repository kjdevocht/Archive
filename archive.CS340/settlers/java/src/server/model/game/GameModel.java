package server.model.game;

import server.logging.Logger;
import server.model.map.*;
import server.model.message.IMessageList;
import server.model.message.MessageList;
import server.persistance.RandomGenerator;
import server.persistance.PersistanceProvider;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameModel implements IGameModel, Serializable {


	private ITurnTracker turnTracker;
	private int winner;
	private int version;
	private IMessageList chat;
	private IMessageList log;
	private ICatanMap catanMap;
	private List<IPlayer> players;
	private IResourceList bank;
	private IBonusTracker bonusTracker;
	private ITradeOffer tradeOffer;
	private IDevCardList deck;
	
	private RandomGenerator random;
	
	private String title;
	private int id;

	public void setCommandsExecuted(int commandsExecuted) {
		this.commandsExecuted = commandsExecuted;
	}

	public int getCommandsExecuted() {
		return commandsExecuted;
	}

	private int commandsExecuted;
	
	/**
	 * Returns a random number deterministically from the servers random number
	 * generator
	 * 
	 * @return a deterministic pseudo random number
	 */
	public int nextInt() {
		return this.random.nextInt();
	}

	/**
	 * 
	 * @return the seed used in this servers random number generator
	 */
	public long getSeed() {
		return this.random.getSeed();
	}

	/**
	 * 
	 * @return the number of times this server has generated a random number
	 */
	public long getGenerations() {
		return this.random.getGenerations();
	}

	private <T> void swap(List<T> a, int i, int change) {
		T helper = a.get(i);
		a.set(i, a.get(change));
		a.set(change, helper);
	}

	public void shuffle(List<?> list) {
		int n = list.size();
		random.nextInt();
		for (int i = 0; i < n; i++) {
			int change = i + random.nextInt(n - i);
			swap(list, i, change);
		}
	}


	public GameModel(String title, int id, boolean randomTiles, boolean randomNumbers, boolean randomPorts) {
			
		players = new ArrayList<>();
		catanMap = new CatanMap(randomTiles, randomNumbers, randomPorts);
		reset();
		this.id = id;
		this.title = title;
		this.random = new RandomGenerator();
		this.commandsExecuted = PersistanceProvider.getSingleton().getGamesDAO().getNumCommands();
	}

	public GameModel(ITurnTracker turnTracker, int winner, int version, IMessageList chat,
			IMessageList log, ICatanMap catanMap, List<IPlayer> players, IResourceList bank,
			IBonusTracker bonusTracker, ITradeOffer tradeOffer, IPlayer localPlayer,
			IDevCardList deck) {
		super();
		this.turnTracker = turnTracker;
		this.winner = winner;
		this.version = version;
		this.chat = chat;
		this.log = log;
		this.catanMap = catanMap;
		this.players = players;
		this.bank = bank;
		this.bonusTracker = bonusTracker;
		this.tradeOffer = tradeOffer;
		this.deck = deck;
		this.random = new RandomGenerator();
	}

    public GameModel(GameModel model) {
        this.turnTracker = model.getTurnTracker();
        this.winner = model.getWinner();
        this.version = model.getVersion();
        this.chat = model.getChat();
        this.log = model.getLog();
        this.catanMap = model.getCatanMap();
        this.players = model.getPlayers();
        this.bank = model.getBank();
        this.bonusTracker = model.getBonusTracker();
        this.tradeOffer = model.getTradeOffer();
        this.deck = model.getDeck();
        this.random = new RandomGenerator();
    }

	public void reset() {
		winner = -1;
		version = 0;				
		turnTracker = new TurnTracker();
		chat = new MessageList();
		log = new MessageList();
		bank = new ResourceList();
		for(ResourceType type : ResourceType.values()) {
			bank.setQuantity(type, 24);
		}
		catanMap.reset();
		
		deck = new DevCardList();
		deck.setDevCardCount(DevCardType.YEAR_OF_PLENTY, 2);
		deck.setDevCardCount(DevCardType.MONOPOLY, 2);
		deck.setDevCardCount(DevCardType.MONUMENT, 14);
		deck.setDevCardCount(DevCardType.ROAD_BUILD, 2);
		deck.setDevCardCount(DevCardType.SOLDIER, 5);		
		bonusTracker = new BonusTracker();
		
		for(IPlayer player: players) {
			player.reset();
		}
	}

    @Override
    public void placeRoad(EdgeLocation edgeLocation, int playerIndex, boolean isFree) {
        IEdgeValue road = new EdgeValue();
        road.setLocation(edgeLocation.getNormalizedLocation());
        road.setOwner(players.get(playerIndex));
        catanMap.placeRoad(road);

        players.get(playerIndex).buyRoad(isFree);

        if(!isFree) {
            bank.setQuantity(ResourceType.BRICK, bank.getQuantity(ResourceType.BRICK) + 1);
            bank.setQuantity(ResourceType.WOOD, bank.getQuantity(ResourceType.WOOD) + 1);
        }

        updateVictoryPoints();
        updateVersion();
    }
    @Override
    public void placeSettlement(VertexLocation location, int playerIndex, boolean isFree) {
        Settlement settlement = new Settlement();
        settlement.setOwner(players.get(playerIndex));
        settlement.setLocation(location.getNormalizedLocation());
        catanMap.placeSettlement(settlement);

        players.get(playerIndex).buySettlement(isFree);



        if(turnTracker.getStatus().equals("SecondRound")) {
            List<HexLocation> nearHexes = LocationComparer.getHexLocations(location);
            for(HexLocation nearHex : nearHexes) {
                for(IHex hex : catanMap.getHexes()) {
                    if(hex.getLocation().equals(nearHex)) {
                        if(hex.getNumber() != -1)
                            givePlayerResource(playerIndex, 1, hex.getResource());
                    }
                }
            }
        }

        if(!isFree) {
            bank.setQuantity(ResourceType.BRICK, bank.getQuantity(ResourceType.BRICK) + 1);
            bank.setQuantity(ResourceType.WOOD, bank.getQuantity(ResourceType.WOOD) + 1);
            bank.setQuantity(ResourceType.WHEAT, bank.getQuantity(ResourceType.WHEAT) + 1);
            bank.setQuantity(ResourceType.SHEEP, bank.getQuantity(ResourceType.SHEEP) + 1);
        }

        updateVictoryPoints();
        updateVersion();
    }
    @Override
    public void placeCity(VertexLocation location, int playerIndex) {
        City city = new City();
        city.setOwner(players.get(playerIndex));
        city.setLocation(location.getNormalizedLocation());
        catanMap.placeCity(city);

        players.get(playerIndex).buyCity();

        bank.setQuantity(ResourceType.ORE, bank.getQuantity(ResourceType.ORE) + 3);
        bank.setQuantity(ResourceType.WHEAT, bank.getQuantity(ResourceType.WHEAT) + 2);

        updateVictoryPoints();
        updateVersion();
    }
    @Override
    public int getPlayerIndex(int playerId) {
        int i = 0;
        for(IPlayer player : players) {
            if(player.getPlayerId() == playerId)
                return i;
            i++;
        }
        return -1;
    }
    @Override
    public void finishTurn(int playerIndex) {
        if(turnTracker.getCurrentTurn() != playerIndex)
            return;
        String status = turnTracker.getStatus();
        if(!(status.equals("Playing") || status.equals("FirstRound") || status.equals("SecondRound")))
            return;

        int currentTurn = turnTracker.getCurrentTurn();

        if(!turnTracker.getStatus().equals("SecondRound"))
            currentTurn = (currentTurn + 1) % 4;
        else {
            currentTurn --;
        }



        if(turnTracker.getStatus().equals("FirstRound") && currentTurn != 0) {
            //Do nothing...
        }
        else if(turnTracker.getStatus().equals("SecondRound") && currentTurn != -1) {
            //Do nothing...
        }
        else if( turnTracker.getStatus().equals("SecondRound") && currentTurn == -1) {
            turnTracker.setStatus("Rolling");
            currentTurn = 0;

        }
        else if(turnTracker.getStatus().equals("FirstRound") && currentTurn == 0) {
            turnTracker.setStatus("SecondRound");
            currentTurn = 3;
        }
        else {
            turnTracker.setStatus("Rolling");
        }

        IDevCardList oldCards = players.get(playerIndex).getOldDevCards();
        IDevCardList newCards = players.get(playerIndex).getNewDevCards();
        for(DevCardType type: DevCardType.values()) {
            oldCards.setDevCardCount(type, oldCards.getDevCardCount(type) + newCards.getDevCardCount(type));
            newCards.setDevCardCount(type, 0);
        }

        Logger.getSingleton().debug("Turn changing to "+currentTurn);
        turnTracker.setCurrentTurn(currentTurn);

        for(IPlayer player : players) {
            player.setPlayedDevCard(false);
            player.setDiscarded(false);
        }


        updateVersion();
    }
    @Override
    public void rollNumber(int playerIndex, int roll) {
        if(turnTracker.getCurrentTurn() != playerIndex)
            return;
        if(!turnTracker.getStatus().equals("Rolling"))
            return;
        if(roll < 2 || roll > 12)
            return;

        addLog(playerIndex, players.get(playerIndex).getName()+" rolled a " +roll+".");

        String newStatus = "Playing";

        if(roll == 7){
            newStatus = "Robbing";
            for(IPlayer player: players) {
                if(player.getResources().getTotalResourceCount() > 7) {
                    newStatus = "Discarding";
                }
            }
        }
        else {

            List<ResourceList> settlementResources = getResourceCount(catanMap.getSettlements(), roll);
            List<ResourceList> cityResources = getResourceCount(catanMap.getCities(), roll);

            //sum up resources to see if the bank has enough
            ResourceList sum = new ResourceList();
            for(int i=0; i<4; i++) {
                for(ResourceType type: ResourceType.values()) {
                    int settlementQty = settlementResources.get(i).getQuantity(type);
                    int cityQty = cityResources.get(i).getQuantity(type) * 2;

                    sum.setQuantity(type, sum.getQuantity(type) + cityQty + settlementQty);
                }
            }

            //add resources if can give to everyone
            for(ResourceType type: ResourceType.values()) {
                if(sum.getQuantity(type) <= bank.getQuantity(type)) {
                    for(int i=0; i<4; i++) {
                        int settlementQty = settlementResources.get(i).getQuantity(type);
                        int cityQty = cityResources.get(i).getQuantity(type) * 2;
                        IResourceList playerResources = players.get(i).getResources();
                        playerResources.setQuantity(type, playerResources.getQuantity(type)+cityQty+settlementQty);
                        bank.setQuantity(type, bank.getQuantity(type) - cityQty - settlementQty);
                    }
                }
            }
        }
        turnTracker.setStatus(newStatus);
        updateVersion();
    }
    private List<ResourceList> getResourceCount(List<ICommunity> communities, int roll) {

        List<ResourceList> playerResources = new ArrayList<>();
        for(int i=0; i<4; i++) {
            playerResources.add(new ResourceList());
        }
        for(ICommunity community : communities) {
            List<HexLocation> hexes = LocationComparer.getHexLocations(community.getLocation());
            for(HexLocation hexLocation : hexes) {
                for(IHex hex: catanMap.getHexes()) {
                    if(hexLocation.equals(hex.getLocation())) {
                        if(hex.getNumber() == roll) {
                            if(!hex.getLocation().equals(catanMap.getRobber())) {
                                ResourceList list = playerResources.get(community.getOwner().getPlayerIndex());
                                list.setQuantity(hex.getResource(), list.getQuantity(hex.getResource()) + 1);
                            }
                        }
                    }
                }
            }
        }
        return playerResources;
    }
    @Override
    public void discardCards(int playerIndex, ResourceList resourceList){
        if(!turnTracker.getStatus().equals("Discarding"))
            return;
        if(players.get(playerIndex).getResources().getTotalResourceCount() <= 7 || players.get(playerIndex).isDiscarded())
            return;
        for(ResourceType type : ResourceType.values()) {
            int currentPlayerCount = players.get(playerIndex).getResources().getQuantity(type);
            int discardAmount = resourceList.getQuantity(type);

            int finalCount = currentPlayerCount - discardAmount;
            if(finalCount < 0)
                return;
        }
        for(ResourceType type : ResourceType.values()) {
            int currentPlayerCount = players.get(playerIndex).getResources().getQuantity(type);
            int discardAmount = resourceList.getQuantity(type);

            int finalCount = currentPlayerCount - discardAmount;

            players.get(playerIndex).getResources().setQuantity(type, finalCount);
            bank.setQuantity(type, bank.getQuantity(type) + discardAmount);
        }
        players.get(playerIndex).setDiscarded(true);

        boolean stillDiscarding = false;
        for(IPlayer player : players) {
            if(player.getResources().getTotalResourceCount() > 7 && !player.isDiscarded()) {
                stillDiscarding = true;
            }
        }
        if(!stillDiscarding) {
            turnTracker.setStatus("Robbing");
        }

        updateVersion();
    }
    @Override
    public void robPlayer(int playerIndex, int victimIndex, HexLocation hexLocation) {
        if(playerIndex == victimIndex)
            return;

        if(victimIndex!= -1) {
            randomStealResource(playerIndex, victimIndex);

            addLog(playerIndex, players.get(playerIndex).getName()+" moved the robber and robbed "+players.get(victimIndex).getName());
        }
        else {
            addLog(playerIndex, players.get(playerIndex).getName()+" moved the robber but couldn't rob anyone!");
        }

        this.catanMap.setRobber(hexLocation);

        turnTracker.setStatus("Playing");
        updateVersion();
    }
    private void randomStealResource(int playerIndex, int victimIndex) {
        IPlayer victim = players.get(victimIndex);
        IPlayer player = players.get(playerIndex);

        IResourceList victimList = victim.getResources();
        IResourceList playerList = player.getResources();

        if(victimList.getTotalResourceCount() != 0) {

            List<ResourceType> resources = new ArrayList<>();
            for(ResourceType type: ResourceType.values()) {
                resources.add(type);
            }
            this.shuffle(resources);

            for(ResourceType type: resources) {
                if(victimList.getQuantity(type) > 0) {
                    victimList.setQuantity(type, victimList.getQuantity(type) - 1);
                    playerList.setQuantity(type, playerList.getQuantity(type) + 1);
                    break;
                }
            }
        }
    }
    @Override
    public void offerTrade(int playerIndex, int recieverIndex, IResourceList trade) {

        if(playerIndex == recieverIndex)
            return;
        if(recieverIndex <0 || recieverIndex > 3)
            return;

        ITradeOffer tradeOffer = new TradeOffer(playerIndex, recieverIndex, trade);

        this.tradeOffer = tradeOffer;

        updateVersion();
    }
    @Override
    public void acceptTrade(int playerIndex, boolean accept) {
        if(playerIndex != tradeOffer.getReceiverId())
            return;
        if(accept) {
            IResourceList receiver = players.get(tradeOffer.getReceiverId()).getResources();
            IResourceList sender = players.get(tradeOffer.getSenderId()).getResources();
            for(ResourceType type: ResourceType.values()) {
                receiver.setQuantity(type, receiver.getQuantity(type) + tradeOffer.getResources().getQuantity(type));
                sender.setQuantity(type, sender.getQuantity(type) - tradeOffer.getResources().getQuantity(type));
            }
            addLog(playerIndex, players.get(playerIndex).getName()+" Trade was accepted");
        }
        else {
            addLog(playerIndex, players.get(playerIndex).getName()+" Trade was not accepted");
            // do nothing for now... log later
        }

        this.tradeOffer = null;
        updateVersion();
    }
    @Override
    public void maritimeTrade(int playerIndex, int ratio, ResourceType input, ResourceType output) {

        if(!canMaritimeTrade(input, output, ratio, playerIndex))
            return;

        if(bank.getQuantity(output) < 1)
            return;

        bank.setQuantity(input, bank.getQuantity(input) + ratio);
        bank.setQuantity(output, bank.getQuantity(output) - 1);

        IResourceList player = players.get(playerIndex).getResources();

        player.setQuantity(input, player.getQuantity(input) - ratio);
        player.setQuantity(output, player.getQuantity(output) + 1);

        updateVersion();
    }
    @Override
    public void buyDevCard(int playerIndex) {
        DevCardType nextCard = null;
        List<DevCardType> mixCards = new ArrayList<>();
        for(DevCardType type: DevCardType.values()) {
            mixCards.add(type);
        }
        this.shuffle(mixCards);
        for(DevCardType type: mixCards) {
            if(deck.getDevCardCount(type) > 0) {
                nextCard = type;
                break;
            }
        }

        if(nextCard == null) {
            return;
        }

        IDevCardList oldDevCards = players.get(playerIndex).getOldDevCards();
        IDevCardList newDevCards = players.get(playerIndex).getNewDevCards();

        deck.setDevCardCount(nextCard, deck.getDevCardCount(nextCard) - 1);
        if(nextCard == DevCardType.MONUMENT) {
            oldDevCards.setDevCardCount(nextCard, oldDevCards.getDevCardCount(nextCard) + 1);
        }
        else {
            newDevCards.setDevCardCount(nextCard, newDevCards.getDevCardCount(nextCard) + 1);
        }

        players.get(playerIndex).buyDevCard();
        bank.setQuantity(ResourceType.ORE, bank.getQuantity(ResourceType.ORE) + 1);
        bank.setQuantity(ResourceType.WHEAT, bank.getQuantity(ResourceType.WHEAT) + 1);
        bank.setQuantity(ResourceType.SHEEP, bank.getQuantity(ResourceType.SHEEP) + 1);

        addLog(playerIndex, players.get(playerIndex).getName()+" bought a Development Card");

        updateVersion();
    }
    @Override
    public void yearOfPlenty(int playerIndex, ResourceType card1, ResourceType card2) {
        removeDevCardFromPlayer(playerIndex, DevCardType.YEAR_OF_PLENTY);

        givePlayerResource(playerIndex, 1, card1);
        givePlayerResource(playerIndex, 1, card2);

        players.get(playerIndex).setPlayedDevCard(true);

        addLog(playerIndex, players.get(playerIndex).getName()+" used Year of Plenty and got a "+card1.toString().toLowerCase() + " and a "+card2.toString().toLowerCase());

        updateVersion();
    }
    @Override
    public void roadBuild(int playerIndex, EdgeLocation edgeLocation1, EdgeLocation edgeLocation2) {
        removeDevCardFromPlayer(playerIndex, DevCardType.ROAD_BUILD);

        IPlayer player = players.get(playerIndex);

        IEdgeValue road1 = new EdgeValue();
        road1.setOwner(player);
        road1.setLocation(edgeLocation1);

        IEdgeValue road2 = new EdgeValue();
        road2.setOwner(player);
        road2.setLocation(edgeLocation2);

        catanMap.placeRoad(road1);
        catanMap.placeRoad(road2);

        player.setRoads(player.getRoads() - 2);

        players.get(playerIndex).setPlayedDevCard(true);

        addLog(playerIndex, players.get(playerIndex).getName()+" built 2 roads");

        updateVictoryPoints();
        updateVersion();
    }
    @Override
    public void soldier(int playerIndex, int victimIndex, HexLocation hexLocation) {
        removeDevCardFromPlayer(playerIndex, DevCardType.SOLDIER);

        catanMap.setRobber(hexLocation);

        addLog(playerIndex, players.get(playerIndex).getName()+" used a soldier");
        if(victimIndex!= -1) {
            randomStealResource(playerIndex, victimIndex);
            addLog(playerIndex, players.get(playerIndex).getName() + " moved the robber and robbed " + players.get(victimIndex).getName());
        }
        else {
            addLog(playerIndex, players.get(playerIndex).getName()+" moved the robber but couldn't rob anyone!");
        }

        IPlayer player = players.get(playerIndex);
        player.setSoldiers(player.getSoldiers() + 1);

        players.get(playerIndex).setPlayedDevCard(true);

        updateVictoryPoints();
        updateVersion();
    }
    @Override
    public void monopoly(int playerIndex, ResourceType type) {
        removeDevCardFromPlayer(playerIndex, DevCardType.MONOPOLY);

        IResourceList player = players.get(playerIndex).getResources();

        addLog(playerIndex, players.get(playerIndex).getName()+" stole everyones "+type.toString().toLowerCase());
        for(IPlayer otherPlayer : players){
            if(playerIndex == otherPlayer.getPlayerIndex())
                continue;
            player.setQuantity(type, player.getQuantity(type) + otherPlayer.getResources().getQuantity(type));
            otherPlayer.getResources().setQuantity(type, 0);
        }

        players.get(playerIndex).setPlayedDevCard(true);

        updateVersion();
    }
    @Override
    public void monument(int playerIndex) {
        removeDevCardFromPlayer(playerIndex, DevCardType.MONUMENT);
        IPlayer player = players.get(playerIndex);
        player.setMonuments(player.getMonuments() + 1);

        addLog(playerIndex, players.get(playerIndex).getName()+" built a monument and gained a victory point");

        updateVictoryPoints();
        updateVersion();
    }
    @Override
    public void sendChat(int playerIndex, String message) {
        chat.addMessage(players.get(playerIndex).getName(), message);
        updateVersion();
    }
    @Override
    public void addLog(int playerIndex, String message) {
        log.addMessage(players.get(playerIndex).getName(), message);
        updateVersion();
    }
    private void removeDevCardFromPlayer(int playerIndex, DevCardType type) {
        deck.setDevCardCount(type, deck.getDevCardCount(type) + 1);
        IDevCardList player = players.get(playerIndex).getOldDevCards();
        player.setDevCardCount(type, player.getDevCardCount(type) - 1);
    }
    //Note this does not do any validation which is important for some server
    //operations like after rolling
    private void givePlayerResource(int playerIndex, int qty, ResourceType type) {
        bank.setQuantity(type, bank.getQuantity(type) - qty);
        IResourceList player = players.get(playerIndex).getResources();
        player.setQuantity(type, player.getQuantity(type) + qty);
    }
    private void updateVictoryPoints() {
        updateLongestRoad();
        updateLargestArmy();

        List<Integer> vps = new ArrayList<>();
        for(int i=0; i<players.size(); i++) {
            vps.add(0);
        }

        if(bonusTracker.getLargestArmy() != -1) {
            int vp = vps.get(bonusTracker.getLargestArmy());
            vp += 2;
            vps.set(bonusTracker.getLargestArmy(), vp);
        }
        if(bonusTracker.getLongestRoad() != -1) {
            int vp = vps.get(bonusTracker.getLongestRoad());
            vp += 2;
            vps.set(bonusTracker.getLongestRoad(), vp);
        }


        for(int i=0; i<players.size(); i++) {
            int vp = vps.get(i);
            vp += (4 - players.get(i).getCities())*2;
            vp += (5 - players.get(i).getSettlements());
            vp += players.get(i).getMonuments();
            vps.set(i, vp);
            players.get(i).setVictoryPoints(vp);
            if(vp >= 10) {
                setWinner(i);
            }
        }
    }
    private void updateLongestRoad() {
        int highIndex = 0;
        int highCount = 0;
        for(IPlayer player: players) {
            if((15-player.getRoads()) > highCount)
            {
                highCount = (15 - player.getRoads());
                highIndex = player.getPlayerIndex();
            }
        }
        Logger.getSingleton().debug("HighIndex: "+highIndex);
        Logger.getSingleton().debug("HighCount: "+highCount);
        if(highCount >= 5) {
            if(bonusTracker.getLongestRoad() == -1) {
                bonusTracker.setLongestRoad(highIndex);
            }
            else {
                int recordRoads = 15 - players.get(bonusTracker.getLongestRoad()).getRoads();
                if(highCount > recordRoads) {
                    bonusTracker.setLongestRoad(highIndex);
                }
            }
        }
    }
    private void updateLargestArmy() {
        int highIndex = 0;
        int highCount = 0;
        for(IPlayer player: players) {
            if(player.getSoldiers() > highCount) {
                highCount = player.getSoldiers();
                highIndex = player.getPlayerIndex();
            }
        }
        if(highCount >= 3) {
            if(bonusTracker.getLargestArmy() == -1) {
                bonusTracker.setLargestArmy(highIndex);
            }
            else{
                int recordSoilder = players.get(bonusTracker.getLargestArmy()).getSoldiers();
                if(highCount > recordSoilder) {
                    bonusTracker.setLargestArmy(highIndex);
                }
            }
        }
    }
    private void updateVersion() {
        version++;
    }
    /**
	 * @.obviousGetter
	 */
	public ITurnTracker getTurnTracker() {
		return turnTracker;
	}

	/**
	 * @.obviousGetter
	 */
	public ICatanMap getCatanMap() {
		return catanMap;
	}

	/**
	 * @.obviousGetter
	 */
	public int getWinner() {
		return winner;
	}

	/**
	 * @.obviousGetter
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @.obviousGetter
	 */
	public IMessageList getChat() {
		return chat;
	}

	/**
	 * @.obviousGetter
	 */
	public IMessageList getLog() {
		return log;
	}

	/**
	 * @.obviousGetter
	 */
	public List<IPlayer> getPlayers() {
		return players;
	}

	/**
	 * @.obviousSetter
	 */
	public void setTurnTracker(ITurnTracker turnTracker) {
		this.turnTracker = turnTracker;
	}

	/**
	 * @.obviousSetter
	 */
	public void setWinner(int winner) {
		this.winner = winner;
	}

	/**
	 * @.obviousSetter
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @.obviousSetter
	 */
	public void setChat(IMessageList chat) {
		this.chat = chat;
	}

	/**
	 * @.obviousSetter
	 */
	public void setLog(IMessageList log) {
		this.log = log;
	}

	/**
	 * @.obviousSetter
	 */
	public void setCatanMap(ICatanMap catanMap) {
		this.catanMap = catanMap;
	}

	/**
	 * @.obviousSetter
	 */
	public void setPlayers(List<IPlayer> players) {
		this.players = players;
	}

	/**
	 * @.obviousGetter
	 */
	public IResourceList getBank() {
		return bank;
	}

	/**
	 * @.obviousSetter
	 */
	public void setBank(IResourceList bank) {
		this.bank = bank;
	}

	/**
	 * @.obviousGetter
	 */
	public IBonusTracker getBonusTracker() {
		return bonusTracker;
	}

	/**
	 * @.obviousSetter
	 */
	public void setBonusTracker(IBonusTracker bonusTracker) {
		this.bonusTracker = bonusTracker;
	}

	/**
	 * @.obviousGetter
	 */
	public ITradeOffer getTradeOffer() {
		return tradeOffer;
	}

	/**
	 * @.obviousSetter
	 */
	public void setTradeOffer(ITradeOffer tradeOffer) {
		this.tradeOffer = tradeOffer;
	}

	public IDevCardList getDeck() {
		return deck;
	}

	public void setDeck(IDevCardList deck) {
		this.deck = deck;
	}

	public boolean canRollNumber(int playerIndex) {
		// Not my turn
		if (turnTracker.getCurrentTurn() != playerIndex)
			return false;
		// has rolling as the status
		if (!turnTracker.getStatus().toLowerCase().equals("rolling"))
			return false;
		return true;
	}

	public boolean canAcceptTrade(int playerIndex) {
		if (tradeOffer == null)
			return false;
		if (tradeOffer.getReceiverId() != playerIndex)
			return false;

		if (!players.get(playerIndex).canAcceptTradeOffer(tradeOffer))
			return false;

		return true;
	}

	public boolean canOfferTrade(ITradeOffer tradeOffer, int playerIndex) {
		if (tradeOffer == null)
			return false;
		if (tradeOffer.getSenderId() != playerIndex)
			return false;
        if(!hasPositiveAndNegative(tradeOffer.getResources()))
            return false;
		if (!players.get(playerIndex).canOfferTradeOffer(tradeOffer))
			return false;
		return true;
	}
    private boolean hasPositiveAndNegative(IResourceList list) {
        boolean hasPositive = false;
        if(list.getQuantity(ResourceType.BRICK) > 0)
            hasPositive = true;
        if(list.getQuantity(ResourceType.WOOD) > 0)
            hasPositive = true;
        if(list.getQuantity(ResourceType.WHEAT) > 0)
            hasPositive = true;
        if(list.getQuantity(ResourceType.ORE) > 0)
            hasPositive = true;
        if(list.getQuantity(ResourceType.SHEEP) > 0)
            hasPositive = true;

        boolean hasNegative = false;
        if(list.getQuantity(ResourceType.BRICK) < 0)
            hasNegative = true;
        if(list.getQuantity(ResourceType.WOOD) < 0)
            hasNegative = true;
        if(list.getQuantity(ResourceType.WHEAT) < 0)
            hasNegative = true;
        if(list.getQuantity(ResourceType.ORE) < 0)
            hasNegative = true;
        if(list.getQuantity(ResourceType.SHEEP) < 0)
            hasNegative = true;

        return hasNegative && hasPositive;
    }
	public boolean canDiscardCards(IResourceList cards, int playerIndex) {
		if (cards == null)
			return false;
		if (players.get(playerIndex).isDiscarded())
			return false;

		if (!turnTracker.getStatus().toLowerCase().equals("discarding"))
			return false;

		IResourceList playerResources = players.get(playerIndex).getResources();
		int totalCards = playerResources.getTotalResourceCount();

		if (totalCards <= 7)
			return false;

		if (!playerResources.isGreaterOrEqualThanInput(cards))
			return false;

		return true;
	}

	public boolean canFinishTurn(int playerIndex) {
		// TODO: is this only true if the current player is playing???
		// Not my turn
		if (turnTracker.getCurrentTurn() != players.get(playerIndex).getPlayerIndex())
			return false;

		if (!turnTracker.getStatus().toLowerCase().equals("playing"))
			return false;

		return true;
	}

	public boolean canBuyDevCard(int playerIndex) {
		// TODO: can you buy during any part of the game??? should I check
		// status
		if (turnTracker.getCurrentTurn() != players.get(playerIndex).getPlayerIndex())
			return false;

		if (!players.get(playerIndex).canBuyDevCard())
			return false;

		if (deck.getTotalCardCount() < 1)
			return false;

		return true;
	}
    public Map<ResourceType, Integer> getTradeRatios(int playerIndex) {
        Map<ResourceType, Integer> ratios = new HashMap<>();
        ratios.put(ResourceType.BRICK, 4);
        ratios.put(ResourceType.ORE, 4);
        ratios.put(ResourceType.SHEEP, 4);
        ratios.put(ResourceType.WOOD, 4);
        ratios.put(ResourceType.WHEAT, 4);

        List<ICommunity> communities = new ArrayList<ICommunity>();
        communities.addAll(catanMap.getSettlements());
        communities.addAll(catanMap.getCities());
        for(IPort port : catanMap.getPorts()) {
            for(ICommunity community : communities){
            	if(community.getOwner().getPlayerIndex() == players.get(playerIndex).getPlayerIndex()) {
	                EdgeLocation portEdge = new EdgeLocation(port.getLocation(), port.getDirection());
	                if(LocationComparer.vertexIsOnEdge(portEdge, community.getLocation())){
	                    int portRatio = port.getRatio();
	                    ResourceType type = port.getResource();
	                    if(type == null) {
	                        for(ResourceType restype: ResourceType.values()) {
	                            if(ratios.get(restype) > portRatio)
	                                ratios.put(restype, 3);
	                        }
	                    }
	                    else if(ratios.get(type) > portRatio)
	                        ratios.put(type, portRatio);
	                }
            	}
            }
        }

        return ratios;
    }
	public boolean canMaritimeTrade(ResourceType inputResource, ResourceType outputResource,
			int ratio, int playerIndex) {
		if (ratio < 2 || ratio > 4)
			return false;
		boolean canMakeOffer = false;
		

		Map<ResourceType, Integer> ratios = new HashMap<>();
		ratios = getTradeRatios(playerIndex);
		if(ratios.get(inputResource)!=ratio)
			return false;
		
		ResourceList inputResources = new ResourceList();
		inputResources.setQuantity(inputResource, ratio);

		ResourceList outputResources = new ResourceList();
		outputResources.setQuantity(outputResource, 1);

		if (turnTracker.getCurrentTurn() != playerIndex)
			return false;
		if (!players.get(playerIndex).getResources().isGreaterOrEqualThanInput(inputResources))
			return false;
		if (!bank.isGreaterOrEqualThanInput(outputResources))
			return false;

		return true;
	}

	private boolean canPlayDevCard(DevCardType devCard, int playerIndex) {
		if (!turnTracker.getStatus().toLowerCase().equals("playing"))
			return false;
		if (turnTracker.getCurrentTurn() != playerIndex)
			return false;
		if (!players.get(playerIndex).canPlayDevCard(devCard))
			return false;
		return true;
	}

	public boolean canPlayYearOfPlenty(ResourceType resource1, ResourceType resource2, int playerIndex) {
		if (!canPlayDevCard(DevCardType.YEAR_OF_PLENTY, playerIndex))
			return false;
		if (resource1.equals(resource2)) {
			if (bank.getQuantity(resource1) < 2)
				return false;
		} else {
			if (bank.getQuantity(resource2) < 1)
				return false;
			if (bank.getQuantity(resource1) < 1)
				return false;
		}
		return true;
	}

	public boolean canPlayMonument(int playerIndex) {
		if (!canPlayDevCard(DevCardType.MONUMENT, playerIndex))
			return false;
		return true;
	}

	public boolean canPlayMonopoly(int playerIndex) {
		if (!canPlayDevCard(DevCardType.MONOPOLY, playerIndex))
			return false;
		return true;
	}

	public boolean canPlaySoldier(HexLocation hexLocation, int victimIndex, int playerIndex) {
		if (!canPlayDevCard(DevCardType.SOLDIER, playerIndex))
			return false;
		if (victimIndex == playerIndex)
			return false;

		if (getCatanMap().getRobber().equals(hexLocation))
			return false;

		if (victimIndex > 0
				&& getPlayers().get(victimIndex).getResources().getTotalResourceCount() == 0)
			return false;

		return true;
	}

	public boolean canPlayRoadBuild(EdgeLocation location1, EdgeLocation location2, int playerIndex) {
		if (!canPlayDevCard(DevCardType.ROAD_BUILD, playerIndex))
			return false;

		if(getCatanMap().canPlaceRoadAtLocation(location1, playerIndex, "Playing")) {
			IEdgeValue road = new EdgeValue();
			road.setLocation(location1);
			road.setOwner(players.get(playerIndex));
			getCatanMap().getRoads().add(road);
			
			if(getCatanMap().canPlaceRoadAtLocation(location2, playerIndex, "Playing")) {
				getCatanMap().getRoads().remove(road);
				return true;
			}
			getCatanMap().getRoads().remove(road);		
		}

		if(!getCatanMap().canPlaceRoadAtLocation(location2, playerIndex, "Playing"))
			return false;
		
		IEdgeValue road = new EdgeValue();
		road.setLocation(location2);
		road.setOwner(players.get(playerIndex));
		getCatanMap().getRoads().add(road);	
		
		if(getCatanMap().canPlaceRoadAtLocation(location1, playerIndex, "Playing")) {
			getCatanMap().getRoads().remove(road);
			return true;
		}
		getCatanMap().getRoads().remove(road);
		return false;

	}

	@Override
	public boolean canBuyRoadAtLocation(EdgeLocation edgeLocation, int playerIndex) {

		String status = getTurnTracker().getStatus().toLowerCase();
		
		if (!(status.equals("firstround") || status.equals("secondround") || status
				.equals("playing")))
			return false;
		if (!(status.equals("firstround") || status.equals("secondround")))
			if (!players.get(playerIndex).canBuyRoad())
				return false;

		if (!getCatanMap().canPlaceRoadAtLocation(edgeLocation.getNormalizedLocation(),
				playerIndex, status))
			return false;

		return true;
	}

	@Override
	public boolean canBuySettlementAtLocation(VertexLocation vertexLocation, int playerIndex) {
		String status = getTurnTracker().getStatus().toLowerCase();

		if (!(status.equals("firstround") || status.equals("secondround") || status
				.equals("playing")))
			return false;
		if (!(status.equals("firstround") || status.equals("secondround")))
			if (!players.get(playerIndex).canBuySettlement())
				return false;

		if (!getCatanMap().canPlaceSettlementAtLocation(vertexLocation.getNormalizedLocation(),
				playerIndex, status))
			return false;

		return true;
	}

	@Override
	public boolean canBuyCityAtLocation(VertexLocation vertexLocation, int playerIndex) {
		String status = getTurnTracker().getStatus().toLowerCase();

		if (!(status.equals("firstround") || status.equals("secondround") || status
				.equals("playing")))
			return false;
		if (!(status.equals("firstround") || status.equals("secondround")))
			if (!players.get(playerIndex).canBuyCity())
				return false;

		if (!getCatanMap().canPlaceCityAtLocation(vertexLocation.getNormalizedLocation(),
				playerIndex, status))
			return false;

		return true;
	}

	@Override
	public ICatanMap getMap() {
		// TODO Auto-generated method stub
		return catanMap;
	}

	@Override
	public void setMap(ICatanMap map) {
		this.catanMap = map;
		
	}

	@Override
	public void addPlayer(IPlayer player) {

        for(IPlayer otherPlayer : players) {
            if(otherPlayer.getPlayerId() == player.getPlayerId()) {
                otherPlayer.setColor(player.getColor());
                return;
            }
        }
        player.setPlayerIndex(players.size());
        players.add(player);
	}
    @Override
    public boolean canAddPlayer(int playerId, CatanColor color) {
        boolean isInGame = false;
        for(IPlayer otherPlayer : players) {
            if(otherPlayer.getPlayerId() == playerId) {
                isInGame = true;
            }
            else if(otherPlayer.getColor().equals(color)) {
                return false;
            }
        }
        return isInGame || players.size() < 4;
    }
    @Override
    public boolean playerInGame(int playerId) {
        for(IPlayer otherPlayer : players) {
            if(otherPlayer.getPlayerId() == playerId) {
                return true;
            }
        }
        return false;
    }


	@Override
	public boolean removePlayer(IPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setName(String title) {
		this.title = title;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
}
