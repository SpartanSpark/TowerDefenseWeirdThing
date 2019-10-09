package enemy;

import tile.Tile;
import tile.TileGrid;

public class EnemyPlane extends Enemy{
	public EnemyPlane(Tile startTile, TileGrid grid) {
		super(EnemyType.Plane, startTile, grid);
	}
}
