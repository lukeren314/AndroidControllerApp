package com.example.androidpad;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;


public class MainActivity extends Activity {
    private FrameLayout base;
    private ControllerView controllerView;
    private ControllerModel controllerModel;
    private SocketClient socket;
    private SharedPreferences sharedPref;
    private String deviceName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controllerModel = new ControllerModel(this);
        controllerView = new ControllerView(this, controllerModel);
        controllerModel.setControllerView(controllerView);

        setContentView(controllerView);
//        getActionBar().hide();
    }

    @Override
    protected void onPause() {
        controllerView.pause();
        super.onPause();
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controllerModel.stop();
    }
}
