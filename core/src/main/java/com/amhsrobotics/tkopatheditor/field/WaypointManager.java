package com.amhsrobotics.tkopatheditor.field;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;

public class WaypointManager {

    private static WaypointManager instance;

    private DelayedRemovalArray<Waypoint> waypoints;

    public static WaypointManager getInstance() {
        if(instance == null) {
            instance = new WaypointManager();
        }
        return instance;
    }

    public void init() {
        waypoints = new DelayedRemovalArray<>();
    }

    public void render(ShapeRenderer r, SpriteBatch b, BitmapFont f) {
        for(Waypoint w : waypoints) {
            w.render(r, b, f);
        }
    }

    public void addWaypoint(Waypoint w) {
        waypoints.add(w);
    }

    public void addWaypointPixels(float x, float y) {
        waypoints.add(new Waypoint(new Vector2(x, y), false));
    }

    public void addWaypointInches(float x, float y) {
        waypoints.add(new Waypoint(new Vector2(x, y), true));
    }
}
