package server.result;

import server.model.game.IUser;

public class LoginResult implements IResult {
	private IUser user;
	public LoginResult(IUser user) {
		this.user = user;
	}
	@Override
	public IUser getResult() {
		return user;
	}

	@Override
	public void setResult(Object object) {
		user = (IUser) object;
	}

}
