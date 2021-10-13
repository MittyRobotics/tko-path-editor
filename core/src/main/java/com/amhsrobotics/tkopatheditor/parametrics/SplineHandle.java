package com.amhsrobotics.tkopatheditor.parametrics;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.github.mittyrobotics.core.math.geometry.Transform;

public class SplineHandle {

    Transform point;

    public SplineHandle(Transform point) {
        this.point = point;
    }

    public void render(ShapeRenderer renderer) {

        Circle circle = new Circle((float) point.getX(), (float) point.getY(), 10);

        if(circle.contains(CameraManager.mouseScreenToWorld(CameraManager.getInstance().getWorldCamera()))) {
            renderer.setColor(Constants.HANDLE_HOVER_COLOR);
        } else {
            renderer.setColor(Constants.HANDLE_IDLE_COLOR);
        }

        renderer.circle(circle.x, circle.y, circle.radius);
    }
}