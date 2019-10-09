package tower;

import org.newdawn.slick.opengl.Texture;

import data.Entity;
import enemy.Enemy;
import projectile.Projectile;
import tile.Tile;
import tile.TileGrid;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexRot;
import static helpers.Artist.TileHeight;
import static helpers.Artist.TileWidth;
import static helpers.Clock.Delta;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Tower implements Entity{

	private float x, y, timeSinceLastShot, firingSpeed, angle;
	private int width, height, range, cost;
	public Enemy target;
	private Texture[] textures;
	private CopyOnWriteArrayList<Enemy> enemies;
	public boolean targeted;
	public CopyOnWriteArrayList<Projectile> projectiles;
	public TowerType type;
	public boolean alive, dummy;
	
	public Tower(TowerType type, Tile tile, CopyOnWriteArrayList<Enemy> enemies, TileGrid grid, boolean dummy) {
		this.type = type;
		this.textures = type.textures;
		this.range = type.range;
		this.cost = type.cost;
		this.x = tile.getX();
		this.y = tile.getY();
		this.width = tile.getWidth();
		this.height = tile.getHeight();
		this.enemies = enemies;
		this.targeted = false;
		this.timeSinceLastShot = 0f;
		this.projectiles = new CopyOnWriteArrayList<Projectile>();
		this.firingSpeed = type.firingSpeed;
		this.angle = 0f;
		this.alive = true;
		this.dummy = dummy;
		if(type.tileify && !dummy && alive) {
			die();
			grid.setTile((int) x/TileWidth, (int) y/TileHeight, type.tileType);
			grid.getTile((int) x/TileWidth, (int) y/TileHeight).reset(); 
		}
		if(type.projectileify && !dummy && alive) {
			shoot(null);
			die();
		}
	}
	
	public Tower(TowerType type, Tile tile, CopyOnWriteArrayList<Enemy> enemies, TileGrid grid) {
		this.type = type;
		this.textures = type.textures;
		this.range = type.range;
		this.cost = type.cost;
		this.x = tile.getX();
		this.y = tile.getY();
		this.width = tile.getWidth();
		this.height = tile.getHeight();
		this.enemies = enemies;
		this.targeted = false;
		this.timeSinceLastShot = 0f;
		this.projectiles = new CopyOnWriteArrayList<Projectile>();
		this.firingSpeed = type.firingSpeed;
		this.angle = 0f;
		this.alive = true;
		this.dummy = false;
		if(type.tileify && !dummy && alive) {
			die();
			grid.setTile((int) x/TileWidth, (int) y/TileHeight, type.tileType);
			grid.getTile((int) x/TileWidth, (int) y/TileHeight).reset(); 
		}
		if(type.projectileify && !dummy && alive) {
			shoot(null);
			die();
		}
	}

	private Enemy acquireTarget() {
		Enemy closest = null;
		float closestDistance = 10000;
		for(Enemy e: enemies) {
			if(isInRange(e) && findDistance(e) < closestDistance && e.getHealth() > 0) {
				closestDistance = findDistance(e);
				closest = e;
			}
		}
		if (closest != null) {
			targeted = true;
		}
		return closest;
	}
	
	private boolean isInRange(Enemy e) {
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		if(xDistance <= range && yDistance <= range) return true;
		return false;
	}
	
	private boolean isInFarRange(Enemy e) {
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		if(xDistance <= range + 2 && yDistance <= range + 2) return true;
		return false;
	}
	
	private void updateTarget() {
		if(!isInFarRange(target) || !target.isAlive()) acquireTarget();
	}
	
	private float findDistance(Enemy e) {
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		return xDistance + yDistance;
	}
	
	private float calculateAngle() {
		int offset = 0;
		if(!type.tracking && target.getType().speed > 256) offset = 0 - (TileWidth / 2);
		double angleTemp = Math.atan2(target.getY() - y, target.getX() - x + offset);
		return (float) Math.toDegrees(angleTemp) - 90;
	}
	
	public abstract void shoot(Enemy target);
	
	public void updateEnemyList(CopyOnWriteArrayList<Enemy> newList) {
		enemies = newList;
	}
	
	public void update() {
		if(target == null || target.isAlive() == false) targeted = false;
		if(shouldAcquireTarget()) target = acquireTarget();
		else {
			updateTarget();
			if(type.tracking || !(target.getType().speed > 256)) angle = calculateAngle();
			if (timeSinceLastShot > firingSpeed) {
				if(!dummy) shoot(target);
				timeSinceLastShot = 0;
			}
		}
		if(!dummy) timeSinceLastShot += Delta();
	}

	public boolean shouldAcquireTarget() {
		return !targeted || target.getHealth() <= 0;
	}
	
	public void pUpdate() {
		for(Projectile p: projectiles) {
			p.update();
			if(p.requestNewList()) p.enemies = enemies;
		}
	}
	
	public void draw() {
		DrawQuadTex(textures[0], x, y, width, height);
		if (textures.length > 1) for (int i = 1; i < textures.length; i++) DrawQuadTexRot(textures[i], x, y, width, height, angle);
	}
	
	public void die() {
		alive = false;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Enemy getTarget() {
		return target;
	}

	public int getCost() {
		return cost;
	}

	public TowerType getType() {
		return type;
	}
	
	public CopyOnWriteArrayList<Enemy> getEnemies() {
		return enemies;
	}
	
	public Tower getWithoutDummy() {
		dummy = false;
		return this;
	}
	
	public Tower getAndReInit(TileGrid grid) {
		this.timeSinceLastShot = 0f;
		this.projectiles = new CopyOnWriteArrayList<Projectile>();
		this.angle = 0f;
		this.alive = true;
		this.dummy = false;
		if(type.tileify && !dummy && alive) {
			die();
			grid.setTile((int) x/TileWidth, (int) y/TileHeight, type.tileType);
			grid.getTile((int) x/TileWidth, (int) y/TileHeight).reset(); 
		}
		if(type.projectileify && !dummy && alive) {
			shoot(null);
			die();
		}
		return this;
	}
}
