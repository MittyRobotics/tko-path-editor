package com.amhsrobotics.tkopatheditor.parametrics;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import me.rohanbansal.ricochet.tools.ModifiedShapeRenderer;

import java.util.ArrayList;

public class SplineManager {

    private static SplineManager instance;

    private DelayedRemovalArray<SplineWrapper> splines;

    private ModifiedShapeRenderer sRenderer;


    public static SplineManager getInstance() {
        if(instance == null) {
            instance = new SplineManager();
        }
        return instance;
    }

    public void init() {
        splines = new DelayedRemovalArray<>();
        sRenderer = new ModifiedShapeRenderer();
    }

    public void render(float delta) {

        sRenderer.setProjectionMatrix(CameraManager.getInstance().getWorldCamera().getCamera().combined);

        sRenderer.setColor(Constants.SPLINE_DEFAULT_COLOR);
        sRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(SplineWrapper s : splines) {
            s.draw(sRenderer);
        }
        sRenderer.end();
    }

    public void registerSpline(SplineWrapper spline) {
        spline.generate();
        splines.add(spline);
    }

    public ArrayList<SplineHandle> getAllHandles() {
        ArrayList<SplineHandle> allHandles = new ArrayList<>();
        for(SplineWrapper s : splines) {
            allHandles.addAll(s.getHandles());
        }
        return allHandles;
    }

    public SplineWrapper getSplineByID(int id) {
        for(SplineWrapper s : splines) {
            if(s.getID() == id) {
                return s;
            }
        }
        return null;
    }
}
