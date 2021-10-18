package com.amhsrobotics.tkopatheditor.parametrics;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.amhsrobotics.tkopatheditor.util.DragConstants;
import com.amhsrobotics.tkopatheditor.util.Tuple;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.github.mittyrobotics.core.math.geometry.Rotation;
import com.github.mittyrobotics.core.math.geometry.Transform;
import com.github.mittyrobotics.core.math.geometry.Vector2D;
import com.github.mittyrobotics.core.math.spline.Path;
import me.rohanbansal.ricochet.tools.ModifiedShapeRenderer;

import java.util.ArrayList;
import java.util.Arrays;

public class SplineWrapper {

    private ArrayList<Transform> waypoints;
    private DelayedRemovalArray<SplineHandle> handles;
    private SplineType type;
    private Color color;
    private int ID;

    // GENERATED VALUES: DO NOT MODIFY
    private Path generatedPath;
    private ArrayList<Tuple<Double, Double>> generatedSpline;

    private static int id_count = 0;

    public SplineWrapper(Transform[] points, SplineType type) {
        this.type = type;

        this.waypoints = new ArrayList<>();
        this.handles = new DelayedRemovalArray<>();
        generatedSpline = new ArrayList<>();

        waypoints.addAll(Arrays.asList(points));

        assignHandles();
        color = Constants.SPLINE_DEFAULT_COLOR;

        ID = id_count;
        id_count++;
    }

    public SplineWrapper(SplineType type) {

        this(
                new Transform[]{
                        new Transform(new Vector2D(CameraManager.getWorldFocusPoint().x - 100, CameraManager.getWorldFocusPoint().y - 100), new Rotation(0)),
                        new Transform(new Vector2D(CameraManager.getWorldFocusPoint().x, CameraManager.getWorldFocusPoint().y), new Rotation(0)),
                        new Transform(new Vector2D(CameraManager.getWorldFocusPoint().x + 100, CameraManager.getWorldFocusPoint().y), new Rotation(0)),
                }, type
        );
    }

    public void generate() {

        if (type == SplineType.CUBIC_HERMITE) {
            generatedPath = Path.Companion.cubicHermitePath(waypoints.toArray(new Transform[0]));
        } else if (type == SplineType.QUINTIC_HERMITE) {
            generatedPath = Path.Companion.quinticHermitePath(waypoints.toArray(new Transform[0]));
        }
        assert generatedPath != null;

        generatedSpline.clear();
        for (double a = 0; a <= 1.0; a += Constants.TIME_STEP) {
            Transform transform = generatedPath.getTransform(a);
            generatedSpline.add(new Tuple<>(transform.getX(), transform.getY()));
        }
    }

    public void draw(ModifiedShapeRenderer renderer, SpriteBatch batch) {

        Vector2 mouse = CameraManager.mouseScreenToWorld();

        renderer.setColor(color);
        for (Tuple<Double, Double> t : generatedSpline) {

            if(Math.sqrt((Math.pow(mouse.x - t.x, 2)) + (Math.pow(mouse.y - t.y, 2))) <= Constants.LINE_WIDTH) {
                // TODO spline touched by mouse cursor
            }

            renderer.circle(t.x.floatValue(), t.y.floatValue(), (float) Constants.LINE_WIDTH);
        }

        for (SplineHandle handle : handles) {
            handle.render(renderer, batch);
        }
    }

    public void addPoint(Transform t, boolean end) {
        if(end) {
            waypoints.add(t);
            handles.add(new SplineHandle(t, waypoints.indexOf(t), this));
        } else {
            waypoints.add(0, t);
            assignHandles();
        }
    }

    private void assignHandles() {
        handles.clear();
        for (Transform wp : waypoints) {
            handles.add(new SplineHandle(wp, waypoints.indexOf(wp), this));
        }
    }

    public SplineHandle getHandleByID(int id) {
        for(SplineHandle sh : handles) {
            if(sh.getId() == id) {
                return sh;
            }
        }
        return null;
    }

    public int getLength() {
        return waypoints.size();
    }

    public SplineHandle getFirstHandle() {
        return handles.get(0);
    }

    public SplineHandle getLastHandle() {
        return handles.get(handles.size - 1);
    }

    public void deletePoint(SplineHandle point) {
        waypoints.remove(point.getPoint());
        handles.removeValue(point, true);
        assignHandles();

        DragConstants.handleSelected = null;
        DragConstants.handleDragged = null;

    }

    public DelayedRemovalArray<SplineHandle> getHandles() {
        return handles;
    }

    public int getID() {
        return ID;
    }

    public SplineType getType() {
        return type;
    }
}