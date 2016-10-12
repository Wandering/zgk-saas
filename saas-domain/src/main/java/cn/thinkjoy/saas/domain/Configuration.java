/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  Configuration.java 2016-10-12 10:51:40 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class Configuration extends BaseDomain{
    private String enName;
    private String chName;
    private String metaType;
    private String checkRule;
    private String domain;
    private Integer order;

	public Configuration(){
	}
    public void setEnName(String value) {
        this.enName = value;
    }

    public String getEnName() {
        return this.enName;
    }
    public void setChName(String value) {
        this.chName = value;
    }

    public String getChName() {
        return this.chName;
    }
    public void setMetaType(String value) {
        this.metaType = value;
    }

    public String getMetaType() {
        return this.metaType;
    }
    public void setCheckRule(String value) {
        this.checkRule = value;
    }

    public String getCheckRule() {
        return this.checkRule;
    }
    public void setDomain(String value) {
        this.domain = value;
    }

    public String getDomain() {
        return this.domain;
    }
    public void setOrder(Integer value) {
        this.order = value;
    }

    public Integer getOrder() {
        return this.order;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("EnName",getEnName())
			.append("ChName",getChName())
			.append("MetaType",getMetaType())
			.append("CheckRule",getCheckRule())
			.append("Domain",getDomain())
			.append("Order",getOrder())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Configuration == false) return false;
		if(this == obj) return true;
		Configuration other = (Configuration)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

