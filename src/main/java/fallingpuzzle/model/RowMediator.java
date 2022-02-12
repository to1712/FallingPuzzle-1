package fallingpuzzle.model;

import java.util.concurrent.TimeUnit;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public class RowMediator {
	
	private ObservableList<Node> rows;
	
	public RowMediator( ObservableList<Node> observableList ) {
		this.rows = observableList;
	}
	
	
	/* THIS STUFF IS EVIL, MY BRAIN IS MELTING AND IT STILL DOES NOT WORK */
	public boolean checkFall( Row row ) {
		
		//wait a little
		try { TimeUnit.MICROSECONDS.sleep( 100 ); } 
		catch (InterruptedException e) { e.printStackTrace(); }
		
		int rowPos = getRowPosition( row );
		
		
		if( rowPos > 0  ) checkFall( ( Row ) rows.get( rowPos -1 ) ); //checks for fall on rows above anyway <-- RECURSIVE CALL 1
		if( rowPos == rows.size() - 1 ) return false; // no rows below it <-- EXIT CONDITION
		Row rowBelow = ( Row ) rows.get( rowPos + 1 );
		
		boolean fell = false;
		
		for( int i = 0; i < row.getChildren().size(); ++i ) {
			Tile tile = (Tile) row.getChildren().get( i );
			if( rowBelow.collidesWithOtherTiles( tile ) ) continue; //tile can't fall 'cause indexes below it aren't empty
			rowBelow.insert( tile ); //put tile in new row
			row.remove( tile ); //remove tile from old row
			fell = true;
		}
		rowBelow.updateRow();
		
		rowPos = getRowPosition( row ); //if rows have been removed rowPos needs to be calculated once again
						
		return true;
	}
	
	
	public void removeRow( Row row ) {
		rows.remove( row );
	}
	
	public int getRowPosition( Row row ) {
		for( int i = 0; i < rows.size(); ++i )
			if( rows.get( i ).equals( row ) ) return i;
		return 0;
	}


}
