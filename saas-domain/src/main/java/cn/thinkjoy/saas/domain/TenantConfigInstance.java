/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  TenantConfigInstance.java 2016-10-12 10:51:40 $
 */



package cn.thinkjoy.saas.domain;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TenantConfigInstance extends BaseDomain{
    private Integer tnId;
    private String configKey;
    private Integer configOrder;
    private String checkRule;
    private String domain;
    private Long createDate;
    private Long modifyDate;
    private String dataType;
    private String dataUrl;
    private String dataValue;

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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setCreateDate(Long value) {
        this.createDate = value;
    }

    public Long getCreateDate() {
        return this.createDate;
    }

    public void setModifyDate(Long value) {
        this.modifyDate = value;
    }

    public Long getModifyDate() {
        return this.modifyDate;
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

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Id", getId())
                .append("TnId", getTnId())
                .append("ConfigKey", getConfigKey())
                .append("ConfigOrder", getConfigOrder())
                .append("CheckRule", getCheckRule())
                .append("Domain", getDomain())
                .append("CreateDate", getCreateDate())
                .append("ModifyDate", getModifyDate())
                .append("DataType", getDataType())
                .append("DataUrl",getDataUrl())
                .append("DataValue",getDataValue())
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

