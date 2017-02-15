package cn.thinkjoy.saas.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.saas.dao.bussiness.ICourseManageDAO;
import cn.thinkjoy.saas.domain.bussiness.CourseManage;
import cn.thinkjoy.saas.service.ICourseManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by douzy on 17/2/14.
 */
@Service("CourseManageServiceImpl")
public class CourseManageServiceImpl extends AbstractPageService<IBaseDAO<CourseManage>, CourseManage> implements ICourseManageService<IBaseDAO<CourseManage>,CourseManage> {
    @Autowired
    private ICourseManageDAO iCourseManageDAO;

    @Override
    public IBaseDAO<CourseManage> getDao() {
        return iCourseManageDAO;
    }

}
