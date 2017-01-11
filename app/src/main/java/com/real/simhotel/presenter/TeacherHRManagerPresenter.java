package com.real.simhotel.presenter;

import com.real.simhotel.model.Applicant;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.iview.IHRManagerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudan on 2017/1/11.
 */
public class TeacherHRManagerPresenter extends BasePresenter {

    IHRManagerView mView;

    private List<Applicant> mDataList;
    private List<DynamicListModel> mViewModelList;

    public TeacherHRManagerPresenter(IHRManagerView view){

        mView = view;

    }


    @Override
    public void requestData(Object... o) {
        super.requestData(o);

        mDataList = new ArrayList<>();
        mViewModelList = new ArrayList<>();

    }

    public void removeApplicant(int pos){


        if (pos > mViewModelList.size() || pos < 0)
            return;


        mViewModelList.remove(pos);
        mDataList.remove(pos);

        mView.renderApplicantsList(mViewModelList);

    }

    public void createApplicant(Applicant model){

        mDataList.add(model);

        DynamicListModel viewModel = new DynamicListModel(DynamicListModel.TYPE_TITLE_INFO);

        viewModel.title = model.name;
        viewModel.info = model.expectValues + "元";
        viewModel.ext = model;

        mViewModelList.add(viewModel);

        mView.renderApplicantsList(mViewModelList);

    }
}
