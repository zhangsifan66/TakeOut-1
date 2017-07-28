package com.yadong.takeout.ui.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
import com.yadong.takeout.ui.adapter.MyHeadAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 *
 */

public class GoodsFragment extends BaseFragment implements
        GoodsContract.View,
        AdapterView.OnItemClickListener,
        AbsListView.OnScrollListener {

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
    private MyHeadAdapter mHeadAdapter;

    /**
     * 看是否是用户主动在滑动列表
     */
    private boolean isScroll = false;

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
            showLeftList();
            showGroupList();
        } else {
            MyToast.show(getContext(), msg);
        }
    }

    /**
     * 处理数据并增加自己这边需要用到的字段到 集合中
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

    /**
     * 展示左侧的list
     */
    private void showLeftList() {
        mHeadAdapter = new MyHeadAdapter(mHeadData);
        mLeftLv.setAdapter(mHeadAdapter);
        //左侧list条目的点击事件
        mLeftLv.setOnItemClickListener(this);
    }

    /**
     * 展示右侧的list
     */
    private void showGroupList() {
        MyGroupAdapter mGroupAdapter = new MyGroupAdapter(mHeadData, mBodyData);
        mGroupLv.setAdapter(mGroupAdapter);
        //右侧list的滑动监听
        mGroupLv.setOnScrollListener(this);
    }

    /**
     * 左侧头列表的点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //让左侧点击条目背景高亮
        mHeadAdapter.setSelectedPosition(position);
        //因为我们的头集合中 存有右侧每个组最顶端的 索引
        StoreMealInfo.GoodsTypeInfo info = mHeadData.get(position);
        int groupFirstIndex = info.groupFirstIndex;
        //右侧列表 跟着滚动定位
        mGroupLv.setSelection(groupFirstIndex);

        isScroll = false;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        /*
         * 看是否是用户主动在滑动列表,要不然会出现刷新adapter多次
         * 因为一点击左边头条目,右侧就会跟着动,跟着动就会触发监听,
         * 然后又刷新左侧adapter,而且这个scroll监听很频繁,会一直刷新左边列表
         */
        isScroll = true;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        //如果是用户主动在滑列表,才会去定位左侧的头条目
        if (isScroll) {
            StoreMealInfo.GoodsTypeInfo.GoodsInfo data = mBodyData.get(firstVisibleItem);
            // 当前正在置顶显示的头高亮处理
            mHeadAdapter.setSelectedPosition(data.headIndex);

            // 判断头容器对应的条目是否处于可见状态
            // 获取到第一个可见，和最后一个可见的。比第一个小的，或者比最后一个大的均为不可见
            int firstVisiblePosition = mLeftLv.getFirstVisiblePosition();
            int lastVisiblePosition = mLeftLv.getLastVisiblePosition();
            if (data.headIndex <= firstVisiblePosition || data.headIndex >= lastVisiblePosition) {
                mLeftLv.setSelection(data.headIndex);// 可见处理
            }
        }
    }


    @OnClick(R.id.cart)
    public void onViewClicked() {
    }

}
