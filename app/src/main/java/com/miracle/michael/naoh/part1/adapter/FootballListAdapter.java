package com.miracle.michael.naoh.part1.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.adapter.RecyclerViewAdapter;
import com.miracle.michael.naoh.common.network.GlideApp;
import com.miracle.michael.naoh.part1.entity.Football;

public class FootballListAdapter extends RecyclerViewAdapter<Football> {
    private Context context;

    public FootballListAdapter(Context context) {
        super(R.layout.item_football);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Football item) {
        helper.setText(R.id.tvTitle, item.getTitle());
        helper.setText(R.id.tvTime, item.getTime());
//        Glide.with(context)
//                .load(item.getThumb())
//                .into((ImageView) helper.getView(R.id.iv));
        String thumb = item.getThumb();
        if (TextUtils.isEmpty(thumb)) {
            helper.setGone(R.id.iv, false);
//            helper.setImageResource(R.id.iv, R.mipmap.default_image);
        } else {
            GlideApp.with(context).load(thumb)
                    .placeholder(R.mipmap.defaule_img)
                    .error(R.mipmap.empty)
                    .into((ImageView) helper.getView(R.id.iv));
            helper.setGone(R.id.iv, true);
        }

    }
}
