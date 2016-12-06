/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwTeachDate.java 2016-12-06 15:06:25 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwTeachDate extends BaseDomain{
    private Integer tid;
    private Integer tnId;
    private String teachDate;
    private String teachDetail;
    private String createDate;
    private Integer taskId;

	public JwTeachDate(){
	}
    public void setTid(Integer value) {
        this.tid = value;
    }

    public Integer getTid() {
        return this.tid;
    }
    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
    }
    public void setTeachDate(String value) {
        this.teachDate = value;
    }

    public String getTeachDate() {
        return this.teachDate;
    }
    public void setTeachDetail(String value) {
        this.teachDetail = value;
    }

    public String getTeachDetail() {
        return this.teachDetail;
    }
    public void setCreateDate(String value) {
        this.createDate = value;
    }

    public String getCreateDate() {
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
			.append("Tid",getTid())
			.append("TnId",getTnId())
			.append("TeachDate",getTeachDate())
			.append("TeachDetail",getTeachDetail())
			.append("CreateDate",getCreateDate())
			.append("TaskId",getTaskId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getTid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwTeachDate == false) return false;
		if(this == obj) return true;
		JwTeachDate other = (JwTeachDate)obj;
		return new EqualsBuilder()
			.append(getTid(),other.getTid())
			.isEquals();
	}
}

