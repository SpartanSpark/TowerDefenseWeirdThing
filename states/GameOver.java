package states;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

import states.StateManager.GameState;

import static helpers.Artist.*;
import static states.StateManager.setState;


public class GameOver {

	private Texture background;
	
	public GameOver() {
		background = QuickLoad("gameOver");
	}
	
	public void update() {
		// Handle Keyboard Input
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()) setState(GameState.MAINMENU);
			if (Keyboard.getEventKey() == Keyboard.KEY_C && Keyboard.getEventKeyState()) hideUI = !hideUI;
			if (Keyboard.getEventKey() == Keyboard.KEY_R && Keyboard.getEventKeyState()) StateManager.mainMenu.choose();;
		}
		DrawQuadTex(background, 0, 0, TileWidth * 32, TileHeight * 16);
	}
}
