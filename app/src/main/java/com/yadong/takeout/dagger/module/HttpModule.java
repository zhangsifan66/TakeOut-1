package com.yadong.takeout.dagger.module;

import android.app.Application;

import com.google.gson.Gson;
import com.yadong.takeout.data.net.request.ApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * App级别的Module,用于网络请求
 */

@Module
public class HttpModule {


    @Provides
    @Singleton
    public OkHttpClient provideOkhttpClient(Application application, Gson gson) {
         HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
         interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // 自己定义的拦截器
        //CommonParamsInterceptor interceptor = new CommonParamsInterceptor(application, gson);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)       //设置连接超时
                .readTimeout(10000L, TimeUnit.MILLISECONDS)          //设置读取超时
                .writeTimeout(10000L, TimeUnit.MILLISECONDS)         //设置写入超时
                .cache(new Cache(application.getCacheDir(), 10 * 1024 * 1024))   //设置缓存目录和10M缓存
                .addInterceptor(interceptor)    //添加日志拦截器（该方法也可以设置公共参数，头信息）
                .build();

        return client;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)        //设置OkHttp
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //  添加数据解析ConverterFactory
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //添加RxJava
                .build();

        return retrofit;
    }

    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }


}
