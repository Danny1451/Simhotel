package com.real.simhotel.presenter;

import com.real.simhotel.R;
import com.real.simhotel.model.Applicant;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.fragment.student.BidResultFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudan on 2016/12/28.
 */
public class BidResultPresenter extends BasePresenter {


    BidResultFragment mView;

    private List<DynamicListModel> mData;

    public BidResultPresenter(BidResultFragment view){

        this.mView = view;

    }

    @Override
    public void requestData(Object... o) {

        mData = new ArrayList<>();
        //初始化数据
        Applicant app1 = new Applicant();
        app1.name = "新垣结衣";
        app1.level = 0;
        app1.expectValues = 5000;
        app1.year = 1;
        app1.quotePrice = 10000;
        app1.headRes = R.mipmap.avatar_female_01;

        DynamicListModel model1 = new DynamicListModel(DynamicListModel.TYPE_TITLE_INFO);
        model1.title = app1.name;
        model1.info = app1.quotePrice + "元";
        model1.ext = app1;



        Applicant app2 = new Applicant();
        app2.name = "鹿晗";
        app2.level = 1;
        app2.expectValues = 6000;
        app2.year = 2;
        app2.quotePrice = 8000;
        app2.headRes = R.mipmap.avatar_female_02;

        DynamicListModel model2 = new DynamicListModel(DynamicListModel.TYPE_TITLE_INFO);
        model2.title = app2.name;
        model2.info = app2.quotePrice + "元";
        model2.ext = app2;


        Applicant app3 = new Applicant();
        app3.name = "吴亦凡";
        app3.level = 2;
        app3.expectValues = 7000;
        app3.year = 3;
        app3.quotePrice = 5000;
        app3.headRes = R.mipmap.avatar_female_03;


        DynamicListModel model3 = new DynamicListModel(DynamicListModel.TYPE_TITLE_INFO);
        model3.title = app3.name;
        model3.info = app3.quotePrice + "元";
        model3.ext = app3;


        mData.add(model1);
        mData.add(model2);
        mData.add(model3);



        mView.loadApplicantList(mData);


        //默认选中1
//        onClickApplicantsRow(0,model1);

    }

    //更新右侧的list
    public void onClickApplicantsRow(int pos , DynamicListModel model){

//        mView.getDetailFragment().updateWithModel(model);

        //获取结果

        int quoteprice = ((Applicant)model.ext).quotePrice;


        List<DynamicListModel> res = new ArrayList<>();

        res.add(DynamicListModel.modelWithNumberTitleInfo(1,"万达喜来登酒店",quoteprice + ""));
        res.add(DynamicListModel.modelWithNumberTitleInfo(2,"希尔顿酒店",quoteprice - 100 + ""));
        res.add(DynamicListModel.modelWithNumberTitleInfo(3,"如家酒店",quoteprice - 200 + ""));
        res.add(DynamicListModel.modelWithNumberTitleInfo(4,"怡莱酒店",quoteprice - 300 + ""));
        res.add(DynamicListModel.modelWithNumberTitleInfo(5,"汉庭酒店",quoteprice - 400 + ""));
        res.add(DynamicListModel.modelWithNumberTitleInfo(6,"好的酒店",quoteprice - 500 + ""));
        res.add(DynamicListModel.modelWithNumberTitleInfo(7,"苏果酒店",quoteprice - 600 + ""));
        res.add(DynamicListModel.modelWithNumberTitleInfo(8,"大润发酒店",quoteprice - 700 + ""));

        mView.loadBidResultList(pos,res);

    }
}
