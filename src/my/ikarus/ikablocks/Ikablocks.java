package my.ikarus.ikablocks;

import my.ikarus.ikablocks.screens.GameScreen;

import com.badlogic.gdx.Game;

public class Ikablocks extends Game {


	@Override
	public void create() {
        setScreen(new GameScreen(this));
	}

}
