package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.domain.bussiness.CourseBaseInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 17/2/14.
 */
public interface IEXCourseBaseInfoService {
    /**
     * 获取课程基础信息集
     * @return
     */
    List<CourseBaseInfo> getCourseBaseInfoList();

}
