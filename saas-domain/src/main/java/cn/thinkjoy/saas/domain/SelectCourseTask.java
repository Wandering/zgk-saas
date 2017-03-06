/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  SelectCourseTask.java 2017-02-27 09:56:07 $
 */





package cn.thinkjoy.saas.domain;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class SelectCourseTask extends CreateBaseDomain<Long> {
    /** 选课名称 */
    private String name;
    /** 年级 */
    private Integer grade;
    /** 选课开始时间 */
    private Date startTime;
    /** 选课结束时间 */
    private Date endTime;
    /** 选课状态  0：未设置  1：未开始  2：进行中  3：已结束  4：学校已使用此次选课数据 */
    private Integer state;
    /** 租户ID */
    private Integer tnId;
    /** 修改时间 */
    private Long modifyDate;

	public SelectCourseTask(){
	}
    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }
    public void setGrade(Integer value) {
        this.grade = value;
    }

    public Integer getGrade() {
        return this.grade;
    }

    public void setStartTime(Date value) {
        this.startTime = value;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setEndTime(Date value) {
        this.endTime = value;
    }

    public Date getEndTime() {
        return this.endTime;
    }
    public void setState(Integer value) {
        this.state = value;
    }

    public Integer getState() {
        return this.state;
    }
    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
    }
    public void setModifyDate(Long value) {
        this.modifyDate = value;
    }

    public Long getModifyDate() {
        return this.modifyDate;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name", getName())
			.append("Grade", getGrade())
			.append("StartTime",getStartTime())
			.append("EndTime",getEndTime())
			.append("State",getState())
			.append("TnId",getTnId())
			.append("CreateDate",getCreateDate())
			.append("ModifyDate",getModifyDate())
			.append("Status",getStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SelectCourseTask == false) return false;
		if(this == obj) return true;
		SelectCourseTask other = (SelectCourseTask)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

