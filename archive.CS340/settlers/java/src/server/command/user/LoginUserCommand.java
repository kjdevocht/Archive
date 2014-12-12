package server.command.user;

import server.command.ICommand;
import server.model.IServerModel;
import server.model.ServerModel;
import server.model.game.IUser;
import server.result.LoginResult;
import serverProxy.request.LoginUserRequest;

/**
 * Created by mitch10e on 11/5/14.
 */
public class LoginUserCommand implements ICommand {

    private LoginUserRequest request;

    public LoginUserCommand(LoginUserRequest request) {
        this.request = request;
    }

    @Override
    public LoginResult execute() {
    	IServerModel model = ServerModel.getSingleton();
    	IUser user = model.getUser(request.getUsername());
        if(!request.getPassword().equals(user.getPassword()))
            return null;
    	if(user == null)
    		return null;
    	return new LoginResult(user);
    }
}
