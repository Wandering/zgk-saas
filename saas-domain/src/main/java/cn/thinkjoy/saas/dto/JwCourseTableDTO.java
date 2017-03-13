package cn.thinkjoy.saas.dto;

import cn.thinkjoy.saas.domain.JwCourseTable;

/**
 * Created by yangyongping on 2017/2/27.
 */
public class JwCourseTableDTO extends JwCourseTable {
    private String teacherName;
    private String className;
    private String courseName;
    private String roomName;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
