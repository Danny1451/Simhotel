package com.real.simhotel.presenter;

import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.utils.log.BaseLog;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.adapter.DyNamicListModel;
import com.real.simhotel.view.fragment.student.CeoInitFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by liudan on 2016/12/14.
 */
public class CeoInitPresenter extends BasePresenter {
    private CeoInitFragment mView;

    private List<DyNamicListModel> list;
    public CeoInitPresenter(CeoInitFragment view){
        mView = view;
    }

    @Override
    public void requestData(Object... o) {
        super.requestData(o);

        //这边请求初始化的 可配置参数

        list = new ArrayList();

        DyNamicListModel modelTest = new DyNamicListModel();
        modelTest.itemType = DyNamicListModel.TYPE_SEEK;
        modelTest.title = "酒店数量";
        modelTest.max = 20;
        modelTest.unit = "间";
        modelTest.selectedValue = - 1;

        DyNamicListModel modelTest2 = new DyNamicListModel();
        modelTest2.itemType = DyNamicListModel.TYPE_SEEK;
        modelTest2.title = "价格";
        modelTest2.max = 100;
        modelTest2.unit = "元";
        modelTest.selectedValue = - 1;

        DyNamicListModel modelTest3 = new DyNamicListModel();
        modelTest3.itemType = DyNamicListModel.TYPE_CHOOSE;
        modelTest3.mChooseItems = new ArrayList<>();
        modelTest3.mChooseItems.add("选项1");
        modelTest3.mChooseItems.add("选项2");
        modelTest3.mChooseItems.add("选项3");
        modelTest3.mChooseItems.add("选项4");

        modelTest3.title = "酒店位置";


        DyNamicListModel modelTest4 = new DyNamicListModel();
        modelTest4.itemType = DyNamicListModel.TYPE_NORMAL_INFO;
        modelTest4.info = "上个月的收入 100玩 ,支出 30玩";
        modelTest4.time = "2016年7月";

        DyNamicListModel modelTest5 = new DyNamicListModel();
        modelTest5.itemType = DyNamicListModel.TYPE_NORMAL_CHOOSE;
        modelTest5.info = "本季度招聘会即将开始是否招聘?";
        modelTest5.time = "2016年8月";


        list.add(modelTest2);
        list.add(modelTest);
        list.add(modelTest3);
        list.add(modelTest4);
        list.add(modelTest5);
        //加载列表
        mView.loadList(list);
    }

    //请求参数
    public void requestParams(){

        String res = "res";

        for (int i = 0 ; i < list.size() ; i++){
            res = res + list.get(i).title + list.get(i).selectedValue;
        }
        KLog.d("dad",list.toString());
        mView.showToast(res);
    }
}
