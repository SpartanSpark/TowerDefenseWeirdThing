package tower;

import enemy.Enemy;
import tile.Tile;
import tile.TileGrid;

public class CrateStone extends Tower{

	public CrateStone(Tile tile, TileGrid grid) {
		super(TowerType.StoneCrate, tile, null, grid);
	}

	public CrateStone(Tile tile, TileGrid grid, boolean dummy) {
		super(TowerType.StoneCrate, tile, null, grid, dummy);
	}

	@Override
	public void shoot(Enemy target) {}
	
}
