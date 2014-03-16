package my.ikarus.engine;

import com.badlogic.gdx.math.Vector2;


public class Physics {
	/**
	 * Method to check the collision between two Entities using
	 * the bounding box algorithm.
	 * @param entity1: Object that collides
	 * @param entity2: Object that may have collided
	 * @return
	 */
	static public boolean checkCollisionBB(Entity entity1, Entity entity2) {
		boolean collided = false;
		// We check collision on the X axis
		float x1ini, x1end, x2ini, x2end;
		x1ini = entity1.getPosition().x;
		x1end = x1ini + entity1.getBounds().width;
		x2ini = entity2.getPosition().x;
		x2end = x2ini + entity2.getBounds().width;
		if (x1ini < x2ini)
			collided = x2ini < x1end;
		else
			collided = x1ini < x2end;
		// We check collision on the Y axis
		float y1ini, y1end, y2ini, y2end;
		y1ini = entity1.getPosition().y;
		y1end = y1ini + entity1.getBounds().height;
		y2ini = entity2.getPosition().y;
		y2end = y2ini + entity2.getBounds().height;
		if (y1ini < y2ini)
			collided &= y2ini < y1end;
		else
			collided &= y1ini < y2end;
		return collided;
	}
	
	/**
	 * Method to check the collision between two Entities using
	 * the bounding box algorithm.
	 * @param entity1: Object that collides
	 * @param entity2: Object that may have collided
	 * @return
	 */
	static public boolean checkCollisionBallSurface(Entity ball, Vector2 pointA, Vector2 pointB) {
		boolean collided = false;
		Vector2 surfaceVector, ballBVector, ballSurfaceVector, massCenter;
		
		// We create the vector defined by the surface 
		// and the ball and one of the surface's points
		surfaceVector = new Vector2(pointB.x-pointA.x,pointB.y-pointA.y);
		massCenter = ball.getPosition().cpy();
		massCenter.x += ball.getBounds().width/2;
		massCenter.y += ball.getBounds().height/2;
		ballBVector = new Vector2(pointB.x-massCenter.x,pointB.y-massCenter.y);
		
		// We get the antiprojection of the vector between the ball and point B
		ballSurfaceVector = vectorAntiProjection(ballBVector, surfaceVector);
		
		// We check that the ball to surface vector is shorter than the radius of the ball
		collided = 2*ballSurfaceVector.len() < ball.getBounds().height;
		
		return collided;
	}
	
	
	/**
	 * Method to get the bouncing direction between a bouncing object
	 * and a bounding box.
	 * @param bouncer
	 * @param block
	 * @return
	 */
	static public Vector2 getBounce(Entity bouncer, Entity block) {
		Vector2 bounceDir = new Vector2(bouncer.getVelocity());
		Vector2 pointA, pointB, v;
		// We get the movement direction for the bouncer
		v = bouncer.getVelocity().cpy();
		/* Now we check the bouncer's direction and look for the	*/
		/* only 2 possible sides where it could bounce				*/
		// First we check the x direction
		if (v.y > 0) {
			// It is moving from bottom to top.
			pointA = block.getPosition().cpy();
			pointB = block.getPosition().cpy();
			pointB.x += block.getBounds().width;
		}
		else {
			// It is moving from top to bottom.
			pointA = block.getPosition().cpy();
			pointA.y += block.getBounds().height;
			pointB = block.getPosition().cpy();
			pointB.x += block.getBounds().width;
			pointB.y += block.getBounds().height;

		}		
		
		// We found the bouncing "wall"
		if (bounceCheck(bouncer,pointA,pointB))
			//bounceDir.y = - bounceDir.y;
			bounceDir = bounceSurface(bounceDir,pointA,pointB);
		
		if (v.x > 0) {
			// It is moving from left to right.
			pointA = block.getPosition().cpy();
			pointB = block.getPosition().cpy();
			pointB.y += block.getBounds().height;
		}
		else {
			// It is moving from right to left.
			pointA = block.getPosition().cpy();
			pointA.x += block.getBounds().width;
			pointB = block.getPosition().cpy();
			pointB.x += block.getBounds().width;
			pointB.y += block.getBounds().height;

		}	
		
		if (bounceCheck(bouncer,pointA,pointB))
			// Otherwise we bounced on the other axis
			//bounceDir.x = - bounceDir.x;
			bounceDir = bounceSurface(bounceDir,pointA,pointB);
		
		return bounceDir;
	}
	/** Method to check if an object is going to bounce with a surface
	 * delimited by the points A and B
	 * @param bouncer: Bouncing object (entity)
	 * @param pointA: Start of the surface
	 * @param pointB: End of the surface
	 * @return true if the surface is on the way of the object, false otherwise
	 */
	static private boolean bounceCheck(Entity bouncer, Vector2 pointA, Vector2 pointB) {
		float m, n, alpha;
		Vector2 massCenter, v;
		
		// We get the movement direction for the bouncer
		v = bouncer.getVelocity().cpy();
		// We get the center of masses of the bouncing object.
		massCenter = bouncer.getPosition().cpy();
		massCenter.x += bouncer.getBounds().width/2;
		massCenter.y += bouncer.getBounds().height/2;
		
		// We compute the surface line equation.
		if ((pointB.x - pointA.x)>0) {
			m = (pointB.y - pointA.y) / (pointB.x - pointA.x);
			n = pointB.y - m*pointB.x;
			// We compute the vector projection from the center of masses
			// of the bouncer to the surface line.
			alpha = (massCenter.y - m*massCenter.x - n)/(m*v.x - v.y);
		}
		else {
			alpha = (pointA.x-massCenter.x)/(v.x);
		}
		
		// If the alpha (projection) is positive, that means the bouncer
		// will collide against the surface. Otherwise the surface is not on the way.
		return (alpha > 0);
	}
	/** Method that returns the direction and velocity
	 * of an object that bounces against a surface defined
	 * by pointA and pointB
	 * @param bouncer: Bouncing entity
	 * @param pointA: Starting point of the surface
	 * @param pointB: Ending point of the surface
	 * @return the bouncing direction
	 */
	static private Vector2 bounceSurface(Vector2 direction, Vector2 pointA, Vector2 pointB) {
		Vector2 newVelocity, normal, projection;
		
		// We get the movement direction for the bouncer
		newVelocity = direction.cpy();
		
		// We the surface's normal
		normal = new Vector2(pointA.y-pointB.y,pointB.x-pointA.x);
		projection = vectorProjection(newVelocity,normal);
		
		// Now we  use the projection to change the vector's direction
		newVelocity.sub(projection.scl(2));		
		
		// If the alpha (projection) is positive, that means the bouncer
		// will collide against the surface. Otherwise the surface is not on the way.
		return newVelocity;
	}
	
