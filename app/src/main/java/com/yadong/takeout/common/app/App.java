package com.yadong.takeout.common.app;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;
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

        initSdk();
        initInjector();
    }

    /**
     * 初始化SDK
     */
    private void initSdk() {
        //友盟统计和多渠道打包
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType. E_UM_NORMAL);
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
