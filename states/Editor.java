package states;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.Height;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.TileWidth;
import static helpers.Artist.hideUI;
import static helpers.Artist.TileHeight;
import static helpers.Leveler.LoadMap;
import static helpers.Leveler.SaveMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import UI.UI.Menu;
import tile.TileGrid;
import tile.TileType;

public class Editor {

	private TileGrid grid;
	private int index;
	private TileType[] types;
	private UI editorUI;
	private Menu tilePickerMenu;
	private Texture menuBackground;
	public boolean ready, crates;

	public Editor() {
		this.grid = LoadMap(StateManager.MAP_NAME);
		this.index = 0;
		this.types = new TileType[21];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.TallGrass;
		this.types[2] = TileType.TrimmedGrass;
		this.types[3] = TileType.Wood;
		this.types[4] = TileType.Sand;
		this.types[5] = TileType.Dirt;
		this.types[6] = TileType.Soil;
		this.types[7] = TileType.DirtBrick;
		this.types[8] = TileType.DirtCrate;
		this.types[9] = TileType.GravellyDirt;
		this.types[10] = TileType.Stone;
		this.types[11] = TileType.Path;
		this.types[12] = TileType.StoneBrick;
		this.types[13] = TileType.StoneCrate;
		this.types[14] = TileType.GravellyStone;
		this.types[15] = TileType.NormalBrick;
		this.types[16] = TileType.CementBrick;
		this.types[17] = TileType.BrickCrate;
		this.types[18] = TileType.Water;
		this.types[19] = TileType.Ice;
		this.types[20] = TileType.Snow;
		this.menuBackground = QuickLoad("menu_background3");
		this.ready = false;
		setupUI();
	}

	private void setupUI() {
		editorUI = new UI();
		editorUI.createMenu("TilePicker", TileWidth * 20, TileHeight, TileWidth * 3, TileHeight * 15, (int) (TileWidth * 0.7), (int) (TileHeight * 0.7), 2, 0);
		tilePickerMenu = editorUI.getMenu("TilePicker");
		tilePickerMenu.quickAdd("Grass", "tiles/grass");
		tilePickerMenu.quickAdd("Tall Grass", "tiles/grassTall");
		tilePickerMenu.quickAdd("Trimmed Grass", "tiles/grass2");
		tilePickerMenu.quickAdd("Wood", "tiles/wood");
		tilePickerMenu.quickAdd("Sand", "tiles/sand");
		tilePickerMenu.quickAdd("Dirt", "tiles/dirt");
		tilePickerMenu.quickAdd("Soil", "tiles/dirt2");
		tilePickerMenu.quickAdd("Dirt Bricks", "tiles/dirtBrick");
		tilePickerMenu.quickAdd("Gravelly Dirt", "tiles/dirtGravel");
		tilePickerMenu.quickAdd("Stone", "tiles/stone");
		tilePickerMenu.quickAdd("Path", "tiles/stone2");
		tilePickerMenu.quickAdd("Stone Bricks", "tiles/stoneBrick");
		tilePickerMenu.quickAdd("Gravelly Stone", "tiles/stoneGravel");
		tilePickerMenu.quickAdd("Normal Bricks", "tiles/brickNormal");
		tilePickerMenu.quickAdd("Cement Bricks", "tiles/brickCement");
		tilePickerMenu.quickAdd("Water", "tiles/water");
		tilePickerMenu.quickAdd("Ice", "tiles/ice");
		tilePickerMenu.quickAdd("Snow", "tiles/snow");
	}
	
	private void draw() {
		grid.draw();
		DrawQuadTex(menuBackground, TileWidth * 20, 0, TileWidth * 3, TileHeight * 15);
		editorUI.draw();
	}
	
	public void update() {
		draw();
		if(!Mouse.isButtonDown(0)) ready = true;
		// Handle Mouse Input
		if (Mouse.next() && Mouse.isButtonDown(0)) {
			if (tilePickerMenu.isButtonClicked("Grass")) index = 0;
			else if (tilePickerMenu.isButtonClicked("Tall Grass")) index = 1;
			else if (tilePickerMenu.isButtonClicked("Trimmed Grass")) index = 2;
			else if (tilePickerMenu.isButtonClicked("Wood")) index = 3;
			else if (tilePickerMenu.isButtonClicked("Sand")) index = 4;
			else if (tilePickerMenu.isButtonClicked("Dirt")) index = 5;
			else if (tilePickerMenu.isButtonClicked("Soil")) index = 6;
			else if (tilePickerMenu.isButtonClicked("Dirt Bricks")) index = 7;
			else if (crates && tilePickerMenu.isButtonClicked("Dirt Crate")) index = 8;
			else if (tilePickerMenu.isButtonClicked("Gravelly Dirt")) index = 9;
			else if (tilePickerMenu.isButtonClicked("Stone")) index = 10;
			else if (tilePickerMenu.isButtonClicked("Path")) index = 11;
			else if (tilePickerMenu.isButtonClicked("Stone Bricks")) index = 12;
			else if (crates && tilePickerMenu.isButtonClicked("Stone Crate")) index = 13;
			else if (tilePickerMenu.isButtonClicked("Gravelly Stone")) index = 14;
			else if (tilePickerMenu.isButtonClicked("Normal Bricks")) index = 15;
			else if (tilePickerMenu.isButtonClicked("Cement Bricks")) index = 16;
			else if (crates && tilePickerMenu.isButtonClicked("Brick Crate")) index = 17;
			else if (tilePickerMenu.isButtonClicked("Water")) index = 18;
			else if (tilePickerMenu.isButtonClicked("Ice")) index = 19;
			else if (tilePickerMenu.isButtonClicked("Snow")) index = 20;
			else if (Mouse.getX() < 1280 && ready) setTile();
		}

		// Handle Keyboard Input
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) moveIndex();
			if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()) leave();
			if (Keyboard.getEventKey() == Keyboard.KEY_C && Keyboard.getEventKeyState()) hideUI = !hideUI;
			if (Keyboard.getEventKey() == Keyboard.KEY_N && Keyboard.getEventKeyState()) initCrates();
		}
	}
	
	public void initCrates() {
		if(!crates) {
			tilePickerMenu.quickAdd("Brick Crate", "tiles/brickCrate");
			tilePickerMenu.quickAdd("Dirt Crate", "tiles/dirtCrate");
			tilePickerMenu.quickAdd("Stone Crate", "tiles/stoneCrate");
		}
		crates = true;
	}
	
	public void leave() {
		SaveMap(StateManager.MAP_NAME, grid);
		ready = false;
		//StateManager.game.waveManager.reload();
		StateManager.mainMenu.choose();
	}
	
	private void setTile() {
		grid.setTile((int) Math.floor(Mouse.getX()) / TileWidth, (int) Math.floor((Height - Mouse.getY() - 1) / TileWidth), types[index]);
	}

	private void moveIndex() {
		index++;
		if (index > types.length - 1) {
			index = 0;
		}

	}
}