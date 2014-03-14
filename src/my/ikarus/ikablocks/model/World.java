package my.ikarus.ikablocks.model;

import my.ikarus.engine.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {

	/*---------- Members ----------*/
	// Constants
	static final float SPEED = 4f;	// Unit per second
	// State members and definitions
	public enum State {
		IDLE, AIM, ACTION, LIVELOST, EMPTY
	}
	State state	= State.IDLE;
	// Camera members
    private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis
	private float unitsX;
	private float unitsY;
    // Entity members
	Array<Entity> entities = new Array<Entity>();
	Array<TextureRegion> textures = new Array<TextureRegion>();
 
	/*---------- Methods ----------*/
	/*---- Constructors ----*/
	/** We load the textures first and then we create and populate the world **/
	public World() {
		loadTextures();
        createDemoWorld();
    }
	
	/*---- World creation methods ----*/
	// Texture loading
	private void loadTextures() {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures/textures.atlas"));
		textures.add(atlas.findRegion("images/mainBar"));
		textures.add(atlas.findRegion("images/mainBall"));
		textures.add(atlas.findRegion("images/target"));
		textures.add(atlas.findRegion("images/block"));
		//TextureRegion[] barFrames = new TextureRegion[5];
		//for (int i = 0; i < 5; i++) {
		//	barFrames[i] = atlas.findRegion("images/bob0" + (i + 2));
		//}
		//barAnimation = new Animation(RUNNING_FRAME_DURATION, barFrames);
	}
	// World creation and population
    private void createDemoWorld() {
    	Entity temp;
    	// We add the bar Sprite
    	temp = new Ikabar(new Vector2(3, 0),textures.get(0));
    	this.entities.add(temp);
    	
    	// We add the ball Sprite
    	temp = new Ball(new Vector2(4.75f, 0.5f),textures.get(1));
    	this.entities.add(temp);
    	
    	// We add the targetting Sprite
    	temp = new Entity(new Vector2(0f, 0f),textures.get(2));
    	temp.setObjectID(Entity.ObjectIDType.TARGET);
    	temp.setVisible(false);
    	temp.getBounds().width = 0.5f;
    	temp.getBounds().height = 0.5f;
    	this.entities.add(temp);
    	
    	// We add some block Sprites
    	createBlocks();
    }
    public void createBlocks() {
    	// We get the target and put it last to guarantee it will always be on top
    	Entity target = entities.pop();
    	Entity temp;
    	for (int i = 1; i < 9; i++) {
    		temp = new Entity(new Vector2(i, 2.5f),textures.get(3));
        	temp.setObjectID(Entity.ObjectIDType.BLOCK);
        	temp.setVisible(true);
        	temp.setCollidable(true);
        	temp.getBounds().width = 1f;
        	temp.getBounds().height = 0.5f;
        	this.entities.add(temp);
    	}
    	for (int i = 1; i < 8; i++) {
    		temp = new Entity(new Vector2(i+0.5f, 3f),textures.get(3));
        	temp.setObjectID(Entity.ObjectIDType.BLOCK);
        	temp.setVisible(true);
        	temp.setCollidable(true);
        	temp.getBounds().width = 1f;
        	temp.getBounds().height = 0.5f;
        	this.entities.add(temp);
    	}
    	for (int i = 2; i < 8; i++) {
    		temp = new Entity(new Vector2(i, 3.5f),textures.get(3));
        	temp.setObjectID(Entity.ObjectIDType.BLOCK);
        	temp.setVisible(true);
        	temp.setCollidable(true);
        	temp.getBounds().width = 1f;
        	temp.getBounds().height = 0.5f;
        	this.entities.add(temp);
    	}
    	for (int i = 2; i < 7; i++) {
    		temp = new Entity(new Vector2(i+0.5f, 4f),textures.get(3));
        	temp.setObjectID(Entity.ObjectIDType.BLOCK);
        	temp.setVisible(true);
        	temp.setCollidable(true);
        	temp.getBounds().width = 1f;
        	temp.getBounds().height = 0.5f;
        	this.entities.add(temp);
    	}
    	for (int i = 3; i < 7; i++) {
    		temp = new Entity(new Vector2(i, 4.5f),textures.get(3));
        	temp.setObjectID(Entity.ObjectIDType.BLOCK);
        	temp.setVisible(true);
        	temp.setCollidable(true);
        	temp.getBounds().width = 1f;
        	temp.getBounds().height = 0.5f;
        	this.entities.add(temp);
    	}
    	for (int i = 3; i < 6; i++) {
    		temp = new Entity(new Vector2(i+0.5f, 5f),textures.get(3));
        	temp.setObjectID(Entity.ObjectIDType.BLOCK);
        	temp.setVisible(true);
        	temp.setCollidable(true);
        	temp.getBounds().width = 1f;
        	temp.getBounds().height = 0.5f;
        	this.entities.add(temp);
    	}
    	for (int i = 4; i < 6; i++) {
    		temp = new Entity(new Vector2(i, 5.5f),textures.get(3));
        	temp.setObjectID(Entity.ObjectIDType.BLOCK);
        	temp.setVisible(true);
        	temp.setCollidable(true);
        	temp.getBounds().width = 1f;
        	temp.getBounds().height = 0.5f;
        	this.entities.add(temp);
    	}
    	for (int i = 4; i < 5; i++) {
    		temp = new Entity(new Vector2(i+0.5f, 6f),textures.get(3));
        	temp.setObjectID(Entity.ObjectIDType.BLOCK);
        	temp.setVisible(true);
        	temp.setCollidable(true);
        	temp.getBounds().width = 1f;
        	temp.getBounds().height = 0.5f;
        	this.entities.add(temp);
    	}
    	
    	
    	this.entities.add(target);
    }
    
    /*---- Getters ----*/
    // State methods
	public State getState() {
		return state;
	}
    // Camera methods
    public float getPpuX() {
		return ppuX;
	}
	public float getPpuY() {
		return ppuY;
	}
	public float getUnitsX() {
		return unitsX;
	}
	public float getUnitsY() {
		return unitsY;
	}
	// Entity methods
	public Entity getEntity(Entity.ObjectIDType objectID) {
		Entity result = null;
		for (Entity entity : entities) {
			if (entity.getObjectID() == objectID) {
				result = entity;
				break;
			}
		}
		return result;
	}
	public Array<Entity> getEntities() {
		return entities;
	}
	
	/*---- Setters ----*/
	// State methods
	public void setState(State state) {
		this.state = state;
	}
	// Camera methods
	public void setPpuX(float ppuX) {
		this.ppuX = ppuX;
	}
	public void setPpuY(float ppuY) {
		this.ppuY = ppuY;
	}
	public void setUnitsX(float unitsX) {
		this.unitsX = unitsX;
	}
	public void setUnitsY(float unitsY) {
		this.unitsY = unitsY;
	}
	// Entity methods
	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}
	public void setEntities(Array<Entity> entities) {
		this.entities = entities;
	}
	
	/*---- Update methods ----*/
	public void update(float delta) {
		// Delete all dead entities
		for (Entity entity : entities) {
			if (!entity.isAlive())
				entities.removeValue(entity, true);
		}
	}
	




	
}
