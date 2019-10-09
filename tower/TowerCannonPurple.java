package tower;

import static helpers.Artist.TileWidth;

import java.util.concurrent.CopyOnWriteArrayList;

import enemy.Enemy;
import projectile.ProjectileBlueBall;
import tile.Tile;

public class TowerCannonPurple extends Tower{
	
	public TowerCannonPurple(Tile tile, CopyOnWriteArrayList<Enemy> enemies) {
		super(TowerType.CannonPurple, tile, enemies, null);
	}
	
	public TowerCannonPurple(Tile tile, CopyOnWriteArrayList<Enemy> enemies, boolean dummy) {
		super(TowerType.CannonPurple, tile, enemies, null, dummy);
	}
	
	@Override
	public void shoot(Enemy target) {
		super.projectiles.add(new ProjectileBlueBall(super.target, super.getEnemies(), super.getX() + TileWidth / 2, super.getY() + TileWidth / 2, TileWidth / 2, TileWidth / 2));
	}
}
