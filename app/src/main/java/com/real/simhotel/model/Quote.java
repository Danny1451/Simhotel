package com.real.simhotel.model;

/**
 * Created by liudan on 2017/1/12.
 */
public class Quote {

    public String hotelName;
    public int prcie;

    private Integer id;
    private Object remark;
    private Integer groupId;
    private String groupName;
    private Integer employId;
    private Integer biddingPrice;
    private Integer biddingStatus;
    private String insertTime;
    private String updateTime;
    private Integer biddingTime;
    private Integer employStatus;

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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getEmployId() {
        return employId;
    }

    public void setEmployId(Integer employId) {
        this.employId = employId;
    }

    public Integer getBiddingPrice() {
        return biddingPrice;
    }

    public void setBiddingPrice(Integer biddingPrice) {
        this.biddingPrice = biddingPrice;
    }

    public Integer getBiddingStatus() {
        return biddingStatus;
    }

    public void setBiddingStatus(Integer biddingStatus) {
        this.biddingStatus = biddingStatus;
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

    public Integer getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(Integer biddingTime) {
        this.biddingTime = biddingTime;
    }

    public Integer getEmployStatus() {
        return employStatus;
    }

    public void setEmployStatus(Integer employStatus) {
        this.employStatus = employStatus;
    }
    public static Quote testQuote(String  name, int price){
        Quote result = new Quote();
        result.hotelName = name;
        result.prcie = price;

        return result;
    }
}
