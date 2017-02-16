package com.real.simhotel.view.fragment.student;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.real.simhotel.R;
import com.real.simhotel.model.Applicant;
import com.real.simhotel.presenter.BidResultPresenter;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.adapter.DynamicListAdapter;
import com.real.simhotel.view.adapter.DynamicListDecoration;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.base.BaseFragment;
import com.real.simhotel.view.fragment.ApplicantListDetailFragment;
import com.real.simhotel.view.iview.ISHrBidResultView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liudan on 2016/12/21.
 */
public class BidResultFragment extends BaseFragment<BidResultPresenter> implements ISHrBidResultView {


    @Inject
    DynamicListAdapter mAdapter;

    @Bind(R.id.bid_applicants_rv)
    RecyclerView mList;

    @Bind(R.id.title_tv)
    TextView mTitle;

    //详情页面
    ApplicantListDetailFragment mDetailFragment;


    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        ButterKnife.bind(this,view);

        mTitle.setText(this.getString(R.string.hr_trainging_reslut));

        mAdapter = new DynamicListAdapter(mActivity)
                .setRowInterface((pos,model)->{

                    //点击选中了list项 请求数据
                    mPresenter.requestDetailList((Applicant) model.ext);

                });


        mList.setLayoutManager(new LinearLayoutManager(mActivity));
        mList.setAdapter(mAdapter);
        mList.addItemDecoration(new DynamicListDecoration(mActivity,DynamicListDecoration.VERTICAL_LIST));


        //初始化话Detail
        mDetailFragment = new ApplicantListDetailFragment();
        this.getHoldingActivity().getSupportFragmentManager().beginTransaction().replace(R.id.bid_applicant_detail,mDetailFragment).commitAllowingStateLoss();

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
    public void renderQuotesList(List<DynamicListModel> quoteList) {

        mDetailFragment.refreshView();


        if (mDetailFragment != null) {
            if (quoteList.size() == 0 ){
                mDetailFragment.showEmptyView("没有报价");
            }else {
                mDetailFragment.renderView(quoteList);
            }
        }

    }

    @Override
    public void renderApplicantsList(List<DynamicListModel> applicantsList) {
        KLog.d(applicantsList.toString());
        //替换 fragment
//


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
//        mDetailFragment.renderView((List<DynamicListModel>) applicantsList.get(0).ext);

        mPresenter.requestDetailList((Applicant) applicantsList.get(0).ext);
    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bit_init;
    }

    @Override
    protected BidResultPresenter getChildPresenter() {
        return new BidResultPresenter(this);
    }

    @Override
    protected View getLoaingTargetView() {
        return mList;
    }
}
