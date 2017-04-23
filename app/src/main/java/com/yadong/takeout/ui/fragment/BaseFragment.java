package com.yadong.takeout.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yadong.takeout.dagger.component.AppComponent;
import com.yadong.takeout.ui.app.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/1/8.
 */

public abstract class BaseFragment extends Fragment {

    private View mRootView;
    private Unbinder mUnbinder;
    private App mApplication;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayout(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mApplication = (App) getActivity().getApplication();
        initInjector(mApplication.getAppComponent());

        initViews();
        updateViews();
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int setLayout();

    /**
     * 用dagger进行注入
     *
     * @param mAppComponent application中得到的
     */
    public abstract void initInjector(AppComponent mAppComponent);

    /**
     * 初始化视图控件
     */
    public abstract void initViews();


    /**
     * 更新视图控件
     */
    public abstract void updateViews();


    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除butterKnife
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }
}
