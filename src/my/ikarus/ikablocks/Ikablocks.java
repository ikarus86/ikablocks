package my.ikarus.ikablocks;

import my.ikarus.ikablocks.screens.*;

import com.badlogic.gdx.Game;

public class Ikablocks extends Game {


	@Override
	public void create() {
		setScreen(new MainMenuScreen(this));
	}

}
