package cn.thinkjoy.saas.domain.bussiness;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by zuohao on 16/12/6.
 */
public class MergeClassInfo extends BaseDomain {
    private String tnId;
    private String courseId;
    private String classIds;
    private String taskId;
    private String createDate;

    public String getTnId() {
        return tnId;
    }

    public void setTnId(String tnId) {
        this.tnId = tnId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getClassIds() {
        return classIds;
    }

    public void setClassIds(String classIds) {
        this.classIds = classIds;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
