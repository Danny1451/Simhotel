package com.real.simhotel.presenter;

import com.real.simhotel.events.BaseStatus;
import com.real.simhotel.events.EventCode;
import com.real.simhotel.events.TrainStatus;
import com.real.simhotel.events.StatusManager;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.adapter.DynamicListAdapter;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.adapter.DynamicListModelFactory;
import com.real.simhotel.view.fragment.student.CeoNormalFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudan on 2016/12/19.
 */
public class CeoNormalPresenter extends BasePresenter{
    private CeoNormalFragment mView;

    private List<DynamicListModel> list;



    public static final int CEO_DECISION_HIRE = 1;
    public static final int CEO_DECISION_LOAN = 2;

    public CeoNormalPresenter(CeoNormalFragment view){
        mView = view;
    }

    public DynamicListAdapter.NormalChooseInterface getChooseInterface() {
        return mChooseInterface;
    }


    @Override
    public void handleStatus(BaseStatus event) {
        switch (event.getStatus()){

            case EventCode.GroupCode.GROUP_CEO_HIRE_ING:
                //开始 增加 雇员 信息 TODO 招聘会的时间

                if (list == null){
                    list = new ArrayList<>();
                }

                list.add(DynamicListModelFactory.modelForCeoDecisionMessage("本季度招聘会即将开始是否招聘?",
                        application.training.getCurrentCycle() + "", CEO_DECISION_HIRE));
                //加载列表
                mView.loadList(list);

                application.traingingStatusManager.consumeStatus(event);
                break;


        }
    }


    @Override
    public void destroy() {
        super.destroy();

        EventBus.getDefault().unregister(this);

    }

    @Override
    public void requestData(Object... o) {
        super.requestData(o);

//        KLog.d("id = " + application.mHotel.getId());

//        list = new ArrayList();

//        if (application.group.getGroupStatus() == EventCode.GroupCode.GROUP_CEO_HIRE_ING){
//            //增加一条招聘信息
//
//            list.add(DynamicListModelFactory.modelForCeoDecisionMessage("本季度招聘会即将开始是否招聘?",
//                    application.training.getCurrentCycle()+"",
//                    CEO_DECISION_HIRE));
//
//        }

//        list.add(DynamicListModelFactory.modelForCeoNormalMessage("本月的收入 80 玩 ,支出 10 玩","2016年6月"));
//        list.add(DynamicListModelFactory.modelForCeoNormalMessage("本月的收入 90 玩 ,支出 30 玩","2016年6月"));
//        list.add(DynamicListModelFactory.modelForCeoNormalMessage("本月的收入 100 玩 ,支出 30 玩","2016年7月"));
//        list.add(DynamicListModelFactory.modelForCeoDecisionMessage("大哥你要破产了,贷款不?","2016年9月",2));

        //加载列表
//        mView.loadList(list);


    }

    /**
     * 监听list中的选项
     */
    private DynamicListAdapter.NormalChooseInterface mChooseInterface = new DynamicListAdapter.NormalChooseInterface() {

        @Override
        public void onChoose(DynamicListModel model, Boolean hasConfirm) {

            //修改状态
            mView.showLoading();
            int eventCode = 0;
            if ( (int)model.ext == CEO_DECISION_HIRE) {

                eventCode = EventCode.GroupCode.GROUP_CEO_HIRE_REJECT;
                if (hasConfirm)
                    eventCode = EventCode.GroupCode.GROUP_CEO_HIRE_CONFIRM;

            }else if ((int)model.ext == CEO_DECISION_LOAN){

                eventCode = EventCode.GroupCode.GROUP_CEO_LOAN_REJECT;
                if (hasConfirm)
                    eventCode = EventCode.GroupCode.GROUP_CEO_LOAN_CONFIRM;

            }

            application.traingingStatusManager.changeGroupStatus(
                    eventCode,
                    new StatusManager.StatusChangeListener() {
                        @Override
                        public void OnChangedSuccess() {

                            mView.disMissLoading();

                            if (hasConfirm){
                                model.butonChooseInfo = "已开始";
                                mView.showToast("已确认");
                            }else {
                                model.butonChooseInfo = "已放弃";
                                mView.showToast("已放弃");
                            }

                            model.hasChoose = true;
                            mView.reload();

                        }

                        @Override
                        public void OnChangedFailed(String erro) {

                            mView.disMissLoading();

                            mView.showToast("请求失败请稍后再试");

                        }
                    });
        }


    };
}
