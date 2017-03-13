package com.yadong.takeout.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yadong.takeout.dagger.component.app.AppComponent;
import com.yadong.takeout.ui.app.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/1/8.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;
    private App mApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());

        mUnbinder = ButterKnife.bind(this);

        mApplication = (App) getApplication();
        AppComponent appComponent = mApplication.getAppComponent();

        initInjector(appComponent);
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
     */
    public abstract void initInjector(AppComponent appComponent);

    /**
     * 初始化
     */
    public abstract void initViews();

    /**
     * 更新视图控件
     */
    public abstract void updateViews();



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除butterKnife
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }
}
