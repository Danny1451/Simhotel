package com.real.simhotel.view.fragment.student;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.real.simhotel.R;
import com.real.simhotel.model.Applicant;
import com.real.simhotel.presenter.HrNormalPresenter;
import com.real.simhotel.presenter.base.Presenter;
import com.real.simhotel.view.adapter.DynamicListAdapter;
import com.real.simhotel.view.adapter.DynamicListDecoration;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.base.BaseFragment;
import com.real.simhotel.view.fragment.ApplicantNormalDetailFragment;
import com.real.simhotel.view.fragment.BaseDetailFragment;
import com.real.simhotel.view.iview.ISHrListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liudan on 2017/2/10.
 */
public class HrNormalFragment extends BaseFragment<HrNormalPresenter> implements ISHrListView{


    @Inject
    DynamicListAdapter mAdapter;

    @Bind(R.id.bid_applicants_rv)
    RecyclerView mList;


    @Bind(R.id.title_tv)
    TextView mTitle;

    ApplicantNormalDetailFragment mDetailFragment;


    @Override
    public BaseDetailFragment getDetailFragment() {
        return mDetailFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        ButterKnife.bind(this,view);

        mTitle.setText(this.getString(R.string.hr_trainging_normal));

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
        mDetailFragment = new ApplicantNormalDetailFragment();
        //绑定按钮事件
        mDetailFragment.setConfirmListener(view1 ->{

            //TODO 数据的改动都放在Presenter中操作
            //回传 Presenter 放弃员工
            mPresenter.fireApplicant(mAdapter.getSelectPos());

        });

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
    protected int getLayoutId() {
        return R.layout.fragment_bit_init;
    }

    @Override
    protected HrNormalPresenter getChildPresenter() {
        return new HrNormalPresenter(this);
    }

    @Override
    protected View getLoaingTargetView() {
        return mList;
    }

    @Override
    public void renderApplicantsList(List<DynamicListModel> applicantsList) {

        //清空之前的选中状态
        for (int i = 0; i < applicantsList.size(); i++) {
            applicantsList.get(i).isSelected = false;
        }

        //若第一个有数据的话 刷新这个人的界面
        if (applicantsList.size() == 0){
            mAdapter.setDataList(applicantsList);

            mDetailFragment.showEmptyView("暂时没有员工");

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
