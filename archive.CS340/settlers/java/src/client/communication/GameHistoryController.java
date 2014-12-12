package client.communication;

import java.awt.*;
import java.util.*;
import java.util.List;

import client.base.*;
import client.model.ClientModel;
import client.model.IPlayer;
import client.model.message.MessageLine;
import client.model.observable.Event;
import client.model.observable.EventObservable;
import client.model.observable.IObserver;
import shared.definitions.*;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController {

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		
		initFromModel();

        EventObservable.getSingleton().subscribeToEvent(Event.UpdateLog, new IObserver<Integer>() {
            @Override
            public void update(Integer metadata) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        ClientModel model = ClientModel.getUpdatableModel();
                        List<MessageLine> messages = model.getLog().getMessages();
                        List<LogEntry> logEntries = new ArrayList<>();
                        Map<String, CatanColor> map = new HashMap<>();
                        for (IPlayer player : model.getPlayers()) {
                            map.put(player.getName(), player.getColor());
                        }
                        for (MessageLine ml : messages) {
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
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		//<temp>
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		entries.add(new LogEntry(CatanColor.WHITE, "Welcome to Catan"));
		getView().setEntries(entries);
	
		//</temp>
	}


	
}

