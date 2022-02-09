package fallingpuzzle.controller;

import java.util.List;

import fallingpuzzle.model.Tile;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;


//this whole class comes from https://edencoding.com/drag-shapes-javafx/

public class DragController {
	
    protected final Node target;
    protected double anchorX;
    protected double anchorY;
    protected double mouseOffsetFromNodeZeroX;
    protected double mouseOffsetFromNodeZeroY;
    protected EventHandler<MouseEvent> setAnchor;
    protected EventHandler<MouseEvent> updatePositionOnDrag;
    protected EventHandler<MouseEvent> commitPositionOnRelease;
    protected final int ACTIVE = 1;
    protected final int INACTIVE = 0;
    protected int cycleStatus = INACTIVE;
    protected BooleanProperty isDraggable;
    
    public DragController( Node target ) {
        this(target, false);
    }
    
    public DragController(Node target, boolean isDraggable) {
        this.target = target;
        createHandlers();
        createDraggableProperty();
        this.isDraggable.set(isDraggable);
    }
    
    protected void createHandlers() { 
    	
	    setAnchor = event -> {
	            if ( event.isPrimaryButtonDown() ) {
	                cycleStatus = ACTIVE;
	                anchorX = event.getSceneX();
	                anchorY = event.getSceneY();
	                mouseOffsetFromNodeZeroX = event.getX();
	                mouseOffsetFromNodeZeroY = event.getY();
	            }
	            if ( event.isSecondaryButtonDown() ) {
	                cycleStatus = INACTIVE;
	                target.setTranslateX(0);
	                target.setTranslateY(0);
	            }
	        };
        
        updatePositionOnDrag = event -> {
            if (cycleStatus != INACTIVE) {
                target.setTranslateX(event.getSceneX() - anchorX);
                target.setTranslateY(event.getSceneY() - anchorY);
            }
        };
        
        commitPositionOnRelease = event -> {
            if (cycleStatus != INACTIVE) {
                //commit changes to LayoutX and LayoutY
                target.setLayoutX( event.getSceneX() - mouseOffsetFromNodeZeroX );
                target.setLayoutY( event.getSceneY() - mouseOffsetFromNodeZeroY );
                //clear changes from TranslateX and TranslateY
                target.setTranslateX(0);
                target.setTranslateY(0);
            }
        };
        
    }
    
    public void createDraggableProperty() {
        isDraggable = new SimpleBooleanProperty();
        isDraggable.addListener( ( observable, oldValue, newValue ) -> {
        	if (newValue) {
                target.addEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor );
                target.addEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                target.addEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
            } else {
                target.removeEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
                target.removeEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                target.removeEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
            }
        });
    }
    
    protected boolean collidingWithOtherTiles( Node currentTile, ObservableList<Node> tiles, double newX ) {
        
    	for( int i = 0; i < tiles.size(); ++i ) {
    		Node tile = tiles.get( i );
    		if( tile == currentTile ) continue;
        	//check left side
            if( tile.getLayoutBounds().getMaxX() > ( newX + currentTile.getLayoutBounds().getMinX() ) ) {
                return true;
            }
            //check right side
            if( tile.getLayoutBounds().getMinX() < ( newX + currentTile.getLayoutBounds().getMaxX() ) ) {
                return true;
            }
    	}
        return false;
    }
    
    protected boolean outSideParentBounds( Bounds childBounds, Bounds parentBounds, double newX ) {
        
    	//check if too left
        if( parentBounds.getMaxX() <= ( newX + childBounds.getMaxX() ) ) {
            return true ;
        }
        //check if too right
        if( parentBounds.getMinX() >= ( newX + childBounds.getMinX() ) ) {
            return true ;
        }
        return false;
    }
    
    public boolean getIsDraggable() {
        return isDraggable.get();
    }
    
    public BooleanProperty isDraggableProperty() {
        return isDraggable;
    }
    
}