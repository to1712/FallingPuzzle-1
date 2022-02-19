package fallingpuzzle.model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Row extends Pane {
		
	private RowMediator rowMediator;
	
	private Row( VBox parent, RowMediator rowMediator ) {
		setParent( parent );
		this.rowMediator = rowMediator;
	}
	
	private Row() {}
	
	@Override
	public boolean equals( Object row ) {
		if( row instanceof Row )
			return this.getChildren().containsAll( ( (Row ) row).getChildren() );
		return false;
	}
	
	public boolean isFull() {
		int indexCount = 0;
		for( int i = 0; i < getChildren().size(); ++i ) {
			indexCount += (( Tile ) getChildren().get( i ) ).getIndexes().size();
		}
		if( indexCount == 8 ) return true;
		return false;
	}
	
	public void setParent( VBox parent ) {
		parent.getChildren().add( this );
		this.setMinWidth( parent.getWidth() );
		this.setMaxWidth( parent.getWidth() );
		this.setMaxHeight( parent.getHeight() / 10 );
		this.setMinHeight( parent.getHeight() / 10  );
		this.setWidth( parent.getWidth() );
		this.setHeight( parent.getHeight() / 10 );
		for( int i = 0; i< getChildren().size(); ++i ) {
			Tile tile = ( Tile ) getChildren().get( i );
			tile.updateTileSize( this.getWidth() / 8, this.getHeight() );
		}
		updateTilesCoords();
	}
	
	/* Only inserts tiles which can fit inside this row */
	public void insert( List<Tile> tilesToInsert ) {
		for( int i = 0; i < tilesToInsert.size(); ++i ) {
			Tile tile = ( Tile ) tilesToInsert.get( i );
			if( !collidesWithOtherTiles( tile ) ) { 
				getChildren().add( tile ); 
				tile.setRow( this ); 
			} 
		}
		updateTilesCoords();
	}
	
	/* Only inserts a tile which can fit inside this row */
	public void insert( Tile tileToInsert, boolean checkForCollision ) {
		if( !checkForCollision || !collidesWithOtherTiles( tileToInsert ) ) {
			getChildren().add( tileToInsert );
			tileToInsert.setRow( this );
		}
		updateTilesCoords();
	}
	
	/* Used by controller to move a tile */
	public void moveTile( Tile tile, int index ) {
		int oldIndex = tile.getFirstIndex();
		tile.move( index );
		if( collidesWithOtherTiles( tile ) ) {
			tile.move( oldIndex );
		}
		else {
			rowMediator.update();
			rowMediator.requestNewRow();
		}
		
	}
	
	public void remove( Tile tile ) {
		getChildren().remove( tile );
	}
	
	/* Updates tile's X for it to be correctly displayed on screen */
	public void updateTilesCoords() {
		for( int i = 0; i < getChildren().size(); ++i ) {
			Tile tile = ( Tile ) getChildren().get( i );
			tile.setX( tile.getFirstIndex() * ( this.getWidth() / 8 ) );
		}
	}
	
	/* Checks for each tile already in if the tested one has any index in common */
	public boolean collidesWithOtherTiles( Tile tileToTest ) {
		ArrayList<Integer> unavailableIndexes = new ArrayList<Integer>();
		for( int i = 0; i < getChildren().size(); ++i ) {
			Tile tile = ( Tile ) getChildren().get( i );
			if( !tile.equals( tileToTest ) )
				unavailableIndexes.addAll( tile.getIndexes() );
		}
		for( int i = 0; i < tileToTest.getIndexes().size(); ++i )
			if( unavailableIndexes.contains( tileToTest.getIndexes().get( i ) ) || i < 0 || i > 7 ) {
				return true;
			}
		return false;
	}
	
	/* Checks for each tile already in if the tested one has any index in common if you try to move it */
	public boolean collidesWithOtherTiles( Tile tileToTest, int mockFirstIndex ) {
		ArrayList<Integer> unavailableIndexes = new ArrayList<Integer>();
		for( int i = 0; i < getChildren().size(); ++i ) {
			Tile tile = ( Tile ) getChildren().get( i );
			if( !tile.equals( tileToTest ) )
				unavailableIndexes.addAll( tile.getIndexes() );
		}
		
		int trueFirstIndex = tileToTest.getFirstIndex();
		tileToTest.move( mockFirstIndex );
		
		for( int i = 0; i < tileToTest.getIndexes().size(); ++i )
			if( unavailableIndexes.contains( tileToTest.getIndexes().get( i ) ) || i < 0 || i > 7 ) {
				tileToTest.move( trueFirstIndex );
				return true;
			}
		tileToTest.move( trueFirstIndex );
		return false;
	}
		
	
	/* F method */
	public static Row createRow( VBox parent, RowMediator rowMediator ) {
		Row row = new Row( parent, rowMediator );
		TileGenerator tg = new TileGenerator();
		tg.genTiles( row );
		
		return row;
	}
	

}
