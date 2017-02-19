/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  EnrollingRatio.java 2016-10-12 10:51:40 $
 */



package cn.thinkjoy.saas.domain;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class EnrollingRatio extends BaseDomain{
    private Integer tnId;
    private Integer stu3numbers;
    private Integer batch1enrolls;
    private Integer batch2enrolls;
    private Integer batch3enrolls;
    private Integer batch4enrolls;
    private Integer year;
    private Integer ratioOrder;
    private Long createDate;

	public EnrollingRatio(){
	}
    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
    }
    public void setStu3numbers(Integer value) {
        this.stu3numbers = value;
    }

    public Integer getStu3numbers() {
        return this.stu3numbers;
    }
    public void setBatch1enrolls(Integer value) {
        this.batch1enrolls = value;
    }

    public Integer getBatch1enrolls() {
        return this.batch1enrolls;
    }
    public void setBatch2enrolls(Integer value) {
        this.batch2enrolls = value;
    }

    public Integer getBatch2enrolls() {
        return this.batch2enrolls;
    }
    public void setBatch3enrolls(Integer value) {
        this.batch3enrolls = value;
    }

    public Integer getBatch3enrolls() {
        return this.batch3enrolls;
    }
    public void setBatch4enrolls(Integer value) {
        this.batch4enrolls = value;
    }

    public Integer getBatch4enrolls() {
        return this.batch4enrolls;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getRatioOrder() {
        return ratioOrder;
    }

    public void setRatioOrder(Integer ratioOrder) {
        this.ratioOrder = ratioOrder;
    }

    public void setCreateDate(Long value) {
        this.createDate = value;
    }

    public Long getCreateDate() {
        return this.createDate;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TnId", getTnId())
			.append("Stu3numbers",getStu3numbers())
			.append("Batch1enrolls",getBatch1enrolls())
			.append("Batch2enrolls",getBatch2enrolls())
			.append("Batch3enrolls",getBatch3enrolls())
			.append("Batch4enrolls",getBatch4enrolls())
            .append("Year", getYear())
            .append("RatioOrder", getRatioOrder())
            .append("CreateDate", getCreateDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof EnrollingRatio == false) return false;
		if(this == obj) return true;
		EnrollingRatio other = (EnrollingRatio)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

