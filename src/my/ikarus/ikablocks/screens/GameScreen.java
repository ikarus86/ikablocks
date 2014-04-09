package my.ikarus.ikablocks.screens;

import my.ikarus.ikablocks.Ikablocks;
import my.ikarus.ikablocks.controller.WorldController;
import my.ikarus.ikablocks.model.World;
import my.ikarus.ikablocks.view.WorldRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

public class GameScreen implements Screen, InputProcessor {
	
	private Ikablocks game;
	
	private World world;
	private WorldRenderer renderer;
	private WorldController controller;
	
	public GameScreen(Ikablocks game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		controller.update(delta);
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
	}

	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world, false);
		controller = new WorldController(world);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);

	}
	
	// * InputProcessor methods *//
	 
	    @Override
	    public boolean keyDown(int keycode) {
	        if (keycode == Keys.LEFT)
	            controller.leftPressed();
	        if (keycode == Keys.RIGHT)
	            controller.rightPressed();
	        return true;
	    }
	 
	    @Override
	    public boolean keyUp(int keycode) {
	        if (keycode == Keys.LEFT)
	            controller.leftReleased();
	        if (keycode == Keys.RIGHT)
	            controller.rightReleased();
	        return true;
	    }
	 
	    @Override
	    public boolean keyTyped(char character) {
	        // TODO Auto-generated method stub
	        return false;
	    }
	 
	    @Override
	    public boolean touchDown(int x, int y, int pointer, int button) {
	    	controller.focusPressed(x, y);
	        return true;
	    }
	 
	    @Override
	    public boolean touchUp(int x, int y, int pointer, int button) {
	    	controller.focusReleased(x, y);
	        return true;
	    }
	 
	    @Override
	    public boolean touchDragged(int x, int y, int pointer) {
	    	controller.focusPressed(x, y);
	        return true;
	    }
	 
	    @Override
	    public boolean mouseMoved(int x, int y) {
	        // TODO Auto-generated method stub
	        return false;
	    }
	 
	    @Override
	    public boolean scrolled(int amount) {
	        // TODO Auto-generated method stub
	        return false;
	    }


}
