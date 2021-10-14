package com.amhsrobotics.tkopatheditor.display.tools;

import com.amhsrobotics.tkopatheditor.blueprints.ToolButton;
import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class HelpTool implements ToolButton {

    private static HelpTool instance;

    TextButton button;

    public static HelpTool getInstance() {
        if(instance == null) {
            instance = new HelpTool();
        }
        return instance;
    }

    @Override
    public TextButton create() {
        button = new TextButton("Help", UITools.textButtonStyle);
        button.setPosition(Gdx.graphics.getWidth() - 280, Gdx.graphics.getHeight() - 60);
        button.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        HelpTool.getInstance().onClick();
                    }
                }
        );
        button.addListener(
                new TextTooltip("View a tutorial, keybinds" ,UITools.tooltipStyle)
        );

        return button;
    }

    @Override
    public void onClick() {

    }


}
