package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.dao.bussiness.ICourseBaseInfoDAO;
import cn.thinkjoy.saas.domain.bussiness.CourseBaseInfo;
import cn.thinkjoy.saas.service.bussiness.IEXCourseBaseInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by douzy on 17/2/14.
 */
@Service("EXCourseBaseInfoServiceImpl")
public class EXCourseBaseInfoServiceImpl implements IEXCourseBaseInfoService {
    @Resource
    ICourseBaseInfoDAO iCourseBaseInfoDAO;



    @Override
    public List<CourseBaseInfo> getCourseBaseInfoList() {
        return iCourseBaseInfoDAO.findAll("id", "asc");
    }
}
