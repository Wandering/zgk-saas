/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwCourse.java 2016-12-06 15:06:24 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwCourse extends BaseDomain{
    private String courseHour;
    private Integer chour;
    private Integer tnId;
    private Integer courseId;
    private Integer taskId;

	public JwCourse(){
	}
    public void setCourseHour(String value) {
        this.courseHour = value;
    }

    public String getCourseHour() {
        return this.courseHour;
    }
    public void setChour(Integer value) {
        this.chour = value;
    }

    public Integer getChour() {
        return this.chour;
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

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getCourseId() {
        return this.courseId;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("CourseHour",getCourseHour())
			.append("Chour",getChour())
			.append("TnId",getTnId())
			.append("CourseId",getCourseId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwCourse == false) return false;
		if(this == obj) return true;
		JwCourse other = (JwCourse)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

