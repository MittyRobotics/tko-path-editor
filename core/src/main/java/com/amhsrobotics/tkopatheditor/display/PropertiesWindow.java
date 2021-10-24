package com.amhsrobotics.tkopatheditor.display;

import com.amhsrobotics.tkopatheditor.field.Waypoint;
import com.amhsrobotics.tkopatheditor.field.WaypointManager;
import com.amhsrobotics.tkopatheditor.parametrics.SplineHandle;
import com.amhsrobotics.tkopatheditor.parametrics.SplineManager;
import com.amhsrobotics.tkopatheditor.util.DragConstants;
import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.text.DecimalFormat;

public class PropertiesWindow {

    private static PropertiesWindow instance;
    private Table window;
    private ScrollPane scroll;

    private boolean isWindowOpen = false;

    private SplineHandle currentHandle;
    private Waypoint currentWaypoint;

    private DecimalFormat format = new DecimalFormat("##.00");

    public static PropertiesWindow getInstance() {
        if(instance == null) {
            instance = new PropertiesWindow();
        }
        return instance;
    }

    public void init() {
        window = new Table();
        window.setBackground(Overlay.getInstance().getSkinAlt().getDrawable("textbox_01"));
        window.setWidth(300);
        window.setHeight(250);

        scroll = new ScrollPane(window, UITools.scrollStyle);
        scroll.setScrollingDisabled(true, false);
        scroll.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Overlay.getInstance().getStage().setScrollFocus(scroll);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Overlay.getInstance().getStage().setScrollFocus(null);
            }
        });

        HandleProperties.getInstance().init(window);

        resetPosition();
    }

    public void resetPosition() {
        window.setPosition(Gdx.graphics.getWidth() - 325, 20);
    }

    public boolean isWindowOpen() {
        return isWindowOpen;
    }

    public void setTarget(SplineHandle handle) {
        currentHandle = handle;
        HandleProperties.getInstance().reset();

        HandleProperties.getInstance().setName(handle.getSpline().getType().toString().replace("_", " ") + " " + handle.getSpline().getID());
        HandleProperties.getInstance().setSubName("Point " + handle.getId());
        HandleProperties.getInstance().addLabel(format.format(handle.getPoint().getX()) + ", " + format.format(handle.getPoint().getY()));
        HandleProperties.getInstance().addLabel(format.format(Math.toDegrees(handle.getPoint().getRotation().getRadians())));
        HandleProperties.getInstance().addButton("Delete Spline", () -> {
            SplineManager.getInstance().deleteSpline(handle.getSpline());
            DragConstants.resetAll();
        });
    }

    public void setTarget(Waypoint waypoint) {
        currentWaypoint = waypoint;
        HandleProperties.getInstance().reset();

        HandleProperties.getInstance().setName("Waypoint " + waypoint.getId());
        HandleProperties.getInstance().addButton("Delete Waypoint", () -> {
            WaypointManager.getInstance().deleteWaypoint(waypoint);
            DragConstants.resetAll();
        });
        HandleProperties.getInstance().addButton("Set Zero", () -> {
            waypoint.setAsZero();
        });
    }

    public void showProperties() {
        isWindowOpen = true;
        resetPosition();
        Overlay.getInstance().getStage().addActors(window);
        UITools.slideIn(window, "bottom", 0.5f, Interpolation.exp5, 300, () -> {});
    }

    public void closeProperties() {
        isWindowOpen = false;
        currentWaypoint = null;
        currentHandle = null;
        UITools.slideOut(window, "bottom", 0.5f, Interpolation.exp5, 300, () -> {
            window.remove();
            resetPosition();
        });
    }
}
