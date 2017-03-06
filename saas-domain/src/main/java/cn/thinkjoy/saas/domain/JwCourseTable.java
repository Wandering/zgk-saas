/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwCourseTable.java 2017-02-23 16:59:42 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwCourseTable extends BaseDomain{
    private Integer tnId;
    private Integer taskId;
    private Integer gradeId;
    private Integer week;
    private Integer sort;
    private Integer classId;
    private Integer teacherId;
    private Integer courseId;
    private Integer status;

	public JwCourseTable(){
	}
    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
    }
    public void setTaskId(Integer value) {
        this.taskId = value;
    }

    public Integer getTaskId() {
        return this.taskId;
    }
    public void setGradeId(Integer value) {
        this.gradeId = value;
    }

    public Integer getGradeId() {
        return this.gradeId;
    }
    public void setWeek(Integer value) {
        this.week = value;
    }

    public Integer getWeek() {
        return this.week;
    }
    public void setSort(Integer value) {
        this.sort = value;
    }

    public Integer getSort() {
        return this.sort;
    }
    public void setClassId(Integer value) {
        this.classId = value;
    }

    public Integer getClassId() {
        return this.classId;
    }
    public void setTeacherId(Integer value) {
        this.teacherId = value;
    }

    public Integer getTeacherId() {
        return this.teacherId;
    }
    public void setCourseId(Integer value) {
        this.courseId = value;
    }

    public Integer getCourseId() {
        return this.courseId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TnId",getTnId())
			.append("TaskId",getTaskId())
			.append("GradeId",getGradeId())
			.append("Week",getWeek())
			.append("Sort",getSort())
			.append("ClassId",getClassId())
			.append("TeacherId",getTeacherId())
			.append("CourseId",getCourseId())
			.append("Status",getStatus())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof JwCourseTable == false) return false;
		if(this == obj) return true;
		JwCourseTable other = (JwCourseTable)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

