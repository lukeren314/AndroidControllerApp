package com.example.androidpad;

import android.graphics.Canvas;

public abstract class ControllerPart {
    private boolean isPressed;
    private int pointerId;
    private InputEvent.PartType partType;
    private String name;


    public ControllerPart(String name, InputEvent.PartType partType){
        this.name = name;
        this.partType = partType;
        pointerId = -1;
    }

    public void update(){}
    public void draw(Canvas canvas){}
    public abstract boolean isPressed(double touchX, double touchY);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }
    public boolean getIsPressed() {
        return isPressed;
    }

    public InputEvent.PartType getPartType() {
        return partType;
    }

    public void setPartType(InputEvent.PartType partType) {
        this.partType = partType;
    }
    public boolean getActuator(){
        return false;
    }
    public void setActuator(double touchX, double touchY){}
    public void resetActuator(){}
    public void setPointerId(int pointerId){
//        Log.d("POINTER", name+": "+(pointerId != -1 ? "True" : "False"));
        this.pointerId = pointerId;
    }
    public int getPointerId() {
        return pointerId;
    }

    public abstract InputEvent getInputEvent();
}
