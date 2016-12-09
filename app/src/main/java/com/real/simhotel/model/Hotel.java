package com.real.simhotel.model;

/**
 * Created by liudan on 2016/12/9.
 */
public class Hotel {

    private int location;
    private int id;
    private Object remark;
    private float equipDeprePer;
    private int equipDepreCycle;
    private int trainingId;
    private int roomLeastNum;
    private int roomCost;
    private int roomIncome;
    private int cleanNum;
    private String insertTime;
    private String updateTime;

    /**
     * @return The location
     */
    public int getLocation() {
        return location;
    }

    /**
     * @param location The location
     */
    public void setLocation(int location) {
        this.location = location;
    }

    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The remark
     */
    public Object getRemark() {
        return remark;
    }

    /**
     * @param remark The remark
     */
    public void setRemark(Object remark) {
        this.remark = remark;
    }

    /**
     * @return The equipDeprePer
     */
    public float getEquipDeprePer() {
        return equipDeprePer;
    }

    /**
     * @param equipDeprePer The equipDeprePer
     */
    public void setEquipDeprePer(float equipDeprePer) {
        this.equipDeprePer = equipDeprePer;
    }

    /**
     * @return The equipDepreCycle
     */
    public int getEquipDepreCycle() {
        return equipDepreCycle;
    }

    /**
     * @param equipDepreCycle The equipDepreCycle
     */
    public void setEquipDepreCycle(int equipDepreCycle) {
        this.equipDepreCycle = equipDepreCycle;
    }

    /**
     * @return The trainingId
     */
    public int getTrainingId() {
        return trainingId;
    }

    /**
     * @param trainingId The trainingId
     */
    public void setTrainingId(int trainingId) {
        this.trainingId = trainingId;
    }

    /**
     * @return The roomLeastNum
     */
    public int getRoomLeastNum() {
        return roomLeastNum;
    }

    /**
     * @param roomLeastNum The roomLeastNum
     */
    public void setRoomLeastNum(int roomLeastNum) {
        this.roomLeastNum = roomLeastNum;
    }

    /**
     * @return The roomCost
     */
    public int getRoomCost() {
        return roomCost;
    }

    /**
     * @param roomCost The roomCost
     */
    public void setRoomCost(int roomCost) {
        this.roomCost = roomCost;
    }

    /**
     * @return The roomIncome
     */
    public int getRoomIncome() {
        return roomIncome;
    }

    /**
     * @param roomIncome The roomIncome
     */
    public void setRoomIncome(int roomIncome) {
        this.roomIncome = roomIncome;
    }

    /**
     * @return The cleanNum
     */
    public int getCleanNum() {
        return cleanNum;
    }

    /**
     * @param cleanNum The cleanNum
     */
    public void setCleanNum(int cleanNum) {
        this.cleanNum = cleanNum;
    }

    /**
     * @return The insertTime
     */
    public String getInsertTime() {
        return insertTime;
    }

    /**
     * @param insertTime The insertTime
     */
    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    /**
     * @return The updateTime
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime The updateTime
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
