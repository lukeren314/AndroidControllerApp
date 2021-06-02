package com.example.androidpad;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

public class ControllerDPad extends ControllerPart {
    private int centerX;
    private int centerY;
    private int radius;
    private int thickness;
    private double actuatorX;
    private double actuatorY;
    private Path path;
    private Paint paint;
    private Paint directionPaint;
    private Path up;
    private Path left;
    private Path down;
    private Path right;


    public ControllerDPad(String name, int dpadColor){
        super(name, InputEvent.PartType.DPAD);

        paint = new Paint();
        paint.setColor(dpadColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

        directionPaint = new Paint();
        directionPaint.setColor(GlobalUtil.darkenColor(dpadColor, 0.75f));
        directionPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
    public void setDimensions(int x, int y, int r, int thickness){
        centerX = x;
        centerY = y;
        radius = r;
        this.thickness = thickness;

        path = createDPadPath(centerX, centerY, radius, thickness);

        up = createUpPath(centerX, centerY, radius, thickness);
        left = createLeftPath(centerX, centerY, radius, thickness);
        down = createDownPath(centerX, centerY, radius, thickness);
        right = createRightPath(centerX, centerY, radius, thickness);
    }
    private Path createDPadPath(int centerX, int centerY, int radius, int thickness) {
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(centerX-thickness, centerY-radius);
        path.lineTo(centerX+thickness, centerY-radius);
        path.lineTo(centerX+thickness, centerY-thickness);
        path.lineTo(centerX+thickness, centerY-thickness);
        path.lineTo(centerX+radius, centerY-thickness);
        path.lineTo(centerX+radius, centerY+thickness);
        path.lineTo(centerX+thickness, centerY+thickness);
        path.lineTo(centerX+thickness, centerY+radius);
        path.lineTo(centerX-thickness, centerY+radius);
        path.lineTo(centerX-thickness, centerY+thickness);
        path.lineTo(centerX-radius, centerY+thickness);
        path.lineTo(centerX-radius, centerY-thickness);
        path.lineTo(centerX-thickness, centerY-thickness);
        path.lineTo(centerX-thickness,centerY-radius);
        path.close();
        return path;
    }
    private Path createUpPath(int centerX, int centerY, int radius, int thickness) {
        Path path = new Path();
        path.moveTo(centerX-thickness, centerY-radius);
        path.lineTo(centerX+thickness, centerY-radius);
        path.lineTo(centerX+thickness, centerY-thickness);
        path.lineTo(centerX-thickness, centerY-thickness);
        path.lineTo(centerX-thickness,centerY-radius);
        path.close();
        return path;
    }
    private Path createLeftPath(int centerX, int centerY, int radius, int thickness) {
        Path path = new Path();
        path.moveTo(centerX-radius, centerY-thickness);
        path.lineTo(centerX-thickness, centerY-thickness);
        path.lineTo(centerX-thickness, centerY+thickness);
        path.lineTo(centerX-radius, centerY+thickness);
        path.lineTo(centerX-radius,centerY-thickness);
        path.close();
        return path;
    }
    private Path createDownPath(int centerX, int centerY, int radius, int thickness) {
        Path path = new Path();
        path.moveTo(centerX-thickness, centerY+thickness);
        path.lineTo(centerX+thickness, centerY+thickness);
        path.lineTo(centerX+thickness, centerY+radius);
        path.lineTo(centerX-thickness, centerY+radius);
        path.lineTo(centerX-thickness,centerY+thickness);
        path.close();
        return path;
    }
    private Path createRightPath(int centerX, int centerY, int radius, int thickness) {
        Path path = new Path();
        path.moveTo(centerX+thickness, centerY-thickness);
        path.lineTo(centerX+radius, centerY-thickness);
        path.lineTo(centerX+radius, centerY+thickness);
        path.lineTo(centerX+thickness, centerY+thickness);
        path.lineTo(centerX+thickness,centerY-thickness);
        path.close();
        return path;
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(path, paint);
        double sensitivity = thickness;
        sensitivity /= radius;

        if(actuatorY < -sensitivity){
            canvas.drawPath(up, directionPaint);
        }
        if(actuatorX < -sensitivity){
            canvas.drawPath(left, directionPaint);
        }
        if(actuatorY > sensitivity){
            canvas.drawPath(down, directionPaint);
        }
        if(actuatorX > sensitivity){
            canvas.drawPath(right, directionPaint);
        }
    }

    @Override
    public boolean isPressed(double touchX, double touchY) {
        return touchX >= centerX - radius && touchX <= centerX + radius && touchY >= centerY-radius && touchY <= centerY+radius;
    }

    @Override
    public boolean getActuator() {
        return true;
    }
    @Override
    public void setActuator(double touchX, double touchY) {
        double deltaX = touchX-centerX;
        double deltaY = touchY-centerY;
        actuatorX = Math.abs(deltaX) < radius ? deltaX/radius :  deltaX/Math.abs(deltaX);
        actuatorY = Math.abs(deltaY) < radius ? deltaY/radius :  deltaY/Math.abs(deltaY);
    }

    @Override
    public void resetActuator() {
        actuatorX = 0;
        actuatorY = 0;
    }

    @Override
    public InputEvent getInputEvent() {

        return new InputEvent(InputEvent.PartType.DPAD,getName(), getIsPressed(), actuatorX, actuatorY);
    }
}
