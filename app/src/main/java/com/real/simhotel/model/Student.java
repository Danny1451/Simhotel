package com.real.simhotel.model;

/**
 * Created by liudan on 2017/2/15.
 */
public class Student {

    private Object remark;
    private String studentNumber;
    private String studentName;
    private Integer studentId;
    private Integer operationPermission;
    private String insertTime;
    private String updateTime;

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getOperationPermission() {
        return operationPermission;
    }

    public void setOperationPermission(Integer operationPermission) {
        this.operationPermission = operationPermission;
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