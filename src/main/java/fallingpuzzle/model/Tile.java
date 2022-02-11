package fallingpuzzle.model;

import java.util.ArrayList;

import fallingpuzzle.controller.TileSelectController;
import fallingpuzzle.model.utils.IndexChangeListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
	
	private ArrayList<Integer> indexes; 
	private IntegerProperty firstIndex;
	private int nCell;
	private Row row;
			
	public Tile( int firstIndex, int nCell, double width, double height ) {
		
		new TileSelectController( this );
//		new TileDragController( this );
		this.indexes = new ArrayList<Integer>();
		this.firstIndex = new SimpleIntegerProperty( 10 );
		
		//Updating first index will also update indexList
		this.firstIndex.addListener( new IndexChangeListener( this ) );
		
		
		this.nCell = nCell;
		this.setWidth( width );
		this.setHeight( height );
		this.firstIndex.set( firstIndex );
	}
	
	//Used by listener to update tile indexes as the first index changes
	public void updateIndexes( Number newValue ) {
		indexes.clear();
		for( int i = 0; i < nCell; ++i )
			indexes.add( newValue.intValue() + i );		
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
	
	public Row getRow() {
		return row;
	}
	
	public void setRow( Row row ) {
		this.row = row;
	}
	
	public void move( int index ) {
		this.firstIndex.set( index );
	}

}
