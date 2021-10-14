package com.amhsrobotics.tkopatheditor.display.tools;

import com.amhsrobotics.tkopatheditor.blueprints.ToolButton;
import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SettingsTool implements ToolButton {

    private static SettingsTool instance;

    TextButton button;

    public static SettingsTool getInstance() {
        if(instance == null) {
            instance = new SettingsTool();
        }
        return instance;
    }

    @Override
    public TextButton create() {
        button = new TextButton("Settings", UITools.textButtonStyle);
        button.setPosition(Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 60);
        button.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        SettingsTool.getInstance().onClick();
                    }
                }
        );
        button.addListener(
                new TextTooltip("Editor Settings" ,UITools.tooltipStyle)
        );

        return button;
    }

    @Override
    public void onClick() {

    }


}
