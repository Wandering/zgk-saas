package cn.thinkjoy.saas.dto;

import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.saas.domain.bussiness.CourseManage;

import java.io.Serializable;

/**
 * Created by douzy on 17/2/13.
 */
public class CourseManageDto extends CourseManage{
    private String courseBaseName;

    public String getCourseBaseName() {
        return courseBaseName;
    }

    public void setCourseBaseName(String courseBaseName) {
        this.courseBaseName = courseBaseName;
    }
}
