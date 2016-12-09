package cn.thinkjoy.saas.domain.bussiness;

import java.io.Serializable;
import java.util.List;

/**
 * Created by douzy on 16/12/9.
 */
public class CourseResultView implements Serializable {
    /**
     * 每周上课天数
     */
    private String  teachDate;
    /**
     * 每天上课节次
     */
    private String teachTime;
    private List<String> courseCon;

    public String getTeachDate() {
        return teachDate;
    }

    public void setTeachDate(String teachDate) {
        this.teachDate = teachDate;
    }

    public String getTeachTime() {
        return teachTime;
    }

    public void setTeachTime(String teachTime) {
        this.teachTime = teachTime;
    }

    public List<String> getCourseCon() {
        return courseCon;
    }

    public void setCourseCon(List<String> courseCon) {
        this.courseCon = courseCon;
    }
}
