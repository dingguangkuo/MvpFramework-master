package com.guangkuo.mvpframework.di.app;

import android.app.Activity;
import android.content.Context;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife
    Context getApplicationContext();

    Activity getActivity();
    // 这个部分可以先不写，未来需要注入哪个activity写下就可以了
    // void inject(LoginActivity activity);
    // void inject(RegisterActivity activity);
}