package com.amhsrobotics.tkopatheditor.util;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.parametrics.SplineHandle;
import com.amhsrobotics.tkopatheditor.parametrics.SplineManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class InputCore implements InputProcessor {

    private static InputCore instance;

    public static InputCore getInstance() {
        if(instance == null) {
            instance = new InputCore();
        }
        return instance;
    }

    public void init() {
        CameraManager.getInstance().getWorldCamera().smoothZoomTo(0.6f, 1.5f, Interpolation.exp5);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if(!DragConstants.draggingSpline) {
            for(SplineHandle h : SplineManager.getInstance().getAllHandles()) {
                if(h.isHovering()) {
                    DragConstants.draggingSpline = true;
                    DragConstants.draggingHandle = h;
                }
            }
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if(DragConstants.draggingSpline) {
            DragConstants.draggingSpline = false;
            DragConstants.draggingHandle = null;
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(DragConstants.draggingSpline) {
            Vector2 mousePosition = CameraManager.mouseScreenToWorld(CameraManager.getInstance().getWorldCamera());
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                DragConstants.draggingHandle.setPosition(SnapGrid.calculateSnap(mousePosition));
            } else {
                DragConstants.draggingHandle.setPosition(mousePosition);
            }
        } else {
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                float x = Gdx.input.getDeltaX() * CameraManager.getInstance().getWorldCamera().getCamera().zoom;
                float y = Gdx.input.getDeltaY() * CameraManager.getInstance().getWorldCamera().getCamera().zoom;

                CameraManager.getInstance().getWorldCamera().getCamera().translate(-x * Constants.PAN_AMPLIFIER, y * Constants.PAN_AMPLIFIER);
            }
        }


        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {

        Vector2 worldCoordsBefore = CameraManager.mouseScreenToWorld(CameraManager.getInstance().getWorldCamera());

        CameraManager.getInstance().getWorldCamera().getCamera().zoom += amountY * CameraManager.getInstance().getWorldCamera().getCamera().zoom * Constants.ZOOM_AMPLIFIER;
        CameraManager.getInstance().getWorldCamera().update();

        Vector2 worldCoordsAfter = CameraManager.mouseScreenToWorld(CameraManager.getInstance().getWorldCamera());

        Vector3 diff = new Vector3(worldCoordsAfter, 0).sub(new Vector3(worldCoordsBefore, 0));

        CameraManager.getInstance().getWorldCamera().getCamera().position.sub(diff);
        CameraManager.getInstance().getWorldCamera().update();

        if (CameraManager.getInstance().getWorldCamera().getCamera().zoom > Constants.MAX_MAP_ZOOM) {
            CameraManager.getInstance().getWorldCamera().getCamera().zoom = Constants.MAX_MAP_ZOOM;
        } else if (CameraManager.getInstance().getWorldCamera().getCamera().zoom < Constants.MIN_MAP_ZOOM) {
            CameraManager.getInstance().getWorldCamera().getCamera().zoom = Constants.MIN_MAP_ZOOM;
        }

        return true;
    }
}
