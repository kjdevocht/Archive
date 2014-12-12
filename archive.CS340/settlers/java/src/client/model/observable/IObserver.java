package client.model.observable;

public interface IObserver<T> {
	public void update(T metadata);
}
