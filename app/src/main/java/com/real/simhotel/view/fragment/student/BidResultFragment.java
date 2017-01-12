package com.real.simhotel.view.fragment.student;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.real.simhotel.R;
import com.real.simhotel.presenter.BidResultPresenter;
import com.real.simhotel.view.adapter.DynamicListAdapter;
import com.real.simhotel.view.adapter.DynamicListDecoration;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.base.BaseFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liudan on 2016/12/21.
 */
public class BidResultFragment extends BaseFragment<BidResultPresenter> {

    @Bind(R.id.bit_result_list)
    RecyclerView bitResultList;

    @Bind(R.id.bit_result_detail)
    RecyclerView bitResultDetail;


    DynamicListAdapter mListAdapter;

    DynamicListAdapter mDetailAdapter;
    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        ButterKnife.bind(this,view);


        mListAdapter = new DynamicListAdapter(mActivity)
                .setRowInterface((pos,model)->{

                    //点击选中了list项
                    mPresenter.onClickApplicantsRow(pos,model);

                });


        bitResultList.setLayoutManager(new LinearLayoutManager(mActivity));
        bitResultList.setAdapter(mListAdapter);
//        bitResultList.addItemDecoration(new DynamicListDecoration(mActivity,DynamicListDecoration.VERTICAL_LIST));



        mDetailAdapter = new DynamicListAdapter(mActivity);
        bitResultDetail.setLayoutManager(new LinearLayoutManager(mActivity));
        bitResultDetail.setAdapter(mDetailAdapter);
//        bitResultDetail.addItemDecoration(new DynamicListDecoration(mActivity,DynamicListDecoration.VERTICAL_LIST));

    }

    @Override
    protected void initData() {
        //获取候选人List的结果
        mPresenter.requestData();
    }

    @Override
    public void loadData() {


    }

    /**
     * 加载候选人的列表
     * @param lists
     */
    public void loadApplicantList(List<DynamicListModel> lists){

        mListAdapter.setDataList(lists);


    }

    /**
     * 加载公司报价的列表
     * @param pos 位置
     * @param lists 竞价结果列表
     */
    public void loadBidResultList(int pos , List<DynamicListModel> lists){

        //1.选中候选人

        bitResultList.getLayoutManager().smoothScrollToPosition(bitResultList,null,pos);
        //2.刷新酒店对候选人报价

        mDetailAdapter.setDataList(lists);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bit_result;
    }

    @Override
    protected BidResultPresenter getChildPresenter() {
        return new BidResultPresenter(this);
    }

    @Override
    protected View getLoaingTargetView() {
        return bitResultList;
    }
}
