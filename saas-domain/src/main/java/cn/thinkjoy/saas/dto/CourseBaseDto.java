package cn.thinkjoy.saas.dto;

import java.io.Serializable;

/**
 * Created by yangguorong on 16/12/7.
 */
public class CourseBaseDto implements Serializable {

    private int courseId; // 课程ID

    private String courseName; // 课程名

    private String time; // 课时

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CourseBaseDto{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
