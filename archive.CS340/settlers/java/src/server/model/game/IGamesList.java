package server.model.game;

import java.util.List;

/**
 * Created by mitch10e on 11/5/14.
 */
public interface IGamesList {
	public List<IGameModel> getGamesList();
	/**
	 * Adds a game to the game list
	 * @param game
	 */
    public void addGame(IGameModel game);
}
