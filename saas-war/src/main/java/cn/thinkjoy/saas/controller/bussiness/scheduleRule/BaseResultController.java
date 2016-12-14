package cn.thinkjoy.saas.controller.bussiness.scheduleRule;

import cn.thinkjoy.saas.common.UserContext;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.bussiness.IEXTeantCustomDAO;
import cn.thinkjoy.saas.domain.JwClassBaseInfo;
import cn.thinkjoy.saas.domain.JwScheduleTask;
import cn.thinkjoy.saas.service.*;
import cn.thinkjoy.saas.service.bussiness.IEXTenantCustomService;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 2016/12/10.
 */
@Controller
@RequestMapping("/baseResult")
public class BaseResultController {
    @Autowired
    private IJwRoomService jwRoomService;
    @Autowired
    private IJwTeacherBaseInfoService jwTeacherBaseInfoService;
    @Autowired
    private IJwScheduleTaskService jwScheduleTaskService;
    @Autowired
    private IJwCourseBaseInfoService jwCourseBaseInfoService;
    @Autowired
    private IJwClassBaseInfoService jwClassBaseInfoService;
    @Autowired
    private IEXTenantCustomService iexTenantCustomService;

    /**
     * 获取教室名称
     * @return
     */
    @RequestMapping(value = "/queryRoom",method = RequestMethod.GET)
    @ResponseBody
    public List getCourseResult(@RequestParam Integer taskId) {
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        JwScheduleTask jwScheduleTask = (JwScheduleTask)jwScheduleTaskService.fetch(taskId);
        Map<String,Object>  map = Maps.newHashMap();
        map.put("tnId",tnId);
        map.put("taskId",taskId);
        map.put("grade",jwScheduleTask.getGrade());
        List list = jwRoomService.queryList(map,"id","desc");
        return list;
    }

    /**
     * 获取教室名称
     * @return
     */
    @RequestMapping(value = "/queryTeacher",method = RequestMethod.GET)
    @ResponseBody
    public List getTeacher(@RequestParam Integer taskId) {
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        JwScheduleTask jwScheduleTask = (JwScheduleTask)jwScheduleTaskService.fetch(taskId);
        Map<String,Object>  map = Maps.newHashMap();
        map.put("tnId",tnId);
        map.put("taskId",taskId);
        map.put("grade",jwScheduleTask.getGrade());
        List list = jwTeacherBaseInfoService.queryList(map,"id","desc");
        return list;
    }

    /**
     * 获取课程信息
     * @return
     */
    @RequestMapping(value = "/queryCourse",method = RequestMethod.GET)
    @ResponseBody
    public List queryCourse(@RequestParam Integer taskId) {
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        Map<String,Object>  map = Maps.newHashMap();
        map.put("tnId",tnId);
        map.put("taskId",taskId);
        List list = jwCourseBaseInfoService.queryList(map,"id","desc");
        return list;
    }



    /**
     * 获取教室
     * @return
     */
    @RequestMapping(value = "/queryClass",method = RequestMethod.GET)
    @ResponseBody
    public List queryClass(@RequestParam Integer taskId) {
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        JwScheduleTask jwScheduleTask = (JwScheduleTask)jwScheduleTaskService.fetch(taskId);
        Map<String,Object>  map = Maps.newHashMap();
        map.put("tnId",tnId);
        map.put("grade",jwScheduleTask.getGrade());
        map.put("classType", Constant.DEFULT_CLASS_TYPE);
        List list = jwClassBaseInfoService.queryList(map,"id","desc");
        return list;
    }
    /**
     * 获取学生
     * @return
     */
    @RequestMapping(value = "/queryStudent",method = RequestMethod.GET)
    @ResponseBody
    public List queryStudent(@RequestParam Integer taskId,@RequestParam Integer classId) {
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        JwClassBaseInfo jwClassBaseInfo = (JwClassBaseInfo)jwClassBaseInfoService.fetch(classId);
        List<LinkedHashMap<String,Object>> list = iexTenantCustomService.getTenantCustom("student",tnId,"student_class",jwClassBaseInfo.getClassName(),0,1000);
        return list;
    }

}
