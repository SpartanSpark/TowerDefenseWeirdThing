package tower;

import enemy.Enemy;
import tile.Tile;
import tile.TileGrid;

public class CrateBrick extends Tower{

	public CrateBrick(Tile tile, TileGrid grid) {
		super(TowerType.BrickCrate, tile, null, grid);
	}

	public CrateBrick(Tile tile, TileGrid grid, boolean dummy) {
		super(TowerType.BrickCrate, tile, null, grid, dummy);
	}

	@Override
	public void shoot(Enemy target) {}
	
}
