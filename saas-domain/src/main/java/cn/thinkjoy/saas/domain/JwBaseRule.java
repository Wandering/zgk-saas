package cn.thinkjoy.saas.domain;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by liusven on 2016/12/5.
 */
public abstract class JwBaseRule extends BaseDomain<Integer>
{
    /** 周一不上课节数，默认为全上9个1：“111111111”，如果勾选某个节，不上，则对应的值为“0” */
    public String mon;
    /** 周二不上课节数，默认为全上9个1：“111111111”，如果勾选某个节，不上，则对应的值为“0” */
    public String tues;
    /** 周三不上课节数，默认为全上9个1：“111111111”，如果勾选某个节，不上，则对应的值为“0” */
    public String wed;
    /** 周四不上课节数，默认为全上9个1：“111111111”，如果勾选某个节，不上，则对应的值为“0” */
    public String thur;
    /** 周五不上课节数，默认为全上9个1：“111111111”，如果勾选某个节，不上，则对应的值为“0” */
    public String fri;
    /** 周六不上课节数，默认为全上9个1：“111111111”，如果勾选某个节，不上，则对应的值为“0” */
    public String sut;
    /** 周日不上课节数，默认为全上9个1：“111111111”，如果勾选某个节，不上，则对应的值为“0” */
    public String sun;

    public JwBaseRule(){
    }
    public void setMon(String value) {
        this.mon = value;
    }

    public String getMon() {
        return this.mon;
    }
    public void setTues(String value) {
        this.tues = value;
    }

    public String getTues() {
        return this.tues;
    }
    public void setWed(String value) {
        this.wed = value;
    }

    public String getWed() {
        return this.wed;
    }
    public void setThur(String value) {
        this.thur = value;
    }

    public String getThur() {
        return this.thur;
    }
    public void setFri(String value) {
        this.fri = value;
    }

    public String getFri() {
        return this.fri;
    }
    public void setSut(String value) {
        this.sut = value;
    }

    public String getSut() {
        return this.sut;
    }
    public void setSun(String value) {
        this.sun = value;
    }

    public String getSun() {
        return this.sun;
    }

    public abstract void setTypeId(Integer value);
}
