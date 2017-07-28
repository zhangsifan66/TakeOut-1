package com.yadong.takeout.presenter.contract;

import com.yadong.takeout.data.net.bean.StoreMealInfo;

import java.util.List;

/**
 *
 */

public interface GoodsContract {


    interface View {

        void showGroupData(List<StoreMealInfo.GoodsTypeInfo> data, String msg);
    }

    interface Presenter {

        void getGoodsData();
    }

}
