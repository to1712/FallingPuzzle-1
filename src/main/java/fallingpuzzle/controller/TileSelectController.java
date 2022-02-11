package fallingpuzzle.controller;

import fallingpuzzle.model.Tile;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class TileSelectController {
	
    protected final Tile target;
    protected EventHandler<MouseEvent> updateSelection;
    
    public TileSelectController( Tile target ) {
    	this.target = target;
    	updateSelection = event -> GameController.updateSelectedTile( target );
    	target.addEventFilter( MouseEvent.MOUSE_CLICKED, updateSelection );
    }
        
    
}
