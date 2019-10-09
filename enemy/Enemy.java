package enemy;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

import data.Entity;
import data.Player;
import tile.Tile;
import tile.TileGrid;

import static helpers.Artist.*;
import static helpers.Clock.*;

public class Enemy implements Entity {
	protected int width, height, currentCheckpoint;
	public float speed;
	protected float x;
	protected float y;
	protected float xVelocity;
	protected float yVelocity;
	protected float health;
	protected float startHealth;
	protected float angle;
	protected Texture texture, healthBackground, healthForeground, healthBorder;
	protected Tile startTile;
	protected boolean first = true, alive = true;
	public projectile.ProjectileSpikes spiked;
	protected TileGrid grid;
	protected EnemyType type;
	
	protected ArrayList<Checkpoint> checkpoints;
	protected int[] directions;
	//Subsidiary constructor
	public Enemy(Enemy enemy) {
		this.type = enemy.type;
		this.texture = enemy.type.texture;
		this.healthBackground = QuickLoad("enemies/healthBackground");
		this.healthForeground = QuickLoad("enemies/healthForeground");
		this.healthBorder = QuickLoad("enemies/healthBorder");
		this.startTile = enemy.startTile;
		this.x = enemy.startTile.getX();
		this.y = enemy.startTile.getY();
		this.xVelocity = 0f;
		this.yVelocity = 0f;
		this.width = TileWidth;
		this.height = TileHeight;
		this.speed = enemy.type.speed;
		this.health = enemy.type.health;
		this.startHealth = enemy.type.health;
		this.grid = enemy.grid;
		this.checkpoints = new ArrayList<Checkpoint>();
		this.directions = new int[2];
		//X direction
		this.directions[0] = 0;
		//Y direction
		this.directions[1] = 0;
		directions = findNextD(startTile);
		this.currentCheckpoint = 0;
		populateCheckpointList();
		this.angle = 0;
		spiked = null;
	}
	//Default constructor
	public Enemy(EnemyType type, Tile startTile, TileGrid grid) {
		this.type = type;
		this.texture = type.texture;
		this.healthBackground = QuickLoad("enemies/healthBackground");
		this.healthForeground = QuickLoad("enemies/healthForeground");
		this.healthBorder = QuickLoad("enemies/healthBorder");
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = TileWidth;
		this.height = TileHeight;
		this.speed = type.speed;
		this.health = type.health;
		this.startHealth = type.health;
		this.grid = grid;
		this.checkpoints = new ArrayList<Checkpoint>();
		this.directions = new int[2];
		//X direction
		this.directions[0] = 0;
		//Y direction
		this.directions[1] = 0;
		directions = findNextD(startTile);
		this.currentCheckpoint = 0;
		populateCheckpointList();
		this.angle = 0;
		spiked = null;
	}
	//Generic constructor
	public Enemy(Texture texture, Tile startTile, TileGrid grid, int width, int height, int speed, float health) {
		this.texture = texture;
		this.healthBackground = QuickLoad("enemies/healthBackground");
		this.healthForeground = QuickLoad("enemies/healthForeground");
		this.healthBorder = QuickLoad("enemies/healthBorder");
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.health = health;
		this.startHealth = health;
		this.grid = grid;
		this.checkpoints = new ArrayList<Checkpoint>();
		this.directions = new int[2];
		//X direction
		this.directions[0] = 0;
		//Y direction
		this.directions[1] = 0;
		directions = findNextD(startTile);
		this.currentCheckpoint = 0;
		populateCheckpointList();
		this.angle = 0;
		spiked = null;
	}
	
	public float getStartHealth() {
		return startHealth;
	}

	public void setStartHealth(float startHealth) {
		this.startHealth = startHealth;
	}

	public Enemy(Texture texture, Tile startTile, TileGrid grid, int width, int height, float speed, float health) {
		this.texture = texture;
		this.healthBackground = QuickLoad("enemies/healthBackground");
		this.healthForeground = QuickLoad("enemies/healthForeground");
		this.healthBorder = QuickLoad("enemies/healthBorder");
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.health = health;
		this.startHealth = health;
		this.grid = grid;
		
		this.checkpoints = new ArrayList<Checkpoint>();
		this.directions = new int[2];
		//X direction
		this.directions[0] = 0;
		//Y direction
		this.directions[1] = 0;
		directions = findNextD(startTile);
		this.currentCheckpoint = 0;
		populateCheckpointList();
	}

