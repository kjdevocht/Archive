package client.communication;

import client.model.ClientModel;
import serverProxy.*;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A ServerPoller polls the server for new model data, and updates the board if
 * needed.
 */
public class ServerPoller implements IServerPoller {
	private static ServerPoller poller = null;

	private ClientCommunicator communicator;
	private boolean runTask;

	private Timer time;

	/**
	 * Creates a ServerPoller object.
	 * 
	 * @.pre Model must not be null
	 * @.pre Model must be initialized
	 * @.pre Model must represent a valid ClientModel.
	 * @.post the ServerPoller will start polling the server. If needed, the
	 *        ServerPoller will call the given ClientModel's setToJsonModel
	 *        method.
	 * @param communicator the IServer to associate with this ServerPoller.
	 */
	private ServerPoller(ClientCommunicator communicator) {
		this.communicator = communicator;
		time = new Timer();
		runTask = true;
	}

	/**
	 * Returns the ServerPoller for the given co0mmunicator and model.
	 * 
	 * @.pre The given Server and Model must be valid and initialized.
	 * @.post If there was already a SeverPoller created for that server, return
	 *        it. If not, create a new poller for that server, and return that.
	 * @param communicator the ClientCommunicator to the IServer that the
	 *        ServerPoller should poll
	 * @return the ServerPoller for the given communicator and model.
	 */
	public static ServerPoller getPoller(ClientCommunicator communicator) {
		if (poller != null) {
			if (poller.getClientCommunicator() == communicator) {
				return poller;
			}
			poller.stop();
		}
		poller = new ServerPoller(communicator);
		return poller;
	}

	protected ClientCommunicator getClientCommunicator() {
		return communicator;
	}

	/**
	 * Begins polling the server for the current model. Updates the client model
	 * if necessary.
	 * 
	 * @.pre The ServerPoller must have been given a valid ClientModel when it
	 *       was created
	 * @.pre The given ClientModel must still be valid and not null.
	 * @.post The ServerPoller begins continuously polling the server for the
	 *        model data. Will update the ClientModel as necessary.
	 */
	public void start() {
		runTask  = true;
		time = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if (runTask) {
					pollServer();
				} else {
					time.cancel();
					time.purge();
				}
			}
		};

		time.schedule(task, 0, 1000);
	}

	/**
	 * Stop polling the server
	 * 
	 * @.pre None. This method will always work.
	 * @.post If the ServerPoller was polling the server, it will stop.
	 */
	public void stop() {
		this.runTask = false;
	}

	/**
	 * Polls the server for the current model.
	 * 
	 * @.pre The ServerPoller was given a ClientModel that is still valid.
	 * @.post The result is either false (if the client was already up-to-date)
	 *        or the true (if the client was out-of-date).
	 */
	private void pollServer() {
//		System.out.println("Polling...");
		try {
            communicator.getGame(-1);
//			ClientModel newModel = communicator.getGame(ClientModel.getUpdatableModel().getVersion());
			//It will not automatically update when the communicator calls get game.
			//			updateModel(newModel);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to poll Server: " + e.getClass() + "\n" + e.getStackTrace().toString() + "" +  e.getMessage());
		}
	}

	/**
	 * Updates the ClientModel with the given new model.
	 * 
	 * @.pre NewModel is valid Json representation of a ClientModel per the
	 *       project specifications
	 * @.pre The ClientModel must still be valid.
	 * @.post The ClientModel will be told to update itself with the new model.
	 * @param newModel A Json representation of the new model.
	 */
	private void updateModel(ClientModel newModel) {
		ClientModel.setUpdatableModel(newModel);
	}
}