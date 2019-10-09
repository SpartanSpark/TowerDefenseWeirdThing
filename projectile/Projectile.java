package projectile;

import static helpers.Artist.CheckCollision;
import static helpers.Artist.DrawQuadTexRot;
import static helpers.Artist.TileWidth;
import static helpers.Artist.TileHeight;
import static helpers.Clock.Delta;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.opengl.Texture;

import data.Entity;
import enemy.Enemy;
import states.StateManager;

public abstract class Projectile implements Entity{

	protected Texture texture;
	protected ProjectileType type;
	protected float x, y, speed, xVelocity, yVelocity, angle;
	protected int width;
	protected int height;
	protected int damage;
	protected Enemy target, tempEnemy;
	public CopyOnWriteArrayList<Enemy> enemies;
	protected boolean alive;

	public Projectile(ProjectileType type, Enemy target, CopyOnWriteArrayList<Enemy> enemies, float x, float y, int width, int height) {
		this.texture = type.texture;
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = type.speed;
		this.damage = type.damage;
		this.target = target;
		this.enemies = enemies;
		this.alive = true;
		this.xVelocity = 0f;
		this.yVelocity = 0f;
		this.angle = 0f;
		calculateDirection();
	}

	public void damage() {
		target.damage(damage);
		alive = false;
	}

	private void calculateDirection() {
		if(target != null && target.isAlive()) {
			float totalAllowedMovement = 1.0f;
			float xDistanceFromTarget = Math.abs(target.getX() - x - TileWidth / 4 + TileWidth / 2);
			float yDistanceFromTarget = Math.abs(target.getY() - y - TileHeight / 4 + TileHeight / 2);
			float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
			float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
			xVelocity = xPercentOfMovement;
			yVelocity = totalAllowedMovement - xPercentOfMovement;
			if (target.getX() < x) xVelocity *= -1;
			if (target.getY() < y) yVelocity *= -1;
		}
	}
	
	public void calculateAngle() {
		if(xVelocity <= 0) angle = (float) (Math.toDegrees(Math.atan(yVelocity / xVelocity))) + 90;
		else angle = (float) (Math.toDegrees(Math.atan(yVelocity / xVelocity))) - 90;
	}
	
	public void update() {
		if (alive) {
			boolean shouldDie = true;
			for(Enemy e : enemies) if(e.isAlive()) shouldDie = false;
			if(shouldDie) {
				alive = false;
				StateManager.game.grid.getTile((int) x/TileWidth, (int) y/TileHeight).reset();
			}
			calculateDirection();
			x += xVelocity * speed * Delta();
			y += yVelocity * speed * Delta();
			for(Enemy e : enemies) if(e.isAlive() && CheckCollision(x, y, width, height, e.getX(), e.getY(), e.getWidth(), e.getHeight())) {
				tempEnemy = target;
				target = e;
				damage();
				target = tempEnemy;
			}
			draw();
		}
	}

	public void draw() {
		if(type.turn) calculateAngle();
		DrawQuadTexRot(texture, x, y, width, height, angle);
	}

	public boolean requestNewList() {
		return false;
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
	
	public void setAlive(boolean status) {
		alive = status;
	}
}