	/**
	 * Method to compute the projection of a vector into another
	 * @param vector: Vector to project
	 * @param mainVector: Vector where we project
	 * @return
	 */
	static private Vector2 vectorProjection(Vector2 vector, Vector2 mainVector) {
		Vector2 projection;
		double moduleMain, moduleProjection;
		
		// We normalize the main vector first
		mainVector.limit(1);
		
		// We compute the projection's module
		moduleProjection = mainVector.x*vector.x + mainVector.y*vector.y;
		
		// Finally we compute the projection
		projection = new Vector2((float)moduleProjection*mainVector.x,(float)moduleProjection*mainVector.y);
		
		return projection;
	}
	
	/**
	 * Method to compute the antiprojection of a vector into another
	 * @param vector: Vector to project
	 * @param mainVector: Vector where we project
	 * @return
	 */
	static private Vector2 vectorAntiProjection(Vector2 vector, Vector2 mainVector) {
		Vector2 projection;
		double moduleMain, moduleProjection;
		
		// We normalize the main vector first
		mainVector.limit(1);
		
		// We compute the projection's module
		moduleProjection = -mainVector.y*vector.x + mainVector.x*vector.y;
		
		// Finally we compute the projection
		projection = new Vector2((float)-moduleProjection*mainVector.y,(float)moduleProjection*mainVector.x);
		
		return projection;
	}

}
