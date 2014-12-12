package client.model.observable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * An observable that allows observers to subscribe to it base on events.
 * When an event is signaled, all of the associated observers will be notified and passed any relevant metadata.
 * 
 * @author Jacob Glad
 *
 */
@SuppressWarnings("rawtypes")
public class EventObservable {
	
	private static EventObservable singleton;
	
	public static EventObservable getSingleton(){
		if(singleton == null){
			singleton = new EventObservable();
		}
		return singleton;
	}
	public static void resetSingleton() {
		singleton = new EventObservable();
	}
	
	private Map<Event,List<IObserver>> observerDirectory;  
	private EventObservable() {
		observerDirectory = new HashMap<Event,List<IObserver>>();
	}

	/**
	 * Subscribes the passed observer to be updated when the specified event is signaled
	 * @param event The event that the observer is listening for
	 * @param observer The observer to update
	 */
	public <T> void subscribeToEvent(Event<T> event, IObserver<T> observer){
		List<IObserver> observers = observerDirectory.containsKey(event) ?			
			observerDirectory.get(event):
			new LinkedList<IObserver>();
		observers.add(observer);
		
		observerDirectory.put(event, observers);
	}
	
	/**
	 * Signal the passed event with no metadata
	 * 
	 * @param event The event to signal
	 * @throws EventNotRegisteredException  Will throw an exception if no observers are listening for the event
	 */
	public <T> void signalEvent(Event<T> event) throws EventNotRegisteredException{
		this.signalEvent(event, null);
	}
	
	/**
	 * Signal the passed event
	 * @param event The event to signal
	 * @param metadata Some data associated with the event
	 * @throws EventNotRegisteredException Will throw an exception if no observers are listening for the event
	 */
	@SuppressWarnings("unchecked")
	public <T> void signalEvent(Event<T> event, T metadata) throws EventNotRegisteredException{
		if(observerDirectory.containsKey(event)){
			for(IObserver<T> observer : observerDirectory.get(event)){
				observer.update(metadata);
			}
		}
		else{
			throw new EventNotRegisteredException(event);
		}
	}
	
	/**
	 * 
	 * @author Jacob Glad
	 *
	 * An exception thrown when trying to access an event that has not been registered with the observable
	 */
	@SuppressWarnings("serial")
	public class EventNotRegisteredException extends Exception{
		
		public EventNotRegisteredException(Event event){
			super("The event " + event.toString() + " has not been registered");
		}
	}
}
