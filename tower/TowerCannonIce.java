package tower;

import static helpers.Artist.TileWidth;

import java.util.concurrent.CopyOnWriteArrayList;

import enemy.Enemy;
import projectile.ProjectileIceBall;
import tile.Tile;

public class TowerCannonIce extends Tower{

	public TowerCannonIce(Tile tile, CopyOnWriteArrayList<Enemy> enemies) {
		super(TowerType.CannonIce, tile, enemies, null);
	}

	public TowerCannonIce(Tile tile, CopyOnWriteArrayList<Enemy> enemies, boolean dummy) {
		super(TowerType.CannonIce, tile, enemies, null, dummy);
	}
	
	@Override
	public void shoot(Enemy target) {
		super.projectiles.add(new ProjectileIceBall(super.target, super.getEnemies(), super.getX(), super.getY(), TileWidth / 2, TileWidth / 2));
	}

	@Override
	public boolean shouldAcquireTarget() {
		return !super.targeted || target.getHealth() <= 0 || target.speed <= 10;
	}
}
