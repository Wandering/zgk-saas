/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwClassRule.java 2016-12-05 17:07:21 $
 */





package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwClassRule extends JwBaseRule{
    /**  */
    private Integer classId;
    /**  */
    private Integer taskId;

	public JwClassRule(){
	}

	public void setClassId(Integer value) {
        this.classId = value;
    }

    public Integer getClassId() {
        return this.classId;
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
			.append("ClassId",getClassId())
			.append("TaskId",getTaskId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwClassRule == false) return false;
		if(this == obj) return true;
		JwClassRule other = (JwClassRule)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

