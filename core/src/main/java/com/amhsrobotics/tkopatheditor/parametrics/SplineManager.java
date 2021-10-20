package com.amhsrobotics.tkopatheditor.parametrics;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.Disposable;
import me.rohanbansal.ricochet.tools.ModifiedShapeRenderer;

public class SplineManager implements Disposable {

    private static SplineManager instance;

    private DelayedRemovalArray<SplineWrapper> splines;

    private ModifiedShapeRenderer sRenderer;
    private SpriteBatch batch;


    public static SplineManager getInstance() {
        if(instance == null) {
            instance = new SplineManager();
        }
        return instance;
    }

    public void init() {
        splines = new DelayedRemovalArray<>();
        sRenderer = new ModifiedShapeRenderer();
        batch = new SpriteBatch();
    }

    public void render(float delta) {

        sRenderer.setProjectionMatrix(CameraManager.getInstance().getWorldCamera().getCamera().combined);
        batch.setProjectionMatrix(CameraManager.getInstance().getWorldCamera().getCamera().combined);

        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        sRenderer.setColor(Constants.SPLINE_DEFAULT_COLOR);
        sRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(SplineWrapper s : splines) {
            s.draw(sRenderer, batch);
        }
        sRenderer.end();

        Gdx.gl.glDisable(GL30.GL_BLEND);

    }

    public void registerSpline(SplineWrapper spline) {
        spline.generate();
        splines.add(spline);
    }

    public DelayedRemovalArray<SplineHandle> getAllHandles() {
        DelayedRemovalArray<SplineHandle> allHandles = new DelayedRemovalArray<>();
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

    @Override
    public void dispose() {
        sRenderer.dispose();
        batch.dispose();
        splines.clear();
    }

    public void deleteSpline(SplineWrapper spline) {
        splines.removeValue(spline, true);
    }
}
