/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  ClassRooms.java 2016-10-12 10:51:39 $
 */



package cn.thinkjoy.saas.domain;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ClassRooms extends BaseDomain{
    private Integer tnId;
    private Integer grade1classes;
    private Integer grade2classes;
    private Integer grade3classes;
    private Long createDate;

	public ClassRooms(){
	}
    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
    }
    public void setGrade1classes(Integer value) {
        this.grade1classes = value;
    }

    public Integer getGrade1classes() {
        return this.grade1classes;
    }
    public void setGrade2classes(Integer value) {
        this.grade2classes = value;
    }

    public Integer getGrade2classes() {
        return this.grade2classes;
    }
    public void setGrade3classes(Integer value) {
        this.grade3classes = value;
    }

    public Integer getGrade3classes() {
        return this.grade3classes;
    }

    public void setCreateDate(Long value) {
        this.createDate = value;
    }

    public Long getCreateDate() {
        return this.createDate;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TnId",getTnId())
			.append("Grade1classes",getGrade1classes())
			.append("Grade2classes",getGrade2classes())
			.append("Grade3classes",getGrade3classes())
			.append("CreateDate",getCreateDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ClassRooms == false) return false;
		if(this == obj) return true;
		ClassRooms other = (ClassRooms)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

