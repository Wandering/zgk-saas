/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwBaseWeekRule.java 2016-12-08 17:19:58 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwBaseWeekRule extends BaseDomain{
    private Integer tnId;
    private Integer courseId;
    private Integer teacherId;
    private Integer importantType;
    private Integer weekType;
    private Long createDate;
    private Integer taskId;

	public JwBaseWeekRule(){
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
    public void setWeekType(Integer value) {
        this.weekType = value;
    }

    public Integer getWeekType() {
        return this.weekType;
    }
    public void setCreateDate(Long value) {
        this.createDate = value;
    }

    public Long getCreateDate() {
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
			.append("WeekType",getWeekType())
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
		if(obj instanceof JwBaseWeekRule == false) return false;
		if(this == obj) return true;
		JwBaseWeekRule other = (JwBaseWeekRule)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

