package fallingpuzzle.controller.scene;

import java.io.File;
import fallingpuzzle.controller.Controller;
import fallingpuzzle.controller.ia.AIService;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

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
    private MenuItem mniInitBoard;
    @FXML
    private MenuItem mniRowUp;
    @FXML
    private RadioMenuItem rmiRunAi;  
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
    
    private static RowMediator rowMediator;
    private DLVController dlvController;
    private static Tile selectedTile;
    private EventHandler<ActionEvent> iASwitch;
    private EventHandler<ActionEvent> rowUp;
    private EventHandler<ActionEvent> initBoard;
    private AIService AiCycle;
    
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
    
    public void genDLVFile() {
		DLVFileBuilder dlvFileBuilder = new DLVFileBuilder();
		dlvFileBuilder.createFile( vboRows.getChildren() );
		File file = dlvFileBuilder.getFile();
		Pair<Tile, Integer> tileMove = dlvController.start( file );
		Tile tile = tileMove.getKey();
		int index = tileMove.getValue();
		moveTile( tile, index );
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
		
		//GameOver
		if( vboRows.getChildren().size() > 10 ) {
			System.out.println("GAME OVER");
			reset();
		}
		
    }
    
    public static void moveTile( Tile tile, int index ) {
    	Row row = ( Row ) tile.getParent();
    	if( row.moveTile( tile, index ) ) {
			rowMediator.update();
			rowMediator.requestNewRow();
    	}
    }
    
    private void reset() {
    	vboNextRow.getChildren().clear();
    	vboRows.getChildren().clear();
    	lblScore.setText( "0" );
    }
    
    @FXML
    public void initialize() {
    	rowMediator = new RowMediator( vboRows.getChildren(), this );
    	dlvController = new DLVController( this );
		AiCycle = new AIService( rmiRunAi, this );
    	
    	rowUp = event -> {  genRow(); rowMediator.update(); };
    	mniRowUp.setOnAction( rowUp ); 

    	iASwitch = event -> { 
    		AiCycle.restart();
    	};
    	rmiRunAi.setOnAction( iASwitch );
    	
    	initBoard = event -> { while( vboRows.getChildren().size() < 4 ) { rowUp.handle( null ); } };
    	mniInitBoard.setOnAction( initBoard );

    }
    
    
}

