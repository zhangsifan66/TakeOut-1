package com.yadong.takeout.ui.fragment;

import com.yadong.takeout.R;
import com.yadong.takeout.dagger.component.app.AppComponent;
import com.yadong.takeout.presenter.contract.HomeContract;

/**
 * home,相对布局实现
 * 上面是沉浸式状态栏
 * 下面是recycleView
 */

public class HomeFragment extends  BaseFragment implements HomeContract.View{


    @Override
    public int setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initInjector(AppComponent mAppComponent) {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void updateViews() {

    }

    @Override
    public void show() {


    }
}
