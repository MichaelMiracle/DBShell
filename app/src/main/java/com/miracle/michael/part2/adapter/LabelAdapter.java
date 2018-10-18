package com.miracle.michael.part2.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miracle.R;
import com.miracle.base.adapter.AbsListViewAdapter;
import com.miracle.michael.part1.entity.Football;


/**
 * Created by Administrator on 2018/3/5.
 */

public class LabelAdapter extends AbsListViewAdapter<Football, LabelAdapter.ViewHolder> {

    private int selectedPosition = -1;

    public LabelAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemViewLayoutId() {
        return R.layout.item_label;
    }

    @Override
    protected void bindView(int position, Football football, ViewHolder viewHolder) {
        viewHolder.tvTitle.setText(football.getTitle());
        viewHolder.tvTime.setText(football.getTime());
        Glide.with(getContext()).load(football.getThumb()).into(viewHolder.ivThumb);
    }


    @Override
    protected ViewHolder loadHolder(View v) {
        return new ViewHolder(v);
    }

    public void setSelectPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    final class ViewHolder {
        TextView tvTitle;
        ImageView ivThumb;
        TextView tvTime;

        ViewHolder(View v) {
            tvTitle = v.findViewById(R.id.tvTitle);
            ivThumb = v.findViewById(R.id.ivThumb);
            tvTime = v.findViewById(R.id.tvTime);
        }
    }
}
