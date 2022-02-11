package fallingpuzzle.model;

import java.util.ArrayList;
import fallingpuzzle.controller.TileDragController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
	
	private final TileDragController dc = new TileDragController( this );
	private final ArrayList<Integer> indexes = new ArrayList<Integer>();
	private IntegerProperty firstIndex = new SimpleIntegerProperty();
			
	public Tile( int firstIndex, int nCell, double width, double height ) {
	
		//Updating first index will also update indexList
		this.firstIndex.addListener( new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				indexes.clear();
				for( int i = 0; i < nCell; ++i )
					indexes.add( newValue.intValue() + i );
			}
		});
		
		this.setWidth( width );
		this.setHeight( height );
		this.firstIndex.setValue( firstIndex );
	}
		
	public Tile( int firstIndex, int nCell ) {
		this( firstIndex, nCell, 0.0, 0.0 );
	}
	
	public ArrayList<Integer> getIndexes() {
		return indexes;
	}
	
	public Integer getFirstIndex() {
		return firstIndex.get();
	}

}
