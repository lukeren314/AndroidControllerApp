package com.example.androidpad;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ControllerSurface extends SurfaceView implements SurfaceHolder.Callback  {
    private Loop loop;
    private Paint backgroundPaint;
    private ControllerModel controllerModel;
    public ControllerSurface(Context context, ControllerModel controllerModel){
        super(context);
        this.controllerModel = controllerModel;
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        loop = new Loop(this, surfaceHolder );

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.BLACK);
        backgroundPaint.setStyle(Paint.Style.FILL);

        setFocusable(true);
//        setFocusableInTouchMode(true);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);
        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                controllerModel.actionDown(
                        event.getX(pointerIndex),
                        event.getY(pointerIndex),
                        pointerId);
                controllerModel.addInputEvents();
                return true;
            case MotionEvent.ACTION_MOVE:
                for(int size = event.getPointerCount(), i = 0; i < size; i++){
                    controllerModel.actionMove(
                            event.getX(i),
                            event.getY(i),
                            event.getPointerId(i));
                }
                controllerModel.addInputEvents();
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                controllerModel.actionUp(pointerId);
                controllerModel.addInputEvents();
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (loop.getState().equals(Thread.State.TERMINATED)) {
            loop = new Loop(this, holder);
        }
        loop.startLoop();
        controllerModel.surfaceCreated();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawPaint(backgroundPaint);
        controllerModel.draw(canvas);
    }

    public void update() {
        controllerModel.update();
    }

    public void pause() {
        loop.stopLoop();
    }

}
