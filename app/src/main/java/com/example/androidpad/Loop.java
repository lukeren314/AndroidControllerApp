package com.example.androidpad;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

class Loop extends Thread {
    private static final double MAX_UPS = 60.0;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    private ControllerSurface view;
    private double averageFPS;
    private double averageUPS;
    public Loop(ControllerSurface view, SurfaceHolder surfaceHolder){
        this.surfaceHolder = surfaceHolder;
        this.view = view;
    }
    public void startLoop(){
        isRunning = true;
        start();
    }

    public void stopLoop() {
        isRunning = false;
        try{
            join();
        } catch(InterruptedException e){
            e.printStackTrace();;
        }
    }
    @Override
    public void run() {
        super.run();

        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        Canvas canvas = null;
        startTime = System.currentTimeMillis();
        while(isRunning){
            try{
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    view.update();
                    updateCount++;
                    view.draw(canvas);
                }
            } catch (IllegalArgumentException e){
                e.printStackTrace();
            } finally{
                try {
                    if(canvas != null){
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }


            elapsedTime = System.currentTimeMillis()-startTime;
            sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            if(sleepTime > 0){
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            while(sleepTime < 0 && updateCount < MAX_UPS - 1){
                view.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis()-startTime;
                sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            }

            elapsedTime = System.currentTimeMillis()-startTime;
            if(elapsedTime>=1000){
                averageUPS = updateCount / (1E-3 * elapsedTime);
                Log.d("UPS", Double.toString(averageUPS));
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }

        }
    }

}
