package cn.thinkjoy.saas.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.saas.dao.bussiness.ICourseBaseInfoDAO;
import cn.thinkjoy.saas.domain.bussiness.CourseBaseInfo;
import cn.thinkjoy.saas.service.ICourseBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by douzy on 17/2/14.
 */
@Service("CourseBaseInfoServiceImpl")
public class CourseBaseInfoServiceImpl extends AbstractPageService<IBaseDAO<CourseBaseInfo>, CourseBaseInfo> implements ICourseBaseInfoService<IBaseDAO<CourseBaseInfo>,CourseBaseInfo> {
    @Autowired
    private ICourseBaseInfoDAO iCourseBaseInfoDAO;

    @Override
    public IBaseDAO<CourseBaseInfo> getDao() {
        return iCourseBaseInfoDAO;
    }

}
