package tower;

import enemy.Enemy;
import tile.Tile;
import tile.TileGrid;

public class CrateDirt extends Tower{

	public CrateDirt(Tile tile, TileGrid grid) {
		super(TowerType.DirtCrate, tile, null, grid);
	}

	public CrateDirt(Tile tile, TileGrid grid, boolean dummy) {
		super(TowerType.DirtCrate, tile, null, grid, dummy);
	}

	@Override
	public void shoot(Enemy target) {}
	
}
