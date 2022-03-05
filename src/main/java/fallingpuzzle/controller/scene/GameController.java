package fallingpuzzle.controller.scene;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import fallingpuzzle.controller.Controller;
import fallingpuzzle.controller.ia.AIService;
import fallingpuzzle.controller.ia.DLVController;
import fallingpuzzle.controller.ia.DLVFileBuilder;
import fallingpuzzle.model.Row;
import fallingpuzzle.model.Tile;
import fallingpuzzle.model.TileGenerator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    
    private DLVController dlvController;
    private static Tile selectedTile;
    private EventHandler<ActionEvent> iASwitch;
    private EventHandler<ActionEvent> rowUp;
    private EventHandler<ActionEvent> initBoard;
    private static AIService AiCycle;
    private ObservableList<Node> rows;
    private AtomicBoolean isReady = new AtomicBoolean( true );
    
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
    
    public boolean isReady() {
    	return isReady.get();
    }
    
    public void genDLVFile() {
		DLVFileBuilder dlvFileBuilder = new DLVFileBuilder();
		dlvFileBuilder.createFile( rows );
		File file = dlvFileBuilder.getFile();
		Pair<Tile, Integer> tileMove = dlvController.start( file );
		moveTile( tileMove.getKey(), tileMove.getValue() );
		file.delete();
    }

        
    public void moveTile( Tile tile, int index ) {
    	isReady.set( false );
    	Row row = ( Row ) tile.getParent();
    	if( row.moveTile( tile, index ) ) {
			update();
			genRow();
			update();
    	}
    	isReady.set( true );
    }
    
    private void reset() {
    	vboNextRow.getChildren().clear();
    	vboRows.getChildren().clear();
    	lblScore.setText( "0" );
    }
    
    @FXML
    public void initialize() {
    	rows = vboRows.getChildren();
    	dlvController = new DLVController( this );
		AiCycle = new AIService( rmiRunAi, this );
    	
    	rowUp = event -> {  
    		genRow(); 
    		update(); 
    	};
    	mniRowUp.setOnAction( rowUp ); 

    	iASwitch = event -> {
    		if( !rmiRunAi.isSelected() ) return;
    		AiCycle.restart();
    	};
    	rmiRunAi.setOnAction( iASwitch );
    	
    	initBoard = event -> { while( vboRows.getChildren().size() < 4 ) { genRow(); } };
    	mniInitBoard.setOnAction( initBoard );

    }
    
    
    
    //ROW MANAGEMENT 
    
    
    public void genRow() {
    	//add row to preview vbox
		createRow();
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
    
    public void update() {
		checkFall();
	}
	
	
	/* MAIN ALGORITHM */
	// 1 - while -> check each row for falling tiles ( starting from bottom ) returns true
	// 2 - if -> check for a full row ( starting from bottom )
	// 2a true -> remove it then go to step 1
	// 2b false -> end
	public void checkFall() {
		
		int score = 0;
		
		boolean cycle = true;		
		while( cycle ) {			
			cycle = false;			
			//step 1
			while( handleFallingTiles() ) {}				
			
			//step 2
			if( handleFullRows() ) {
				cycle = true;		
				++score;
			}
		}
		addScore( score );
				
	}
	
	private boolean handleFallingTiles() {
		boolean falling = false;
		for( int i = rows.size() - 2; i >= 0; --i ) {
			Row currentRow = ( Row ) rows.get( i );
			Row nextRow = ( Row ) rows.get( i + 1 );
			for( int j = 0; j < currentRow.getChildren().size(); ++j ) {
				Tile tile = ( Tile ) currentRow.getChildren().get( j );
				if( !nextRow.collidesWithOtherTiles( tile ) ) {
					nextRow.insert( tile, false );
					currentRow.remove( tile );
					falling = true;
				}
			}
		}
		return falling;
	}
	
	private Row createRow() {
		Row row = new Row();
		row.setController( this );
		row.setParent( vboNextRow );
		TileGenerator tg = new TileGenerator();
		tg.genTiles( row );
		return row;
	}
	
	private boolean handleFullRows() {
		for( int i = rows.size() - 1; i >= 0; --i ) {
			Row currentRow = ( Row ) rows.get( i );
			if( currentRow.isFull() ) {
				rows.remove( currentRow );
				return true;
			}
		}
		return false;
	}
	
	public void removeRow( Row row ) {
		rows.remove( row );
	}
	
	public int getRowPosition( Row row ) {
		for( int i = 0; i < rows.size(); ++i )
			if( rows.get( i ).equals( row ) ) return i;
		return 0;
	}

}


