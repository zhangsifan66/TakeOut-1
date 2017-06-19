package com.yadong.takeout.ui.activity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yadong.takeout.R;
import com.yadong.takeout.dagger.component.AppComponent;
import com.yadong.takeout.ui.fragment.HomeFragment;
import com.yadong.takeout.ui.fragment.MoreFragment;
import com.yadong.takeout.ui.fragment.OrderFragment;
import com.yadong.takeout.ui.fragment.UserFragment;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * 主Activity,Fragment+导航栏的结构
 */
public class MainActivity extends BaseActivity {


    @BindView(R.id.main_fragment_container)
    FrameLayout mFragmentContainer;

    @BindView(R.id.main_bottome_switcher_container)
    LinearLayout mSwitcherContainer;

    private ArrayList<Fragment> mFragments;

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector(AppComponent appComponent) {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void updateViews() {
        initData();
        initListener();
    }

    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new OrderFragment());
        mFragments.add(new UserFragment());
        mFragments.add(new MoreFragment());
        //默认选中第一个状态栏(这里第一个是home)
        onClickListener.onClick(mSwitcherContainer.getChildAt(0));
    }

    /**
     * 设置每个导航状态栏的点击事件
     */
    private void initListener() {
        int childCount = mSwitcherContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            mSwitcherContainer.getChildAt(i).setOnClickListener(onClickListener);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //得到当前点击的状态栏的  索引(得到这个索引是为了不但要设置自己的状态,还要设置其他导航栏的状态)
            int indexOfChild = mSwitcherContainer.indexOfChild(view);
            changeUI(indexOfChild);
            changeFragment(indexOfChild);
        }
    };

    private void changeFragment(int indexOfChild) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, mFragments.get(indexOfChild))
                .commit();
    }

    /**
     * 改变被点击的自己的ui,同时改变其他导航栏的ui
     * 被点击的状态栏 : 不可以在点击
     * 另外的状态栏  : 可以点击
     */
    private void changeUI(int indexOfChild) {
        int childCount = mSwitcherContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i == indexOfChild) {
                //不可以在点击
                setEnable(mSwitcherContainer.getChildAt(i), false);
            } else {
                //另外的状态栏  可以点击
                setEnable(mSwitcherContainer.getChildAt(i), true);
            }
        }
    }

    /**
     * 设置每个导航栏和其里面的内容 是否可用
     * 因为select是根据 enable进行判断的,所以用递归循环判断就OK
     */
    private void setEnable(View item, boolean b) {
        item.setEnabled(b);
        //循环遍历 每个导航栏里面的 内容
        if (item instanceof ViewGroup) {
            int childCount = ((ViewGroup) item).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = ((ViewGroup) item).getChildAt(i);
                setEnable(childAt, b);
            }
        }
    }


}
