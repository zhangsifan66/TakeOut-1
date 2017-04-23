package com.yadong.takeout.dagger.module;

import android.app.Application;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * App级别的Module
 */

@Module
public class AppModule {


    private Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }


    @Provides
    @Singleton
    public Application provideMyApplication() {
        return mApplication;
    }


    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }
}
