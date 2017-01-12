package com.real.simhotel.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.real.simhotel.R;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.presenter.base.Presenter;
import com.real.simhotel.view.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liudan on 2017/1/5.
 */
public class DetailFragment<T> extends BaseFragment {

    @Bind(R.id.training_detail_tv_1)
    TextView tvLine1;

    @Bind(R.id.training_detail_tv_2)
    TextView tvLine2;

    @Bind(R.id.training_detail_tv_3)
    TextView tvLine3;

    @Bind(R.id.training_detail_tv_4)
    TextView tvLine4;

    @Bind(R.id.training_detail_tv_5)
    TextView tvLine5;

    @Bind(R.id.training_detail_tv_6)
    TextView tvLine6;

    @Bind(R.id.training_detail_btn_confirm)
    Button confirmBtn;

    @Bind(R.id.normal_detail_layout)
    LinearLayout normLayout;

    @Bind(R.id.list_detail_layout)
    LinearLayout listLayout;

    public T model;

    private View.OnClickListener mConfirmListener;


    public void setConfirmListener(View.OnClickListener listener){
        this.mConfirmListener = listener;


    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this,view);


        initView();

        confirmBtn.setOnClickListener(mConfirmListener);



        if (model != null)
            updateInfo(model);


    }

    public void initView(){

        normLayout.setVisibility(View.GONE);
        listLayout.setVisibility(View.GONE);

    }


    public void updateInfo(T info){

        model = info;

        //避免为初始化的状态
        if (tvLine1 == null || info == null)
            return;


        renderView(info);

    }

    public void renderView(T model){

    }

    @Override
    protected void initData() {

    }

    @Override
    public void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_training_detail;
    }

    @Override
    protected Presenter getChildPresenter() {
        return new BasePresenter();
    }

    @Override
    protected View getLoaingTargetView() {
        return null;
    }


}
