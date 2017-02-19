package cn.thinkjoy.saas.controller.bussiness.scheduleRule;

import cn.thinkjoy.saas.common.UserContext;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.domain.JwScheduleTask;
import cn.thinkjoy.saas.dto.CourseManageDto;
import cn.thinkjoy.saas.dto.TeacherBaseDto;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.enums.GradeEnum;
import cn.thinkjoy.saas.service.*;
import cn.thinkjoy.saas.service.bussiness.*;
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
    private IEXScheduleBaseInfoService iexScheduleBaseInfoService;
    @Autowired
    private IEXCourseManageService courseManageService;

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
        String tableName = "saas"+Constant.TIME_INTERVAL+tnId+Constant.TIME_INTERVAL+Constant.TABLE_TYPE_TEACHER+Constant.TIME_INTERVAL+"excel";
        List<Map<String,Object>> params = new ArrayList<>();
        Map<String,Object> param;
        param = new HashMap<>();
        param.put("key","teacher_grade");
        param.put("op","=");
        param.put("value",GradeEnum.getName(Integer.valueOf(jwScheduleTask.getGrade())));
        params.add(param);
        param = new HashMap<>();
        param.put("key","teacher_major_type");
        param.put("op","=");
        param.put("value",teacherCourse);
        params.add(param);
        List<LinkedHashMap<String,Object>> rtnList = exiTenantConfigInstanceService.likeTeacherByParams(tableName,params);
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
        Map<String,Object> map = new HashMap<>();
        Set<Map<String,Object>> maps = new HashSet<>();
        for (CourseManageDto courseManageDto : courseManageDtos) {
            map.put("courseBaseId",courseManageDto.getCourseBaseId());
            map.put("courseBaseName",courseManageDto.getCourseBaseName());
            maps.add(map);
        }

        return maps;
    }




}
