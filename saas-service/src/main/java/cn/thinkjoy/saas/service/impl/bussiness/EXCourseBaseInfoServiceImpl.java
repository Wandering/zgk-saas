package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.dao.bussiness.ICourseBaseInfoDAO;
import cn.thinkjoy.saas.dao.bussiness.IEXCourseBaseInfoDAO;
import cn.thinkjoy.saas.dao.bussiness.IEXCourseManageDAO;
import cn.thinkjoy.saas.domain.bussiness.CourseBaseInfo;
import cn.thinkjoy.saas.service.bussiness.IEXCourseBaseInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 17/2/14.
 */
@Service("EXCourseBaseInfoServiceImpl")
public class EXCourseBaseInfoServiceImpl implements IEXCourseBaseInfoService {
    @Resource
    ICourseBaseInfoDAO iCourseBaseInfoDAO;

    @Resource
    IEXCourseManageDAO iexCourseManageDAO;



    @Override
    public List<CourseBaseInfo> getCourseBaseInfoList() {
        return iCourseBaseInfoDAO.findAll("id", "asc");
    }

    @Override
    public CourseBaseInfo getCourseBaseInfoById(int id) {
        return iCourseBaseInfoDAO.fetch(id);
    }

    @Override
    public List<CourseBaseInfo> queryListByCondition(Map<String, Object> condition) {
        return iexCourseManageDAO.queryListByCondition(condition);
    }
}
