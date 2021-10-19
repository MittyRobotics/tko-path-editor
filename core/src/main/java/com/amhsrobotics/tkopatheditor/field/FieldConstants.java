package com.amhsrobotics.tkopatheditor.field;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class FieldConstants {

    public static double INCH_HEIGHT = 323.25;
    public static double INCH_WIDTH = 629.25;

    public static double PIXEL_HEIGHT;
    public static double PIXEL_WIDTH;
    public static double PIXELS_PER_INCH_HEIGHT;
    public static double PIXELS_PER_INCH_WIDTH;

    public static Vector2 zero = new Vector2();

    public static void init(Sprite fieldImage) {
        PIXEL_WIDTH = fieldImage.getWidth();
        PIXEL_HEIGHT = fieldImage.getHeight();
        PIXELS_PER_INCH_HEIGHT = (PIXEL_HEIGHT / INCH_HEIGHT);
        PIXELS_PER_INCH_WIDTH = (PIXEL_WIDTH / INCH_WIDTH);

        zero = new Vector2(fieldImage.getX(), fieldImage.getY());

    }

    public static Vector2 pixelToRelativeInches(Vector2 position) {
        Vector2 df = new Vector2(position.x - zero.x, position.y - zero.y);
        float locationInInchesX = (float) (df.x / PIXELS_PER_INCH_WIDTH);
        float locationInInchesY = (float) (df.y / PIXELS_PER_INCH_HEIGHT);

        return new Vector2(locationInInchesX, locationInInchesY);
    }

    public static Vector2 inchesToRelativePixels(Vector2 position) {
        return new Vector2((float) (position.x * PIXELS_PER_INCH_WIDTH) + zero.x, (float) (position.y * PIXELS_PER_INCH_HEIGHT) + zero.y);
    }

    public static Vector2 pixelToInches(Vector2 vec) {
        return new Vector2((float) (vec.x / PIXELS_PER_INCH_WIDTH), (float) (vec.y / PIXELS_PER_INCH_HEIGHT));
    }
}
