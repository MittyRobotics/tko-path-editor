package com.amhsrobotics.tkopatheditor.display;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.amhsrobotics.tkopatheditor.util.ModifiedStage;
import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.rohanbansal.ricochet.tools.ModifiedShapeRenderer;

public class Overlay implements Disposable {

    private static Overlay instance;
    private ModifiedStage stage;
    private SpriteBatch overlayBatch;
    private ModifiedShapeRenderer sRenderer;

    private TextureAtlas atlas;
    private Skin skin;

    public static Overlay getInstance() {
        if(instance == null) {
            instance = new Overlay();
        }
        return instance;
    }

    public void init() {

        overlayBatch = new SpriteBatch();
        sRenderer = new ModifiedShapeRenderer();
        stage = new ModifiedStage(CameraManager.getInstance().getHUDViewport(), overlayBatch);

        atlas = new TextureAtlas(Gdx.files.internal(Constants.UI_SKIN));
        skin = new Skin(atlas);

        UITools.init();

        stage.addActors(
                QuinticSplineTool.getInstance().create(),
                CubicSplineTool.getInstance().create(),
                MeasureTool.getInstance().create(),
                WaypointTool.getInstance().create(),
                ExportTool.getInstance().create()
        );
    }

    public void update(float delta) {

        stage.update(delta);
    }

    public ModifiedStage getStage() {
        return stage;
    }

    public Skin getSkin() {
        return skin;
    }

    @Override
    public void dispose() {
        atlas.dispose();
        skin.dispose();
    }
}
