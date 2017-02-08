package com.real.simhotel.view.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.real.simhotel.R;
import com.real.simhotel.model.Applicant;
import com.real.simhotel.view.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by liudan on 2017/2/4.
 */
public class ApplicantBidDetailFragment extends BaseDetailFragment<Applicant> {


    @Bind(R.id.bid_seekbar_layout)
    LinearLayout mSeekLayout;

    @Bind(R.id.tv_bid_result)
    TextView mSelectResultTv;

    @Bind(R.id.bid_seekbar)
    SeekBar mSeekBar;

    int mSeekBarlValue;

    public int getSeekBarValue() {
        return mSeekBarlValue;
    }

    @Override
    public void initView() {
        super.initView();

        //显示
        mSeekLayout.setVisibility(View.VISIBLE);

        //绑定seekbar
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                //更新值
                mSeekBarlValue = i;
                //滑动的时候
                mSelectResultTv.setText(mSeekBarlValue * 1000 +  "/月");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void renderView(Applicant model) {
        super.renderView(model);

        tvLine1.setText("人员名称:"+ model.getLevelStr());
        tvLine2.setText("等级:" + model.getLevel());
        tvLine3.setText("期望月薪:" + model.getExpectMonthIncome());
//        tvLine4.setText("教师id:" + model.getTeacherId());
//        tvLine5.setText("参数5:" + model.getCurrentCycle());
        tvLine6.setText("工作年限:" + model.getExpectWorkPlace());

        confirmBtn.setText("竞价");
        if (model.quotePrice != 0){
            confirmBtn.setEnabled(false);
        }

    }
}
