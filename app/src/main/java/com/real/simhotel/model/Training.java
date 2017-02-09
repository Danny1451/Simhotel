package com.real.simhotel.model;

import java.util.List;

/**
 * Created by liudan on 2017/1/4.
 */
public class Training {

    private Integer id;
    private Integer trainingStatus;
    private Object remark;
    private Integer teacherId;
    private String trainingName;
    private Integer trainingCycle;
    private Integer initialCapital;
    private Integer recruitCycle;
    private Integer customerCycle;
    private Double equipDeprePer;
    private Integer equipDepreCycle;
    private Integer currentCycle;
    private String insertTime;
    private String updateTime;
    private List<GroupDetailVo> groupDetailVos = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingStatus(Integer trainingStatus) {
        this.trainingStatus = trainingStatus;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public Integer getTrainingCycle() {
        return trainingCycle;
    }

    public void setTrainingCycle(Integer trainingCycle) {
        this.trainingCycle = trainingCycle;
    }

    public Integer getInitialCapital() {
        return initialCapital;
    }

    public void setInitialCapital(Integer initialCapital) {
        this.initialCapital = initialCapital;
    }

    public Integer getRecruitCycle() {
        return recruitCycle;
    }

    public void setRecruitCycle(Integer recruitCycle) {
        this.recruitCycle = recruitCycle;
    }

    public Integer getCustomerCycle() {
        return customerCycle;
    }

    public void setCustomerCycle(Integer customerCycle) {
        this.customerCycle = customerCycle;
    }

    public Double getEquipDeprePer() {
        return equipDeprePer;
    }

    public void setEquipDeprePer(Double equipDeprePer) {
        this.equipDeprePer = equipDeprePer;
    }

    public Integer getEquipDepreCycle() {
        return equipDepreCycle;
    }

    public void setEquipDepreCycle(Integer equipDepreCycle) {
        this.equipDepreCycle = equipDepreCycle;
    }

    public Integer getCurrentCycle() {
        return currentCycle;
    }

    public void setCurrentCycle(Integer currentCycle) {
        this.currentCycle = currentCycle;
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

    public List<GroupDetailVo> getGroupDetailVos() {
        return groupDetailVos;
    }

    public void setGroupDetailVos(List<GroupDetailVo> groupDetailVos) {
        this.groupDetailVos = groupDetailVos;
    }

}
