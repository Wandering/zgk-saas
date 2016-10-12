/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  MenuResource.java 2016-10-12 10:51:40 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class MenuResource extends BaseDomain{
    private String resName;
    private String resParentUrl;
    private String resUrl;
    private Date createDate;

	public MenuResource(){
	}
    public void setResName(String value) {
        this.resName = value;
    }

    public String getResName() {
        return this.resName;
    }
    public void setResParentUrl(String value) {
        this.resParentUrl = value;
    }

    public String getResParentUrl() {
        return this.resParentUrl;
    }
    public void setResUrl(String value) {
        this.resUrl = value;
    }

    public String getResUrl() {
        return this.resUrl;
    }

    public void setCreateDate(Date value) {
        this.createDate = value;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ResName",getResName())
			.append("ResParentUrl",getResParentUrl())
			.append("ResUrl",getResUrl())
			.append("CreateDate",getCreateDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MenuResource == false) return false;
		if(this == obj) return true;
		MenuResource other = (MenuResource)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

