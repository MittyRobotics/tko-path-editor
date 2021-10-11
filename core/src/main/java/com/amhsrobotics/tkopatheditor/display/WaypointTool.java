package com.amhsrobotics.tkopatheditor.display;

import com.amhsrobotics.tkopatheditor.blueprints.ToolButton;
import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class WaypointTool implements ToolButton {

    private static WaypointTool instance;

    TextButton button;

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

    @Override
    public void onClick() {

    }


}
