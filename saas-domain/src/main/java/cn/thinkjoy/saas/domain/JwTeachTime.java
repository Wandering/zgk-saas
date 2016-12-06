/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwTeachTime.java 2016-12-06 15:06:25 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwTeachTime extends BaseDomain{
    private String teachTime;
    private Integer tenId;
    private Integer createTime;
    private Integer tid;

	public JwTeachTime(){
	}
    public void setTeachTime(String value) {
        this.teachTime = value;
    }

    public String getTeachTime() {
        return this.teachTime;
    }
    public void setTenId(Integer value) {
        this.tenId = value;
    }

    public Integer getTenId() {
        return this.tenId;
    }
    public void setCreateTime(Integer value) {
        this.createTime = value;
    }

    public Integer getCreateTime() {
        return this.createTime;
    }
    public void setTid(Integer value) {
        this.tid = value;
    }

    public Integer getTid() {
        return this.tid;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TeachTime",getTeachTime())
			.append("TenId",getTenId())
			.append("CreateTime",getCreateTime())
			.append("Tid",getTid())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwTeachTime == false) return false;
		if(this == obj) return true;
		JwTeachTime other = (JwTeachTime)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

