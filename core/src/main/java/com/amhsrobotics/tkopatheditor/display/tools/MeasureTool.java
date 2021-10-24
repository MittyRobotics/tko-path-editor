package com.amhsrobotics.tkopatheditor.display.tools;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.blueprints.ToolButton;
import com.amhsrobotics.tkopatheditor.field.FieldConstants;
import com.amhsrobotics.tkopatheditor.field.Waypoint;
import com.amhsrobotics.tkopatheditor.field.WaypointManager;
import com.amhsrobotics.tkopatheditor.parametrics.SplineHandle;
import com.amhsrobotics.tkopatheditor.parametrics.SplineManager;
import com.amhsrobotics.tkopatheditor.util.CameraManager;
import com.amhsrobotics.tkopatheditor.util.DragConstants;
import com.amhsrobotics.tkopatheditor.util.SnapGrid;
import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.text.DecimalFormat;

public class MeasureTool implements ToolButton {

    private static MeasureTool instance;

    TextButton button;

    private Vector2 point1;
    private Vector2 point2;

    private GlyphLayout layout = new GlyphLayout();
    private DecimalFormat format = new DecimalFormat("##.00");

    private Vector2 mouseCursor = new Vector2();

    public static MeasureTool getInstance() {
        if(instance == null) {
            instance = new MeasureTool();
        }
        return instance;
    }

    @Override
    public TextButton create() {
        button = new TextButton("M", UITools.textButtonStyle);
        button.setPosition(400, Gdx.graphics.getHeight() - 60);
        button.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        MeasureTool.getInstance().onClick();
                    }
                }
        );
        button.addListener(
                new TextTooltip("Measure distance between two points, or find a point on the field.", UITools.tooltipStyle)
        );

        return button;
    }

    @Override
    public void onClick() {
        if(DragConstants.measureToolEnabled) {
            DragConstants.measureToolEnabled = false;
            mouseCursor = new Vector2();
        } else {
            DragConstants.resetAll();
            DragConstants.measureToolEnabled = true;
            point1 = null;
            point2 = null;
        }

    }

    public void render(ShapeRenderer renderer, SpriteBatch batch, BitmapFont font) {
        renderer.setProjectionMatrix(CameraManager.getInstance().getWorldCamera().getCamera().combined);

        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        mouseCursor = CameraManager.mouseScreenToWorld();
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            mouseCursor = SnapGrid.calculateSnap(mouseCursor);
            for(SplineHandle hl : SplineManager.getInstance().getAllHandles()) {
                Vector2 v = new Vector2((float) hl.getPoint().getX(), (float) hl.getPoint().getY());
                if(v.dst(mouseCursor) <= 20) {
                    mouseCursor.set(v);
                    break;
                }
            }
            for(Waypoint wp : WaypointManager.getInstance().getWaypoints()) {
                if(wp.getPositionPixels().dst(mouseCursor) <= 20) {
                    mouseCursor.set(wp.getPositionPixels());
                    break;
                }
            }
        }

        renderer.setColor(Constants.HANDLE_ROTATION_COLOR);
        renderer.circle(mouseCursor.x, mouseCursor.y, Constants.MEASURE_TOOL_RADIUS);

        if(point1 != null && point2 != null) {
            renderer.setColor(Constants.MEASURE_TOOL_COLOR);
            renderer.rectLine(point1.x, point1.y, point2.x, point2.y, Constants.MEASURE_TOOL_LINE_WIDTH);

            renderer.setColor(Color.LIME);
            renderer.rectLine(point1.x, point1.y, point2.x, point1.y, Constants.MEASURE_TOOL_LINE_WIDTH - 2);

            renderer.setColor(Color.SCARLET);
            renderer.rectLine(point2.x, point1.y, point2.x, point2.y, Constants.MEASURE_TOOL_LINE_WIDTH - 2);
        }

        if(point1 != null) {
            renderer.setColor(Constants.MEASURE_TOOL_OUTER_COLOR);
            renderer.circle(point1.x, point1.y, Constants.MEASURE_TOOL_OUTER_RADIUS);
            renderer.setColor(Constants.MEASURE_TOOL_COLOR);
            renderer.circle(point1.x, point1.y, Constants.MEASURE_TOOL_RADIUS);
        }
        if(point2 != null) {
            renderer.setColor(Constants.MEASURE_TOOL_OUTER_COLOR);
            renderer.circle(point2.x, point2.y, Constants.MEASURE_TOOL_OUTER_RADIUS);
            renderer.setColor(Constants.MEASURE_TOOL_COLOR);
            renderer.circle(point2.x, point2.y, Constants.MEASURE_TOOL_RADIUS);
        }

        renderer.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);

        batch.setProjectionMatrix(CameraManager.getInstance().getHUDcamera().getCamera().combined);
        batch.begin();

        drawCoordinate(batch, font, point1);
        drawCoordinate(batch, font, point2);

        drawLength(batch, font);

        batch.end();
    }

    private void drawLength(SpriteBatch batch, BitmapFont font) {
        if(point1 != null && point2 != null) {
            Vector2 components = FieldConstants.pixelToInches(new Vector2(point2.x - point1.x, point2.y - point1.y));

            double dst = Math.sqrt(Math.pow(components.x, 2) + Math.pow(components.y, 2));

            // magnitude
            String text = format.format(dst);
            this.layout.setText(font, text);
            font.setColor(Color.GOLDENROD);
            Vector2 coordinatePos = CameraManager.vectorWorldtoScreen(new Vector2((point1.x + point2.x) / 2, (point1.y + point2.y) / 2));

            font.draw(batch, text, coordinatePos.x - layout.width / 2, coordinatePos.y + 10);

            // x-component
            text = format.format(Math.abs(components.x));
            this.layout.setText(font, text);
            font.setColor(Color.LIME);
            coordinatePos = CameraManager.vectorWorldtoScreen(new Vector2((point1.x + point2.x) / 2, point1.y));

            font.draw(batch, text, coordinatePos.x - layout.width / 2, coordinatePos.y + 10);

            // y-component
            text = format.format(Math.abs(components.y));
            this.layout.setText(font, text);
            font.setColor(Color.SCARLET);
            coordinatePos = CameraManager.vectorWorldtoScreen(new Vector2(point2.x, (point1.y + point2.y) / 2));

            font.draw(batch, text, coordinatePos.x - layout.width / 2, coordinatePos.y + 10);
        }
    }

    private void drawCoordinate(SpriteBatch batch, BitmapFont font, Vector2 point) {
        if(point != null) {
            Vector2 inchVec = FieldConstants.pixelToRelativeInches(point);
            String text = format.format(inchVec.x) + ", " + format.format(inchVec.y);
            this.layout.setText(font, text);
            font.setColor(Constants.MEASURE_TOOL_COLOR);
            Vector2 coordinatePos = CameraManager.vectorWorldtoScreen(new Vector2(point.x, point.y));

            font.draw(batch, text, coordinatePos.x - layout.width / 2, coordinatePos.y + 30);
        }
    }

    public Vector2 getMouseCursor() {
        return mouseCursor;
    }

    public Vector2 getPoint1() {
        return point1;
    }

    public void setPoint1(Vector2 point1) {
        this.point1 = point1;
    }

    public Vector2 getPoint2() {
        return point2;
    }

    public void setPoint2(Vector2 point2) {
        this.point2 = point2;
    }
}
