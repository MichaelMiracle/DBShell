package com.miracle.michael.part3.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.michael.part3.entity.ClubKey;

public class ClubKeyAdapter extends RecyclerViewAdapter<ClubKey> {
    public ClubKeyAdapter() {
        super(R.layout.item_club_key);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClubKey item) {
        helper.setText(R.id.tvIndex, item.getName().replace("比分", ""));
    }
}
