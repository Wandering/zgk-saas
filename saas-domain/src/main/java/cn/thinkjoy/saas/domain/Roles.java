/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  Roles.java 2016-10-14 09:50:58 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class Roles extends BaseDomain{
    private String roleName;
    private String roleDesc;
    private Integer tnId;
    private Date createDate;

	public Roles(){
	}
    public void setRoleName(String value) {
        this.roleName = value;
    }

    public String getRoleName() {
        return this.roleName;
    }
    public void setRoleDesc(String value) {
        this.roleDesc = value;
    }

    public String getRoleDesc() {
        return this.roleDesc;
    }
    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
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
			.append("RoleName",getRoleName())
			.append("RoleDesc",getRoleDesc())
			.append("TnId",getTnId())
			.append("CreateDate",getCreateDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Roles == false) return false;
		if(this == obj) return true;
		Roles other = (Roles)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

