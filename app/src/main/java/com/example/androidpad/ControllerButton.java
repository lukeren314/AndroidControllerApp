package com.example.androidpad;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ControllerButton extends ControllerPart{
    private Paint buttonPaint;
    private Paint pressedPaint;
    private int buttonRadius;
    private int centerX;
    private int centerY;
    private Paint labelPaint;
    private String labelText;
    public ControllerButton(String name, int buttonColor, String label, int labelColor){
        super(name, InputEvent.PartType.BUTTON);
        buttonPaint = new Paint();
        buttonPaint.setColor(buttonColor);
        buttonPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        pressedPaint = new Paint();
        pressedPaint.setColor(GlobalUtil.darkenColor(buttonColor, 0.5f));
        pressedPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        this.labelText = label;

        labelPaint = new Paint();
        labelPaint.setStyle(Paint.Style.FILL);
        labelPaint.setTextAlign(Paint.Align.CENTER);

        labelPaint.setColor(labelColor);
        labelPaint.setTextSize(64);
        labelPaint.setFakeBoldText(true);
    }

    public void setDimensions(int x, int y, int r){
        centerX = x;
        centerY = y;
        buttonRadius = r;
    }
    @Override
    public void draw(Canvas canvas){
        canvas.drawCircle(centerX, centerY, buttonRadius, getIsPressed() ? pressedPaint : buttonPaint);
        canvas.drawText(labelText, centerX, centerY, labelPaint);
    }
    @Override
    public boolean isPressed(double touchX, double touchY) {
        return GlobalUtil.distance(centerX, centerY, touchX, touchY) < buttonRadius;
    }

    @Override
    public InputEvent getInputEvent() {
        return new InputEvent(InputEvent.PartType.BUTTON, getName(), getIsPressed());
    }
}
