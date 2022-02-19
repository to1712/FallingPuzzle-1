package fallingpuzzle.controller.ia;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import fallingpuzzle.controller.data.SettingsDAO;

public class DLVController {
	
	
	public static void start( File file ) {
		
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
			while ((s = stdInput.readLine()) != null) {
			    System.out.println(s);
			}

			while ((s = stdError.readLine()) != null) {
				System.out.println("dlv error: ");
			    System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
