package com.real.simhotel.view.fragment;

import android.os.Looper;
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

        normLayout.setVisibility(View.VISIBLE);
        //显示
        mSeekLayout.setVisibility(View.VISIBLE);

        //绑定seekbar
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                //更新值
                mSeekBarlValue = i;
                //滑动的时候
                mSelectResultTv.setText(mSeekBarlValue * 1000 +  getString(R.string.applicant_unit));

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




        tvLine1.setText(getString(R.string.applicant_name,model.getLevelStr()));
        tvLine2.setText(getString(R.string.applicant_level,model.getLevel()));
        tvLine3.setText(getString(R.string.applicant_expect_income,model.getExpectMonthIncome()));
//        tvLine4.setText("教师id:" + model.getTeacherId());
//        tvLine5.setText("参数5:" + model.getCurrentCycle());
        tvLine6.setText(getString(R.string.applicant_expeerience,model.getExpectWorkPlace()));

        tvLine5.setText(getString(R.string.applicant_quote,model.getBiddingPrice()));
        confirmBtn.setText(getString(R.string.applicant_action_bid));
        if (model.quotePrice != null || model.getBiddingPrice() != 0 ){
            confirmBtn.setEnabled(false);
        }else {
            confirmBtn.setEnabled(true);
        }

    }
}
