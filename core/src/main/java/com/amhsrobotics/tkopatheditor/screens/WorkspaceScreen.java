package com.amhsrobotics.tkopatheditor.screens;

import com.amhsrobotics.tkopatheditor.display.Overlay;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.amhsrobotics.tkopatheditor.util.InputCore;
import com.amhsrobotics.tkopatheditor.util.SnapGrid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import static com.amhsrobotics.tkopatheditor.Constants.BACKGROUND_COLOR;

public class WorkspaceScreen implements Screen {


	public WorkspaceScreen() {
		CameraManager.getInstance().init();
		InputCore.getInstance().init();
		Overlay.getInstance().init();

		Gdx.input.setInputProcessor(new InputMultiplexer(
				Overlay.getInstance().getStage(),
				InputCore.getInstance()
		));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b, BACKGROUND_COLOR.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		CameraManager.getInstance().update();

		SnapGrid.renderGrid(CameraManager.getInstance().getWorldCamera());

		Overlay.getInstance().update(delta);
	}


	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {
		CameraManager.getInstance().getWorldViewport().update(width, height);
		CameraManager.getInstance().getHUDViewport().update(width, height);
		// Resize your screen here. The parameters represent the new window size.
	}

	@Override
	public void pause() {
		// Invoked when your application is paused.
	}

	@Override
	public void resume() {
		// Invoked when your application is resumed after pause.
	}

	@Override
	public void hide() {
		// This method is called when another screen replaces this one.
	}

	@Override
	public void dispose() {
		Overlay.getInstance().dispose();
	}
}