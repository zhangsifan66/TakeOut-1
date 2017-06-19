package com.yadong.takeout.common.utils;

import android.content.Context;

import com.yadong.takeout.data.net.request.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit工具管理类
 */

public class RetrofitUtils {

    private static Retrofit retrofit;

    public void initOkHttp(Context context) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //CommonParamsInterceptor interceptor = new CommonParamsInterceptor(context, new Gson());
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)       //设置连接超时
                .readTimeout(10000L, TimeUnit.MILLISECONDS)          //设置读取超时
                .writeTimeout(10000L, TimeUnit.MILLISECONDS)         //设置写入超时
                .cache(new Cache(context.getCacheDir(), 10 * 1024 * 1024))   //设置缓存目录和10M缓存
                .addInterceptor(interceptor)    //添加日志拦截器（该方法也可以设置公共参数，头信息）
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)        //设置OkHttp
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //  添加数据解析ConverterFactory
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //添加RxJava
                .build();
    }


    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }


    public static RetrofitUtils getInstance() {
        return SingleLoader.INSTANCE;
    }

    private static class SingleLoader {
        private static final RetrofitUtils INSTANCE = new RetrofitUtils();
    }

}
