package com.yadong.takeout.ui.app;

import android.app.Application;

import com.yadong.takeout.utils.RetrofitUtils;

/**
 *
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitUtils.getInstance().initOkHttp(this);
    }
}
