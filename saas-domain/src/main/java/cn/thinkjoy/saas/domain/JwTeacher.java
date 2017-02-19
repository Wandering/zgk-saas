/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwTeacher.java 2016-12-07 11:45:05 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwTeacher extends BaseDomain{
    private Integer teachNum;
    private Integer tnId;
    private String course;
    private Integer teacherId;
    private Integer taskId;
    private String classId;

	public JwTeacher(){
	}
    public void setTeachNum(Integer value) {
        this.teachNum = value;
    }

    public Integer getTeachNum() {
        return this.teachNum;
    }
    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
    }
    public void setCourse(String value) {
        this.course = value;
    }

    public String getCourse() {
        return this.course;
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
    public void setClassId(String value) {
        this.classId = value;
    }

    public String getClassId() {
        return this.classId;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TeachNum",getTeachNum())
			.append("TnId",getTnId())
			.append("Course",getCourse())
			.append("TeacherId",getTeacherId())
			.append("TaskId",getTaskId())
			.append("ClassId",getClassId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwTeacher == false) return false;
		if(this == obj) return true;
		JwTeacher other = (JwTeacher)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

