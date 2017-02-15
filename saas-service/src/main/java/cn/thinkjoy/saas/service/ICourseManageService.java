package cn.thinkjoy.saas.service;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.saas.domain.bussiness.CourseManage;
import cn.thinkjoy.saas.domain.bussiness.CourseManageVo;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 17/2/14.
 */
public interface ICourseManageService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T> {

    List<CourseManageVo> selectCourseManageInfo(Map map);

    public boolean insertCourseManage(CourseManage courseManage,String ids);
}
