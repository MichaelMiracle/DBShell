package com.miracle.michael.naoh.part5.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.adapter.RecyclerViewAdapter;

public class LotteryListAdapter extends RecyclerViewAdapter<String[]> {

    public LotteryListAdapter() {
        super(R.layout.item_lottery);
    }

    @Override
    protected void convert(BaseViewHolder helper, String[] item) {

    }
}
