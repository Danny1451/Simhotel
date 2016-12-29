package com.real.simhotel.presenter;

import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.view.adapter.DynamicListAdapter;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.adapter.DynamicListModelFactory;
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

        list.add(DynamicListModelFactory.modelForCeoNormalMessage("本月的收入 80 玩 ,支出 10 玩","2016年6月"));
        list.add(DynamicListModelFactory.modelForCeoNormalMessage("本月的收入 90 玩 ,支出 30 玩","2016年6月"));
        list.add(DynamicListModelFactory.modelForCeoNormalMessage("本月的收入 100 玩 ,支出 30 玩","2016年7月"));
        list.add(DynamicListModelFactory.modelForCeoDecisionMessage("本季度招聘会即将开始是否招聘?","2016年8月",1));
        list.add(DynamicListModelFactory.modelForCeoDecisionMessage("大哥你要破产了,贷款不?","2016年9月",2));

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
            if ( (int)model.ext == 1) {

                model.butonChooseInfo = "已开始招聘";

                mView.showToast("选择招聘");

            }else if ((int)model.ext == 2){

                model.butonChooseInfo = "已经选择贷款";
                mView.showToast("选择贷款");

            }

            mView.reload();
        }

        @Override
        public void cancel(DynamicListModel model) {

            model.hasChoose = true;
            model.butonChooseInfo = "已放弃";
            mView.showToast("选择放弃");
            mView.reload();

        }
    };
}
