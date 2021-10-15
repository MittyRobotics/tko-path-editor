package com.amhsrobotics.tkopatheditor.parametrics;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.amhsrobotics.tkopatheditor.util.DragConstants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.github.mittyrobotics.core.math.geometry.Rotation;
import com.github.mittyrobotics.core.math.geometry.Transform;
import com.github.mittyrobotics.core.math.geometry.Vector2D;

import static com.github.mittyrobotics.core.math.units.ConversionsKt.degrees;

public class SplineHandle {

    private Transform point;
    private Circle circle;
    private Circle rotationCircle;
    private SplineWrapper spline;

    private Sprite addPoint, deletePoint;

    private int id;

    public SplineHandle(Transform point, int ID, SplineWrapper wrapper) {
        this.spline = wrapper;
        this.point = point;
        this.id = ID;
        circle = new Circle((float) point.getX(), (float) point.getY(), Constants.HANDLE_RADIUS);
        rotationCircle = new Circle(circle.x, circle.y, Constants.HANDLE_ROTATION_RADIUS);

        addPoint = new Sprite(new Texture(Gdx.files.internal("png/plus.png")));
        deletePoint = new Sprite(new Texture(Gdx.files.internal("png/minus.png")));

        addPoint.setOriginCenter();
        deletePoint.setOriginCenter();

    }

    public void render(ShapeRenderer renderer, SpriteBatch batch) {

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

            if(spline.getLastHandle() == this || spline.getFirstHandle() == this) {

                addPoint.setOriginBasedPosition((float) point.getX() + (spline.getFirstHandle() != this ? 35 : -35), (float) point.getY() + 10);
                deletePoint.setOriginBasedPosition((float) point.getX() + (spline.getFirstHandle() != this ? 35 : -35), (float) point.getY() - 10);

                renderer.end();
                Gdx.gl.glDisable(GL30.GL_BLEND);

                batch.begin();
                addPoint.draw(batch);

                if(addPoint.getBoundingRectangle().contains(CameraManager.mouseScreenToWorld()) && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                    spline.addPoint(
                            new Transform(new Vector2D(point.getX() + (spline.getFirstHandle() != this ? 50 : -50), point.getY()), new Rotation(0)),
                            spline.getFirstHandle() != this
                    );
                    spline.generate();
                }

                if(spline.getLength() > 2) {
                    deletePoint.draw(batch);

                    if(deletePoint.getBoundingRectangle().contains(CameraManager.mouseScreenToWorld()) && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                        spline.deletePoint(this);
                        spline.generate();
                    }
                }
                batch.end();

                Gdx.gl.glEnable(GL30.GL_BLEND);
                Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
                renderer.begin(ShapeRenderer.ShapeType.Filled);
            } else {
                deletePoint.setOriginBasedPosition((float) point.getX(), (float) point.getY() - 35);

                renderer.end();
                Gdx.gl.glDisable(GL30.GL_BLEND);

                batch.begin();

                deletePoint.draw(batch);

                if(deletePoint.getBoundingRectangle().contains(CameraManager.mouseScreenToWorld()) && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                    spline.deletePoint(this);
                    spline.generate();
                }

                batch.end();

                Gdx.gl.glEnable(GL30.GL_BLEND);
                Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
                renderer.begin(ShapeRenderer.ShapeType.Filled);
            }
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

    public boolean isHoveringHandleModifier() {
        return addPoint.getBoundingRectangle().contains(CameraManager.mouseScreenToWorld()) || deletePoint.getBoundingRectangle().contains(CameraManager.mouseScreenToWorld());
    }

    public boolean isHoveringHandle() {
        return circle.contains(CameraManager.mouseScreenToWorld());
    }

    public boolean isHoveringRotationCircle() {
        return (rotationCircle.contains(CameraManager.mouseScreenToWorld()) && !isHoveringHandle());
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

    public int getId() {
        return id;
    }

    public void assignId(int id) {
        this.id = id;
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