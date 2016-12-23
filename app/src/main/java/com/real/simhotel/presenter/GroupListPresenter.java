package com.real.simhotel.presenter;

import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;

import com.real.simhotel.model.Group;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.rx.DefaultSubscriber;
import com.real.simhotel.view.iview.IGroupListView;


import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liudan on 2016/12/12.
 */
public class GroupListPresenter extends BasePresenter {
    private IGroupListView mView;

    private Subscription subscription;

    public GroupListPresenter(IGroupListView view) {

        mView = view;

    }

    public void onUserClicked(Group groupModel) {
        mView.viewGroup(groupModel);
    }



    @Override
    public void requestData(Object... o) {
        super.requestData(o);


        mView.showLoading();

        subscription = apiService.getGroupList(1).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .flatMap(new Func1<Response<List<Group>>,Observable<List<Group>>>() {
                    @Override
                    public Observable<List<Group>> call(Response<List<Group>> listResponse) {
                        return RetrofitUtils.flatResponse(listResponse);
                    }
                }).subscribe(new GroupListSubscriber());


    }


    public class GroupListSubscriber extends DefaultSubscriber<List<Group>> {
        @Override
        public void onNext(List<Group> groups) {
            super.onNext(groups);

            if (groups == null || groups.size() == 0){

                mView.showEmptyView("获取数据异常");

            }else {
                //刷新界面
                mView.refreshView();
                mView.renderGroupList(groups);

            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            mView.showError("网络异常");
        }
    }
}
