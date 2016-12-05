/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwTeacherRule.java 2016-12-05 17:07:23 $
 */





package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwTeacherRule extends BaseDomain<Integer>{
    /** 周一不上课节数，默认为全上9个1：“111111111”，如果勾选某个节，不上，则对应的值为“0” */
    private String mon;
    /** 周二不上课节数，默认为全上9个1：“111111111”，如果勾选某个节，不上，则对应的值为“0” */
    private String tues;
    /** 周三不上课节数，默认为全上9个1：“111111111”，如果勾选某个节，不上，则对应的值为“0” */
    private String wed;
    /** 周四不上课节数，默认为全上9个1：“111111111”，如果勾选某个节，不上，则对应的值为“0” */
    private String thur;
    /** 周五不上课节数，默认为全上9个1：“111111111”，如果勾选某个节，不上，则对应的值为“0” */
    private String fri;
    /** 周六不上课节数，默认为全上9个1：“111111111”，如果勾选某个节，不上，则对应的值为“0” */
    private String sut;
    /** 周日不上课节数，默认为全上9个1：“111111111”，如果勾选某个节，不上，则对应的值为“0” */
    private String sun;
    /**  */
    private Integer teacherId;
    /**  */
    private Integer taskId;

	public JwTeacherRule(){
	}
    public void setMon(String value) {
        this.mon = value;
    }

    public String getMon() {
        return this.mon;
    }
    public void setTues(String value) {
        this.tues = value;
    }

    public String getTues() {
        return this.tues;
    }
    public void setWed(String value) {
        this.wed = value;
    }

    public String getWed() {
        return this.wed;
    }
    public void setThur(String value) {
        this.thur = value;
    }

    public String getThur() {
        return this.thur;
    }
    public void setFri(String value) {
        this.fri = value;
    }

    public String getFri() {
        return this.fri;
    }
    public void setSut(String value) {
        this.sut = value;
    }

    public String getSut() {
        return this.sut;
    }
    public void setSun(String value) {
        this.sun = value;
    }

    public String getSun() {
        return this.sun;
    }
    public void setTeacherId(Integer value) {
        this.teacherId = value;
    }

    public Integer getTeacherId() {
        return this.teacherId;
    }
    public void setTaskId(Integer value) {
        this.taskId = value;
    }

    public Integer getTaskId() {
        return this.taskId;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Mon",getMon())
			.append("Tues",getTues())
			.append("Wed",getWed())
			.append("Thur",getThur())
			.append("Fri",getFri())
			.append("Sut",getSut())
			.append("Sun",getSun())
			.append("TeacherId",getTeacherId())
			.append("TaskId",getTaskId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwTeacherRule == false) return false;
		if(this == obj) return true;
		JwTeacherRule other = (JwTeacherRule)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

