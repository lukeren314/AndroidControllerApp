package com.example.androidpad;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ControllerStick extends ControllerPart{
    private Paint outerPaint;
    private Paint innerPaint;
    private int outerRadius;
    private int innerRadius;
    private int centerX;
    private int centerY;
    private int innerX;
    private int innerY;
    private double actuatorX;
    private double actuatorY;
    public ControllerStick(String name, int stickColor){
        super(name, InputEvent.PartType.STICK);
        outerPaint = new Paint();
        outerPaint.setColor(GlobalUtil.darkenColor(stickColor, 0.75f));
        outerPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerPaint = new Paint();
        innerPaint.setColor(stickColor);
        innerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
    public void setDimensions(int x, int y, int outerRadius, int innerRadius){
        centerX = x;
        centerY = y;
        this.outerRadius = outerRadius;
        this.innerRadius = innerRadius;
    }
    @Override
    public void update() {
        updateInnerPosition();
    }
    private void updateInnerPosition(){
        innerX = (int) (centerX + actuatorX*outerRadius);
        innerY = (int) (centerY + actuatorY*outerRadius);
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, outerRadius, outerPaint);
        canvas.drawCircle(innerX, innerY, innerRadius, innerPaint);
    }
    @Override
    public boolean isPressed(double touchX, double touchY) {
        return GlobalUtil.distance(centerX, centerY, touchX, touchY) < outerRadius;
    }

    @Override
    public boolean getActuator() {
        return true;
    }

    @Override
    public void setActuator(double touchX, double touchY) {
        double deltaX = touchX-centerX;
        double deltaY = touchY-centerY;
        double deltaDistance = GlobalUtil.distance(centerX, centerY, touchX, touchY);

        if(deltaDistance < outerRadius){
            actuatorX = deltaX/outerRadius;
            actuatorY = deltaY/outerRadius;
        } else {
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaY/deltaDistance;
        }
//        Log.d("ACTUATOR", "X: "+actuatorX+", Y: "+actuatorY);
    }
    @Override
    public void resetActuator() {
        actuatorX = 0;
        actuatorY = 0;
    }

    @Override
    public InputEvent getInputEvent() {
        return new InputEvent(InputEvent.PartType.STICK,getName(), getIsPressed(), actuatorX, actuatorY);
    }

}
