package com.amhsrobotics.tkopatheditor.display.tools;

import com.amhsrobotics.tkopatheditor.blueprints.ToolButton;
import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MeasureTool implements ToolButton {

    private static MeasureTool instance;

    TextButton button;

    public static MeasureTool getInstance() {
        if(instance == null) {
            instance = new MeasureTool();
        }
        return instance;
    }

    @Override
    public TextButton create() {
        button = new TextButton("M", UITools.textButtonStyle);
        button.setPosition(400, Gdx.graphics.getHeight() - 60);
        button.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        MeasureTool.getInstance().onClick();
                    }
                }
        );
        button.addListener(
                new TextTooltip("Measure distance between two points." ,UITools.tooltipStyle)
        );

        return button;
    }

    @Override
    public void onClick() {

    }


}
