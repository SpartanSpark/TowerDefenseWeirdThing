package tower;

import static helpers.Artist.TileWidth;
import static helpers.Artist.TileHeight;

import java.util.concurrent.CopyOnWriteArrayList;

import enemy.Enemy;
import tile.Tile;

public class TowerSpikes extends Tower{

	public TowerSpikes(Tile tile, CopyOnWriteArrayList<Enemy> enemies) {
		super(TowerType.Spikes, tile, enemies, null);
	}

	public TowerSpikes(Tile tile, CopyOnWriteArrayList<Enemy> enemies, boolean dummy) {
		super(TowerType.Spikes, tile, enemies, null, dummy);
	}

	@Override
	public void shoot(Enemy target) {
		super.projectiles.add(new projectile.ProjectileSpikes(super.target, super.getEnemies(), super.getX(), super.getY(), TileHeight, TileWidth));
	}
}
