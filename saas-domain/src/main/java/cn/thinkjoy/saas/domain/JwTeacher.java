/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: zgk-saas
 * $Id:  JwTeacher.java 2017-02-16 14:02:50 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwTeacher extends BaseDomain{
    private Integer tnId;
    private Integer teacherId;
    private Integer taskId;
    private Integer isAttend;

	public JwTeacher(){
	}
    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
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
    public void setIsAttend(Integer value) {
        this.isAttend = value;
    }

    public Integer getIsAttend() {
        return this.isAttend;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TnId",getTnId())
			.append("TeacherId",getTeacherId())
			.append("TaskId",getTaskId())
			.append("IsAttend",getIsAttend())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwTeacher == false) return false;
		if(this == obj) return true;
		JwTeacher other = (JwTeacher)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

