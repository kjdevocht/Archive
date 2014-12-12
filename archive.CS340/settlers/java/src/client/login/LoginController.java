package client.login;

import java.util.*;

import serverProxy.ClientCommunicator;
import serverProxy.response.FailSuccessResponse;
import serverProxy.response.GameResponse;
import serverProxy.response.GameResponse.GameResponsePlayer;

import client.base.*;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.misc.*;

/*import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;*/


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController {

	private IMessageView messageView;
	private IAction loginAction;
	
	/**
	 * LoginController constructor
	 * 
	 * @param view Login view
	 * @param messageView Message view (used to display error messages that occur during the login process)
	 */
	public LoginController(ILoginView view, IMessageView messageView) {

		super(view);
		
		this.messageView = messageView;
	}
	
	public ILoginView getLoginView() {
		
		return (ILoginView)super.getView();
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	
	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {
		
		loginAction = value;
	}
	
	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {
		
		return loginAction;
	}

	@Override
	public void start() {
		
		getLoginView().showModal();
	}

	@Override
	public void signIn() {
		String username = getLoginView().getLoginUsername();
		String password = getLoginView().getLoginPassword();


		boolean success = false;
		try {
			success = ClientCommunicator.getClientCommunicator().loginUser(username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(success) {
//            System.out.println("[INFO]\tUser Logged In");
            // If log in succeeded
			getLoginView().closeModal();
			loginAction.execute();
		}
        else {
            getMessageView().setTitle("Login Error");
            getMessageView().setMessage("Login Failed - bad password or username");
            getMessageView().showModal();
        }
	}

	@Override
	public void register() {
		
		// TODO: register new user (which, if successful, also logs them in)
        String username = getLoginView().getRegisterUsername();
        String password = getLoginView().getRegisterPassword();
        String repeat = getLoginView().getRegisterPasswordRepeat();

        if(!password.equals(repeat)){
            getMessageView().setTitle("Register Error");
            getMessageView().setMessage("The two passwords must match");
            getMessageView().showModal();
            return;
        }
        if(!validatePassword(password)){
            getMessageView().setTitle("Register Error");
            getMessageView().setMessage("Invalid password -  the password must be five or more character: letters, digits, _ and -");
            getMessageView().showModal();
            return;
        }
        if(!validateUsername(username)) {
              getMessageView().setTitle("Register Error");
              getMessageView().setMessage("Invalid username - The username must be between three and seven characters: letters, digits, _ and -");
              getMessageView().showModal();
              return;
          }
        if(password.equals(repeat)) {

            boolean success = false;
            try {
                success = ClientCommunicator.getClientCommunicator().registerUser(username, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (success) {
//                System.out.println("[INFO]\tRegistered User");
                // If register succeeded
                getLoginView().closeModal();
                loginAction.execute();
            }
            else {
            	getMessageView().setTitle("Register Error");
                getMessageView().setMessage("Failed to register - someone already has that username.");
                getMessageView().showModal();
            }
        }
    }
    public boolean validateUsername(String username)
    {
        final int MIN_UNAME_LENGTH = 3;
        final int MAX_UNAME_LENGTH = 7;

        if (username.length() < MIN_UNAME_LENGTH
                || username.length() > MAX_UNAME_LENGTH)
        {
            return false;
        }
        else
        {
            for (char c : username.toCharArray())
            {
                if (!Character.isLetterOrDigit(c)
                        && c != '_' && c != '-')
                {
                    return false;
                }
            }
        }

        return true;
    }
    public boolean validatePassword(String input)
    {
        final int MIN_PASS_LENGTH = 5;

        if (input.length() < MIN_PASS_LENGTH)
        {
            return false;
        }
        else
        {
            for (char c : input.toCharArray())
            {
                if (!Character.isLetterOrDigit(c)
                        && c != '_' && c != '-')
                {
                    return false;
                }
            }
        }

        return true;
    }
}

