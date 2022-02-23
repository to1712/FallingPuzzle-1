package fallingpuzzle.model;

import fallingpuzzle.controller.scene.GameController;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class RowMediator {
	
	private ObservableList<Node> rows;
	private GameController gameController;
	
	public RowMediator( ObservableList<Node> observableList, GameController gameController ) {
		this.gameController = gameController;
		this.rows = observableList;
	}
	
	
	public void update() {
		checkFall();
	}
	
	
	/* MAIN ALGORITHM */
	// 1 - while -> check each row for falling tiles ( starting from bottom ) returns true
	// 2 - if -> check for a full row ( starting from bottom )
	// 2a true -> remove it then go to step 1
	// 2b false -> end
	public void checkFall() {
		
		int score = 0;
		
		boolean cycle = true;		
		while( cycle ) {			
			cycle = false;			
			//step 1
			while( handleFallingTiles() ) {}				
			
			//step 2
			if( handleFullRows() ) {
				cycle = true;		
				++score;
			}
		}
		gameController.addScore( score );
				
	}
	
	private boolean handleFallingTiles() {
		boolean falling = false;
		for( int i = rows.size() - 2; i >= 0; --i ) {
			Row currentRow = ( Row ) rows.get( i );
			Row nextRow = ( Row ) rows.get( i + 1 );
			for( int j = 0; j < currentRow.getChildren().size(); ++j ) {
				Tile tile = ( Tile ) currentRow.getChildren().get( j );
				if( !nextRow.collidesWithOtherTiles( tile ) ) {
					nextRow.insert( tile, false );
					currentRow.remove( tile );
					falling = true;
				}
			}
		}
		return falling;
	}
	
	private boolean handleFullRows() {
		for( int i = rows.size() - 1; i >= 0; --i ) {
			Row currentRow = ( Row ) rows.get( i );
			if( currentRow.isFull() ) {
			//	try { TimeUnit.SECONDS.sleep( 2 ); } catch (InterruptedException e) { e.printStackTrace(); }
				rows.remove( currentRow );
				return true;
			}
		}
		return false;
	}
	
	public void removeRow( Row row ) {
		rows.remove( row );
	}
	
	public int getRowPosition( Row row ) {
		for( int i = 0; i < rows.size(); ++i )
			if( rows.get( i ).equals( row ) ) return i;
		return 0;
	}


	public void requestNewRow() {
		gameController.genRow();
		update();
	}

}
