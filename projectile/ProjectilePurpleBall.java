package projectile;

import java.util.concurrent.CopyOnWriteArrayList;

import enemy.Enemy;

public class ProjectilePurpleBall extends Projectile{
	public ProjectilePurpleBall(Enemy target, CopyOnWriteArrayList<Enemy> enemies, float x, float y, int width, int height) {
		super(ProjectileType.PurpleBall, target, enemies, x, y, width, height);
	}
}