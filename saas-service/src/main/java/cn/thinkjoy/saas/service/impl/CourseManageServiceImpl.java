package cn.thinkjoy.saas.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.saas.dao.bussiness.ICourseManageDAO;
import cn.thinkjoy.saas.domain.bussiness.*;
import cn.thinkjoy.saas.service.ICourseManageService;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    @Override
    public List<CourseManageMapperVo> selectCourseManageInfo(Map map) {

        List<CourseManageMapperVo> courseManageMapperVos=new ArrayList<>();

        List<CourseManageVo> courseManageVos= iCourseManageDAO.selectCourseManageGroup(map);


        for(CourseManageVo courseManageVo:courseManageVos) {

            CourseManageMapperVo courseManageMapperVo=new CourseManageMapperVo();

            courseManageMapperVo.setCourseBaseId(courseManageVo.getCourseId());
            courseManageMapperVo.setCourseName(courseManageVo.getCourseName());

            List<GradeCourseMap> gradeCourseMaps=new ArrayList<>();

            Map courseInfo=new HashMap();
            courseInfo.put("courseBaseId",courseManageVo.getCourseId());
            courseInfo.put("tnId",map.get("tnId"));

            List<CourseManageVo> courseVos=iCourseManageDAO.selectCourseManageInfo(courseInfo);

            for(CourseManageVo courseVo:courseVos){
                GradeCourseMap gradeCourseMap=new GradeCourseMap();
                gradeCourseMap.setGradeId(courseVo.getGradeId());
                gradeCourseMap.setCourseType(courseVo.getCourseType());
                gradeCourseMaps.add(gradeCourseMap);
            }
            courseManageMapperVo.setCourseMapList(gradeCourseMaps);
            courseManageMapperVos.add(courseManageMapperVo);
        }

        return courseManageMapperVos;
    }

    @Override
    public boolean insertCourseManage(CourseManage courseManage,String ids) {

        List<String> idsList = ParamsUtils.idsSplit(ids);

        List<CourseManage> courseManages = new ArrayList<>();
        for (int i = 0; i < idsList.size(); i++) {
            CourseManage courseObj=new CourseManage();

            String row = idsList.get(i);
            String[] rowArr = row.split(ParamsUtils.CLASSROOM_GRADE_COMBIN_CHAR);
            courseObj.setCourseBaseId(courseManage.getCourseBaseId());
            courseObj.setCustom(courseManage.getCustom());
            courseObj.setTnId(courseManage.getTnId());
            courseObj.setCreateTime(courseManage.getCreateTime());
            courseObj.setGradeId(Integer.valueOf(rowArr[0]));
            courseObj.setCourseType(Integer.valueOf(rowArr[1]) + "");
            courseManages.add(courseObj);
        }

        Integer result = iCourseManageDAO.addCourses(courseManages);

        return result > 0;
    }

    @Override
    public boolean updateCourseManage(CourseManage courseManage,String ids) {
        boolean result = false;
        Map map = new HashMap();
        map.put("tnId", courseManage.getTnId());
        map.put("courseBaseId", courseManage.getCourseBaseId());
        Integer resu = iCourseManageDAO.deleteByCondition(map);

        if (resu > 0)
            result = insertCourseManage(courseManage, ids);

        return result;
    }

    @Override
    public boolean deleteCourseManage(Integer tnId,Integer courseId) {
        Map map = new HashMap();
        map.put("tnId", tnId);
        map.put("courseBaseId", courseId);
        Integer resu = iCourseManageDAO.deleteByCondition(map);

        return resu > 0;
    }


}
