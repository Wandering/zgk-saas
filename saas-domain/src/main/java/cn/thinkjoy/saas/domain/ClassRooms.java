/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  ClassRooms.java 2016-10-12 10:51:39 $
 */



package cn.thinkjoy.saas.domain;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ClassRooms extends BaseDomain {
    private Integer tnId;
    private Integer gradeId;
    private Integer executiveNumber;
    private Integer dayNumber;
    private Integer roomOrder;
    private Long createDate;
    private Integer scheduleNumber;

    public Integer getScheduleNumber() {
        return scheduleNumber;
    }

    public void setScheduleNumber(Integer scheduleNumber) {
        this.scheduleNumber = scheduleNumber;
    }

    public ClassRooms() {
    }

    public Integer getTnId() {
        return tnId;
    }

    public void setTnId(Integer tnId) {
        this.tnId = tnId;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getExecutiveNumber() {
        return executiveNumber;
    }

    public void setExecutiveNumber(Integer executiveNumber) {
        this.executiveNumber = executiveNumber;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Integer getRoomOrder() {
        return roomOrder;
    }

    public void setRoomOrder(Integer roomOrder) {
        this.roomOrder = roomOrder;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Id", getId())
                .append("TnId", getTnId())
                .append("GradeId", getGradeId())
                .append("ExecutiveNumber",getExecutiveNumber())
                .append("DayNumber", getDayNumber())
                .append("RoomOrder", getRoomOrder())
                .append("CreateDate", getCreateDate())
                .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof ClassRooms == false) return false;
        if (this == obj) return true;
        ClassRooms other = (ClassRooms) obj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .isEquals();
    }
}

