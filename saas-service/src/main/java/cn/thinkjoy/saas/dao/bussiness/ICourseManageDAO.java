package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.saas.domain.bussiness.CourseManage;
import cn.thinkjoy.saas.domain.bussiness.CourseManageVo;

import java.util.List;
import java.util.Map;

import java.util.List;

/**
 * Created by douzy on 17/2/13.
 */
public interface ICourseManageDAO extends IBaseDAO<CourseManage> {

    List<CourseManageVo> selectCourseManageInfo(Map map);
}
