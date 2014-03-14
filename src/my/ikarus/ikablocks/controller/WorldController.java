package my.ikarus.ikablocks.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import my.ikarus.engine.Entity;
import my.ikarus.engine.Physics;
import my.ikarus.engine.Entity.ObjectIDType;
import my.ikarus.ikablocks.model.Ball;
import my.ikarus.ikablocks.model.World;
import my.ikarus.ikablocks.model.World.State;

public class WorldController {

	//*---------- Members ----------*//
	// Key definitions
	enum Keys {
		LEFT, RIGHT, AIM
	}
	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.AIM, false);
	};
	
	// Focus when the screen is touched
	private Vector2 focus = new Vector2(0,0);
	
	// Constants
	static final float SPEED = 4f;	// Unit per second
	
	// Model variables
	private World world;
	
	// Collision variables
	//private Rectangle collisionArea = new Rectangle();

	//*---------- Methods ----------*//
	/*---- Constructors ----*/
	public WorldController(World world) {
		this.world = world;
	}

	/*---- Key input methods ----*/
	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}

	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}
	
	/*---- Focus methods ----*/
	public void focusPressed(float x, float y) {
		keys.get(keys.put(Keys.AIM, true));
		focus.x = x;
		focus.y = y;
	}
	public void focusReleased(float x, float y) {
		keys.get(keys.put(Keys.AIM, false));
		focus.x = x;
		focus.y = y;
	}

	/*---- Update methods ----*/
	/** Update all entities **/
	public void update(float delta) {
		int nBlocks=0;
		processInput();
		processCollisions();
		// We update the ball and check how many blocks are left
		for (Entity entity : world.getEntities()) {
			entity.update(delta);
			if (entity.getObjectID() == ObjectIDType.BALL) {
				if (((Ball)entity).getState()==Ball.State.MOVING) {
					// Now we check if it is out of bounds and make it bounce
					if (entity.getPosition().x < 0) {
						entity.getPosition().x = 0;
						entity.getVelocity().x = -entity.getVelocity().x;
					}
					if (entity.getPosition().x > world.getUnitsX()-entity.getBounds().width) {
						entity.getPosition().x = world.getUnitsX()-entity.getBounds().width;
						entity.getVelocity().x = -entity.getVelocity().x;
					}
					if (entity.getPosition().y < 0) {
						world.setState(State.LIVELOST);
					}
					if (entity.getPosition().y > world.getUnitsY()-entity.getBounds().height) {
						entity.getPosition().y = world.getUnitsY()-entity.getBounds().height;
						entity.getVelocity().y = -entity.getVelocity().y;
					}
				}
			}
			if (entity.getObjectID() == ObjectIDType.BLOCK) {
				nBlocks++;
			}
		}
		if (nBlocks == 0) {
			world.setState(State.EMPTY);
		}
		world.update(delta);
	}
	/** Update the controllables' status **/
	private void processInput() {
		Entity ball = world.getEntity(ObjectIDType.BALL);
		Entity bar = world.getEntity(ObjectIDType.BAR);
		Entity target = world.getEntity(ObjectIDType.TARGET);
		// First we need to check the world's state
		switch (world.getState()) {
		case IDLE:
			if (keys.get(Keys.LEFT)) {
				// Left is pressed
				// Move
				ball.getVelocity().x = -SPEED;
				bar.getVelocity().x = -SPEED;
			}
			if (keys.get(Keys.LEFT)) {
				// Left is pressed
				// Move
				ball.getVelocity().x = -SPEED;
				bar.getVelocity().x = -SPEED;
			}
			if (keys.get(Keys.RIGHT)) {
				// Right is pressed
				// Move
				ball.getVelocity().x = SPEED;
				bar.getVelocity().x = SPEED;
			}
			// If both or none directions are pressed, the bar is idle
			if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
					(!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
				// Brake
				ball.getAcceleration().x = 0;
				ball.getVelocity().x = 0;
				bar.getAcceleration().x = 0;
				bar.getVelocity().x = 0;	
			}
			if (keys.get(Keys.AIM)){
				// If we touch "the bar zone"
				if (focus.y > (world.getUnitsY()-1)*world.getPpuY()) {
					ball.getPosition().x = focus.x/world.getPpuX()-ball.getBounds().width/2;
					bar.getPosition().x = focus.x/world.getPpuX()-bar.getBounds().width/2;
				}
				// If not, we are aiming the ball (if not shot)
				else {
					world.setState(State.AIM);
					target.setVisible(true);
					target.getPosition().x = focus.x/world.getPpuX()-target.getBounds().width/2;
					target.getPosition().y = world.getUnitsY()-(focus.y/world.getPpuY())-target.getBounds().height/2;
				}
			}
			break;
		case AIM:
			// If we released focus means we shot the ball
			if (!keys.get(Keys.AIM)) {
				world.setState(State.ACTION);
				target.setVisible(false);
				ball.getVelocity().x = target.getPosition().x - ball.getPosition().x;
				ball.getVelocity().y = target.getPosition().y - ball.getPosition().y;
				((Ball)ball).setState(Ball.State.MOVING);
			}
			else {
				target.setVisible(true);
				target.getPosition().x = focus.x/world.getPpuX()-target.getBounds().width/2;
				target.getPosition().y = world.getUnitsY()-(focus.y/world.getPpuY())-target.getBounds().height/2;
			}
			break;
		case ACTION:
			// We move the bar if needed
			if (keys.get(Keys.LEFT)) {
				// Left is pressed
				// Move
				bar.getVelocity().x = -SPEED;
			}
			if (keys.get(Keys.LEFT)) {
				// Left is pressed
				// Move
				bar.getVelocity().x = -SPEED;
			}
			if (keys.get(Keys.RIGHT)) {
				// Right is pressed
				// Move
				bar.getVelocity().x = SPEED;
			}
			// If both or none directions are pressed, the bar is idle
			if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
					(!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
				// Brake
				bar.getAcceleration().x = 0;
				bar.getVelocity().x = 0;	
			}
			if (keys.get(Keys.AIM)){
				// If we touch "the bar zone"
				if (focus.y > (world.getUnitsY()-1)*world.getPpuY()) {
					bar.getPosition().x = focus.x/world.getPpuX()-bar.getBounds().width/2;
				}
			}
			break;
		case LIVELOST:
			// If we have enough lives left we respawn the ball
			// and change to IDLE
			ball.getPosition().x = bar.getPosition().x + bar.getBounds().width/2 - ball.getBounds().width/2;
			ball.getPosition().y = 0.5f;
			ball.getVelocity().x = 0f;
			ball.getVelocity().y = 0f;
			ball.getAcceleration().x = 0f;
			ball.getAcceleration().y = 0f;
			((Ball)ball).setState(Ball.State.IDLE);
			world.setState(State.IDLE);
			break;
		case EMPTY:
			// We respawn the ball, repopulate the level and change to IDLE
			ball.getPosition().x = bar.getPosition().x + bar.getBounds().width/2 - ball.getBounds().width/2;
			ball.getPosition().y = 0.5f;
			ball.getVelocity().x = 0f;
			ball.getVelocity().y = 0f;
			ball.getAcceleration().x = 0f;
			ball.getAcceleration().y = 0f;
			((Ball)ball).setState(Ball.State.IDLE);
			world.setState(State.IDLE);
			world.createBlocks();
			break;
		}
	}
	/** Collision detection method **/
	private void processCollisions() {
		boolean collision;
		Entity entity;
		
		for (Entity collider : world.getEntities() ) {
			// We only check collision if there are collider entities.
			// These are usually "moving" objects.
			// This could be optimised looking only for close entities.
			if (collider.isCollider()) {
				for (int i = 0; i < world.getEntities().size; i++) {
					// Now that we have a collider we look if it hit something.
					entity = world.getEntities().get(i);
					if (entity.isCollidable() && entity != collider) {
						collision = Physics.checkCollision(collider,entity);
						collider.setCollided(collision);
						entity.setCollided(collision);
						if (collision)
							processCollision(collider,entity);
					}
				}
			}
		}		
	}
	private void processCollision(Entity collider, Entity collided) {
		switch (collider.getObjectID()) {
		case BALL:
			if (collided.getObjectID()==ObjectIDType.BAR) {
				collider.getVelocity().y = Math.abs(collider.getVelocity().y);				
			}
			else if  (collided.getObjectID()==ObjectIDType.BLOCK) {
				collided.setAlive(false);
				collided.setVisible(false);
				collider.setVelocity(Physics.getBounce(collider, collided));
				// We accelerate the ball
				collider.getVelocity().x *= 1.1;
				collider.getVelocity().y *= 1.1;
			}
			break;
		default:
			break;
		}
	}	
}


