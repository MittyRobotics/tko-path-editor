package com.amhsrobotics.tkopatheditor.display;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.display.tools.*;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.amhsrobotics.tkopatheditor.util.DragConstants;
import com.amhsrobotics.tkopatheditor.util.ModifiedStage;
import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class Overlay implements Disposable {

    private static Overlay instance;
    private ModifiedStage stage;
    private SpriteBatch overlayBatch;

    private TextureAtlas atlas;
    private TextureAtlas atlasAlt;
    private Skin skin;
    private Skin skinAlt;

    public static Overlay getInstance() {
        if(instance == null) {
            instance = new Overlay();
        }
        return instance;
    }

    public void init() {

        overlayBatch = new SpriteBatch();
        stage = new ModifiedStage(CameraManager.getInstance().getHUDViewport(), overlayBatch);

        atlas = new TextureAtlas(Gdx.files.internal(Constants.UI_SKIN));
        skin = new Skin(atlas);

        atlasAlt = new TextureAtlas(Gdx.files.internal(Constants.UI_SKIN_ALT));
        skinAlt = new Skin(atlasAlt);

        UITools.init();
        PropertiesWindow.getInstance().init();

        stage.addActors(
                QuinticSplineTool.getInstance().create(),
                CubicSplineTool.getInstance().create(),
                MeasureTool.getInstance().create(),
                WaypointTool.getInstance().create(),
                ExportTool.getInstance().create(),
                SettingsTool.getInstance().create(),
                HelpTool.getInstance().create()
        );
    }

    public void update(float delta) {
        stage.update(delta);

        if(DragConstants.handleSelected != null) {
            if(!PropertiesWindow.getInstance().isWindowOpen()) {
                PropertiesWindow.getInstance().resetPosition();
                PropertiesWindow.getInstance().showProperties(DragConstants.handleSelected);
            }
        } else {
            if(PropertiesWindow.getInstance().isWindowOpen()) {
                PropertiesWindow.getInstance().closeProperties();
            }
        }
    }

    public ModifiedStage getStage() {
        return stage;
    }

    public Skin getSkin() {
        return skin;
    }

    public Skin getSkinAlt() {
        return skinAlt;
    }

    @Override
    public void dispose() {
        atlas.dispose();
        atlasAlt.dispose();
        skin.dispose();
        skinAlt.dispose();
    }
}
