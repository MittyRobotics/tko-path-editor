package com.amhsrobotics.tkopatheditor.display;

import com.amhsrobotics.tkopatheditor.util.UITools;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class HandleProperties {

    private static HandleProperties instance;

    private Label name, subName;

    public static HandleProperties getInstance() {
        if(instance == null) {
            instance = new HandleProperties();
        }
        return instance;
    }

    public void init(Table table) {
        name = new Label("", UITools.labelStyle);
        subName = new Label("", UITools.labelStyleAlt);

        table.add(name);
        table.row();
        table.add(subName);
    }

    public void setName(String val) {
        name.setText(val);
    }

    public void setSubName(String val) {
        subName.setText(val);
    }
}
