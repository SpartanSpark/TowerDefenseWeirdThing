package tower;

import static helpers.Artist.TileWidth;
import static helpers.Artist.TileHeight;

import java.util.concurrent.CopyOnWriteArrayList;

import enemy.Enemy;
import tile.Tile;

public class TowerMine extends Tower{

	public TowerMine(Tile tile, CopyOnWriteArrayList<Enemy> enemies) {
		super(TowerType.TowerMine, tile, enemies, null);
	}

	public TowerMine(Tile tile, CopyOnWriteArrayList<Enemy> enemies, boolean dummy) {
		super(TowerType.TowerMine, tile, enemies, null, dummy);
	}

	@Override
	public void shoot(Enemy target) {
		super.projectiles.add(new projectile.ProjectileMine(super.target, super.getEnemies(), super.getX(), super.getY(), TileHeight, TileWidth, super.type.range));
	}
}
