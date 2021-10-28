package com.amhsrobotics.tkopatheditor.display.tools;

import com.amhsrobotics.tkopatheditor.blueprints.ToolButton;
import com.amhsrobotics.tkopatheditor.display.ColorWindow;
import com.amhsrobotics.tkopatheditor.display.Overlay;
import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class ExportTool implements ToolButton {

    private static ExportTool instance;

    TextButton button;

    private Window window;

    private boolean windowOpen = false;

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

        window = new Window("Export", UITools.windowStyle);
        window.setSize(500, 400);
        window.setKeepWithinStage(false);
        window.setMovable(false);
        window.setPosition(((float) Gdx.graphics.getWidth() / 2) - 250, -450);
        Overlay.getInstance().getStage().addActor(window);

        return button;
    }

    public void open() {
        windowOpen = true;
        window.setPosition(((float) Gdx.graphics.getWidth() / 2) - 250, 200);
        UITools.slideIn(window, "bottom", 1f, Interpolation.exp10, 450);

        if(SettingsTool.getInstance().isWindowOpen()) {
            SettingsTool.getInstance().close();
        }
        if(HelpTool.getInstance().isWindowOpen()) {
            HelpTool.getInstance().close();
        }
    }

    public void close() {
        windowOpen = false;
        UITools.slideOut(window, "bottom", 1f,Interpolation.exp10, 450);
    }

    public boolean isWindowOpen() {
        return windowOpen;
    }

    public void toggle() {
        windowOpen = !windowOpen;
        if(windowOpen) {
            open();
        } else {
            close();
        }
    }

    @Override
    public void onClick() {
        toggle();
    }


}
