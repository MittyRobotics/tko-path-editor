package com.amhsrobotics.tkopatheditor.util;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.display.PropertiesWindow;
import com.amhsrobotics.tkopatheditor.display.tools.MeasureTool;
import com.amhsrobotics.tkopatheditor.parametrics.SplineHandle;
import com.amhsrobotics.tkopatheditor.parametrics.SplineManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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

    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Input.Keys.ESCAPE) {
            DragConstants.resetAll();
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

        if(DragConstants.measureToolEnabled && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

            if(MeasureTool.getInstance().getPoint1() == null) {
                MeasureTool.getInstance().setPoint1(MeasureTool.getInstance().getMouseCursor());
            } else {
                MeasureTool.getInstance().setPoint2(MeasureTool.getInstance().getMouseCursor());
            }

            return false;
        }

        // if not dragging a spline
        if(!DragConstants.draggingHandle && button == Input.Buttons.LEFT) {
            for(SplineHandle h : SplineManager.getInstance().getAllHandles()) {
                // set rotating handle to true since rotation circle is hovered
                if(h.isHoveringRotationCircle()) {
                    if (DragConstants.handleSelected != null) {
                        DragConstants.draggingRotationHandle = true;

                        Vector2 mousePosition = CameraManager.mouseScreenToWorld();
                        DragConstants.draggingFromLeft = mousePosition.x < DragConstants.handleSelected.getPoint().getX();

                    }
                } else if(h.isHoveringHandle()) {
                    DragConstants.draggingHandle = true;
                    DragConstants.handleSelected = h;
                    DragConstants.handleDragged = h;
                    PropertiesWindow.getInstance().setTarget(h);
                }
            }
        }

        // set selection to none if clicking on empty space
        if(DragConstants.handleSelected != null) {
            boolean nonePressed = true;
            for(SplineHandle h : SplineManager.getInstance().getAllHandles()) {
                if(h.isHoveringHandle() || h.isHoveringRotationCircle() || h.isHoveringHandleModifier()) {
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

        if(DragConstants.draggingHandle) {
            DragConstants.draggingHandle = false;
            DragConstants.handleDragged = null;
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
        if(DragConstants.draggingHandle) {
            Vector2 mousePosition = CameraManager.mouseScreenToWorld();
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                DragConstants.handleDragged.setPosition(SnapGrid.calculateSnap(mousePosition));
            } else {
                DragConstants.handleDragged.setPosition(mousePosition);
            }
        } else if(DragConstants.draggingRotationHandle && DragConstants.handleSelected != null) {

            Vector2 mousePosition = CameraManager.mouseScreenToWorld();
            double angle = Math.atan2((mousePosition.x - DragConstants.handleSelected.getPoint().getX()), (mousePosition.y - DragConstants.handleSelected.getPoint().getY()));
//            double dst = new Vector2((float) DragConstants.handleSelected.getPoint().getX(), (float) DragConstants.handleSelected.getPoint().getY()).dst(new Vector2(mousePosition.x, mousePosition.y));

            if(DragConstants.draggingFromLeft) {
                angle = -angle - degrees(90);
            } else {
                angle = -angle + degrees(90);
            }

            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                angle = Math.toRadians(45) * (float) Math.round(angle / Math.toRadians(45));
            }

            DragConstants.handleSelected.setRotation(Math.toDegrees(angle));
        } else {
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                float x = Gdx.input.getDeltaX() * CameraManager.getInstance().getWorldCamera().getCamera().zoom;
                float y = Gdx.input.getDeltaY() * CameraManager.getInstance().getWorldCamera().getCamera().zoom;

                if(CameraManager.getInstance().getWorldCamera().getCamera().zoom > 1.0) {
                    CameraManager.getInstance().getWorldCamera().getCamera().translate(-x * Constants.PAN_AMPLIFIER * 2, y * Constants.PAN_AMPLIFIER * 2);
                } else if(CameraManager.getInstance().getWorldCamera().getCamera().zoom > 0) {
                    CameraManager.getInstance().getWorldCamera().getCamera().translate(-x * Constants.PAN_AMPLIFIER, y * Constants.PAN_AMPLIFIER);
                }
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

        Vector2 worldCoordsBefore = CameraManager.mouseScreenToWorld();

        CameraManager.getInstance().getWorldCamera().getCamera().zoom += amountY * CameraManager.getInstance().getWorldCamera().getCamera().zoom * Constants.ZOOM_AMPLIFIER;
        CameraManager.getInstance().getWorldCamera().update();

        Vector2 worldCoordsAfter = CameraManager.mouseScreenToWorld();

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
