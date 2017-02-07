package com.real.simhotel.view.fragment.student;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;
import com.real.simhotel.R;
import com.real.simhotel.model.Applicant;
import com.real.simhotel.presenter.BidInitPresenter;
import com.real.simhotel.view.adapter.DynamicListAdapter;
import com.real.simhotel.view.adapter.DynamicListDecoration;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.base.BaseFragment;
import com.real.simhotel.view.fragment.ApplicantBidDetailFragment;
import com.real.simhotel.view.iview.ISHRBidView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by liudan on 2016/12/21.
 */
public class BidInitFragment extends BaseFragment<BidInitPresenter> implements ISHRBidView{


    @Inject
    DynamicListAdapter mAdapter;

    @Bind(R.id.bid_applicants_rv)
    RecyclerView mList;

    @Bind(R.id.bid_confirm_btn)
    Button mConfirm;

    //详情页面
    ApplicantBidDetailFragment mDetailFragment;


    public ApplicantBidDetailFragment getDetailFragment(){
        if (mDetailFragment == null){
            mDetailFragment = new ApplicantBidDetailFragment();
        }

        return mDetailFragment;
    }
    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        ButterKnife.bind(this,view);

        //设置Adapter
        mAdapter = new DynamicListAdapter(mActivity)
                .setRowInterface((pos,model)->
                     //刷新左侧的界面
                     mDetailFragment.renderView((Applicant) model.ext)
                );

        mList.setLayoutManager(new LinearLayoutManager(mActivity));
        mList.setAdapter(mAdapter);
        mList.addItemDecoration(new DynamicListDecoration(mActivity,DynamicListDecoration.VERTICAL_LIST));


        //初始化话Detail
        mDetailFragment = new ApplicantBidDetailFragment();
        //绑定按钮事件
        mDetailFragment.setConfirmListener(view1 ->{

            //TODO 数据的改动都放在Presenter中操作
            //回传 Presenter 一个选中的位置 和 滑动的值。
            mPresenter.updateApplicantsRow(mAdapter.getSelectPos(),mDetailFragment.getSeekBarValue());

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

        mPresenter.requestData();
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

    @Override
    public void renderApplicantsList(List<DynamicListModel> applicantsList) {

        //替换 fragment
        this.getHoldingActivity().getSupportFragmentManager().beginTransaction().replace(R.id.bid_applicant_detail,mDetailFragment).commitAllowingStateLoss();


        //清空之前的选中状态
        for (int i = 0; i < applicantsList.size(); i++) {
            applicantsList.get(i).isSelected = false;
        }

        //若第一个有数据的话 刷新这个人的界面
        if (applicantsList.size() == 0){
            mAdapter.setDataList(applicantsList);
            return;
        }

        //选中第一个
        applicantsList.get(0).isSelected = true;



        //更新
        mAdapter.setDataList(applicantsList);

        //默认选中第一个
        mDetailFragment.renderView((Applicant)applicantsList.get(0).ext);
    }
}
