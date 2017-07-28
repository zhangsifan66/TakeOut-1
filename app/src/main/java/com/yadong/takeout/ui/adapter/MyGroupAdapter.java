package com.yadong.takeout.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yadong.takeout.R;
import com.yadong.takeout.data.net.bean.StoreMealInfo;

import java.util.List;

import butterknife.ButterKnife;
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

        public ItemViewHolder(View itemView) {
            this.itemView = itemView;
            ButterKnife.bind(this, this.itemView);
        }

        public void setData(StoreMealInfo.GoodsTypeInfo.GoodsInfo data) {

        }
    }

}
