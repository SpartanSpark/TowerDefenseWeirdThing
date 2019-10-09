package states;

import static helpers.Leveler.LoadMap;
import org.lwjgl.input.Keyboard;

import static helpers.Artist.hideUI;
import UI.UI;
import tile.TileGrid;

public class StateManager {

	public static enum GameState {
		MAINMENU, GAME, EDITOR, GAMEOVER
	}

	public static final String MAP_NAME = "map.tdm";
	
	public static GameState gameState = GameState.MAINMENU;
	public static MainMenu mainMenu;
	public static Game game;
	public static Editor editor;
	public static GameOver gameOver;

	private static int curY;
	private static UI infoUI;
	
	public static long nextSecond = System.currentTimeMillis() + 1000;
	public static int framesInLastSecond = 0;
	public static int framesInCurrentSecond = 0;
	
	static TileGrid map = LoadMap(MAP_NAME);
	
	public static void update() {
		//State Setups + State Updates
		switch(gameState) {
		case MAINMENU:
			if (mainMenu == null)
				mainMenu = new MainMenu();
			mainMenu.update();
			break;
		case GAME:
			if (game == null || game.isOver() || game.restartReq()) 
				game = new Game(map);
			game.update();
			break;
		case EDITOR:
			if (editor == null)
				editor = new Editor();
			editor.update();
			break;
		case GAMEOVER:
			if (gameOver == null)
				gameOver = new GameOver();
			gameOver.update();
			break;
		}
		
		//Time Calculations
		
		long currentTime = System.currentTimeMillis();
		if (currentTime > nextSecond) {
			nextSecond += 1000;
			framesInLastSecond = framesInCurrentSecond;
			framesInCurrentSecond = 0;
		}
		
		framesInCurrentSecond++;
		
		// Handle Keyboard Input
		
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_C && Keyboard.getEventKeyState()) hideUI = !hideUI;
		}
		
		updateUI();
	}
	
	public static void setState(GameState newState) {
		gameState = newState;
	}
	
	public static void setupUI() {
		infoUI = new UI(); 
	}
	
	private static void updateUI() {
		if(!hideUI) {
			infoUI.drawString(0, 0, StateManager.framesInLastSecond + " FPS");
			infoUI.drawString(0, 60, "Controls:");
			curY = 90;
			if(gameState == GameState.GAME) {
				infoUI.drawString(0, curY, "R: Reset level.");
				curY += 30;
				infoUI.drawString(0, curY, "X: Clear held tower.");
				curY += 30;
				infoUI.drawString(0, curY, "Esc: Back to main menu.");
				curY += 30;
			} else if(gameState == GameState.EDITOR) {
				infoUI.drawString(0, curY, "Esc: Back to main menu.");
				curY += 30;
			} else if(gameState == GameState.GAMEOVER) {
				infoUI.drawString(0, curY, "R: Reset level.");
				curY += 30;
				infoUI.drawString(0, curY, "Esc: Back to main menu.");
				curY += 30;
			}
			infoUI.drawString(0, curY, "C: Toggle this menu.");
		}
	}
	
}
