package cn.thinkjoy.saas.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangguorong on 17/3/2.
 */
public class BaseStuDto implements Serializable {

    // 班级名称
    private String className;
    // 学生名称
    private String stuName;
    // 学生学号
    private String stuNo;

    private List<CourseBaseDto> courses;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public List<CourseBaseDto> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseBaseDto> courses) {
        this.courses = courses;
    }


    @Override
    public String toString() {
        return "BaseStuDto{" +
                "className='" + className + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuNo='" + stuNo + '\'' +
                ", courses=" + courses +
                '}';
    }
}
