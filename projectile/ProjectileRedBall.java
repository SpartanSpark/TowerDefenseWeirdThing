package projectile;

import java.util.concurrent.CopyOnWriteArrayList;

import enemy.Enemy;

public class ProjectileRedBall extends Projectile{

	public ProjectileRedBall(Enemy target, CopyOnWriteArrayList<Enemy> enemies, float x, float y, int width, int height) {
		super(ProjectileType.RedBall, target, enemies, x, y, width, height);
	}
}