	public void changeAngle() {
		if(type.turn && type.obeyPath) {
			int x = checkpoints.get(currentCheckpoint).getxDirection();
			int y = checkpoints.get(currentCheckpoint).getyDirection();
			if(x == 1 && y == 0) angle = 0;
			if(x == -1 && y == 0) angle = 180;
			if(x == 0 && y == 1) angle = 90;
			if(x == 0 && y == -1) angle = 270;
		}
	}
	
	public void extra() {}
	
	public void update() {
		extra();
		away();
		if (first)
			first = false;
		else {
			if(type.obeyPath) {
				if (checkpointReached()) {
					if (currentCheckpoint + 1 == checkpoints.size())
						endOfMazeReached();
					else
						currentCheckpoint++;
				} else {
					x += Delta() * checkpoints.get(currentCheckpoint).getxDirection() * speed;
					y += Delta() * checkpoints.get(currentCheckpoint).getyDirection() * speed;
				}
			} else {
				if(CheckCollision(x, y, width, height, checkpoints.get(checkpoints.size() - 1).getTile().getX(), checkpoints.get(checkpoints.size() - 1).getTile().getY(), TileWidth,TileHeight)) endOfMazeReached();
				calculateDirection();
				x += xVelocity * speed * Delta();
				y += yVelocity * speed * Delta();
			}
		}
	}

	protected void calculateDirection() {
		float totalAllowedMovement = 1.0f;
		float xDistanceFromTarget = Math.abs(checkpoints.get(checkpoints.size() - 1).getTile().getX() - x - TileWidth / 4 + TileWidth / 2);
		float yDistanceFromTarget = Math.abs(checkpoints.get(checkpoints.size() - 1).getTile().getY() - y - TileHeight / 4 + TileHeight / 2);
		float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
		float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
		xVelocity = xPercentOfMovement;
		yVelocity = totalAllowedMovement - xPercentOfMovement;
		if (checkpoints.get(checkpoints.size() - 1).getTile().getX() < x) xVelocity *= -1;
		if (checkpoints.get(checkpoints.size() - 1).getTile().getY() < y) yVelocity *= -1;
	}
	
	public void calculateAngle() {
		if(xVelocity <= 0) angle = (float) (Math.toDegrees(Math.atan(yVelocity / xVelocity)));
		else angle = (float) (Math.toDegrees(Math.atan(yVelocity / xVelocity)));
	}
	
	protected void endOfMazeReached() {
		Player.modifyLives(-1);
		die();
	}
	
	protected boolean checkpointReached() {
		boolean reached = false;
		Tile t = checkpoints.get(currentCheckpoint).getTile();
		//Check if position reached tile within variance of 3 (arbitrary)
		if (x > t.getX() - 3 && x < t.getX() + 3 &&	y > t.getY() - 3 && y < t.getY() + 3) {
			reached = true;
			x = t.getX();
			y = t.getY();
		}
		return reached;
	}

	protected void populateCheckpointList() {
		checkpoints.add(findNextC(startTile, directions = findNextD(startTile)));
		int counter = 0;
		boolean cont = true;
		while(cont) {
			int[] currentD = findNextD(checkpoints.get(counter).getTile());
			//Check if next direction/checkpoint exists, end after 20 checkpoints (arbitrary)
			if (currentD[0] == 2 || counter == 20) {
				cont = false;
			} else {
				checkpoints.add(findNextC(checkpoints.get(counter).getTile(),
						directions = findNextD(checkpoints.get(counter).getTile())));
			}
			counter++;
		}
	}
	protected Checkpoint findNextC(Tile s, int[] dir) {
		Tile next = null;
		Checkpoint c = null;
		//Boolean to decide if next checkpoint is found
		boolean found = false;
		//Integer to increment each loop
		int counter = 1;
		while (!found) {
			if(s.getXPlace() + dir[0] * counter == grid.getTilesWide() ||
					s.getYPlace() + dir[1] * counter == grid.getTilesHigh() || !walkable(grid.getTile(s.getXPlace() + dir[0] * counter,
					s.getYPlace() + dir[1] * counter))) {
				found = true;
				//Move counter back 1 to find tile before new tile type
				counter -= 1;
				next = grid.getTile(s.getXPlace() + dir[0] * counter,
						s.getYPlace() + dir[1] * counter);
			}
			counter++;
		}
		c = new Checkpoint(next, dir[0], dir[1]);
		return c;
	}
	
	protected boolean walkable(Tile t) {
		return t.getType().path;
	}
	
