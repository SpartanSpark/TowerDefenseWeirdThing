package data;

import static tile.TileGrid.MouseTile;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import enemy.WaveManager;
import tile.Tile;
import tile.TileGrid;
import tower.Tower;

import static helpers.Artist.TileWidth;
import static helpers.Artist.TileHeight;

public class Player {

	private WaveManager waveManager;
	private ArrayList<Tower> towerList;
	public static int Cash, Lives;
	public static boolean Invincible;
	private boolean leftMouseButtonDown, holdingTower;
	private Tower tempTower;
	private TileGrid grid;
	
	public Player(TileGrid grid, WaveManager waveManager) {
		this.waveManager = waveManager;
		this.towerList = new ArrayList<Tower>();
		this.leftMouseButtonDown = false;
		this.holdingTower = false;
		this.tempTower = null;
		this.grid = grid;
		Cash = 0;
		Lives = 0;
	}
	
	public void setup() {
		Cash = 10;
		Lives = 3;
	}
	
	//Check if player can afford tower, if so process transaction
	public static boolean modifyCash(int amount) {
		if(Cash + amount >= 0) {
			Cash += amount;
			return true;
		}
		return false;
	}
	
	public static void modifyLives(int amount) {
		if(Lives + amount >= 0 && !Invincible) Lives += amount;
	}
	
	public static int getLives() {
		return Lives;
	}

	public void update() {
		//Update Holding Tower
		if(holdingTower) {
			tempTower.setX(MouseTile().getX());
			tempTower.setY(MouseTile().getY());
			tempTower.draw();
		}
		
		//Update All Towers
		for (Tower t: towerList) {
			t.pUpdate();
			t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
			if(!grid.getTile((int) t.getX()/TileWidth, (int) t.getY()/TileHeight).getType().buildable && t.getType().obeyBuildable) t.die();
			if(t.alive) {
				t.update();
				t.draw();
			}
		}
		
		//Handle Mouse Input
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown) placeTower();
		leftMouseButtonDown = Mouse.isButtonDown(0);
		Mouse.isButtonDown(1);
	}
	
	private void placeTower() {
		Tile currentTile = MouseTile();
		if(holdingTower && (currentTile.getType().buildable || !tempTower.getType().obeyBuildable) && !currentTile.getOccupied() && modifyCash(-tempTower.getCost())) {
			towerList.add(tempTower.getWithoutDummy().getAndReInit(grid));
			if(tempTower.type.tileify) waveManager.reload();
			currentTile.occupy(tempTower.type);
			holdingTower = false;
			tempTower = null;
		}
//		else if(holdingTower && currentTile.getOccupiedType().stage < tempTower.type.stage && modifyCash(-(tempTower.getCost()-currentTile.getOccupiedType().cost))) {
//			for(Tower t : towerList) if (t.getX() == currentTile.getX() && t.getY() == currentTile.getY() && t.type == currentTile.getOccupiedType()) t.die();
//			towerList.add(tempTower.getWithoutDummy());
//			currentTile.occupy(tempTower.type);
//			holdingTower = false;
//			tempTower = null;
//		} else if(holdingTower && currentTile.getOccupiedType().stage > tempTower.type.stage && modifyCash(-(tempTower.getCost()-currentTile.getOccupiedType().cost))) {
//			for(Tower t : towerList) if (t.getX() == currentTile.getX() && t.getY() == currentTile.getY() && t.type == currentTile.getOccupiedType()) t.die();
//			towerList.add(tempTower.getWithoutDummy());
//			currentTile.occupy(tempTower.type);
//			holdingTower = false;
//			tempTower = null;
//		}
	}
	
	public void pickTower(Tower t) {
		if(holdingTower) clearTower();
		tempTower = t;
		holdingTower = true;
	}
	
	public void clearTower() {
		tempTower.die();
		tempTower = null;
		holdingTower = false;
	}
}
