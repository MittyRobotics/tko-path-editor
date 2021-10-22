package com.amhsrobotics.tkopatheditor.display.tools;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.blueprints.ToolButton;
import com.amhsrobotics.tkopatheditor.field.FieldConstants;
import com.amhsrobotics.tkopatheditor.field.Waypoint;
import com.amhsrobotics.tkopatheditor.field.WaypointManager;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.amhsrobotics.tkopatheditor.util.DragConstants;
import com.amhsrobotics.tkopatheditor.util.SnapGrid;
import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class WaypointTool implements ToolButton {

    private static WaypointTool instance;

    TextButton button;

    private Vector2 mouseCursor = new Vector2();


    public static WaypointTool getInstance() {
        if(instance == null) {
            instance = new WaypointTool();
        }
        return instance;
    }

    @Override
    public TextButton create() {
        button = new TextButton("W", UITools.textButtonStyle);
        button.setPosition(480, Gdx.graphics.getHeight() - 60);
        button.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        WaypointTool.getInstance().onClick();
                    }
                }
        );
        button.addListener(
                new TextTooltip("Create a waypoint on the field." ,UITools.tooltipStyle)
        );

        return button;
    }

    public void render(ShapeRenderer renderer, SpriteBatch batch, BitmapFont font) {

        renderer.setProjectionMatrix(CameraManager.getInstance().getWorldCamera().getCamera().combined);


        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        mouseCursor = CameraManager.mouseScreenToWorld();
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            mouseCursor = SnapGrid.calculateSnap(mouseCursor);
        }

        renderer.setColor(Constants.HANDLE_ROTATION_COLOR);
        renderer.circle(mouseCursor.x, mouseCursor.y, Constants.MEASURE_TOOL_RADIUS);

        renderer.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    public Vector2 getMouseCursor() {
        return mouseCursor;
    }

    @Override
    public void onClick() {
        if(DragConstants.waypointToolEnabled) {
            DragConstants.waypointToolEnabled = false;
        } else {
            DragConstants.resetAll();
            DragConstants.waypointToolEnabled = true;
        }
    }


    public Waypoint createWaypoint(Vector2 mouseCursor) {
        Vector2 inches = FieldConstants.pixelToRelativeInches(mouseCursor);
        return WaypointManager.getInstance().addWaypointInches(inches.x, inches.y);
    }
}
