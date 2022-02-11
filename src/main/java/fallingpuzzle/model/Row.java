package fallingpuzzle.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Row extends Pane {
	
	private Row( VBox parent ) {
		parent.getChildren().add( this );
		this.resize( parent.getWidth(), parent.getHeight() / 8 );		
	}
	
	private Row() {}
	
	/* Only inserts tiles which can fit inside this row */
	public void insert( List<Tile> tilesToInsert ) {
		tilesToInsert.forEach( tile -> { if( !collidesWithOtherTiles( tile ) ) getChildren().add( tile ); } );
		updateTilesCoords();
	}
	
	/* Only inserts tiles which can fit inside this row */
	public void insert( Tile tileToInsert ) {
		if( !collidesWithOtherTiles( tileToInsert ) ) {
			getChildren().add( tileToInsert );
			System.out.println( "newTile - values" );
			System.out.println( "firstIndex: " + tileToInsert.getFirstIndex() );
			System.out.println( "all idexes: " + tileToInsert.getIndexes().toString() );
			System.out.println( "size:" + tileToInsert.getIndexes().size() );
			System.out.println();
		}
		updateTilesCoords();
	}
	
	
	private void updateTilesCoords() {
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
			unavailableIndexes.addAll( tile.getIndexes() );
		});
		for( Integer i : tileToTest.getIndexes() )
			if( unavailableIndexes.contains( i ) || i < 0 || i > 8 ) return true;
		return false;
	}
	
	public static Row createRow( VBox parent ) {
		Row row = new Row( parent );
		TileGenerator tg = new TileGenerator();
		tg.genTiles( row );
		
		return row;
	}

}
