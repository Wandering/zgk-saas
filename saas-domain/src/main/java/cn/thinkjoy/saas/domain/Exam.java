/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  Exam.java 2016-11-01 14:30:27 $
 */





package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class Exam extends BaseDomain<Long>{
    /**  */
    private String examName;
    /**  */
    private String examTime;
    /**  */
    private String grade;
    /**  */
    private String uploadFilePath;

    private String createDate;

    private String tnId;

    private String originFileName;

	public Exam(){
	}

    public String getTnId()
    {
        return tnId;
    }

    public void setTnId(String tnId)
    {
        this.tnId = tnId;
    }

    public void setExamName(String value) {
        this.examName = value;
    }

    public String getExamName() {
        return this.examName;
    }
    public void setExamTime(String value) {
        this.examTime = value;
    }

    public String getExamTime() {
        return this.examTime;
    }
    public void setGrade(String value) {
        this.grade = value;
    }

    public String getGrade() {
        return this.grade;
    }
    public void setUploadFilePath(String value) {
        this.uploadFilePath = value;
    }

    public String getUploadFilePath() {
        return this.uploadFilePath;
    }

    public String getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }

    public String getOriginFileName()
    {
        return originFileName;
    }

    public void setOriginFileName(String originFileName)
    {
        this.originFileName = originFileName;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ExamName",getExamName())
			.append("ExamTime",getExamTime())
			.append("Grade",getGrade())
			.append("UploadFilePath",getUploadFilePath())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Exam == false) return false;
		if(this == obj) return true;
		Exam other = (Exam)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

