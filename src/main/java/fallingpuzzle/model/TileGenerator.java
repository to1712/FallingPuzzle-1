package fallingpuzzle.model;

import java.util.Random;

import javafx.scene.paint.Color;

public class TileGenerator {
	
	public void genTiles( Row row ) {
		Random random = new Random();
		
		double tileHeight = ( row.getHeight() >= 73 ) ? row.getHeight() : 73;
		
		
		while( row.getChildrenUnmodifiable().size() < 3 ) {
			int firstIndex = random.nextInt( 8 );
			int nCell = 0;
			
			nCell = random.nextInt( 3 ) + 1;
									
			Tile tile = new Tile( firstIndex, nCell, row.getWidth() / 8, tileHeight );
			
			while( true ) {
				if( tile.getIndexes().get( tile.getNCell() - 1 ) < 8 ) break;
				tile.setNCell( random.nextInt( 3 ) + 1 );
			}
			
			if( tile.getNCell() == 1 )
				tile.setFill( Color.BLACK );
			else if( tile.getNCell()  == 2 )
				tile.setFill( Color.RED );
			else
				tile.setFill( Color.BLUE );
			
			
			row.insert( tile, true );
			
			if( row.isFull() ) row.getChildren().clear();
			genTiles( row );
			
		}
	}
	
}
