package com.guangkuo.mvpframework.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.guangkuo.mvpframework.app.App;
import com.guangkuo.mvpframework.di.scopes.ContextLife;
import com.guangkuo.mvpframework.di.scopes.PerApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides
    @PerApp
    @ContextLife
    public Context provideApplicationContext() {
        return mApp.getApplicationContext();
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mApp);
    }
}