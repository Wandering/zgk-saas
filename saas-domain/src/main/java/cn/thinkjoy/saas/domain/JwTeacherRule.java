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

public class JwTeacherRule extends JwBaseRule{
    /**  */
    private Integer teacherId;
    /**  */
    private Integer taskId;

	public JwTeacherRule(){
	}

	@Override
	public void setTypeId(Integer value)
	{
		setTeacherId(value);
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

