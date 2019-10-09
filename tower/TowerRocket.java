package tower;

import static helpers.Artist.TileWidth;

import java.util.concurrent.CopyOnWriteArrayList;

import enemy.Enemy;
import projectile.ProjectileFirework;
import tile.Tile;

public class TowerRocket extends Tower{

	public TowerRocket(Tile tile, CopyOnWriteArrayList<Enemy> enemies) {
		super(TowerType.Rocket, tile, enemies, null);
	}

	public TowerRocket(Tile tile, CopyOnWriteArrayList<Enemy> enemies, boolean dummy) {
		super(TowerType.Rocket, tile, enemies, null, dummy);
	}

	@Override
	public void shoot(Enemy target) {
		super.projectiles.add(new ProjectileFirework(super.target, super.getEnemies(), super.getX(), super.getY(), TileWidth / 2, TileWidth / 2));
	}

}
