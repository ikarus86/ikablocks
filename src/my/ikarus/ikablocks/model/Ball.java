package my.ikarus.ikablocks.model;


import my.ikarus.engine.Entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Ball extends Entity {
	//*---------- Members ----------*//
	// States
	public enum State {
		IDLE, MOVING
	}
	// Constants (consider an scale factor)
	static final float WIDTH = 0.5f;
	static final float HEIGHT = 0.5f;
	static final float SPEED = 1f;	// Unit per second
	
	// State members
	State		state = State.IDLE;
	
	//*---------- Methods ----------*//
	/*---- Constructors ----*/
	public Ball(Vector2 pos) {
		super(pos);
		this.bounds.width = WIDTH;
		this.bounds.height = HEIGHT;
		this.updatable = true;
		this.controllable = true;
		this.collider = true;
		this.collidable = true;
		this.objectID = ObjectIDType.BALL;
	}
	public Ball(Vector2 pos, TextureRegion texture) {
		super(pos,texture);
		this.bounds.width = WIDTH;
		this.bounds.height = HEIGHT;
		this.updatable = true;
		this.controllable = true;
		this.collider = true;
		this.collidable = true;
		this.objectID = ObjectIDType.BALL;
	}
	
	/*---- Getters ----*/
	// State methods
	public State getState() {
		return state;
	}
	// Position/movement methods
	@Override
	public float getSpeed() {
		return SPEED;
	}

	/*---- Setters ----*/
	// State methods
	public void setState(State state) {
		this.state = state;
	}

}