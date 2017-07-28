package com.yadong.takeout.presenter.fragment;

import com.yadong.takeout.data.model.GoodsModel;
import com.yadong.takeout.data.net.bean.StoreMealInfo;
import com.yadong.takeout.presenter.contract.GoodsContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */

public class GoodsPresenter implements GoodsContract.Presenter {

    private GoodsModel mModel;
    private GoodsContract.View mView;

    public GoodsPresenter(GoodsContract.View view, GoodsModel model) {
        this.mView = view;
        this.mModel = model;
    }

    @Override
    public void getGoodsData() {
        mModel
                .getHomeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<StoreMealInfo>() {
                    @Override
                    public void accept(@NonNull StoreMealInfo storeMealInfo) throws Exception {
                        mView.showGroupData(storeMealInfo.data,null);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showGroupData(null,throwable.getMessage());
                    }
                });

    }


}
