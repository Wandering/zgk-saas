/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwGapRule.java 2016-12-06 15:06:24 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwGapRule extends BaseDomain{
    private Integer seg12;
    private Integer seg23;
    private Integer seg34;
    private Integer seg45;
    private Integer seg56;
    private Integer seg67;
    private Integer seg78;
    private Integer taskId;
    private Integer classId;
    private Integer courseId;

	public JwGapRule(){
	}
    public void setSeg12(Integer value) {
        this.seg12 = value;
    }

    public Integer getSeg12() {
        return this.seg12;
    }
    public void setSeg23(Integer value) {
        this.seg23 = value;
    }

    public Integer getSeg23() {
        return this.seg23;
    }
    public void setSeg34(Integer value) {
        this.seg34 = value;
    }

    public Integer getSeg34() {
        return this.seg34;
    }
    public void setSeg45(Integer value) {
        this.seg45 = value;
    }

    public Integer getSeg45() {
        return this.seg45;
    }
    public void setSeg56(Integer value) {
        this.seg56 = value;
    }

    public Integer getSeg56() {
        return this.seg56;
    }
    public void setSeg67(Integer value) {
        this.seg67 = value;
    }

    public Integer getSeg67() {
        return this.seg67;
    }
    public void setSeg78(Integer value) {
        this.seg78 = value;
    }

    public Integer getSeg78() {
        return this.seg78;
    }
    public void setTaskId(Integer value) {
        this.taskId = value;
    }

    public Integer getTaskId() {
        return this.taskId;
    }
    public void setClassId(Integer value) {
        this.classId = value;
    }

    public Integer getClassId() {
        return this.classId;
    }
    public void setCourseId(Integer value) {
        this.courseId = value;
    }

    public Integer getCourseId() {
        return this.courseId;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Seg12",getSeg12())
			.append("Seg23",getSeg23())
			.append("Seg34",getSeg34())
			.append("Seg45",getSeg45())
			.append("Seg56",getSeg56())
			.append("Seg67",getSeg67())
			.append("Seg78",getSeg78())
			.append("TaskId",getTaskId())
			.append("ClassId",getClassId())
			.append("CourseId",getCourseId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwGapRule == false) return false;
		if(this == obj) return true;
		JwGapRule other = (JwGapRule)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

