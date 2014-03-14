package my.ikarus.ikablocks.view;

import my.ikarus.engine.Entity;
import my.ikarus.ikablocks.model.World;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {
 
    private static final float CAMERA_WIDTH = 10f;
    private static final float CAMERA_HEIGHT = 7f;
    //private static final float RUNNING_FRAME_DURATION = 0.06f;

 
    private World world;
    private OrthographicCamera cam;
 
    /** Debug rendering **/
    ShapeRenderer debugRenderer = new ShapeRenderer();
	
	/** Animations **/
	//private Animation barAnimation;
 
    private SpriteBatch spriteBatch;
    private boolean debug = false;
    private int width;
    private int height;
    private float ppuX; // pixels per unit on the X axis
    private float ppuY; // pixels per unit on the Y axis
	
	
	
    public void setSize (int w, int h) {
        this.width = w;
        this.height = h;
        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;
        world.setPpuX(ppuX);
        world.setPpuY(ppuY);
    }
 
    public WorldRenderer(World world, boolean debug) {
        this.world = world;
        world.setUnitsX(CAMERA_WIDTH);
        world.setUnitsY(CAMERA_HEIGHT);
        this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
        this.cam.update();
        this.debug = debug;
        spriteBatch = new SpriteBatch();
    }
 
    public void render() {
        spriteBatch.begin();
            draw();
        spriteBatch.end();
        if (debug)
            drawDebug();
    }
    
    private void draw() {
    	try {
			for (Entity entity : world.getEntities()) {
				if (entity.isVisible())
					spriteBatch.draw(entity.getTexture(), entity.getPosition().x * ppuX, entity.getPosition().y * ppuY, entity.getBounds().width * ppuX, entity.getBounds().height * ppuY);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
    private void drawDebug() {
        // Render the bounding boxes
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeType.Line);
        for (Entity entity : world.getEntities()) {
        	if (entity.isVisible()) {
	            Rectangle rect = entity.getBounds();
	            float x1 = entity.getPosition().x + rect.x;
	            float y1 = entity.getPosition().y + rect.y;
	            if (entity.isCollided())
	            	debugRenderer.setColor(new Color(1, 0, 0, 1));
	            else
	            	debugRenderer.setColor(new Color(0, 1, 0, 1));
	            debugRenderer.rect(x1, y1, rect.width, rect.height);
        	}
        }
        debugRenderer.end();
    }
}
