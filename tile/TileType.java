package tile;

public enum TileType {

	//Towers
	Grass("grass", true, false, false),
	Soil("dirt2", true, false, false),
	Path("stone2", true, false, false),
	Ice("ice", true, false, false),
	Wood("wood", true, false, false),
	//Enemies
	TrimmedGrass("grass2", false, true, false),
	Dirt("dirt", false, true, false),
	Stone("stone", false, true, false),
	Sand("sand", false, true, false),
	Snow("snow", false, true, false),
	//Bricks
	NormalBrick("brickNormal", false, false, true),
	CementBrick("brickCement", false, false, true),
	DirtBrick("dirtBrick", false, false, true),
	StoneBrick("stoneBrick", false, false, true),
	//Crates
	BrickCrate("brickCrate", false, false, true),
	DirtCrate("dirtCrate", false, false, true),
	StoneCrate("stoneCrate", false, false, true),
	//Blank
	Water("water", false, false, false),
	TallGrass("grassTall", false, false, false),
	GravellyDirt("dirtGravel", false, false, false),
	GravellyStone("stoneGravel", false, false, false),
	NULL("null", false, false, false);
	
	
	String textureName;
	public boolean buildable, path, wall;
	
	TileType(String textureName, boolean buildable, boolean path, boolean wall) {
		this.textureName = "tiles/" + textureName;
		this.buildable = buildable;
		this.path = path;
		this.wall = wall;
	}
	
}
