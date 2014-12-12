package server.model;

import server.command.ICommand;
import server.handler.Credentials;
import server.model.game.*;
import server.persistance.PersistanceProvider;
import shared.definitions.CatanColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerModel implements IServerModel {
	private static ServerModel singleton;

	public static ServerModel getSingleton() {
		if (singleton == null) {
			singleton = new ServerModel();
		}
		return singleton;
	}

	Map<String, IUser> users;
	List<IGameModel> games;

	public ServerModel() {
		users = new HashMap<>();
		games = new ArrayList<IGameModel>();
	}

	@Override
	public List<IGameModel> getGames() {
		return games;
	}

	@Override
	public IGameModel addGame(String name, boolean randomTiles,
			boolean randomNumbers, boolean randomPorts) {
		int id = games.size();
		IGameModel game = new GameModel(name, id, randomTiles, randomNumbers,
				randomPorts);
		games.add(game);
		ICommand dummy = null;
		PersistanceProvider.getSingleton().getGamesDAO().storeCommand(id,dummy);
		return game;
	}

	public void loadGames(List<IGameModel> load){
		this.games = load;
	}
	@Override
	public boolean removeGame(IGameModel game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, IUser> getUsers() {
		return users;
	}

	@Override
	public IUser addUser(String username, String password) {
		IUser user = new User();
		user.setName(username);
		user.setPassword(password);
		user.setId(users.size());
		users.put(username, user);
		PersistanceProvider.getSingleton().getUsersDAO().saveUser(user);
		return user;
	}
	
	public void addAllUsers(List<IUser> userList) {
		for(IUser user: userList) {
			user.setName(user.getName());
			user.setPassword(user.getPassword());
			user.setId(user.getId());
			users.put(user.getName(), user);
		}
	}

	@Override
	public boolean canAddUser(String username, String password) {
		if (users.containsKey(username)) {
			System.out.println("USERNAME ALREADY EXISTS");
			return false;
		}
		String regex = "[a-zA-Z1-9\\-_]+";
		if (!username.matches(regex)) {
			System.out.println("USERNAME DOES NOT MATCH REGEX");
			return false;
		}
		if (username.length() < 3 || username.length() > 7) {
			System.out.println("INCORRECT USERNAME LENGTH");
			return false;
		}
		if (!password.matches(regex)) {
			System.out.println("PASSWORD DOES NOT MATCH REGEX");
			return false;
		}
		if (password.length() < 5) {
			System.out.println("INCORRECT PASSWORD LENGTH");
			return false;
		}
		return true;
	}

	@Override
	public IUser getUser(String username) {
		return users.get(username);
	}

	public boolean checkCredentials(Credentials credentials) {
		IUser user = users.get(credentials.getName());
		if (user == null)
			return false;
		if (!user.getPassword().equals(credentials.getPassword()))
			return false;
		if (user.getId() != credentials.getPlayerId())
			return false;

		return true;
	}

	public boolean checkGameCredentials(Credentials credentials) {
		if (!checkCredentials(credentials))
			return false;
		int gameId = credentials.getGameId();
		if (gameId < 0)
			return false;
		if (gameId >= games.size())
			return false;
		IGameModel game = games.get(gameId);
		if (!game.playerInGame(credentials.getPlayerId())) {
			return false;
		}
		return true;
	}

	public boolean canJoinGame(int gameId, int playerId, CatanColor color) {
		if (gameId < 0)
			return false;
		if (gameId >= games.size())
			return false;
		IGameModel game = games.get(gameId);
		if (!game.canAddPlayer(playerId, color)) {
			return false;
		}
		return true;
	}

	public void joinGame(int gameId, int playerId, CatanColor color) {
        //System.out.println("Joining the game");
		for (Map.Entry<String, IUser> entry : users.entrySet()) {
			IUser user = entry.getValue();
			if (user.getId() == playerId) {
				IPlayer player = new Player(user.getName(), user.getId(),
						color, games.get(gameId).getPlayers().size());
				games.get(gameId).addPlayer(player);

                ICommand dummy = null;
                games.get(gameId).setCommandsExecuted(PersistanceProvider.getSingleton().getGamesDAO().getNumCommands());
                //System.out.println("About to store the game");
                PersistanceProvider.getSingleton().getGamesDAO().storeCommand(gameId, dummy);
				return;
			}
		}

	}

}
