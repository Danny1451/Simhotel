package com.real.simhotel.presenter;

import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.view.adapter.DynamicListAdapter;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.fragment.student.CeoNormalFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudan on 2016/12/19.
 */
public class CeoNormalPresenter extends BasePresenter{
    private CeoNormalFragment mView;

    private List<DynamicListModel> list;



    public CeoNormalPresenter(CeoNormalFragment view){
        mView = view;
    }

    public DynamicListAdapter.NormalChooseInterface getChooseInterface() {
        return mChooseInterface;
    }



    @Override
    public void requestData(Object... o) {
        super.requestData(o);

        list = new ArrayList();


        DynamicListModel modelTest4 = new DynamicListModel();
        modelTest4.itemType = DynamicListModel.TYPE_NORMAL_INFO;
        modelTest4.info = "上个月的收入 100玩 ,支出 30玩";
        modelTest4.time = "2016年7月";

        DynamicListModel modelTest5 = new DynamicListModel();
        modelTest5.itemType = DynamicListModel.TYPE_NORMAL_CHOOSE;
        modelTest5.detailType = 1;
        modelTest5.info = "本季度招聘会即将开始是否招聘?";
        modelTest5.time = "2016年8月";


        DynamicListModel modelTest1 = new DynamicListModel();
        modelTest1.itemType = DynamicListModel.TYPE_NORMAL_CHOOSE;
        modelTest1.detailType = 2;
        modelTest1.info = "大哥你要破产了,贷款不?";
        modelTest1.time = "2016年9月";

        list.add(modelTest4);
        list.add(modelTest5);
        list.add(modelTest1);

        //加载列表
        mView.loadList(list);

    }

    /**
     * 监听list中的选项
     */
    private DynamicListAdapter.NormalChooseInterface mChooseInterface = new DynamicListAdapter.NormalChooseInterface() {
        @Override
        public void confim(DynamicListModel model) {

            model.hasChoose = true;
            if (model.detailType == 1) {

                model.chooseInfo = "已开始招聘";

                mView.showToast("选择招聘");

            }else if (model.detailType == 2){

                model.chooseInfo = "已经选择贷款";
                mView.showToast("选择贷款");

            }

            mView.reload();
        }

        @Override
        public void cancel(DynamicListModel model) {

            model.hasChoose = true;
            model.chooseInfo = "已放弃";
            mView.showToast("选择放弃");
            mView.reload();

        }
    };
}
