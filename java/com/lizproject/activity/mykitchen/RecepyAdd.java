package com.lizproject.activity.mykitchen;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;


public class RecepyAdd extends BaseActivity implements DialogInterface.OnDismissListener {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_recepy);


    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.i("gol","gabito");
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
