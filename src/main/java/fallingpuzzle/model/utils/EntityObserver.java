package fallingpuzzle.model.utils;

@FunctionalInterface
public interface EntityObserver {
	
	public abstract void update( Object info );	
	
}
