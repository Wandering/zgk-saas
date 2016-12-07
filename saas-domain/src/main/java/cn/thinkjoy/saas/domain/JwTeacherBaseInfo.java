/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwTeacherBaseInfo.java 2016-12-07 11:45:06 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwTeacherBaseInfo extends BaseDomain{
    private Integer tnId;
    private String teacherName;
    private String teacherCourse;
    private String teacherClass;
    private Integer grade;

	public JwTeacherBaseInfo(){
	}
    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
    }
    public void setTeacherName(String value) {
        this.teacherName = value;
    }

    public String getTeacherName() {
        return this.teacherName;
    }
    public void setTeacherCourse(String value) {
        this.teacherCourse = value;
    }

    public String getTeacherCourse() {
        return this.teacherCourse;
    }
    public void setTeacherClass(String value) {
        this.teacherClass = value;
    }

    public String getTeacherClass() {
        return this.teacherClass;
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
			.append("TeacherName",getTeacherName())
			.append("TeacherCourse",getTeacherCourse())
			.append("TeacherClass",getTeacherClass())
			.append("Grade",getGrade())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwTeacherBaseInfo == false) return false;
		if(this == obj) return true;
		JwTeacherBaseInfo other = (JwTeacherBaseInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

