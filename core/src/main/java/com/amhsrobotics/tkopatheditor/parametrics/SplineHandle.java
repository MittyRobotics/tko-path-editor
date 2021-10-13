package com.amhsrobotics.tkopatheditor.parametrics;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.amhsrobotics.tkopatheditor.util.DragConstants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.github.mittyrobotics.core.math.geometry.Transform;

public class SplineHandle {

    Transform point;
    Circle circle;
    SplineWrapper spline;

    public SplineHandle(Transform point, SplineWrapper wrapper) {
        this.spline = wrapper;
        this.point = point;
        circle = new Circle((float) point.getX(), (float) point.getY(), 10);
    }

    public void render(ShapeRenderer renderer) {

        circle = new Circle((float) point.getX(), (float) point.getY(), 10);

        if(DragConstants.handleSelected == this) {
            renderer.setColor(Constants.HANDLE_SELECTED_COLOR);
        } else {
            if(isHovering()) {
                renderer.setColor(Constants.HANDLE_HOVER_COLOR);
            } else {
                renderer.setColor(Constants.HANDLE_IDLE_COLOR);
            }
        }


        renderer.circle(circle.x, circle.y, circle.radius);

        if(DragConstants.handleSelected == this) {
            renderer.setColor(Constants.HANDLE_MODIFICATION_COLOR);
            renderer.circle(circle.x, circle.y, circle.radius * 2 + 3);
        }


    }

    public boolean isHovering() {
        return circle.contains(CameraManager.mouseScreenToWorld(CameraManager.getInstance().getWorldCamera()));
    }

    public void setPosition(Vector2 position) {
        point.setX(position.x);
        point.setY(position.y);

        spline.generate();
    }

    public SplineWrapper getSpline() {
        return spline;
    }

    public Transform getPoint() {
        return point;
    }

    public Circle getCircle() {
        return circle;
    }
}