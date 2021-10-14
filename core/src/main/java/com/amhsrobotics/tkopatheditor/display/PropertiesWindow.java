package com.amhsrobotics.tkopatheditor.display;

import com.amhsrobotics.tkopatheditor.parametrics.SplineHandle;
import com.amhsrobotics.tkopatheditor.parametrics.SplineWrapper;
import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class PropertiesWindow {

    private static PropertiesWindow instance;
    private Table window;

    private boolean isWindowOpen = false;

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
        resetPosition();
    }

    public void resetPosition() {
        window.setPosition(Gdx.graphics.getWidth() - 325, 20);
    }

    public boolean isWindowOpen() {
        return isWindowOpen;
    }

    public void showProperties(SplineHandle handle) {
        isWindowOpen = true;
        Overlay.getInstance().getStage().addActors(window);
        UITools.slideIn(window, "bottom", 0.5f, Interpolation.exp5, 300, new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    public void showProperties(SplineWrapper spline) {
        isWindowOpen = true;
    }

    public void closeProperties() {
        isWindowOpen = false;
        UITools.slideOut(window, "bottom", 0.5f, Interpolation.exp5, 300, new Runnable() {
            @Override
            public void run() {
                window.remove();
                resetPosition();
            }
        });
    }

}
