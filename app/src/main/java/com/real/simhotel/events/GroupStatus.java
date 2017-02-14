package com.real.simhotel.events;

/**
 * Created by liudan on 2017/2/13.
 */
public class GroupStatus extends BaseStatus {

    private Integer groupStaus;
    private String statusDes;
    @Override
    public Integer getStatus() {
        return groupStaus;
    }

    @Override
    public String getDes() {
        return statusDes;
    }

    public void setGrouoStatus(Integer grouoStatus) {
        this.groupStaus = grouoStatus;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }

    public Integer getGrouoStatus() {
        return groupStaus;
    }

    public String getStatusDes() {
        return statusDes;
    }
}
