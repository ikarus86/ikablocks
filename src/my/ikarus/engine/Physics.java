package my.ikarus.engine;

import com.badlogic.gdx.math.Vector2;


public class Physics {
	
	static public boolean checkCollision(Entity entity1, Entity entity2) {
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
	// Bouncing for bounding boxes
	// Better bouncing between an entity and a surface should be implemented
	// Also surface bouncing check
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
		Vector2 newVelocity, normal;
		double normalLength, projection;
		
		// We get the movement direction for the bouncer
		newVelocity = direction.cpy();
		
		// We the surface's normal
		normal = new Vector2(pointA.y-pointB.y,pointB.x-pointA.x);
		normalLength = Math.sqrt(normal.x*normal.x + normal.y*normal.y);
		normal.x /= normalLength;
		normal.y /= normalLength;
		
		// We compute the projection of the velocity to the normal vector
		projection = normal.x*newVelocity.x + normal.y*newVelocity.y;
		
		// Now we  use the projection to change the vector's direction
		newVelocity.x -= 2*projection*normal.x;
		newVelocity.y -= 2*projection*normal.y;
		
		
		// If the alpha (projection) is positive, that means the bouncer
		// will collide against the surface. Otherwise the surface is not on the way.
		return newVelocity;
	}

}
