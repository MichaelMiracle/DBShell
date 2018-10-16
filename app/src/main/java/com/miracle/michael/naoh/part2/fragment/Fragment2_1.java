package com.miracle.michael.naoh.part2.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.base.BaseFragment;
import com.miracle.michael.naoh.common.network.ZCallback;
import com.miracle.michael.naoh.common.network.ZClient;
import com.miracle.michael.naoh.common.network.ZResponse;
import com.miracle.michael.naoh.common.view.zradiogroup.ZRadioButton;
import com.miracle.michael.naoh.databinding.Fragment21Binding;
import com.miracle.michael.naoh.part2.Service2;
import com.miracle.michael.naoh.part2.entity.FootballKey;

import java.util.ArrayList;
import java.util.List;


public class Fragment2_1 extends BaseFragment<Fragment21Binding> {

    private LinearLayout.LayoutParams params;
    private List<CategoryDetailFragment2> fragments = new ArrayList<>();

    @Override
    public int getLayout() {
        return R.layout.fragment2_1;
    }

    @Override
    public void initView() {
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 10, 0);
        reqIndex();
    }

    private void reqIndex() {
        ZClient.getService(Service2.class).getSearchKeys().enqueue(new ZCallback<ZResponse<List<FootballKey>>>() {
            @Override
            public void onSuccess(ZResponse<List<FootballKey>> data) {
                for (FootballKey footballKey : data.getData()) {
                    ZRadioButton zRadioButton = new ZRadioButton(mContext);
                    zRadioButton.setLayoutParams(params);
                    zRadioButton.setMinimumWidth(150);
                    zRadioButton.setText(footballKey.getName());
                    zRadioButton.setIndicatorPosition(ZRadioButton.INDICATOR_BOTTOM);
                    binding.zRadiogroup2.addView(zRadioButton);
                    fragments.add(new CategoryDetailFragment2().setReqKey2(footballKey));
                }
                binding.zRadiogroup2.setupWithContainerAndFragments(R.id.container2, fragments.toArray(new CategoryDetailFragment2[0]));
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
