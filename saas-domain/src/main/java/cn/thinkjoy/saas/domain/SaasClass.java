/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  SaasClass.java 2016-12-06 15:06:25 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class SaasClass extends BaseDomain{
    private Integer tnId;
    private Integer teacherId;
    private Integer classId;

	public SaasClass(){
	}
    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
    }
    public void setTeacherId(Integer value) {
        this.teacherId = value;
    }

    public Integer getTeacherId() {
        return this.teacherId;
    }
    public void setClassId(Integer value) {
        this.classId = value;
    }

    public Integer getClassId() {
        return this.classId;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TnId",getTnId())
			.append("TeacherId",getTeacherId())
			.append("ClassId",getClassId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SaasClass == false) return false;
		if(this == obj) return true;
		SaasClass other = (SaasClass)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

