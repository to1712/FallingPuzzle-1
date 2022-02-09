package fallingpuzzle.sprite;

import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class TileSprite extends Sprite {
	
	private static final TileSprite S_TILE = new TileSprite( 1 );
	private static final TileSprite D_TILE = new TileSprite( 2 );
	private static final TileSprite T_TILE = new TileSprite( 3 );
	
	public static TileSprite getSprite( int size ) {
		switch( size ) {
			case 2 :
				return D_TILE;
			case 3 :
				return T_TILE;
			default :
				return S_TILE;
		}
	}
	
	private TileSprite( int size ) {
		super( loadSprite(), 64 * size, 64, 1.0, 1.0 );
	}
	
	private static BufferedImage loadSprite() {
		try {
			return ImageIO.read( TileSprite.class.getResource( "/images/tile_spriteV2.png" ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
