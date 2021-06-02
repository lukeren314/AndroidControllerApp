//package com.example.androidpad;
//
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.util.Log;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;
//
//public class ControllerLayout {
//
//    public String[] getDefaultLayouts() {
//        return new String[] { "Gamepad", "Minimal", "Basic", "Joycon", "Bumpers", "Triggers"};
//    }
//    public void setLayout(int position) {
//        setLayout(DefaultLayout.values()[position]);
//    }
//
//    public enum DefaultLayout{
//        GAMEPAD,
//        MINIMAL,
//        BASIC,
//        JOYCON,
//        BUMPERS,
//        TRIGGERS
//    }
//    private int screenWidth;
//    private int screenHeight;
//    private List<ControllerPart> parts;
//    private Queue<InputEvent> inputEvents;
//
//    ControllerStick lsb;
//    ControllerStick rsb;
//    ControllerButton ab;
//    ControllerButton bb;
//    ControllerButton xb;
//    ControllerButton yb;
//    ControllerDPad dpad;
//    ControllerBumper lb;
//    ControllerBumper rb;
//    ControllerTrigger lt;
//    ControllerTrigger rt;
//    ControllerButton start;
//    ControllerButton back;
//    ControllerButton menu;
//
//    public ControllerLayout(int screenWidth, int screenHeight){
//        this.screenWidth = screenWidth;
//        this.screenHeight = screenHeight;
//        inputEvents = new LinkedList<InputEvent>();
//
//        lsb = new ControllerStick("LSB", Color.GRAY);
//        rsb = new ControllerStick("LSB", Color.GRAY);
//        ab = new ControllerButton("AB", Color.GREEN, "A", Color.BLACK);
//        bb = new ControllerButton("BB", Color.RED, "B", Color.BLACK);
//        xb = new ControllerButton("XB", Color.YELLOW, "X", Color.BLACK);
//        yb = new ControllerButton("YB", Color.BLUE, "Y", Color.BLACK);
//        dpad = new ControllerDPad("DPAD", Color.GRAY);
//        lb = new ControllerBumper("LB", Color.GRAY, "LB", Color.BLACK);
//        rb = new ControllerBumper("RB", Color.GRAY, "RB", Color.BLACK);
//        lt = new ControllerTrigger("LT", Color.GRAY, "LT", Color.BLACK);
//        rt = new ControllerTrigger("RT", Color.GRAY, "RT", Color.BLACK);
//
//        start = new ControllerButton("START", Color.GRAY, "START", Color.WHITE);
//        back = new ControllerButton("BACK", Color.GRAY, "BACK", Color.WHITE);
//        menu = new ControllerButton("MENU", Color.GRAY, "MENU", Color.WHITE);
//
//        parts = new ArrayList<>();
//    }
//    public ControllerLayout (int screenWidth, int screenHeight, DefaultLayout layoutEnum){
//        this(screenWidth, screenHeight);
//        setLayout(layoutEnum);
//    }
//    public void setLayout(DefaultLayout layout){
//        switch(layout) {
//            case MINIMAL:
//                setParts(new ControllerPart[] {lsb, ab, bb, start, back, menu});
//                lsb.setDimensions((int) (screenWidth * .195), (int) (screenHeight * .5), (int) (screenWidth * .09), (int) (screenWidth * .045));
//                ab.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .65), (int) (screenWidth * .035));
//                bb.setDimensions((int) (screenWidth * .83 + screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
//                start.setDimensions((int) (screenWidth * .575), (int) (screenHeight * .4), (int) (screenHeight * .035));
//                back.setDimensions((int) (screenWidth * .425), (int) (screenHeight * .4), (int) (screenHeight * .035));
//                menu.setDimensions((int) (screenWidth * 0.5), (int) (screenHeight * 0.25), (int) (screenHeight * 0.05));
//                break;
//            case BASIC:
//                setParts(new ControllerPart[] {lsb, ab, bb, xb, yb, start, back, menu});
//                lsb.setDimensions((int) (screenWidth * .195), (int) (screenHeight * .5), (int) (screenWidth * .09), (int) (screenWidth * .045));
//                ab.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .65), (int) (screenWidth * .035));
//                bb.setDimensions((int) (screenWidth * .83 + screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
//                xb.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .35), (int) (screenWidth * .035));
//                yb.setDimensions((int) (screenWidth * .83 - screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
//                start.setDimensions((int) (screenWidth * .575), (int) (screenHeight * .4), (int) (screenHeight * .035));
//                back.setDimensions((int) (screenWidth * .425), (int) (screenHeight * .4), (int) (screenHeight * .035));
//                menu.setDimensions((int) (screenWidth * 0.5), (int) (screenHeight * 0.25), (int) (screenHeight * 0.05));
//                break;
//            case JOYCON:
//                setParts(new ControllerPart[] {lsb, ab, bb, xb, yb, lb, rb, start, back, menu});
//                lsb.setDimensions((int) (screenWidth * .195), (int) (screenHeight * .5), (int) (screenWidth * .09), (int) (screenWidth * .045));
//                ab.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .65), (int) (screenWidth * .035));
//                bb.setDimensions((int) (screenWidth * .83 + screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
//                xb.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .35), (int) (screenWidth * .035));
//                yb.setDimensions((int) (screenWidth * .83 - screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
//                lb.setDimensions((int) (screenWidth* .15), (int) (screenHeight*.1), (int) (screenWidth*.3), (int) (screenHeight*.2));
//                rb.setDimensions((int) (screenWidth* .85), (int) (screenHeight*.1), (int) (screenWidth*.3), (int) (screenHeight*.2));
//                start.setDimensions((int) (screenWidth * .575), (int) (screenHeight * .4), (int) (screenHeight * .035));
//                back.setDimensions((int) (screenWidth * .425), (int) (screenHeight * .4), (int) (screenHeight * .035));
//                menu.setDimensions((int) (screenWidth * 0.5), (int) (screenHeight * 0.25), (int) (screenHeight * 0.05));
//                break;
//            case BUMPERS:
//                setParts(new ControllerPart[] {lsb, rsb, ab, bb, xb, yb, dpad, lb, rb, start, back, menu});
//                lsb.setDimensions((int) (screenWidth * .195), (int) (screenHeight * .5), (int) (screenWidth * .09), (int) (screenWidth * .045));
//                rsb.setDimensions((int) (screenWidth * .65), (int) (screenHeight * .775), (int) (screenWidth * .09), (int) (screenWidth * .045));
//                ab.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .65), (int) (screenWidth * .035));
//                bb.setDimensions((int) (screenWidth * .83 + screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
//                xb.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .35), (int) (screenWidth * .035));
//                yb.setDimensions((int) (screenWidth * .83 - screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
//                dpad.setDimensions((int) (screenWidth * 0.40), (int) (screenHeight * 0.775), (int) (screenWidth * 0.09), (int) (screenWidth * 0.03));
//                lb.setDimensions((int) (screenWidth* .15), (int) (screenHeight*.1), (int) (screenWidth*.3), (int) (screenHeight*.2));
//                rb.setDimensions((int) (screenWidth* .85), (int) (screenHeight*.1), (int) (screenWidth*.3), (int) (screenHeight*.2));
//                start.setDimensions((int) (screenWidth * .575), (int) (screenHeight * .4), (int) (screenHeight * .035));
//                back.setDimensions((int) (screenWidth * .425), (int) (screenHeight * .4), (int) (screenHeight * .035));
//                menu.setDimensions((int) (screenWidth * 0.5), (int) (screenHeight * 0.25), (int) (screenHeight * 0.05));
//                break;
//            case TRIGGERS:
//                setParts(new ControllerPart[] {lsb, rsb, ab, bb, xb, yb, dpad, lt, rt, start, back, menu});
//                lsb.setDimensions((int) (screenWidth * .195), (int) (screenHeight * .5), (int) (screenWidth * .09), (int) (screenWidth * .045));
//                rsb.setDimensions((int) (screenWidth * .65), (int) (screenHeight * .775), (int) (screenWidth * .09), (int) (screenWidth * .045));
//                ab.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .65), (int) (screenWidth * .035));
//                bb.setDimensions((int) (screenWidth * .83 + screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
//                xb.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .35), (int) (screenWidth * .035));
//                yb.setDimensions((int) (screenWidth * .83 - screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
//                dpad.setDimensions((int) (screenWidth * 0.40), (int) (screenHeight * 0.775), (int) (screenWidth * 0.09), (int) (screenWidth * 0.03));
//                lt.setDimensions((int) (screenWidth* .15), (int) (screenHeight*.1), (int) (screenWidth*.3), (int) (screenHeight*.2));
//                rt.setDimensions((int) (screenWidth* .85), (int) (screenHeight*.1), (int) (screenWidth*.3), (int) (screenHeight*.2));
//                start.setDimensions((int) (screenWidth * .575), (int) (screenHeight * .4), (int) (screenHeight * .035));
//                back.setDimensions((int) (screenWidth * .425), (int) (screenHeight * .4), (int) (screenHeight * .035));
//                menu.setDimensions((int) (screenWidth * 0.5), (int) (screenHeight * 0.25), (int) (screenHeight * 0.05));
//                break;
//            case GAMEPAD:
//                setParts(new ControllerPart[] {lsb, rsb, ab, bb, xb, yb, dpad, lb, rb, lt, rt, start, back, menu});
//                lsb.setDimensions((int) (screenWidth * .195), (int) (screenHeight * .5), (int) (screenWidth * .09), (int) (screenWidth * .045));
//                rsb.setDimensions((int) (screenWidth * .65), (int) (screenHeight * .775), (int) (screenWidth * .09), (int) (screenWidth * .045));
//                ab.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .65), (int) (screenWidth * .035));
//                bb.setDimensions((int) (screenWidth * .83 + screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
//                xb.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .35), (int) (screenWidth * .035));
//                yb.setDimensions((int) (screenWidth * .83 - screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
//                dpad.setDimensions((int) (screenWidth * 0.40), (int) (screenHeight * 0.775), (int) (screenWidth * 0.09), (int) (screenWidth * 0.03));
//                lb.setDimensions((int) (screenWidth* .15), (int) (screenHeight*.05), (int) (screenWidth*.3), (int) (screenHeight*.1));
//                rb.setDimensions((int) (screenWidth* .85), (int) (screenHeight*.05), (int) (screenWidth*.3), (int) (screenHeight*.1));
//                lt.setDimensions((int) (screenWidth* .15), (int) (screenHeight*.17), (int) (screenWidth*.3), (int) (screenHeight*.12));
//                rt.setDimensions((int) (screenWidth* .85), (int) (screenHeight*.17), (int) (screenWidth*.3), (int) (screenHeight*.12));
//                start.setDimensions((int) (screenWidth * .575), (int) (screenHeight * .4), (int) (screenHeight * .035));
//                back.setDimensions((int) (screenWidth * .425), (int) (screenHeight * .4), (int) (screenHeight * .035));
//                menu.setDimensions((int) (screenWidth * 0.5), (int) (screenHeight * 0.25), (int) (screenHeight * 0.05));
//                break;
//        }
//    }
//
//    private void setParts(ControllerPart[] controllerParts) {
//        parts.clear();
//        for(int i = 0; i < controllerParts.length; i++){
//            parts.add(controllerParts[i]);
//        }
//    }
//
//    public void addInputEvent(InputEvent inputEvent){
//        inputEvents.add(inputEvent);
//    }
//    public Queue<InputEvent> getInputEvents() {
//        return inputEvents;
//    }
//
//    public void actionDown(double touchX, double touchY, int pointerId) {
//        for(int i = 0; i < parts.size(); i++){
//            if(parts.get(i).isPressed(touchX, touchY)){
//                parts.get(i).setIsPressed(true);
//                parts.get(i).setPointerId(pointerId);
//                parts.get(i).setActuator(touchX, touchY);
//                addInputEvent(parts.get(i).getInputEvent());
//            }
//        }
////        for(int i = 0; i < parts.size();i++){
////            Log.d("POINTER", "DOWN:"+i+": "+parts.get(i).getPointerId()+", X: "+touchX+", Y:"+touchY);
////        }
//    }
//
//    public void actionMove(double touchX, double touchY, int pointerId) {
//        for(int i = 0; i < parts.size(); i++){
//            if(parts.get(i).getIsPressed() && parts.get(i).getActuator() && parts.get(i).getPointerId() == pointerId){
//                parts.get(i).setActuator(touchX, touchY);
//                addInputEvent(parts.get(i).getInputEvent());
//            }
//        }
//    }
//
//    public void actionUp(int pointerId) {
//        for(int i = 0; i < parts.size(); i++){
//            if(parts.get(i).getPointerId() == pointerId){
//                parts.get(i).setIsPressed(false);
//                parts.get(i).setPointerId(-1);
//                parts.get(i).resetActuator();
//                addInputEvent(parts.get(i).getInputEvent());
//            }
//        }
//    }
//    public void update(){
//        for(int i = 0; i < parts.size(); i++){
//            parts.get(i).update();
//        }
//    }
//    public void draw(Canvas canvas){
//        for(int i = 0; i < parts.size(); i++){
//            parts.get(i).draw(canvas);
//        }
//    }
//}
