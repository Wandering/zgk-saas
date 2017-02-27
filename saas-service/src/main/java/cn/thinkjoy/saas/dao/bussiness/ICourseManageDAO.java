package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.saas.domain.bussiness.CourseManage;
import cn.thinkjoy.saas.domain.bussiness.CourseManageVo;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 17/2/13.
 */
public interface ICourseManageDAO extends IBaseDAO<CourseManage> {

    List<CourseManageVo> selectCourseManageInfo(Map map);

    List<CourseManageVo> selectCourseManageGroup(Map map);

    Integer addCourses(List<CourseManage> courseManages);

    List<Map> selectCourseList(Map map);

    List<CourseManageVo> selectCourseManageInfoPK(Map map);
}
