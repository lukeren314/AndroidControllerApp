package com.example.androidpad;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SettingsMenu extends PopupWindow {
    private LinearLayout settingsLayout;
    private LinearLayout.LayoutParams settingsParams;
    private TextView title;
    private TextView controllerLayoutTextView;
    private Spinner controllerLayoutSpinner;
    private boolean open;
    public SettingsMenu(Context context, final ControllerModel controllerModel){
        super(context);
        setFocusable(true);

        settingsParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        settingsLayout = new LinearLayout(context);
        settingsLayout.setOrientation(LinearLayout.VERTICAL);

        settingsLayout.setFocusable(true);
        settingsLayout.setFocusableInTouchMode(true);

        title = new TextView(context);
        title.setText("Settings");
        settingsLayout.addView(title, settingsParams);

        controllerLayoutTextView = new TextView(context);
        controllerLayoutTextView.setText("Controller Layout");
        settingsLayout.addView(controllerLayoutTextView, settingsParams);

        controllerLayoutSpinner = new Spinner(context);
        String[] layoutOptions = controllerModel.getDefaultLayouts();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, layoutOptions);
        controllerLayoutSpinner.setAdapter(adapter);
        controllerLayoutSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                controllerModel.setLayout(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                controllerModel.setLayout(0);
            }
        });
        settingsLayout.addView(controllerLayoutSpinner, settingsParams);
        setContentView(settingsLayout);
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
    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

}
