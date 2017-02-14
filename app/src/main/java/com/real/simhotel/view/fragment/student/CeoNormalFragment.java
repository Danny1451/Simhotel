package com.real.simhotel.view.fragment.student;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.real.simhotel.R;
import com.real.simhotel.events.BaseStatus;
import com.real.simhotel.presenter.CeoInitPresenter;
import com.real.simhotel.presenter.CeoNormalPresenter;
import com.real.simhotel.presenter.base.Presenter;
import com.real.simhotel.view.adapter.DynamicListAdapter;
import com.real.simhotel.view.adapter.DynamicListDecoration;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liudan on 2016/12/16.
 */
public class CeoNormalFragment extends BaseFragment{

    @Inject
    DynamicListAdapter mAdapter;

    @Bind(R.id.ceo_normal_rv)
    RecyclerView mList;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this,view);

        mAdapter = new DynamicListAdapter(mActivity)
                .setChooseInterface(getChildPresenter().getChooseInterface());


        mList.setLayoutManager(new LinearLayoutManager(mActivity));

        mList.setAdapter(mAdapter);
        mList.addItemDecoration(new DynamicListDecoration(mActivity,DynamicListDecoration.VERTICAL_LIST));

    }

    @Override
    protected void initData() {
        mPresenter.requestData();
    }

    @Override
    public void loadData() {

    }

    public void loadList(List<DynamicListModel> list){

        if (list != null)
            mAdapter.setDataList(list);


    }

    public void reload(){
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ceo_normal;
    }

    @Override
    protected CeoNormalPresenter getChildPresenter() {
        return new CeoNormalPresenter(this);
    }

    @Override
    protected View getLoaingTargetView() {
        return mList;
    }

    @Override
    public void handlerStatus(BaseStatus status) {
        super.handlerStatus(status);
        getChildPresenter().handleStatus(status);
    }
}
