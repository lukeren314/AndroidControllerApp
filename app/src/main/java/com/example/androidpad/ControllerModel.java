package com.example.androidpad;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ControllerModel {


    public enum DefaultLayout{
        GAMEPAD,
        MINIMAL,
        BASIC,
        JOYCON,
        BUMPERS,
        TRIGGERS
    }
    public String[] getDefaultLayouts() {
        return new String[] { "Gamepad", "Minimal", "Basic", "Joycon", "Bumpers", "Triggers"};
    }
    public void setLayout(int position) {
        setLayout(DefaultLayout.values()[position]);
    }

    public int screenWidth;
    public int screenHeight;
    public String deviceName;

    private SocketClient socket;
    private ControllerView controllerView;
    private List<ControllerPart> controllerParts;
    private Queue<InputEvent> inputEvents;

    private int numParts;
    //region Controller Parts
    ControllerStick lsb;
    ControllerStick rsb;
    ControllerButton ab;
    ControllerButton bb;
    ControllerButton xb;
    ControllerButton yb;
    ControllerDPad dpad;
    ControllerBumper lb;
    ControllerBumper rb;
    ControllerTrigger lt;
    ControllerTrigger rt;
    ControllerButton start;
    ControllerButton back;
    ControllerButton menu;
    //endregion

    public ControllerModel(Context context){
        deviceName = Build.MODEL;
        deviceName = TextUtils.join("-", deviceName.split(" "));

        socket = new SocketClient();
        socket.setDeviceName(deviceName);
        socket.setProtocol(SocketClient.Protocol.TCP);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;


        lsb = new ControllerStick("L", Color.GRAY);
        rsb = new ControllerStick("R", Color.GRAY);
        ab = new ControllerButton("A", Color.GREEN, "A", Color.BLACK);
        bb = new ControllerButton("B", Color.RED, "B", Color.BLACK);
        xb = new ControllerButton("X", Color.YELLOW, "X", Color.BLACK);
        yb = new ControllerButton("Y", Color.BLUE, "Y", Color.BLACK);
        dpad = new ControllerDPad("D", Color.GRAY);
        lb = new ControllerBumper("L", Color.GRAY, "LB", Color.BLACK);
        rb = new ControllerBumper("R", Color.GRAY, "RB", Color.BLACK);
        lt = new ControllerTrigger("L", Color.GRAY, "LT", Color.BLACK);
        rt = new ControllerTrigger("R", Color.GRAY, "RT", Color.BLACK);

        start = new ControllerButton("S", Color.GRAY, "START", Color.WHITE);
        back = new ControllerButton("BA", Color.GRAY, "BACK", Color.WHITE);
        menu = new ControllerButton("M", Color.GRAY, "MENU", Color.WHITE);

        controllerParts = new ArrayList<>();
        inputEvents = new LinkedList<>();

        setLayout(DefaultLayout.GAMEPAD);
    }
    public void setLayout(DefaultLayout layout){
        switch(layout) {
            case GAMEPAD:
                setLayoutGamepad();
                break;
            case MINIMAL:
                setLayoutMinimal();
                break;
            case BASIC:
                setLayoutBasic();
                break;
            case JOYCON:
                setLayoutJoycon();
                break;
            case BUMPERS:
                setLayoutBumpers();
                break;
            case TRIGGERS:
                setLayoutTriggers();
                break;
            default:
                setLayoutGamepad();
                break;
        }
    }


    private void setLayoutGamepad() {
        setControllerParts(new ControllerPart[] {lsb, rsb, ab, bb, xb, yb, dpad, lb, rb, lt, rt, start, back, menu});
        lsb.setDimensions((int) (screenWidth * .195), (int) (screenHeight * .5), (int) (screenWidth * .09), (int) (screenWidth * .045));
        rsb.setDimensions((int) (screenWidth * .65), (int) (screenHeight * .775), (int) (screenWidth * .09), (int) (screenWidth * .045));
        ab.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .65), (int) (screenWidth * .035));
        bb.setDimensions((int) (screenWidth * .83 + screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
        xb.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .35), (int) (screenWidth * .035));
        yb.setDimensions((int) (screenWidth * .83 - screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
        dpad.setDimensions((int) (screenWidth * 0.40), (int) (screenHeight * 0.775), (int) (screenWidth * 0.09), (int) (screenWidth * 0.03));
        lb.setDimensions((int) (screenWidth* .15), (int) (screenHeight*.05), (int) (screenWidth*.3), (int) (screenHeight*.1));
        rb.setDimensions((int) (screenWidth* .85), (int) (screenHeight*.05), (int) (screenWidth*.3), (int) (screenHeight*.1));
        lt.setDimensions((int) (screenWidth* .15), (int) (screenHeight*.17), (int) (screenWidth*.3), (int) (screenHeight*.12));
        rt.setDimensions((int) (screenWidth* .85), (int) (screenHeight*.17), (int) (screenWidth*.3), (int) (screenHeight*.12));
        start.setDimensions((int) (screenWidth * .575), (int) (screenHeight * .4), (int) (screenHeight * .035));
        back.setDimensions((int) (screenWidth * .425), (int) (screenHeight * .4), (int) (screenHeight * .035));
        menu.setDimensions((int) (screenWidth * 0.5), (int) (screenHeight * 0.25), (int) (screenHeight * 0.05));
    }

    private void setLayoutMinimal() {
        setControllerParts(new ControllerPart[] {lsb, ab, bb, start, back, menu});
        lsb.setDimensions((int) (screenWidth * .195), (int) (screenHeight * .5), (int) (screenWidth * .09), (int) (screenWidth * .045));
        ab.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .65), (int) (screenWidth * .035));
        bb.setDimensions((int) (screenWidth * .83 + screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
        start.setDimensions((int) (screenWidth * .575), (int) (screenHeight * .4), (int) (screenHeight * .035));
        back.setDimensions((int) (screenWidth * .425), (int) (screenHeight * .4), (int) (screenHeight * .035));
        menu.setDimensions((int) (screenWidth * 0.5), (int) (screenHeight * 0.25), (int) (screenHeight * 0.05));
    }

    private void setLayoutBasic() {
        setControllerParts(new ControllerPart[] {lsb, ab, bb, xb, yb, start, back, menu});
        lsb.setDimensions((int) (screenWidth * .195), (int) (screenHeight * .5), (int) (screenWidth * .09), (int) (screenWidth * .045));
        ab.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .65), (int) (screenWidth * .035));
        bb.setDimensions((int) (screenWidth * .83 + screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
        xb.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .35), (int) (screenWidth * .035));
        yb.setDimensions((int) (screenWidth * .83 - screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
        start.setDimensions((int) (screenWidth * .575), (int) (screenHeight * .4), (int) (screenHeight * .035));
        back.setDimensions((int) (screenWidth * .425), (int) (screenHeight * .4), (int) (screenHeight * .035));
        menu.setDimensions((int) (screenWidth * 0.5), (int) (screenHeight * 0.25), (int) (screenHeight * 0.05));
    }

    private void setLayoutJoycon() {
        setControllerParts(new ControllerPart[] {lsb, ab, bb, xb, yb, lb, rb, start, back, menu});
        lsb.setDimensions((int) (screenWidth * .195), (int) (screenHeight * .5), (int) (screenWidth * .09), (int) (screenWidth * .045));
        ab.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .65), (int) (screenWidth * .035));
        bb.setDimensions((int) (screenWidth * .83 + screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
        xb.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .35), (int) (screenWidth * .035));
        yb.setDimensions((int) (screenWidth * .83 - screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
        lb.setDimensions((int) (screenWidth* .15), (int) (screenHeight*.1), (int) (screenWidth*.3), (int) (screenHeight*.2));
        rb.setDimensions((int) (screenWidth* .85), (int) (screenHeight*.1), (int) (screenWidth*.3), (int) (screenHeight*.2));
        start.setDimensions((int) (screenWidth * .575), (int) (screenHeight * .4), (int) (screenHeight * .035));
        back.setDimensions((int) (screenWidth * .425), (int) (screenHeight * .4), (int) (screenHeight * .035));
        menu.setDimensions((int) (screenWidth * 0.5), (int) (screenHeight * 0.25), (int) (screenHeight * 0.05));
    }

    private void setLayoutBumpers() {
        setControllerParts(new ControllerPart[] {lsb, rsb, ab, bb, xb, yb, dpad, lb, rb, start, back, menu});
        lsb.setDimensions((int) (screenWidth * .195), (int) (screenHeight * .5), (int) (screenWidth * .09), (int) (screenWidth * .045));
        rsb.setDimensions((int) (screenWidth * .65), (int) (screenHeight * .775), (int) (screenWidth * .09), (int) (screenWidth * .045));
        ab.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .65), (int) (screenWidth * .035));
        bb.setDimensions((int) (screenWidth * .83 + screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
        xb.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .35), (int) (screenWidth * .035));
        yb.setDimensions((int) (screenWidth * .83 - screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
        dpad.setDimensions((int) (screenWidth * 0.40), (int) (screenHeight * 0.775), (int) (screenWidth * 0.09), (int) (screenWidth * 0.03));
        lb.setDimensions((int) (screenWidth* .15), (int) (screenHeight*.1), (int) (screenWidth*.3), (int) (screenHeight*.2));
        rb.setDimensions((int) (screenWidth* .85), (int) (screenHeight*.1), (int) (screenWidth*.3), (int) (screenHeight*.2));
        start.setDimensions((int) (screenWidth * .575), (int) (screenHeight * .4), (int) (screenHeight * .035));
        back.setDimensions((int) (screenWidth * .425), (int) (screenHeight * .4), (int) (screenHeight * .035));
        menu.setDimensions((int) (screenWidth * 0.5), (int) (screenHeight * 0.25), (int) (screenHeight * 0.05));
    }

    private void setLayoutTriggers() {
        setControllerParts(new ControllerPart[] {lsb, rsb, ab, bb, xb, yb, dpad, lt, rt, start, back, menu});
        lsb.setDimensions((int) (screenWidth * .195), (int) (screenHeight * .5), (int) (screenWidth * .09), (int) (screenWidth * .045));
        rsb.setDimensions((int) (screenWidth * .65), (int) (screenHeight * .775), (int) (screenWidth * .09), (int) (screenWidth * .045));
        ab.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .65), (int) (screenWidth * .035));
        bb.setDimensions((int) (screenWidth * .83 + screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
        xb.setDimensions((int) (screenWidth * .83), (int) (screenHeight * .35), (int) (screenWidth * .035));
        yb.setDimensions((int) (screenWidth * .83 - screenHeight * 0.15), (int) (screenHeight * .5), (int) (screenWidth * .035));
        dpad.setDimensions((int) (screenWidth * 0.40), (int) (screenHeight * 0.775), (int) (screenWidth * 0.09), (int) (screenWidth * 0.03));
        lt.setDimensions((int) (screenWidth* .15), (int) (screenHeight*.1), (int) (screenWidth*.3), (int) (screenHeight*.2));
        rt.setDimensions((int) (screenWidth* .85), (int) (screenHeight*.1), (int) (screenWidth*.3), (int) (screenHeight*.2));
        start.setDimensions((int) (screenWidth * .575), (int) (screenHeight * .4), (int) (screenHeight * .035));
        back.setDimensions((int) (screenWidth * .425), (int) (screenHeight * .4), (int) (screenHeight * .035));
        menu.setDimensions((int) (screenWidth * 0.5), (int) (screenHeight * 0.25), (int) (screenHeight * 0.05));
    }

    public List<ControllerPart> getControllerParts(){
        return controllerParts;
    }
    private void setControllerParts(ControllerPart[] parts) {
        controllerParts.clear();
        for(int i = 0; i < parts.length; i++){
            controllerParts.add(parts[i]);
        }
        numParts = parts.length;
    }

    public void addInputEvent(InputEvent inputEvent){
        inputEvents.add(inputEvent);
    }
    public Queue<InputEvent> getInputEvents() {
        return inputEvents;
    }
    public void addInputEvents() {
        if(socket.isConnected()){
            while(inputEvents.peek() != null){
                InputEvent ie = inputEvents.remove();
                String msg = ie.toString();
                socket.sendMessage(msg);
            }
        }
    }
    public void setControllerView(ControllerView controllerView) {
        this.controllerView = controllerView;
        socket.setControllerView(controllerView);
    }

    public void connect(String ipAddress, int port) {
//        socket.setServerIp(ipAddress);
//        socket.setPortNum(port);
        socket.connect(ipAddress, port);
        //change the view to indicate pending?
        //change the connect menu to indicate connection active?
    }

    public void actionDown(double touchX, double touchY, int pointerId) {
        for(int i = 0; i < numParts; i++){
            if(controllerParts.get(i).isPressed(touchX, touchY)){
                controllerParts.get(i).setIsPressed(true);
                controllerParts.get(i).setPointerId(pointerId);
                controllerParts.get(i).setActuator(touchX, touchY);
                addInputEvent(controllerParts.get(i).getInputEvent());
            }
        }
    }

    public void actionMove(double touchX, double touchY, int pointerId) {
        for(int i = 0; i < numParts; i++){
            if(controllerParts.get(i).getIsPressed() && controllerParts.get(i).getActuator() && controllerParts.get(i).getPointerId() == pointerId){
                controllerParts.get(i).setActuator(touchX, touchY);
                addInputEvent(controllerParts.get(i).getInputEvent());
            }
        }
    }

    public void actionUp(int pointerId) {
        for(int i = 0; i < numParts; i++){
            if(controllerParts.get(i).getPointerId() == pointerId){
                controllerParts.get(i).setIsPressed(false);
                controllerParts.get(i).setPointerId(-1);
                controllerParts.get(i).resetActuator();
                addInputEvent(controllerParts.get(i).getInputEvent());
            }
        }
    }
    public void surfaceCreated() {
        /*if(socket.isDisconnected()){
            socket.connect();
        }*/
    }
    public void update(){
        for(int i = 0; i < numParts; i++){
            controllerParts.get(i).update();
        }
    }
    public void draw(Canvas canvas){
        for(int i = 0; i < numParts; i++){
            controllerParts.get(i).draw(canvas);
        }
    }
    public void stop(){
        socket.stop();
    }
}
