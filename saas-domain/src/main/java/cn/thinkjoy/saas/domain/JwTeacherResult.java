/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwTeacherResult.java 2016-12-06 15:06:25 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwTeacherResult extends BaseDomain{
    private String teacherId;
    private Integer tnId;
    private String result;
    private Integer createDate;
    private Integer taskId;

	public JwTeacherResult(){
	}
    public void setTeacherId(String value) {
        this.teacherId = value;
    }

    public String getTeacherId() {
        return this.teacherId;
    }
    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
    }
    public void setResult(String value) {
        this.result = value;
    }

    public String getResult() {
        return this.result;
    }
    public void setCreateDate(Integer value) {
        this.createDate = value;
    }

    public Integer getCreateDate() {
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
			.append("TeacherId",getTeacherId())
			.append("TnId",getTnId())
			.append("Result",getResult())
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
		if(obj instanceof JwTeacherResult == false) return false;
		if(this == obj) return true;
		JwTeacherResult other = (JwTeacherResult)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

