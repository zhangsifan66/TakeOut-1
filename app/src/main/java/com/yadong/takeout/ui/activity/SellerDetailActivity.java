package com.yadong.takeout.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.yadong.takeout.R;
import com.yadong.takeout.dagger.component.AppComponent;
import com.yadong.takeout.ui.adapter.SellerDetailAdapter;

import butterknife.BindView;

/**
 * 商家详情页面
 */

public class SellerDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tabs)
    TabLayout mTabs;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private int mSellerId;
    private String mName;

    @Override
    public int setLayout() {
        return R.layout.activity_seller_detail;
    }

    @Override
    public void initInjector(AppComponent appComponent) {

    }

    @Override
    public void initViews() {
        getIntentData();
    }

    /**
     * 获取从 首页的列表中 传递过来的 id和name数据
     */
    private void getIntentData() {
        Intent intent = getIntent();
        mSellerId = intent.getIntExtra("seller_id", -1);
        mName = intent.getStringExtra("name");
    }

    @Override
    public void updateViews() {
        setToolBar();
        setTabLayoutAndAdapter();
    }

    /**
     * 设置ToolBar
     */
    private void setToolBar() {
        mToolbar.setTitle(mName);// Toolbar的相关配置需要在setSupportActionBar之前完成
        setSupportActionBar(mToolbar);// 替换ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 设置TabLayout和ViewPager的联动操作(viewPager中填充的是fragment)
     */
    private void setTabLayoutAndAdapter() {
        SellerDetailAdapter adapter = new SellerDetailAdapter(getSupportFragmentManager());

        adapter.setSellerId(mSellerId);
        mViewPager.setAdapter(adapter);
        mTabs.setupWithViewPager(mViewPager);
    }


}
