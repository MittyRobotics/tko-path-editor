package com.amhsrobotics.tkopatheditor.display;

import com.amhsrobotics.tkopatheditor.display.tools.ExportTool;
import com.amhsrobotics.tkopatheditor.display.tools.HelpTool;
import com.amhsrobotics.tkopatheditor.util.DragConstants;
import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;

public class ColorWindow implements Disposable {

    private static ColorWindow instance;

    private Window window;
    private boolean windowOpen = false;

    private Image colorwheel;
    private Texture colorwheelImage;
    private Pixmap colorWheelPixmap;

    private boolean hovered = false;

    public static ColorWindow getInstance() {
        if(instance == null) {
            instance = new ColorWindow();
        }
        return instance;
    }

    public Window create() {
        window = new Window("", UITools.windowStyleAlt);
        window.setSize(300, 250);
        window.setKeepWithinStage(false);
        window.setMovable(false);
        window.setPosition((float) Gdx.graphics.getWidth() + 350, 300);

        colorwheelImage = new Texture(Gdx.files.internal("png/colorwheel.png"));
        colorwheelImage.getTextureData().prepare();
        colorWheelPixmap = colorwheelImage.getTextureData().consumePixmap();

        colorwheel = new Image(colorwheelImage);
        colorwheel.setPosition((float) (300.0/2 - colorwheel.getWidth() / 2), (float) (250.0/2 - colorwheel.getHeight() / 2));
        window.addActor(colorwheel);

        colorwheel.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hovered = true;

                Vector2 localCoords = colorwheel.screenToLocalCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                int localX = (int) localCoords.x;
                int localY = (int) localCoords.y;

                if(DragConstants.handleSelected != null) {
                    DragConstants.handleSelected.getSpline().setColor(new Color(colorWheelPixmap.getPixel(localX, (int) colorwheel.getHeight() - localY)));
                }

                return false;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                hovered = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                hovered = false;
            }
        });

        return window;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void open() {
        windowOpen = true;
        window.setPosition((float) Gdx.graphics.getWidth() - 325, 300);
        UITools.slideIn(window, "right", 1f, Interpolation.exp10, 300);

        if(ExportTool.getInstance().isWindowOpen()) {
            ExportTool.getInstance().close();
        }
        if(HelpTool.getInstance().isWindowOpen()) {
            HelpTool.getInstance().close();
        }
    }

    public void close() {
        windowOpen = false;
        UITools.slideOut(window, "right", 1f,Interpolation.exp10, 300);
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
    public void dispose() {
        colorWheelPixmap.dispose();
        colorwheelImage.dispose();
    }
}
