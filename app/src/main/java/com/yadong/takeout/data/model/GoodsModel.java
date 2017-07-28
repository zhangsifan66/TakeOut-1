package com.yadong.takeout.data.model;

import com.yadong.takeout.common.utils.GsonUtil;
import com.yadong.takeout.common.utils.LocalJson;
import com.yadong.takeout.data.net.bean.StoreMealInfo;
import com.yadong.takeout.data.net.request.ApiService;

import io.reactivex.Observable;

/**
 *
 */

public class GoodsModel {

    private ApiService mApiService;

    public GoodsModel(ApiService apiService) {
        this.mApiService = apiService;
    }

    /**
     * 用假数据代替
     */
    public Observable<StoreMealInfo> getHomeData() {
        StoreMealInfo info = GsonUtil.processJSON(LocalJson.STORE_MEAL_JSON.trim(), StoreMealInfo.class);
        return Observable.just(info);
        //正常的写下面的代码
        //      return mApiService.getGoodsData();
    }
}
