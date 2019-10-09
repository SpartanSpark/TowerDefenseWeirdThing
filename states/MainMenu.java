package states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import states.StateManager.GameState;

import static helpers.Artist.*;


public class MainMenu {

	private Texture background;
	private UI menuUI;
	private int menu;
	
	public MainMenu() {
		choose();
		menuUI = new UI();
		menuUI.addButton("Play", "buttonPlay", Width / 2 - 128, (int) (Height * 0.45));
		menuUI.addButton("Editor", "buttonEditor", Width / 2 - 128, (int) (Height * 0.55f));
		menuUI.addButton("Quit", "buttonQuit", Width / 2 - 128, (int) (Height * 0.65f));
	}
	
	private void updateButtons() {
		if (Mouse.isButtonDown(0)) {
			if (menuUI.isButtonClicked("Play"))
				StateManager.setState(GameState.GAME);
			if(menuUI.isButtonClicked("Editor"))
				StateManager.setState(GameState.EDITOR);
			if (menuUI.isButtonClicked("Quit"))
				System.exit(0);
		}
	}
	
	public void update() {
		DrawQuadTex(background, 0, 0, TileWidth * 32, TileHeight * 16);
		menuUI.draw();
		updateButtons();
	}
	
	public void choose() {
		StateManager.setState(GameState.MAINMENU);
		menu = (int) Math.round(Math.random() * 2);
		background = QuickLoad("mainmenu" + menu);
	}
}
