package fallingpuzzle.controller.ia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import fallingpuzzle.model.Row;
import fallingpuzzle.model.Tile;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class DLVFileBuilder {
	
	private File file;
	
	public File getFile() {
		return file;
	}
	
	public void createFile( ObservableList<Node> rows ) {
		try {
			file = File.createTempFile( "file", null );
			@SuppressWarnings("resource")
			OutputStreamWriter ow = new OutputStreamWriter( new FileOutputStream( file ) );
			ow.write( createProgram( rows ) );
			ow.flush();
		}
		catch( IOException e ) {
			
		}
	}
	
	public String createProgram( ObservableList<Node> rows ) {
		StringBuilder sb = new StringBuilder();
		
		//FACTS
		for( int i = 0; i < rows.size(); ++i ) {
			Row row = ( Row ) rows.get( i );
			sb.append( convertRowIntoRules( row, i ) );
		}
		
		//RULES
		sb.append( createTileSizeRule() );
		sb.append( createTileFallRule() );

		
		return sb.toString();
	}
	
	//Maps tile indexes ( first index, every index, row ) GENS FACTS
	private String convertRowIntoRules( Row row, int rowIndex ) {
		StringBuilder sb = new StringBuilder();
		for( int i = 0; i < row.getChildren().size(); ++i ) {
			Tile tile = ( Tile ) row.getChildren().get( i );
			for( int j = 0; j < tile.getIndexes().size(); ++j ) {
				sb.append( "\ntile(" + tile.getFirstIndex() + ", " + tile.getIndexes().get( j ) + ", " + rowIndex + ")." );
			}
		}		
		return sb.toString();
	}
	
	//Create tileSize rule
	private String createTileSizeRule() {
		return "\n" + "tileSize( X, Size, R ):- tile( X, Y, R ), #count{ X, I : tile( X, I, R ) } = Size.";
	}
	
	//Create tailFall rule <-- TODO
	private String createTileFallRule() {
		return "\n" + "";
	}
	
}
