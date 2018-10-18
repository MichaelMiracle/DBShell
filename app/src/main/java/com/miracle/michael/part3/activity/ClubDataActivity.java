package com.miracle.michael.part3.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.view.zradiogroup.ZRadioButton;
import com.miracle.databinding.ActivityClubDataBinding;
import com.miracle.michael.part3.Service3;
import com.miracle.michael.part3.entity.ClubKey;
import com.miracle.michael.part3.entity.DataType;
import com.miracle.michael.part3.fragment.SimpleWebFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubDataActivity extends BaseActivity<ActivityClubDataBinding> {
    private ClubKey clubKey;

    private List<SimpleWebFragment> fragments = new ArrayList<>();
    private LinearLayout.LayoutParams params;

    @Override
    public int getLayout() {
        return R.layout.activity_club_data;
    }

    @Override
    public void initView() {
        clubKey = (ClubKey) getIntent().getSerializableExtra("clubkey");
        setTitle(clubKey.getName().replace("比分", ""));
        params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reqData();
    }

    private void reqData() {
        ZClient.getService(Service3.class).getType(clubKey.getId()).enqueue(new Callback<ZResponse<List<DataType>>>() {
            @Override
            public void onResponse(Call<ZResponse<List<DataType>>> call, Response<ZResponse<List<DataType>>> response) {
                for (DataType dataType : response.body().getData()) {
                    ZRadioButton zRadioButton = new ZRadioButton(mContext);
                    zRadioButton.setLayoutParams(params);
                    zRadioButton.setText(dataType.getTitle().replace(clubKey.getName().replace("比分", ""), ""));
                    zRadioButton.setIndicatorPosition(ZRadioButton.INDICATOR_BOTTOM);
                    binding.zRadiogroup.addView(zRadioButton);
                    fragments.add(new SimpleWebFragment().setType(dataType.getType()));
                }
                binding.zRadiogroup.setupWithContainerAndFragments(R.id.container, fragments.toArray(new SimpleWebFragment[0]));
            }

            @Override
            public void onFailure(Call<ZResponse<List<DataType>>> call, Throwable t) {
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
