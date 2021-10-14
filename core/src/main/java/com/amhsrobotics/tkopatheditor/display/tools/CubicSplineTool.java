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

public class CubicSplineTool implements ToolButton {

    private static CubicSplineTool instance;

    TextButton button;

    public static CubicSplineTool getInstance() {
        if(instance == null) {
            instance = new CubicSplineTool();
        }
        return instance;
    }

    @Override
    public TextButton create() {
        button = new TextButton("Cubic Hermite", UITools.textButtonStyle);
        button.setPosition(190, Gdx.graphics.getHeight() - 60);
        button.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        CubicSplineTool.getInstance().onClick();
                    }
                }
        );
        button.addListener(
                new TextTooltip("Create a cubic hermite spline path." ,UITools.tooltipStyle)
        );

        return button;
    }

    @Override
    public void onClick() {
        SplineManager.getInstance().registerSpline(
                new SplineWrapper(SplineType.CUBIC_HERMITE)
        );
    }


}
