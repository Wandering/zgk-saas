package cn.thinkjoy.saas.domain;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by douzy on 16/10/21.
 */
public class Grade extends BaseDomain {

    private Integer tnId;

    private String grade;

    private Integer gOrder;

    public Integer getTnId() {
        return tnId;
    }

    public void setTnId(Integer tnId) {
        this.tnId = tnId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getgOrder() {
        return gOrder;
    }

    public void setgOrder(Integer gOrder) {
        this.gOrder = gOrder;
    }
}
