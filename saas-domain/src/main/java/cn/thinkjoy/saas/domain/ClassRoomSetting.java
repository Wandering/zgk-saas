package cn.thinkjoy.saas.domain;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by douzy on 17/1/18.
 */
public class ClassRoomSetting extends BaseDomain {
    private Integer tnId;
    private Integer maxNumber;
    private long createDate;

    public Integer getTnId() {
        return tnId;
    }

    public void setTnId(Integer tnId) {
        this.tnId = tnId;
    }

    public Integer getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(Integer maxNumber) {
        this.maxNumber = maxNumber;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
