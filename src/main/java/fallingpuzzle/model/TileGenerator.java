package fallingpuzzle.model;

import java.util.Random;

public class TileGenerator {
	
	public void genTiles( Row row ) {
		Random random = new Random();
		
		while( row.getChildrenUnmodifiable().size() < 4 ) {
			int firstIndex = random.nextInt( 8 );
			int nCell = random.nextInt( 3 ) + 1;
			Tile tile = new Tile( firstIndex, nCell, ( row.getWidth() / 8 ), row.getHeight() );
			row.insert( tile );
		}
	}
	
}
