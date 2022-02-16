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
    
    @FXML
    private Label lblScore;
    
    
    public static Scene getScene() {
    	return getScene( "/view/Game.fxml" );
    }
    
    public void addScore( int score ) {
    	int currentScore = Integer.parseInt( lblScore.getText() );
    	currentScore += score;
    	StringBuilder sb = new StringBuilder();
    	sb.append( currentScore );
    	lblScore.setText( sb.toString() );
    }
    
    
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
    	
    	System.out.println( selectedTile.getIndexes().toString() );
    	
		selectedTile.setWidth( selectedTile.getWidth() - 2 );
		selectedTile.setHeight( selectedTile.getHeight() - 2 );
		selectedTile.setX( selectedTile.getX() + 1 );
		selectedTile.setY( selectedTile.getY() + 1 );
			
    }
    
    public void genRow() {
    	//add row to preview vbox
		Row.createRow( vboNextRow, rowMediator );
		
		//shift upper row to game vbox
		if( vboNextRow.getChildren().size() > 1 ) {
			Row row1 = ( Row ) vboNextRow.getChildren().get( 0 );
			row1.setParent( vboRows );
			//add some features to tails
			for( int i = 0; i < row1.getChildren().size(); ++i ) { 
				Tile tile = ( Tile ) row1.getChildren().get( i ); 
				tile.setSelectable( true ); 
				tile.setDraggable( true ); 
			}
			
			vboNextRow.getChildren().remove( row1 );
		}
		
		rowMediator.update();
		
		if( vboRows.getChildren().size() > 10 ) vboRows.getChildren().clear();
    }

    
    @FXML
    public void initialize() {
    	
    	rowMediator = new RowMediator( vboRows.getChildren(), this );
    	
    	btnRowUp.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				genRow();
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

