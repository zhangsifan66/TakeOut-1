package com.yadong.takeout.dagger.module.app;

import com.yadong.takeout.ui.app.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * App级别的Module
 */

@Module
public class AppModule {


    private App mApplication;

    public AppModule(App application) {
        this.mApplication = application;
    }


    @Provides
    @Singleton
    public App provideMyApplication() {
        return mApplication;
    }
}
