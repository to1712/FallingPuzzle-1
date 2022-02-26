package fallingpuzzle.controller.scene;

import java.io.File;

import fallingpuzzle.controller.Controller;
import fallingpuzzle.controller.ia.DLVController;
import fallingpuzzle.controller.ia.DLVFileBuilder;
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
    private Button btnIa;
    
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
    
    
    public Row getRow( int rowIndex ) {
    	return ( Row ) vboRows.getChildren().get( rowIndex );
    }
    
    private RowMediator rowMediator;
    private DLVController dlvController;
    private static Tile selectedTile;
    private boolean aiOn = false;
    public static void updateSelectedTile( Tile newTile ) {
    	    	
    	if( selectedTile != null ) { 
    		selectedTile.setWidth( selectedTile.getWidth() + 2 );
    		selectedTile.setHeight( selectedTile.getHeight() + 2 );
    		selectedTile.setX( selectedTile.getX() - 1 );
    		selectedTile.setY( selectedTile.getY() - 1 );
    	}   	
    	selectedTile = newTile;
    	
 //   	System.out.println( selectedTile.getIndexes().toString() );
    	
		selectedTile.setWidth( selectedTile.getWidth() - 2 );
		selectedTile.setHeight( selectedTile.getHeight() - 2 );
		selectedTile.setX( selectedTile.getX() + 1 );
		selectedTile.setY( selectedTile.getY() + 1 );
			
    }
    
    public void genDLVFile() {
		DLVFileBuilder dlvFileBuilder = new DLVFileBuilder();
		dlvFileBuilder.createFile( vboRows.getChildren() );
		File file = dlvFileBuilder.getFile();
		dlvController.start( file );
		file.delete();
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
		
		if( vboRows.getChildren().size() > 10 ) vboRows.getChildren().clear();
		if( aiOn )
			genDLVFile();
    }

    
    @FXML
    public void initialize() {
    	
    	rowMediator = new RowMediator( vboRows.getChildren(), this );
    	dlvController = new DLVController( this );
    	
    	btnRowUp.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				genRow();
				rowMediator.update();
			}
		});
    	
    	btnIa.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				aiOn = ( aiOn ) ? false : true;
			}
		});

    	    	
    }

    
}

