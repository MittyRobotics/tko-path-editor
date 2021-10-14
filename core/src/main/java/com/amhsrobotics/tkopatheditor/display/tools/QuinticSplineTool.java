package com.amhsrobotics.tkopatheditor.display.tools;

import com.amhsrobotics.tkopatheditor.blueprints.ToolButton;
import com.amhsrobotics.tkopatheditor.parametrics.SplineManager;
import com.amhsrobotics.tkopatheditor.parametrics.SplineType;
import com.amhsrobotics.tkopatheditor.parametrics.SplineWrapper;
import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class QuinticSplineTool implements ToolButton {

    private static QuinticSplineTool instance;

    TextButton button;

    public static QuinticSplineTool getInstance() {
        if(instance == null) {
            instance = new QuinticSplineTool();
        }
        return instance;
    }

    @Override
    public TextButton create() {
        button = new TextButton("Quintic Hermite", UITools.textButtonStyle);
        button.setPosition(30, Gdx.graphics.getHeight() - 60);
        button.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        QuinticSplineTool.getInstance().onClick();
                    }
                }
        );
        button.addListener(
                new TextTooltip("Create a quintic hermite spline path." ,UITools.tooltipStyle)
        );

        return button;
    }

    @Override
    public void onClick() {
        SplineManager.getInstance().registerSpline(
                new SplineWrapper(SplineType.QUINTIC_HERMITE)
        );
    }


}
