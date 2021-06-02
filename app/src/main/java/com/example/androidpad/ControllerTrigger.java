package com.example.androidpad;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class ControllerTrigger extends ControllerPart{
    private Paint triggerPaint;
    private Paint pressedPaint;
    private int width;
    private int height;
    private int centerX;
    private int centerY;
    private Rect rect;
    private Paint labelPaint;
    private String labelText;
    public ControllerTrigger(String name, int triggerColor, String labelText, int labelColor){
        super(name, InputEvent.PartType.TRIGGER);

        triggerPaint = new Paint();
        triggerPaint.setColor(triggerColor);
        triggerPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        pressedPaint = new Paint();
        pressedPaint.setColor(GlobalUtil.darkenColor(triggerColor, 0.5f));
        pressedPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        this.labelText = labelText;

        labelPaint = new Paint();
        labelPaint.setStyle(Paint.Style.FILL);
        labelPaint.setTextAlign(Paint.Align.CENTER);

        labelPaint.setColor(labelColor);
        labelPaint.setTextSize(64);
        labelPaint.setFakeBoldText(true);
    }
    public void setDimensions(int x, int y, int w, int h){
        centerX = x;
        centerY = y;
        width = w;
        height = h;
        rect = new Rect(x-w/2, y-h/2, x+w/2, y+h/2);
    }
    @Override
    public void draw(Canvas canvas){
        canvas.drawRect(rect, getIsPressed() ? pressedPaint : triggerPaint);
        canvas.drawText(labelText, centerX, centerY, labelPaint);
    }
    @Override
    public boolean isPressed(double touchX, double touchY) {
        return rect.contains((int) touchX, (int) touchY);
    }

    @Override
    public InputEvent getInputEvent() {
        return new InputEvent(InputEvent.PartType.TRIGGER, getName(), getIsPressed());
    }
}
