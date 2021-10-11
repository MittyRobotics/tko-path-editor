package com.amhsrobotics.tkopatheditor.util;

import com.badlogic.gdx.Gdx;
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

    public static Vector2 mouseScreenToWorld(CameraController cam) {
        Vector3 vec = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.getCamera().unproject(vec);
        return new Vector2(vec.x, vec.y);
    }

    public static Vector2 vectorScreenToWorld(CameraController cam, Vector2 vector) {
        Vector3 vec = new Vector3(vector.x, vector.y, 0);
        cam.getCamera().unproject(vec);
        return new Vector2(vec.x, vec.y);
    }

}
