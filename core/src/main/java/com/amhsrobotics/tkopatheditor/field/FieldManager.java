package com.amhsrobotics.tkopatheditor.field;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

// 283.98 in x 685.5 in
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

        FieldConstants.init(fieldImage);
        Gdx.app.log(FieldConstants.pixelToRelativeInches(new Vector2(fieldImage.getX(), fieldImage.getY())) + "", "");
    }

    public void render() {

        batch.setProjectionMatrix(CameraManager.getInstance().getWorldCamera().getCamera().combined);

        batch.begin();
        fieldImage.draw(batch);
        batch.end();
    }



    public Sprite getFieldSprite() {
        return fieldImage;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
