/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwSyllabus.java 2017-03-02 09:54:35 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwSyllabus extends BaseDomain{
    private Integer taskId;
    private Integer classId;
    private String info;

	public JwSyllabus(){
	}
    public void setTaskId(Integer value) {
        this.taskId = value;
    }

    public Integer getTaskId() {
        return this.taskId;
    }
    public void setClassId(Integer value) {
        this.classId = value;
    }

    public Integer getClassId() {
        return this.classId;
    }
    public void setInfo(String value) {
        this.info = value;
    }

    public String getInfo() {
        return this.info;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TaskId",getTaskId())
			.append("ClassId",getClassId())
			.append("Info",getInfo())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwSyllabus == false) return false;
		if(this == obj) return true;
		JwSyllabus other = (JwSyllabus)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

