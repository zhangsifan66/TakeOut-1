package com.yadong.takeout.presenter.fragment;

import com.yadong.takeout.data.model.HomeModel;
import com.yadong.takeout.data.model.HomeModelImpl;
import com.yadong.takeout.presenter.contract.HomeContract;

/**
 *
 *
 */

public class HomePresenter implements  HomeContract.Presenter{


    private  HomeModelImpl mModel;
    private HomeContract.View mView;
    public HomePresenter(HomeContract.View  view, HomeModelImpl model){
        this.mView=view;
        this.mModel=model;


    }


    @Override
    public void getData() {
        mModel.getRecommendData("", new HomeModel.RecommendCallBack() {
            @Override
            public void requestSuccess() {

            }

            @Override
            public void requestFail(String msg) {

            }
        });
    }
}
