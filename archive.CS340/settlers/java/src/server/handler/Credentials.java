package server.handler;

/**
 * Created by curtis on 11/11/14.
 */
public class Credentials {
    private int playerId;
    private int gameId;
    private String name;
    private String password;

    public Credentials(int playerId, int gameId, String name, String password) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.name = name;
        this.password = password;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String toString() {
        return "CREDENTIALS: "+playerId+" "+gameId+" "+name+" "+password;
    }
}
