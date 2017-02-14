package cn.thinkjoy.saas.domain.bussiness;

import cn.thinkjoy.common.domain.BaseDomain;

import java.io.Serializable;

/**
 * Created by douzy on 17/2/13.
 */
public class CourseManage extends BaseDomain implements Serializable {
    private Integer courseBaseId;
    private byte custom;
    private String courseType1;
    private String courseType2;
    private String courseType3;
    private long createTime;

    public Integer getCourseBaseId() {
        return courseBaseId;
    }

    public void setCourseBaseId(Integer courseBaseId) {
        this.courseBaseId = courseBaseId;
    }

    public byte getCustom() {
        return custom;
    }

    public void setCustom(byte custom) {
        this.custom = custom;
    }

    public String getCourseType1() {
        return courseType1;
    }

    public void setCourseType1(String courseType1) {
        this.courseType1 = courseType1;
    }

    public String getCourseType2() {
        return courseType2;
    }

    public void setCourseType2(String courseType2) {
        this.courseType2 = courseType2;
    }

    public String getCourseType3() {
        return courseType3;
    }

    public void setCourseType3(String courseType3) {
        this.courseType3 = courseType3;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
