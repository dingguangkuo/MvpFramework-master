package com.guangkuo.mvpframework.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.guangkuo.mvpframework.R;
import com.guangkuo.mvpframework.app.App;
import com.guangkuo.mvpframework.data.remote.error.ApiException;
import com.guangkuo.mvpframework.di.components.DaggerFragmentComponent;
import com.guangkuo.mvpframework.di.components.FragmentComponent;
import com.guangkuo.mvpframework.di.modules.FragmentModule;
import com.guangkuo.mvpframework.module.user.LoginActivity;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * BaseFragment
 *
 * @param <P> Presenter
 */
public abstract class BaseFragment<P extends BasePresenter> extends RxFragment implements BaseContract.BaseView {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    @Nullable
    @Inject
    protected P mPresenter;
    protected FragmentComponent mFragmentComponent;
    private Unbinder unbinder;
    private View mRootView;

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initView(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragmentComponent();
        initInjector();
        attachView();
        if (!NetworkUtils.isConnected()) {
            showNoNet();
        }
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            if (getFragmentManager() != null) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (isSupportHidden) {
                    ft.hide(this);
                } else {
                    ft.show(this);
                }
                ft.commit();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflaterView(inflater, container);
        unbinder = ButterKnife.bind(this, mRootView);
        initView(mRootView);
        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        detachView();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void stopLoading() {
    }

    @Override
    public void onLoadFailed(ApiException ex) {
    }

    @Override
    public void onLoadSuccess(Object result) {
    }

    @Override
    public void showNoNet() {
        ToastUtils.showShort(R.string.no_network_connection);
    }

    @Override
    public <RD> LifecycleTransformer<RD> bindToLife() {
        return this.bindToLifecycle();
    }

    /**
     * 初始化FragmentComponent
     */
    private void initFragmentComponent() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            mFragmentComponent = DaggerFragmentComponent.builder()
                    .appComponent(((App) activity.getApplication()).getAppComponent())
                    .fragmentModule(new FragmentModule(this))
                    .build();
        }
    }

    /**
     * 贴上view
     */
    @SuppressWarnings("unchecked")
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 分离view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * 设置View
     *
     * @param inflater
     * @param container
     */
    private void inflaterView(LayoutInflater inflater, @Nullable ViewGroup container) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
    }

    @Override
    public void jumpToLogin() {
        ActivityUtils.startActivity(LoginActivity.class);
    }
}
