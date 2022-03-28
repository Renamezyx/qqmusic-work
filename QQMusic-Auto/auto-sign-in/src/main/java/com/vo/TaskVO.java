package com.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.util.TimerTask;

import java.util.Date;
import java.util.TimeZone;
import java.util.function.Consumer;

public class TaskVO {
    private String uuid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date startTime;
    private String remark;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
