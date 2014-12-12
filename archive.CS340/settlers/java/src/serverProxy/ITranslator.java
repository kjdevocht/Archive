package serverProxy;

import client.model.ClientModel;
import com.google.gson.JsonObject;

/**
 * Created by mitch10e on 9/29/14.
 */
public interface ITranslator {
	/**
	 * @.pre valid json model from the server. Both the model json and the cookie json data
	 * @.post A ClientModel representation of the input.
	 */
    public ClientModel translate(JsonObject input, JsonObject cookie);


}
