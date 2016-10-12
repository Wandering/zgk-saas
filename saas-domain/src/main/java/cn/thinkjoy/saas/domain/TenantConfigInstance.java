/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  TenantConfigInstance.java 2016-10-12 10:51:40 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class TenantConfigInstance extends BaseDomain{
    private Integer tnId;
    private String configKey;
    private Integer configOrder;
    private String checkRule;
    private Date createDate;
    private Date modifyDate;

	public TenantConfigInstance(){
	}
    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
    }
    public void setConfigKey(String value) {
        this.configKey = value;
    }

    public String getConfigKey() {
        return this.configKey;
    }
    public void setConfigOrder(Integer value) {
        this.configOrder = value;
    }

    public Integer getConfigOrder() {
        return this.configOrder;
    }
    public void setCheckRule(String value) {
        this.checkRule = value;
    }

    public String getCheckRule() {
        return this.checkRule;
    }

    public void setCreateDate(Date value) {
        this.createDate = value;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setModifyDate(Date value) {
        this.modifyDate = value;
    }

    public Date getModifyDate() {
        return this.modifyDate;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TnId",getTnId())
			.append("ConfigKey",getConfigKey())
			.append("ConfigOrder",getConfigOrder())
			.append("CheckRule",getCheckRule())
			.append("CreateDate",getCreateDate())
			.append("ModifyDate",getModifyDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof TenantConfigInstance == false) return false;
		if(this == obj) return true;
		TenantConfigInstance other = (TenantConfigInstance)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

