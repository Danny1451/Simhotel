package com.real.simhotel.model;

import com.real.simhotel.view.adapter.DynamicListModel;

import java.util.List;

/**
 * Created by liudan on 2016/12/23.
 */
public class Applicant {

    private Integer level;
    private Object remark;
    private Integer trainingId;
    private Integer currentCycle;
    private Integer round;
    private Integer expectMonthIncome;
    private Integer expectWorkPlace;
    private Integer employId;
    private Integer employNum;
    private String insertTime;
    private String updateTime;
    private Integer biddingPrice;

    public List<Quote> quotes;
    public Integer quotePrice;

    public Boolean hasFired;

    public String getLevelStr(){
        switch (level){
            case 1:
                return "初级工人";
            case 2:
                return "中级工人";
            case 3:
                return "高级工人";
            case 4:
                return "特级工人";
        }
        return "逗逼工人";
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    public Integer getBiddingPrice() {
        return biddingPrice;
    }

    public void setBiddingPrice(Integer biddingPrice) {
        this.biddingPrice = biddingPrice;
    }


    public Integer getCurrentCycle() {
        return currentCycle;
    }

    public void setCurrentCycle(Integer currentCycle) {
        this.currentCycle = currentCycle;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Integer getExpectMonthIncome() {
        return expectMonthIncome;
    }

    public void setExpectMonthIncome(Integer expectMonthIncome) {
        this.expectMonthIncome = expectMonthIncome;
    }

    public Integer getExpectWorkPlace() {
        return expectWorkPlace;
    }

    public void setExpectWorkPlace(Integer expectWorkPlace) {
        this.expectWorkPlace = expectWorkPlace;
    }

    public Integer getEmployId() {
        return employId;
    }

    public void setEmployId(Integer employId) {
        this.employId = employId;
    }

    public Integer getEmployNum() {
        return employNum;
    }

    public void setEmployNum(Integer employNum) {
        this.employNum = employNum;
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
}
