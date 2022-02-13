package fallingpuzzle.controller;

import fallingpuzzle.model.Tile;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class TileSelectController {
	
    protected final Tile target;
    protected EventHandler<MouseEvent> updateSelection;
    protected boolean canBeSelected;
    
    public TileSelectController( Tile target, boolean canBeSelected ) {
    	this.target = target;
    	this.canBeSelected = canBeSelected;
    	updateSelection = event -> GameController.updateSelectedTile( target );
    	if( canBeSelected )
    		addFilter();
    }
    
    public void addFilter() {
    	target.addEventFilter( MouseEvent.MOUSE_CLICKED, updateSelection );
    }
    
    public void removeFilter() {
    	target.removeEventFilter( MouseEvent.MOUSE_CLICKED, updateSelection );
    }
    
    public void setSelectable( boolean selectable ) {
    	if( selectable ) {
    		if( canBeSelected ) return;
    		addFilter();
    	}
    	else {
    		if( !canBeSelected ) return;
    		removeFilter();
    	}
    }
    
}
