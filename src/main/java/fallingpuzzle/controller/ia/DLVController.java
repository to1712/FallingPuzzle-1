package fallingpuzzle.controller.ia;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import fallingpuzzle.controller.data.SettingsDAO;
import fallingpuzzle.controller.scene.GameController;

public class DLVController {	
	
	GameController gameController;
	
	public DLVController( GameController gameController ) {
		this.gameController = gameController;
	}
	
	public void start( File file ) {
		
		Runtime rt = Runtime.getRuntime();
		String[] commands = { SettingsDAO.getById( "DLV_PATH" ).getValue(), file.getAbsolutePath() };
		Process proc;
		try {
			proc = rt.exec(commands);
	
			BufferedReader stdInput = new BufferedReader(new 
			     InputStreamReader(proc.getInputStream(), Charset.defaultCharset()));

			BufferedReader stdError = new BufferedReader(new 
			     InputStreamReader(proc.getErrorStream(), Charset.defaultCharset()));
			
			// Read the output from the command
			String s = null;
			processOutput( stdInput );

			while ((s = stdError.readLine()) != null) {
				System.out.println("dlv error: ");
			    System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void processOutput( BufferedReader stdInput ) throws IOException {
		String s = null;
		int counter = 0;
		while ( ( s = stdInput.readLine() ) != null ) {
			if( counter++ < 2 ) continue;
		    System.out.println(s);
		}
	}
	
}
