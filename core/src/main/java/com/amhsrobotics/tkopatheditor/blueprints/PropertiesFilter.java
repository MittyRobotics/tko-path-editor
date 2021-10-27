package com.amhsrobotics.tkopatheditor.blueprints;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class PropertiesFilter implements TextField.TextFieldFilter {

    @Override
    public boolean acceptChar(TextField textField, char c) {
        return Character.isDigit(c) || c == '.' || c == '-';
    }
}
