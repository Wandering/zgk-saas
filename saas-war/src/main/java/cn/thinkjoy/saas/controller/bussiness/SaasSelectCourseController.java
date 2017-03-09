package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.common.protocol.Request;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.gk.pojo.Page;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.domain.SelectCourseSetting;
import cn.thinkjoy.saas.domain.SelectCourseTask;
import cn.thinkjoy.saas.dto.BaseDto;
import cn.thinkjoy.saas.dto.BaseStuDto;
import cn.thinkjoy.saas.dto.CourseBaseDto;
import cn.thinkjoy.saas.dto.SelectCourseSurveyDto;
import cn.thinkjoy.saas.enums.CourseStateEnum;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.service.ISelectCourseSettingService;
import cn.thinkjoy.saas.service.ISelectCourseTaskService;
import cn.thinkjoy.saas.service.common.ExceptionUtil;
import cn.thinkjoy.saas.service.web.ISelectCourseService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yangguorong on 17/2/28.
 */
@Controller
@RequestMapping("/saas/selectCourse")
public class SaasSelectCourseController {

    @Autowired
    private ISelectCourseTaskService iSelectCourseTaskService;

    @Autowired
    private ISelectCourseSettingService iSelectCourseSettingService;

    @Autowired
    private ISelectCourseService iSelectCourseService;

    @ResponseBody
    @ApiDesc(value = "新建选课任务",owner = "gryang")
    @RequestMapping(value = "/addSelectCourseTask",method = RequestMethod.POST)
    public Map<String,Object> addSelectCourseTask(@RequestBody Request request){

        SelectCourseTask task = JSONObject.parseObject(
                JSONObject.toJSON(request.getData()).toString(),
                SelectCourseTask.class
        );

        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("tnId",task.getTnId());
        paramMap.put("name",task.getName());
        paramMap.put("status",0);
        SelectCourseTask tmpTask = (SelectCourseTask) iSelectCourseTaskService.queryOne(paramMap);

        if(tmpTask != null){
            ExceptionUtil.throwException(ErrorCode.TASK_REPEAT);
        }

        task.setCreateDate(System.currentTimeMillis());
        task.setModifyDate(System.currentTimeMillis());
        task.setState(0);
        task.setStatus(0);
        iSelectCourseTaskService.insert(task);

        return Maps.newHashMap();
    }

    @ResponseBody
    @ApiDesc(value = "修改选课任务",owner = "gryang")
    @RequestMapping(value = "/updateSelectCourseTask",method = RequestMethod.POST)
    public Map<String,Object> updateSelectCourseTask(@RequestBody Request request){

        SelectCourseTask task = JSONObject.parseObject(
                JSONObject.toJSON(request.getData()).toString(),
                SelectCourseTask.class
        );

        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("tnId",task.getTnId());
        paramMap.put("name",task.getName());
        paramMap.put("status",0);
        SelectCourseTask tmpTask = (SelectCourseTask) iSelectCourseTaskService.queryOne(paramMap);

        if(tmpTask != null && !String.valueOf(tmpTask.getId()).equals(task.getId())){
            ExceptionUtil.throwException(ErrorCode.TASK_REPEAT);
        }

        task.setModifyDate(System.currentTimeMillis());
        iSelectCourseTaskService.update(task);

        return Maps.newHashMap();
    }

    @ResponseBody
    @ApiDesc(value = "删除选课任务",owner = "gryang")
    @RequestMapping(value = "/deleteSelectCourseTask",method = RequestMethod.POST)
    public Map<String,Object> deleteSelectCourseTask(@RequestBody Request request){

        String ids = request.getDataString("ids");

        String [] idsArr = ids.split(",");

        for(int i=0;i<idsArr.length;i++){
            SelectCourseTask task = new SelectCourseTask();
            task.setId(Long.valueOf(idsArr[i]));
            task.setStatus(-1);
            iSelectCourseTaskService.update(task);

            // 删除选课设置
            Map<String,Object> updateMap = Maps.newHashMap();
            updateMap.put("status",-1);
            updateMap.put("modifyDate",System.currentTimeMillis());
            Map<String,Object> conditionMap = Maps.newHashMap();
            conditionMap.put("taskId",idsArr[i]);
            iSelectCourseSettingService.updateByCondition(updateMap,conditionMap);
        }

        return Maps.newHashMap();
    }

    @ResponseBody
    @ApiDesc(value = "拉取选课任务",owner = "gryang")
    @RequestMapping(value = "/getSelectCourseTasks",method = RequestMethod.GET)
    public List<SelectCourseTask> getSelectCourseTasks(@RequestParam Integer tnId){

        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("tnId",tnId);
        paramMap.put("status",0);
        List<SelectCourseTask> tasks = iSelectCourseTaskService.queryList(paramMap, Constant.ID,Constant.DESC);

        for (SelectCourseTask task : tasks){
            // 0：选课未设置
            SelectCourseSetting setting = (SelectCourseSetting) iSelectCourseSettingService.findOne("taskId",task.getId());
            if(setting == null){
                task.setState(CourseStateEnum.WSZ.getCode());
            }
            // 1：选课未开始
            Date currentTime = new Date();
            Date startTime = task.getStartTime();
            Date endTime = task.getEndTime();
            if(currentTime.before(startTime)){
                task.setState(CourseStateEnum.WKS.getCode());
                continue;
            }
            //  2：学生选课中
            if(currentTime.after(startTime) && currentTime.before(endTime)){
                task.setState(CourseStateEnum.XKZ.getCode());
                continue;
            }
            // 3：选课结果待使用
            if(task.getState() == 0){
                task.setState(CourseStateEnum.DSY.getCode());
                continue;
            }
            // 4：选课结果已使用
            if(task.getState() == 1){
                task.setState(CourseStateEnum.YSY.getCode());
                continue;
            }
        }

        return tasks;
    }

