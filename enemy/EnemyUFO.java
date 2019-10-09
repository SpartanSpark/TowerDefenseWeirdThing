package enemy;

import tile.Tile;
import tile.TileGrid;

public class EnemyUFO extends Enemy{
	public EnemyUFO(Tile startTile, TileGrid grid) {
		super(EnemyType.UFO, startTile, grid);
	}
}
