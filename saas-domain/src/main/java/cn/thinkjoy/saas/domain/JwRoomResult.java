/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwRoomResult.java 2016-12-06 15:06:24 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwRoomResult extends BaseDomain{
    private Integer roomId;
    private String result;
    private Integer createDate;
    private Integer tnId;
    private Integer taskId;

	public JwRoomResult(){
	}
    public void setRoomId(Integer value) {
        this.roomId = value;
    }

    public Integer getRoomId() {
        return this.roomId;
    }
    public void setResult(String value) {
        this.result = value;
    }

    public String getResult() {
        return this.result;
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
    public void setTaskId(Integer value) {
        this.taskId = value;
    }

    public Integer getTaskId() {
        return this.taskId;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("RoomId",getRoomId())
			.append("Result",getResult())
			.append("CreateDate",getCreateDate())
			.append("TnId",getTnId())
			.append("TaskId",getTaskId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwRoomResult == false) return false;
		if(this == obj) return true;
		JwRoomResult other = (JwRoomResult)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

