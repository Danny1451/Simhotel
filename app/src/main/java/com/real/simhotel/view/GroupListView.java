package com.real.simhotel.view;

import com.real.simhotel.model.Group;


import java.util.List;

/**
 * Created by liudan on 2016/12/12.
 */
public interface GroupListView extends BaseView{
    void renderGroupList(List<Group> groupList);

    void viewGroup(Group groupModel);
}
