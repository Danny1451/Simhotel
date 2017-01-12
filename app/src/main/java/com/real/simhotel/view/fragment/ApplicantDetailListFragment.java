package com.real.simhotel.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.real.simhotel.R;
import com.real.simhotel.model.Applicant;
import com.real.simhotel.presenter.base.Presenter;
import com.real.simhotel.view.adapter.DynamicListAdapter;
import com.real.simhotel.view.adapter.DynamicListDecoration;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by liudan on 2017/1/11.
 * 列表样式的详情
 */
public class ApplicantDetailListFragment extends DetailFragment<List<DynamicListModel>> {


    @Bind(R.id.detail_info_rv)
    RecyclerView mList;

    @Inject
    DynamicListAdapter mAdapter;

    @Override
    public void initView() {
        super.initView();

        //设置可见
        listLayout.setVisibility(View.VISIBLE);

        //设置Adapter
        mAdapter = new DynamicListAdapter(mActivity);
        mList.setAdapter(mAdapter);
        mList.setLayoutManager(new LinearLayoutManager(mActivity));
        mList.addItemDecoration(new DynamicListDecoration(mActivity,DynamicListDecoration.VERTICAL_LIST));


    }

    @Override
    public void renderView(List<DynamicListModel> model) {
        super.renderView(model);

        //刷新数据
        mAdapter.setDataList(model);

    }
}
