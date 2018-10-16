package com.miracle.michael.naoh.part2.fragment;

import android.view.View;
import android.widget.AdapterView;

import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.base.BaseFragment;
import com.miracle.michael.naoh.common.network.ZCallback;
import com.miracle.michael.naoh.common.network.ZClient;
import com.miracle.michael.naoh.common.network.ZResponse;
import com.miracle.michael.naoh.databinding.Fragment2Binding;
import com.miracle.michael.naoh.part2.Service2;
import com.miracle.michael.naoh.part2.adapter.IndexAdapter;
import com.miracle.michael.naoh.part2.entity.FootballKey;

import java.util.List;


public class Fragment2 extends BaseFragment<Fragment2Binding> {
    private IndexAdapter indexAdapter;

    private CategoryDetailFragment detailFragment;


    @Override
    public int getLayout() {
        return R.layout.fragment2;
    }

    @Override
    public void initView() {
        indexAdapter = new IndexAdapter(mContext);
        binding.indexListView.setAdapter(indexAdapter);
        detailFragment = (CategoryDetailFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.categoryDetailFragment);
    }

    private void reqData() {
        ZClient.getService(Service2.class).getSearchKeys().enqueue(new ZCallback<ZResponse<List<FootballKey>>>() {
            @Override
            public void onSuccess(ZResponse<List<FootballKey>> data) {
                indexAdapter.update(data.getData());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        reqData();
    }

    @Override
    public void initListener() {
        binding.indexListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long itemId) {
                indexAdapter.setSelectPosition(position);
                detailFragment.setReqKey(indexAdapter.getItem(position));
            }
        });

    }


    @Override
    public void onClick(View view) {

    }

}
