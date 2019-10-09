package enemy;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexRot;
import static helpers.Artist.TileWidth;
import static helpers.Artist.TileHeight;
import static helpers.Artist.QuickLoad;

import org.newdawn.slick.opengl.Texture;

import tile.Tile;
import tile.TileGrid;

public class EnemyHuman extends Enemy{
	
	private boolean other;
	private Texture tex;
	private Texture tex1;
	private Texture tex2;
	private int counter;
	
	public EnemyHuman(Tile startTile, TileGrid grid) {
		super(EnemyType.Human, startTile, grid);
		this.other = false;
		this.tex = super.texture;
		this.tex1 = super.texture;
		this.tex2 = QuickLoad("enemies/enemyHumanOther");
	}
	
	@Override
	protected boolean walkable(Tile t) {
		return t.getType().path || t.getType().wall;
	}
	
	@Override
	public void draw() {
		counter++;
		if(counter % 60 == 0) other = !other;
		if(other) tex = tex2;
		else tex = tex1;
		float healthPercentage = super.health / super.startHealth;
		super.changeAngle();
		DrawQuadTexRot(tex, super.x, super.y, super.width, super.height, super.angle);
		DrawQuadTex(super.healthBackground, super.x + TileWidth / 16, super.y - TileHeight / 4, super.width * 7 / 8, super.height / 8);
		DrawQuadTex(super.healthForeground, super.x + TileWidth / 16, super.y - TileHeight / 4, super.width * healthPercentage * 7 / 8, super.height / 8);
		DrawQuadTex(super.healthBorder, super.x + TileWidth / 16, super.y - TileHeight / 4, super.width * 7 / 8, super.height / 8);
	}
}
