package cn.thinkjoy.saas.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangguorong on 17/1/18.
 */
public class CourseAndTeacherDto implements Serializable {

    /**
     * 课程名
     */
    private String courseName;

    /**
     * 教师数量
     */
    private int teacherNum;

    /**
     * 最大带班数
     */
    private int classMaxNum;

    /**
     * 最大选课人数
     */
    private int stuMaxNum;

    private List<TeacherAndClassDto> teachers = new ArrayList<>();

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getTeacherNum() {
        return teacherNum;
    }

    public void setTeacherNum(int teacherNum) {
        this.teacherNum = teacherNum;
    }

    public int getClassMaxNum() {
        return classMaxNum;
    }

    public void setClassMaxNum(int classMaxNum) {
        this.classMaxNum = classMaxNum;
    }

    public int getStuMaxNum() {
        return stuMaxNum;
    }

    public void setStuMaxNum(int stuMaxNum) {
        this.stuMaxNum = stuMaxNum;
    }

    public List<TeacherAndClassDto> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherAndClassDto> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return "CourseAndTeacherDto{" +
                "courseName='" + courseName + '\'' +
                ", teacherNum='" + teacherNum + '\'' +
                ", classMaxNum='" + classMaxNum + '\'' +
                ", stuMaxNum='" + stuMaxNum + '\'' +
                ", teachers=" + teachers +
                '}';
    }
}
