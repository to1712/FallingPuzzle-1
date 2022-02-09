package fallingpuzzle.model.utils;

import java.util.ArrayList;

public interface Observable {
	
	static final ArrayList<EntityObserver> observers = new ArrayList<EntityObserver>();
	
	public default Object getInfo() { return this; }
	
	public default void addObserver( EntityObserver observer ) {
		observers.add( observer );
	}
	
	public default void removeObserver( EntityObserver observer ) {
		observers.remove( observer );
	}
	
	default void notifyToObservers() {
		observers.forEach( observer -> { observer.update( getInfo() ); } );
	}
	
}
