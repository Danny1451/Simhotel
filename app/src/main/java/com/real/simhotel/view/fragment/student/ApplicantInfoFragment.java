package com.real.simhotel.view.fragment.student;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.real.simhotel.R;
import com.real.simhotel.model.Applicant;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.presenter.base.Presenter;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liudan on 2016/12/26.
 */
public class ApplicantInfoFragment extends BaseFragment {

    @Bind(R.id.applicant_name_tv)
    TextView mNameTv;

    @Bind(R.id.applicant_level_tv)
    TextView mLevelTv;

    @Bind(R.id.applicant_money_tv)
    TextView mWagesTv;

    @Bind(R.id.applicant_experience_tv)
    TextView mExperienceTv;

    @Bind(R.id.applicant_value_tv)
    TextView mSelectResultTv;

    @Bind(R.id.applicant_seekBar)
    SeekBar mSeekBar;


    @Bind(R.id.applicant_head_iv)
    ImageView mHead;


    @Bind(R.id.applicant_confirm_btn)
    Button mConfirm;

    private DynamicListModel mData;


    private View.OnClickListener mConfirmListener;


    public void setConfirmListener(View.OnClickListener listener){
        this.mConfirmListener = listener;


    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this,view);


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                //滑动的时候
                mSelectResultTv.setText(i * 1000 + "/月");
                ((Applicant)mData.ext).quotePrice = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mConfirm.setOnClickListener(mConfirmListener);


    }

    @Override
    protected void initData() {

    }

    public void updateWithModel(DynamicListModel model){

        mData = model;

        Applicant source =(Applicant)model.ext;
        //更新progress
        mSeekBar.setProgress(source.quotePrice);
        mSelectResultTv.setText(source.quotePrice * 1000 + "/月");
        //更新数据
        mNameTv.setText("姓名: "+source.name);
        mLevelTv.setText("等级: " + source.getLevelStr());
        mWagesTv.setText("期望工资: " + source.expectValues + "/月");
        mExperienceTv.setText("工作时间: " + source.year + "年");

        mHead.setImageResource(source.headRes);

        //把Data 作为tag
        mConfirm.setTag(mData);
    }



    @Override
    public void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_applicant_info;
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
