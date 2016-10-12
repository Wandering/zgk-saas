/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  Tenant.java 2016-10-12 10:51:40 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class Tenant extends BaseDomain{
    private String loginCode;
    private String loginPass;
    private String tnName;
    private Integer tnAddr;
    private String tnExtroInfo;
    private Date createDate;

	public Tenant(){
	}
    public void setLoginCode(String value) {
        this.loginCode = value;
    }

    public String getLoginCode() {
        return this.loginCode;
    }
    public void setLoginPass(String value) {
        this.loginPass = value;
    }

    public String getLoginPass() {
        return this.loginPass;
    }
    public void setTnName(String value) {
        this.tnName = value;
    }

    public String getTnName() {
        return this.tnName;
    }
    public void setTnAddr(Integer value) {
        this.tnAddr = value;
    }

    public Integer getTnAddr() {
        return this.tnAddr;
    }
    public void setTnExtroInfo(String value) {
        this.tnExtroInfo = value;
    }

    public String getTnExtroInfo() {
        return this.tnExtroInfo;
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
			.append("LoginCode",getLoginCode())
			.append("LoginPass",getLoginPass())
			.append("TnName",getTnName())
			.append("TnAddr",getTnAddr())
			.append("TnExtroInfo",getTnExtroInfo())
			.append("CreateDate",getCreateDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Tenant == false) return false;
		if(this == obj) return true;
		Tenant other = (Tenant)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

