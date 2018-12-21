package com.guangkuo.mvpframework.di.app;

import android.content.Context;

import dagger.Component;

@PerApplication
@Component(modules = AppModule.class)
public interface AppComponent {
    @ContextLife
    Context getApplication();
}