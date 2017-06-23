package com.yadong.takeout.ui.fragment;

import android.animation.ArgbEvaluator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yadong.takeout.R;
import com.yadong.takeout.common.utils.MyToast;
import com.yadong.takeout.dagger.component.AppComponent;
import com.yadong.takeout.dagger.component.DaggerHomeComponent;
import com.yadong.takeout.dagger.module.HomeModule;
import com.yadong.takeout.data.net.bean.HomeInfo;
import com.yadong.takeout.presenter.contract.HomeContract;
import com.yadong.takeout.presenter.fragment.HomePresenter;
import com.yadong.takeout.ui.adapter.HomeAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * home,相对布局实现
 * 上面是沉浸式状态栏
 * 下面是recycleView
 */
public class HomeFragment extends BaseFragment implements HomeContract.View {

    @BindView(R.id.rv_home)
    RecyclerView mRecyclerView;

    @BindView(R.id.home_tv_address)
    TextView mTvAddress;

    @BindView(R.id.ll_title_search)
    LinearLayout mTitleSearch;

    @BindView(R.id.ll_title_container)
    LinearLayout mTitleContainer;

    @Inject
    HomePresenter mHomePresenter;
    private HomeAdapter mHomeAdapter;
    private float sumY = 0;
    private float duration = 150.0f;//在0-150之间去改变头部的透明度
    private ArgbEvaluator evaluator = new ArgbEvaluator();

    @Override
    public int setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initInjector(AppComponent mAppComponent) {
        DaggerHomeComponent
                .builder()
                .appComponent(mAppComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mHomeAdapter = new HomeAdapter();
        mRecyclerView.setAdapter(mHomeAdapter);
    }

    @Override
    public void updateViews() {
        mHomePresenter.getHomeData();
        mRecyclerView.addOnScrollListener(scrollListener);
    }

    @OnClick({R.id.ll_title_search, R.id.ll_title_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_search:
                break;
            case R.id.ll_title_container:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void showHomeData(HomeInfo homeInfo, String msg) {
        if (homeInfo != null) {
            mHomeAdapter.setData(homeInfo);
        } else {
            MyToast.show(getContext(), msg);
        }
    }

    /**
     * RecyclerView的滑动监听
     */
    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            sumY += dy;
            int bgColor;
            if (sumY < 0) {
                bgColor = 0X553190E8;//半透明
            } else if (sumY > duration) {
                bgColor = 0XFF3190E8;//不透明
            } else {
                //半透明到不透明中的颜色,会根据进度进行改变
                bgColor = (int) evaluator.evaluate(sumY / duration, 0X553190E8, 0XFF3190E8);
            }
            mTitleContainer.setBackgroundColor(bgColor);
        }
    };

}
