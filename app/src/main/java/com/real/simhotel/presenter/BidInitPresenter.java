package com.real.simhotel.presenter;

import android.view.View;
import android.widget.Toast;

import com.real.simhotel.R;
import com.real.simhotel.model.Applicant;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.fragment.student.BidInitFragment;
import com.real.simhotel.view.iview.ISHRBidView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudan on 2016/12/23.
 */
public class BidInitPresenter extends BasePresenter {

    private ISHRBidView mView;

    private List<DynamicListModel> data;


    public BidInitPresenter(ISHRBidView view){

        mView = view;
    }

    @Override
    public void requestData(Object... o) {

        data = new ArrayList<>();


//        //初始化数据
//        Applicant app1 = new Applicant();
//        app1.name = "新垣结衣";
//        app1.level = 0;
//        app1.expectValues = 5000;
//        app1.year = 1;
//        app1.headRes = R.mipmap.avatar_female_01;
//
//        DynamicListModel model1 = new DynamicListModel(DynamicListModel.TYPE_TITLE_INFO);
//        model1.title = app1.name;
//        model1.info = "未报价";
//        model1.ext = app1;
//
//
//
//        Applicant app2 = new Applicant();
//        app2.name = "鹿晗";
//        app2.level = 1;
//        app2.expectValues = 6000;
//        app2.year = 2;
//        app2.headRes = R.mipmap.avatar_female_02;
//
//        DynamicListModel model2 = new DynamicListModel(DynamicListModel.TYPE_TITLE_INFO);
//        model2.title = app2.name;
//        model2.info = "未报价";
//        model2.ext = app2;
//
//
//        Applicant app3 = new Applicant();
//        app3.name = "吴亦凡";
//        app3.level = 2;
//        app3.expectValues = 7000;
//        app3.year = 3;
//        app3.headRes = R.mipmap.avatar_female_03;
//
//
//        DynamicListModel model3 = new DynamicListModel(DynamicListModel.TYPE_TITLE_INFO);
//        model3.title = app3.name;
//        model3.info = "未报价";
//        model3.ext = app3;
//
//
//        data.add(model1);
//        data.add(model2);
//        data.add(model3);



        //更新数据
        mView.renderApplicantsList(data);


//        onClickApplicantsRow(0,model1);
    }

    //开始最终的竞价
    public void requestForBid(){

        //遍历数据
        String res = "";
        for (DynamicListModel model : data){

            Applicant applicant = (Applicant) model.ext;
            res = res + "名字:" + applicant.getLevelStr() + "报价:" + applicant.quotePrice;

        }

        mView.showToast(res);


    }

    //更新左侧的列表的报价
    public void updateApplicantsRow(int pos,int seekValue){

        //得到 model 和 选择的value

        DynamicListModel model = data.get(pos);
        Applicant applicant = (Applicant) model.ext;


        //更新值
        applicant.quotePrice = seekValue;

        //更新界面
        model.info = applicant.quotePrice * 1000 + "元";


        mView.renderApplicantsList(data);

    }


}
