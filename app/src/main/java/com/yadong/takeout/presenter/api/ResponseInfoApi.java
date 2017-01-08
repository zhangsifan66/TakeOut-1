package com.yadong.takeout.presenter.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2016/12/29.
 */

public interface ResponseInfoApi {

    @POST("")
    Call<ResponseBody> user();
}
