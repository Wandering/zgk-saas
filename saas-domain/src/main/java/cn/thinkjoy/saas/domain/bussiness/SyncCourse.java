package cn.thinkjoy.saas.domain.bussiness;

import java.io.Serializable;

/**
 * Created by douzy on 16/12/8.
 */
public class SyncCourse implements Serializable {
    private String major;
    private String stuClass;
    private String grade;

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStuClass() {
        return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
