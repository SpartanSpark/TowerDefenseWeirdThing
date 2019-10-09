package projectile;

import java.util.concurrent.CopyOnWriteArrayList;

import enemy.Enemy;

public class ProjectileGreenBall extends Projectile{
	public ProjectileGreenBall(Enemy target, CopyOnWriteArrayList<Enemy> enemies, float x, float y, int width, int height) {
		super(ProjectileType.GreenBall, target, enemies, x, y, width, height);
	}
}