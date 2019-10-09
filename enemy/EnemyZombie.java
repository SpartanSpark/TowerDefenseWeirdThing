package enemy;

import tile.Tile;
import tile.TileGrid;

public class EnemyZombie extends Enemy{
	
	public EnemyZombie(Tile startTile, TileGrid grid) {
		super(EnemyType.Zombie, startTile, grid);
	}
	
	@Override
	public void extra() {
		if (super.health < super.type.health) super.health += 0.02;
	}
}
