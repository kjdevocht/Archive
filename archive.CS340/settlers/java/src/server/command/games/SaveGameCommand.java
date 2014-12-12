package server.command.games;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import server.command.ICommand;
import server.model.ServerModel;
import serverProxy.request.SaveGameRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by mitch10e on 11/5/14.
 */
public class SaveGameCommand implements ICommand {

    private SaveGameRequest request;
    private String result;

    public SaveGameCommand(SaveGameRequest request) {
        this.request = request;

    }

    @Override
    public String execute() {

        int gameId = request.getGameId();
        ServerModel model = ServerModel.getSingleton();
        if(gameId < 0 || gameId >= model.getGames().size())
            return null;

        File saveDir = new File("saves");
        if(!saveDir.isDirectory()) {
            saveDir.mkdir();
        }

        XStream xstream = new XStream(new DomDriver());

        String xml = xstream.toXML(model.getGames().get(gameId));
        PrintWriter out= null;
        try {
            out = new PrintWriter("saves/"+request.getName());
            out.print(xml);
            out.close();
        } catch (FileNotFoundException e) {
            if(out!=null)
                out.close();
            return null;
        }

        result = "Success";

        return result;
    }
}
