package com.example.androidpad;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Overlay extends RelativeLayout {
    private Button connectButton;
    private Button settingsButton;
    private ConnectMenu connectMenu;
    private SettingsMenu settingsMenu;
    private LinearLayout buttonsLayout;
    private ControllerModel controllerModel;
    public Overlay(Context context, ControllerModel controllerModel){
        super(context);

        LayoutParams overlayParams = new
                LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        overlayParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        buttonsLayout = new LinearLayout(context);
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);

        settingsButton = new Button(context);
        settingsButton.setText("Settings");
        settingsButton.setLayoutParams(overlayParams);
        settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSettingsMenu();
            }
        });
        buttonsLayout.addView(settingsButton);

        connectButton = new Button(context);
        connectButton.setText("Connect");
        connectButton.setLayoutParams(overlayParams);
        connectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openConnectMenu();
            }
        });
        buttonsLayout.addView(connectButton);

        addView(buttonsLayout, overlayParams);
        connectMenu = new ConnectMenu(context, controllerModel);
        settingsMenu = new SettingsMenu(context, controllerModel);
    }

    private void openSettingsMenu() {
        connectMenu.close();
        settingsMenu.open();
//        if(!settingsMenu.isOpen()){
//
//        } else{
//            settingsMenu.close();
//        }
    }

    private void openConnectMenu() {
        settingsMenu.close();
        connectMenu.open();
//        if(!connectMenu.isOpen()){
//
//        } else{
//            connectMenu.close();
//        }
    }

    public void setConnected(boolean isConnected) {
        connectMenu.setConnected(isConnected);
    }
}
