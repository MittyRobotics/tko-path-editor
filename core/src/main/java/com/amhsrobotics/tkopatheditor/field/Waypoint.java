package com.amhsrobotics.tkopatheditor.field;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.text.DecimalFormat;

public class Waypoint {

    private Vector2 position;
    private Vector2 positionInches;

    private GlyphLayout layout = new GlyphLayout();
    private DecimalFormat format = new DecimalFormat("##.00");

    private Sprite marker;

    public Waypoint(Vector2 position, boolean inInches) {
        if(inInches) {
            this.positionInches = position;
            this.position = FieldConstants.inchesToRelativePixels(this.positionInches);
        } else {
            this.position = position;
            this.positionInches = FieldConstants.pixelToRelativeInches(this.position);

        }

        marker = new Sprite(new Texture(Gdx.files.internal("png/marker.png")));
        marker.setOrigin(15, 0);
        marker.setOriginBasedPosition(this.position.x, this.position.y);
    }


    public void render(ShapeRenderer renderer, SpriteBatch batch, BitmapFont font) {

//        batch.setProjectionMatrix(CameraManager.getInstance().getWorldCamera().getCamera().combined);
//        renderer.setProjectionMatrix(CameraManager.getInstance().getWorldCamera().getCamera().combined);

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(Constants.WAYPOINT_TOOL_CIRCLE_COLOR);
        renderer.circle(position.x, position.y, Constants.WAYPOINT_TOOL_RADIUS);

        renderer.end();


        batch.begin();

        batch.setProjectionMatrix(CameraManager.getInstance().getWorldCamera().getCamera().combined);
        marker.draw(batch);

        batch.setProjectionMatrix(CameraManager.getInstance().getHUDcamera().getCamera().combined);
        if(marker.getBoundingRectangle().contains(CameraManager.mouseScreenToWorld())) {
            String text = format.format(positionInches.x) + ", " + format.format(positionInches.y);
            this.layout.setText(font, text);
            font.setColor(Color.GOLDENROD);
            Vector2 coordinatePos = CameraManager.vectorWorldtoScreen(position);

            font.draw(batch, text, coordinatePos.x - layout.width / 2, coordinatePos.y + 40);

        }

        batch.end();
    }
}
