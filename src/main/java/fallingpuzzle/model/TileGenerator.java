package fallingpuzzle.model;

import java.util.Random;

import javafx.scene.paint.Color;

public class TileGenerator {
	
	public void genTiles( Row row ) {
		Random random = new Random();
		
		while( row.getChildrenUnmodifiable().size() < 3 ) {
			int firstIndex = random.nextInt( 8 );
			int nCell = random.nextInt( 3 ) + 1;
			Tile tile = new Tile( firstIndex, nCell, ( ( row.getWidth() / 8 ) * nCell ) - 2, row.getHeight() - 2 );
			
			if( nCell == 1 )
				tile.setFill( Color.BLACK );
			else if( nCell == 2 )
				tile.setFill( Color.RED );
			else
				tile.setFill( Color.BLUE );
			
			row.insert( tile );
		}
	}
	
}
