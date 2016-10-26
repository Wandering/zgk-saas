/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  Tenant.java 2016-10-26 10:18:21 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class Tenant extends BaseDomain{
    private String tnName;
    private String tnAddr;
    private String tnExtroInfo;
    private Integer isInit;
    private Date createDate;
    private String tnLogo;
    private String countyId;

	public Tenant(){
	}
    public void setTnName(String value) {
        this.tnName = value;
    }

    public String getTnName() {
        return this.tnName;
    }
    public void setTnAddr(String value) {
        this.tnAddr = value;
    }

    public String getTnAddr() {
        return this.tnAddr;
    }
    public void setTnExtroInfo(String value) {
        this.tnExtroInfo = value;
    }

    public String getTnExtroInfo() {
        return this.tnExtroInfo;
    }
    public void setIsInit(Integer value) {
        this.isInit = value;
    }

    public Integer getIsInit() {
        return this.isInit;
    }


    public void setCreateDate(Date value) {
        this.createDate = value;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    public void setTnLogo(String value) {
        this.tnLogo = value;
    }

    public String getTnLogo() {
        return this.tnLogo;
    }
    public void setCountyId(String value) {
        this.countyId = value;
    }

    public String getCountyId() {
        return this.countyId;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TnName",getTnName())
			.append("TnAddr",getTnAddr())
			.append("TnExtroInfo",getTnExtroInfo())
			.append("IsInit",getIsInit())
			.append("CreateDate",getCreateDate())
			.append("TnLogo",getTnLogo())
			.append("CountyId",getCountyId())
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

