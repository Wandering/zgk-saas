package cn.thinkjoy.saas.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangguorong on 16/10/13.
 */
public class UserInfoDto implements Serializable {

    /**
     * 学校租户名称
     */
    private String tnName;

    /**
     * 学校租户地址
     */
    private String tnAddr;

    /**
     * 是否已初始化 0:没有 1:已初始化
     */
    private int isInit;

    /**
     * 用户Id
     */
    private int userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户登录账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPass;

    /**
     * 是否超级管理员 0：不是 1：是
     */
    private String isSuperManager;

    /**
     * 状态：0 启用，1禁用
     */
    private int status;

    /**
     * 区域Id
     */
    private long countyId;

    /**
     * 租户首页地址
     */
    private String indexUrl;

    /**
     * 角色集合
     */
    private List<String> roles;

    /**
     * 菜单集合
     */
    private List<MeunDto> meuns;

    private String tnId;


    public String getTnId()
    {
        return tnId;
    }

    public void setTnId(String tnId)
    {
        this.tnId = tnId;
    }

    public String getTnName() {
        return tnName;
    }

    public void setTnName(String tnName) {
        this.tnName = tnName;
    }

    public String getTnAddr() {
        return tnAddr;
    }

    public void setTnAddr(String tnAddr) {
        this.tnAddr = tnAddr;
    }

    public int getIsInit() {
        return isInit;
    }

    public void setIsInit(int isInit) {
        this.isInit = isInit;
    }

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

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getIsSuperManager() {
        return isSuperManager;
    }

    public void setIsSuperManager(String isSuperManager) {
        this.isSuperManager = isSuperManager;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<MeunDto> getMeuns() {
        return meuns;
    }

    public void setMeuns(List<MeunDto> meuns) {
        this.meuns = meuns;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCountyId() {
        return countyId;
    }

    public void setCountyId(long countyId) {
        this.countyId = countyId;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "tnName='" + tnName + '\'' +
                ", tnAddr='" + tnAddr + '\'' +
                ", isInit=" + isInit +
                ", userName='" + userName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", userPass='" + userPass + '\'' +
                ", isSuperManager='" + isSuperManager + '\'' +
                ", roles=" + roles +
                ", meuns=" + meuns +
                '}';
    }
}
