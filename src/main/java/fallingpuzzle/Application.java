package fallingpuzzle;

import fallingpuzzle.controller.MainMenuController;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
	
	private static Stage primaryStage;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start( Stage primaryStage ) throws Exception {
		Application.primaryStage = primaryStage;
		primaryStage.setScene( MainMenuController.getScene() );
		primaryStage.setTitle( "Falling Puzzle" );
		primaryStage.setWidth( 600 );
		primaryStage.setHeight( 800 );
		primaryStage.setResizable( false );
		primaryStage.show();
	}
	
	public static void setScene( Scene scene ) {
		if( primaryStage == null ) return;
		primaryStage.setScene( scene );
	}

}
