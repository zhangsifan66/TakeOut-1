package com.yadong.takeout.data.model;


import com.yadong.takeout.common.utils.GsonUtil;
import com.yadong.takeout.common.utils.LocalJson;
import com.yadong.takeout.data.net.bean.HomeInfo;
import com.yadong.takeout.data.net.request.ApiService;

import io.reactivex.Observable;

/**
 *
 */
public class HomeModel {

    private ApiService mApiService;

    public HomeModel(ApiService apiService) {
        this.mApiService = apiService;
    }

    /**
     * 用假数据代替
     */
    public Observable<HomeInfo> getHomeData() {
        HomeInfo info = GsonUtil.processJSON(LocalJson.HOME_JSON.trim(), HomeInfo.class);
        return Observable.just(info);
        //正常的写下面的代码
//      return mApiService.getHomeData();
    }
}
