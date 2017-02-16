/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  Grade.java 2017-02-10 14:32:10 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class Grade extends BaseDomain{
    private Integer tnId;
    private Integer gradeCode;
    private String grade;
    private Integer gorder;
    private Integer year;
    private Integer classType;

	public Grade(){
	}
    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
    }
    public void setGradeCode(Integer value) {
        this.gradeCode = value;
    }

    public Integer getGradeCode() {
        return this.gradeCode;
    }
    public void setGrade(String value) {
        this.grade = value;
    }

    public String getGrade() {
        return this.grade;
    }
    public void setGorder(Integer value) {
        this.gorder = value;
    }

    public Integer getGorder() {
        return this.gorder;
    }
    public void setYear(Integer value) {
        this.year = value;
    }

    public Integer getYear() {
        return this.year;
    }
    public void setClassType(Integer value) {
        this.classType = value;
    }

    public Integer getClassType() {
        return this.classType;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TnId",getTnId())
			.append("GradeCode",getGradeCode())
			.append("Grade",getGrade())
			.append("Gorder",getGorder())
			.append("Year",getYear())
			.append("ClassType",getClassType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Grade == false) return false;
		if(this == obj) return true;
		Grade other = (Grade)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

