/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwCourseRule.java 2016-12-05 17:07:22 $
 */





package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwCourseRule extends JwBaseRule{
    /**  */
    private Integer courseId;
    /**  */
    private Integer taskId;

	public JwCourseRule(){
	}

	public void setCourseId(Integer value) {
        this.courseId = value;
    }

    public Integer getCourseId() {
        return this.courseId;
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
			.append("CourseId",getCourseId())
			.append("TaskId",getTaskId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwCourseRule == false) return false;
		if(this == obj) return true;
		JwCourseRule other = (JwCourseRule)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

