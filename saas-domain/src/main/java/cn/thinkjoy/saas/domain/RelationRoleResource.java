/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  RelationRoleResource.java 2016-10-14 09:50:57 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class RelationRoleResource extends BaseDomain{
    private Integer roleId;
    private Integer resId;

	public RelationRoleResource(){
	}
    public void setRoleId(Integer value) {
        this.roleId = value;
    }

    public Integer getRoleId() {
        return this.roleId;
    }
    public void setResId(Integer value) {
        this.resId = value;
    }

    public Integer getResId() {
        return this.resId;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("RoleId",getRoleId())
			.append("ResId",getResId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof RelationRoleResource == false) return false;
		if(this == obj) return true;
		RelationRoleResource other = (RelationRoleResource)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

