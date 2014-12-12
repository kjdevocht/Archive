package serverProxy.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.io.BufferedReader;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.List;
import java.util.ArrayList;

import serverProxy.IResponse;
import shared.definitions.CatanColor;

/**
 * A response type that is returned from many of the game and games endpoints on the server
 * 
 * @author Jacob Glad
 *
 */
public class GameResponse implements IResponse {

	private String title;
	private int id;
	private List<GameResponsePlayer> players;

    private GameResponsePlayer localPlayer;

	public GameResponse(){}
	
	/**
	 * @param title
	 * @param id
	 */
	public GameResponse(String title, int id) {
		this.title = title;
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}



	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}



	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	public GameResponsePlayer getLocalPlayer() {
		return localPlayer;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	public List<GameResponsePlayer> getPlayers(){
		return this.players;
	}
	public void setPlayers(List<GameResponsePlayer> players){
		this.players = players;
	}
	
	public void addPlayer(GameResponsePlayer player){
		this.players.add(player);
	}

	public void fromJson(JsonObject obj){
		this.setId(obj.get("id").getAsInt());
		this.setTitle(obj.get("title").getAsString());
		
		this.setPlayers(new ArrayList<GameResponsePlayer>());
		
		JsonArray playerArray = obj.getAsJsonArray("players");
		
		for(JsonElement el : playerArray){
			JsonObject playerObj = (JsonObject)el;
			if(playerObj.has("id"))
			{
				this.addPlayer(new GameResponsePlayer(playerObj));	
			}
		}
	}

	public void setLocalPlayer(GameResponsePlayer localPlayer) {
		this.localPlayer = localPlayer;
	}

	@Override
	public IResponse fromResponse(BufferedReader response, CookieStore cookies) throws IOException {
		Gson gson = new GsonBuilder().create();
		
		JsonObject obj = gson.fromJson(response, JsonObject.class);	
		this.fromJson(obj);
		
		return this;
	}
	
	public class GameResponsePlayer{
		private CatanColor color;
		private String playerName;
		private int id;
		
		
		public GameResponsePlayer(JsonObject obj){
			this.setColor(CatanColor.fromString((obj.get("color").getAsString())));
			this.setPlayerName(obj.get("name").getAsString());
			this.setId(obj.get("id").getAsInt());
		}

		/**
		 * @param color
		 * @param playerName
		 * @param id
		 */
		public GameResponsePlayer(CatanColor color, String playerName, int id) {
			this.color = color;
			this.playerName = playerName;
			this.id = id;
		}

		/**
		 * @return the color
		 */
		public CatanColor getColor() {
			return color;
		}

		/**
		 * @param color the color to set
		 */
		public void setColor(CatanColor color) {
			this.color = color;
		}

		/**
		 * @return the playerName
		 */
		public String getPlayerName() {
			return playerName;
		}

		/**
		 * @param playerName the playerName to set
		 */
		public void setPlayerName(String playerName) {
			this.playerName = playerName;
		}

		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(int id) {
			this.id = id;
		}
		
		
	}
}
