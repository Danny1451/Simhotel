package com.real.simhotel.model;

/**
 * Created by liudan on 2016/12/12.
 */
public class Group {

    private Integer id;
    private Object remark;
    private Integer trainingId;
    private String groupName;
    private String groupDes;
    private String insertTime;
    private String updateTime;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The remark
     */
    public Object getRemark() {
        return remark;
    }

    /**
     *
     * @param remark
     * The remark
     */
    public void setRemark(Object remark) {
        this.remark = remark;
    }

    /**
     *
     * @return
     * The trainingId
     */
    public Integer getTrainingId() {
        return trainingId;
    }

    /**
     *
     * @param trainingId
     * The trainingId
     */
    public void setTrainingId(Integer trainingId) {
        this.trainingId = trainingId;
    }

    /**
     *
     * @return
     * The groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     *
     * @param groupName
     * The groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     *
     * @return
     * The groupDes
     */
    public String getGroupDes() {
        return groupDes;
    }

    /**
     *
     * @param groupDes
     * The groupDes
     */
    public void setGroupDes(String groupDes) {
        this.groupDes = groupDes;
    }

    /**
     *
     * @return
     * The insertTime
     */
    public String getInsertTime() {
        return insertTime;
    }

    /**
     *
     * @param insertTime
     * The insertTime
     */
    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    /**
     *
     * @return
     * The updateTime
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     *
     * @param updateTime
     * The updateTime
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
