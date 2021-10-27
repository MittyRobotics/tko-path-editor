package com.amhsrobotics.tkopatheditor.display;

import com.amhsrobotics.tkopatheditor.blueprints.PropertiesFilter;
import com.amhsrobotics.tkopatheditor.field.FieldConstants;
import com.amhsrobotics.tkopatheditor.field.Waypoint;
import com.amhsrobotics.tkopatheditor.parametrics.SplineHandle;
import com.amhsrobotics.tkopatheditor.util.DragConstants;
import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.text.DecimalFormat;

public class HandleProperties {

    private static HandleProperties instance;

    private Table table;

    private TextField positionX, positionY, rotation;

    private DecimalFormat format = new DecimalFormat("##.00");

    public static HandleProperties getInstance() {
        if(instance == null) {
            instance = new HandleProperties();
        }
        return instance;
    }

    public void init(Table table) {
        this.table = table;
        this.positionX = new TextField("", UITools.textFieldStyle);
        this.positionX.setTextFieldFilter(new PropertiesFilter());

        this.positionY = new TextField("", UITools.textFieldStyle);
        this.positionY.setTextFieldFilter(new PropertiesFilter());

        this.rotation = new TextField("", UITools.textFieldStyle);
        this.rotation.setTextFieldFilter(new PropertiesFilter());

    }

    public Table getTable() {
        return table;
    }

    public void setName(String val) {
        Label name = new Label(val, UITools.labelStyle);
        table.row();
        table.add(name);
    }

    public void setSubName(String val) {
        Label subName = new Label(val, UITools.labelStyleAlt);
        table.row();
        table.add(subName);
    }

    public void addLabel(String l) {
        Label la = new Label(l, UITools.labelStyleSmall);
        this.table.row().pad(10);
        this.table.add(la);
    }

    public void addPositionRotation(boolean handle) {
        if(handle) {
            this.table.row().pad(10);
            Table container = new Table();
            container.add(positionX).width(100).padRight(5);
            container.add(positionY).width(100).padLeft(5);
            this.table.add(container);

            this.table.row().padBottom(10);
            Table container2 = new Table();
            container2.add(rotation).width(60).padRight(5);
            Label rotLabel = new Label(" deg", UITools.labelStyleSmall);
            container2.add(rotLabel);
            this.table.add(container2);

            this.positionX.setTextFieldListener((textField, c) -> {
                if(!DragConstants.draggingHandle) {
                    try {
                        Vector2 val = FieldConstants.inchesToRelativePixels(new Vector2(
                                Float.parseFloat(textField.getText()), 0
                        ));
                        PropertiesWindow.getInstance().getCurrentHandle().setPosition(
                                new Vector2(
                                        val.x, (float) PropertiesWindow.getInstance().getCurrentHandle().getPoint().getY()
                                )
                        );
                    } catch (Exception ignored) {

                    }
                }
            });
            this.positionY.setTextFieldListener((textField, c) -> {
                if(!DragConstants.draggingHandle) {
                    try {
                        Vector2 val = FieldConstants.inchesToRelativePixels(new Vector2(
                                0, Float.parseFloat(textField.getText())
                        ));
                        PropertiesWindow.getInstance().getCurrentHandle().setPosition(
                                new Vector2(
                                        (float) PropertiesWindow.getInstance().getCurrentHandle().getPoint().getX(), val.y
                                )
                        );
                    } catch (Exception ignored) {

                    }
                }
            });
            this.rotation.setTextFieldListener((textField, c) -> {
                if(!DragConstants.draggingRotationHandle) {
                    try {
                        PropertiesWindow.getInstance().getCurrentHandle().setRotation(Float.parseFloat(textField.getText()));
                    } catch (Exception ignored) {

                    }
                }
            });
        } else {
            this.table.row().pad(10);
            Table container = new Table();
            container.add(positionX).width(100).padRight(5);
            container.add(positionY).width(100).padLeft(5);
            this.table.add(container);

            this.positionX.setTextFieldListener((textField, c) -> {
                try {
                    Vector2 val = FieldConstants.inchesToRelativePixels(new Vector2(
                            Float.parseFloat(textField.getText()), 0
                    ));
                    PropertiesWindow.getInstance().getCurrentWaypoint().setPosition(
                            new Vector2(
                                    val.x, PropertiesWindow.getInstance().getCurrentWaypoint().getPositionPixels().y
                            )
                    );
                } catch (Exception ignored) {

                }
            });
            this.positionY.setTextFieldListener((textField, c) -> {
                try {
                    Vector2 val = FieldConstants.inchesToRelativePixels(new Vector2(
                            0, Float.parseFloat(textField.getText())
                    ));
                    PropertiesWindow.getInstance().getCurrentWaypoint().setPosition(
                            new Vector2(
                                    PropertiesWindow.getInstance().getCurrentWaypoint().getPositionPixels().x, val.y
                            )
                    );
                } catch (Exception ignored) {

                }
            });
        }

    }

    public void trackTargetPosition(SplineHandle h) {
        Vector2 position = new Vector2((float) h.getPoint().getX(), (float) h.getPoint().getY());
        double rotation = Math.toDegrees(h.getPoint().getRotation().getRadians());

        Vector2 inchPos = FieldConstants.pixelToRelativeInches(position);

        setPositionText(inchPos.x, inchPos.y);
        this.rotation.setText(format.format(rotation));
    }

    public void trackTargetPosition(Waypoint w) {
        Vector2 inchPos = w.getPositionInches();

        setPositionText(inchPos.x, inchPos.y);
    }

    public void setPositionText(float x, float y) {
        this.positionX.setText(format.format(x));
        this.positionY.setText(format.format(y));
    }

    public void row(int... padding) {
        if(padding.length > 0) {
            this.table.row().pad(padding[0]);
        } else {
            this.table.row();
        }
    }

    public void addButton(String b1, Runnable toRun) {
        TextButton btn = new TextButton(b1, UITools.textButtonStyle);
        btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toRun.run();
            }
        });
        this.table.add(btn);
    }

    public void addButtonRow(String[] content, Runnable[] runnables, int padding) {
        Table container = new Table();
        for(int i = 0; i < content.length; i++) {
            TextButton btn = new TextButton(content[i], UITools.textButtonStyle);
            int finalI = i;
            btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    runnables[finalI].run();
                }
            });
            container.add(btn).pad(padding);
        }
        table.add(container);
    }

    public void reset() {
        table.clear();
    }

}
