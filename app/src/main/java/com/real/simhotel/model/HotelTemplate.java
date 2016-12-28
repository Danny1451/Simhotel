package com.real.simhotel.model;

import java.util.HashMap;
import java.util.Map;

public class HotelTemplate {

    private Integer location;
    private Integer id;
    private Object remark;
    private Double equipDeprePer;
    private Integer equipDepreCycle;
    private Integer trainingId;
    private Integer roomLeastNum;
    private Integer roomCost;
    private Integer roomIncome;
    private Integer cleanNum;
    private Integer roomNum;
    private String insertTime;
    private String updateTime;
    private String locationName;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
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

    public Integer getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Integer trainingId) {
        this.trainingId = trainingId;
    }

    public Integer getRoomLeastNum() {
        return roomLeastNum;
    }

    public void setRoomLeastNum(Integer roomLeastNum) {
        this.roomLeastNum = roomLeastNum;
    }

    public Integer getRoomCost() {
        return roomCost;
    }

    public void setRoomCost(Integer roomCost) {
        this.roomCost = roomCost;
    }

    public Integer getRoomIncome() {
        return roomIncome;
    }

    public void setRoomIncome(Integer roomIncome) {
        this.roomIncome = roomIncome;
    }

    public Integer getCleanNum() {
        return cleanNum;
    }

    public void setCleanNum(Integer cleanNum) {
        this.cleanNum = cleanNum;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}