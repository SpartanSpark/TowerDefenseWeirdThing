package enemy;

import tile.Tile;
import tile.TileGrid;

public class EnemyEnt extends Enemy{
	public EnemyEnt(Tile startTile, TileGrid grid) {
		super(EnemyType.Ent, startTile, grid);
	}
}
