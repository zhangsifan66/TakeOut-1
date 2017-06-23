package com.yadong.takeout.presenter.contract;

import com.yadong.takeout.data.net.bean.HomeInfo;

/**
 *
 */
public interface HomeContract {

    interface View {
        void showHomeData(HomeInfo homeInfo,String msg);
    }

    interface Presenter {
        void getHomeData();
    }

}
