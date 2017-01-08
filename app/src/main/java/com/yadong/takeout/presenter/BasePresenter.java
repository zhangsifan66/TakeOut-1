package com.yadong.takeout.presenter;

import com.yadong.takeout.presenter.api.ResponseInfoApi;
import com.yadong.takeout.utils.RetrofitUtils;

/**
 * P层(业务逻辑层)公共代码的封装
 * <p>
 * 联网
 * 数据库
 */

public class BasePresenter {


    protected final ResponseInfoApi mApi;

    public BasePresenter() {

        //网络
        mApi = RetrofitUtils.createService(ResponseInfoApi.class);


    }
}
