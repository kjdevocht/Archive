package server.result;

import server.model.game.GamesList;
import server.model.game.IGameModel;

import java.util.List;

/**
 * The result generated by requesting a list of games through server end point.
 */
public class ListGamesResult implements IResult {

    private List<IGameModel> gamesList;

    public ListGamesResult(List<IGameModel> gamesList) {
        this.gamesList = gamesList;
    }

    @Override
    public List<IGameModel> getResult() {
        return gamesList;
    }

    @Override
    public void setResult(Object gamesList) {
        this.gamesList = (List<IGameModel>)gamesList;
    }
}
