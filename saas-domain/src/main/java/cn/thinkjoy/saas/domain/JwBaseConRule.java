/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwBaseConRule.java 2016-12-06 15:06:23 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwBaseConRule extends BaseDomain{
    private Integer tnId;
    private Integer courseId;
    private Integer teacherId;
    private Integer importantType;
    private Integer dayConType;
    private long createDate;
    private Integer taskId;

	public JwBaseConRule(){
	}
    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
    }
    public void setCourseId(Integer value) {
        this.courseId = value;
    }

    public Integer getCourseId() {
        return this.courseId;
    }
    public void setTeacherId(Integer value) {
        this.teacherId = value;
    }

    public Integer getTeacherId() {
        return this.teacherId;
    }
    public void setImportantType(Integer value) {
        this.importantType = value;
    }

    public Integer getImportantType() {
        return this.importantType;
    }
    public void setDayConType(Integer value) {
        this.dayConType = value;
    }

    public Integer getDayConType() {
        return this.dayConType;
    }
    public void setCreateDate(long value) {
        this.createDate = value;
    }

    public long getCreateDate() {
        return this.createDate;
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
			.append("TnId",getTnId())
			.append("CourseId",getCourseId())
			.append("TeacherId",getTeacherId())
			.append("ImportantType",getImportantType())
			.append("DayConType",getDayConType())
			.append("CreateDate",getCreateDate())
			.append("TaskId",getTaskId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwBaseConRule == false) return false;
		if(this == obj) return true;
		JwBaseConRule other = (JwBaseConRule)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

