package com.yadong.takeout.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.yadong.takeout.R;
import com.yadong.takeout.common.app.App;
import com.yadong.takeout.data.net.bean.HomeInfo;
import com.yadong.takeout.ui.activity.SellerDetailActivity;

/**
 * 首页的adapter
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    // 分析条目的样式：三种
    private final int TYPE_HEAD = 0;
    private final int TYPE_SELLER = 1;
    private final int TYPE_RECOMMEND = 2;

    private HomeInfo mHomeInfo;

    public void setData(HomeInfo homeInfo) {
        this.mHomeInfo = homeInfo;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == TYPE_HEAD) {
            return TYPE_HEAD;
        } else {
            HomeInfo.DataBean.BodyBean bodyBean = mHomeInfo.data.body.get(position - 1);
            int type = bodyBean.type;
            return type == 0 ? TYPE_SELLER : TYPE_RECOMMEND;
        }
    }

    @Override
    public int getItemCount() {
        int count;
        if (mHomeInfo == null || mHomeInfo.data == null) {
            count = 0;
        } else {
            count = mHomeInfo.data.body.size() + 1;
        }
        return count;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_HEAD:
                viewHolder = new HeadHolder(layoutInflater.inflate(R.layout.item_title, parent, false));
                break;

            case TYPE_SELLER:
                viewHolder = new SellerHolder(layoutInflater.inflate(R.layout.item_seller, parent, false));
                break;

            case TYPE_RECOMMEND:
                viewHolder = new RecommendHolder(layoutInflater.inflate(R.layout.item_division, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = holder.getItemViewType();
        switch (type) {
            case TYPE_HEAD:
                ((HeadHolder) holder).setData(mHomeInfo.data.head);
                break;
            case TYPE_SELLER:
                HomeInfo.DataBean.BodyBean bodyBean = mHomeInfo.data.body.get(position - 1);
                ((SellerHolder) holder).setData(bodyBean.seller);
                break;
            case TYPE_RECOMMEND:
                ((RecommendHolder) holder).setData(mHomeInfo.data.body.get(position - 1));
                break;
        }
    }

    /**
     * 头容器管理
     */
    static class HeadHolder extends RecyclerView.ViewHolder {

        private HomeInfo.DataBean.HeadBean data;
        private SliderLayout sliderLayout;
        private LinearLayout categoryContainer;

        public HeadHolder(View itemView) {
            super(itemView);
            sliderLayout = (SliderLayout) itemView.findViewById(R.id.slider);
            categoryContainer = (LinearLayout) itemView.findViewById(R.id.catetory_container);
        }

        public void setData(HomeInfo.DataBean.HeadBean head) {
            this.data = head;
            sliderLayout.removeAllSliders();
            if (data != null && data.promotionList.size() > 0) {
                for (HomeInfo.DataBean.HeadBean.PromotionListBean item : data.promotionList) {
                    TextSliderView textSliderView = new TextSliderView(App.getInstance());
                    textSliderView.image(item.pic);
                    textSliderView.description(item.info);
                    sliderLayout.addSlider(textSliderView);
                }
            }

            if (data != null && data.categorieList.size() > 0) {
                categoryContainer.removeAllViews();
                // 0、1  2、3  4、5
                // 0:创建整个条目的布局，将数据设置好后添加到条目中
                // 1:设置数据
                View item = null;
                for (int i = 0; i < data.categorieList.size(); i++) {
                    HomeInfo.DataBean.HeadBean.CategorieListBean category = data.categorieList.get(i);

                    if (i % 2 == 0) {
                        // 每个条目中的第一个元素
                        item = View.inflate(App.getInstance(), R.layout.item_home_head_category, null);
                        //Picasso.with(App.getInstance()).load(category.pic).into((ImageView) item.findViewById(R.id.top_iv));
                        ((TextView) item.findViewById(R.id.top_tv)).setText(category.name);
                        categoryContainer.addView(item);
                    }
                    if (i % 2 != 0) {
                        //Picasso.with(App.getInstance()).load(category.pic).into((ImageView) item.findViewById(R.id.bottom_iv));
                        ((TextView) item.findViewById(R.id.bottom_tv)).setText(category.name);
                    }
                }
            }
        }
    }

    /**
     * 商家容器管理
     */
    class SellerHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvCount;
        private  HomeInfo.DataBean.BodyBean.SellerBean seller;

        public SellerHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(App.getInstance(), SellerDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 由于使用了Application的上下文，需要增加该配置信息（错误日志中会提示）
                    intent.putExtra("seller_id", seller.id);
                    intent.putExtra("name", seller.name);
                    App.getInstance().startActivity(intent);
                }
            });
        }

        public void setData(HomeInfo.DataBean.BodyBean.SellerBean seller) {
            this.seller=seller;
            tvTitle.setText(seller.name);
        }
    }

    /**
     * 大家都在找的条目管理
     */
    class RecommendHolder extends RecyclerView.ViewHolder {

        public RecommendHolder(View itemView) {
            super(itemView);
        }

        public void setData(HomeInfo.DataBean.BodyBean bodyBean) {

        }
    }
}



