package utils.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<E extends Event> {

	private List<Observer<E>> observers = new ArrayList<>();
	
	public void addObserver(Observer<E> o) {
		observers.add(o);
	}
	
	protected void dispatchEvent(E e) {
		for (Observer<E> o : observers) {
			o.handleNewEvent(e);
		}
	}
}
