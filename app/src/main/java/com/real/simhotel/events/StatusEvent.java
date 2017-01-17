package com.real.simhotel.events;

/**
 * Created by liudan on 2017/1/17.
 */
public class StatusEvent {

    private Integer trainingStatus;
    private String statusDes;

    public Integer getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingStatus(Integer trainingStatus) {
        this.trainingStatus = trainingStatus;
    }

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }

}
