package data;

import static helpers.Artist.BeginSession;
import static helpers.Artist.UpdateDisplay;

import org.lwjgl.opengl.Display;

import states.Editor;
import states.StateManager;

public class TDBoot {
	
	public TDBoot() {
		//Makes screen and sets it up
		BeginSession("Map Editor | Made by: cool dude");
		//Update everything repeatedly until you close the window
		while(!Display.isCloseRequested()) {
			StateManager.update();
			UpdateDisplay();
		}
		StateManager.editor.leave();
		Display.destroy();
	}
	
	public static void main(String[] args) {
		new TDBoot();
	}
}