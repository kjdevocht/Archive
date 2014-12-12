package server.command.games;

import server.command.ICommand;
import server.model.ServerModel;
import server.model.game.IGameModel;
import serverProxy.request.ListGamesRequest;

import java.util.List;

/**
 * Created by mitch10e on 11/5/14.
 */
public class ListGamesCommand implements ICommand {

    private ListGamesRequest request;
    public ListGamesCommand(ListGamesRequest request) {
        this.request = request;
    }

    @Override
    public List<IGameModel> execute() {
    	List<IGameModel> gameList = ServerModel.getSingleton().getGames();
        return gameList;
    }
}
