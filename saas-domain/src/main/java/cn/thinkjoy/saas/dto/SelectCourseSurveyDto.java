package cn.thinkjoy.saas.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by yangguorong on 17/3/2.
 */
public class SelectCourseSurveyDto implements Serializable {

    // 选课名称
    private String name;
    // 年级编号 1：高一  2：高二  3：高三
    private int grade;
    // 开始时间：2017-02-28 13:00:00
    private Date startTime;
    // 结束时间：2017-02-28 13:00:00
    private Date endTime;
    // 已选学生数
    private int selectedCount;
    // 未选学生数
    private int unSelectedCount;
    // 未选学生集合
    private List<BaseStuDto> unSelectedList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(int selectedCount) {
        this.selectedCount = selectedCount;
    }

    public int getUnSelectedCount() {
        return unSelectedCount;
    }

    public void setUnSelectedCount(int unSelectedCount) {
        this.unSelectedCount = unSelectedCount;
    }

    public List<BaseStuDto> getUnSelectedList() {
        return unSelectedList;
    }

    public void setUnSelectedList(List<BaseStuDto> unSelectedList) {
        this.unSelectedList = unSelectedList;
    }

    @Override
    public String toString() {
        return "SelectCourseSurveyDto{" +
                "name='" + name + '\'' +
                ", grade=" + grade +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", selectedCount=" + selectedCount +
                ", unSelectedCount=" + unSelectedCount +
                ", unSelectedList=" + unSelectedList +
                '}';
    }
}
