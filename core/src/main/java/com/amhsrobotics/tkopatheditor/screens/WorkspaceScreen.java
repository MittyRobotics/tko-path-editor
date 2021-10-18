package com.amhsrobotics.tkopatheditor.screens;

import com.amhsrobotics.tkopatheditor.display.Overlay;
import com.amhsrobotics.tkopatheditor.field.FieldManager;
import com.amhsrobotics.tkopatheditor.parametrics.SplineManager;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.amhsrobotics.tkopatheditor.util.input.InputCore;
import com.amhsrobotics.tkopatheditor.util.SnapGrid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import static com.amhsrobotics.tkopatheditor.Constants.BACKGROUND_COLOR;

public class WorkspaceScreen implements Screen {


	public WorkspaceScreen() {
		CameraManager.getInstance().init();
		Overlay.getInstance().init();
		SplineManager.getInstance().init();
		FieldManager.getInstance().init();

		InputCore.getInstance();

		Gdx.input.setInputProcessor(new InputMultiplexer(
				Overlay.getInstance().getStage(),
				InputCore.getInstance(),
				InputCore.getMultipleKeyProcessor()
		));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b, BACKGROUND_COLOR.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		CameraManager.getInstance().update();


		if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			SnapGrid.renderGrid(CameraManager.getInstance().getWorldCamera());
		}
		FieldManager.getInstance().render();

		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			SnapGrid.renderGrid(CameraManager.getInstance().getWorldCamera());
		}
		SplineManager.getInstance().render(delta);

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
		SplineManager.getInstance().dispose();
		FieldManager.getInstance().dispose();
	}
}