package com.yadong.takeout.ui.fragment;

import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yadong.takeout.R;
import com.yadong.takeout.common.utils.MyToast;
import com.yadong.takeout.dagger.component.AppComponent;
import com.yadong.takeout.dagger.component.DaggerGoodsComponent;
import com.yadong.takeout.dagger.module.GoodsModule;
import com.yadong.takeout.data.net.bean.StoreMealInfo;
import com.yadong.takeout.presenter.contract.GoodsContract;
import com.yadong.takeout.presenter.fragment.GoodsPresenter;
import com.yadong.takeout.ui.adapter.MyGroupAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 *
 */

public class GoodsFragment extends BaseFragment implements GoodsContract.View {

    @BindView(R.id.lv_left)
    ListView mLeftLv;

    @BindView(R.id.lv_right)
    StickyListHeadersListView mGroupLv;

    @BindView(R.id.tv_count)
    TextView mTvCount;

    @BindView(R.id.cart)
    RelativeLayout mCart;

    @Inject
    GoodsPresenter mPresenter;

    private List<StoreMealInfo.GoodsTypeInfo> mHeadData = new ArrayList<>();//头数据集合
    private List<StoreMealInfo.GoodsTypeInfo.GoodsInfo> mBodyData = new ArrayList<>();//普通数据集合

    @Override
    public int setLayout() {
        return R.layout.fragment_goods;
    }

    @Override
    public void initInjector(AppComponent mAppComponent) {
        DaggerGoodsComponent
                .builder()
                .appComponent(mAppComponent)
                .goodsModule(new GoodsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void updateViews() {
        mPresenter.getGoodsData();
    }

    @Override
    public void showGroupData(List<StoreMealInfo.GoodsTypeInfo> data, String msg) {
        if (data != null) {
            handleData(data);
            setLeftLv();
            setGroupLv();
        } else {
            MyToast.show(getContext(), msg);
        }
    }

    /**
     * 处理数据并增加自己这边需要用到的字段到 集合中
     * @param data
     */
    private void handleData(List<StoreMealInfo.GoodsTypeInfo> data) {
        mHeadData.clear();
        mBodyData.clear();
        mHeadData.addAll(data);
        //遍历头条目
        for (int x = 0; x < mHeadData.size(); x++) {
            StoreMealInfo.GoodsTypeInfo head = mHeadData.get(x);
            //遍历 普通条目
            for (int y = 0; y < head.list.size(); y++) {
                StoreMealInfo.GoodsTypeInfo.GoodsInfo body = head.list.get(y);
                body.headId = head.id;
                body.headIndex = x;

                if (y == 0) {
                    //目的是为了拿到头的 索引id,用于定位
                    head.groupFirstIndex = mBodyData.size();
                }
                mBodyData.add(body);
            }
        }
    }


    private void setLeftLv() {

    }

    private void setGroupLv() {
        MyGroupAdapter myGroupAdapter = new MyGroupAdapter(mHeadData, mBodyData);
        mGroupLv.setAdapter(myGroupAdapter);
    }


    @OnClick(R.id.cart)
    public void onViewClicked() {
    }

}
