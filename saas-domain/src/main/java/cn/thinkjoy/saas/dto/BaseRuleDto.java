package cn.thinkjoy.saas.dto;

import java.io.Serializable;

/**
 * Created by zuohao on 16/12/6.
 */
public class BaseRuleDto implements Serializable {
    private String id;
    private String tnId;
    private String courseId;
    private String courseName;
    private String teacherId;
    private String teacherName;
    private String importantType;
    private String weekType;
    private String dayType;
    private String dayConType;
    private String createDate;
    private String taskId;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getImportantType() {
        return importantType;
    }

    public void setImportantType(String importantType) {
        this.importantType = importantType;
    }

    public String getWeekType() {
        return weekType;
    }

    public void setWeekType(String weekType) {
        this.weekType = weekType;
    }

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    public String getDayConType() {
        return dayConType;
    }

    public void setDayConType(String dayConType) {
        this.dayConType = dayConType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
