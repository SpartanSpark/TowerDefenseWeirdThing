package enemy;

import tile.Tile;
import tile.TileGrid;

public class EnemyTank extends Enemy{
	public EnemyTank(Tile startTile, TileGrid grid) {
		super(EnemyType.Tank, startTile, grid);
	}
}