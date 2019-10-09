package tower;

import static helpers.Artist.TileWidth;
import java.util.concurrent.CopyOnWriteArrayList;

import enemy.Enemy;
import projectile.ProjectileRedBall;
import tile.Tile;

public class TowerCannonRed extends Tower{

	public TowerCannonRed(Tile tile, CopyOnWriteArrayList<Enemy> enemies) {
		super(TowerType.CannonRed, tile, enemies, null);
	}

	public TowerCannonRed(Tile tile, CopyOnWriteArrayList<Enemy> enemies, boolean dummy) {
		super(TowerType.CannonRed, tile, enemies, null, dummy);
	}
	
	@Override
	public void shoot(Enemy target) {
		super.projectiles.add(new ProjectileRedBall(super.target, super.getEnemies(), super.getX(), super.getY(), TileWidth / 2, TileWidth / 2));
	}
}
