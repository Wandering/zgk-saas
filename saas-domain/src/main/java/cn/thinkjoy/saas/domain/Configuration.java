/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  Configuration.java 2016-10-12 10:51:40 $
 */



package cn.thinkjoy.saas.domain;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Configuration extends BaseDomain{
    private String enName;
    private String chName;
    private String metaType;
    private String regular;
    private String checkRule;
    private String domain;
    private Integer configOrder;
    private String dataType;
    private String dataUrl;
    private String dataValue;
    private byte isRetain;
    private byte isShow;

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public byte getIsShow() {
        return isShow;
    }

    public void setIsShow(byte isShow) {
        this.isShow = isShow;
    }

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
    public void setConfigOrder(Integer value) {
        this.configOrder = value;
    }

    public Integer getConfigOrder() {
        return this.configOrder;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public byte getIsRetain() {
        return isRetain;
    }

    public void setIsRetain(byte isRetain) {
        this.isRetain = isRetain;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("EnName", getEnName())
			.append("ChName",getChName())
			.append("MetaType",getMetaType())
			.append("CheckRule",getCheckRule())
			.append("Domain",getDomain())
			.append("ConfigOrder",getConfigOrder())
            .append("DataType", getDataType())
            .append("DataUrl",getDataUrl())
            .append("DataValue",getDataValue())
            .append("IsRetain", getIsRetain())
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

