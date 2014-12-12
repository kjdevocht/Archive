package serverProxy.request;

import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Created by mitch10e on 11/5/14.
 */
public class SaveGameRequest implements IRequest {

    private int gameId;
    private String name;

    public SaveGameRequest(int gameId, String name) {
        this.gameId = gameId;
        this.name = name;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEndpoint() {
        return "/games/save";
    }

    @Override
    public String getRequestMethod() {
        return "POST";
    }

    @Override
    public String getBody() {
        return null;
    }

    @Override
    public IResponse getDefaultResponse() {
        return null;
    }


}
