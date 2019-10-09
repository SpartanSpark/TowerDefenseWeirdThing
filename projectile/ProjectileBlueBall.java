package projectile;

import java.util.concurrent.CopyOnWriteArrayList;

import enemy.Enemy;

public class ProjectileBlueBall extends Projectile{
	public ProjectileBlueBall(Enemy target, CopyOnWriteArrayList<Enemy> enemies, float x, float y, int width, int height) {
		super(ProjectileType.BlueBall, target, enemies, x, y, width, height);
	}
}