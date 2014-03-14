package my.ikarus.engine;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Entity {
	//*---------- Members ----------*//
	// Constants (consider an scale factor)
	static final float WIDTH = 1f;
	static final float HEIGHT = 1f;
	protected static final float SPEED = 0f;
	static public enum ObjectIDType {
		DUMMY, BAR, BALL, TARGET, BLOCK
	}
	
	// Live members
	boolean		alive = true;
	// ID members
	protected	ObjectIDType objectID = ObjectIDType.DUMMY;
	// Animation members
	boolean		visible = true;
	float       stateTime = 0;
	// Position/movement members
	protected boolean		controllable = false;
	protected boolean		updatable = false;
	Vector2 	position = new Vector2();
	Vector2 	velocity = new Vector2();
	Vector2 	acceleration = new Vector2();
	// Collision members
	protected boolean 	collider = false;
	protected boolean 	collidable = false;
	boolean 	collided = false;
	// Bounding box (size)
	protected Rectangle 	bounds = new Rectangle();
	// Texture (consider including different ones)
	private		TextureRegion texture;

	//*---------- Methods ----------*//
	/*---- Constructors ----*/
	public Entity() {
		this.position = new Vector2(0,0);
		this.bounds.width = WIDTH;
		this.bounds.height = HEIGHT;
	}
	
	public Entity(Vector2 pos) {
		this.position = pos;
		this.bounds.width = WIDTH;
		this.bounds.height = HEIGHT;
	}
	
	public Entity(Vector2 pos, TextureRegion texture) {
		this.position = pos;
		this.bounds.width = WIDTH;
		this.bounds.height = HEIGHT;
		this.texture = texture;
	}
	
	/*---- Getters ----*/
	// Live methods
	public boolean isAlive() {
		return alive;
	}
	// ID methods
	public ObjectIDType getObjectID() {
		return objectID;
	}
	// Animation methods
	public boolean isVisible() {
		return visible;
	}
	public float getStateTime() {
		return stateTime;
	}
	// Position/movement methods
	public boolean isControllable() {
		return controllable;
	}
	public boolean isUpdatable() {
		return updatable;
	}
	public Vector2 getPosition() {
		return position;
	}
	public Vector2 getVelocity() {
		return velocity;
	}
	public Vector2 getAcceleration() {
		return acceleration;
	}
	public float getSpeed() {
		return SPEED;
	}
	// Collision methods
	public boolean isCollider() {
		return collider;
	}
	public boolean isCollidable() {
		return collidable;
	}
	public boolean isCollided() {
		return collided;
	}
	public void setCollided(boolean collided) {
		this.collided = collided;
	}
	// Bounding Box methods
	public Rectangle getBounds() {
		return bounds;
	}
	// Texture methods
	public TextureRegion getTexture() {
		return texture;
	}

	/*---- Setters ----*/
	// Live members
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	// ID methods
	public void setObjectID(ObjectIDType objectID) {
		this.objectID = objectID;
	}
	// Animation methods
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
	// Position/movement methods
	public void setControllable(boolean controllable) {
		this.controllable = controllable;
	}
	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}
	// Collision methods
	public void setCollider(boolean collider) {
		this.collider = collider;
	}
	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}
	// Bounding Box methods
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	// Texture methods
	public void setTexture(TextureRegion texture) {
		this.texture = texture;
	}	

	/*---- Update methods ----*/
	public void update(float delta) {
		if (updatable) {
			position.add(velocity.cpy().scl(delta));
			stateTime += delta;
		}
	}

}
