package com.example.androidpad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.LinkedList;
import java.util.Queue;

class ControllerView extends FrameLayout{
    private ControllerModel controllerModel;
    private ControllerSurface controllerSurface;
    private Overlay overlay;

    private int screenWidth;
    private int screenHeight;
    public ControllerView(Context context, ControllerModel controllerModel) {
        super(context);
        this.controllerModel = controllerModel;
        screenWidth = controllerModel.screenWidth;
        screenHeight = controllerModel.screenHeight;

        controllerSurface = new ControllerSurface(context, controllerModel);
        overlay = new Overlay(context, controllerModel);

        addView(controllerSurface);
        addView(overlay);
    }
    public void setConnected(final boolean isConnected){
        ((Activity)getContext()).runOnUiThread(new Runnable(){
            @Override
            public void run() {
                overlay.setConnected(isConnected);
            }
        });
    }
    public void pause(){
        controllerSurface.pause();
    }
}
