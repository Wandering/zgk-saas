/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwCourseGapRule.java 2016-12-06 15:51:08 $
 */





package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwCourseGapRule extends BaseDomain<Long>{
    /** 任务Id */
    private Long taskId;
    /** 规则 */
    private String rule;

	private Long createDate;
	public JwCourseGapRule(){
	}
    public void setTaskId(Long value) {
        this.taskId = value;
    }

    public Long getTaskId() {
        return this.taskId;
    }
    public void setRule(String value) {
        this.rule = value;
    }

    public String getRule() {
        return this.rule;
    }

	public Long getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Long createDate)
	{
		this.createDate = createDate;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TaskId",getTaskId())
			.append("Rule",getRule())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof JwCourseGapRule == false) return false;
		if(this == obj) return true;
		JwCourseGapRule other = (JwCourseGapRule)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

