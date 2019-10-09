package enemy;

import tile.Tile;
import tile.TileGrid;

public class EnemyAlien extends Enemy{
	public EnemyAlien(Tile startTile, TileGrid grid) {
		super(EnemyType.Alien, startTile, grid);
	}
}
