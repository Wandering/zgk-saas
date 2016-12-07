/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwCourseBaseInfo.java 2016-12-06 15:06:24 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwCourseBaseInfo extends BaseDomain{
    private Integer tnId;
    private String courseName;
    private Integer courseType;
    private String grade;

	public JwCourseBaseInfo(){
	}
    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
    }
    public void setCourseName(String value) {
        this.courseName = value;
    }

    public String getCourseName() {
        return this.courseName;
    }
    public void setCourseType(Integer value) {
        this.courseType = value;
    }

    public Integer getCourseType() {
        return this.courseType;
    }
    public void setGrade(String value) {
        this.grade = value;
    }

    public String getGrade() {
        return this.grade;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TnId",getTnId())
			.append("CourseName",getCourseName())
			.append("CourseType",getCourseType())
			.append("Grade",getGrade())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwCourseBaseInfo == false) return false;
		if(this == obj) return true;
		JwCourseBaseInfo other = (JwCourseBaseInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

