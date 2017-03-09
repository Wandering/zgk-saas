/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: zgk-saas
 * $Id:  JwGradeRule.java 2017-03-09 11:02:24 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwGradeRule extends BaseDomain{
    private String mon;
    private String tues;
    private String wed;
    private String thur;
    private String fri;
    private String sut;
    private String sun;
    private Integer gradeId;
    private Integer taskId;

	public JwGradeRule(){
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
    public void setGradeId(Integer value) {
        this.gradeId = value;
    }

    public Integer getGradeId() {
        return this.gradeId;
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
			.append("GradeId",getGradeId())
			.append("TaskId",getTaskId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwGradeRule == false) return false;
		if(this == obj) return true;
		JwGradeRule other = (JwGradeRule)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

