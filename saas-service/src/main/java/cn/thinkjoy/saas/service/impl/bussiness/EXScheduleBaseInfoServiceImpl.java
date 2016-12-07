package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.IJwCourseBaseInfoDAO;
import cn.thinkjoy.saas.dao.IJwCourseDAO;
import cn.thinkjoy.saas.dao.IJwScheduleTaskDAO;
import cn.thinkjoy.saas.domain.JwCourse;
import cn.thinkjoy.saas.domain.JwCourseBaseInfo;
import cn.thinkjoy.saas.domain.JwScheduleTask;
import cn.thinkjoy.saas.dto.CourseBaseDto;
import cn.thinkjoy.saas.service.bussiness.IEXScheduleBaseInfoService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by yangguorong on 16/12/7.
 */
public class EXScheduleBaseInfoServiceImpl implements IEXScheduleBaseInfoService {

    @Autowired
    private IJwCourseDAO jwCourseDAO;

    @Autowired
    private IJwCourseBaseInfoDAO jwCourseBaseInfoDAO;

    @Autowired
    private IJwScheduleTaskDAO jwScheduleTaskDAO;

    @Override
    public List<CourseBaseDto> queryCourseInfoByTaskId(long taskId) {

        // 先从任务基本信息中获取课程
        List<JwCourse> jwCourses = jwCourseDAO.findList("task_id",taskId,"id", Constant.DESC);
        if(jwCourses.size() != 0){
            return convert2Dtos(jwCourses);
        }

        // 根据租户ID和年级获取课程
        JwScheduleTask task = jwScheduleTaskDAO.findOne("id",taskId,"id", Constant.DESC);
        if(task == null){

        }

        return null;
    }

    /**
     * List<JwCourse>  -->  List<CourseBaseDto>
     *
     * @param jwCourses
     * @return
     */
    private List<CourseBaseDto> convert2Dtos(List<JwCourse> jwCourses){

        List<CourseBaseDto> dtos = Lists.newArrayList();
        for(JwCourse course : jwCourses){
            CourseBaseDto dto = new CourseBaseDto();
            dto.setCourseId(course.getCourseId());
            JwCourseBaseInfo info = jwCourseBaseInfoDAO.findOne("id",course.getCourseId(),"id",Constant.DESC);
            dto.setCourseName(info.getCourseName());
            dto.setTime(course.getCourseHour());
            dtos.add(dto);
        }
        return dtos;
    }

}