    @ResponseBody
    @ApiDesc(value = "拉取课程集合（高考课程，校本课程）",owner = "gryang")
    @RequestMapping(value = "/getSelectCourses",method = RequestMethod.GET)
    public List<SelectCourseSetting> getSelectCourses(@RequestParam Integer taskId){

        return iSelectCourseService.getSelectCourses(taskId);
    }

    @ResponseBody
    @ApiDesc(value = "提交选课信息",owner = "gryang")
    @RequestMapping(value = "/saveOrUpdateSelectCourse",method = RequestMethod.POST)
    public Map<String,Object> saveOrUpdateSelectCourse(@RequestBody Request request){

        List<SelectCourseSetting> settings = JSONObject.parseArray(
                JSON.toJSON(request.getData().get("settings")).toString(),
                SelectCourseSetting.class
        );

        SelectCourseTask task = (SelectCourseTask) iSelectCourseTaskService.findOne(
                "id",
                settings.get(0).getTaskId()
        );

        if(task == null){
            ExceptionUtil.throwException(ErrorCode.TASK_NOT_EXIST);
        }
        if (task.getStartTime().before(new Date())){
            ExceptionUtil.throwException(ErrorCode.TASK_HAS_START);
        }

        iSelectCourseSettingService.deleteByProperty("taskId",settings.get(0).getTaskId());

        for(SelectCourseSetting setting : settings){
            setting.setCreateDate(System.currentTimeMillis());
            setting.setModifyDate(System.currentTimeMillis());
            setting.setStatus(0);
            iSelectCourseSettingService.insert(setting);
        }

        return Maps.newHashMap();
    }

    @ResponseBody
    @ApiDesc(value = "获取选课概况",owner = "gryang")
    @RequestMapping(value = "/getSelectCourseSurvey",method = RequestMethod.GET)
    public SelectCourseSurveyDto getSelectCourseSurvey(@RequestParam Integer taskId){

        return iSelectCourseService.SelectCourseSurvey(taskId);
    }

    @ResponseBody
    @ApiDesc(value = "单科选课结果",owner = "gryang")
    @RequestMapping(value = "/getSingleCourseSituation",method = RequestMethod.GET)
    public List<CourseBaseDto> getSingleCourseSituation(@RequestParam Integer taskId){

        return iSelectCourseService.getSingleCourseSituation(taskId);
    }

    @ResponseBody
    @ApiDesc(value = "组合选课结果",owner = "gryang")
    @RequestMapping(value = "/getGroupCourseSituation",method = RequestMethod.GET)
    public List<CourseBaseDto> getGroupCourseSituation(@RequestParam Integer taskId){

        return iSelectCourseService.getGroupCourseSituation(taskId);
    }

    @ResponseBody
    @ApiDesc(value = "查询学生高考课程选课详情（带分页）",owner = "gryang")
    @RequestMapping(value = "/getStuCourseDetail",method = RequestMethod.GET)
    public Page<BaseStuDto> getStuCourseDetail(@RequestParam Integer taskId,
                                               @RequestParam Integer type,
                                               @RequestParam Integer pageNo,
                                               @RequestParam Integer pageSize){

        return iSelectCourseService.getStuCourseDetail(taskId,type,pageNo,pageSize);
    }

    @ResponseBody
    @ApiDesc(value = "修改学生选课信息",owner = "gryang")
    @RequestMapping(value = "/updateStuCourse",method = RequestMethod.POST)
    public Map<String,Object> updateStuCourse(@RequestBody Request request){

        String stuNo = request.getDataString("stuNo");
        Integer taskId = request.getDataInteger("taskId");
        Integer type = request.getDataInteger("type");
        String courseIds = request.getDataString("courseIds");

        String [] courseIdArr = courseIds.split(",");
        iSelectCourseService.updateStuCourse(stuNo, taskId,type,Arrays.asList(courseIdArr));

        return Maps.newHashMap();
    }

    @ResponseBody
    @ApiDesc(value = "确认使用选课数据",owner = "gryang")
    @RequestMapping(value = "/confirmSelectCourse",method = RequestMethod.GET)
    public Map<String,Object> confirmSelectCourse(@RequestParam Integer taskId){

        iSelectCourseService.confirmSelectCourse(taskId);

        return Maps.newHashMap();
    }

    @ResponseBody
    @ApiDesc(value = "根据任务ID和课程类型查询课程信息",owner = "gryang")
    @RequestMapping(value = "/getCourseBaseInfo",method = RequestMethod.GET)
    public List<BaseDto> getCourseBaseInfo(@RequestParam Integer taskId, @RequestParam Integer type){

        List<BaseDto> dtos = iSelectCourseService.getCourseBaseInfo(taskId,type);

        return dtos;
    }

}
