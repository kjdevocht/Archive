package serverProxy.request;

import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Created by mitch10e on 11/5/14.
 */
public class LoadGameRequest implements IRequest {
    private String name;

    public LoadGameRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEndpoint() {
        return "/games/load";
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
