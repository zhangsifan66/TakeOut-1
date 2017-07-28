package com.yadong.takeout.ui.adapter;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yadong.takeout.R;
import com.yadong.takeout.common.app.App;
import com.yadong.takeout.data.net.bean.StoreMealInfo;

import java.util.List;


/**
 *
 */

public class MyHeadAdapter extends BaseAdapter {

    private List<StoreMealInfo.GoodsTypeInfo> mHeadData;

    public MyHeadAdapter(List<StoreMealInfo.GoodsTypeInfo> data) {
        this.mHeadData = data;
    }

    @Override
    public int getCount() {
        return mHeadData == null ? 0 : mHeadData.size();
    }

    @Override
    public Object getItem(int position) {
        return mHeadData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoreMealInfo.GoodsTypeInfo data = mHeadData.get(position);
        HeadViewHolder holder;
        if (convertView == null) {
            convertView = new TextView(parent.getContext());
            holder = new HeadViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HeadViewHolder) convertView.getTag();
        }

        holder.setData(data);

        if (position == mSelectedPosition) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

        return convertView;
    }


    private int mSelectedPosition;

    public void setSelectedPosition(int index) {
        mSelectedPosition = index;
        notifyDataSetChanged();
    }

    class HeadViewHolder {

        private View itemView;

        public HeadViewHolder(View itemView) {
            this.itemView = itemView;
        }

        public void setData(StoreMealInfo.GoodsTypeInfo data) {
            TextView tv = (TextView) this.itemView;
            tv.setText(data.name);
            tv.setTextSize(12);

            tv.setBackgroundColor(App.getInstance().getResources().getColor(R.color.colorItemBg));


            int h = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50,
                    tv.getResources().getDisplayMetrics()) + 0.5f);

            tv.setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h));
            tv.setGravity(Gravity.CENTER);
        }
    }

}
