package projectile;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public enum ProjectileType {
	
	RedBall(QuickLoad("projectiles/projectileBullet"), 9, 400, false),
	GreenBall(QuickLoad("projectiles/projectileBullet"), 20, 400, false),
	IceBall(QuickLoad("projectiles/projectileBulletIce"), 5, 350, false),
	BlueBall(QuickLoad("projectiles/projectileBullet"), 25, 400, false),
	Firework(QuickLoad("projectiles/projectileRocket"), 1500, 875, true),
	PurpleBall(QuickLoad("projectiles/projectileBullet"), 3, 1000, false),
	Spikes(QuickLoad("tiles/spikes"), 20, 0, false),
	ProjectileMine(QuickLoad("tiles/mine"), 4000, 125, false);
	
	Texture texture;
	public int damage;
	float speed;
	boolean turn;
	
	ProjectileType(Texture texture, int damage, float speed, boolean turn) {
		this.texture = texture;
		this.damage = damage;
		this.speed = speed;
		this.turn = turn;
	}
	
}
