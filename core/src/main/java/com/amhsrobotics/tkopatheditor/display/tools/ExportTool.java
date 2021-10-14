package com.amhsrobotics.tkopatheditor.display.tools;

import com.amhsrobotics.tkopatheditor.blueprints.ToolButton;
import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class ExportTool implements ToolButton {

    private static ExportTool instance;

    TextButton button;

    public static ExportTool getInstance() {
        if(instance == null) {
            instance = new ExportTool();
        }
        return instance;
    }

    @Override
    public TextButton create() {
        button = new TextButton("Export", UITools.textButtonStyle);
        button.setPosition(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 60);
        button.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        ExportTool.getInstance().onClick();
                    }
                }
        );
        button.addListener(
                new TextTooltip("Export Path" ,UITools.tooltipStyle)
        );

        return button;
    }

    @Override
    public void onClick() {

    }


}
