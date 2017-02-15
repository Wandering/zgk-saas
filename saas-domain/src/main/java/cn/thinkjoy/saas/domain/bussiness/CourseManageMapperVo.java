package cn.thinkjoy.saas.domain.bussiness;

import java.util.List;

/**
 * Created by douzy on 17/2/15.
 */
public class CourseManageMapperVo {
    private Integer tnId;
    private Integer courseBaseId;
    private String courseName;
    private List<GradeCourseMap> courseMapList;
    private byte custom;
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

    public List<GradeCourseMap> getCourseMapList() {
        return courseMapList;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseMapList(List<GradeCourseMap> courseMapList) {
        this.courseMapList = courseMapList;
    }

    public byte getCustom() {
        return custom;
    }

    public void setCustom(byte custom) {
        this.custom = custom;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
