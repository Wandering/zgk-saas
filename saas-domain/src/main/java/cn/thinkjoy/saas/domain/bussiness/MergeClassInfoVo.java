package cn.thinkjoy.saas.domain.bussiness;

import java.io.Serializable;

/**
 * Created by douzy on 17/2/17.
 */
public class MergeClassInfoVo implements Serializable {
    private Integer courseId;
    private String classIds;
    private Integer taskId;
    private Integer classType;
    private String courseHour;
    private byte chour;
    private String courseName;
    private String courseType;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getClassIds() {
        return classIds;
    }

    public void setClassIds(String classIds) {
        this.classIds = classIds;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getClassType() {
        return classType;
    }

    public void setClassType(Integer classType) {
        this.classType = classType;
    }

    public String getCourseHour() {
        return courseHour;
    }

    public void setCourseHour(String courseHour) {
        this.courseHour = courseHour;
    }

    public byte getChour() {
        return chour;
    }

    public void setChour(byte chour) {
        this.chour = chour;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }
}
