package fallingpuzzle.controller.ia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import fallingpuzzle.controller.data.SettingsDAO;
import fallingpuzzle.controller.scene.GameController;
import fallingpuzzle.model.Row;
import fallingpuzzle.model.Tile;
import javafx.util.Pair;

public class DLVController {	
	
	GameController gameController;
	
	public DLVController( GameController gameController ) {
		this.gameController = gameController;
	}
	
	@SuppressWarnings("resource")
	public Pair<Tile, Integer> start( File file ) {
		Pair<Tile, Integer> tileMove = null;
		Runtime rt = Runtime.getRuntime();
		String[] commands = { SettingsDAO.getById( "DLV_PATH" ).getValue(), "--no-facts", file.getAbsolutePath() };
		Process proc;
		try {
			proc = rt.exec(commands);
	
			BufferedReader stdInput = new BufferedReader(new 
			     InputStreamReader(proc.getInputStream(), Charset.defaultCharset()));

			BufferedReader stdError = new BufferedReader(new 
			     InputStreamReader(proc.getErrorStream(), Charset.defaultCharset()));
			
			// Read the output from the command
			String s = null;
			boolean error = false;			
			while ((s = stdError.readLine()) != null) {
				
				System.out.println("dlv error: ");
			    System.out.println(s);
			    error = true;
			    FileInputStream fi = new FileInputStream( file );
			    BufferedReader br = new BufferedReader( new InputStreamReader( fi ) );
			    String k = null;
			    while( ( k = br.readLine() ) != null ) System.out.println( k );
			    break;
			    
			}
			if( !error ) tileMove = processOutput( stdInput );
		} catch (IOException e) { e.printStackTrace(); }
		
		return tileMove;
		
	}
	
	private Pair<Tile, Integer> processOutput( BufferedReader stdInput ) throws IOException {
		
		String s = null;
		
		Pair<Tile, Integer> tileMove = null;
		
		int counter = 0;
		while ( ( s = stdInput.readLine() ) != null ) {
			if( counter++ < 2 ) continue;
	//		System.out.println( s );

			if( !s.contains( "tileMove" ) ) return null;
			//tileMove( firstIndex, newIndex, row );
			s = s.strip();
			
			
			int firstIndex = 0, newIndex = 0, rowIndex = 0;
			int lastCharIndex = s.indexOf( "tileMove(" ) + 9;
			
			//get firstIndex			
			for( int i = lastCharIndex; i < s.length(); ++i ) {
				if( s.charAt( i ) == ',' || s.charAt( i ) == '}' ) {
					lastCharIndex = ++i;
					break;
				}
				String temp = "";
				temp +=	s.charAt( i );
				firstIndex *= 10;
				firstIndex += Integer.parseInt( temp );
			}
			
			//get newIndex
			for( int i = lastCharIndex; i < s.length(); ++i ) {
				if( s.charAt( i ) == ',' ) {
					lastCharIndex = ++i;
					break;
				}
				String temp = "";
				temp +=	s.charAt( i );
				newIndex *= 10;
				newIndex += Integer.parseInt( temp );
			}
			
			//get rowIndex
			{
				String temp = "";
				temp += s.charAt( lastCharIndex );
				rowIndex += Integer.parseInt( temp );
			}

			
			Row row = gameController.getRow( rowIndex );
			Tile tile = row.getTile( firstIndex );
			tileMove = new Pair<Tile, Integer>( tile, newIndex );
			return tileMove;
		}
		return tileMove;
	}
	
}
