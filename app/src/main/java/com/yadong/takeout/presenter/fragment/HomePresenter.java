package com.yadong.takeout.presenter.fragment;

import com.yadong.takeout.data.model.HomeModel;
import com.yadong.takeout.data.net.bean.HomeInfo;
import com.yadong.takeout.presenter.contract.HomeContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 *
 */
public class HomePresenter implements HomeContract.Presenter {

    private HomeModel mModel;
    private HomeContract.View mView;

    public HomePresenter(HomeContract.View view, HomeModel model) {
        this.mView = view;
        this.mModel = model;
    }

    @Override
    public void getHomeData() {
        mModel
                .getHomeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HomeInfo>() {
                    @Override
                    public void accept(@NonNull HomeInfo homeInfo) throws Exception {
                        mView.showHomeData(homeInfo,null);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showHomeData(null,throwable.getMessage());
                    }
                });
    }
}
