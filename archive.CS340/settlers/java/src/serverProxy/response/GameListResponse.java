package serverProxy.response;

import serverProxy.IResponse;
import serverProxy.response.GameResponse.GameResponsePlayer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * A response type that is returned from the list games command
 * 
 * @author Jacob Glad
 *
 */
public class GameListResponse implements IResponse {

	private List<GameResponse> gameList;
	
	/**
	 * @return the gameList
	 */
	public List<GameResponse> getGameList() {
		return gameList;
	}
	/**
	 * @param gameList the gameList to set
	 */
	private void setGameList(List<GameResponse> gameList) {
		this.gameList = gameList;
	}

	private void addGame(GameResponse game) {
		this.gameList.add(game);
	}
	

	public GameListResponse() {
		
	}	

	@Override
	public IResponse fromResponse(BufferedReader response, CookieStore cookies)
			throws IOException {
		Gson gson = new GsonBuilder().create();
		
		JsonArray obj = gson.fromJson(response, JsonArray.class);
		
		this.setGameList(new ArrayList<GameResponse>());
		for(JsonElement el : obj)
		{
			GameResponse game = new GameResponse();			
			game.fromJson((JsonObject)el);
			
			JsonObject parsedJson = null;
			GameResponsePlayer localPlayer = game.new GameResponsePlayer(null, null, -1);
			for(HttpCookie cookie: cookies.getCookies()) {
				if(cookie.getName().equals("catan.user")) {
					String userJSON = URLDecoder.decode(cookie.getValue(), "UTF-8");
					parsedJson = gson.fromJson(userJSON, JsonObject.class);
					if(parsedJson.has("playerID")) {
						parsedJson.add("id", parsedJson.get("playerID"));
					}
					parsedJson.addProperty("color", "");
				}
			}

			if(parsedJson != null) {
				localPlayer = game.new GameResponsePlayer(parsedJson);
			}
			for(GameResponsePlayer player: game.getPlayers()) {
				if(player.getId() ==  localPlayer.getId()) {
					localPlayer.setColor(player.getColor());
				}
			}
			game.setLocalPlayer(localPlayer);
			
			this.addGame(game);
		}
		
		return this;
	}

}
