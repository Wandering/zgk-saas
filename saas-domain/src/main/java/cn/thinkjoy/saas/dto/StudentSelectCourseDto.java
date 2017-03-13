package cn.thinkjoy.saas.dto;

/**
 * Created by zuohao on 17/2/27.
 */
public class StudentSelectCourseDto {
    private String studentNo;
    private String selectCourseList;
    private String schoolCourseId;
    private String schoolCourse;

    public String getSchoolCourseId() {
        return schoolCourseId;
    }

    public void setSchoolCourseId(String schoolCourseId) {
        this.schoolCourseId = schoolCourseId;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getSelectCourseList() {
        return selectCourseList;
    }

    public void setSelectCourseList(String selectCourseList) {
        this.selectCourseList = selectCourseList;
    }

    public String getSchoolCourse() {
        return schoolCourse;
    }

    public void setSchoolCourse(String schoolCourse) {
        this.schoolCourse = schoolCourse;
    }
}
