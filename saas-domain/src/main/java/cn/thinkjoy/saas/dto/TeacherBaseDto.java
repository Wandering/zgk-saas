package cn.thinkjoy.saas.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangguorong on 16/12/7.
 */
public class TeacherBaseDto implements Serializable {

    private int teacherId; // 教师ID
    private String teacherName; // 教师姓名
    private String courseName; // 课程名
    private int classNum; // 最大带班数
    private String classes; // 所带班级
    private String grade; // 所带年纪
    private int isAttend = 0;// 是否排课 0：不排课 1：排课

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

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

    public int getClassNum() {
        return classNum;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getIsAttend() {
        return isAttend;
    }

    public void setIsAttend(int isAttend) {
        this.isAttend = isAttend;
    }

    @Override
    public String toString() {
        return "TeacherBaseDto{" +
                "teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", classNum=" + classNum +
                ", classes='" + classes + '\'' +
                ", grade='" + grade + '\'' +
                ", isAttend=" + isAttend +
                '}';
    }
}
