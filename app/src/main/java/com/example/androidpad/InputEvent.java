package com.example.androidpad;

public class InputEvent {
    public enum EventType{
        DOWN,
        MOVE,
        UP
    }
    public enum PartType{
        STICK,
        BUTTON,
        DPAD,
        TRIGGER,
        BUMPER
    }
    PartType partType;
    private String name;
    private int val0;
    private double val1;
    private double val2;
    public InputEvent(PartType stick, String name, boolean isPressed, double actuatorX, double actuatorY) {
        partType = stick;
        this.name = name;
        val0 = isPressed ? 1: 0;
        val1 = actuatorX;
        val2 = actuatorY;
    }

    public InputEvent(PartType button, String name, boolean isPressed) {
        partType = button;
        this.name = name;
        val0 = isPressed ? 1: 0;
    }

    @Override
    public String toString() {
        switch (partType){
            case STICK:
                return "I S "+name+" "+val0+" "+String.format("%.3f", val1)+" "+String.format("%.3f", val2);
            case BUTTON:
                return "I B "+name+" "+val0;
            case DPAD:
                return "I D "+name+" "+val0+" "+String.format("%.3f", val1)+" "+String.format("%.3f", val2);
            case BUMPER:
                return "I BU "+name+" "+val0;
            case TRIGGER:
                return "I T "+name+" "+val0;
        }
        return "I I";
    }
}
