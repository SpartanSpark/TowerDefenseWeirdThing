package tower;

import org.newdawn.slick.opengl.Texture;

import projectile.ProjectileType;
import tile.TileType;

import static helpers.Artist.*;

public enum TowerType {
	
	CannonRed(new Texture[]{QuickLoad("towers/cannonBase"), QuickLoad("towers/cannonGun")}, ProjectileType.RedBall, 4 * TileWidth, 2.5f, 5, false, 0, true, true, false, null, false),
	CannonGreen(new Texture[]{QuickLoad("towers/cannonBaseGreen"),QuickLoad("towers/cannonGunGreen")}, ProjectileType.GreenBall, 12 * TileWidth, 2, 20, false, 1, true, true, false, null, false),
	CannonIce(new Texture[]{QuickLoad("towers/cannonBaseIce"), QuickLoad("towers/cannonGunIce")}, ProjectileType.IceBall, 8 * TileWidth, 5, 30, false, 0, false, true, false, null, false),
	CannonBlue(new Texture[]{QuickLoad("towers/cannonBaseBlue"),QuickLoad("towers/cannonGunBlue")}, ProjectileType.BlueBall, 16 * TileWidth, 1, 40, false, 2, true, true, false, null, false),
	Rocket(new Texture[]{QuickLoad("towers/towerRocketBase"),QuickLoad("towers/towerRocketGun")}, ProjectileType.Firework, 32 * TileWidth, 10, 150, true, 0, false, true, false, null, false),
	CannonPurple(new Texture[]{QuickLoad("towers/cannonBasePurple"),QuickLoad("towers/cannonGunPurple")}, ProjectileType.PurpleBall, 32 * TileWidth, 0.05f, 200, false, 3, true, true, false, null, false),
	BrickCrate(new Texture[]{QuickLoad("tiles/brickCrate")}, null, 0, 0, 50, false, 0, false, false, true, TileType.BrickCrate, false),
	DirtCrate(new Texture[]{QuickLoad("tiles/dirtCrate")}, null, 0, 0, 50, false, 0, false, false, true, TileType.DirtCrate, false),
	StoneCrate(new Texture[]{QuickLoad("tiles/stoneCrate")}, null, 0, 0, 50, false, 0, false, false, true, TileType.StoneCrate, false),
	Spikes(new Texture[]{QuickLoad("tiles/spikes")}, ProjectileType.Spikes, 0, 0, 5, false, 0, false, false, false, null, true),
	TowerMine(new Texture[]{QuickLoad("tiles/mineOn")}, ProjectileType.ProjectileMine, 5 * TileWidth, 0, 125, false, 0, false, false, false, null, true);
	
	Texture[] textures;
	ProjectileType projectileType;
	int range;
	public int cost;
	public int stage;
	float firingSpeed;
	public boolean tracking, stageable, obeyBuildable, tileify, projectileify;
	public TileType tileType;
	
	TowerType(Texture[] textures, ProjectileType projectileType, int range, float firingSpeed, int cost, boolean tracking, int stage, boolean stageable, boolean obeyBuildable, boolean tileify, TileType tileType, boolean projectileify) {
		this.textures = textures;
		this.projectileType = projectileType;
		this.range = range;
		this.cost = cost;
		this.firingSpeed = firingSpeed;
		this.tracking = tracking;
		this.stage = stage;
		this.stageable = stageable;
		this.obeyBuildable = obeyBuildable;
		this.tileify = tileify;
		this.tileType = tileType;
		this.projectileify = projectileify;
	}
	
}