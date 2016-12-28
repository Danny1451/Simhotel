package com.real.simhotel.view.fragment.student;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.real.simhotel.R;
import com.real.simhotel.presenter.CeoInitPresenter;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.adapter.DynamicListAdapter;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.adapter.DynamicListDecoration;
import com.real.simhotel.view.base.BaseFragment;
import com.real.simhotel.view.iview.ICeoInitView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liudan on 2016/12/14.
 */
public class CeoInitFragment extends BaseFragment<CeoInitPresenter> implements ICeoInitView{

    @Inject
    DynamicListAdapter mAdapter;

    @Bind(R.id.ceo_init_rv)
    RecyclerView mList;

    @Bind(R.id.ceo_init_confirm)
    Button mConfirm;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        ButterKnife.bind(this,view);
        mAdapter = new DynamicListAdapter(mActivity);

        mAdapter.setItemChooseInterce((int pos, DynamicListModel model)-> {

            //选中的话
            mPresenter.updateMinusRoomNum(pos);


        });

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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ceo_init;
    }

    @Override
    protected CeoInitPresenter getChildPresenter() {
        return new CeoInitPresenter(this);
    }

    @Override
    protected View getLoaingTargetView() {
        return mList;
    }


    @OnClick({R.id.ceo_init_confirm})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ceo_init_confirm:{

                //请求参数
                mPresenter.requestParams();

            }
            default:
        }
    }

    @Override
    public void renderHotelInitItems(List<DynamicListModel> list) {
        if (list != null)
            mAdapter.setDataList(list);
    }
}
