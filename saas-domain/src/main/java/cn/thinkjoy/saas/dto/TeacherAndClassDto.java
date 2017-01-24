package cn.thinkjoy.saas.dto;

import java.io.Serializable;

/**
 * Created by yangguorong on 17/1/18.
 */
public class TeacherAndClassDto implements Serializable {

    /**
     * 教师ID
     */
    private int teacherId;

    /**
     * 老师名称
     */
    private String teacherName;

    /**
     * 所教科目
     */
    private String courseName;

    /**
     * 所教年级
     */
    private String grade;

    /**
     * 所带班级
     */
    private String className;

    /**
     * 最大带班数
     */
    private int maxClass;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getMaxClass() {
        return maxClass;
    }

    public void setMaxClass(int maxClass) {
        this.maxClass = maxClass;
    }

    @Override
    public String toString() {
        return "TeacherAndClassDto{" +
                "teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", grade='" + grade + '\'' +
                ", className='" + className + '\'' +
                ", maxClass=" + maxClass +
                '}';
    }
}