	protected int[] findNextD(Tile s) {
		int[] dir = new int[2];
		Tile u = grid.getTile(s.getXPlace(), s.getYPlace() - 1);
		Tile uu = grid.getTile(s.getXPlace(), s.getYPlace() - 2);
		Tile uuu = grid.getTile(s.getXPlace(), s.getYPlace() - 3);
		Tile r = grid.getTile(s.getXPlace() + 1, s.getYPlace());
		Tile rr = grid.getTile(s.getXPlace() + 2, s.getYPlace());
		Tile rrr = grid.getTile(s.getXPlace() + 3, s.getYPlace());
		Tile d = grid.getTile(s.getXPlace(), s.getYPlace() + 1);
		Tile dd = grid.getTile(s.getXPlace(), s.getYPlace() + 2);
		Tile ddd = grid.getTile(s.getXPlace(), s.getYPlace() + 3);
		Tile l = grid.getTile(s.getXPlace() - 1, s.getYPlace());
		Tile ll = grid.getTile(s.getXPlace() - 2, s.getYPlace());
		Tile lll = grid.getTile(s.getXPlace() - 3, s.getYPlace());
		if(!walkable(s)) {
			if(walkable(u)) {
				dir[0] = 0;
				dir[1] = -1;
			} else if(walkable(r)) {
				dir[0] = 1;
				dir[1] = 0;
			} else if(walkable(d)) {
				dir[0] = 0;
				dir[1] = 1;
			} else if(walkable(l)) {
				dir[0] = -1;
				dir[1] = 0;
			} else if(walkable(uu)) {
				dir[0] = 0;
				dir[1] = -2;
			} else if(walkable(rr)) {
				dir[0] = 2;
				dir[1] = 0;
			} else if(walkable(dd)) {
				dir[0] = 0;
				dir[1] = 2;
			} else if(walkable(ll)) {
				dir[0] = -2;
				dir[1] = 0;
			} else if(walkable(uuu)) {
				dir[0] = 0;
				dir[1] = -3;
			} else if(walkable(rrr)) {
				dir[0] = 3;
				dir[1] = 0;
			} else if(walkable(ddd)) {
				dir[0] = 0;
				dir[1] = 3;
			} else if(walkable(lll)) {
				dir[0] = -3;
				dir[1] = 0;
			}
		} else if (walkable(u) && directions[1] != 1) {
			dir[0] = 0;
			dir[1] = -1;
		} else if (walkable(r) && directions[0] != -1) {
			dir[0] = 1;
			dir[1] = 0;
		} else if (walkable(d) && directions[1] != -1) {
			dir[0] = 0;
			dir[1] = 1;
		} else if (walkable(l) && directions[0] != 1) {
			dir[0] = -1;
			dir[1] = 0;
		} else {
			dir[0] = 2;
			dir[1] = 2;
		}
		return dir;
	}
	
	public void damage(int amount)  {
		if (health <= 0) die();
		else {
			health -= amount;
			if (health <= 0) {
				die();
				if(type != null) Player.modifyCash(type.prize);
			}
		}
	}
	
	protected void away() {
		if(x > 20 * TileWidth || x < 0 * TileWidth || y > 15 * TileHeight || y < 0 * TileHeight) die();
	}
	
	public void die() {
		alive = false;
	}
	
	public void draw() {
		float healthPercentage = health / startHealth;
		changeAngle();
		if(type.turn && !type.obeyPath) calculateAngle();
		DrawQuadTexRot(texture, x, y, width, height, angle);
		DrawQuadTex(healthBackground, x + TileWidth / 16, y - TileWidth / 4, width * 7 / 8, height / 8);
		DrawQuadTex(healthForeground, x + TileWidth / 16, y - TileWidth / 4, width * healthPercentage * 7 / 8, height / 8);
		DrawQuadTex(healthBorder, x + TileWidth / 16, y - TileWidth / 4, width * 7 / 8, height / 8);
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void addSpeed(float speed) {
		this.speed += speed;
	}
	
	public void slow(float speed) {
		this.speed /= speed;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Tile getStartTile() {
		return startTile;
	}

	public void setStartTile(Tile startTile) {
		this.startTile = startTile;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}
	
	public TileGrid getTileGrid() {
		return grid;
	}

	public boolean isAlive() {
		return alive;
	}
	
	public int getXPlace() {
		int xPlace = (int) (x / 64);
		return xPlace;
	}
	
	public int getYPlace() {
		int yPlace = (int) (y / 64);
		return yPlace;
	}
	
	public EnemyType getType() {
		return type;
	}
	
	public void setTexture(String name) {
		this.texture = QuickLoad(name);
	}
	
	public void reload() {
		checkpoints.clear();
		populateCheckpointList();
	}
}