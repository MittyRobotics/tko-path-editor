package com.amhsrobotics.tkopatheditor.field;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class FieldManager implements Disposable {

    private static FieldManager instance;

    private SpriteBatch batch;
    private Sprite fieldImage;

    public static FieldManager getInstance() {
        if(instance == null) {
            instance = new FieldManager();
        }
        return instance;
    }

    public void init() {
        batch = new SpriteBatch();
        fieldImage = new Sprite(new Texture(Gdx.files.internal("png/field.png")));
        fieldImage.setOriginCenter();
        fieldImage.setOriginBasedPosition(Constants.WORLD_DIMENSIONS.x / 2, Constants.WORLD_DIMENSIONS.y / 2);
    }

    public void render() {

        batch.setProjectionMatrix(CameraManager.getInstance().getWorldCamera().getCamera().combined);

        batch.begin();
        fieldImage.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
