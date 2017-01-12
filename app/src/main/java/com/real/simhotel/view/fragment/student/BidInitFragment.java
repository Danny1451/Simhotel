package com.real.simhotel.view.fragment.student;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;
import com.real.simhotel.R;
import com.real.simhotel.presenter.BidInitPresenter;
import com.real.simhotel.view.adapter.DynamicListAdapter;
import com.real.simhotel.view.adapter.DynamicListDecoration;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by liudan on 2016/12/21.
 */
public class BidInitFragment extends BaseFragment<BidInitPresenter> {


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

        mAdapter = new DynamicListAdapter(mActivity)
                .setRowInterface((pos,model)->{

                     mPresenter.onClickApplicantsRow(pos,model);

                });



        mList.setLayoutManager(new LinearLayoutManager(mActivity));

        mList.setAdapter(mAdapter);
        mList.addItemDecoration(new DynamicListDecoration(mActivity,DynamicListDecoration.VERTICAL_LIST));


        mDetailFragment = new ApplicantInfoFragment();
        //绑定按钮事件



        this.getHoldingActivity().getSupportFragmentManager().beginTransaction().replace(R.id.bid_applicant_detail,mDetailFragment).commitAllowingStateLoss();

        mDetailFragment.setConfirmListener(view1 ->{
            //点击了确定 通知presenter 刷新list

            mPresenter.updateApplicantsRow((DynamicListModel)view.getTag());
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
    protected BidInitPresenter getChildPresenter() {
        return new BidInitPresenter(this);
    }

    @Override
    protected View getLoaingTargetView() {
        return mList;
    }
}
