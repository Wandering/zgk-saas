package cn.thinkjoy.saas.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.saas.dao.ICountyDAO;
import cn.thinkjoy.saas.domain.County;
import cn.thinkjoy.saas.service.ICountyService;
import cn.thinkjoy.saas.service.ICourseBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by douzy on 17/2/14.
 */
@Service("CourseBaseInfoServiceImpl")
public class CourseBaseInfoServiceImpl extends AbstractPageService<IBaseDAO<County>, County> implements ICourseBaseInfoService<IBaseDAO<County>,County> {
    @Autowired
    private ICountyDAO countyDAO;

    @Override
    public IBaseDAO<County> getDao() {
        return countyDAO;
    }

}
