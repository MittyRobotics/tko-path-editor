package com.amhsrobotics.tkopatheditor;

import com.amhsrobotics.tkopatheditor.screens.WorkspaceScreen;
import com.badlogic.gdx.Game;

public class Main extends Game {

	@Override
	public void create() {
		setScreen(new WorkspaceScreen());
	}
}