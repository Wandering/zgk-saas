package cn.thinkjoy.saas.domain.bussiness;

import cn.thinkjoy.common.domain.BaseDomain;

import java.io.Serializable;

/**
 * Created by douzy on 17/2/13.
 */
public class CourseBaseInfo extends BaseDomain implements Serializable {
    private String courseName;
    private byte status;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
