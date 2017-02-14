package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.domain.bussiness.CourseBaseInfo;

import java.util.List;

/**
 * Created by douzy on 17/2/14.
 */
public interface ICourseBaseInfoService {
    /**
     * 获取课程基础信息集
     * @return
     */
    public List<CourseBaseInfo> getCourseBaseInfoList();
}
