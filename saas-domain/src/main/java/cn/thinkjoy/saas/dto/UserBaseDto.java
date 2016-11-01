package cn.thinkjoy.saas.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangguorong on 16/10/14.
 */
public class UserBaseDto implements Serializable {

    /**
     * 用户Id
     */
    private int userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 状态 0:正常 1:被禁用
     */
    private Integer status;

    /**
     * 角色ID
     */
    private int roleId;

    /**
     * 角色名
     */
    private String role;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserBaseDto{" +
                "userName='" + userName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", telephone='" + telephone + '\'' +
                ", createDate=" + createDate +
                ", status=" + status +
                ", role='" + role + '\'' +
                '}';
    }
}
