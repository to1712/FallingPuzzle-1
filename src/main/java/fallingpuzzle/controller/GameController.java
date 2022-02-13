package fallingpuzzle.controller;

import fallingpuzzle.model.Row;
import fallingpuzzle.model.RowMediator;
import fallingpuzzle.model.Tile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class GameController extends Controller {
	
    @FXML
    private VBox vboRows;
    
    @FXML
    private VBox vboNextRow;
	
    @FXML
    private AnchorPane achBoard;

    @FXML
    private Label lblGameTitle;

    @FXML
    private Canvas cnvGameBG;

    @FXML
    private Button btnRowUp;
    
    @FXML
    private Button btnRight;
    
    @FXML
    private Button btnLeft;
    
    
    
    public static Scene getScene() {
    	return getScene( "/view/Game.fxml" );
    }
    
    //testing stuff
    private RowMediator rowMediator;
    private static Tile selectedTile;
    public static void updateSelectedTile( Tile newTile ) {
    	    	
    	if( selectedTile != null ) { 
    		selectedTile.setWidth( selectedTile.getWidth() + 2 );
    		selectedTile.setHeight( selectedTile.getHeight() + 2 );
    		selectedTile.setX( selectedTile.getX() - 1 );
    		selectedTile.setY( selectedTile.getY() - 1 );
    	}   	
    	selectedTile = newTile;
    	
		selectedTile.setWidth( selectedTile.getWidth() - 2 );
		selectedTile.setHeight( selectedTile.getHeight() - 2 );
		selectedTile.setX( selectedTile.getX() + 1 );
		selectedTile.setY( selectedTile.getY() + 1 );
			
    }

    
    @FXML
    public void initialize() {
    	
    	rowMediator = new RowMediator( vboRows.getChildren() );
    	
    	btnRowUp.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				
				//add row to preview vbox
				Row.createRow( vboNextRow, rowMediator );
				
				//shift upper row to game vbox
				if( vboNextRow.getChildren().size() > 1 ) {
					Row row1 = ( Row ) vboNextRow.getChildren().get( 0 );
					row1.setParent( vboRows );
					row1.getChildren().forEach(  node -> { Tile tile = ( Tile ) node; tile.setSelectable( true ); } );
					vboNextRow.getChildren().remove( row1 );
				}
				
				rowMediator.update();
				
				if( vboRows.getChildren().size() >= 8 ) vboRows.getChildren().clear();
				
			}
		});
    	
    	btnLeft.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if( selectedTile == null ) return;
				selectedTile.getRow().moveTile( selectedTile, selectedTile.getFirstIndex() - 1 );
			}
		});
    	
    	btnRight.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if( selectedTile == null ) return;
				selectedTile.getRow().moveTile( selectedTile, selectedTile.getFirstIndex() + 1 );
			}
		});
    	
    }

    
}

