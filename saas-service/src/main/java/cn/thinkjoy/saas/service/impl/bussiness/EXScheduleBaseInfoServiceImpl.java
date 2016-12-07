package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.IJwCourseBaseInfoDAO;
import cn.thinkjoy.saas.dao.IJwCourseDAO;
import cn.thinkjoy.saas.dao.IJwScheduleTaskDAO;
import cn.thinkjoy.saas.domain.JwCourse;
import cn.thinkjoy.saas.domain.JwCourseBaseInfo;
import cn.thinkjoy.saas.domain.JwScheduleTask;
import cn.thinkjoy.saas.dto.CourseBaseDto;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.enums.StatusEnum;
import cn.thinkjoy.saas.service.bussiness.IEXScheduleBaseInfoService;
import cn.thinkjoy.saas.service.common.ExceptionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yangguorong on 16/12/7.
 */
@Service("eXScheduleBaseInfoServiceImpl")
public class EXScheduleBaseInfoServiceImpl implements IEXScheduleBaseInfoService {

    @Autowired
    private IJwCourseDAO jwCourseDAO;

    @Autowired
    private IJwCourseBaseInfoDAO jwCourseBaseInfoDAO;

    @Autowired
    private IJwScheduleTaskDAO jwScheduleTaskDAO;

    @Override
    public List<CourseBaseDto> queryCourseInfoByTaskId(int taskId) {

        // 先从任务基本信息中获取课程
        List<JwCourse> jwCourses = jwCourseDAO.findList("task_id",taskId,"id", Constant.DESC);
        if(jwCourses.size() != 0){
            return convertCourses2Dtos(jwCourses);
        }

        // 根据租户ID和年级获取课程
        JwScheduleTask task = jwScheduleTaskDAO.findOne("id",taskId,"id", Constant.DESC);
        if(task == null || task.getDelStatus() == StatusEnum.D.getCode()){
            ExceptionUtil.throwException(ErrorCode.TASK_NOT_EXIST);
        }

        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("tn_id",task.getTnId());
        paramMap.put("grade",task.getGrade());
        List<JwCourseBaseInfo> infos = jwCourseBaseInfoDAO.queryList(paramMap,"id",Constant.DESC);

        return convertInfos2Dtos(infos);
    }

    /**
     * List<JwCourse>  -->  List<CourseBaseDto>
     *
     * @param jwCourses
     * @return
     */
    private List<CourseBaseDto> convertCourses2Dtos(List<JwCourse> jwCourses){

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

    /**
     * List<JwCourseBaseInfo>  -->  List<CourseBaseDto>
     *
     * @param infos
     * @return
     */
    private List<CourseBaseDto> convertInfos2Dtos(List<JwCourseBaseInfo> infos){

        List<CourseBaseDto> dtos = Lists.newArrayList();
        for(JwCourseBaseInfo info : infos){
            CourseBaseDto dto = new CourseBaseDto();
            dto.setCourseId((long)info.getId());
            dto.setCourseName(info.getCourseName());
            dto.setTime("0");
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public int getTnIdByTaskId(int taskId) {
        JwScheduleTask task = jwScheduleTaskDAO.findOne("id",taskId,"id", Constant.DESC);
        if(task == null || task.getDelStatus() == StatusEnum.D.getCode()){
            ExceptionUtil.throwException(ErrorCode.TASK_NOT_EXIST);
        }
        return task.getTnId();
    }
}
