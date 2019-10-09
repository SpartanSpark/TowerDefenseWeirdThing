package states;

import static helpers.Artist.QuickLoad;
import static helpers.Artist.TileWidth;
import static helpers.Artist.hideUI;
import static states.StateManager.setState;
import static helpers.Artist.TileHeight;
import static helpers.Artist.DrawQuadTex;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import UI.UI.Menu;
import data.Player;
import enemy.WaveManager;
import helpers.Clock;
import states.StateManager.GameState;
import tile.TileGrid;
import tower.CrateBrick;
import tower.CrateDirt;
import tower.CrateStone;
import tower.TowerSpikes;
import tower.TowerCannonBlue;
import tower.TowerCannonGreen;
import tower.TowerCannonIce;
import tower.TowerCannonPurple;
import tower.TowerCannonRed;
import tower.TowerMine;
import tower.TowerRocket;
import tower.TowerType;

public class Game {

	public TileGrid grid;
	public Player player;
	public WaveManager waveManager;
	private UI gameUI;
	private Menu towerPickerMenu;
	private Texture menuBackground;
	private boolean crates;
	
	public Game(TileGrid grid) {
		this.grid = grid;
		this.menuBackground = QuickLoad("menu_background2");
		waveManager = new WaveManager(2, 5, grid);
		player = new Player(grid, waveManager);
		player.setup();
		setupUI();
	}
	
	private void setupUI() {
		gameUI = new UI(); 
		gameUI.createMenu("TowerPicker", TileWidth * 20, TileHeight, TileWidth * 3, TileHeight * 14, (int) (TileWidth * 0.7), (int) (TileHeight * 0.7), 2, 0);
		towerPickerMenu = gameUI.getMenu("TowerPicker");
		towerPickerMenu.quickAdd("Spikes", "$" + TowerType.Spikes.cost, "tiles/spikes");
		towerPickerMenu.quickAdd("RedCannon", "$" + TowerType.CannonRed.cost, "towers/cannonFull");
		towerPickerMenu.quickAdd("GreenCannon", "$" + TowerType.CannonGreen.cost, "towers/cannonFullGreen");
		towerPickerMenu.quickAdd("IceCannon", "$" + TowerType.CannonIce.cost, "towers/cannonFullIce");
		towerPickerMenu.quickAdd("BlueCannon", "$" + TowerType.CannonBlue.cost, "towers/cannonFullBlue");
		towerPickerMenu.quickAdd("StoneCrate", "$" + TowerType.StoneCrate.cost, "tiles/StoneCrate");
		towerPickerMenu.quickAdd("Mine", "$" + TowerType.TowerMine.cost, "tiles/mineOn");
		towerPickerMenu.quickAdd("Rocket", "$" + TowerType.Rocket.cost, "towers/towerRocketFull");
		towerPickerMenu.quickAdd("PurpleCannon", "$" + TowerType.CannonPurple.cost, "towers/cannonFullPurple");
	}
	
	private void updateUI() {
		DrawQuadTex(menuBackground, TileWidth * 20, 0, TileWidth * 3, TileHeight * 15);
		gameUI.draw();
		gameUI.drawString((int) (TileWidth * 20.5), TileHeight * 8, "Lives: " + Player.Lives);
		gameUI.drawString((int) (TileWidth * 20.5), TileHeight * 9, "Cash: $" + Player.Cash);
		gameUI.drawString((int) (TileWidth * 20.5), TileHeight * 10, "Wave: " + waveManager.getWaveNumber());
		if (Mouse.next() && Mouse.isButtonDown(0)) {
			if (towerPickerMenu.isButtonClicked("RedCannon")) player.pickTower(new TowerCannonRed(grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList(), true));
			else if (towerPickerMenu.isButtonClicked("GreenCannon")) player.pickTower(new TowerCannonGreen(grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList(), true));
			else if (towerPickerMenu.isButtonClicked("BlueCannon")) player.pickTower(new TowerCannonBlue(grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList(), true));
			else if (towerPickerMenu.isButtonClicked("PurpleCannon")) player.pickTower(new TowerCannonPurple(grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList(), true));
			else if (towerPickerMenu.isButtonClicked("IceCannon")) player.pickTower(new TowerCannonIce(grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList(), true));
			else if (towerPickerMenu.isButtonClicked("Rocket")) player.pickTower(new TowerRocket(grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList(), true));
			else if (towerPickerMenu.isButtonClicked("StoneCrate")) player.pickTower(new CrateStone(grid.getTile(0, 0), grid, true));
			else if (crates && towerPickerMenu.isButtonClicked("BrickCrate")) player.pickTower(new CrateBrick(grid.getTile(0, 0), grid, true));
			else if (crates && towerPickerMenu.isButtonClicked("DirtCrate")) player.pickTower(new CrateDirt(grid.getTile(0, 0), grid, true));
			else if (towerPickerMenu.isButtonClicked("Spikes")) player.pickTower(new TowerSpikes(grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList(), true));
			else if (towerPickerMenu.isButtonClicked("Mine")) player.pickTower(new TowerMine(grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList(), true));
		}
	}
	
	public void initCrates() {
		if(!crates) {
			towerPickerMenu.quickAdd("BrickCrate", "$" + TowerType.BrickCrate.cost, "tiles/BrickCrate");
			towerPickerMenu.quickAdd("DirtCrate", "$" + TowerType.DirtCrate.cost, "tiles/DirtCrate");
		}
		crates = true;
	}
	
	public boolean isOver() {
		if(Player.getLives() < 1) grid.reset();
		if(Player.getLives() < 1) Clock.ResetMultiplier();
		return Player.getLives() < 1;
	}
	
	public boolean restartReq() {
		if(Keyboard.getEventKey() == Keyboard.KEY_R && Keyboard.getEventKeyState()) grid.reset();
		if(Keyboard.getEventKey() == Keyboard.KEY_R && Keyboard.getEventKeyState()) Clock.ResetMultiplier();
		return Keyboard.getEventKey() == Keyboard.KEY_R && Keyboard.getEventKeyState();
	}
	
	public void update() {
		// Handle Keyboard Input
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()) StateManager.mainMenu.choose();
			if (Keyboard.getEventKey() == Keyboard.KEY_C && Keyboard.getEventKeyState()) hideUI = !hideUI;
			if (Keyboard.getEventKey() == Keyboard.KEY_X && Keyboard.getEventKeyState()) player.clearTower();
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) Clock.ChangeMultiplier(0.2f);
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) Clock.ChangeMultiplier(-0.2f);
			if (Keyboard.getEventKey() == Keyboard.KEY_H && Keyboard.getEventKeyState()) Player.Cash += 20;
			if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) Player.Lives += 2;
			if (Keyboard.getEventKey() == Keyboard.KEY_E && Keyboard.getEventKeyState()) Player.Invincible = !Player.Invincible;
			if (Keyboard.getEventKey() == Keyboard.KEY_D && Keyboard.getEventKeyState()) waveManager.reload();
			if (Keyboard.getEventKey() == Keyboard.KEY_N && Keyboard.getEventKeyState()) initCrates();
		}
		grid.draw();
		waveManager.update();
		player.update();
		if(Player.getLives() < 1) setState(GameState.GAMEOVER);
		updateUI();
	}
}
