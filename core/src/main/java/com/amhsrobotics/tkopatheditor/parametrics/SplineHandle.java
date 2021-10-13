package com.amhsrobotics.tkopatheditor.parametrics;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.amhsrobotics.tkopatheditor.util.DragConstants;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.github.mittyrobotics.core.math.geometry.Transform;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.degrees;

public class SplineHandle {

    Transform point;
    Circle circle;
    Circle rotationCircle;
    SplineWrapper spline;

    public SplineHandle(Transform point, SplineWrapper wrapper) {
        this.spline = wrapper;
        this.point = point;
        circle = new Circle((float) point.getX(), (float) point.getY(), Constants.HANDLE_RADIUS);
        rotationCircle = new Circle(circle.x, circle.y, Constants.HANDLE_ROTATION_RADIUS);
    }

    public void render(ShapeRenderer renderer) {

        circle = new Circle((float) point.getX(), (float) point.getY(), Constants.HANDLE_RADIUS);
        rotationCircle = new Circle(circle.x, circle.y, Constants.HANDLE_ROTATION_RADIUS);

        if(DragConstants.handleSelected == this) {
            renderer.setColor(Color.RED);

            double startX = circle.x - (25 * Math.cos(point.getRadians()));
            double startY = circle.y - (25 * Math.sin(point.getRadians()));
            double endX = circle.x + (25 * Math.cos(point.getRadians()));
            double endY = circle.y + (25 * Math.sin(point.getRadians()));
            renderer.rectLine((float) startX, (float) startY, (float) endX, (float) endY, 3);

            renderer.setColor(Constants.HANDLE_SELECTED_COLOR);
        } else {
            if(isHoveringHandle()) {
                renderer.setColor(Constants.HANDLE_HOVER_COLOR);
            } else {
                renderer.setColor(Constants.HANDLE_IDLE_COLOR);
            }
        }


        renderer.circle(circle.x, circle.y, circle.radius);

        if(DragConstants.handleSelected == this) {
            renderer.setColor(Constants.HANDLE_ROTATION_COLOR);
            renderer.circle(rotationCircle.x, rotationCircle.y, rotationCircle.radius);

        }


    }

    public boolean isHoveringHandle() {
        return circle.contains(CameraManager.mouseScreenToWorld(CameraManager.getInstance().getWorldCamera()));
    }

    public boolean isHoveringRotationCircle() {
        return (rotationCircle.contains(CameraManager.mouseScreenToWorld(CameraManager.getInstance().getWorldCamera())) && !isHoveringHandle());
    }

    public void setPosition(Vector2 position) {
        point.setX(position.x);
        point.setY(position.y);

        spline.generate();
    }

    public void setRotation(double deg) {
        point.setRadians(degrees(deg));

        spline.generate();
    }

    public double getRotation() {
        return point.getRadians();
    }

    public void changeRotation(double value) {
        if(Math.toDegrees(point.getRotation().getRadians()) >= 360) {
            point.setRadians(0);
        }
        point.setRadians(point.getRadians() + value);

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