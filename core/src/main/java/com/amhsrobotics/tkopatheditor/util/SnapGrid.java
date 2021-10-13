package com.amhsrobotics.tkopatheditor.util;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Vector2;
import me.rohanbansal.ricochet.camera.ClippedCameraController;

import static com.amhsrobotics.tkopatheditor.Constants.*;

public class SnapGrid {

    private static final ImmediateModeRenderer20 lineRenderer = new ImmediateModeRenderer20(false, true, 0);

    public static void line(float x1, float y1,
                            float x2, float y2,
                            Color color) {
        lineRenderer.color(color);
        lineRenderer.vertex(x1, y1, 0);
        lineRenderer.color(color);
        lineRenderer.vertex(x2, y2, 0);
    }


    public static void renderGrid(ClippedCameraController camera) {
        for (int i = GRID_STARTING_GAP; i < WORLD_DIMENSIONS.x; i += GRID_SIZE) {

            lineRenderer.begin(camera.getCamera().combined, GL30.GL_LINES);
            for (int j = GRID_STARTING_GAP; j < WORLD_DIMENSIONS.y; j += GRID_SIZE) {
                line(i, 0, i, WORLD_DIMENSIONS.y, GRID_COLOR);
                line(0, j, WORLD_DIMENSIONS.x, j, GRID_COLOR);
            }
            lineRenderer.end();
        }
    }

    public static Vector2 calculateSnap(Vector2 position) {
        return new Vector2(
                Math.round(position.x / GRID_SIZE) * GRID_SIZE + GRID_STARTING_GAP,
                Math.round(position.y / GRID_SIZE) * GRID_SIZE + GRID_STARTING_GAP
        );
    }
}
