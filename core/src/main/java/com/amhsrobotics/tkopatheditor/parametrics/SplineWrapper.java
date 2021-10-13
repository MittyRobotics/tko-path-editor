package com.amhsrobotics.tkopatheditor.parametrics;

import com.github.mittyrobotics.core.math.geometry.Rotation;
import com.github.mittyrobotics.core.math.geometry.Transform;
import com.github.mittyrobotics.core.math.geometry.Vector2D;
import com.github.mittyrobotics.core.math.spline.Path;

public class SplineWrapper {

    private Path path;
    private SplineType type;
    private int ID;

    private static int id_count = 0;

    public SplineWrapper(Transform[] points, SplineType type) {
        this.type = type;

        if(type == SplineType.CUBIC_HERMITE) {
            path = Path.Companion.cubicHermitePath(points);
        } else if(type == SplineType.QUINTIC_HERMITE) {
            path = Path.Companion.quinticHermitePath(points);
        }

        ID = id_count;
        id_count++;
    }

    public SplineWrapper(SplineType type) {
        this(
                new Transform[] {
                        new Transform(new Vector2D(0, 0), new Rotation(0)),
                        new Transform(new Vector2D(100, 100), new Rotation(0)),
                },
                type
        );
    }

    public int getID() {
        return ID;
    }

    public SplineType getType() {
        return type;
    }
}
