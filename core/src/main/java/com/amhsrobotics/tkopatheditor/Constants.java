package com.amhsrobotics.tkopatheditor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {

    public static final float ZOOM_AMPLIFIER = 0.1f;
    public static final float PAN_AMPLIFIER = 3.5f;

    public static final Vector2 WORLD_DIMENSIONS = new Vector2(4000, 4000);
    public static final int GRID_STARTING_GAP = 10;
    public static final int GRID_SIZE = 30;

    public static final float MAX_MAP_ZOOM = 1.6f;
    public static final float MIN_MAP_ZOOM = 0.3f;

    public static final Color BACKGROUND_COLOR = Color.valueOf("#4E629CFF");
    public static final Color GRID_COLOR = Color.valueOf("#7888B4FF");

    public static final Color PANEL_COLOR = Color.valueOf("#34353EFF");
    public static final int PANEL_HEIGHT = 65;

    public static final String UI_SKIN = "skin/ui-white.atlas";
    public static final String FONT_SMALL = "font/small.fnt";
    public static final String FONT_LARGE = "font/large.fnt";

    public static final double TIME_STEP = 0.0001;
    public static final double LINE_WIDTH = 2;
    public static final Color SPLINE_DEFAULT_COLOR = Color.valueOf("#C6F9F8FF");

    public static final Color HANDLE_MODIFICATION_COLOR = Color.valueOf("#F9F2F644");
    public static final Color HANDLE_IDLE_COLOR = Color.valueOf("#E396C2FF");
    public static final Color HANDLE_HOVER_COLOR = Color.valueOf("#F0B1D5FF");
    public static final Color HANDLE_SELECTED_COLOR = Color.valueOf("#EC5DAFFF");

}
