package com.yadong.takeout.dagger.module;

import com.yadong.takeout.data.model.HomeModel;
import com.yadong.takeout.data.net.request.ApiService;
import com.yadong.takeout.presenter.contract.HomeContract;
import com.yadong.takeout.presenter.fragment.HomePresenter;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class HomeModule {

    private HomeContract.View mView;

    public HomeModule(HomeContract.View view) {
        this.mView = view;
    }

    @Provides
    public HomeContract.View provideView() {
        return mView;
    }

    @Provides
    public HomeModel provideModel(ApiService service) {
        return new HomeModel(service);
    }

    @Provides
    public HomePresenter providePresenter(HomeContract.View view, HomeModel model) {
        return new HomePresenter(view, model);
    }
}
