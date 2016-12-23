package com.real.simhotel.view.iview;

import com.real.simhotel.model.Group;


import java.util.List;

/**
 * Created by liudan on 2016/12/12.
 */
public interface IGroupListView extends IBaseView {
    void renderGroupList(List<Group> groupList);

    void viewGroup(Group groupModel);
}
