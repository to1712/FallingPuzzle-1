package fallingpuzzle.model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Row extends Pane {
	
	private VBox parent;
	
	private Row( VBox parent ) {
		this.parent = parent;
		parent.getChildren().add( this );
		this.setMinWidth( parent.getWidth() );
		this.setMaxWidth( parent.getWidth() );
		this.setMaxHeight( parent.getHeight() / 8 );
		this.setMinHeight( parent.getHeight() / 8  );
		this.setWidth( parent.getWidth() );
		this.setHeight( parent.getHeight() / 8 );
	}
	
	private Row() {}
	
	/* Only inserts tiles which can fit inside this row */
	public void insert( List<Tile> tilesToInsert ) {
		tilesToInsert.forEach( tile -> { if( !collidesWithOtherTiles( tile ) ) { getChildren().add( tile ); tile.setRow( this ); } } );
		updateTilesCoords();
	}
	
	/* Only inserts tiles which can fit inside this row */
	public void insert( Tile tileToInsert ) {
		if( !collidesWithOtherTiles( tileToInsert ) ) {
			getChildren().add( tileToInsert );
			tileToInsert.setRow( this );
		}
		updateTilesCoords();
	}
	
	/* Used by controller to move a tile */
	public void moveTile( Tile tile, int index ) {
		int oldIndex = tile.getFirstIndex();
		tile.move( index );
		if( collidesWithOtherTiles( tile ) ) tile.move( oldIndex );
	}
	
	/* Updates tile's X for it to be correctly displayed on screen */
	public void updateTilesCoords() {
		getChildren().forEach( node -> {
			Tile tile = ( Tile ) node;
			tile.setX( tile.getFirstIndex() * ( this.getWidth() / 8 ) );
		} );
	}
	
	/* Checks for each tile already in if the tested one has any index in common */
	private boolean collidesWithOtherTiles( Tile tileToTest ) {
		ArrayList<Integer> unavailableIndexes = new ArrayList<Integer>();
		this.getChildren().forEach( node -> {
			Tile tile = ( Tile ) node;
			if( !tile.equals( tileToTest ) )
				unavailableIndexes.addAll( tile.getIndexes() );
		});
		for( Integer i : tileToTest.getIndexes() )
			if( unavailableIndexes.contains( i ) || i < 0 || i > 7 ) {
				return true;
			}
		return false;
	}
	
	//this deletes a row once it is completed ( WIP )
	public void updateRow() {
		int indexSum = 0;
		for( Node node : getChildren() )
			indexSum += (( Tile ) node).getIndexSum();
		if( indexSum == 28 ) parent.getChildren().remove( this );
	}
	
	//unsure if i should create a mediator to work with all the rows at once
	public void checkFallingTiles() {
		
	}
	
	/* F method */
	public static Row createRow( VBox parent ) {
		Row row = new Row( parent );
		TileGenerator tg = new TileGenerator();
		tg.genTiles( row );
		
		return row;
	}

}
