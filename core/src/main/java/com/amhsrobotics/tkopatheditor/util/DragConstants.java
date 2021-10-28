package com.amhsrobotics.tkopatheditor.util;

import com.amhsrobotics.tkopatheditor.display.PropertiesWindow;
import com.amhsrobotics.tkopatheditor.field.Waypoint;
import com.amhsrobotics.tkopatheditor.parametrics.SplineHandle;
import com.amhsrobotics.tkopatheditor.parametrics.SplineWrapper;

public class DragConstants {

    public static boolean splineSelected;
    public static SplineWrapper splineDragged;

    public static Waypoint waypointSelected;

    public static boolean draggingHandle = false;
    public static SplineHandle handleDragged = null;

    public static SplineHandle handleSelected = null;

    public static boolean draggingRotationHandle = false;
    public static boolean draggingFromLeft = false;

    public static boolean measureToolEnabled = false;
    public static boolean waypointToolEnabled = false;

    public static void resetAll() {
        draggingHandle = false;
        handleDragged = null;
        handleSelected = null;
        draggingRotationHandle = false;
        draggingFromLeft = false;
        measureToolEnabled = false;
        splineSelected = false;
        splineDragged = null;
        waypointToolEnabled = false;
        waypointSelected = null;
    }
}
