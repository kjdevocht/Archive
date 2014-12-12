package server.command.games;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import server.command.ICommand;
import server.model.ServerModel;
import server.model.game.GameModel;
import server.model.game.IGameModel;
import serverProxy.request.LoadGameRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by mitch10e on 11/5/14.
 */
public class LoadGameCommand implements ICommand {

    private LoadGameRequest request;
    String result;

    public LoadGameCommand(LoadGameRequest request) {
        this.request = request;


    }

    @Override
    public String execute() {

        String name = request.getName();
        File file = new File("saves/"+name);
        if(!file.isFile())
            return null;

        String content = null;
        try {
            content = new Scanner(new File("saves/"+request.getName())).useDelimiter("\\Z").next();

        } catch (FileNotFoundException e) {
            return content;
        }
        if(content == null) {
            return content;
        }
        XStream xstream = new XStream(new DomDriver());
        IGameModel gameModel = (GameModel)xstream.fromXML(content);

        int gameId = gameModel.getId();

        ServerModel serverModel = ServerModel.getSingleton();
        int gameCount = serverModel.getGames().size();
        for(int i=gameCount; i<gameId; i++) {
            serverModel.addGame("filler_game", false, false, false);
        }
        serverModel.getGames().set(gameId, gameModel);

        result = "Success";

        return result;
    }
}
