/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwScheduleTask.java 2016-12-06 15:06:24 $
 */



package cn.thinkjoy.saas.domain;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class JwScheduleTask extends BaseDomain{
    private String scheduleName;
    private String grade;
    private String year;
    private String term;
    private Long createDate;
    private Integer tnId;
    private Integer status;
    private Integer delStatus;
    private String path;

	public JwScheduleTask(){

	}
    public void setScheduleName(String value) {
        this.scheduleName = value;
    }

    public String getScheduleName() {
        return this.scheduleName;
    }
    public void setGrade(String value) {
        this.grade = value;
    }

    public String getGrade() {
        return this.grade;
    }
    public void setYear(String value) {
        this.year = value;
    }

    public String getYear() {
        return this.year;
    }
    public void setTerm(String value) {
        this.term = value;
    }

    public String getTerm() {
        return this.term;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
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
			.append("ScheduleName",getScheduleName())
			.append("Grade",getGrade())
			.append("Year",getYear())
			.append("Term",getTerm())
			.append("Status",getStatus())
			.append("CreateDate",getCreateDate())
			.append("TnId",getTnId())
			.append("delStatus",getDelStatus())
            .append("Path",getPath())
			.toString();
	}

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}


	public boolean equals(Object obj) {
		if(obj instanceof JwScheduleTask == false) return false;
		if(this == obj) return true;
		JwScheduleTask other = (JwScheduleTask)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

