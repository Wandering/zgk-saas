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

    /**
     * 根据Id查询课程信息
     *
     * @param id
     * @return
     */
    CourseBaseInfo getCourseBaseInfoById(int id);

    /**
     * 查询集合
     *
     * @param condition
     * @param orderBy
     * @param sortBy
     * @return
     */
    List<CourseBaseInfo> queryList(Map<String, Object> condition, String orderBy, String sortBy);
}
