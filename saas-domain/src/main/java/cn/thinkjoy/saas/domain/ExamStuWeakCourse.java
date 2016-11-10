/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  ExamStuWeakCourse.java 2016-11-10 17:35:30 $
 */





package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class ExamStuWeakCourse extends BaseDomain<Long>{
    /**  */
    private Long examId;
    /**  */
    private String batchName;
    /**  */
    private Long examDetailId;
    /**  */
    private String weakCourseOne;
    /**  */
    private String weakCourseTwo;
    /**  */
    private String weakCourseDetails;

	public ExamStuWeakCourse(){
	}
    public void setExamId(Long value) {
        this.examId = value;
    }

    public Long getExamId() {
        return this.examId;
    }
    public void setBatchName(String value) {
        this.batchName = value;
    }

    public String getBatchName() {
        return this.batchName;
    }
    public void setExamDetailId(Long value) {
        this.examDetailId = value;
    }

    public Long getExamDetailId() {
        return this.examDetailId;
    }
    public void setWeakCourseOne(String value) {
        this.weakCourseOne = value;
    }

    public String getWeakCourseOne() {
        return this.weakCourseOne;
    }
    public void setWeakCourseTwo(String value) {
        this.weakCourseTwo = value;
    }

    public String getWeakCourseTwo() {
        return this.weakCourseTwo;
    }
    public void setWeakCourseDetails(String value) {
        this.weakCourseDetails = value;
    }

    public String getWeakCourseDetails() {
        return this.weakCourseDetails;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ExamId",getExamId())
			.append("BatchName",getBatchName())
			.append("ExamDetailId",getExamDetailId())
			.append("WeakCourseOne",getWeakCourseOne())
			.append("WeakCourseTwo",getWeakCourseTwo())
			.append("WeakCourseDetails",getWeakCourseDetails())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ExamStuWeakCourse == false) return false;
		if(this == obj) return true;
		ExamStuWeakCourse other = (ExamStuWeakCourse)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

