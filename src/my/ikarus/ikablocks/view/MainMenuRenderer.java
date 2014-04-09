package my.ikarus.ikablocks.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class MainMenuRenderer {
    private static final float CAMERA_WIDTH = 10f;
    private static final float CAMERA_HEIGHT = 7f;
 
    private OrthographicCamera cam;
    
    private int width;
    private int height;
    
    /** Initial rendering **/
    ShapeRenderer mainRenderer = new ShapeRenderer();
    
    public MainMenuRenderer() {
    	this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
        this.cam.update();
    }
    
    public void setSize (int w, int h) {
        this.width = w;
        this.height = h;
    }
    
    public void render() {
    	mainRenderer.setProjectionMatrix(cam.combined);
    	mainRenderer.begin(ShapeType.Filled);
        mainRenderer.setColor(new Color(0, 1, 0, 1));
	    mainRenderer.rect(0, 0, width, height);
        mainRenderer.end();
    }
}
