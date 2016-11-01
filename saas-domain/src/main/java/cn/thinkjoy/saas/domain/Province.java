/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  Province.java 2016-10-26 10:18:21 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class Province extends BaseDomain{
    private String name;
    private String code;
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Province(){
	}
    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }
    public void setCode(String value) {
        this.code = value;
    }

    public String getCode() {
        return this.code;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Status",getStatus())
			.append("Name",getName())
			.append("Code",getCode())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Province == false) return false;
		if(this == obj) return true;
		Province other = (Province)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

