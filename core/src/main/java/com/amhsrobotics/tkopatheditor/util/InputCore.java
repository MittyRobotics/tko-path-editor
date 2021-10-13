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

import static com.github.mittyrobotics.core.math.units.ConversionsKt.degrees;


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

        if(DragConstants.handleSelected != null) {
            if(keycode == Input.Keys.ESCAPE) {
                DragConstants.handleSelected = null;
                DragConstants.draggingRotationHandle = false;
            }
        }

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

        // if not dragging a spline
        if(!DragConstants.draggingSpline && button == Input.Buttons.LEFT) {
            for(SplineHandle h : SplineManager.getInstance().getAllHandles()) {
                // set rotating handle to true since rotation circle is hovered
                if(h.isHoveringRotationCircle()) {
                    DragConstants.draggingRotationHandle = true;

                    Vector2 mousePosition = CameraManager.mouseScreenToWorld(CameraManager.getInstance().getWorldCamera());
                    DragConstants.draggingFromLeft = mousePosition.x < DragConstants.handleSelected.getPoint().getX();

                    // set dragging/selected handle to true since inner circle hovered
                } else if(h.isHoveringHandle()) {
                    DragConstants.draggingSpline = true;
                    DragConstants.handleSelected = h;
                    DragConstants.draggingHandle = h;
                }
            }
        }

        // set selection to none if clicking on empty space
        if(DragConstants.handleSelected != null) {
            boolean nonePressed = true;
            for(SplineHandle h : SplineManager.getInstance().getAllHandles()) {
                if(h.isHoveringHandle() || h.isHoveringRotationCircle()) {
                    nonePressed = false;
                }
            }
            if(nonePressed) {
                DragConstants.handleSelected = null;
                DragConstants.draggingRotationHandle = false;
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

        if(DragConstants.handleSelected != null) {
            if(DragConstants.draggingRotationHandle) {
                DragConstants.draggingRotationHandle = false;
            }
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(DragConstants.draggingSpline) {
            Vector2 mousePosition = CameraManager.mouseScreenToWorld(CameraManager.getInstance().getWorldCamera());
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                DragConstants.draggingHandle.setPosition(SnapGrid.calculateSnap(mousePosition));
            } else {
                DragConstants.draggingHandle.setPosition(mousePosition);
            }
        } else if(DragConstants.draggingRotationHandle && DragConstants.handleSelected != null) {

            Vector2 mousePosition = CameraManager.mouseScreenToWorld(CameraManager.getInstance().getWorldCamera());
            double angle = Math.atan2((mousePosition.x - DragConstants.handleSelected.getPoint().getX()), (mousePosition.y - DragConstants.handleSelected.getPoint().getY()));

            if(DragConstants.draggingFromLeft) {
                angle = -angle - degrees(90);
            } else {
                angle = -angle + degrees(90);
            }

            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                angle = Math.toRadians(45) * (float) Math.round(angle / Math.toRadians(45));
            }
//            System.out.println(Math.toDegrees(angle));
            DragConstants.handleSelected.setRotation(Math.toDegrees(angle));
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
