/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwClassBaseInfo.java 2016-12-06 15:06:23 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwClassBaseInfo extends BaseDomain{
    private Integer tnId;
    private String className;
    private Integer classType;
    private Integer grade;

	public JwClassBaseInfo(){
	}
    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
    }
    public void setClassName(String value) {
        this.className = value;
    }

    public String getClassName() {
        return this.className;
    }
    public void setClassType(Integer value) {
        this.classType = value;
    }

    public Integer getClassType() {
        return this.classType;
    }
    public void setGrade(Integer value) {
        this.grade = value;
    }

    public Integer getGrade() {
        return this.grade;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TnId",getTnId())
			.append("ClassName",getClassName())
			.append("ClassType",getClassType())
			.append("Grade",getGrade())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwClassBaseInfo == false) return false;
		if(this == obj) return true;
		JwClassBaseInfo other = (JwClassBaseInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

