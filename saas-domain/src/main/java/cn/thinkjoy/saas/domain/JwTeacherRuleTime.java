/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwTeacherRuleTime.java 2016-12-06 15:06:25 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwTeacherRuleTime extends BaseDomain{
    private String teachTime;
    private Integer createTime;
    private Integer teacherRuleId;

	public JwTeacherRuleTime(){
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
    public void setTeacherRuleId(Integer value) {
        this.teacherRuleId = value;
    }

    public Integer getTeacherRuleId() {
        return this.teacherRuleId;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TeachTime",getTeachTime())
			.append("CreateTime",getCreateTime())
			.append("TeacherRuleId",getTeacherRuleId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwTeacherRuleTime == false) return false;
		if(this == obj) return true;
		JwTeacherRuleTime other = (JwTeacherRuleTime)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

