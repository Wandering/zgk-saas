package cn.thinkjoy.saas.dto;

import java.io.Serializable;

/**
 * Created by zuohao on 16/12/6.
 */
public class MergeClassInfoDto implements Serializable {
    private String id;
    private String tnId;
    private String courseId;
    private String courseName;
    private String classIds;
    private String classNames;
    private String taskId;
    private String createDate;
    private Integer classType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTnId() {
        return tnId;
    }

    public void setTnId(String tnId) {
        this.tnId = tnId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassIds() {
        return classIds;
    }

    public void setClassIds(String classIds) {
        this.classIds = classIds;
    }

    public String getClassNames() {
        return classNames;
    }

    public void setClassNames(String classNames) {
        this.classNames = classNames;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getClassType() {
        return classType;
    }

    public void setClassType(Integer classType) {
        this.classType = classType;
    }

    @Override
    public String toString() {
        return "MergeClassInfoDto{" +
                "id='" + id + '\'' +
                ", tnId='" + tnId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", classIds='" + classIds + '\'' +
                ", classNames='" + classNames + '\'' +
                ", taskId='" + taskId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", classType=" + classType +
                '}';
    }
}
