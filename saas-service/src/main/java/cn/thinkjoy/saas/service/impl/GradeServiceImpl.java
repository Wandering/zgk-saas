package cn.thinkjoy.saas.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.saas.dao.IGradeDAO;
import cn.thinkjoy.saas.domain.Grade;
import cn.thinkjoy.saas.service.IGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by douzy on 16/10/21.
 */
@Service("GradeServiceImpl")
public class GradeServiceImpl extends AbstractPageService<IBaseDAO<Grade>, Grade> implements IGradeService<IBaseDAO<Grade>,Grade> {
    @Autowired
    private IGradeDAO iGradeDAO;

    @Override
    public IBaseDAO<Grade> getDao() {
        return iGradeDAO;
    }
}
