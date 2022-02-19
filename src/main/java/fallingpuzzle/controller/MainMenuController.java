package fallingpuzzle.controller;

import java.io.File;
import fallingpuzzle.Application;
import fallingpuzzle.controller.data.Setting;
import fallingpuzzle.controller.data.SettingsDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainMenuController extends Controller {
	
	private static Scene scene;
	
    //Components
    @FXML
    private Button btnPLAY;
    @FXML
    private Button btnDLVPATH;
    @FXML
    private Canvas cnvMenuBG;
    @FXML
    private Button btnDLVTry;
    @FXML
    private TextArea txaDVLTry;
    
    public static Scene getScene() {
    	scene = getScene("/view/MainMenu.fxml");
    	return scene;
	}
    
    
    @FXML
    public void initialize() {
    	
    	cnvMenuBG.widthProperty().bind( ( (AnchorPane) cnvMenuBG.getParent() ).widthProperty() );
    	cnvMenuBG.heightProperty().bind( ( (AnchorPane) cnvMenuBG.getParent() ).heightProperty() );
    	
    	btnDLVPATH.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	FileChooser fileChooser = new FileChooser();
            	 fileChooser.setTitle("Open Resource File");
            	 fileChooser.getExtensionFilters().addAll(
            	         new ExtensionFilter("DLV", "*.exe") );
            	 File selectedFile = fileChooser.showOpenDialog( scene.getWindow() );
            	 if( selectedFile != null ) {
            		 Setting setting = new Setting( "DLV_PATH", selectedFile.getAbsolutePath() );
            		 SettingsDAO.insert( setting );
            	 }
            }
        });
    	
    	btnPLAY.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Application.setScene( GameController.getScene() );
            }
        }); 
    	
    }
       
    
}
