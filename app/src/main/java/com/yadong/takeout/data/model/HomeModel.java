package com.yadong.takeout.data.model;

/**
 *
 *
 * M层接口
 *
 * 接口的目的:
 * 让实现类实现并能通过回调接口把数据传给P层
 *
 */
public interface HomeModel {

    //获取数据
    void getRecommendData(String jsonParam,RecommendCallBack callBack);

    //回调接口,把数据传递出去
    interface  RecommendCallBack{
        void requestSuccess();
        void requestFail(String  msg);
    }





}
