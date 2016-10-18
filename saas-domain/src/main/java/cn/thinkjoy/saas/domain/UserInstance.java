/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  UserInstance.java 2016-10-14 14:15:50 $
 */



package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class UserInstance extends BaseDomain{
    private String tnId;
    private String userName;
    private String userAccount;
    private String userPass;
    private String telephone;
    private String isSuperManager;
    private Date createDate;
    private Integer status;

	public UserInstance(){
	}
    public void setTnId(String value) {
        this.tnId = value;
    }

    public String getTnId() {
        return this.tnId;
    }
    public void setUserName(String value) {
        this.userName = value;
    }

    public String getUserName() {
        return this.userName;
    }
    public void setUserAccount(String value) {
        this.userAccount = value;
    }

    public String getUserAccount() {
        return this.userAccount;
    }
    public void setUserPass(String value) {
        this.userPass = value;
    }

    public String getUserPass() {
        return this.userPass;
    }
    public void setTelephone(String value) {
        this.telephone = value;
    }

    public String getTelephone() {
        return this.telephone;
    }
    public void setIsSuperManager(String value) {
        this.isSuperManager = value;
    }

    public String getIsSuperManager() {
        return this.isSuperManager;
    }

    public void setCreateDate(Date value) {
        this.createDate = value;
    }

    public Date getCreateDate() {
        return this.createDate;
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
			.append("TnId",getTnId())
			.append("UserName",getUserName())
			.append("UserAccount",getUserAccount())
			.append("UserPass",getUserPass())
			.append("Telephone",getTelephone())
			.append("Status",getStatus())
			.append("IsSuperManager",getIsSuperManager())
			.append("CreateDate",getCreateDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserInstance == false) return false;
		if(this == obj) return true;
		UserInstance other = (UserInstance)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

