package fallingpuzzle.controller;

import fallingpuzzle.model.Row;
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
    	
    	btnRowUp.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Row.createRow( vboRows );
			}
		});
    	
    }

    
}

