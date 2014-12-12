package serverProxy.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import serverProxy.IResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.CookieStore;
import java.util.ArrayList;
import java.util.List;

/**
 * A response type that is returned from the ListAi endpoint on the server
 * 
 * @author Jacob Glad
 *
 */
public class ListAIResponse implements IResponse {

	private List<String> aiTypes;
	
	public ListAIResponse() {
		aiTypes = new ArrayList<>();
	}
	
	public List<String> getAiTypes(){
		return aiTypes;
	}
	
	public void addAiType(String aiType){
		this.aiTypes.add(aiType);
	}

	@Override
	public IResponse fromResponse(BufferedReader response, CookieStore cookies)
			throws IOException {
        Gson gson = new GsonBuilder().create();

        JsonArray obj = gson.fromJson(response, JsonArray.class);

        for(int i=0; i<obj.size(); i++) {
            aiTypes.add(obj.get(i).getAsString());
        }
        return this;
    }

}
