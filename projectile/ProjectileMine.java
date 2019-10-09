package projectile;

import static helpers.Artist.CheckCollision;
import static helpers.Artist.TileHeight;
import static helpers.Artist.TileWidth;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.DrawQuadTex;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.opengl.Texture;

import enemy.Enemy;
import helpers.Clock;
import states.StateManager;

public class ProjectileMine extends Projectile{

	private int range, explosion;
	private float counter, size;
	private boolean count;
	private boolean mainAlive;
	private Texture tex;
	
	public ProjectileMine(Enemy target, CopyOnWriteArrayList<Enemy> enemies, float x, float y, int width, int height, int range) {
		super(ProjectileType.ProjectileMine, target, enemies, x, y, width, height);
		this.range = range;
		this.counter = 0;
		this.explosion = 0;
		this.size = 3;
		this.count = false;
		this.mainAlive = true;
		this.tex = super.texture;
	}

	@Override
	public void damage() {
		explode();
		counter = 0;
		for(Enemy e : super.enemies) if(CheckCollision(super.x - range, super.y - range, super.width + range, super.height + range, e.getX(), e.getY(), e.getWidth(), e.getHeight())) {
			e.damage(super.damage);
		}
		mainAlive = false;
		StateManager.game.grid.getTile((int) super.x/TileWidth, (int) super.y/TileHeight).reset();
	}
	
	@Override
	public void update() {
		if(super.alive && !mainAlive) {
			explode();
			counter += Clock.Delta() * 100;
			if(counter >= 5) {
				explosion = 1;
				size = 1;
			}
			if(counter >= 10) {
				explosion = 2;
				size = 0.5f;
			}
			if(counter >= 20) super.alive = false;
		}
		if(mainAlive) {
			boolean shouldDie = true;
			for(Enemy e : super.enemies) if(e.isAlive()) shouldDie = false;
			if(count) {
				counter += Clock.Delta() * 100;
				if(counter >= super.type.speed * 0.125) {
					tex = QuickLoad("tiles/mineOn");
				}
				if(counter >= super.type.speed * 0.25) {
					tex = QuickLoad("tiles/mine");
				}
				if(counter >= super.type.speed * 0.375) {
					tex = QuickLoad("tiles/mineOn");
				}
				if(counter >= super.type.speed * 0.5) {
					tex = QuickLoad("tiles/mine");
				}
				if(counter >= super.type.speed * 0.625) {
					tex = QuickLoad("tiles/mineOn");
				}
				if(counter >= super.type.speed * 0.75) {
					tex = QuickLoad("tiles/mine");
				}
				if(counter >= super.type.speed * 0.875) {
					tex = QuickLoad("tiles/mine");
				}
				if(counter >= super.type.speed) damage();
			}
			if(shouldDie) {
				mainAlive = false;
				super.alive = false;
				StateManager.game.grid.getTile((int) super.x/TileWidth, (int) super.y/TileHeight).reset();
			}
			for(Enemy e : super.enemies) if(e.isAlive() && CheckCollision(super.x, super.y, super.width, super.height, e.getX(), e.getY(), e.getWidth(), e.getHeight())) {
				count = true;
				break;
			}
			draw();
		}
	}
	
	public void explode() {
		DrawQuadTex(QuickLoad("explosion" + explosion), super.x - TileWidth * size, super.y - TileHeight * size, super.width + TileWidth * (size * 2 + 0.5f), super.height + TileHeight * (size * 2 + 0.5f));
	}
	

	public void draw() {
		DrawQuadTex(tex, super.x, super.y, super.width, super.height);
	}
	
	@Override
	public boolean requestNewList() {
		return super.enemies == null;
	}
}
