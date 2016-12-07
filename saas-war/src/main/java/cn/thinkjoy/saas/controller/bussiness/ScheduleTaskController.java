package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.saas.common.UserContext;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.domain.JwScheduleTask;
import cn.thinkjoy.saas.dto.CourseBaseDto;
import cn.thinkjoy.saas.dto.JwScheduleTaskDto;
import cn.thinkjoy.saas.dto.TeacherBaseDto;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.enums.GradeEnum;
import cn.thinkjoy.saas.enums.TermEnum;
import cn.thinkjoy.saas.service.IJwCourseService;
import cn.thinkjoy.saas.service.IJwScheduleTaskService;
import cn.thinkjoy.saas.service.bussiness.IEXScheduleBaseInfoService;
import cn.thinkjoy.saas.service.common.ExceptionUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 2016/12/6.
 *
 * 排课任务CRUD
 */
@Controller
@RequestMapping("/scheduleTask")
public class ScheduleTaskController {

    @Autowired
    IJwScheduleTaskService jwScheduleTaskService;

    @Autowired
    private IEXScheduleBaseInfoService iexScheduleBaseInfoService;

    /**
     * 新建排课任务
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveScheduleTask")
    public boolean saveScheduleTask(@RequestParam String scheduleName,
                                               @RequestParam String grade,
                                               @RequestParam String year,
                                               @RequestParam String term){
        //参数校验
        if (StringUtils.isEmpty(scheduleName)||StringUtils.isEmpty(grade)||StringUtils.isEmpty(year)||StringUtils.isEmpty(term)){
            ExceptionUtil.throwException(ErrorCode.PARAN_NULL);
        }
        JwScheduleTask jwScheduleTask = new JwScheduleTask();
        jwScheduleTask.setScheduleName(scheduleName);
        jwScheduleTask.setGrade(grade);
        jwScheduleTask.setTerm(term);
        jwScheduleTask.setYear(year);
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        jwScheduleTask.setTnId(tnId);
        jwScheduleTask.setCreateDate(System.currentTimeMillis());
        jwScheduleTask.setStatus(Constant.SCHEDULE_TASK_INIT_STATUS);
        return jwScheduleTaskService.add(jwScheduleTask)>0;
    }

    @ResponseBody
    @RequestMapping("/queryGradeInfo")
    public List<Map<String,Object>> queryGradeInfo(){
        List<Map<String,Object>> list = new ArrayList();
        Map<String,Object> map = Maps.newHashMap();
        map.put("key","1");
        map.put("value","高一");
        list.add(map);
        map = Maps.newHashMap();
        map.put("key","2");
        map.put("value","高二");
        list.add(map);
        map = Maps.newHashMap();
        map.put("key","3");
        map.put("value","高三");
        list.add(map);
        return list;
    }

    @ResponseBody
    @RequestMapping("/queryScheduleTask")
    public List<JwScheduleTaskDto> queryScheduleTask(){
        List<JwScheduleTaskDto> returnList = new ArrayList<>();
        Map<String,Object> map = Maps.newHashMap();
        map.put("tnId",UserContext.getCurrentUser().getTnId());
        List<JwScheduleTask> dataList = jwScheduleTaskService.queryList(map,"createDate","desc");
        if (dataList!=null&&dataList.size()>0){
            returnList = JwScheduleTask2Dto(dataList);
        }
        return returnList;
    }

    @ResponseBody
    @RequestMapping("/checkTaskBaseInfo")
    public boolean checkTaskBaseInfo(@RequestParam Integer taskId){
        if (false){
            throw  new BizException(ErrorCode.TASK_ERROR.getCode(),"您还未填写****、****、*****字段信息，请至学生管理中完善");
        }
        return true;
    }

    private List<JwScheduleTaskDto> JwScheduleTask2Dto(List<JwScheduleTask> jwScheduleTasks){
        List<JwScheduleTaskDto> list = new ArrayList<>();
        if (jwScheduleTasks!=null && jwScheduleTasks.size()>0) {
            for (JwScheduleTask jwScheduleTask:jwScheduleTasks){
                list.add(JwScheduleTask2Dto(jwScheduleTask));
            }
        }
        return list;
    }

    private JwScheduleTaskDto JwScheduleTask2Dto(JwScheduleTask jwScheduleTask){
        if (jwScheduleTask==null)return null;
        JwScheduleTaskDto dto = new JwScheduleTaskDto();
        BeanUtils.copyProperties(jwScheduleTask,dto);
        dto.setGradeName(GradeEnum.getName(Integer.valueOf(jwScheduleTask.getGrade())));
        dto.setTermName(TermEnum.getName(Integer.valueOf(jwScheduleTask.getTerm())));
        return dto;
    }

    @ResponseBody
    @ApiDesc(value = "根据任务ID获取课程信息",owner = "gryang")
    @RequestMapping(value = "/queryCourseInfoByTaskId",method = RequestMethod.GET)
    public List<CourseBaseDto> queryCourseInfoByTaskId(@RequestParam long taskId){
        List<CourseBaseDto> dtos = iexScheduleBaseInfoService.queryCourseInfoByTaskId(taskId);
        return dtos;
    }

    @ResponseBody
    @ApiDesc(value = "添加课程课时信息",owner = "gryang")
    @RequestMapping(value = "/updateCourseTime",method = RequestMethod.POST)
    public Map updateCourseTime(@RequestParam long taskId,@RequestParam long courseId,@RequestParam String time){
        return null;
    }

    @ResponseBody
    @ApiDesc(value = "根据任务ID获取教师信息",owner = "gryang")
    @RequestMapping(value = "/queryTeacherByTaskId",method = RequestMethod.GET)
    public TeacherBaseDto queryTeacherByTaskId(@RequestParam long taskId){
        return null;
    }

    @ResponseBody
    @ApiDesc(value = "自动补全教师姓名",owner = "gryang")
    @RequestMapping(value = "/queryTeacherByKeyWord",method = RequestMethod.GET)
    public TeacherBaseDto queryTeacherByKeyWord(@RequestParam String keyWord){
        return null;
    }

    @ResponseBody
    @ApiDesc(value = "保存或修改教师配置信息",owner = "gryang")
    @RequestMapping(value = "/saveOrUpdateTeacher",method = RequestMethod.POST)
    public Map saveOrUpdateTeacher(@RequestParam long taskId,
                                   @RequestParam long tnId,
                                   @RequestParam long teacherId,
                                   @RequestParam int classNum,
                                   @RequestParam String classId){
        return null;
    }

    @ResponseBody
    @ApiDesc(value = "删除教师配置信息",owner = "gryang")
    @RequestMapping(value = "/deleteTeacher",method = RequestMethod.GET)
    public TeacherBaseDto deleteTeacher(@RequestParam long taskId,@RequestParam long teacherId){
        return null;
    }

}
