package cn.thinkjoy.saas.dto;

import java.io.Serializable;

/**
 * Created by yangguorong on 16/12/7.
 */
public class ClassBaseDto implements Serializable {

    private int classId; // 班级ID
    private String className; // 名称
    private String classGrade; // 年级
    private String classBoss; // 班主任
    private String classType; // 班级类型 教学班，行政班，文科班，理科班
    private String course; // 班级课程
    private String isMerge;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassGrade() {
        return classGrade;
    }

    public void setClassGrade(String classGrade) {
        this.classGrade = classGrade;
    }

    public String getClassBoss() {
        return classBoss;
    }

    public void setClassBoss(String classBoss) {
        this.classBoss = classBoss;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getIsMerge() {
        return isMerge;
    }

    public void setIsMerge(String isMerge) {
        this.isMerge = isMerge;
    }

    @Override
    public String toString() {
        return "ClassBaseDto{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", classGrade='" + classGrade + '\'' +
                ", classBoss='" + classBoss + '\'' +
                ", classType='" + classType + '\'' +
                ", course='" + course + '\'' +
                ", isMerge='" + isMerge + '\'' +
                '}';
    }
}
