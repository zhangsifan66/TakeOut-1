package com.yadong.takeout.ui.adapter;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yadong.takeout.R;
import com.yadong.takeout.common.app.App;
import com.yadong.takeout.common.utils.AnimationUtils;
import com.yadong.takeout.common.utils.NumberFormatUtils;
import com.yadong.takeout.common.utils.ShoppingCartManager;
import com.yadong.takeout.data.net.bean.StoreMealInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 *
 */

public class MyGroupAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private List<StoreMealInfo.GoodsTypeInfo> mHeadData;//头数据集合
    private List<StoreMealInfo.GoodsTypeInfo.GoodsInfo> mBodyData;//普通条目数据集合

    public MyGroupAdapter(List<StoreMealInfo.GoodsTypeInfo> headDataSet,
                          List<StoreMealInfo.GoodsTypeInfo.GoodsInfo> itemDataSet) {
        this.mHeadData = headDataSet;
        this.mBodyData = itemDataSet;
    }

    /**********************头管理*************************/
    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {

        StoreMealInfo.GoodsTypeInfo.GoodsInfo body = mBodyData.get(position);
        int headIndex = body.headIndex;

        StoreMealInfo.GoodsTypeInfo head = mHeadData.get(headIndex);

        TextView tv = new TextView(parent.getContext());
        tv.setText(head.name);
        tv.setBackgroundColor(parent.getContext().getResources().getColor(R.color.colorItemBg));
        return tv;
    }

    /**
     * 主要是根据普通条目下,他们的头id 是否相同
     * 如果相同就是一组,是一组的话就会走上面的 getHeaderView方法
     */
    @Override
    public long getHeaderId(int position) {
        return mBodyData.get(position).headId;
    }

    /**********************普通条目管理*************************/
    @Override
    public int getCount() {
        return mBodyData == null ? 0 : mBodyData.size();
    }

    @Override
    public Object getItem(int position) {
        return mBodyData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoreMealInfo.GoodsTypeInfo.GoodsInfo data = mBodyData.get(position);
        ItemViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_goods, null);
            holder = new ItemViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ItemViewHolder) convertView.getTag();
        }

        holder.setData(data);
        return convertView;
    }

    class ItemViewHolder {

        private View itemView;
        private StoreMealInfo.GoodsTypeInfo.GoodsInfo data;

        @BindView(R.id.iv_icon)
        ImageView ivIcon;

        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_zucheng)
        TextView tvZucheng;

        @BindView(R.id.tv_yueshaoshounum)
        TextView tvYueshaoshounum;

        @BindView(R.id.tv_newprice)
        TextView tvNewprice;

        @BindView(R.id.tv_oldprice)
        TextView tvOldprice;

        @BindView(R.id.ib_minus)
        ImageButton ibMinus;//减少

        @BindView(R.id.tv_count)
        TextView tvCount;//商品个数

        @BindView(R.id.ib_add)
        ImageButton ibAdd;//增加

        private FrameLayout container;
        private TextView count;

        public ItemViewHolder(View itemView) {
            this.itemView = itemView;
            ButterKnife.bind(this, this.itemView);
        }

        public void setData(StoreMealInfo.GoodsTypeInfo.GoodsInfo data) {
            this.data = data;

            //图片
            Picasso.with(App.getInstance()).load(data.icon).into(ivIcon);
            tvName.setText(data.name);
            if (TextUtils.isEmpty(data.form)) {
                tvZucheng.setVisibility(View.GONE);
            } else {
                tvZucheng.setVisibility(View.VISIBLE);
                tvZucheng.setText(data.form);
            }
            tvYueshaoshounum.setText("月销售" + data.monthSaleNum + "份");
            tvNewprice.setText(NumberFormatUtils.formatDigits(data.newPrice));
            if (data.oldPrice == 0) {
                tvOldprice.setVisibility(View.GONE);
            } else {
                tvOldprice.setVisibility(View.VISIBLE);
                tvOldprice.setText(NumberFormatUtils.formatDigits(data.oldPrice));
                //TextView出现中间的线
                tvOldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }

            // 判断购物重是否有当前条目的商品，如果有需要获取到商品的数据量
            Integer num = ShoppingCartManager.getInstance().getGoodsIdNum(data.id);
            if (num > 0) {
                ibMinus.setVisibility(View.VISIBLE);
                tvCount.setVisibility(View.VISIBLE);
                tvCount.setText(num.toString());
            } else {
                ibMinus.setVisibility(View.INVISIBLE);
                tvCount.setVisibility(View.INVISIBLE);
            }
        }

        @OnClick({R.id.ib_minus, R.id.ib_add})
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_minus:
                    minusHandler(v);
                    break;
                case R.id.ib_add:
                    addHandler(v);
                    break;
            }
        }

        /**
         * 点击增加商品
         */
        private void addHandler(View v) {
            Integer num = ShoppingCartManager.getInstance().addGoods(data);
            if (num == 1) {// 第一次动画执行
                // 动画操作:－的ImageView和数量TextView
                AnimationSet showMinusAnimation = AnimationUtils.getShowMinusAnimation();
                tvCount.startAnimation(showMinusAnimation);
                ibMinus.startAnimation(showMinusAnimation);

                tvCount.setVisibility(View.VISIBLE);
                ibMinus.setVisibility(View.VISIBLE);
            }
            tvCount.setText(num.toString());

            // 处理飞入到购物车的动画
            //            flyToShoppingCart(v);
            //
            //            // 修改气泡提示
            //            if(count==null) {
            //                count = (TextView) container.findViewById(R.id.fragment_goods_tv_count);
            //            }
            //
            //            if(num>0){
            //                count.setVisibility(View.VISIBLE);
            //            }
            //            Integer totalNum=ShoppingCartManager.getInstance().getTotalNum();
            //            count.setText(totalNum.toString());
        }

        /**
         * 点击减少商品
         */
        private void minusHandler(View v) {
            Integer num = ShoppingCartManager.getInstance().minusGoods(data);
            if (num == 0) {
                // 动画处理
                AnimationSet animation = AnimationUtils.getHideMinusAnimation();
                ibMinus.startAnimation(animation);
                tvCount.startAnimation(animation);

                animation.setAnimationListener(new AnimationUtils.AnimationListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ibMinus.setVisibility(View.GONE);
                        tvCount.setVisibility(View.GONE);
                    }
                });
            }
            tvCount.setText(num.toString());

            // 处理购物车气泡的显示
            //            Integer totalNum=ShoppingCartManager.getInstance().getTotalNum();
            //            if(totalNum==0){
            //                count.setVisibility(View.INVISIBLE);
            //            }
            //            count.setText(totalNum.toString());
        }
    }


}
