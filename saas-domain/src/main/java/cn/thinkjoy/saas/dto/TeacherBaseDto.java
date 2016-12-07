package cn.thinkjoy.saas.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangguorong on 16/12/7.
 */
public class TeacherBaseDto implements Serializable {

    private long teacherId; // 教师ID
    private String teacherName; // 教师姓名
    private long courseId; // 课程ID
    private String courseName; // 课程名
    private int classNum; // 最大带班数
    private List<ClassBaseDto> classInfo;

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
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

    public List<ClassBaseDto> getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(List<ClassBaseDto> classInfo) {
        this.classInfo = classInfo;
    }

    @Override
    public String toString() {
        return "TeacherBaseDto{" +
                "teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", classNum=" + classNum +
                ", classInfo=" + classInfo +
                '}';
    }
}
