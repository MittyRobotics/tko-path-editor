package com.amhsrobotics.tkopatheditor.util;

import com.amhsrobotics.tkopatheditor.Constants;
import com.amhsrobotics.tkopatheditor.display.Overlay;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;

public class UITools {

    public static TextButton.TextButtonStyle textButtonStyle;
    public static TextTooltip.TextTooltipStyle tooltipStyle;
    public static Label.LabelStyle labelStyle;

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
    }

    public static BitmapFont loadSmallFont() {
        return new BitmapFont(Gdx.files.internal(Constants.FONT_SMALL));
    }

    public static BitmapFont loadLargeFont() {
        return new BitmapFont(Gdx.files.internal(Constants.FONT_LARGE));
    }
}
