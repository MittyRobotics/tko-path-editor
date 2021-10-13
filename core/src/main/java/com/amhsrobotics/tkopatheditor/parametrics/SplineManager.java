package com.amhsrobotics.tkopatheditor.parametrics;

import com.badlogic.gdx.utils.DelayedRemovalArray;

public class SplineManager {

    private static SplineManager instance;

    private DelayedRemovalArray<SplineWrapper> splines;


    public static SplineManager getInstance() {
        if(instance == null) {
            instance = new SplineManager();
        }
        return instance;
    }

    public void init() {
        splines = new DelayedRemovalArray<>();
    }

    public void registerSpline(SplineWrapper spline) {
        splines.add(spline);
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
