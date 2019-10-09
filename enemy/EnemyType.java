package enemy;

import static helpers.Artist.*;
import org.newdawn.slick.opengl.Texture;

public enum EnemyType {

	UFO(QuickLoad("enemies/enemyUFO"), 100, 25f, 5, true, false, false),
	Alien(QuickLoad("enemies/enemyAlien"), 70, 37f, 10,true, false, false),
	Plane(QuickLoad("enemies/enemyJet"), 450, 18f, 25, false, true, true),
	Ent(QuickLoad("enemies/enemyEnt"), 40, 250f, 30, true, true, true),
	Human(QuickLoad("enemies/enemyHuman"), 90, 50f, 35, true, true, false),
	Zombie(QuickLoad("enemies/enemyZombie"), 110, 400f, 50, true, true, true),
	Tank(QuickLoad("enemies/enemyTank"), 40, 5000f, 75, true, true, false),
	SuperTank(QuickLoad("enemies/enemySuperTank"), 30, 100000f, 500, true, true, false);
	
	Texture texture;
	public int speed;
	int prize;
	float health;
	public boolean obeyPath, turn, slowImmunity;
	EnemyType(Texture texture, int speed, float health, int prize, boolean obeyPath, boolean turn, boolean slowImmunity) {
		this.texture = texture;
		this.speed = speed;
		this.health = health;
		this.prize = prize;
		this.obeyPath = obeyPath;
		this.turn = turn;
		this.slowImmunity = slowImmunity;
	}
}
