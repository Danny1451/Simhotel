package com.real.simhotel.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.real.simhotel.R;
import com.real.simhotel.model.Training;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.presenter.base.Presenter;
import com.real.simhotel.view.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liudan on 2017/1/5.
 */
public class TrainingDetailFragment extends BaseFragment {

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

    public Training model;

    private View.OnClickListener mConfirmListener;


    public void setConfirmListener(View.OnClickListener listener){
        this.mConfirmListener = listener;


    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
        confirmBtn.setOnClickListener(mConfirmListener);
    }

    public void updateInfo(Training info){

        model = info;
        tvLine1.setText(info.getTrainingName());
        tvLine2.setText("创建时间:" + info.getInsertTime());
        tvLine3.setText("更新时间:" + info.getUpdateTime());
        tvLine4.setText("教师id:" + info.getTeacherId());
        tvLine5.setText("参数5:" + info.getCurrentCycle());
        tvLine6.setText("参数6:" + info.getEquipDepreCycle());


    }

    @Override
    protected void initData() {

    }

    @Override
    public void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_training_detail_normal;
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
