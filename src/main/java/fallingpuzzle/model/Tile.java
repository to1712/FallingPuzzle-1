package fallingpuzzle.model;

import fallingpuzzle.controller.TileDragController;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
	
	private TileDragController dc;
		
	public Tile( double x, double y, double width, double height ) {
		this.setX( x );
		this.setY( y );
		this.setWidth( width );
		this.setHeight( height );
		this.dc = new TileDragController( this );
	}
	

}
