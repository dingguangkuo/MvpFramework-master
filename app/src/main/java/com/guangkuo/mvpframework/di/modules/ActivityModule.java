package com.guangkuo.mvpframework.di.modules;

import android.app.Activity;
import android.content.Context;

import com.guangkuo.mvpframework.di.scopes.ContextLife;
import com.guangkuo.mvpframework.di.scopes.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @PerActivity
    @Provides
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return mActivity;
    }
}