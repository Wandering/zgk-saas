package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.dao.bussiness.ICourseManageDAO;
import cn.thinkjoy.saas.dao.bussiness.IEXCourseManageDAO;
import cn.thinkjoy.saas.domain.bussiness.CourseManage;
import cn.thinkjoy.saas.dto.CourseManageDto;
import cn.thinkjoy.saas.service.bussiness.IEXCourseManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 17/2/14.
 */
@Service("EXCourseManageServiceImpl")
public class EXCourseManageServiceImpl implements IEXCourseManageService {

    @Resource
    IEXCourseManageDAO iexCourseManageDAO;

    /**
     * 根据TnId查询课程
     *
     * @param tnId
     * @return
     */
    @Override
    public List<CourseManageDto> getCourseByTnId(Integer tnId) {
        Map<String,Object> params = new HashMap();
        params.put("tnId",tnId);
        return iexCourseManageDAO.getCourseByParams(params);
    }

    /**
     * 根据TnId查询课程
     *
     * @param tnId
     * @param gradeCode
     * @return
     */
    @Override
    public List<CourseManageDto> getCourseByTnIdAndGrade(Integer tnId, Integer gradeCode) {
        Map<String,Object> params = new HashMap();
        params.put("tnId",tnId);
        params.put("gradeId",gradeCode);
        return iexCourseManageDAO.getCourseByParams(params);
    }

    /**
     * 根据课程和TnId查询年级信息
     *
     * @param tnId
     * @param subject
     * @return
     */
    @Override
    public List<CourseManageDto> getCourseGradeByTnIdAndSubject(Integer tnId, String subject) {
        Map<String,Object> params = new HashMap();
        params.put("tnId",tnId);
        params.put("courseBaseName",subject);
        return iexCourseManageDAO.getCourseByParams(params);
    }
}
