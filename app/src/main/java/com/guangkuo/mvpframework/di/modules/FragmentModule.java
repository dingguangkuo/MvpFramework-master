package com.guangkuo.mvpframework.di.modules;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.guangkuo.mvpframework.di.scopes.ContextLife;
import com.guangkuo.mvpframework.di.scopes.PerFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @PerFragment
    @Provides
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mFragment.getContext();
    }

    @Provides
    @PerFragment
    public Activity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Fragment provideFragment() {
        return mFragment;
    }
}