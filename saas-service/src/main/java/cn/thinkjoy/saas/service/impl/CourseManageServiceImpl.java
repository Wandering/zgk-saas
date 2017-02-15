package cn.thinkjoy.saas.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.saas.dao.bussiness.ICourseManageDAO;
import cn.thinkjoy.saas.domain.bussiness.CourseManage;
import cn.thinkjoy.saas.domain.bussiness.CourseManageVo;
import cn.thinkjoy.saas.service.ICourseManageService;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     *
     * @param map
     * @return
     */
    public List<CourseManageVo> selectCourseManageInfo(Map map) {
        return iCourseManageDAO.selectCourseManageInfo(map);
    }

    @Override
    public boolean insertCourseManage(CourseManage courseManage,String ids) {

        List<String> idsList = ParamsUtils.idsSplit(ids);

        List<CourseManage> courseManages = new ArrayList<>();
        for (int i = 0; i < idsList.size(); i++) {
            String row = idsList.get(i);
            String[] rowArr = row.split(ParamsUtils.CLASSROOM_GRADE_COMBIN_CHAR);

            courseManage.setGradeId(Integer.valueOf(rowArr[0]));

            courseManage.setCourseType(Integer.valueOf(rowArr[1]) + "");
            courseManages.add(courseManage);
        }

        Integer result = iCourseManageDAO.addCourses(courseManages);

        return result > 0;
    }
}
