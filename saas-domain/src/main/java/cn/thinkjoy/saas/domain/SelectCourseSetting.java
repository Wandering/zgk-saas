/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  SelectCourseSetting.java 2017-02-27 10:14:36 $
 */





package cn.thinkjoy.saas.domain;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class SelectCourseSetting extends CreateBaseDomain<Long> {
    /** 修改时间 */
    private Long modifyDate;
    /** 课程集合，存储规则 json  list  [{"id":"1","name":"物理"},{"id":"2","name":"化学"}] */
    private String courses;
    /** 可选门数 */
    private Integer selectCount;
    /** 课程类型  0：高考科目  1：校本课程 */
    private Integer type;
    /** 任务ID */
    private Integer taskId;

	public SelectCourseSetting(){
	}
    public void setModifyDate(Long value) {
        this.modifyDate = value;
    }

    public Long getModifyDate() {
        return this.modifyDate;
    }
    public void setCourses(String value) {
        this.courses = value;
    }

    public String getCourses() {
        return this.courses;
    }
    public void setSelectCount(Integer value) {
        this.selectCount = value;
    }

    public Integer getSelectCount() {
        return this.selectCount;
    }
    public void setType(Integer value) {
        this.type = value;
    }

    public Integer getType() {
        return this.type;
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
			.append("Courses",getCourses())
			.append("SelectCount",getSelectCount())
			.append("Type",getType())
			.append("TaskId",getTaskId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SelectCourseSetting == false) return false;
		if(this == obj) return true;
		SelectCourseSetting other = (SelectCourseSetting)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

