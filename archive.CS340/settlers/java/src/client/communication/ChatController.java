package client.communication;

import client.base.*;
import client.model.ClientModel;
import client.model.IPlayer;
import client.model.message.MessageLine;
import client.model.observable.Event;
import client.model.observable.EventObservable;
import client.model.observable.IObserver;
import serverProxy.ClientCommunicator;
import shared.definitions.CatanColor;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController {

	public ChatController(IChatView view) {
		
		super(view);

        EventObservable.getSingleton().subscribeToEvent(Event.UpdateChat, new IObserver<Integer>() {
            @Override
            public void update(Integer metadata) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        ClientModel model = ClientModel.getUpdatableModel();
                        List<MessageLine> messages = model.getChat().getMessages();
                        List<LogEntry> logEntries = new ArrayList<>();
                        Map<String, CatanColor> map = new HashMap<>();
                        for(IPlayer player : model.getPlayers()) {
                            map.put(player.getName(), player.getColor());
                        }
                        for(MessageLine ml : messages) {
                            LogEntry logEntry = new LogEntry(map.get(ml.getSource()), ml.getMessage());
                            logEntries.add(logEntry);
                        }
                        logEntries.toArray(new LogEntry[logEntries.size()]);
                        getView().setEntries(logEntries);
                    }
                });
            }
        });
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
        int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
        try{
            ClientCommunicator.getClientCommunicator().sendChat(message, playerIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}

