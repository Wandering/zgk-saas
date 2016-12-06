/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwCourseRuleTime.java 2016-12-06 15:06:24 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwCourseRuleTime extends BaseDomain{
    private String teachTime;
    private Integer createTime;
    private Integer courseRuleId;

	public JwCourseRuleTime(){
	}
    public void setTeachTime(String value) {
        this.teachTime = value;
    }

    public String getTeachTime() {
        return this.teachTime;
    }
    public void setCreateTime(Integer value) {
        this.createTime = value;
    }

    public Integer getCreateTime() {
        return this.createTime;
    }
    public void setCourseRuleId(Integer value) {
        this.courseRuleId = value;
    }

    public Integer getCourseRuleId() {
        return this.courseRuleId;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TeachTime",getTeachTime())
			.append("CreateTime",getCreateTime())
			.append("CourseRuleId",getCourseRuleId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwCourseRuleTime == false) return false;
		if(this == obj) return true;
		JwCourseRuleTime other = (JwCourseRuleTime)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

