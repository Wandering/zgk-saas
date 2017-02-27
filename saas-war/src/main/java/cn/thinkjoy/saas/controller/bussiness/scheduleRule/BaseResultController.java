package cn.thinkjoy.saas.controller.bussiness.scheduleRule;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.saas.common.UserContext;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.domain.JwScheduleTask;
import cn.thinkjoy.saas.dto.CourseManageDto;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.enums.GradeEnum;
import cn.thinkjoy.saas.service.IJwScheduleTaskService;
import cn.thinkjoy.saas.service.bussiness.EXITenantConfigInstanceService;
import cn.thinkjoy.saas.service.bussiness.IEXCourseManageService;
import cn.thinkjoy.saas.service.bussiness.IEXTeacherService;
import cn.thinkjoy.saas.service.common.ExceptionUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by yangyongping on 2016/12/10.
 */
@Controller
@RequestMapping("/baseResult")
public class BaseResultController {
    @Autowired
    private IJwScheduleTaskService jwScheduleTaskService;

    @Autowired
    EXITenantConfigInstanceService exiTenantConfigInstanceService;
    @Autowired
    private IEXCourseManageService courseManageService;
    @Autowired
    private IEXTeacherService teacherService;

    /**
     * 获取教室名称
     * @return
     */
    @RequestMapping(value = "/queryClass",method = RequestMethod.GET)
    @ResponseBody
    public List getCourseResult(@RequestParam Integer taskId) {
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        JwScheduleTask jwScheduleTask = (JwScheduleTask)jwScheduleTaskService.fetch(taskId);
        //获取行政班班级列表
        List list = exiTenantConfigInstanceService.getClassByTnIdAndGrade(tnId, GradeEnum.getName(Integer.valueOf(jwScheduleTask.getGrade())),Constant.CLASS_ADM);
        return list;
    }

    /**
     * 获取教室名称
     * @return
     */
    @RequestMapping(value = "/queryTeacher",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getTeacher(@RequestParam Integer taskId,@RequestParam String teacherCourse) {
        if (StringUtils.isEmpty(teacherCourse)){
            ExceptionUtil.throwException(ErrorCode.PARAN_NULL);
        }
        JwScheduleTask jwScheduleTask = (JwScheduleTask)jwScheduleTaskService.fetch(taskId);
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        Map<String,Object> param = new HashMap<>();
        param.put("teacher_grade",GradeEnum.getName(Integer.valueOf(jwScheduleTask.getGrade())));
        param.put("teacher_major_type",teacherCourse);
        List<LinkedHashMap<String,Object>> rtnList = teacherService.getScheduleTeacherByTnIdAndTaskId(tnId,taskId,param);
        if (rtnList == null) {
            throw new BizException("error","当前科目所查教师为空");
        }
        return teacherBaseDtotoMap(rtnList);
    }
    List<Map<String,Object>> teacherBaseDtotoMap(List<LinkedHashMap<String,Object>> list){
        Map<String,Object> map ;
        List<Map<String,Object> >  maps = new ArrayList<>();
        for (LinkedHashMap<String,Object> teacherBaseDto:list){
            map = Maps.newHashMap();
            map.put("id",teacherBaseDto.get("id"));
            map.put("teacherName",teacherBaseDto.get("teacher_name"));
            maps.add(map);
        }
        return maps;
    }
    /**
     * 获取课程信息
     * @return
     */
    @RequestMapping(value = "/queryCourse",method = RequestMethod.GET)
    @ResponseBody
    public Set<Map<String,Object>> queryCourse(@RequestParam Integer taskId) {
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        List<CourseManageDto> courseManageDtos = courseManageService.getCourseByTnId(tnId);
        Map<String,Object> map;
        Set<Map<String,Object>> maps = new HashSet<>();
        for (CourseManageDto courseManageDto : courseManageDtos) {
            map = new HashMap<>();
            map.put("courseBaseId",courseManageDto.getCourseBaseId());
            map.put("courseBaseName",courseManageDto.getCourseBaseName());
            maps.add(map);
        }

        return maps;
    }

}
