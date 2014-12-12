package server.command.user;

import server.command.ICommand;
import server.model.IServerModel;
import server.model.ServerModel;
import server.model.game.IUser;
import server.result.LoginResult;
import serverProxy.request.RegisterUserRequest;

/**
 * Created by mitch10e on 11/5/14.
 */
public class RegisterUserCommand implements ICommand {

    private RegisterUserRequest request;

    public RegisterUserCommand(RegisterUserRequest request) {
        this.request = request;
    }

    @Override
    public LoginResult execute() {
    	IServerModel model = ServerModel.getSingleton();
		if(!model.canAddUser(request.getUsername(), request.getPassword())) {
    		return null;
    	}
    	IUser user = model.addUser(request.getUsername(), request.getPassword());
    	return new LoginResult(user);
    }
}
