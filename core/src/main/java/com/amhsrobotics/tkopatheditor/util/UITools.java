package com.amhsrobotics.tkopatheditor.util;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.display.Overlay;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class UITools {

    public static TextButton.TextButtonStyle textButtonStyle;
    public static TextTooltip.TextTooltipStyle tooltipStyle;
    public static Label.LabelStyle labelStyle;
    public static ScrollPane.ScrollPaneStyle scrollStyle;

    public static BitmapFont SMALL_FONT;
    public static BitmapFont LARGE_FONT;

    public static void init() {
        SMALL_FONT = loadSmallFont();
        LARGE_FONT = loadLargeFont();

        labelStyle = new Label.LabelStyle();
        labelStyle.font = SMALL_FONT;
        labelStyle.fontColor = Color.SALMON;

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = SMALL_FONT;
        textButtonStyle.up = Overlay.getInstance().getSkin().getDrawable("button_03");
        textButtonStyle.down = Overlay.getInstance().getSkin().getDrawable("button_02");

        tooltipStyle = new TextTooltip.TextTooltipStyle();
        tooltipStyle.background = Overlay.getInstance().getSkin().getDrawable("button_01");
        tooltipStyle.wrapWidth = 150;
        tooltipStyle.label = labelStyle;

        scrollStyle = new ScrollPane.ScrollPaneStyle();
        scrollStyle.vScrollKnob = Overlay.getInstance().getSkinAlt().getDrawable("scroll_back_ver");
    }

    public static BitmapFont loadSmallFont() {
        return new BitmapFont(Gdx.files.internal(Constants.FONT_SMALL));
    }

    public static BitmapFont loadLargeFont() {
        return new BitmapFont(Gdx.files.internal(Constants.FONT_LARGE));
    }

    public static void slideIn(Actor actor, String from, float duration, Interpolation interp, int offset, Runnable... runnable) {
        Vector2 actorXY = new Vector2(actor.getX(), actor.getY());

        Runnable toRun = null;
        if (runnable.length > 0) {
            toRun = runnable[0];
        }

        if (from.equals("left")) {
            actor.setPosition(-offset, actorXY.y);
            if (toRun != null) {
                actor.addAction(sequence(moveTo(actorXY.x, actorXY.y, duration, interp), run(toRun)));
            } else {
                actor.addAction(moveTo(actorXY.x, actorXY.y, duration, interp));
            }
        } else if (from.equals("right")) {
            actor.setPosition(Gdx.graphics.getWidth() + offset, actorXY.y);
            if (toRun != null) {
                actor.addAction(sequence(moveTo(actorXY.x, actorXY.y, duration, interp), run(toRun)));
            } else {
                actor.addAction(moveTo(actorXY.x, actorXY.y, duration, interp));
            }
        } else if (from.equals("top")) {
            actor.setPosition(actorXY.x, Gdx.graphics.getHeight() + offset);
            if (toRun != null) {
                actor.addAction(sequence(moveTo(actorXY.x, actorXY.y, duration, interp), run(toRun)));
            } else {
                actor.addAction(moveTo(actorXY.x, actorXY.y, duration, interp));
            }
        } else if (from.equals("bottom")) {
            actor.setPosition(actorXY.x, -offset);
            if (toRun != null) {
                actor.addAction(sequence(moveTo(actorXY.x, actorXY.y, duration, interp), run(toRun)));
            } else {
                actor.addAction(moveTo(actorXY.x, actorXY.y, duration, interp));
            }
        }

        if (runnable.length > 0) {
            actor.addAction(run(runnable[0]));
        }
    }

    public static void slideOut(Actor actor, String to, float duration, Interpolation interp, int offset, Runnable... runnable) {
        Vector2 actorXY = new Vector2(actor.getX(), actor.getY());

        Runnable toRun = null;
        if (runnable.length > 0) {
            toRun = runnable[0];
        }

        if (to.equals("left")) {
            if (toRun != null) {
                actor.addAction(sequence(moveTo(-offset, actorXY.y, duration, interp), run(toRun)));
            } else {
                actor.addAction(moveTo(-offset, actorXY.y, duration, interp));
            }
        } else if (to.equals("right")) {
            if (toRun != null) {
                actor.addAction(sequence(moveTo(Gdx.graphics.getWidth() + offset, actorXY.y, duration, interp), run(toRun)));
            } else {
                actor.addAction(moveTo(Gdx.graphics.getWidth() + offset, actorXY.y, duration, interp));
            }
        } else if (to.equals("top")) {
            if (toRun != null) {
                actor.addAction(sequence(moveTo(actorXY.x, Gdx.graphics.getHeight() + offset, duration, interp), run(toRun)));
            } else {
                actor.addAction(moveTo(actorXY.x, Gdx.graphics.getHeight() + offset, duration, interp));
            }
        } else if (to.equals("bottom")) {
            if (toRun != null) {
                actor.addAction(sequence(moveTo(actorXY.x, -offset, duration, interp), run(toRun)));
            } else {
                actor.addAction(moveTo(actorXY.x, -offset, duration, interp));
            }
        }
    }
}
