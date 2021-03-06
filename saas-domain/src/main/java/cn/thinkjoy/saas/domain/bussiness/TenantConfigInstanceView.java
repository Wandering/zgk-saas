package cn.thinkjoy.saas.domain.bussiness;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by douzy on 16/10/14.
 */
public class TenantConfigInstanceView  extends BaseDomain {
    public TenantConfigInstanceView() {
    }

    private Integer tnId;
    private String configKey;
    private Integer configOrder;
    private String checkRule;
    private String domain;
    private String name;
    private String enName;
    private Long createDate;
    private Long modifyDate;
    private String  dataType;
    private String  dataUrl;
    private String  dataValue;
    private byte isRetain;


    public Integer getTnId() {
        return tnId;
    }

    public void setTnId(Integer tnId) {
        this.tnId = tnId;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public Integer getConfigOrder() {
        return configOrder;
    }

    public void setConfigOrder(Integer configOrder) {
        this.configOrder = configOrder;
    }

    public String getCheckRule() {
        return checkRule;
    }

    public void setCheckRule(String checkRule) {
        this.checkRule = checkRule;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Long modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public byte getIsRetain() {
        return isRetain;
    }

    public void setIsRetain(byte isRetain) {
        this.isRetain = isRetain;
    }
}
