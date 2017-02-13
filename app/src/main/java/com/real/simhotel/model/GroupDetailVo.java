package com.real.simhotel.model;

import java.util.List;

public class GroupDetailVo {

    private String location;
    private Integer id;
    private Object remark;
    private Integer trainingId;
    private String groupName;
    private String groupDes;
    private String insertTime;
    private String updateTime;
    private Integer groupStatus;
    private List<GroupRoleDetailVo> groupRoleDetailVos = null;
    private List<Object> groupHotelDetailVos = null;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public Integer getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Integer trainingId) {
        this.trainingId = trainingId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDes() {
        return groupDes;
    }

    public void setGroupDes(String groupDes) {
        this.groupDes = groupDes;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(Integer groupStatus) {
        this.groupStatus = groupStatus;
    }

    public List<GroupRoleDetailVo> getGroupRoleDetailVos() {
        return groupRoleDetailVos;
    }

    public void setGroupRoleDetailVos(List<GroupRoleDetailVo> groupRoleDetailVos) {
        this.groupRoleDetailVos = groupRoleDetailVos;
    }

    public List<Object> getGroupHotelDetailVos() {
        return groupHotelDetailVos;
    }

    public void setGroupHotelDetailVos(List<Object> groupHotelDetailVos) {
        this.groupHotelDetailVos = groupHotelDetailVos;
    }

    public String getGroupRoleDetailsString(){
        String roleDes = "";

        if (getGroupRoleDetailVos() != null && getGroupRoleDetailVos().size() != 0) {
            for (GroupRoleDetailVo temp : getGroupRoleDetailVos()) {

                roleDes = roleDes + "职位:" + temp.getRoleName() + " 姓名:" + temp.getStudentName() + "\n";
            }
        }else {
            roleDes = "暂无组员";
        }
        return roleDes;
    }

    public boolean checkHasMyPos(int role){

        if (getGroupRoleDetailVos() != null && getGroupRoleDetailVos().size() != 0) {
            for (GroupRoleDetailVo temp : getGroupRoleDetailVos()) {

                if (temp.getRoleId() == role)
                    return true;

            }
        }
        return false;
    }
}