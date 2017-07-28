package com.yadong.takeout.dagger.module;

import com.yadong.takeout.data.model.GoodsModel;
import com.yadong.takeout.data.net.request.ApiService;
import com.yadong.takeout.presenter.contract.GoodsContract;
import com.yadong.takeout.presenter.fragment.GoodsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class GoodsModule {

    private GoodsContract.View mView;

    public GoodsModule(GoodsContract.View view) {
        this.mView = view;
    }

    @Provides
    public GoodsContract.View provideView() {
        return mView;
    }

    @Provides
    public GoodsModel provideModel(ApiService service) {
        return new GoodsModel(service);
    }

    @Provides
    public GoodsPresenter providePresenter(GoodsContract.View view, GoodsModel model) {
        return new GoodsPresenter(view, model);
    }
}
