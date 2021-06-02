package com.example.androidpad;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ConnectMenu extends PopupWindow {
    private Context context;
    private LinearLayout connectLayout;
    private LinearLayout.LayoutParams connectParams;
    private TextView title;
    private TextView ipAddressTextView;
    private EditText ipAddressEditText;
    private TextView portTextView;
    private EditText portEditText;
    private TextView deviceNameTextView;
    private Button connectButton;
    private TextView connectStatus;
    private ControllerModel controllerModel;
    private boolean open;
    public ConnectMenu(Context context, ControllerModel controllerModel){
        super(context);
        this.context = context;
        this.controllerModel = controllerModel;

        setFocusable(true);
        connectParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        connectLayout = new LinearLayout(context);
        connectLayout.setOrientation(LinearLayout.VERTICAL);

        connectLayout.setFocusable(true);
        connectLayout.setFocusableInTouchMode(true);

        title = new TextView(context);
        title.setText("Connect to Server");
        connectLayout.addView(title, connectParams);

        ipAddressTextView = new TextView(context);
        ipAddressTextView.setText("Server IP Address:");
        connectLayout.addView(ipAddressTextView, connectParams);

        ipAddressEditText = new EditText(context);
//        ipAddressEditText.setText("192.168.0.110");
        ipAddressEditText.setHint("Enter IP Address Here...");
        ipAddressEditText.setWidth(1000);
        connectLayout.addView(ipAddressEditText, connectParams);

        portTextView = new TextView(context);
        portTextView.setText("Port:");
        connectLayout.addView(portTextView, connectParams);


        portEditText = new EditText(context);
        portEditText.setHint("Enter Port Number");
        portEditText.setText("11000");
        portEditText.setWidth(500);
        connectLayout.addView(portEditText, connectParams);

        deviceNameTextView = new TextView(context);
        deviceNameTextView.setText("Device Name: "+controllerModel.deviceName);
        connectLayout.addView(deviceNameTextView, connectParams);
        connectButton = new Button(context);
        connectButton.setText("Connect");
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConnection();
            }
        });
        connectLayout.addView(connectButton, connectParams);

        connectStatus = new TextView(context);
        connectStatus.setText("Disconnected");
        connectLayout.addView(connectStatus, connectParams);

        setContentView(connectLayout);
    }
    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
    public void open(){
        showAtLocation(getContentView(), Gravity.CENTER, 0,0);
        update();
        setOpen(true);
    }
    public void close(){
        dismiss();
        setOpen(false);
    }
    public void startConnection(){
        String ipAddress = ipAddressEditText.getText().toString();
        String portString = portEditText.getText().toString();
        int port;
        try{
            port = Integer.parseInt(portString);
            if(ipAddress.length() > 0){
                controllerModel.connect(ipAddress, port);
            }
        } catch( NumberFormatException e){
            connectStatus.setText("Port Invalid!");
        }
    }

    public void setConnected(boolean isConnected) {
        connectStatus.setText(isConnected ? "Connected" :"Disconnected");
    }
}
