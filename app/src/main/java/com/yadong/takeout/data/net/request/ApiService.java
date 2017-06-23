package com.yadong.takeout.data.net.request;

import com.yadong.takeout.data.net.bean.HomeInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 *
 */

public interface ApiService {


    String BASE_URL = "http://192.168.1.113:8080/";
    // 登陆
    String LOGIN = "TakeoutService/login";
    // http://localhost:8080/TakeoutService/home
    String HOME = "TakeoutService/home";
    // http://localhost:8080/TakeoutService/goods?sellerId=1
    String GOODS = "TakeoutService/goods";
    //    http://localhost:8080/TakeoutService/address?userId=2163&&&&&&
    String ADDRESS = "TakeoutService/address";

    String ORDER = "TakeoutService/order";
    String PAY = "TakeoutService/pay";

    // 短信登陆的分类值
    int LOGIN_TYPE_SMS = 2;

    /**
     * 获取首页数据
     */
    @GET(HOME)
    Observable<HomeInfo> getHomeData();


}
