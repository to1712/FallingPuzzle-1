package fallingpuzzle.controller;

import javafx.scene.Node;

public class TileDragController extends DragController {

	public TileDragController( Node target ) {
		super( target, true );
	}
	
	@Override
	protected void createHandlers() { 
    	
	    setAnchor = event -> {
	            if ( event.isPrimaryButtonDown() ) {
	                cycleStatus = ACTIVE;
	                anchorX = event.getSceneX();
	                mouseOffsetFromNodeZeroX = event.getX();
	            }
	            if ( event.isSecondaryButtonDown() ) {
	                cycleStatus = INACTIVE;
	                target.setTranslateX(0);
	            }
	        };
        
        updatePositionOnDrag = event -> {
            if (cycleStatus != INACTIVE) {
            	double newX = event.getSceneX() - anchorX;
            	if( !( outSideParentBounds( target.getLayoutBounds(), target.getParent().getLayoutBounds(), target.getLayoutX() + newX ) ) &&
            			!( collidingWithOtherTiles( target, target.getParent().getChildrenUnmodifiable(), newX ) ) )
            		target.setTranslateX( newX );
            }
        };
        
        commitPositionOnRelease = event -> {
            if (cycleStatus != INACTIVE) {
            		target.setLayoutX( target.getLayoutX() + target.getTranslateX() );
	                target.setTranslateX( 0 );
            }
        };
        
    }
	
}
