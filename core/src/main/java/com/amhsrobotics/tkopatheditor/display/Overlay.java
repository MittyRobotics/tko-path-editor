package com.amhsrobotics.tkopatheditor.display;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.display.tools.*;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.amhsrobotics.tkopatheditor.util.DragConstants;
import com.amhsrobotics.tkopatheditor.util.ModifiedStage;
import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class Overlay implements Disposable {

    private static Overlay instance;
    private ModifiedStage stage;
    private SpriteBatch overlayBatch;
    private ShapeRenderer overlayShape;

    private TextureAtlas atlas;
    private TextureAtlas atlasAlt;
    private Skin skin;
    private Skin skinAlt;
    private BitmapFont fieldFont;

    public static Overlay getInstance() {
        if(instance == null) {
            instance = new Overlay();
        }
        return instance;
    }

    public void init() {

        overlayBatch = new SpriteBatch();
        overlayShape = new ShapeRenderer();
        stage = new ModifiedStage(CameraManager.getInstance().getHUDViewport(), overlayBatch);

        atlas = new TextureAtlas(Gdx.files.internal(Constants.UI_SKIN));
        skin = new Skin(atlas);

        atlasAlt = new TextureAtlas(Gdx.files.internal(Constants.UI_SKIN_ALT));
        skinAlt = new Skin(atlasAlt);

        fieldFont = UITools.renderFont("font/Abel-Regular.ttf", 18, true);

        UITools.init();
        PropertiesWindow.getInstance().init();

        stage.addActors(
                QuinticSplineTool.getInstance().create(),
                CubicSplineTool.getInstance().create(),
                MeasureTool.getInstance().create(),
                WaypointTool.getInstance().create(),
                ExportTool.getInstance().create(),
                SettingsTool.getInstance().create(),
                HelpTool.getInstance().create(),
                ColorWindow.getInstance().create()
        );
    }

    public void update(float delta) {
        if(DragConstants.measureToolEnabled || DragConstants.waypointToolEnabled) {
            overlayShape.setProjectionMatrix(CameraManager.getInstance().getHUDcamera().getCamera().combined);
            overlayShape.setColor(Constants.MEASURE_TOOL_COLOR);
            overlayShape.begin(ShapeRenderer.ShapeType.Filled);

            overlayShape.rectLine(0, 0, Gdx.graphics.getWidth(), 0, 10);
            overlayShape.rectLine(0, 0, 0, Gdx.graphics.getHeight(), 10);
            overlayShape.rectLine(Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 10);
            overlayShape.rectLine(0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 10);

            overlayShape.end();

            if(DragConstants.measureToolEnabled) {
                MeasureTool.getInstance().render(overlayShape, overlayBatch, fieldFont);
            } else if(DragConstants.waypointToolEnabled) {
                WaypointTool.getInstance().render(overlayShape, overlayBatch, fieldFont);
            }
        }

        if(DragConstants.handleSelected != null || DragConstants.waypointSelected != null) {
            if(!PropertiesWindow.getInstance().isWindowOpen()) {
                PropertiesWindow.getInstance().showProperties();
            }
        } else {
            if(PropertiesWindow.getInstance().isWindowOpen()) {
                PropertiesWindow.getInstance().closeProperties();
            }
        }

        stage.update(delta);
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
        overlayBatch.dispose();
        overlayShape.dispose();
        fieldFont.dispose();
        ColorWindow.getInstance().dispose();
    }
}
