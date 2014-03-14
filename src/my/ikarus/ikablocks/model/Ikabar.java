package my.ikarus.ikablocks.model;

import my.ikarus.engine.Entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Ikabar extends Entity {
	//*---------- Members ----------*//
	// Constants (consider an scale factor)
	static final float WIDTH = 4f; // 4 units
	static final float HEIGHT = 0.5f; // Half a unit

	//*---------- Methods ----------*//
	/*---- Constructors ----*/
	public Ikabar(Vector2 pos) {
		super(pos);
		this.bounds.width = WIDTH;
		this.bounds.height = HEIGHT;
		this.updatable = true;
		this.controllable = true;
		this.collidable = true;
		this.objectID = ObjectIDType.BAR;
	}

	public Ikabar(Vector2 pos, TextureRegion texture) {
		super(pos,texture);
		this.bounds.width = WIDTH;
		this.bounds.height = HEIGHT;
		this.updatable = true;
		this.controllable = true;
		this.collidable = true;
		this.objectID = ObjectIDType.BAR;
	}
	
	/*---- Getters ----*/
	// Position/movement methods
	@Override
	public float getSpeed() {
		return SPEED;
	}
}
