package cn.thinkjoy.saas.domain.bussiness;

import cn.thinkjoy.common.domain.BaseDomain;

import java.io.Serializable;

/**
 * Created by douzy on 17/2/13.
 */
public class CourseManage extends BaseDomain implements Serializable {
    private Integer tnId;
    private Integer courseBaseId;
    private byte custom;
    private Integer gradeId;
    private String courseType;
    private long createTime;

    public Integer getTnId() {
        return tnId;
    }

    public void setTnId(Integer tnId) {
        this.tnId = tnId;
    }

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

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
