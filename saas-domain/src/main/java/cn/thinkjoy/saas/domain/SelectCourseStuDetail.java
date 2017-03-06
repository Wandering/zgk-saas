/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  SelectCourseStuDetail.java 2017-02-27 10:11:18 $
 */





package cn.thinkjoy.saas.domain;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class SelectCourseStuDetail extends CreateBaseDomain<Long> {
    /** 修改时间 */
    private Long modifyDate;
    /** 学号 */
    private String stuNo;
    /** 校本课程Id */
    private Integer courseId;
    /** 校本课程名称 */
    private String courseName;
    /** 任务ID */
    private Integer taskId;

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public SelectCourseStuDetail(){
	}
    public void setModifyDate(Long value) {
        this.modifyDate = value;
    }

    public Long getModifyDate() {
        return this.modifyDate;
    }
    public void setStuNo(String value) {
        this.stuNo = value;
    }

    public String getStuNo() {
        return this.stuNo;
    }
    public void setCourseId(Integer value) {
        this.courseId = value;
    }

    public Integer getCourseId() {
        return this.courseId;
    }
    public void setCourseName(String value) {
        this.courseName = value;
    }

    public String getCourseName() {
        return this.courseName;
    }
    public void setTaskId(Integer value) {
        this.taskId = value;
    }

    public Integer getTaskId() {
        return this.taskId;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("CreateDate",getCreateDate())
			.append("ModifyDate",getModifyDate())
			.append("Status",getStatus())
			.append("StuNo",getStuNo())
			.append("CourseId",getCourseId())
			.append("CourseName",getCourseName())
			.append("TaskId",getTaskId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SelectCourseStuDetail == false) return false;
		if(this == obj) return true;
		SelectCourseStuDetail other = (SelectCourseStuDetail)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

