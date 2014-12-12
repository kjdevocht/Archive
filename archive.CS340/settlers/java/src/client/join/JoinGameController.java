package client.join;

import java.awt.*;
import java.awt.Event;
import java.util.ArrayList;
import java.util.List;

import client.model.IPlayer;
import client.model.observable.*;
//import cs340.model.game.Game;
import serverProxy.ClientCommunicator;
import serverProxy.response.GameResponse;
import serverProxy.response.GameResponse.GameResponsePlayer;
import shared.definitions.CatanColor;
import client.base.*;
import client.data.*;
import client.misc.*;
import client.model.ClientModel;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
    private List<GameInfo> gameInfo;
    private int localplayerId;
	private IAction joinAction;
	
	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
        gameInfo = new ArrayList<GameInfo>();
        localplayerId = -1;
	}
	public IJoinGameView getJoinGameView() {
		
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {	
		
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		
		return selectColorView;
	}
	public void setSelectColorView(ISelectColorView selectColorView) {
		
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	public void setMessageView(IMessageView messageView) {
		
		this.messageView = messageView;
	}
	private GameInfo gameToJoin = null;
	@Override
	public void start() {
		loadGames();

		getJoinGameView().showModal();
	}

	@Override
	public void startCreateNewGame() {
		
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		
		getNewGameView().closeModal();
	}

	@Override
	public void createNewGame() {
		boolean randomHexes = getNewGameView().getRandomlyPlaceHexes();
		boolean randomNumber = getNewGameView().getRandomlyPlaceNumbers();
		boolean randomPorts = getNewGameView().getUseRandomPorts();
		String name = getNewGameView().getTitle();
		GameResponse game = null;
		try {
			game = ClientCommunicator.getClientCommunicator().createGame(name, randomHexes, randomNumber, randomPorts);
		} catch (Exception e) {
		}
		if(game != null) {
			loadGames();
			getNewGameView().closeModal();
		}		
	}

	@Override
	public void startJoinGame(GameInfo game) {
		gameToJoin = game;
        openSelectColorView();
	}

	@Override
	public void cancelJoinGame() {
	
		getSelectColorView().closeModal();
	}

    private void openSelectColorView() {
       GameInfo game = gameInfo.get(gameToJoin.getId());

        for(CatanColor color: CatanColor.values()) {
            getSelectColorView().setColorEnabled(color, true);
        }

        for(PlayerInfo player : game.getPlayers()) {
            if(player.getId() == localplayerId) {
                continue;
            }
            getSelectColorView().setColorEnabled(player.getColor(), false);
        }
        getSelectColorView().showModal();
    }

	@Override
	public void joinGame(CatanColor color) {
		
		boolean success = false;
		try {
			success = ClientCommunicator.getClientCommunicator().joinGame(gameToJoin.getId(), CatanColor.toString(color));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(success) {
			try {
				ClientCommunicator.getClientCommunicator().getGame(-1);
				ClientCommunicator.getClientCommunicator().getPoller().start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			getSelectColorView().closeModal();
			getJoinGameView().closeModal();
			
			joinAction.execute();
		}
	}
	private void loadGames() {
		List<GameResponse> games = new ArrayList<GameResponse>();
		gameInfo = new ArrayList<GameInfo>();
		try {
			games = ClientCommunicator.getClientCommunicator().listGames();
        } catch (Exception e) {
            e.printStackTrace();
        }
		PlayerInfo localPlayerInfo = new PlayerInfo();
		for(GameResponse game : games) {
			GameInfo info = new GameInfo();
			info.setId(game.getId());
			info.setTitle(game.getTitle());
			int count = 0;
			GameResponsePlayer localplayer = game.getLocalPlayer();
            localplayerId = localplayer.getId();
			localPlayerInfo.setName(localplayer.getPlayerName());
			localPlayerInfo.setId(localplayer.getId());
			localPlayerInfo.setColor(localplayer.getColor());
			localPlayerInfo.setPlayerIndex(-1);
			
			for(GameResponsePlayer player: game.getPlayers()) {
				PlayerInfo playerInfo = new PlayerInfo();
				playerInfo.setName(player.getPlayerName());
				playerInfo.setId(player.getId());
				playerInfo.setColor(player.getColor());
				playerInfo.setPlayerIndex(count);
				info.addPlayer(playerInfo);
				count++;
			}
			gameInfo.add(info);
		}
		GameInfo[] gameInfoArray = gameInfo.toArray(new GameInfo[gameInfo.size()]);
		getJoinGameView().setGames(gameInfoArray, localPlayerInfo);
	}
}

