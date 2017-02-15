package cn.thinkjoy.saas.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.saas.dao.ICountyDAO;
import cn.thinkjoy.saas.domain.County;
import cn.thinkjoy.saas.service.ICountyService;
import cn.thinkjoy.saas.service.ICourseManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by douzy on 17/2/14.
 */
@Service("CourseManageServiceImpl")
public class CourseManageServiceImpl extends AbstractPageService<IBaseDAO<County>, County> implements ICourseManageService<IBaseDAO<County>,County> {
    @Autowired
    private ICountyDAO countyDAO;

    @Override
    public IBaseDAO<County> getDao() {
        return countyDAO;
    }

}
