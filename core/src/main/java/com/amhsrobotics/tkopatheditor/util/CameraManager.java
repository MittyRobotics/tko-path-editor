package com.amhsrobotics.tkopatheditor.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.rohanbansal.ricochet.camera.CameraController;
import me.rohanbansal.ricochet.camera.ClippedCameraController;

import static com.amhsrobotics.tkopatheditor.Constants.WORLD_DIMENSIONS;

public class CameraManager {

    private static CameraManager instance;

    private ClippedCameraController worldCamera;
    private ExtendViewport worldViewport;

    private CameraController HUDcamera;
    private ExtendViewport HUDViewport;

    public static CameraManager getInstance() {
        if(instance == null) {
            instance = new CameraManager();
        }
        return instance;
    }

    public void init() {
        worldCamera = new ClippedCameraController(true, WORLD_DIMENSIONS);
        worldViewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), WORLD_DIMENSIONS.x, WORLD_DIMENSIONS.y, worldCamera.getCamera());

        HUDcamera = new CameraController(true);
        HUDViewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), HUDcamera.getCamera());

        getWorldCamera().getCamera().zoom = 2.0f;
        getWorldCamera().getCamera().position.set(new Vector2(WORLD_DIMENSIONS.x / 2, WORLD_DIMENSIONS.y / 2), 0);
        getWorldCamera().smoothZoomTo(1.0f, 1.5f, Interpolation.exp5);
    }

    public void update() {
        worldCamera.update();
        HUDcamera.update();
    }


    public ClippedCameraController getWorldCamera() {
        return worldCamera;
    }

    public CameraController getHUDcamera() {
        return HUDcamera;
    }

    public ExtendViewport getWorldViewport() {
        return worldViewport;
    }

    public ExtendViewport getHUDViewport() {
        return HUDViewport;
    }

    public static Vector2 mouseScreenToWorld() {
        Vector3 vec = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        getInstance().getWorldCamera().getCamera().unproject(vec);
        return new Vector2(vec.x, vec.y);
    }

    public static Vector2 vectorScreenToWorld(Vector2 vector) {
        Vector3 vec = new Vector3(vector.x, vector.y, 0);
        getInstance().getWorldCamera().getCamera().unproject(vec);
        return new Vector2(vec.x, vec.y);
    }

    public static Vector2 vectorWorldtoScreen(Vector2 vector) {
        Vector3 vec = new Vector3(vector.x, vector.y, 0);
        getInstance().getWorldCamera().getCamera().project(vec);
        return new Vector2(vec.x, vec.y);
    }

    public static Vector2 getWorldFocusPoint() {
        return vectorScreenToWorld(
                new Vector2((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2)
        );
    }

}
