package projectile;

import static helpers.Artist.CheckCollision;
import static helpers.Artist.TileHeight;
import static helpers.Artist.TileWidth;

import java.util.concurrent.CopyOnWriteArrayList;

import enemy.Enemy;
import states.StateManager;

public class ProjectileSpikes extends Projectile{

	private int targets;
	private boolean shouldDie;
	
	public ProjectileSpikes(Enemy target, CopyOnWriteArrayList<Enemy> enemies, float x, float y, int width, int height) {
		super(ProjectileType.Spikes, target, enemies, x, y, width, height);
		this.targets = 0;
		this.shouldDie = false;
	}

	@Override
	public void damage() {
		if(target.spiked != this) {
			super.target.damage(super.damage);
			target.spiked = this;
			targets++;
		}
	}
	
	@Override
	public void update() {
		if(super.alive) {
			if(targets >= 5) shouldDie = true;
			if(shouldDie) {
				super.alive = false;
				StateManager.game.grid.getTile((int) super.x/TileWidth, (int) super.y/TileHeight).reset();
			}
			for(Enemy e : super.enemies) if(e.isAlive() && CheckCollision(super.x, super.y, super.width, super.height, e.getX(), e.getY(), e.getWidth(), e.getHeight())) {
				target = e;
				damage();
				target = null;
			}
			draw();
		}
	}
	
	@Override
	public boolean requestNewList() {
		for(Enemy e : super.enemies) if(e.isAlive()) return false;
		return true;
	}
}
