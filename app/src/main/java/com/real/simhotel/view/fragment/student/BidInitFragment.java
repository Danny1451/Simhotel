package com.real.simhotel.view.fragment.student;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.real.simhotel.R;
import com.real.simhotel.presenter.BitInitPresenter;
import com.real.simhotel.view.adapter.DynamicListAdapter;
import com.real.simhotel.view.adapter.DynamicListDecoration;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liudan on 2016/12/21.
 */
public class BidInitFragment extends BaseFragment<BitInitPresenter> {


    @Inject
    DynamicListAdapter mAdapter;

    @Bind(R.id.bid_applicants_rv)
    RecyclerView mList;

    @Bind(R.id.bid_confirm_btn)
    Button mConfirm;

    //详情页面
    ApplicantInfoFragment mDetailFragment;


    public ApplicantInfoFragment getDetailFragment(){
        if (mDetailFragment == null){
            mDetailFragment = new ApplicantInfoFragment();
        }

        return mDetailFragment;
    }
    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        ButterKnife.bind(this,view);

        mAdapter = new DynamicListAdapter(mActivity);

        //绑定触摸相应
        mAdapter.setRowInterface(new DynamicListAdapter.DynamicListRowInterface() {
            @Override
            public void onSelected(int pos, DynamicListModel model) {

                mPresenter.onClickApplicantsRow(pos,model);
            }
        });

        mList.setLayoutManager(new LinearLayoutManager(mActivity));

        mList.setAdapter(mAdapter);
        mList.addItemDecoration(new DynamicListDecoration(mActivity,DynamicListDecoration.VERTICAL_LIST));


        mDetailFragment = new ApplicantInfoFragment();
        //绑定按钮事件



        this.getHoldingActivity().getSupportFragmentManager().beginTransaction().replace(R.id.bid_applicant_detail,mDetailFragment).commitAllowingStateLoss();

        mDetailFragment.setConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //点击了确定 通知presenter 刷新list

                mPresenter.updateApplicantsRow((DynamicListModel)view.getTag());

            }
        });
    }

    @OnClick({R.id.bid_confirm_btn})
    public void onClick(View view) {

        mPresenter.requestForBid();
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bit_init;
    }

    @Override
    protected BitInitPresenter getChildPresenter() {
        return new BitInitPresenter(this);
    }

    @Override
    protected View getLoaingTargetView() {
        return mList;
    }
}
