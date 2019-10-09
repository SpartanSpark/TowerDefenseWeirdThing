package projectile;

import java.util.concurrent.CopyOnWriteArrayList;

import enemy.Enemy;

public class ProjectileIceBall extends Projectile{

	public ProjectileIceBall(Enemy target, CopyOnWriteArrayList<Enemy> enemies, float x, float y, int width, int height) {
		super(ProjectileType.IceBall, target, enemies, x, y, width, height);
	}

	@Override
	public void damage() {
		super.damage();
		if(!super.getTarget().getType().slowImmunity) super.getTarget().slow(4f);
	}
}
