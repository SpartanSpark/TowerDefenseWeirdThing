package tile;

import static helpers.Artist.TileWidth;
import static helpers.Artist.TileHeight;
import static helpers.Artist.Height;

import org.lwjgl.input.Mouse;

public class TileGrid {

	public static Tile[][] map;
	private static int tilesWide;
	private static int tilesHigh;
	
	@SuppressWarnings("static-access")
	public TileGrid() {
		this.tilesWide = 20;
		this.tilesHigh = 15;
		map = new Tile[20][15];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new Tile(i * TileWidth, j * TileWidth, TileWidth, TileWidth, TileType.NULL);
			}
		}
	}
	
	public void setTile(int xCoord, int yCoord, TileType type) {
		map[xCoord][yCoord] = new Tile(xCoord * TileWidth, yCoord * TileWidth, TileWidth, TileWidth, type);
	}
	
	public Tile getTile(int xPlace, int yPlace) {
		if(xPlace < tilesWide && yPlace < tilesHigh && xPlace > -1 && yPlace > -1)
			return map[xPlace][yPlace];
		else
			return new Tile(0, 0, 0, 0, TileType.NULL);
	}
	
	public void draw() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j].draw();
			}
		}
	}

	public int getTilesWide() {
		return tilesWide;
	}

	@SuppressWarnings("static-access")
	public void setTilesWide(int tilesWide) {
		this.tilesWide = tilesWide;
	}

	public int getTilesHigh() {
		return tilesHigh;
	}

	@SuppressWarnings("static-access")
	public void setTilesHigh(int tilesHigh) {
		this.tilesHigh = tilesHigh;
	}
	
	public static Tile MouseTile() {
		int xPlace = Mouse.getX() / TileWidth;
		int yPlace = (Height - Mouse.getY()) / TileHeight;
		if(xPlace < tilesWide && yPlace < tilesHigh && xPlace > -1 && yPlace > -1)
			return map[xPlace][yPlace];
		else
			return new Tile(0, 0, 0, 0, TileType.NULL);
	}
	
	public void reset() {
		for(Tile[] a: map) for(Tile t: a) t.reset();
	}
}
