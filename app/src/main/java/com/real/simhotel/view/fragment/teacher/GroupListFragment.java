package com.real.simhotel.view.fragment.teacher;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.real.simhotel.R;
import com.real.simhotel.model.Group;
import com.real.simhotel.presenter.GroupListPresenter;
import com.real.simhotel.view.iview.IGroupListView;
import com.real.simhotel.view.adapter.GroupListAdapter;
import com.real.simhotel.view.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liudan on 2016/12/12.
 */
public class GroupListFragment extends BaseFragment<GroupListPresenter> implements IGroupListView {


    @Inject
    GroupListAdapter mAdapter;

    @Bind(R.id.hotels_rv)
    RecyclerView mGroupList;


    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        ButterKnife.bind(this,view);
        mAdapter = new GroupListAdapter(mActivity);
        mGroupList.setLayoutManager(new LinearLayoutManager(mActivity));
        mGroupList.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {

        mPresenter.requestData();

    }

    @Override
    public void loadData() {

    }

    public void reloadData() {
        //重载数据
        mPresenter.requestData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hotel_list;
    }

    @Override
    protected GroupListPresenter getChildPresenter() {
        return new GroupListPresenter(this);
    }

    @Override
    protected View getLoaingTargetView() {
        return mGroupList;
    }

    @Override
    public void renderGroupList(List<Group> groupList) {

        if (groupList != null)
            mAdapter.setGroupsList(groupList);

    }


    @Override
    public void viewGroup(Group groupModel) {

    }
}
