package utils.observer;

public interface Observer<E extends Event> {
	public void handleNewEvent(E e);

}
