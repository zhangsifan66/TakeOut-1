package com.yadong.takeout.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yadong.takeout.ui.fragment.GoodsFragment;
import com.yadong.takeout.ui.fragment.RecommendFragment;
import com.yadong.takeout.ui.fragment.SellerFragment;

/**
 *
 */

public class SellerDetailAdapter extends FragmentPagerAdapter {


    private long mSellerId;

    private String[] titles = new String[]{"商品", "评价", "商家"};

    public SellerDetailAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new GoodsFragment();
                Bundle arguments = new Bundle();
                arguments.putLong("seller_id", mSellerId);
                fragment.setArguments(arguments);
                break;
            case 1:
                fragment = new RecommendFragment();
                break;
            case 2:
                fragment = new SellerFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public void setSellerId(long sellerId) {
        mSellerId = sellerId;
    }
}
