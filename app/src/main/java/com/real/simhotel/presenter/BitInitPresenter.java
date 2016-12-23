package com.real.simhotel.presenter;

import android.view.View;
import android.widget.Toast;

import com.real.simhotel.model.Applicant;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.fragment.student.BidInitFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudan on 2016/12/23.
 */
public class BitInitPresenter extends BasePresenter {

    private BidInitFragment mView;

    private List<DynamicListModel> data;

    private List<Applicant> applicants;


    public BitInitPresenter(BidInitFragment view){

        mView = view;
    }

    @Override
    public void requestData(Object... o) {

        data = new ArrayList<>();


        applicants = new ArrayList<>();




        //初始化数据
        DynamicListModel model1 = new DynamicListModel(DynamicListModel.TYPE_TITLE_INFO);
        model1.title = "张三";
        model1.info = "未报价";




        DynamicListModel model2 = new DynamicListModel(DynamicListModel.TYPE_TITLE_INFO);
        model2.title = "李四";
        model2.info = "未报价";



        DynamicListModel model3 = new DynamicListModel(DynamicListModel.TYPE_TITLE_INFO);
        model3.title = "刘五";
        model3.info = "未报价";


        data.add(model1);
        data.add(model2);
        data.add(model3);

        //更新数据
        mView.loadList(data);

    }

    public void clickApplicantsList(int pos ,DynamicListModel model){

        mView.showToast("选择了" + model.title + " " +pos);
    }
}
