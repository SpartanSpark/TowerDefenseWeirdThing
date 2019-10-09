package enemy;

import tile.Tile;
import tile.TileGrid;

public class EnemySuperTank extends Enemy{
	public EnemySuperTank(Tile startTile, TileGrid grid) {
		super(EnemyType.SuperTank, startTile, grid);
	}
}