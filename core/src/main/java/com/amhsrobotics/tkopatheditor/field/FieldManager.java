package com.amhsrobotics.tkopatheditor.field;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

// 283.98 in x 685.5 in
public class FieldManager implements Disposable {

    private static FieldManager instance;

    private SpriteBatch batch;
    private ShapeRenderer renderer;
    private BitmapFont font;
    private Sprite fieldImage;

    public static FieldManager getInstance() {
        if(instance == null) {
            instance = new FieldManager();
        }
        return instance;
    }

    public void init() {
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        font = UITools.renderFont("font/Abel-Regular.ttf", 18, true);
        fieldImage = new Sprite(new Texture(Gdx.files.internal("png/field.png")));
        fieldImage.setOriginCenter();
        fieldImage.setOriginBasedPosition(Constants.WORLD_DIMENSIONS.x / 2, Constants.WORLD_DIMENSIONS.y / 2);

        FieldConstants.init(fieldImage);
    }

    public void render() {

        batch.setProjectionMatrix(CameraManager.getInstance().getWorldCamera().getCamera().combined);
        renderer.setProjectionMatrix(CameraManager.getInstance().getWorldCamera().getCamera().combined);

        batch.begin();
        fieldImage.draw(batch);
        batch.end();

        WaypointManager.getInstance().render(renderer, batch, font);
    }



    public Sprite getFieldSprite() {
        return fieldImage;
    }

    @Override
    public void dispose() {
        batch.dispose();
        renderer.dispose();
    }
}
