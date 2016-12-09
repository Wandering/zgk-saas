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
    private List<ClassBaseDto> classInfo;

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
                ", courseName='" + courseName + '\'' +
                ", classNum=" + classNum +
                ", classInfo=" + classInfo +
                '}';
    }
}
