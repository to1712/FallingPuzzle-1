package fallingpuzzle.model.utils;

import fallingpuzzle.model.Tile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class IndexChangeListener implements ChangeListener<Number> {
	
	private Tile tile;
	
	public IndexChangeListener( Tile tile ) {
		this.tile = tile;
	}
	
	@Override
	public void changed( ObservableValue<? extends Number> observable, Number oldValue, Number newValue ) {
		tile.updateIndexes( newValue );
		if( tile.getRow() != null )
			tile.getRow().updateTilesCoords();
	}

}
