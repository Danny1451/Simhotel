package com.real.simhotel.view.fragment.teacher;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.real.simhotel.R;
import com.real.simhotel.model.Hotel;
import com.real.simhotel.presenter.HotelListPresenter;
import com.real.simhotel.view.iview.IHotelListView;
import com.real.simhotel.view.adapter.HotelListAdapter;
import com.real.simhotel.view.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liudan on 2016/12/12.
 */
public class HotelTemplateFragment extends BaseFragment<HotelListPresenter> implements IHotelListView {


    @Inject
    HotelListAdapter mAdapter;

    @Bind(R.id.hotels_rv)
    RecyclerView mHotelList;

    private HotelListAdapter.OnItemClickListener onItemClickListener = new HotelListAdapter.OnItemClickListener() {
        @Override
        public void onHotelItemClicked(Hotel hotel) {
            if (mPresenter != null && mHotelList != null) {
                ((HotelListPresenter) mPresenter).onUserClicked(hotel);
            }
        }
    };

    @Override
    public void loadData() {

    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        ButterKnife.bind(this,view);
        mAdapter = new HotelListAdapter(mActivity);
        mAdapter.setOnItemClickListener(onItemClickListener);
        mHotelList.setLayoutManager(new LinearLayoutManager(mActivity));
        mHotelList.setAdapter(mAdapter);
    }



    @Override
    protected void initData() {
        //请求数据
        mPresenter.requestData();
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
    protected HotelListPresenter getChildPresenter() {
        //构建Presenter
        return new HotelListPresenter(this);
    }

    @Override
    protected View getLoaingTargetView() {
        return mHotelList;
    }



    //实现 View的接口
    @Override
    public void renderHotelList(List<Hotel> hotels) {

        if (hotels != null)
            mAdapter.setHotelsList(hotels);
    }

    @Override
    public void viewHotel(Hotel hotelModel) {

        //点击了界面 考虑是否调整
    }





}
