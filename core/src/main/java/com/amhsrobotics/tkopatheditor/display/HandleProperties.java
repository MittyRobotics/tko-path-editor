package com.amhsrobotics.tkopatheditor.display;

import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class HandleProperties {

    private static HandleProperties instance;

    private Table table;

    public static HandleProperties getInstance() {
        if(instance == null) {
            instance = new HandleProperties();
        }
        return instance;
    }

    public void init(Table table) {
        this.table = table;
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

    public void addButton(String b1, Runnable toRun) {
        TextButton btn = new TextButton(b1, UITools.textButtonStyle);
        btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toRun.run();
            }
        });
        this.table.row().pad(10);
        this.table.add(btn);
    }

    public void reset() {
        table.clear();
    }

}
