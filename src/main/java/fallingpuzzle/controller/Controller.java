package fallingpuzzle.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public abstract class Controller {
	
	protected static Parent root;
		
    //Creates a new scene from a FXML file
    protected static Scene getScene( String path ) {
    	return load( path );
    }
	
	private static Scene load( String path ) {
		FXMLLoader loader = new FXMLLoader( Controller.class.getResource( path ) );
		Scene scene = null;
		try {
			root = loader.load();
			scene = new Scene( root );
		}
		catch (IOException e) {
			e.printStackTrace();
			System.err.println("Can't figure out controller's path in FXML file.");
		}
		return scene;
	}

}
