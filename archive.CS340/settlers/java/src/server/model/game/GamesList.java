package server.model.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitch10e on 11/5/14.
 */
public class GamesList implements IGamesList{

    private List<IGameModel> gamesList;

    public GamesList() {
        gamesList = new ArrayList<>();
    }

    public List<IGameModel> getGamesList() {
        return gamesList;
    }
    public void addGame(IGameModel game) {
    	gamesList.add(game);
    }

}
