package fallingpuzzle.controller;

import fallingpuzzle.model.Tile;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GameController extends Controller {

    @FXML
    private AnchorPane achBoard;

    @FXML
    private Label lblGameTitle;

    @FXML
    private Canvas cnvGameBG;

    @FXML
    private Button btnRowUp;

    
    public static Scene getScene() {
    	return getScene( "/view/Game.fxml" );
    }
    
    @FXML
    public void initialize() { 
    	Tile tile1 = new Tile( 0, 0, 64, 64 );
    	Tile tile2 = new Tile( 80, 0, 128, 64 );
    	achBoard.getChildren().add( tile1 );
    	achBoard.getChildren().add( tile2 );

    }

    
}

