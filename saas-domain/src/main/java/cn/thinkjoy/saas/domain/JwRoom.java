/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwRoom.java 2016-12-14 15:30:36 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class JwRoom extends BaseDomain{
    private Integer tnId;
    private String roomName;
    private Integer roomType;
    private Integer grade;

	public JwRoom(){
	}
    public void setTnId(Integer value) {
        this.tnId = value;
    }

    public Integer getTnId() {
        return this.tnId;
    }
    public void setRoomName(String value) {
        this.roomName = value;
    }

    public String getRoomName() {
        return this.roomName;
    }
    public void setRoomType(Integer value) {
        this.roomType = value;
    }

    public Integer getRoomType() {
        return this.roomType;
    }
    public void setGrade(Integer value) {
        this.grade = value;
    }

    public Integer getGrade() {
        return this.grade;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TnId",getTnId())
			.append("RoomName",getRoomName())
			.append("RoomType",getRoomType())
			.append("Grade",getGrade())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JwRoom == false) return false;
		if(this == obj) return true;
		JwRoom other = (JwRoom)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

