/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwScheduleTask.java 2016-12-06 15:06:24 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwScheduleTask extends BaseDomain{
    private String scheduleName;
    private String grade;
    private String year;
    private String term;
    private Integer createDate;
    private Integer tnId;
    private Integer status;

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
    public void setCreateDate(Integer value) {
        this.createDate = value;
    }

    public Integer getCreateDate() {
        return this.createDate;
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
			.toString();
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

