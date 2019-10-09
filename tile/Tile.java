package tile;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.TileWidth;

import org.newdawn.slick.opengl.Texture;

import tower.TowerType;

public class Tile {

	private float x, y;
	private int width, height;
	private Texture texture;
	private TileType type;
	private boolean occupied;
	private TowerType occupiedType;
	
	public Tile(float x, float y, int width, int height, TileType type) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
		this.texture = QuickLoad(type.textureName);
		this.occupiedType = null;
		occupied = false;
	}
	
	public void draw() {
		DrawQuadTex(texture, x, y, width, height);
	}

	public int getXPlace() {
		return (int) x / TileWidth;
	}
	
	public float getX() {
		return x;
	}

	public int getYPlace() {
		return (int) y / TileWidth;
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

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}
	
	public boolean getOccupied() {
		return occupied;
	}
	
	public TowerType getOccupiedType() {
		return occupiedType;
	}
	
	public void occupy(TowerType type) {
		occupied = true;
		occupiedType = type;
	}
	
	public void reset() {
		this.occupiedType = null;
		this.occupied = false;
	}
}
