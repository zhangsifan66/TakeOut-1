package com.yadong.takeout.common.app;

import android.app.Application;

import com.yadong.takeout.dagger.component.AppComponent;
import com.yadong.takeout.dagger.component.DaggerAppComponent;
import com.yadong.takeout.dagger.module.AppModule;
import com.yadong.takeout.dagger.module.HttpModule;


/**
 *
 */

public class App extends Application {
    private static App instance;
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initInjector();
    }

    /**
     * 初始化注射器
     */
    private void initInjector() {
        // 这里不做注入操作，只提供一些全局单例数据
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .httpModule(new HttpModule())
                .build();
    }


    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
