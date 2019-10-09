package projectile;

import java.util.concurrent.CopyOnWriteArrayList;

import enemy.Enemy;

public class ProjectileFirework extends Projectile{

	public ProjectileFirework(Enemy target, CopyOnWriteArrayList<Enemy> enemies, float x, float y, int width, int height) {
		super(ProjectileType.Firework, target, enemies, x, y, width, height);
	}
}
