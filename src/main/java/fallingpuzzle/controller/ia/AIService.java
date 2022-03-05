package fallingpuzzle.controller.ia;

import fallingpuzzle.controller.scene.GameController;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.RadioMenuItem;

public class AIService extends Service<Void> {
	
	RadioMenuItem rmiRunAi;
	GameController gameController;
	
	public AIService( RadioMenuItem rmiRunAi, GameController gameController ) {
		this.rmiRunAi = rmiRunAi;
		this.gameController = gameController;
	}
	
	@Override
	protected Task<Void> createTask() {
		
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				System.out.println("AI RUNNING:");
				while( rmiRunAi.isSelected() ) {
					if( !gameController.isReady() ) continue;
				Platform.runLater( () -> { gameController.genDLVFile(); } );
					Thread.sleep( 1000 );
				}
				System.out.println("AI STOP");
				return null;
			}
		};
		
	}

}
